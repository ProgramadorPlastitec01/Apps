package Method;

import Connection.LinkBatchRecord;
import Controller.CertificatesJpaController;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds the list of documents that make up a Batch Record for a given
 * orden/lote, pulling from every source system involved (Registros LAB,
 * Certificados COA, Generación de Lotes, Registros LAB Resumen, and manually
 * uploaded physical files). Shared by BatchRecordPdfServlet (JSON manifest
 * used by older client-side code) and BatchRecordPdfGenerateServlet
 * (server-side PDF generation), so the list of documents never drifts
 * between the two.
 */
public class BatchRecordManifest {

    /** Kept for backwards compatibility: no physical-files folder to scan. */
    public static Map<String, Object> build(String orden, String lote, String cliente, String anio) throws Exception {
        return build(orden, lote, cliente, anio, null);
    }

    /**
     * @param certificatesBasePath Absolute path to the app's "Certificates" storage
     *      root (e.g. getServletContext().getRealPath("/Certificates")), used to list
     *      manually uploaded physical files (the same folder FileManager.jsp browses
     *      and FileManagerServlet uploads into). Pass null to skip that step (e.g. for
     *      contexts that don't have a ServletContext handy).
     */
    public static Map<String, Object> build(String orden, String lote, String cliente, String anio, String certificatesBasePath) throws Exception {
        LinkBatchRecord linkBatch = new LinkBatchRecord();
        CertificatesJpaController certJpa = new CertificatesJpaController();

        List<Map<String, Object>> listaDocumentos = new ArrayList<Map<String, Object>>();

        // 0. Archivos físicos subidos manualmente al lote (misma carpeta que
        // FileManager.jsp lista y FileManagerServlet usa para las subidas).
        if (certificatesBasePath != null && cliente != null && anio != null) {
            File lotDir = new File(certificatesBasePath + File.separator + cliente + File.separator + anio
                    + File.separator + orden + File.separator + lote);
            File[] archivos = lotDir.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (!archivo.isFile()) {
                        continue;
                    }
                    Map<String, Object> doc = new HashMap<String, Object>();
                    doc.put("origen", "Archivo Físico");
                    doc.put("tipo", "Archivo Físico");
                    doc.put("nombre", archivo.getName());
                    doc.put("url", "Certificates/" + cliente + "/" + anio + "/" + orden + "/" + lote + "/" + archivo.getName());
                    doc.put("categoria", "fisico");
                    listaDocumentos.add(doc);
                }
            }
        }

        // 1. Links Registros LAB
        List lstLink = linkBatch.LinkBatchRecord(orden, lote);
        if (lstLink != null) {
            for (Object item : lstLink) {
                String[] arg = Util.parseResult(item);
                if (arg.length >= 4) {
                    Map<String, Object> doc = new HashMap<String, Object>();
                    doc.put("origen", "Registros LAB");
                    doc.put("tipo", arg[1]);
                    doc.put("nombre", arg[2]);
                    doc.put("url", arg[3]);
                    doc.put("categoria", "link");
                    listaDocumentos.add(doc);
                }
            }
        }

        // 2. Certificados COA
        String materialBatch = "";
        List lstCert = certJpa.ConsultCertificatesBatchRecord(orden, lote);
        if (lstCert != null) {
            for (Object item : lstCert) {
                Object[] arg = (Object[]) item;
                if (arg.length >= 5) {
                    materialBatch += arg[4];
                    Map<String, Object> doc = new HashMap<String, Object>();
                    doc.put("origen", "Certificado COA");
                    doc.put("tipo", String.valueOf(arg[1]));
                    doc.put("nombre", String.valueOf(arg[2]));
                    doc.put("url", String.valueOf(arg[3]));
                    doc.put("categoria", "coa");
                    listaDocumentos.add(doc);
                }
            }
        }

        // 3. Anexos Generación de Lotes
        if (!materialBatch.isEmpty()) {
            List lstMat = linkBatch.AttachmentBatchRecord(materialBatch);
            if (lstMat != null) {
                for (Object item : lstMat) {
                    String[] arg = Util.parseResult(item);
                    if (arg.length >= 4) {
                        // arg[2] (Nombre) es el nombre real del archivo en disco (con
                        // extensión, ej. "C17601-36B13pH_Y_C._20260910_1240.pdf");
                        // arg[3] (Descripcion) es solo la etiqueta mostrada en pantalla
                        // (ej. "C17601-36B13pH Y C.", sin extensión). El servidor necesita
                        // el nombre real (con extensión) para saber cómo renderizarlo, y
                        // codificado porque puede traer espacios.
                        String archivoReal = arg[2].trim();
                        Map<String, Object> doc = new HashMap<String, Object>();
                        doc.put("origen", "Generación de Lotes");
                        doc.put("tipo", arg[1]);
                        doc.put("nombre", arg[3]);
                        doc.put("archivo", archivoReal);
                        doc.put("url", "DownloadGL?File_name=" + java.net.URLEncoder.encode(archivoReal, "UTF-8"));
                        doc.put("categoria", "anexo");
                        listaDocumentos.add(doc);
                    }
                }
            }
        }

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("cliente", cliente != null ? cliente : "");
        resultado.put("anio", anio != null ? anio : "");
        resultado.put("orden", orden);
        resultado.put("lote", lote);
        resultado.put("documentos", listaDocumentos);
        return resultado;
    }
}
