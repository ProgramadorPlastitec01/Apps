package Servlet;

import Controller.CertificateFileJpaController;
import Controller.CertificateFileRow;
import Controller.ChecklistJpaController;
import Method.BatchRecordChecklist;
import Method.BatchRecordVersiones;
import Method.OfficePlatformService;
import Method.PartUtil;
import Method.SignatureHistory;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Acciones del usuario sobre la lista de verificación R-GC-094 de un lote
 * (requerimiento 1.1.U04), desde FileManager.jsp:
 * <ul>
 * <li>accion=confirmar: marca el ítem como verificado, opcionalmente
 * adjuntando el documento (se sube a Office Platform en la misma carpeta de
 * archivos físicos del lote, así entra al Batch Record unificado).</li>
 * <li>accion=na: el ítem no aplica a este lote.</li>
 * <li>accion=deshacer: borra la marca manual (el ítem vuelve a pendiente,
 * salvo que el sistema lo encuentre solo). El archivo adjunto, si hubo, se
 * conserva en el lote.</li>
 * </ul>
 * Lo pueden hacer el Gestor ([3], subir archivos al lote) y la Coordinadora
 * Documental ([41]). Cada acción queda en signature_history (tipo
 * CHECKLIST_BR, llave cliente|anio|orden|lote).
 */
@WebServlet("/BatchRecordChecklistServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50,     // 50MB
        maxRequestSize = 1024 * 1024 * 100  // 100MB
)
public class BatchRecordChecklistServlet extends HttpServlet {

    public static final String TIPO_HISTORIAL = "CHECKLIST_BR";

