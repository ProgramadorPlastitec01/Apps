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
 * Certificados COA, Generación de Lotes, Inspección Manga, and manually
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
        int idCertificateManga = 0;
        List lstCert = certJpa.ConsultCertificatesBatchRecord(orden, lote);
        if (lstCert != null) {
            for (Object item : lstCert) {
                Object[] arg = (Object[]) item;
                if (arg.length >= 5) {
                    materialBatch += arg[4];
                    if (idCertificateManga == 0) {
                        try {
                            idCertificateManga = Integer.parseInt(String.valueOf(arg[0]));
                        } catch (NumberFormatException ignored) {
                        }
                    }
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

        // 4. Resumen Estadístico Inspección Manga (ver consultarInspeccionManga).
        MangaResult mangaResult = buildMangaDocumento(certJpa, linkBatch, idCertificateManga);
        if (mangaResult.documento != null) {
            listaDocumentos.add(mangaResult.documento);
        }

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("cliente", cliente != null ? cliente : "");
        resultado.put("anio", anio != null ? anio : "");
        resultado.put("orden", orden);
        resultado.put("lote", lote);
        resultado.put("documentos", listaDocumentos);
        return resultado;
    }

    /**
     * Resultado de intentar traer el Resumen Estadístico de Inspección Manga:
     * o bien el documento listo para el manifiesto ({@code documento} no
     * nulo), o una explicación de por qué no se pudo traer ({@code
     * diagnostico} no nulo) — útil para mostrarle al usuario en el listado
     * previo una razón concreta (certificado no encontrado, datos de
     * Referencia/Lote inválidos, fallo de conexión, o lotes que no coinciden
     * en Inspección Manga) en vez de simplemente omitir la fila en silencio.
     */
    public static class MangaResult {

        public Map<String, Object> documento;
        public String diagnostico;

        private static MangaResult ok(Map<String, Object> documento) {
            MangaResult r = new MangaResult();
            r.documento = documento;
            return r;
        }

        private static MangaResult fallo(String diagnostico) {
            MangaResult r = new MangaResult();
            r.diagnostico = diagnostico;
            return r;
        }
    }

    /**
     * Consulta únicamente el Resumen Estadístico de Inspección Manga para un
     * orden/lote, sin construir el resto del manifiesto. Usado por
     * FileManager.jsp para mostrar la fila de Inspección Manga (o el motivo
     * por el que no aparece) en el listado de documentos del lote, antes de
     * generar el Batch Record unificado, reutilizando exactamente la misma
     * consulta/derivación que build() usa para el PDF final, de modo que el
     * listado nunca quede desincronizado con lo que realmente se incluye al
     * generar.
     */
    public static MangaResult consultarInspeccionManga(String orden, String lote) throws Exception {
        LinkBatchRecord linkBatch = new LinkBatchRecord();
        CertificatesJpaController certJpa = new CertificatesJpaController();

        int idCertificateManga = 0;
        List lstCert = certJpa.ConsultCertificatesBatchRecord(orden, lote);
        if (lstCert != null) {
            for (Object item : lstCert) {
                Object[] arg = (Object[]) item;
                if (arg.length >= 1) {
                    try {
                        idCertificateManga = Integer.parseInt(String.valueOf(arg[0]));
                    } catch (NumberFormatException ignored) {
                    }
                    break;
                }
            }
        }
        return buildMangaDocumento(certJpa, linkBatch, idCertificateManga);
    }

    /**
     * Resumen Estadístico Inspección Manga ("RESUMEN PVC Y PP", el mismo
     * reporte de su Reporte?opc=8 con Tipo_consulta=3). Su servlet "Reporte"
     * siempre exige sesión de usuario logeado en Inspección Manga (correcto para
     * sus propios usuarios), y COA no tiene ni debe tener esa sesión — pedirle la
     * página por HTTP nunca va a funcionar de forma confiable. Por eso se conecta
     * directo a su base de datos (igual que ya se hace con Registros LAB y
     * Generación de Lotes) y se reimplementa aquí el mismo cálculo que hace
     * Metodos.Estadisticos en esa app (ver LinkBatchRecord.InspeccionMangaResumenEstadistico
     * y Method.EstadisticosInspeccionManga), sin tocar su autenticación.
     * <p>
     * Inspección Manga identifica sus registros con su PROPIO "lote_producto"
     * (ej. "8835-36H12"), que NO es ni el lote de COA (ej. "4824-36H11") ni el
     * "LOTE #" del certificado (ej. "17601-36H12") por sí solo — es
     * "&lt;Referencia del material&gt;-&lt;mismo sufijo de fecha que el LOTE #&gt;",
     * confirmado consultando su base de datos directamente. La "Referencia"
     * (ej. "8835") no se guarda en ningún lado de COA salvo dentro del HTML del
     * certificado ya almacenado (columna certificates.format), así que se
     * extrae de ahí en vez de tocar el flujo de creación del certificado.
     */
    private static MangaResult buildMangaDocumento(CertificatesJpaController certJpa, LinkBatchRecord linkBatch, int idCertificateManga) throws Exception {
        if (idCertificateManga <= 0) {
            return MangaResult.fallo("No se encontró un certificado COA para este lote, por lo que no se puede determinar "
                    + "la Referencia necesaria para consultar Inspección Manga.");
        }
        List lstHtml = certJpa.ConsultCertificatesIdHtml(idCertificateManga);
        if (lstHtml == null || lstHtml.isEmpty()) {
            return MangaResult.fallo("No se pudo leer el contenido del certificado (id " + idCertificateManga
                    + ") para extraer la Referencia y el Lote #.");
        }
        Object[] certRow = (Object[]) lstHtml.get(0);
        String htmlCertificado = certRow.length > 3 && certRow[3] != null ? certRow[3].toString() : "";
        String[] materialManga = extraerPrimerMaterial(htmlCertificado);
        if (materialManga == null) {
            return MangaResult.fallo("No se pudo extraer la Referencia y el Lote # del certificado (id " + idCertificateManga
                    + "): el formato del HTML no coincidió con lo esperado.");
        }
        String referencia = materialManga[0];
        String loteC = materialManga[1];
        int guionIdx = loteC.lastIndexOf('-');
        if (referencia.isEmpty() || referencia.equals("----") || loteC.isEmpty()
                || loteC.equals("----") || guionIdx < 0) {
            return MangaResult.fallo("El certificado no tiene una Referencia ('" + referencia + "') o Lote # ('" + loteC
                    + "') válidos para consultar Inspección Manga.");
        }
        String loteProducto = referencia + loteC.substring(guionIdx);
        Map<String, Object> resumenManga = linkBatch.InspeccionMangaResumenEstadistico(loteProducto, loteC);
        if (resumenManga == null) {
            String motivo = linkBatch.getDiagnosticoManga();
            return MangaResult.fallo(motivo != null ? motivo
                    : "No se pudo obtener el Resumen Estadístico de Inspección Manga para Lote Producto '" + loteProducto
                    + "' y Lote C '" + loteC + "'.");
        }
        Map<String, Object> doc = new HashMap<String, Object>();
        doc.put("origen", "Inspección Manga");
        doc.put("tipo", "Resumen Estadístico");
        doc.put("nombre", "Resumen Estadístico - Lote C " + loteC);
        doc.put("categoria", "manga");
        doc.put("html", buildMangaResumenHtml(resumenManga));
        return MangaResult.ok(doc);
    }

    /**
     * Extrae del HTML ya guardado del certificado (tabla "1. MATERIALES") la
     * Referencia y el Lote # de la primera fila (siempre la manga/lay-flat,
     * filas 1.1/1.2 del formato R-GC-046) — ej. de
     * {@code <td colspan="2">8835</td><td colspan="2" id="IdBatchM1">17601-36H12</td>}
     * devuelve {"8835", "17601-36H12"}. La celda de Referencia siempre es la
     * inmediatamente anterior a la celda con id="IdBatchM1" en esa fila.
     */
    private static String[] extraerPrimerMaterial(String html) {
        if (html == null || html.isEmpty()) {
            return null;
        }
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(
                "<td[^>]*>\\s*([^<]*?)\\s*</td>\\s*<td[^>]*id=\"IdBatchM1\"[^>]*>\\s*([^<]*?)\\s*</td>",
                java.util.regex.Pattern.CASE_INSENSITIVE).matcher(html);
        if (m.find()) {
            return new String[]{m.group(1).trim(), m.group(2).trim()};
        }
        return null;
    }

    /**
     * Arma la página HTML del "Resumen Estadístico por Generación de Lotes"
     * de Inspección Manga a partir de los datos ya traídos directo de su base
     * de datos (ver LinkBatchRecord.InspeccionMangaResumenEstadistico), con
     * el mismo layout que su propio Tag_reporte (tipo_consulta=3) pero con el
     * estilo visual de COA, para que quede consistente con el resto del
     * Batch Record.
     */
    private static String buildMangaResumenHtml(Map<String, Object> r) {
        return "<html><head><meta charset='UTF-8'>"
                + "<style>"
                + "@page { size: A4 landscape; margin: 10mm; }"
                + "body { font-family: Arial, sans-serif; margin:0; padding:0; color:#212529; }"
                + "table { width:100%; border-collapse: collapse; font-size:12px; margin-top:12px; }"
                + "th, td { border:1px solid #ccc; padding:6px 8px; text-align:left; }"
                + "th { background:#e9ecef; text-align:center; }"
                + "td.num { text-align:center; }"
                + "</style></head><body>"
                + "<div style='background:#0b0025;color:#fff;padding:20px;border-radius:4px;'>"
                + "<h2 style='margin:0 0 5px 0;'>PLASTITEC S.A.S</h2>"
                + "<h4 style='margin:0;font-weight:normal;opacity:0.9;'>RESUMEN ESTADÍSTICO POR GENERACIÓN DE LOTES</h4>"
                + "</div>"
                + "<table>"
                + "<tr><td><b>Producto</b></td><td colspan='3'>" + esc(r.get("producto")) + "</td>"
                + "<td><b>Ficha Técnica</b></td><td>" + esc(r.get("fichaCodigo")) + " V " + esc(r.get("fichaVersion")) + "</td></tr>"
                + "<tr><td><b>Lote Producto</b></td><td>" + esc(r.get("loteProducto")) + "</td>"
                + "<td><b>Lote C</b></td><td>" + esc(r.get("loteC")) + "</td>"
                + "<td><b>Lote P</b></td><td>" + esc(r.get("loteP")) + "</td></tr>"
                + "</table>"
                + "<table>"
                + "<tr><th>Parámetro</th><th>MIN</th><th>MAX</th><th>PROM</th>"
                + "<th>Parámetro</th><th>MIN</th><th>MAX</th><th>PROM</th></tr>"
                + fila("Curvatura mm", r.get("minCV"), r.get("maxCV"), r.get("avgCV"),
                        "Dureza Sh.A", r.get("minDR"), r.get("maxDR"), r.get("avgDR"))
                + fila("Ancho de manga mm", r.get("minAM"), r.get("maxAM"), r.get("avgAM"),
                        "Ancho de bobina mm", r.get("minAB"), r.get("maxAB"), r.get("avgAB"))
                + fila("Peso Bruto Kg", r.get("minPB"), r.get("maxPB"), r.get("avgPB"),
                        "Peso Neto Kg", r.get("minPN"), r.get("maxPN"), r.get("avgPN"))
                + fila("Perímetros Insumos mm", r.get("minPI"), r.get("maxPI"), r.get("avgPI"),
                        "Perímetros Calidad mm", r.get("minPC"), r.get("maxPC"), r.get("avgPC"))
                + fila("Pared Doble Insumos mm", r.get("minPD"), r.get("maxPD"), r.get("avgPD"),
                        "Pared Sencilla Insumos mm", r.get("minPS"), r.get("maxPS"), r.get("avgPS"))
                + fila("Pared Doble Calidad mm", r.get("minPDCalidad"), r.get("maxPDCalidad"), r.get("promPDCalidad"),
                        "Pared Sencilla Calidad mm", r.get("minPSCalidad"), r.get("maxPSCalidad"), r.get("promPSCalidad"))
                + "</table>"
                + "</body></html>";
    }

    private static String fila(String etiqueta1, Object min1, Object max1, Object prom1,
            String etiqueta2, Object min2, Object max2, Object prom2) {
        return "<tr>"
                + "<td>" + etiqueta1 + "</td><td class='num'>" + fmt(min1) + "</td><td class='num'>" + fmt(max1) + "</td><td class='num'>" + fmt(prom1) + "</td>"
                + "<td>" + etiqueta2 + "</td><td class='num'>" + fmt(min2) + "</td><td class='num'>" + fmt(max2) + "</td><td class='num'>" + fmt(prom2) + "</td>"
                + "</tr>";
    }

    private static String fmt(Object value) {
        if (value == null) {
            return "N/A";
        }
        String s = String.valueOf(value);
        if (s.isEmpty()) {
            return "N/A";
        }
        return esc(s);
    }

    private static String esc(Object value) {
        return value == null ? "" : String.valueOf(value)
                .replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
