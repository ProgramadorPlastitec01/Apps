package Method;

import Controller.CertificateFileJpaController;
import Controller.CertificateFileRow;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.jsp.JspWriter;

/**
 * Botón/estado del documento de soporte de un Certificado COA, reusado tanto
 * en la lista de certificados del módulo de Generación (Tag.GenerateReport)
 * como en el detalle de un certificado recién firmado (Tag.Visual). El
 * documento queda ligado a un id_certificate puntual (certificate_files),
 * no solo al lote: un lote puede tener varios Certificados COA.
 *
 * El modal de carga que disparan estos botones se imprime una sola vez por
 * página con {@link #renderModal(JspWriter)}; los botones solo llaman a la
 * función JS global {@code abrirModalSoporte(...)} que ese modal define.
 */
public final class SupportDocumentUi {

    private SupportDocumentUi() {
    }

    /**
     * @param returnTo URL (con su propio query string, p.ej.
     * "Generate?opt=1&Type=X&IdCertificate=5") a la que deben volver el
     * upload y la firma del soporte al terminar.
     */
    public static void renderCertificateAction(JspWriter out, CertificateFileJpaController certificateFiles,
            long idCertificate, String cliente, String anio, String orden, String lote, String type,
            boolean canManage, String returnTo) throws IOException {
        renderCertificateAction(out, certificateFiles, idCertificate, cliente, anio, orden, lote, type,
                canManage, returnTo, false);
    }