    /** Mismos formatos que FileManagerServlet: lo único que el Batch Record unificado sabe fusionar. */
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".pdf", ".png", ".jpg", ".jpeg", ".gif");

    public static boolean puedeMarcar(String permisos) {
        return permisos != null && (permisos.contains("[3]") || permisos.contains("[41]"));
    }

    public static String llaveHistorial(String cliente, String anio, String orden, String lote) {
        return cliente + "|" + anio + "|" + orden + "|" + lote;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String permisos = attr(session, "Permisos");
        String userDocument = attr(session, "Documento");
        String userName = attr(session, "Usuario");
        String nombreVisible = attr(session, "Nombres").isEmpty() ? userName : attr(session, "Nombres");

        String cliente = request.getParameter("cliente");
        String anio = request.getParameter("anio");
        String orden = request.getParameter("orden");
        String lote = request.getParameter("lote");
        String accion = request.getParameter("accion");
        String itemKey = request.getParameter("itemKey");
        String observacion = request.getParameter("observacion");
        observacion = observacion == null ? "" : observacion.trim();
        if (observacion.length() > 500) {
            observacion = observacion.substring(0, 500);
        }

        String redirectBase = "FileManager.jsp?cliente=" + enc(cliente) + "&anio=" + enc(anio)
                + "&orden=" + enc(orden) + "&lote=" + enc(lote);

        if (!puedeMarcar(permisos)) {
            response.sendRedirect(redirectBase + "&msg=ckl_permission#checklistRgc094");
            return;
        }
        if (cliente == null || anio == null || orden == null || lote == null || itemKey == null || accion == null) {
            response.sendRedirect(redirectBase + "&msg=ckl_error#checklistRgc094");
            return;
        }

        // El ítem debe pertenecer a la lista que aplica HOY a este lote (no se
        // acepta cualquier clave que llegue en el POST).
        BatchRecordChecklist.Resultado lista;
        try {
            lista = BatchRecordChecklist.evaluar(cliente, anio, orden, lote);
        } catch (Exception ex) {
            getServletContext().log("No se pudo evaluar la lista de verificación del lote " + lote, ex);
            response.sendRedirect(redirectBase + "&msg=ckl_error#checklistRgc094");
            return;
        }
        BatchRecordChecklist.Item item = lista.aplica ? lista.buscar(itemKey) : null;
        if (item == null) {
            response.sendRedirect(redirectBase + "&msg=ckl_error#checklistRgc094");
            return;
        }
        // Lote aprobado (el Batch Record vigente está firmado): la lista queda en
        // solo lectura. Para corregirla se genera un Batch Record nuevo, que pasa
        // a ser el vigente y requiere una nueva firma.
        if (BatchRecordVersiones.consultar(cliente, anio, orden, lote, lista.aplica).isAprobado()) {
            response.sendRedirect(redirectBase + "&msg=ckl_locked#checklistRgc094");
            return;
        }

        ChecklistJpaController checklist = new ChecklistJpaController();
        String etiqueta = item.numero + ". " + item.nombre + " (" + item.codigoRegistro + ")";
        String llave = llaveHistorial(cliente, anio, orden, lote);
        String msg;

        if ("deshacer".equals(accion)) {
            if (!checklist.deleteLoteMark(cliente, anio, orden, lote, itemKey)) {
                response.sendRedirect(redirectBase + "&msg=ckl_error#checklistRgc094");
                return;
            }
            SignatureHistory.registrar(request, TIPO_HISTORIAL, llave, null, etiqueta, cliente, anio, orden, lote,
                    "REVERTIDO", "Se deshizo la marca manual del ítem " + etiqueta + ".");
            msg = "ckl_undo";

        } else if ("na".equals(accion)) {
            if (!checklist.saveLoteMark(cliente, anio, orden, lote, item.idItem, itemKey, BatchRecordChecklist.ESTADO_NA,
                    null, observacion, userDocument, nombreVisible)) {
                response.sendRedirect(redirectBase + "&msg=ckl_error#checklistRgc094");
                return;
            }
            SignatureHistory.registrar(request, TIPO_HISTORIAL, llave, null, etiqueta, cliente, anio, orden, lote,
                    "NA", "Ítem " + etiqueta + " marcado N/A" + (observacion.isEmpty() ? "." : ": " + observacion));
            msg = "ckl_na";

        } else if ("confirmar".equals(accion)) {
            Long idFile = item.idFile;
            String nombreArchivo = null;
            Part archivo = null;
            try {
                archivo = request.getPart("file");
            } catch (Exception ignored) {
                archivo = null;
            }
            if (archivo != null && archivo.getSize() > 0) {
                nombreArchivo = PartUtil.getFileName(archivo);
                String lower = nombreArchivo == null ? "" : nombreArchivo.toLowerCase();
                int dot = lower.lastIndexOf('.');
                if (dot < 0 || !ALLOWED_EXTENSIONS.contains(lower.substring(dot))) {
                    response.sendRedirect(redirectBase + "&msg=error_extension#checklistRgc094");
                    return;
                }
                try {
                    idFile = subirArchivo(archivo, nombreArchivo, cliente, anio, orden, lote, userDocument, userName);
                } catch (IOException ex) {
                    getServletContext().log("No se pudo subir el adjunto de la lista de verificación: " + nombreArchivo, ex);
                    idFile = null;
                }
                if (idFile == null) {
                    response.sendRedirect(redirectBase + "&msg=error_upload#checklistRgc094");
                    return;
                }
            }
            if (!checklist.saveLoteMark(cliente, anio, orden, lote, item.idItem, itemKey, BatchRecordChecklist.ESTADO_VERIFICADO,
                    idFile, observacion, userDocument, nombreVisible)) {
                response.sendRedirect(redirectBase + "&msg=ckl_error#checklistRgc094");
                return;
            }
            SignatureHistory.registrar(request, TIPO_HISTORIAL, llave, null, etiqueta, cliente, anio, orden, lote,
                    "VERIFICADO", "Ítem " + etiqueta + " verificado"
                    + (nombreArchivo != null ? " con el archivo " + nombreArchivo : (idFile != null ? " (con archivo ya adjunto)" : " sin archivo adjunto"))
                    + (observacion.isEmpty() ? "." : ": " + observacion));
            msg = "ckl_ok";

        } else {
            msg = "ckl_error";
        }

        response.sendRedirect(redirectBase + "&msg=" + msg + "#checklistRgc094");
    }

    /**
     * Sube el adjunto a Office Platform (misma carpeta y registro que
     * FileManagerServlet: archivo físico del lote) y devuelve su id en
     * certificate_files, o null si no se pudo registrar.
     */
    private Long subirArchivo(Part part, String fileName, String cliente, String anio, String orden, String lote,
            String userDocument, String userName) throws IOException {
        long folderId = OfficePlatformService.resolveOrCreateFolderPath("COA", cliente, anio, orden, lote);
        OfficePlatformService.OfficeUploadResult resultado = OfficePlatformService.subirArchivo(
                folderId, fileName, null, part.getInputStream(), part.getContentType(), userDocument, userName);
        CertificateFileJpaController certificateFiles = new CertificateFileJpaController();
        if (!certificateFiles.registerFile(cliente, anio, orden, lote, "", fileName, resultado.fileId, resultado.uuid,
                resultado.mimeType, resultado.size >= 0 ? resultado.size : part.getSize(), userDocument, userName)) {
            return null;
        }
        for (CertificateFileRow fila : certificateFiles.consultFilesByLote(cliente, anio, orden, lote, "")) {
            if (fila.getOfficeFileId() == resultado.fileId) {
                return fila.getId();
            }
        }
        return null;
    }

    private static String attr(HttpSession session, String nombre) {
        Object v = session.getAttribute(nombre);
        return v == null ? "" : v.toString();
    }

    private static String enc(String value) throws IOException {
        return value == null ? "" : URLEncoder.encode(value, "UTF-8");
    }
}
