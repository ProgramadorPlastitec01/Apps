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
                        Map<String, Object> doc = new HashMap<String, Object>();
                        doc.put("origen", "Generación de Lotes");
                        doc.put("tipo", arg[1]);
                        doc.put("nombre", arg[3]);
                        doc.put("url", "DownloadGL?File_name=" + arg[2].trim());
                        doc.put("categoria", "anexo");
                        listaDocumentos.add(doc);
                    }
                }
            }
        }

        // 4. Reportes Resumen (VisorResumen Registros LAB)
        List lstSummary = linkBatch.RGC17BatchRecord(orden, lote);
        if (lstSummary != null) {
            for (Object item : lstSummary) {
                String[] arg = Util.parseResult(item);
                if (arg.length >= 14) {
                    Map<String, Object> doc = new HashMap<String, Object>();
                    doc.put("origen", "Registros LAB (Resumen)");
                    doc.put("tipo", arg[0]);
                    doc.put("nombre", arg[13]);
                    doc.put("categoria", "summary");
                    Map<String, String> postParams = new HashMap<String, String>();
                    postParams.put("Txt_orden", arg[1]);
                    postParams.put("Cbx_producto", arg[2]);
                    postParams.put("Cbx_lote", arg[3]);
                    postParams.put("Txt_fecha_inicio", arg[4]);
                    postParams.put("Txt_fecha_fin", arg[5]);
                    postParams.put("Txt_hora_inicio", arg[6]);
                    postParams.put("Txt_hora_fin", arg[7]);
                    postParams.put("Txt_numero_certificado", arg[8]);
                    postParams.put("Txt_fecha_despacho", arg[9]);
                    postParams.put("Txt_datos_totales", arg[10]);
                    postParams.put("Txt_usuario_responsable", arg[11]);
                    postParams.put("Id_resumen", arg[12]);
                    postParams.put("loteCola", "");
                    doc.put("postParams", postParams);
                    doc.put("postUrl", "http://172.16.1.164:8084/Registros_lab/VisorResumen?opc=1");
                    listaDocumentos.add(doc);
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
