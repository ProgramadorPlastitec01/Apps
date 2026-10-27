package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.FormatJpaController;
import Connection.ConnectionSQLServer;
import Connection.ConnectionRegistrosLAB;
import Connection.ConnectionGeneracionLotes;
import Controller.SettingJpaController;
import Controller.CertificatesJpaController;
import Controller.CertificateFileJpaController;
import Controller.CertificateFileRow;
import Method.SupportDocumentUi;
import java.util.List;
import Method.Util;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpSession;

public class Visual extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        FormatJpaController FormatJpa = new FormatJpaController();
        ConnectionSQLServer FactoryJpa = new ConnectionSQLServer();
        ConnectionRegistrosLAB RegistrosLabJpa = new ConnectionRegistrosLAB();
        SettingJpaController SettingJpa = new SettingJpaController();
        CertificatesJpaController CertificatesJpa = new CertificatesJpaController();
        CertificateFileJpaController CertificateFilesJpa = new CertificateFileJpaController();
        ConnectionGeneracionLotes GeneracionLotesJpa = new ConnectionGeneracionLotes();
        String Type = "", Product = "", ProductFact = "", Batch = "", DataTechnicalSheet = "", Html = "", BatchLay = "", Record = "", FormatName = "", Message = "", IdSig = "", Permission = "", Client = "",
                DateI = "", DateF = "", Cycle = "";
        String[] BatchCycle = {};
        int Order = 0, IdForm = 0, Count = 1, Count2 = 1, IdCertificates = 0, State = 0, TempDelete = 0, StateCerti = 0, TempM = 0, AmoutReg = 0;
        List lst_content = null;
        List lst_headFact = null;
        List lst_RLab = null;
        List lst_Glotes1 = null;
        List lst_GlotesRep = null;
        List lst_parameter = null;
        List lst_data = null;
        List lst_welds = null;
        List lst_sheet = null;
        List lst_prm = null;
        List lst_tags = null;
        Object[] parametrosVerificacion = null;
        try {
            //<editor-fold defaultstate="collapsed" desc="ENVIRONMENT VARIABLES">
            // Variables recibidas
            Type = Optional.ofNullable(pageContext.getRequest().getParameter("Type")).orElse("");
            FormatName = Optional.ofNullable(pageContext.getRequest().getParameter("FormatName")).orElse("");
            Product = Optional.ofNullable(pageContext.getRequest().getParameter("Product")).orElse("");
            Batch = Optional.ofNullable(pageContext.getRequest().getParameter("Batch")).orElse("");
            // Variables derivadas
            // Procesar FormatName
            try {
                if (!FormatName.isEmpty() && FormatName.contains("/")) {
                    String[] parts = FormatName.split("/");
                    IdForm = Integer.parseInt(parts[0]);
                    Record = parts.length > 1 ? parts[1] : "";
                }
            } catch (Exception e) {
                IdForm = 0;
                Record = "";
            }
            // Procesar IdCertificates
            try {
                IdCertificates = Integer.parseInt(Optional.ofNullable(pageContext.getRequest().getParameter("IdCertificates")).orElse("0"));
            } catch (NumberFormatException e) {
                IdCertificates = 0;
            }
            try {
                StateCerti = Integer.parseInt(Optional.ofNullable(pageContext.getRequest().getParameter("StateCerti")).orElse("0"));
            } catch (NumberFormatException e) {
                StateCerti = 0;
            }
            // Procesar Order
            try {
                Order = Integer.parseInt(Optional.ofNullable(pageContext.getRequest().getParameter("Order")).orElse("0"));
            } catch (NumberFormatException e) {
                Order = 0;
            }
            try {
                TempM = Integer.parseInt(Optional.ofNullable(pageContext.getRequest().getParameter("TempM")).orElse("0"));
            } catch (NumberFormatException e) {
                TempM = 0;
            }
            // Procesar ProductFact
            try {
                ProductFact = Product.replace("2C", "3C");
            } catch (Exception e) {
                ProductFact = "";
            }
            // Procesar TempDelete
            try {
                TempDelete = Integer.parseInt(Optional.ofNullable(pageContext.getRequest().getParameter("TempDelete")).orElse("0"));
            } catch (NumberFormatException e) {
                TempDelete = 0;
            }
            try {
                Permission = sesion.getAttribute("Permisos").toString();
            } catch (Exception e) {
                Permission = "";
            }
            try {
                DateI = sesion.getAttribute("DateI").toString();
            } catch (Exception e) {
                DateI = "";
            }
            try {
                DateF = sesion.getAttribute("DateF").toString();
            } catch (Exception e) {
                DateF = "";
            }
            try {
                AmoutReg = Integer.parseInt(Optional.ofNullable(pageContext.getRequest().getParameter("AmoutReg")).orElse("0"));
            } catch (NumberFormatException e) {
                AmoutReg = 0;
            }
            if (Batch.contains("///")) {
                BatchCycle = Batch.split("///");
                Batch = BatchCycle[0];
                Cycle = BatchCycle[1];
            }
            Client = Optional.ofNullable(pageContext.getRequest().getParameter("Client")).orElse("");

            if (Order > 0 && !Product.isEmpty() && !Batch.isEmpty()) {
                String cicloVerificacion = BatchCycle.length == 2 ? BatchCycle[1] : "";
                try {
                    parametrosVerificacion = RegistrosLabJpa.ConsultParametrosLoteVerificar(
                            Order, Product, Batch, cicloVerificacion);
                } catch (Exception ex) {
                    Logger.getLogger(Visual.class.getName()).log(Level.WARNING,
                            "No fue posible obtener los parámetros de verificación", ex);
                }
            }
            //</editor-fold>
            out.print("<section class='section'>");
            if (IdCertificates > 0) {
                //<editor-fold defaultstate="collapsed" desc="CONSULT DATA REGISTER">
                lst_content = CertificatesJpa.ConsultCertificatesIdHtml(IdCertificates);
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="CONSULT DATA NEW">
                lst_content = FormatJpa.ConsultFormatId(IdForm);
                //</editor-fold>
            }
            if (lst_content != null) {
                out.print("<input type='hidden' id='AmoutRegData' value='" + AmoutReg + "'>");
                if (parametrosVerificacion != null) {
                    out.print("<input type='hidden' id='IdProductoVerificar' value='"
                            + escapeHtmlAttribute(parametrosVerificacion[0]) + "'>");
                    out.print("<input type='hidden' id='LoteVerificar' value='"
                            + escapeHtmlAttribute(parametrosVerificacion[1]) + "'>");
                    out.print("<input type='hidden' id='IdLineaVerificar' value='"
                            + escapeHtmlAttribute(parametrosVerificacion[2]) + "'>");
                    out.print("<input type='hidden' id='CicloVerificar' value='"
                            + escapeHtmlAttribute(parametrosVerificacion[3]) + "'>");
                }
                Object[] Obj_Format = (Object[]) lst_content.get(0);
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<div class='d-flex justify-content-between' style='width:100%'>");
                out.print("<div class='mr-2 d-flex align-items-baseline'>");
                if (!Client.equals("")) {
                    out.print("<button class='btn btn-outline-primary btn-sm mr-2' "
                            + "style='border-radius: 4px; padding: 2px 9px;' "
                            + "onclick=\"javascript:location.href='Client?opt=1&Client=" + Client + "';cargarDatos()\">"
                            + "<i class='fas fa-arrow-left'></i>"
                            + "</button>");
                } else if (TempM == 0) {
                    out.print("<button class='btn btn-outline-primary btn-sm mr-2' "
                            + "style='border-radius: 4px; padding: 2px 9px;' "
                            + "onclick=\"javascript:location.href='Generate?opt=1&Type="
                            + Type + "&TempDelete=" + TempDelete + "';cargarDatos()\">"
                            + "<i class='fas fa-arrow-left'></i>"
                            + "</button>");
                } else {
                    out.print("<button class='btn btn-outline-primary btn-sm mr-2' "
                            + "style='border-radius: 4px; padding: 2px 9px;' "
                            + "onclick=\"javascript:location.href='Generate?opt=8';cargarDatos()\">"
                            + "<i class='fas fa-arrow-left'></i>"
                            + "</button>");
                }
                out.print("<h4>Generación Certificado</h4>");
                out.print("</div>");

                //<editor-fold defaultstate="collapsed" desc="BUTTOM'S">
                if (StateCerti == 1) {
                    if (Permission.contains("[28]")) {
                        out.print("<div class='d-flex'>");
                        List lstId = CertificatesJpa.ConsultCertificatesId(Type, IdCertificates);
                        if (lstId != null) {
                            Object[] ObjId = (Object[]) lstId.get(0);
                            out.print("<div>"
                                    + "<button class='btn btn-outline-warning btn-sm' "
                                    + "style='border-radius: 4px; padding: 2px 9px;' "
                                    + "onclick=\"UpdateCustomerCode();cargarDatos()\""
                                    + " data-toggle='tooltip' "
                                    + "data-placement='top' title='Código'>"
                                    + "<i class=\"fas fa-list-ol\"></i>"
                                    + "</button></div>");

                            out.print("<div class='ml-4'>"
                                    + "<button class='btn btn-outline-info btn-sm' "
                                    + "style='border-radius: 4px; padding: 2px 9px;' "
                                    + "onclick=\"UpdateCustomer();cargarDatos()\""
                                    + " data-toggle='tooltip' "
                                    + "data-placement='top' title='Clientes'>"
                                    + "<i class=\"fas fa-id-card\"></i>"
                                    + "</button></div>");

                            // Borrador con Factura/Remisión (u otros campos de cabecera)
                            // aún pendientes porque Factory no los tenía al generar: deja
                            // reconsultar Factory sin perder lo ya diligenciado a mano.
                            out.print("<div class='ml-4'>"
                                    + "<button class='btn btn-outline-primary btn-sm' "
                                    + "style='border-radius: 4px; padding: 2px 9px;' "
                                    + "onclick=\"actualizarDesdeFactory(" + IdCertificates + ")\""
                                    + " data-toggle='tooltip' "
                                    + "data-placement='top' title='Actualizar desde Factory'>"
                                    + "<i class=\"fas fa-industry\"></i>"
                                    + "</button></div>");

                            out.print("<div class='ml-4'>"
                                    + "<button class='btn btn-outline-success btn-sm' "
                                    + "style='border-radius: 4px; padding: 2px 9px;' "
                                    + "onclick=\"javascript:location.href='Generate?opt=7&Type=" + Type + "&IdCertificates=" + IdCertificates + "&Customer=" + ObjId[4] + "&Anio=" + ObjId[12] + "&Order=" + ObjId[5] + "&Batch=" + ObjId[7] + "';cargarDatos()\""
                                    + " data-toggle='tooltip' "
                                    + "data-placement='top' title='Finalizar'>"
                                    + "<i class='fas fa-check'></i>"
                                    + "</button></div>");
                        }
                        out.print("</div>");
                    }
                }
                // Estado 2 → Firmar
                if (StateCerti == 2) {

                    out.print("<div class='d-flex'>");
                    if (Permission.contains("[19]")) {
                        List lstId = CertificatesJpa.ConsultCertificatesId(Type, IdCertificates);
                        if (lstId != null) {
                            Object[] ObjId = (Object[]) lstId.get(0);
                            out.print("<div class='mr-4'>"
                                    + "<button class='btn btn-outline-danger btn-sm' "
                                    + "style='border-radius: 4px; padding: 2px 9px;' onclick=\"confirmarDevolucion('Generate?opt=5&Type=" + Type + "&IdCertificates=" + IdCertificates + "&Category=Return&State=1&NumberCertificate=" + ObjId[3] + "')\" data-toggle='tooltip' "
                                    + "data-placement='top' title='Devolver'>"
                                    + "<i class=\"fas fa-undo-alt\"></i>"
                                    + "</button></div>");
                        }
                    }
                    if (Permission.contains("[11]")) {
                        List lstId = CertificatesJpa.ConsultCertificatesId(Type, IdCertificates);
                        if (lstId != null) {
                            Object[] ObjId = (Object[]) lstId.get(0);
                            String firmarCertUrl = "Generate?opt=4&Type=" + Type + "&Temp=1&IdCertiMasive=" + IdCertificates
                                    + "&Customer=" + ObjId[4] + "&Anio=" + ObjId[12] + "&Order=" + ObjId[5] + "&Batch=" + ObjId[7];

                            // Si ya hay un soporte pendiente de firma (lo dejó el gestor en
                            // Finalizado), se ofrece firmarlo justo al firmar el certificado,
                            // en vez de que el firmante tenga que acordarse de volver después.
                            CertificateFileRow soportePendienteFirma = Permission.contains("[39]")
                                    ? CertificateFilesJpa.consultSupportFileByCertificate(IdCertificates) : null;
                            boolean hayPendiente = soportePendienteFirma != null
                                    && !soportePendienteFirma.getName().contains("_FIRMADO_");

                            String onclickFirmar;
                            if (hayPendiente) {
                                String returnToSoporte;
                                try {
                                    returnToSoporte = java.net.URLEncoder.encode(
                                            "Generate?opt=2&Type=" + Type + "&IdCertificates=" + IdCertificates
                                            + "&TempDelete=" + TempDelete + "&StateCerti=3&TempM=" + TempM, "UTF-8");
                                } catch (Exception e) {
                                    returnToSoporte = "";
                                }
                                String soporteUrl = "SupportDocumentSign.jsp?cliente=" + ObjId[4] + "&anio=" + ObjId[12]
                                        + "&orden=" + ObjId[5] + "&lote=" + ObjId[7] + "&id=" + soportePendienteFirma.getId()
                                        + "&returnTo=" + returnToSoporte;
                                onclickFirmar = "confirmarFirmaConSoporte('" + firmarCertUrl + "','" + soporteUrl + "')";
                            } else {
                                onclickFirmar = "javascript:location.href='" + firmarCertUrl + "';cargarDatos()";
                            }

                            out.print("<div class='mr-2'>"
                                    + "<button class='btn btn-outline-warning btn-sm' "
                                    + "style='border-radius: 4px; padding: 2px 9px;' "
                                    + "onclick=\"" + onclickFirmar + "\" data-toggle='tooltip' "
                                    + "data-placement='top' title='Firmar'>"
                                    + "<i class='fas fa-signature'></i>"
                                    + "</button></div>");
                        }
                    }
                    // El soporte se adjunta desde Finalizado, no desde Firmado: así el
                    // firmante, al entrar aquí a firmar el certificado, de paso ve y
                    // puede firmar el soporte que ya dejó el gestor.
                    if (IdCertificates > 0 && Permission.contains("[39]")) {
                        List lstIdSoporte = CertificatesJpa.ConsultCertificatesId(Type, IdCertificates);
                        if (lstIdSoporte != null) {
                            Object[] ObjIdSoporte = (Object[]) lstIdSoporte.get(0);
                            String returnToSoporte = "Generate?opt=2&Type=" + Type + "&IdCertificates=" + IdCertificates
                                    + "&TempDelete=" + TempDelete + "&StateCerti=2&TempM=" + TempM;
                            out.print("<div class='ml-2 d-inline'>");
                            SupportDocumentUi.renderCertificateAction(out, CertificateFilesJpa, IdCertificates,
                                    ObjIdSoporte[4] == null ? "" : ObjIdSoporte[4].toString(),
                                    ObjIdSoporte[12] == null ? "" : ObjIdSoporte[12].toString(),
                                    ObjIdSoporte[5] == null ? "" : ObjIdSoporte[5].toString(),
                                    ObjIdSoporte[7] == null ? "" : ObjIdSoporte[7].toString(),
                                    Type, true, returnToSoporte, Permission.contains("[4]"), true);
                            out.print("</div>");
                        }
                    }
                    out.print("</div>");

                }
                // Estado 3 → Imprimir / Documento de soporte
                if (StateCerti == 3) {
                    out.print("<div class='d-flex align-items-center'>");
                    if (Permission.contains("[26]")) {
                        out.print("<div class='mr-2'>"
                                + "<button class='btn btn-outline-primary btn-sm' "
                                + "style='border-radius: 4px; padding: 2px 9px;' "
                                + "onclick='descargarPDFCarta()' data-toggle='tooltip' "
                                + "data-placement='top' title='Imprimir'>"
                                + "<i class='fas fa-print'></i>"
                                + "</button></div>");
                    }
                    if (IdCertificates > 0 && Permission.contains("[39]")) {
                        List lstIdSoporte = CertificatesJpa.ConsultCertificatesId(Type, IdCertificates);
                        if (lstIdSoporte != null) {
                            Object[] ObjIdSoporte = (Object[]) lstIdSoporte.get(0);
                            String returnToSoporte = "Generate?opt=2&Type=" + Type + "&IdCertificates=" + IdCertificates
                                    + "&TempDelete=" + TempDelete + "&StateCerti=3&TempM=" + TempM;
                            SupportDocumentUi.renderCertificateAction(out, CertificateFilesJpa, IdCertificates,
                                    ObjIdSoporte[4] == null ? "" : ObjIdSoporte[4].toString(),
                                    ObjIdSoporte[12] == null ? "" : ObjIdSoporte[12].toString(),
                                    ObjIdSoporte[5] == null ? "" : ObjIdSoporte[5].toString(),
                                    ObjIdSoporte[7] == null ? "" : ObjIdSoporte[7].toString(),
                                    Type, true, returnToSoporte, Permission.contains("[4]"), false);
                        }
                    }
                    out.print("</div>");
                }
                if (IdCertificates == 0) {
                    out.print("<div class='d-flex'>");
                    out.print("<div>"
                            + "<button class='btn btn-outline-warning btn-sm' "
                            + "style='border-radius: 4px; padding: 2px 9px;' "
                            + "onclick=\"UpdateCustomerCode();cargarDatos()\""
                            + " data-toggle='tooltip' "
                            + "data-placement='top' title='Código'>"
                            + "<i class=\"fas fa-list-ol\"></i>"
                            + "</button></div>");
                    out.print("<div class='ml-4' >"
                            + "<button class='btn btn-outline-info btn-sm' "
                            + "style='border-radius: 4px; padding: 2px 9px;' "
                            + "onclick=\"UpdateCustomer();cargarDatos()\""
                            + " data-toggle='tooltip' "
                            + "data-placement='top' title='Clientes'>"
                            + "<i class=\"fas fa-id-card\"></i>"
                            + "</button></div>");
                    // Un location.reload() simple pierde Type/Product/Batch/Order/etc.
                    // cuando la página se cargó con esos datos solo en el cuerpo del
                    // formulario (POST) y no en la URL: el navegador vuelve a pedir la
                    // misma URL, pero sin los parámetros, y el certificado llega vacío.
                    // En su lugar se reconstruye la navegación con los mismos
                    // parámetros que trajo esta carga (igual que hace el botón "Atrás").
                    String BatchReload = Cycle.isEmpty() ? Batch : Batch + "///" + Cycle;
                    String reloadUrl = "Generate?opt=2"
                            + "&Type=" + urlEncode(Type)
                            + "&Order=" + Order
                            + "&Product=" + urlEncode(Product)
                            + "&Batch=" + urlEncode(BatchReload)
                            + "&FormatName=" + urlEncode(IdForm + "/" + Record)
                            + "&Client=" + urlEncode(Client)
                            + "&TempDelete=" + TempDelete
                            + "&StateCerti=" + StateCerti
                            + "&TempM=" + TempM
                            + "&AmoutReg=" + AmoutReg;
                    out.print("<div class='ml-4'>"
                            + "<button class='btn btn-outline-primary btn-sm' "
                            + "style='border-radius: 4px; padding: 2px 9px;' "
                            + "onclick=\"javascript:location.href='" + reloadUrl + "';cargarDatos()\" data-toggle='tooltip' "
                            + "data-placement='top' title='Recargar'>"
                            + "<i class='fas fa-sync-alt'></i>"
                            + "</button></div>");
                    out.print("</div>");

                }
                out.print("</div>"); // Cierra contenedor principal
                out.print("</div>");
                //</editor-fold>
                if (IdCertificates > 0) {
                    //<editor-fold defaultstate="collapsed" desc="VALIDATION BY ID">
                    Html = Obj_Format[3].toString();
                    State = Integer.parseInt(Obj_Format[4].toString());
                    if (Obj_Format[5] != null) {
                        IdSig = Obj_Format[5].toString();
                    }
                    // Diagnóstico temporal: confirmar si lo que vuelve de BD ya
                    // llega incompleto (vs. haberse guardado incompleto).
                    Logger.getLogger(Visual.class.getName()).log(Level.INFO,
                            "LOAD Html IdCertificates={0}: length={1}, tail=[{2}]",
                            new Object[]{
                                IdCertificates,
                                Html.length(),
                                Html.length() > 300 ? Html.substring(Html.length() - 300) : Html
                            });
                    // El Html guardado trae el badge "ErrorCount" congelado con el valor
                    // calculado al momento de generar/registrar el certificado. Los
                    // registros en Registros LAB pueden corregirse después (vía
                    // "Comprobar Errores"), así que aquí se recalcula contra los datos
                    // actuales y se actualiza el valor ya embebido en el Html, en vez de
                    // mostrar el conteo congelado de cuando se generó.
                    try {
                        List lstCertificadoRow = CertificatesJpa.ConsultCertificatesId(Type, IdCertificates);
                        if (lstCertificadoRow != null && !lstCertificadoRow.isEmpty()) {
                            Object[] ObjCertificadoRow = (Object[]) lstCertificadoRow.get(0);
                            String recordCertificado = ObjCertificadoRow[2] == null ? "" : ObjCertificadoRow[2].toString().trim();
                            int orderCertificado = 0;
                            try {
                                orderCertificado = Integer.parseInt(ObjCertificadoRow[5].toString().trim());
                            } catch (Exception ignore) {
                            }
                            String productCertificado = ObjCertificadoRow[6] == null ? "" : ObjCertificadoRow[6].toString().trim();
                            String batchCertificado = ObjCertificadoRow[7] == null ? "" : ObjCertificadoRow[7].toString().trim();
                            if (orderCertificado > 0 && !productCertificado.isEmpty() && !batchCertificado.isEmpty()) {
                                String freshErrorCount = null;
                                if ("R-GC-046".equals(recordCertificado)) {
                                    List lstMaterialesCertificado = RegistrosLabJpa.ConsultMaterials(orderCertificado, productCertificado, batchCertificado);
                                    if (lstMaterialesCertificado != null && !lstMaterialesCertificado.isEmpty()) {
                                        String[] argMaterialesCertificado = Util.parseResult(lstMaterialesCertificado.get(0));
                                        if (argMaterialesCertificado.length > 14) {
                                            freshErrorCount = argMaterialesCertificado[14];
                                        }
                                    }
                                } else if ("R-GC-220".equals(recordCertificado)) {
                                    List lstMaterialesCertificado = RegistrosLabJpa.ConsultMaterialsRGC220(orderCertificado, productCertificado, batchCertificado);
                                    if (lstMaterialesCertificado != null && !lstMaterialesCertificado.isEmpty()) {
                                        String[] argMaterialesCertificado = Util.parseResult(lstMaterialesCertificado.get(0));
                                        if (argMaterialesCertificado.length > 9) {
                                            freshErrorCount = argMaterialesCertificado[9];
                                        }
                                    }
                                } else if ("R-GC-194".equals(recordCertificado)) {
                                    List lstMaterialesCertificado = RegistrosLabJpa.ConsultMaterialsRGC194(orderCertificado, productCertificado, batchCertificado);
                                    if (lstMaterialesCertificado != null && !lstMaterialesCertificado.isEmpty()) {
                                        String[] argMaterialesCertificado = Util.parseResult(lstMaterialesCertificado.get(0));
                                        if (argMaterialesCertificado.length > 9) {
                                            freshErrorCount = argMaterialesCertificado[9];
                                        }
                                    }
                                }
                                if (freshErrorCount != null) {
                                    Html = Html.replaceAll("(id=['\"]ErrorCount['\"]\\s+value=['\"])[^'\"]*(['\"])",
                                            "$1" + freshErrorCount + "$2");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Visual.class.getName()).log(Level.WARNING,
                                "No fue posible refrescar el conteo de diferencias del certificado " + IdCertificates, ex);
                    }
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="VALIDATION NEW">
                    out.print("<script>");
                    out.print("$(document).ready(function() {\n"
                            + "    iziToast.success({\n"
                            + "        title: '¡Correcto!',\n"
                            + "        message: 'Se generó el certificado correctamente, favor verificar.',\n"
                            + "        position: 'bottomRight'\n"
                            + "    });\n"
                            + "});");
                    out.print("</script>");
                    Html = Obj_Format[4].toString();
                    State = 99;
                    //</editor-fold>
                }
                out.print("<div class='p-3'>");
                if (IdCertificates == 0) {
                    //<editor-fold defaultstate="collapsed" desc="GENERATION CERO">
                    // R-GC-074: el producto dimensional (Product/ProductFact) es el que
                    // usan los controles (materiales, dimensional), pero Factory/Tags
                    // identifican la orden por el producto terminado o complementario,
                    // nunca por el dimensional. Se resuelve aparte y solo se usa para
                    // las dos búsquedas de cabecera de más abajo; si no se puede
                    // resolver, se cae al ProductFact de siempre (comportamiento actual).
                    String ProductFactCabecera = ProductFact;
                    if (IdForm == 2) {
                        try {
                            String productoTerminado = RegistrosLabJpa.ConsultProductoFactoryRGC74(Order, Product, Batch);
                            if (productoTerminado != null && !productoTerminado.isEmpty()) {
                                ProductFactCabecera = productoTerminado.replace("2C", "3C");
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(Visual.class.getName()).log(Level.WARNING,
                                    "No fue posible resolver el producto terminado/complementario (R-GC-074) para Order=" + Order, ex);
                        }
                    }
                    lst_headFact = FactoryJpa.Products(Order, ProductFactCabecera, Batch);
                    if (lst_headFact != null && !lst_headFact.isEmpty() && lst_headFact.size() > 0) {
                        //<editor-fold defaultstate="collapsed" desc="HEAD FACTORY">
                        String[] ArgHead = Util.parseResult(lst_headFact.get(1));
                        Html = Html.replace("XOrderX", (ArgHead[0].equals("NULL") || ArgHead[0] == null) ? "" + Order + "" : ArgHead[0]);
                        Html = Html.replace("XClientX", (ArgHead[1].equals("NULL") || ArgHead[1] == null) ? "<span class='editable pending' data-field='Client' contenteditable='true'>----</span>" : "<span class='editable' data-field='Client' contenteditable='true'>" + ArgHead[1] + "</span>");
                        Html = Html.replace("XAddressX", (ArgHead[2].equals("NULL") || ArgHead[2] == null) ? "<span class='editable pending' data-field='Address' contenteditable='true'>----</span>" : "<span  class='editable' data-field='Address' contenteditable='true'>" + ArgHead[2] + "</span>");
                        Html = Html.replace("XPhoneX", (ArgHead[3].equals("NULL") || ArgHead[3] == null) ? "<span  class='editable' data-field='Phone' contenteditable='true'>----</span>" : "<span  class='editable' data-field='Phone' contenteditable='true'>" + ArgHead[3] + "</span>");
                        Html = Html.replace("XCityX", (ArgHead[4].equals("NULL") || ArgHead[4] == null) ? "<span class='editable pending' data-field='City' contenteditable='true'>----</span>" : "<span class='editable' data-field='City' contenteditable='true'>" + ArgHead[4] + "</span>");
                        Html = Html.replace("XCountryX", (ArgHead[5].equals("NULL") || ArgHead[5] == null) ? "<span class='editable pending' data-field='Country' contenteditable='true'>----</span>" : "<span class='editable' data-field='Country' contenteditable='true'>" + ArgHead[5] + "</span>");
                        Html = Html.replace("XBillX", (ArgHead[6].equals("NULL") || ArgHead[6] == null) ? "<span  class='editable pending' data-field='Bill' contenteditable='true'>----</span>" : "<span  class='editable' data-field='Bill' contenteditable='true'>" + ArgHead[6] + "</span>");
                        Html = Html.replace("XReissue_listX", (ArgHead[7].equals("NULL") || ArgHead[7] == null) ? "<span  class='editable pending' data-field='ReissueList' contenteditable='true'>----</span>" : "<span  class='editable ' data-field='ReissueList' contenteditable='true'>" + ArgHead[7] + "</span>");
                        Html = Html.replace("XProductX", (ArgHead[9].equals("NULL") || ArgHead[9] == null) ? "<span  class='editable pending' data-field='Product' contenteditable='true'>----</span>" : "<span  class='editable ' data-field='Product' contenteditable='true'>" + ArgHead[9] + "</span>");
                        Html = Html.replace("XAbilityX", (ArgHead[10].equals("NULL") || ArgHead[10] == null) ? "<span  class='editable pending' data-field='Ability' contenteditable='true'>----</span>" : "<span  class='editable' data-field='Ability' contenteditable='true'>" + ArgHead[10] + "</span>");
                        Html = Html.replace("XBatchX", Batch);
                        Html = Html.replace("XQuatityGenX", "<span  class='editable" + ((ArgHead[11].equals("NULL") || ArgHead[11] == null) ? " pending" : "") + "' data-field='Quantity' contenteditable='true'>" + ((ArgHead[11].equals("NULL") || ArgHead[11] == null) ? "----" : ArgHead[11]) + " UNIDADES </span>");
                        Html = Html.replace("XClient_OrderX", (ArgHead[12].equals("NULL") || ArgHead[12] == null) ? "<span class='editable pending' data-field='ClientOrder' contenteditable='true'>----</span>" : "<span class='editable' data-field='ClientOrder' contenteditable='true'>" + ArgHead[12] + "</span>");
                        Html = Html.replace("<h3 id=\"consValue\" class=\"mb-0\">XConsX</h3>", "<h3 id=\"consValue\" class=\"mb-0 editable\" contenteditable='true' pending>CC----</h3>");
                        Html = Html.replace("XCodeSpeedX", (ArgHead[13].equals("NULL") || ArgHead[13] == null) ? "<span  class='editable pending' data-field='CodeSpeed' contenteditable='true'>----</span>" : "<span  class='editable' data-field='CodeSpeed' contenteditable='true'>" + ArgHead[13] + "</span>");

                        Html = Html.replace("XSampleX", "<span  class='editable pending' contenteditable='true'>----</span>");
                        Html = Html.replace("XNumberPartX", "<span  class='editable pending' contenteditable='true'>----</span>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA - FALLBACK TAGS">
                        lst_tags = FactoryJpa.ConsultTags(Order, ProductFactCabecera, Batch);
                        if (lst_tags != null && !lst_tags.isEmpty() && lst_tags.size() > 0) {
                            //<editor-fold defaultstate="collapsed" desc="HEAD TAGS">
                            // Factory no tiene registro por orden/producto/lote: se completa la
                            // cabecera con lo que haya en Tags (columnas directas + AI_* del JSON
                            // de AdditionalInformation). XClientX y XClient_OrderX los resuelve más
                            // abajo el segundo bloque de Tags (fechas), no se tocan aquí.
                            String[] ArgTags = Util.parseResult(lst_tags.get(1));
                            Html = Html.replace("XOrderX", (ArgTags[0].equals("NULL")) ? String.valueOf(Order) : ArgTags[0]);
                            Html = Html.replace("XAddressX", (ArgTags[13].equals("NULL")) ? "<span class='editable pending' data-field='Address' contenteditable='true'>----</span>" : "<span  class='editable' data-field='Address' contenteditable='true'>" + ArgTags[13] + "</span>");
                            Html = Html.replace("XPhoneX", "<span  class='editable' data-field='Phone' contenteditable='true'>----</span>");
                            Html = Html.replace("XCityX", (ArgTags[14].equals("NULL")) ? "<span class='editable pending' data-field='City' contenteditable='true'>----</span>" : "<span class='editable' data-field='City' contenteditable='true'>" + ArgTags[14] + "</span>");
                            Html = Html.replace("XCountryX", (ArgTags[15].equals("NULL")) ? "<span class='editable pending' data-field='Country' contenteditable='true'>----</span>" : "<span class='editable' data-field='Country' contenteditable='true'>" + ArgTags[15] + "</span>");
                            Html = Html.replace("XBillX", "<span  class='editable pending' data-field='Bill' contenteditable='true'>----</span>");
                            Html = Html.replace("XReissue_listX", "<span  class='editable pending' data-field='ReissueList' contenteditable='true'>----</span>");
                            // Tags trae el volumen pegado al nombre ("...2000mL..."), a
                            // diferencia de Factory que ya lo separa en SQL; se extrae aquí
                            // para que CAPACIDAD/SIZE no quede pendiente cuando sí hay dato.
                            String nombreProductoTags = ArgTags[10].equals("NULL") ? null : ArgTags[10];
                            String capacidadTags = null;
                            String[] volumenSeparado = Util.separarVolumenDelNombre(nombreProductoTags);
                            if (volumenSeparado != null) {
                                nombreProductoTags = volumenSeparado[0];
                                capacidadTags = volumenSeparado[1];
                            }
                            Html = Html.replace("XProductX", (nombreProductoTags == null || nombreProductoTags.isEmpty()) ? "<span  class='editable' data-field='Product' contenteditable='true'>------</span>" : "<span  class='editable ' data-field='Product' contenteditable='true'>" + nombreProductoTags + "</span>");
                            Html = Html.replace("XAbilityX", (capacidadTags == null) ? "<span  class='editable pending' data-field='Ability' contenteditable='true'>----</span>" : "<span  class='editable' data-field='Ability' contenteditable='true'>" + capacidadTags + "</span>");
                            Html = Html.replace("XBatchX", "<span  class='editable' contenteditable='true'>" + Batch + "</span>");
                            String cantidadTags = "----";
                            if (!ArgTags[16].equals("NULL")) {
                                try {
                                    long cantidadUnidades = (long) Double.parseDouble(ArgTags[16]);
                                    cantidadTags = String.format("%,d", cantidadUnidades).replace(",", ".");
                                } catch (NumberFormatException e) {
                                    cantidadTags = ArgTags[16];
                                }
                            }
                            Html = Html.replace("XQuatityGenX", "<span  class='editable' data-field='Quantity' contenteditable='true'>" + cantidadTags + " UNIDADES</span>");
                            Html = Html.replace("<h3 id=\"consValue\" class=\"mb-0\">XConsX</h3>", "<h3 id=\"consValue\" class=\"mb-0 editable\" contenteditable='true'>CC----</h3>");
                            Html = Html.replace("XSampleX", "<span  class='editable pending' contenteditable='true'>----</span>");
                            Html = Html.replace("XCodeSpeedX", "<span  class='editable pending' contenteditable='true'>N/A</span>");
                            Message = "No se encuentra información en Factory por orden, producto y lote; se completó la cabecera con datos de Tags, favor verifique";
                            // ✅ Ejecutar automáticamente cuando todo esté cargado
                            out.print("<script>"
                                    + "window.addEventListener('load',function(){"
                                    + " registrarEvento('Factory','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                    + "});"
                                    + "</script>");
                            //</editor-fold>
                        } else {
                            Html = Html.replace("XOrderX", String.valueOf(Order));
                            Html = Html.replace("XAddressX", "<span  class='editable pending' data-field='Address' contenteditable='true'>----</span>");
                            Html = Html.replace("XPhoneX", "<span  class='editable' data-field='Phone' contenteditable='true'>----</span>");
                            Html = Html.replace("XCityX", "<span  class='editable pending' data-field='City' contenteditable='true'>----</span>");
                            Html = Html.replace("XCountryX", "<span  class='editable pending' data-field='Country' contenteditable='true'>----</span>");
                            Html = Html.replace("XBillX", "<span  class='editable pending' data-field='Bill' contenteditable='true'>----</span>");
                            Html = Html.replace("XReissue_listX", "<span  class='editable pending' data-field='ReissueList' contenteditable='true'>----</span>");
                            Html = Html.replace("XProductX", "<span  class='editable' data-field='Product' contenteditable='true'>------</span>");
                            Html = Html.replace("XAbilityX", "<span  class='editable pending' data-field='Ability' contenteditable='true'>----</span>");
                            Html = Html.replace("XBatchX", "<span  class='editable' contenteditable='true'>" + Batch + "</span>");
                            Html = Html.replace("XQuatityGenX", "<span  class='editable pending' data-field='Quantity' contenteditable='true'>---- UNIDADES</span>");
                            Html = Html.replace("<h3 id=\"consValue\" class=\"mb-0\">XConsX</h3>", "<h3 id=\"consValue\" class=\"mb-0 editable\" contenteditable='true'>CC----</h3>");
                            Html = Html.replace("XSampleX", "<span  class='editable pending' contenteditable='true'>----</span>");
                            Html = Html.replace("XCodeSpeedX", "<span  class='editable pending' contenteditable='true'>----</span>");

                            Message = "No se encuentra información en Factory ni en Tags por orden, producto y lote, favor verifique";
                            // ✅ Ejecutar automáticamente cuando todo esté cargado
                            out.print("<script>"
                                    + "window.addEventListener('load',function(){"
                                    + " registrarEvento('Factory','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                    + "});"
                                    + "</script>");
                        }
                        //</editor-fold>
                    }
                    lst_tags = FactoryJpa.ConsultTags(Order, ProductFactCabecera, Batch);
                    if (lst_tags != null && !lst_tags.isEmpty() && lst_tags.size() > 0) {
                        //<editor-fold defaultstate="collapsed" desc="DATE MANUFACTURE AND EXPIRATION">
                        String[] ArgTags = Util.parseResult(lst_tags.get(1));
                        if (ArgTags[5].contains("-")) {
                            String[] DateExpiración = ArgTags[5].split("-");
                            Html = Html.replace("XYearMANX", "<span  class='editable' contenteditable='true'>" + DateExpiración[0] + "</span>");
                            Html = Html.replace("XMonthMANX", "<span  class='editable' contenteditable='true'>" + DateExpiración[1] + "</span>");
                            Html = Html.replace("XDayMANX", "<span  class='editable' contenteditable='true'>" + DateExpiración[2] + "</span>");
                        } else {
                            Html = Html.replace("XYearMANX", "<span  class='editable pending' contenteditable='true'>----</span>");
                            Html = Html.replace("XMonthMANX", "<span  class='editable pending' contenteditable='true'>----</span>");
                            Html = Html.replace("XDayMANX", "<span  class='editable pending' contenteditable='true'>----</span>");
                        }
                        if (ArgTags[6].contains("-")) {
                            String[] DateExpiración = ArgTags[6].split("-");
                            Html = Html.replace("XYearEXPX", "<span  class='editable' contenteditable='true'>" + DateExpiración[0] + "</span>");
                            Html = Html.replace("XMonthEXPX", "<span  class='editable' contenteditable='true'>" + DateExpiración[1] + "</span>");
                            Html = Html.replace("XDayEXPX", "<span  class='editable' contenteditable='true'>" + DateExpiración[2] + "</span>");
                        } else {
                            Html = Html.replace("XYearEXPX", "<span  class='editable pending' contenteditable='true'>----</span>");
                            Html = Html.replace("XMonthEXPX", "<span  class='editable pending' contenteditable='true'>----</span>");
                            Html = Html.replace("XDayEXPX", "<span  class='editable pending' contenteditable='true'>----</span>");
                        }
                        Html = Html.replace("XClientX", "<span  class='editable' data-field='Client' contenteditable='true'>" + ArgTags[8] + "</span>");
                        Html = Html.replace("XCodeX", "<span  class='editable' contenteditable='true'>" + ArgTags[7] + "</span>");
                        Html = Html.replace("XClient_OrderX", "<span  class='editable' data-field='ClientOrder' contenteditable='true'>" + ArgTags[9] + "</span>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                        Html = Html.replace("XYearMANX", "<span  class='editable pending' contenteditable='true'>----</span>");
                        Html = Html.replace("XMonthMANX", "<span  class='editable pending' contenteditable='true'>----</span>");
                        Html = Html.replace("XDayMANX", "<span  class='editable pending' contenteditable='true'>----</span>");
                        Html = Html.replace("XYearEXPX", "<span  class='editable pending' contenteditable='true'>----</span>");
                        Html = Html.replace("XMonthEXPX", "<span  class='editable pending' contenteditable='true'>----</span>");
                        Html = Html.replace("XDayEXPX", "<span  class='editable pending' contenteditable='true'>----</span>");

                        Html = Html.replace("XClientX", "<span  class='editable pending' data-field='Client' contenteditable='true'>----</span>");
                        Html = Html.replace("XCodeX", "<span  class='editable pending' contenteditable='true'>----</span>");
                        Html = Html.replace("XClient_OrderX", "<span  class='editable pending' data-field='ClientOrder' contenteditable='true'>----</span>");

                        Message = "No se encuentra información en Tags por orden, producto y lote, favor verifique";

                        // ✅ Ejecutar automáticamente cuando todo esté cargado
                        out.print("<script>"
                                + "window.addEventListener('load',function(){"
                                + " registrarEvento('Tags','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                + "});"
                                + "</script>");
                        //</editor-fold>
                    }
                    switch (IdForm) {
                        case 1:
                            //<editor-fold defaultstate="collapsed" desc="R-GC-046">
                            lst_RLab = RegistrosLabJpa.ConsultMaterials(Order, Product, Batch);
                            if (lst_RLab != null && !lst_RLab.isEmpty() && lst_RLab.size() > 0) {
                                //<editor-fold defaultstate="collapsed" desc="MATERIAL REGISTROS LAB AND GENERACION DE LOTES">
                                String[] ArgMaterials = Util.parseResult(lst_RLab.get(0));
                                out.print("<input type='hidden' id='ErrorCount' value='" + ArgMaterials[14] + "'>");
                                String[] etiquetas = {
                                    "REF1", "LBT1", "REF2", "LBT2", "REF3", "LBT3",
                                    "REF4", "LBT4", "REF5", "LBT5", "REF6", "LBT6",
                                    "LBT7", "LBT8"
                                };

                                for (int i = 0; i < etiquetas.length; i++) {
                                    String valor = ArgMaterials[i].trim();
                                    String etiqueta = etiquetas[i];

                                    if (valor.equals("N/A")) {
                                        Html = Html.replace(etiqueta, "<span  class='editable pending' contenteditable='true'>----</span>");
                                    } else {
                                        // Un lote compuesto ("A/B", dos recepciones distintas para el
                                        // mismo material) debe verse en líneas separadas, no pegado con
                                        // "/" en una sola línea.
                                        if (etiqueta.startsWith("LBT") && valor.contains("/")) {
                                            valor = valor.replace("/", "<br>");
                                        }
                                        Html = Html.replace(etiqueta, valor);
                                    }
                                }

                                BatchLay = ArgMaterials[1];
                                String[] ArgImP = {ArgMaterials[1], ArgMaterials[3], ArgMaterials[5], ArgMaterials[7], ArgMaterials[9], ArgMaterials[11]};
                                String[] ArgProc = {"MANGA PVC", "DUCTO PVC", "DUCTO PVC", "DUCTO PVC", "CONECTOR", "CONECTOR"};
                                int Cnt = 1;
                                for (int i = 0; i < ArgImP.length; i++) {
                                    lst_Glotes1 = GeneracionLotesJpa.ConsultarCC_GeneracionLote(ArgImP[i], ArgProc[i]);
                                    if (lst_Glotes1 != null && !lst_Glotes1.isEmpty() && lst_Glotes1.size() > 0) {
                                        String[] ArgLay = lst_Glotes1.get(0).toString().replace("]", "").replace("[", "").split("///");
                                        Html = Html.replace("COS" + Cnt + "", "CC" + ArgLay[0]);
                                    } else {
                                        // Salvedad: si no se encontró por lote + familia de proceso
                                        // (el proceso puede tener un nombre que variantesProceso()
                                        // todavía no conoce, o el material de Registros LAB está
                                        // nombrado distinto al de Generación de Lotes), se busca de
                                        // nuevo solo por lote antes de rendirse. Si hay un único
                                        // consecutivo se usa directo; si hay varios, se deja elegir.
                                        List<String> lst_GlotesSinProceso = GeneracionLotesJpa.ConsultarCC_GeneracionLoteDetalle(ArgImP[i]);
                                        Html = Util.resolverConsecutivoAmbiguo(Html, Cnt, lst_GlotesSinProceso);

                                        if (lst_GlotesSinProceso == null || lst_GlotesSinProceso.isEmpty()) {
                                            Message = "No se encuentra información en Generacion de lotes en el módulo de Control consecutivos por el lote, favor verifique";

                                            // ✅ Ejecutar automáticamente cuando todo esté cargado
                                            out.print("<script>"
                                                    + "window.addEventListener('load',function(){"
                                                    + " registrarEvento('Generacion de lotes / Control Consecutivos','" + Order + "','" + ProductFact + "','" + ArgImP[i] + "','" + Message.replace("'", "\\'") + "');"
                                                    + "});"
                                                    + "</script>");
                                        }
                                    }
                                    Cnt++;
                                }

                                String[] ArgTnt = {ArgMaterials[12], ArgMaterials[13]};
                                int Cnt2 = 7;
                                for (int o = 0; o < ArgTnt.length; o++) {
                                    if (ArgTnt[o].equals("N/A")) {
                                        //<editor-fold defaultstate="collapsed" desc="VALIDATION">
                                        Html = Html.replace("REF" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                        Html = Html.replace("COS" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                        Message = "No se encuentra información en Generacion de lotes en el módulo de repcepción material por el lote, favor verifique";

                                        // ✅ Ejecutar automáticamente cuando todo esté cargado
                                        out.print("<script>"
                                                + "window.addEventListener('load',function(){"
                                                + " registrarEvento('Generacion de lotes / Recepción Material','" + Order + "','" + ProductFact + "','" + ArgTnt[o] + "','" + Message.replace("'", "\\'") + "');"
                                                + "});"
                                                + "</script>");
                                        //</editor-fold>    
                                    } else {
                                        lst_GlotesRep = GeneracionLotesJpa.ConsultarCC_RepcecionMaterial(ArgTnt[o]);
                                        if (lst_GlotesRep != null && !lst_GlotesRep.isEmpty() && lst_GlotesRep.size() > 0) {
                                            //<editor-fold defaultstate="collapsed" desc="CC RECECTION">
                                            Html = Util.replaceLoteReception(Html, lst_GlotesRep, Cnt2, false);
                                            //</editor-fold>
                                        } else {
                                            //<editor-fold defaultstate="collapsed" desc="VALIDATION">
                                            Html = Html.replace("REF" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                            Html = Html.replace("COS" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");

                                            Message = "No se encuentra información en Generacion de lotes en el módulo de repcepción material por el lote, favor verifique";

                                            // ✅ Ejecutar automáticamente cuando todo esté cargado
                                            out.print("<script>"
                                                    + "window.addEventListener('load',function(){"
                                                    + " registrarEvento('Generacion de lotes / Recepción Material','" + Order + "','" + ProductFact + "','" + ArgTnt[o] + "','" + Message.replace("'", "\\'") + "');"
                                                    + "});"
                                                    + "</script>");
                                            //</editor-fold>
                                        }
                                    }
                                    Cnt2++;
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                for (int i = 1; i < 10; i++) {
                                    Html = Html.replace("REF" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                    Html = Html.replace("LBT" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                    Html = Html.replace("COS" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                }
                                Message = "No se encuentra información materiales o ductos en Registros, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB -  Materiales','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            lst_sheet = SettingJpa.ConsultSettingCategorie(Record + "-Ficha");
                            if (lst_sheet != null) {
                                //<editor-fold defaultstate="collapsed" desc="TECNHNICAL SHEET">
                                Object[] ObjSheet = (Object[]) lst_sheet.get(0);
                                if (ObjSheet[2] != null && !ObjSheet[2].equals("")) {
                                    lst_prm = RegistrosLabJpa.QueryTechnicalSheet(Order, Product, Batch, ObjSheet[2].toString());
                                    if (lst_prm != null && lst_prm.size() > 0) {
                                        String[] ArgPrm = Util.parseResult(lst_prm.get(0));
                                        // Prioriza Soldadura bocas/colas "alt" sobre el valor normal cuando trae dato
                                        if (ArgPrm.length > 17 && !ArgPrm[17].isEmpty() && !ArgPrm[17].contains("0 +/- 0")) {
                                            ArgPrm[14] = ArgPrm[17];
                                        }
                                        if (ArgPrm.length > 18 && !ArgPrm[18].isEmpty() && !ArgPrm[18].contains("0 +/- 0")) {
                                            ArgPrm[15] = ArgPrm[18];
                                        }
                                        DataTechnicalSheet = ArgPrm[16];
                                        int ForCant = Integer.parseInt(ArgPrm[0].trim());
                                        for (int i = 1; i < ForCant; i++) {
                                            if (ArgPrm[i].contains("0 +/- 0")) {
                                                Html = Html.replaceFirst("PRM" + Count2 + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("PRM" + Count2 + "", ArgPrm[i]);
                                            }
                                            Count2++;
                                        }
                                    }
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                Message = "No se encuentra información de la ficha tecnica en Registros LAB, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB - Ficha Tecnica','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            lst_parameter = SettingJpa.ConsultSettingCategorie(Record);
                            if (lst_parameter != null) {
                                //<editor-fold defaultstate="collapsed" desc="DATA PARAMETER">
                                Object[] ObjParameter = (Object[]) lst_parameter.get(0);
                                if (ObjParameter[2] != null) {
                                    lst_data = RegistrosLabJpa.DimensionalQuery(Order, Product, Batch, ObjParameter[2].toString(), ObjParameter[3].toString());
                                    if (lst_data != null) {
                                        for (int i = 0; i < lst_data.size(); i++) {
                                            String[] ArgData = Util.parseResult(lst_data.get(i));
                                            if (ArgData[1].trim().equals("0")) {
                                                Html = Html.replaceFirst("MIN" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MIN" + Count + "", ArgData[1]);
                                            }
                                            if (ArgData[2].trim().equals("0")) {
                                                Html = Html.replaceFirst("MAX" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MAX" + Count + "", ArgData[2]);
                                            }
                                            if (ArgData[3].trim().equals("0.00")) {
                                                Html = Html.replaceFirst("MEAN" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MEAN" + Count + "", ArgData[3]);
                                            }
                                            Count++;
                                        }
                                    }
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                Message = "No se encuentra información de los controles, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB - Controles','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            //</editor-fold>
                            break;
                        case 2:
                            //<editor-fold defaultstate="collapsed" desc="R-GC-074">
                            lst_RLab = RegistrosLabJpa.ConsultMaterialsRGC74(Order, Product, Batch);
                            if (lst_RLab != null && !lst_RLab.isEmpty() && lst_RLab.size() > 0) {
                                //<editor-fold defaultstate="collapsed" desc="MATERIAL REGISTROS LAB">
                                String[] ArgMaterials = Util.parseResult(lst_RLab.get(0));
                                String[] etiquetas = {
                                    "REF1", "LBT1", "REF2", "LBT2", "REF3", "LBT3",
                                    "REF4", "LBT4", "REF5", "LBT5", "REF6", "LBT6",
                                    "REF7", "LBT7", "REF8", "LBT8", "LBT9"
                                };

                                for (int i = 0; i < etiquetas.length; i++) {
                                    String valor = ArgMaterials[i].trim();
                                    String etiqueta = etiquetas[i];

                                    if (valor.equals("N/A")) {
                                        Html = Html.replace(etiqueta, "<span  class='editable pending' contenteditable='true'>----</span>");
                                    } else {
                                        // Un lote compuesto ("A/B", dos recepciones distintas para el
                                        // mismo material) debe verse en líneas separadas, no pegado con
                                        // "/" en una sola línea.
                                        if (etiqueta.startsWith("LBT") && valor.contains("/")) {
                                            valor = valor.replace("/", "<br>");
                                        }
                                        Html = Html.replace(etiqueta, valor);
                                    }
                                }

                                BatchLay = ArgMaterials[1];
                                String[] ArgImP = {ArgMaterials[1], ArgMaterials[3], ArgMaterials[5], ArgMaterials[7], ArgMaterials[9], ArgMaterials[11], ArgMaterials[13], ArgMaterials[15]};
                                int Cnt = 1;
                                for (int i = 0; i < ArgImP.length; i++) {
                                    lst_Glotes1 = GeneracionLotesJpa.ConsultarCC_GeneracionLote(ArgImP[i]);
                                    if (lst_Glotes1 != null && !lst_Glotes1.isEmpty() && lst_Glotes1.size() > 0) {
                                        String[] ArgLay = lst_Glotes1.get(0).toString().replace("]", "").replace("[", "").split("///");
                                        Html = Html.replace("COS" + Cnt + "", "CC" + ArgLay[0]);
                                    } else {
                                        Html = Html.replace("COS" + Cnt, "<span  class='editable pending' contenteditable='true'>----</span>");
                                        Message = "No se encuentra información en Generacion de lotes en el módulo de Control consecutivos por el lote, favor verifique";

                                        // ✅ Ejecutar automáticamente cuando todo esté cargado
                                        out.print("<script>"
                                                + "window.addEventListener('load',function(){"
                                                + " registrarEvento('Generacion de lotes / Control Consecutivos','" + Order + "','" + ProductFact + "','" + ArgImP[i] + "','" + Message.replace("'", "\\'") + "');"
                                                + "});"
                                                + "</script>");
                                    }
                                    Cnt++;
                                }

                                String[] ArgTnt = {ArgMaterials[16]};
                                int Cnt2 = 9;
                                for (int o = 0; o < ArgTnt.length; o++) {
                                    if (ArgTnt[o].equals("N/A")) {
                                        //<editor-fold defaultstate="collapsed" desc="VALIDATION">
                                        Html = Html.replace("REF" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                        Html = Html.replace("COS" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                        Message = "No se encuentra información en Generacion de lotes en el módulo de repcepción material por el lote, favor verifique";

                                        // ✅ Ejecutar automáticamente cuando todo esté cargado
                                        out.print("<script>"
                                                + "window.addEventListener('load',function(){"
                                                + " registrarEvento('Generacion de lotes / Recepción Material','" + Order + "','" + ProductFact + "','" + ArgTnt[o] + "','" + Message.replace("'", "\\'") + "');"
                                                + "});"
                                                + "</script>");
                                        //</editor-fold>
                                    } else {
                                        lst_GlotesRep = GeneracionLotesJpa.ConsultarCC_RepcecionMaterial(ArgTnt[o]);
                                        if (lst_GlotesRep != null && !lst_GlotesRep.isEmpty() && lst_GlotesRep.size() > 0) {
                                            //<editor-fold defaultstate="collapsed" desc="CC RECECTION">
                                            Html = Util.replaceLoteReception(Html, lst_GlotesRep, Cnt2, true);
                                            //</editor-fold>
                                        } else {
                                            //<editor-fold defaultstate="collapsed" desc="VALIDATION">
                                            Html = Html.replace("REF" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                            Html = Html.replace("COS" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                            Message = "No se encuentra información en Generacion de lotes en el módulo de repcepción material por el lote, favor verifique";

                                            // ✅ Ejecutar automáticamente cuando todo esté cargado
                                            out.print("<script>"
                                                    + "window.addEventListener('load',function(){"
                                                    + " registrarEvento('Generacion de lotes / Recepción Material','" + Order + "','" + ProductFact + "','" + ArgTnt[o] + "','" + Message.replace("'", "\\'") + "');"
                                                    + "});"
                                                    + "</script>");
                                            //</editor-fold>
                                        }
                                    }
                                    Cnt2++;
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                for (int i = 1; i < 10; i++) {
                                    Html = Html.replace("REF" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                    Html = Html.replace("LBT" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                    Html = Html.replace("COS" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                }
                                Message = "No se encuentra información materiales o ductos en Registros, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB -  Materiales','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            lst_sheet = SettingJpa.ConsultSettingCategorie(Record + "-Ficha");
                            if (lst_sheet != null) {
                                //<editor-fold defaultstate="collapsed" desc="TECNHNICAL SHEET">
                                Object[] ObjSheet = (Object[]) lst_sheet.get(0);
                                if (!ObjSheet[2].equals("") || ObjSheet[2] != null) {
                                    lst_prm = RegistrosLabJpa.QueryTechnicalSheetRGC74(Order, Product, Batch, ObjSheet[2].toString());
                                    if (lst_prm != null) {
                                        String[] ArgPrm = Util.parseResult(lst_prm.get(0));
                                        DataTechnicalSheet = ArgPrm[11];
                                        int ForCant = Integer.parseInt(ArgPrm[0].trim());
                                        for (int i = 1; i < ForCant; i++) {
                                            if (ArgPrm[i].contains("0 +/- 0")) {
                                                Html = Html.replaceFirst("PRM" + Count2 + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("PRM" + Count2 + "", ArgPrm[i]);
                                            }
                                            Count2++;
                                        }
                                    }
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                Message = "No se encuentra información de la ficha tecnica en Registros LAB, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB - Ficha Tecnica','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            lst_parameter = SettingJpa.ConsultSettingCategorie(Record);
                            if (lst_parameter != null) {
                                //<editor-fold defaultstate="collapsed" desc="DATA PARAMETER">
                                Object[] ObjParameter = (Object[]) lst_parameter.get(0);
                                if (ObjParameter[2] != null) {
                                    lst_data = RegistrosLabJpa.DimensionalQueryRGC74(Order, Product, Batch, ObjParameter[2].toString(), ObjParameter[3].toString());
                                    if (lst_data != null) {
                                        for (int i = 0; i < lst_data.size(); i++) {
                                            String[] ArgData = Util.parseResult(lst_data.get(i));
                                            if (ArgData[1].trim().equals("0")) {
                                                Html = Html.replaceFirst("MIN" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MIN" + Count + "", ArgData[1]);
                                            }
                                            if (ArgData[2].trim().equals("0")) {
                                                Html = Html.replaceFirst("MAX" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MAX" + Count + "", ArgData[2]);
                                            }
                                            if (ArgData[3].trim().equals("0.00")) {
                                                Html = Html.replaceFirst("MEAN" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MEAN" + Count + "", ArgData[3]);
                                            }
                                            Count++;
                                        }
                                    }
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                Message = "No se encuentra información de los controles, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB - Controles','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            Html = Html.replace("XxRINVIMAxX", "<select><option>2016DM-0014249</option><option>2025DM-0014249-R1</option></select>");
                            if (!Cycle.equals("")) {
                                Html = Html.replace("XxCyclexX", Cycle);
                            }
                            //</editor-fold>
                            break;
                        case 3:
                            //<editor-fold defaultstate="collapsed" desc="R-GC-194">
                            lst_RLab = RegistrosLabJpa.ConsultMaterialsRGC194(Order, Product, Batch);
                            if (lst_RLab != null && !lst_RLab.isEmpty() && lst_RLab.size() > 0) {
                                //<editor-fold defaultstate="collapsed" desc="MATERIAL REGISTROS LAB AND GENERACION DE LOTES">
                                String[] ArgMaterials = Util.parseResult(lst_RLab.get(0));
                                out.print("<input type='hidden' id='ErrorCount' value='" + ArgMaterials[9] + "'>");
                                String[] etiquetas = {
                                    "REF1", "LBT1", "REF2", "LBT2", "REF3", "LBT3", "REF4", "LBT4", "LBT5"
                                };

                                for (int i = 0; i < etiquetas.length; i++) {
                                    String valor = ArgMaterials[i].trim();
                                    String etiqueta = etiquetas[i];

                                    if (valor.equals("N/A")) {
                                        Html = Html.replace(etiqueta, "<span  class='editable pending' contenteditable='true'>----</span>");
                                    } else {
                                        // Un lote compuesto ("A/B", dos recepciones distintas para el
                                        // mismo material) debe verse en líneas separadas, no pegado con
                                        // "/" en una sola línea.
                                        if (etiqueta.startsWith("LBT") && valor.contains("/")) {
                                            valor = valor.replace("/", "<br>");
                                        }
                                        Html = Html.replace(etiqueta, valor);
                                    }
                                }

                                BatchLay = ArgMaterials[1];
                                String[] ArgImP = {ArgMaterials[1], ArgMaterials[3], ArgMaterials[5], ArgMaterials[7]};
                                String[] ArgProc = {"MANGA PVC", "DUCTO PVC", "DUCTO PVC", "CONECTOR"};
                                int Cnt = 1;
                                for (int i = 0; i < ArgImP.length; i++) {
                                    lst_Glotes1 = GeneracionLotesJpa.ConsultarCC_GeneracionLote(ArgImP[i], ArgProc[i]);
                                    if (lst_Glotes1 != null && !lst_Glotes1.isEmpty() && lst_Glotes1.size() > 0) {
                                        String[] ArgLay = lst_Glotes1.get(0).toString().replace("]", "").replace("[", "").split("///");
                                        Html = Html.replace("COS" + Cnt + "", "CC" + ArgLay[0]);
                                    } else {
                                        // Salvedad: si no se encontró por lote + familia de proceso
                                        // (el proceso puede tener un nombre que variantesProceso()
                                        // todavía no conoce, o el material de Registros LAB está
                                        // nombrado distinto al de Generación de Lotes), se busca de
                                        // nuevo solo por lote antes de rendirse. Si hay un único
                                        // consecutivo se usa directo; si hay varios, se deja elegir.
                                        List<String> lst_GlotesSinProceso = GeneracionLotesJpa.ConsultarCC_GeneracionLoteDetalle(ArgImP[i]);
                                        Html = Util.resolverConsecutivoAmbiguo(Html, Cnt, lst_GlotesSinProceso);

                                        if (lst_GlotesSinProceso == null || lst_GlotesSinProceso.isEmpty()) {
                                            Message = "No se encuentra información en Generacion de lotes en el módulo de Control consecutivos por el lote, favor verifique";

                                            // ✅ Ejecutar automáticamente cuando todo esté cargado
                                            out.print("<script>"
                                                    + "window.addEventListener('load',function(){"
                                                    + " registrarEvento('Generacion de lotes / Control Consecutivos','" + Order + "','" + ProductFact + "','" + ArgImP[i] + "','" + Message.replace("'", "\\'") + "');"
                                                    + "});"
                                                    + "</script>");
                                        }
                                    }
                                    Cnt++;
                                }

                                String[] ArgTnt = {ArgMaterials[8]};
                                int Cnt2 = 5;
                                for (int o = 0; o < ArgTnt.length; o++) {
                                    lst_GlotesRep = GeneracionLotesJpa.ConsultarCC_RepcecionMaterial(ArgTnt[o]);
                                    if (ArgTnt[o].equals("N/A")) {
                                        //<editor-fold defaultstate="collapsed" desc="VALIDATION">
                                        Html = Html.replace("REF" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                        Html = Html.replace("COS" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");

                                        Message = "No se encuentra información en Generacion de lotes en el módulo de repcepción material por el lote, favor verifique";

                                        // ✅ Ejecutar automáticamente cuando todo esté cargado
                                        out.print("<script>"
                                                + "window.addEventListener('load',function(){"
                                                + " registrarEvento('Generacion de lotes / Recepción Material','" + Order + "','" + ProductFact + "','" + ArgTnt[o] + "','" + Message.replace("'", "\\'") + "');"
                                                + "});"
                                                + "</script>");
                                        //</editor-fold>
                                    } else {
                                        if (lst_GlotesRep != null && !lst_GlotesRep.isEmpty() && lst_GlotesRep.size() > 0) {
                                            //<editor-fold defaultstate="collapsed" desc="CC RECECTION">
                                            Html = Util.replaceLoteReception(Html, lst_GlotesRep, Cnt2, false);
                                            //</editor-fold>
                                        } else {
                                            //<editor-fold defaultstate="collapsed" desc="VALIDATION">
                                            Html = Html.replace("REF" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                            Html = Html.replace("COS" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");

                                            Message = "No se encuentra información en Generacion de lotes en el módulo de repcepción material por el lote, favor verifique";

                                            // ✅ Ejecutar automáticamente cuando todo esté cargado
                                            out.print("<script>"
                                                    + "window.addEventListener('load',function(){"
                                                    + " registrarEvento('Generacion de lotes / Recepción Material','" + Order + "','" + ProductFact + "','" + ArgTnt[o] + "','" + Message.replace("'", "\\'") + "');"
                                                    + "});"
                                                    + "</script>");
                                            //</editor-fold>
                                        }
                                    }
                                    Cnt2++;
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                for (int i = 1; i < 10; i++) {
                                    Html = Html.replace("REF" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                    Html = Html.replace("LBT" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                    Html = Html.replace("COS" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                }
                                Message = "No se encuentra información materiales o ductos en Registros, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB -  Materiales','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            lst_sheet = SettingJpa.ConsultSettingCategorie(Record + "-Ficha");
                            if (lst_sheet != null) {
                                //<editor-fold defaultstate="collapsed" desc="TECNHNICAL SHEET">
                                Object[] ObjSheet = (Object[]) lst_sheet.get(0);
                                if (ObjSheet[2] != null && !ObjSheet[2].equals("")) {
                                    lst_prm = RegistrosLabJpa.QueryTechnicalSheetRGC194(Order, Product, Batch, ObjSheet[2].toString());
                                    if (lst_prm != null) {
                                        String[] ArgPrm = Util.parseResult(lst_prm.get(0));
                                        DataTechnicalSheet = ArgPrm[13];
                                        int ForCant = Integer.parseInt(ArgPrm[0].trim());
                                        for (int i = 1; i < ForCant; i++) {
                                            if (ArgPrm[i].contains("0 +/- 0")) {
                                                Html = Html.replaceFirst("PRM" + Count2 + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("PRM" + Count2 + "", ArgPrm[i]);
                                            }
                                            Count2++;
                                        }
                                    }
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                Message = "No se encuentra información de la ficha tecnica en Registros LAB, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB - Ficha Tecnica','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            lst_parameter = SettingJpa.ConsultSettingCategorie(Record);
                            if (lst_parameter != null) {
                                //<editor-fold defaultstate="collapsed" desc="DATA PARAMETER">
                                Object[] ObjParameter = (Object[]) lst_parameter.get(0);
                                if (ObjParameter[2] != null) {
                                    lst_data = RegistrosLabJpa.DimensionalQueryRGC194(Order, Product, Batch, ObjParameter[2].toString(), ObjParameter[3].toString());
                                    if (lst_data != null) {
                                        for (int i = 0; i < lst_data.size(); i++) {
                                            String[] ArgData = Util.parseResult(lst_data.get(i));
                                            if (ArgData[1].trim().equals("0")) {
                                                Html = Html.replaceFirst("MIN" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MIN" + Count + "", ArgData[1]);
                                            }
                                            if (ArgData[2].trim().equals("0")) {
                                                Html = Html.replaceFirst("MAX" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MAX" + Count + "", ArgData[2]);
                                            }
                                            if (ArgData[3].trim().equals("0.00")) {
                                                Html = Html.replaceFirst("MEAN" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MEAN" + Count + "", ArgData[3]);
                                            }
                                            Count++;
                                        }
                                    }
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                Message = "No se encuentra información de los controles, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB - Controles','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            //</editor-fold>
                            break;
                        default:
                            //<editor-fold defaultstate="collapsed" desc="R-GC-220">
                            lst_RLab = RegistrosLabJpa.ConsultMaterialsRGC220(Order, Product, Batch);
                            if (lst_RLab != null && !lst_RLab.isEmpty() && lst_RLab.size() > 0) {
                                //<editor-fold defaultstate="collapsed" desc="MATERIAL REGISTROS LAB AND GENERACION DE LOTES">
                                String[] ArgMaterials = Util.parseResult(lst_RLab.get(0));
                                out.print("<input type='hidden' id='ErrorCount' value='" + ArgMaterials[9] + "'>");
                                String[] etiquetas = {
                                    "REF1", "LBT1", "REF2", "LBT2", "REF3", "LBT3", "REF4", "LBT4", "LBT5"
                                };

                                for (int i = 0; i < etiquetas.length; i++) {
                                    String valor = ArgMaterials[i].trim();
                                    String etiqueta = etiquetas[i];

                                    if (valor.equals("N/A")) {
                                        Html = Html.replace(etiqueta, "<span  class='editable pending' contenteditable='true'>----</span>");
                                    } else {
                                        // Un lote compuesto ("A/B", dos recepciones distintas para el
                                        // mismo material) debe verse en líneas separadas, no pegado con
                                        // "/" en una sola línea.
                                        if (etiqueta.startsWith("LBT") && valor.contains("/")) {
                                            valor = valor.replace("/", "<br>");
                                        }
                                        Html = Html.replace(etiqueta, valor);
                                    }
                                }

                                BatchLay = ArgMaterials[1];
                                String[] ArgImP = {ArgMaterials[1], ArgMaterials[3], ArgMaterials[5], ArgMaterials[7]};
                                String[] ArgProc = {"MANGA PVC", "DUCTO PVC", "DUCTO PVC", "CONECTOR"};
                                int Cnt = 1;
                                for (int i = 0; i < ArgImP.length; i++) {
                                    lst_Glotes1 = GeneracionLotesJpa.ConsultarCC_GeneracionLote(ArgImP[i], ArgProc[i]);
                                    if (lst_Glotes1 != null && !lst_Glotes1.isEmpty() && lst_Glotes1.size() > 0) {
                                        String[] ArgLay = lst_Glotes1.get(0).toString().replace("]", "").replace("[", "").split("///");
                                        Html = Html.replace("COS" + Cnt + "", "CC" + ArgLay[0]);
                                    } else {
                                        // Salvedad: si no se encontró por lote + familia de proceso
                                        // (el proceso puede tener un nombre que variantesProceso()
                                        // todavía no conoce, o el material de Registros LAB está
                                        // nombrado distinto al de Generación de Lotes), se busca de
                                        // nuevo solo por lote antes de rendirse. Si hay un único
                                        // consecutivo se usa directo; si hay varios, se deja elegir.
                                        List<String> lst_GlotesSinProceso = GeneracionLotesJpa.ConsultarCC_GeneracionLoteDetalle(ArgImP[i]);
                                        Html = Util.resolverConsecutivoAmbiguo(Html, Cnt, lst_GlotesSinProceso);

                                        if (lst_GlotesSinProceso == null || lst_GlotesSinProceso.isEmpty()) {
                                            Message = "No se encuentra información en Generacion de lotes en el módulo de Control consecutivos por el lote, favor verifique";

                                            // ✅ Ejecutar automáticamente cuando todo esté cargado
                                            out.print("<script>"
                                                    + "window.addEventListener('load',function(){"
                                                    + " registrarEvento('Generacion de lotes / Control Consecutivos','" + Order + "','" + ProductFact + "','" + ArgImP[i] + "','" + Message.replace("'", "\\'") + "');"
                                                    + "});"
                                                    + "</script>");
                                        }
                                    }
                                    Cnt++;
                                }

                                String[] ArgTnt = {ArgMaterials[8]};
                                int Cnt2 = 5;
                                for (int o = 0; o < ArgTnt.length; o++) {
                                    if (ArgTnt[o].equals("N/A")) {
                                        //<editor-fold defaultstate="collapsed" desc="VALIDATION">
                                        Html = Html.replace("REF" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                        Html = Html.replace("COS" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                        Message = "No se encuentra información en Generacion de lotes en el módulo de repcepción material por el lote, favor verifique";

                                        // ✅ Ejecutar automáticamente cuando todo esté cargado
                                        out.print("<script>"
                                                + "window.addEventListener('load',function(){"
                                                + " registrarEvento('Generacion de lotes / Recepción Material','" + Order + "','" + ProductFact + "','" + ArgTnt[o] + "','" + Message.replace("'", "\\'") + "');"
                                                + "});"
                                                + "</script>");
                                        //</editor-fold>    
                                    } else {
                                        lst_GlotesRep = GeneracionLotesJpa.ConsultarCC_RepcecionMaterial(ArgTnt[o]);
                                        if (lst_GlotesRep != null && !lst_GlotesRep.isEmpty() && lst_GlotesRep.size() > 0) {
                                            //<editor-fold defaultstate="collapsed" desc="CC RECECTION">
                                            Html = Util.replaceLoteReception(Html, lst_GlotesRep, Cnt2, false);
                                            //</editor-fold>
                                        } else {
                                            //<editor-fold defaultstate="collapsed" desc="VALIDATION">
                                            Html = Html.replace("REF" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");
                                            Html = Html.replace("COS" + Cnt2, "<span  class='editable pending' contenteditable='true'>----</span>");

                                            Message = "No se encuentra información en Generacion de lotes en el módulo de repcepción material por el lote, favor verifique";

                                            // ✅ Ejecutar automáticamente cuando todo esté cargado
                                            out.print("<script>"
                                                    + "window.addEventListener('load',function(){"
                                                    + " registrarEvento('Generacion de lotes / Recepción Material','" + Order + "','" + ProductFact + "','" + ArgTnt[o] + "','" + Message.replace("'", "\\'") + "');"
                                                    + "});"
                                                    + "</script>");
                                            //</editor-fold>
                                        }
                                    }
                                    Cnt2++;
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                for (int i = 1; i < 10; i++) {
                                    Html = Html.replace("REF" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                    Html = Html.replace("LBT" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                    Html = Html.replace("COS" + i + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                }
                                Message = "No se encuentra información materiales o ductos en Registros, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB -  Materiales','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            lst_sheet = SettingJpa.ConsultSettingCategorie(Record + "-Ficha");
                            if (lst_sheet != null) {
                                //<editor-fold defaultstate="collapsed" desc="TECNHNICAL SHEET">
                                Object[] ObjSheet = (Object[]) lst_sheet.get(0);
                                if (ObjSheet[2] != null && !ObjSheet[2].equals("")) {
                                    lst_prm = RegistrosLabJpa.QueryTechnicalSheetRGC220(Order, Product, Batch, ObjSheet[2].toString());
                                    if (lst_prm != null && lst_prm.size() > 0) {
                                        String[] ArgPrm = Util.parseResult(lst_prm.get(0));
                                        DataTechnicalSheet = ArgPrm[9];
                                        int ForCant = Integer.parseInt(ArgPrm[0].trim());
                                        for (int i = 1; i < ForCant; i++) {
                                            if (ArgPrm[i].contains("0 +/- 0")) {
                                                Html = Html.replaceFirst("PRM" + Count2 + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("PRM" + Count2 + "", ArgPrm[i]);
                                            }
                                            Count2++;
                                        }
                                    }
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                Message = "No se encuentra información de la ficha tecnica en Registros LAB, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB - Ficha Tecnica','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            lst_parameter = SettingJpa.ConsultSettingCategorie(Record);
                            if (lst_parameter != null) {
                                //<editor-fold defaultstate="collapsed" desc="DATA PARAMETER">
                                Object[] ObjParameter = (Object[]) lst_parameter.get(0);
                                if (ObjParameter[2] != null) {
                                    lst_data = RegistrosLabJpa.DimensionalQueryRGC220(Order, Product, Batch, ObjParameter[2].toString(), ObjParameter[3].toString());
                                    if (lst_data != null) {
                                        for (int i = 0; i < lst_data.size(); i++) {
                                            String[] ArgData = Util.parseResult(lst_data.get(i));
                                            if (ArgData[1].trim().equals("0")) {
                                                Html = Html.replaceFirst("MIN" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MIN" + Count + "", ArgData[1]);
                                            }
                                            if (ArgData[2].trim().equals("0")) {
                                                Html = Html.replaceFirst("MAX" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MAX" + Count + "", ArgData[2]);
                                            }
                                            if (ArgData[3].trim().equals("0.00")) {
                                                Html = Html.replaceFirst("MEAN" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                                            } else {
                                                Html = Html.replaceFirst("MEAN" + Count + "", ArgData[3]);
                                            }
                                            Count++;
                                        }
                                    }
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                                Message = "No se encuentra información de los controles, favor verifique";

                                // ✅ Ejecutar automáticamente cuando todo esté cargado
                                out.print("<script>"
                                        + "window.addEventListener('load',function(){"
                                        + " registrarEvento('Registros LAB - Controles','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                        + "});"
                                        + "</script>");
                                //</editor-fold>
                            }
                            //</editor-fold>
                            break;
                    }
                    String[] ArgCategory = {"boca", "cola"};
                    for (int i = 0; i < ArgCategory.length; i++) {
                        //<editor-fold defaultstate="collapsed" desc="DATA WELDS">
                        lst_welds = RegistrosLabJpa.QueryWelds(Order, Product, Batch, ArgCategory[i]);
                        if (lst_welds != null && !lst_welds.isEmpty() && lst_welds.size() > 0) {
                            String[] ArgWelds = Util.parseResult(lst_welds.get(0));
                            Html = Html.replace("MIN" + Count + "", ArgWelds[0]);
                            Html = Html.replace("MAX" + Count + "", ArgWelds[1]);
                            Html = Html.replace("MEAN" + Count + "", ArgWelds[2]);
                        } else {
                            Html = Html.replace("MIN" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                            Html = Html.replace("MAX" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                            Html = Html.replace("MEAN" + Count + "", "<span  class='editable pending' contenteditable='true'>----</span>");
                            //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                            Message = "No se encuentra información de soldadura Registros LAB, favor verifique";

                            // ✅ Ejecutar automáticamente cuando todo esté cargado
                            out.print("<script>"
                                    + "window.addEventListener('load',function(){"
                                    + " registrarEvento('Soldadura','" + Order + "','" + ProductFact + "','" + Batch + "','" + Message.replace("'", "\\'") + "');"
                                    + "});"
                                    + "</script>");
                            //</editor-fold>
                        }
                        Count++;
                        //</editor-fold>
                    }
                    if (!DataTechnicalSheet.equals("")) {
                        //<editor-fold defaultstate="collapsed" desc="CHANGE DATASHEET OBSERVATION">
                        Html = Html.replace("XFichaTecnicaX", DataTechnicalSheet);
                        //</editor-fold>
                    }
                    if (StateCerti == 0) {
                        out.print("<table class=\"table table-bordered mb-1\" style=\"width:100%\">\n"
                                + "		<tr>\n"
                                + "			<td colspan=\"10\" style=\"height: 0px;\" class=\"bg-warning text-white text-center fw-bold py-1\">\n"
                                + "				BORRADOR\n"
                                + "			</td>\n"
                                + "		</tr>\n"
                                + "	</table>");
                    }
                    out.print("<div id='HtmlContent'>");
                    out.print(Html);
                    out.print("</div>");

                    if (Permission.contains("[10]")) {
                        //<editor-fold defaultstate="collapsed" desc="FORM SAVE">
                        out.print("<form id='FormGenerate' action='Generate?opt=3' method='post' class='needs-validation' novalidate='' onsubmit='return FormGenerate(this)'>");
                        out.print("<input type='hidden' name='Type' value='" + Type + "'>");
                        out.print("<input type='hidden' name='Order' value='" + Order + "'>");
                        out.print("<input type='hidden' name='Product' value='" + ProductFact + "'>");
                        out.print("<input type='hidden' name='Batch' value='" + Batch + "'>");
                        out.print("<input type='hidden' name='FormatName' value='" + FormatName + "'>");
                        out.print("<input type='hidden' name='AmoutReg' value='" + AmoutReg + "'>");
                        out.print("</form>");
                        out.print("<div class='DivButtonPending'>");
                        out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='saveHtml()' data-toggle='tooltip' data-placement='top' title='Guardar'><i class='fas fa-save'></i></button>");
                        out.print("</div>");
                        out.print(" <script src=\"Interface/Content/Assets/js/DeleteRow.js\"></script>");
                        //</editor-fold>
                    }
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="VIEW CERTIFICATE UPDATE - CLOSE">
                    if (StateCerti == 2 || StateCerti == 3) {
                        //<editor-fold defaultstate="collapsed" desc="CLOSE">
                        out.print("<div id='Imprimir'  class=\"print-scale-wrapper\" ><div id='HtmlContent'>");
                        Html = Util.collapseSelects(Html);
                        Html = Util.collapseDateInputs(Html);
                        Html = Html.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                        Html = Html.replaceAll("<input type=\"checkbox\"", "<input type=\"checkbox\" class=\"disabled\" ");
                        Html = Html.replaceAll("<input name=\"result\" type=\"radio\"", "<input name=\"result\" type=\"radio\" class=\"disabled\"");
                        if (StateCerti == 3) {
                            if (IdSig != null && !IdSig.equals("")) {
                                Html = Html.replaceAll("<div id=\"SignatureImage\"></div>", "<div id=\"SignatureImage\"><img src=\"SignatureImage?cert=" + IdCertificates + "\" height=\"58px\" alt=\"Logo\" class=\"ImgSig\"></div>");
                            } else {
                                Html = Html.replaceAll("<div id=\"SignatureImage\"></div>", "<div id=\"SignatureImage\"><span class='text-warning'>--- No existe firma asociada ---</span></div>");
                            }
                        }
                        Html = Html.replaceAll(
                                "<button class=\"btn btn-sm btn-danger me-2 mr-2\" title=\"Eliminar fila o grupo\"><i class=\"fas fa-times\"></i></button>",
                                ""
                        );
                        // Quita la clase "pending" (resaltado gris de campo sin
                        // llenar) sin depender del orden ni de qué otras clases
                        // la acompañen (ej: "editable completed pending").
                        Html = Html.replaceAll("class=\"([^\"]*)\\bpending\\b\\s*([^\"]*)\"", "class=\"$1$2\"");

                        out.print(Html);
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="UPDATE">
                        out.print("<div id='HtmlContent'>");
                        out.print(Html);
                        out.print("</div>");
                        if (Client.equals("")) {
                            if (Permission.contains("[29]")) {
                                out.print("<form id='FormGenerate' action='Generate?opt=3' method='post' class='needs-validation' novalidate='' onsubmit='return FormGenerate(this)'>");
                                out.print("<input type='hidden' name='Type' value='" + Type + "'>");
                                out.print("<input type='hidden' name='Order' value='" + Order + "'>");
                                out.print("<input type='hidden' name='Product' value='" + Product + "'>");
                                out.print("<input type='hidden' name='Batch' value='" + Batch + "'>");
                                out.print("<input type='hidden' name='FormatName' value='" + FormatName + "'>");
                                out.print("<input type='hidden' name='IdCertificates' value='" + IdCertificates + "'>");
                                out.print("</form>");
                                out.print("<div class='DivButtonPending'>");
                                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='saveHtml()' data-toggle='tooltip' data-placement='top' title='Guardar'><i class='fas fa-save'></i></button>");
                                out.print("</div>");
                            }
                        }
                        out.print(" <script src=\"Interface/Content/Assets/js/DeleteRow.js\"></script>");
                        //</editor-fold>
                    }
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            if (Permission.contains("[39]")) {
                SupportDocumentUi.renderModal(out);

                // Sugerencia post-firma: Generate.java (opt=4, firma única) forward
                // encadena hasta acá dejando SigUnique=true en el mismo request; si
                // el certificado recién firmado aún no tiene soporte, se sugiere
                // adjuntarlo de una vez, reusando el mismo modal/flujo de arriba.
                Boolean sigUnique = (Boolean) pageContext.getRequest().getAttribute("SigUnique");
                if (Boolean.TRUE.equals(sigUnique) && StateCerti == 3 && IdCertificates > 0) {
                    List lstIdSugerencia = CertificatesJpa.ConsultCertificatesId(Type, IdCertificates);
                    if (lstIdSugerencia != null
                            && CertificateFilesJpa.consultSupportFileByCertificate(IdCertificates) == null) {
                        Object[] ObjIdSugerencia = (Object[]) lstIdSugerencia.get(0);
                        String returnToSugerencia = "Generate?opt=2&Type=" + Type + "&IdCertificates=" + IdCertificates
                                + "&TempDelete=" + TempDelete + "&StateCerti=3&TempM=" + TempM;
                        out.print("<script>");
                        out.print("$(document).ready(function () {");
                        out.print("swal({");
                        out.print("title: '¿Adjuntar documento de soporte?',");
                        out.print("text: 'El certificado quedó firmado. ¿Desea adjuntar y firmar un documento de soporte para este certificado?',");
                        out.print("icon: 'warning',");
                        out.print("buttons: { cancel: { text: 'Ahora no', visible: true, className: 'btn btn-secondary' },"
                                + "confirm: { text: 'Adjuntar', value: true, className: 'btn btn-primary' } }");
                        out.print("}).then(function (confirmado) {");
                        out.print("if (confirmado) {");
                        // Esto es JS dentro de <script>, no un atributo HTML: usar
                        // Util.jsString (no jsAttr, que deja "&amp;" literal aquí al
                        // no pasar por el decodificador de entidades del navegador).
                        out.print("abrirModalSoporte(" + IdCertificates + ",'" + Util.jsString(ObjIdSugerencia[4]) + "','"
                                + Util.jsString(ObjIdSugerencia[12]) + "','" + Util.jsString(ObjIdSugerencia[5]) + "','"
                                + Util.jsString(ObjIdSugerencia[7]) + "','" + Util.jsString(Type) + "','"
                                + Util.jsString(returnToSugerencia) + "');");
                        out.print("}");
                        out.print("});");
                        out.print("});");
                        out.print("</script>");
                    }
                }
            }
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
            try {
                out.print("<div class='alert alert-danger'>Error al cargar los datos del certificado.</div>");
            } catch (IOException ex1) {
                Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return super.doStartTag();
    }

    private String escapeHtmlAttribute(Object value) {
        return String.valueOf(value == null ? "" : value)
                .replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private String urlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value == null ? "" : value, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return "";
        }
    }
}