    /**
     * @param canDelete habilita la opción "Eliminar" del menú (p.ej. Permission.contains("[4]")),
     * por si el usuario subió el archivo equivocado y necesita reemplazarlo.
     */
    public static void renderCertificateAction(JspWriter out, CertificateFileJpaController certificateFiles,
            long idCertificate, String cliente, String anio, String orden, String lote, String type,
            boolean canManage, String returnTo, boolean canDelete) throws IOException {

        if (!canManage) {
            return;
        }

        CertificateFileRow soporte = certificateFiles.consultSupportFileByCertificate(idCertificate);

        if (soporte == null) {
            String args = "'" + idCertificate + "','" + Util.jsAttr(cliente) + "','" + Util.jsAttr(anio) + "','"
                    + Util.jsAttr(orden) + "','" + Util.jsAttr(lote) + "','" + Util.jsAttr(type) + "','"
                    + Util.jsAttr(returnTo) + "'";
            out.print("<button type='button' class='btn btn-outline-secondary btn-sm mr-2' style='border-radius: 4px;' "
                    + "onclick=\"abrirModalSoporte(" + args + ")\" "
                    + "data-toggle='tooltip' data-placement='top' title='Adjuntar documento de soporte'>"
                    + "<i class=\"fas fa-paperclip\"></i></button>");
            return;
        }

        boolean firmado = soporte.getName().contains("_FIRMADO_");
        String verUrl = "FileDownloadProxyServlet?id=" + soporte.getId() + "&modo=inline";
        String descargaUrl = "FileDownloadProxyServlet?id=" + soporte.getId() + "&modo=adjunto";

        // Un solo botón, coloreado según el estado (gris = sin soporte, naranja
        // = pendiente de firma, verde = firmado); ver/firmar/descargar quedan
        // dentro del menú desplegable que abre, en vez de botones sueltos en
        // la fila.
        String colorClass = firmado ? "btn-success" : "btn-warning";
        String tooltip = firmado ? "Documento de soporte firmado" : "Documento de soporte pendiente de firma";

        out.print("<div class='dropdown d-inline'>");
        out.print("<button type='button' class='btn btn-sm " + colorClass + " mr-2' style='border-radius: 4px;' "
                + "data-toggle='dropdown' aria-haspopup='true' aria-expanded='false' "
                + "data-placement='top' title='" + tooltip + "'>"
                + "<i class=\"fas fa-file-signature\"></i></button>");
        out.print("<div class='dropdown-menu menuDropdow'>");
        out.print("<a class='dropdown-item has-icon' href='" + verUrl + "' target='_blank'>"
                + "<i class='fas fa-eye'></i> Ver documento</a>");
        if (!firmado) {
            String returnToEncoded;
            try {
                returnToEncoded = URLEncoder.encode(returnTo == null ? "" : returnTo, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                returnToEncoded = "";
            }
            String signUrl = "SupportDocumentSign.jsp?cliente=" + Util.jsAttr(cliente) + "&anio=" + Util.jsAttr(anio)
                    + "&orden=" + Util.jsAttr(orden) + "&lote=" + Util.jsAttr(lote) + "&id=" + soporte.getId()
                    + "&returnTo=" + returnToEncoded;
            out.print("<a class='dropdown-item has-icon' href='#' onclick=\"window.open('" + signUrl + "', '_blank'); return false;\">"
                    + "<i class='fas fa-signature'></i> Firmar</a>");
        }
        out.print("<a class='dropdown-item has-icon' href='" + descargaUrl + "' download>"
                + "<i class='fas fa-download'></i> Descargar</a>");
        if (canDelete) {
            String returnToEncoded;
            try {
                returnToEncoded = URLEncoder.encode(returnTo == null ? "" : returnTo, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                returnToEncoded = "";
            }
            String deleteArgs = "'" + Util.jsAttr(cliente) + "','" + Util.jsAttr(anio) + "','" + Util.jsAttr(orden) + "','"
                    + Util.jsAttr(lote) + "','" + soporte.getId() + "','" + returnToEncoded + "'";
            out.print("<div class='dropdown-divider'></div>");
            out.print("<a class='dropdown-item has-icon text-danger' href='#' onclick=\"confirmarEliminarSoporte("
                    + deleteArgs + "); return false;\"><i class='fas fa-trash'></i> Eliminar</a>");
        }
        out.print("</div>");
        out.print("</div>");
    }

    /** Imprime el modal de carga y su JS una sola vez por página. */
    public static void renderModal(JspWriter out) throws IOException {
        // Menu.jsp agrega una barra fija "Entorno de pruebas" con z-index:9998,
        // más alto que el modal de Bootstrap (1050 por defecto): sin esto esa
        // barra queda por encima del modal y tapa sus botones (Cancelar/
        // Adjuntar). Sin backdrop (data-backdrop='false' más abajo) no hace
        // falta subir el z-index del fondo oscuro: no se usa ninguno.
        out.print("<style>#supportCertModal { z-index: 10050; }</style>");
        out.print("<div class='modal fade' id='supportCertModal' tabindex='-1' role='dialog' data-backdrop='false'>");
        out.print("<div class='modal-dialog modal-md modal-dialog-centered' role='document'>");
        out.print("<div class='modal-content'>");
        out.print("<div class='modal-header'>");
        out.print("<h5 class='modal-title'><i class='fas fa-file-signature text-primary'></i> Adjuntar documento de soporte</h5>");
        out.print("<button type='button' class='close' data-dismiss='modal'><span>&times;</span></button>");
        out.print("</div>");
        out.print("<form id='supportCertUploadForm' action='SupportDocumentUploadServlet' method='post' enctype='multipart/form-data'>");
        out.print("<div class='modal-body'>");
        out.print("<input type='hidden' name='idCertificate' id='supportCertIdCertificate'>");
        out.print("<input type='hidden' name='cliente' id='supportCertCliente'>");
        out.print("<input type='hidden' name='anio' id='supportCertAnio'>");
        out.print("<input type='hidden' name='orden' id='supportCertOrden'>");
        out.print("<input type='hidden' name='lote' id='supportCertLote'>");
        out.print("<input type='hidden' name='Type' id='supportCertType'>");
        out.print("<input type='hidden' name='returnTo' id='supportCertReturnTo'>");
        out.print("<div class='form-group'>");
        out.print("<label>Documento (carta de cliente u otro soporte)</label>");
        out.print("<input type='file' name='file' class='form-control' accept='.pdf,.png,.jpg,.jpeg,.gif' required>");
        out.print("<small class='form-text text-muted'>Solo pdf, png, jpg, jpeg, gif. Queda vinculado a este Certificado COA "
                + "y disponible para firmar con la firma registrada del Director de Calidad.</small>");
        out.print("</div>");
        out.print("</div>");
        out.print("<div class='modal-footer'>");
        out.print("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
        out.print("<button type='submit' class='btn btn-primary'><i class=\"fas fa-cloud-upload-alt\"></i> Adjuntar</button>");
        out.print("</div>");
        out.print("</form>");
        out.print("</div>");
        out.print("</div>");
        out.print("</div>");

        out.print("<script>");
        out.print("function abrirModalSoporte(idCertificate, cliente, anio, orden, lote, type, returnTo) {");
        out.print("document.getElementById('supportCertIdCertificate').value = idCertificate;");
        out.print("document.getElementById('supportCertCliente').value = cliente;");
        out.print("document.getElementById('supportCertAnio').value = anio;");
        out.print("document.getElementById('supportCertOrden').value = orden;");
        out.print("document.getElementById('supportCertLote').value = lote;");
        out.print("document.getElementById('supportCertType').value = type;");
        out.print("document.getElementById('supportCertReturnTo').value = returnTo;");
        out.print("$('#supportCertModal').modal('show');");
        out.print("}");
        out.print("function confirmarEliminarSoporte(cliente, anio, orden, lote, id, returnToEncoded) {");
        out.print("swal({");
        out.print("title: '¿Eliminar documento de soporte?',");
        out.print("text: 'Podrá volver a adjuntar uno nuevo para este certificado. Esta acción no se puede deshacer.',");
        out.print("icon: 'warning',");
        out.print("buttons: { cancel: { text: 'Cancelar', visible: true, className: 'btn btn-secondary' },"
                + "confirm: { text: 'Sí, eliminar', value: true, className: 'btn btn-danger' } },");
        out.print("dangerMode: true");
        out.print("}).then(function (confirmado) {");
        out.print("if (confirmado) {");
        out.print("window.location.href = 'DeleteFileServlet'"
                + "+ '?cliente=' + encodeURIComponent(cliente)"
                + "+ '&anio=' + encodeURIComponent(anio)"
                + "+ '&orden=' + encodeURIComponent(orden)"
                + "+ '&lote=' + encodeURIComponent(lote)"
                + "+ '&id=' + encodeURIComponent(id)"
                + "+ '&returnTo=' + returnToEncoded;");
        out.print("}");
        out.print("});");
        out.print("}");
        out.print("</script>");
    }
}
