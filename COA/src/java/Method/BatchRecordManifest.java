package Method;

import Connection.LinkBatchRecord;
import Controller.CertificatesJpaController;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
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

        // 0b. Documentos de soporte adjuntados y firmados desde GenerateReport
        // (SupportDocumentUploadServlet/SupportDocumentSignServlet), guardados en
        // la subcarpeta SupportDocs/ dentro del mismo lote. Se tratan como archivo
        // físico (PDF o imagen ya convertida a PDF) a la hora de fusionar.
        if (certificatesBasePath != null && cliente != null && anio != null) {
            File supportDocsDir = new File(certificatesBasePath + File.separator + cliente + File.separator + anio
                    + File.separator + orden + File.separator + lote + File.separator + "SupportDocs");
            File[] soportes = supportDocsDir.listFiles();
            if (soportes != null) {
                for (File soporte : soportes) {
                    if (!soporte.isFile()) {
                        continue;
                    }
                    Map<String, Object> doc = new HashMap<String, Object>();
                    boolean firmado = soporte.getName().contains("_FIRMADO_");
                    doc.put("origen", "Documento de Soporte");
                    doc.put("tipo", firmado ? "Documento de Soporte (Firmado)" : "Documento de Soporte (Pendiente de firma)");
                    doc.put("nombre", soporte.getName());
                    doc.put("url", "Certificates/" + cliente + "/" + anio + "/" + orden + "/" + lote + "/SupportDocs/" + soporte.getName());
                    doc.put("categoria", "soporte");
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

        // 5. Registros de Despeje de Línea Inspección Manga (ver consultarRegistrosDespejeManga).
        MangaListResult despejeResult = buildMangaDespejeDocumentos(certJpa, linkBatch, idCertificateManga);
        listaDocumentos.addAll(despejeResult.documentos);

        // 6. Registros de Cabecera Inspección Manga (ver consultarRegistrosCabeceraManga).
        MangaResult cabeceraResult = buildMangaCabeceraDocumento(certJpa, linkBatch, idCertificateManga);
        if (cabeceraResult.documento != null) {
            listaDocumentos.add(cabeceraResult.documento);
        }

        // 7. Registros R-PI-004 de Control Fórmulas (ver consultarRegistrosFormula).
        MangaListResult formulaResult = buildFormulaDocumentos(certJpa, linkBatch, idCertificateManga);
        listaDocumentos.addAll(formulaResult.documentos);

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
        LoteMangaResolucion lote = resolverLoteManga(certJpa, idCertificateManga);
        if (lote.diagnostico != null) {
            return MangaResult.fallo(lote.diagnostico);
        }
        Map<String, Object> resumenManga = linkBatch.InspeccionMangaResumenEstadistico(lote.loteProducto, lote.loteC);
        if (resumenManga == null) {
            String motivo = linkBatch.getDiagnosticoManga();
            return MangaResult.fallo(motivo != null ? motivo
                    : "No se pudo obtener el Resumen Estadístico de Inspección Manga para Lote Producto '" + lote.loteProducto
                    + "' y Lote C '" + lote.loteC + "'.");
        }
        Map<String, Object> doc = new HashMap<String, Object>();
        doc.put("origen", "Inspección Manga");
        doc.put("tipo", "Resumen Estadístico");
        doc.put("nombre", "Resumen Estadístico - Lote C " + lote.loteC);
        doc.put("categoria", "manga");
        doc.put("html", buildMangaResumenHtml(resumenManga));
        return MangaResult.ok(doc);
    }

    /**
     * Resultado de una lista de documentos de Inspección Manga (un
     * documento por cada registro, ej. cada despeje de línea asociado al
     * lote): {@code documentos} nunca es null (puede venir vacía), y
     * {@code diagnostico} explica por qué vino vacía cuando corresponde a un
     * fallo real (certificado/Referencia inválidos, error de conexión, o
     * simplemente no hay registros para ese lote en Inspección Manga).
     */
    public static class MangaListResult {

        public List<Map<String, Object>> documentos = new ArrayList<Map<String, Object>>();
        public String diagnostico;
    }

    /**
     * Consulta únicamente los Registros de Despeje de Línea de Inspección
     * Manga para un orden/lote, sin construir el resto del manifiesto —
     * mismo propósito que consultarInspeccionManga pero para "VER REGISTROS
     * DE DESPEJE", que trae uno o varios registros (uno por cada turno de
     * arranque) en vez de un único resumen.
     */
    public static MangaListResult consultarRegistrosDespejeManga(String orden, String lote) throws Exception {
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
        return buildMangaDespejeDocumentos(certJpa, linkBatch, idCertificateManga);
    }

    /**
     * Registros de Despeje de Línea ("VER REGISTROS DE DESPEJE" del Reporte
     * por lote de Inspección Manga, sp_rgt_t_registro_depeje_lotes_todos_p).
     * A diferencia del Resumen Estadístico, cada despeje ya trae su HTML
     * final completo guardado (registro_despeje.formato — el mismo
     * documento que se abre con el ícono de su listado, Orden?opc=14), así
     * que no hay que recalcular ni reconstruir nada: solo traerlo tal cual
     * (ver LinkBatchRecord.InspeccionMangaRegistrosDespeje) y agregar un
     * documento del manifiesto por cada uno, para que todos los despejes
     * asociados al lote_producto/lote_c queden incluidos en el Batch
     * Record.
     */
    private static MangaListResult buildMangaDespejeDocumentos(CertificatesJpaController certJpa, LinkBatchRecord linkBatch, int idCertificateManga) throws Exception {
        MangaListResult resultado = new MangaListResult();
        LoteMangaResolucion lote = resolverLoteManga(certJpa, idCertificateManga);
        if (lote.diagnostico != null) {
            resultado.diagnostico = lote.diagnostico;
            return resultado;
        }
        List<Map<String, Object>> despejes = linkBatch.InspeccionMangaRegistrosDespeje(lote.loteProducto, lote.loteC);
        if (despejes == null) {
            String motivo = linkBatch.getDiagnosticoDespeje();
            resultado.diagnostico = motivo != null ? motivo
                    : "No se pudieron obtener los Registros de Despeje de Inspección Manga para Lote Producto '"
                    + lote.loteProducto + "' y Lote C '" + lote.loteC + "'.";
            return resultado;
        }
        if (despejes.isEmpty()) {
            resultado.diagnostico = "No se encontraron Registros de Despeje en Inspección Manga para Lote Producto '"
                    + lote.loteProducto + "' y Lote C '" + lote.loteC + "'.";
            return resultado;
        }
        for (Map<String, Object> despeje : despejes) {
            String formato = (String) despeje.get("formato");
            if (formato == null || formato.isEmpty()) {
                continue;
            }
            Map<String, Object> doc = new HashMap<String, Object>();
            doc.put("origen", "Inspección Manga");
            doc.put("tipo", "Registro de Despeje");
            doc.put("nombre", "Despeje - " + esc(despeje.get("fechaTurno")) + " - Turno " + esc(despeje.get("turnoProduccion"))
                    + " - Lote P " + esc(despeje.get("loteP")));
            doc.put("categoria", "manga_despeje");
            doc.put("html", limpiarHtmlDespeje(formato));
            resultado.documentos.add(doc);
        }
        if (resultado.documentos.isEmpty()) {
            resultado.diagnostico = "Se encontraron Registros de Despeje en Inspección Manga para Lote Producto '"
                    + lote.loteProducto + "' y Lote C '" + lote.loteC + "', pero ninguno tiene el HTML del formato guardado.";
        }
        return resultado;
    }

    /**
     * Logo de PLASTITEC como Data URI (base64), precargado una sola vez desde
     * logo_plastitec_base64.txt (empaquetado junto a esta clase, así que
     * llega al classpath sin depender de ServletContext/rutas de disco). Se
     * usa para reemplazar la referencia rota
     * {@code <img src="Interfaz/Contenido/images/Logo.png">} que trae el
     * HTML de Inspección Manga — esa ruta es relativa a SU propio servidor
     * web y nunca resuelve desde COA. Es el mismo logo (PLASTITEC S.A.S) que
     * ya usa COA en web/Interface/Imagen/Logo.png. null si no se pudo cargar
     * (no rompe el render, el despeje simplemente queda sin logo).
     */
    private static final String LOGO_DATA_URI = cargarLogoBase64();

    private static String cargarLogoBase64() {
        try (InputStream in = BatchRecordManifest.class.getResourceAsStream("logo_plastitec_base64.txt")) {
            if (in == null) {
                return null;
            }
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] chunk = new byte[8192];
            int leidos;
            while ((leidos = in.read(chunk)) != -1) {
                buffer.write(chunk, 0, leidos);
            }
            return "data:image/png;base64," + buffer.toString("US-ASCII").trim();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Consulta únicamente los Registros de Cabecera de Inspección Manga para
     * un orden/lote, sin construir el resto del manifiesto.
     */
    public static MangaResult consultarRegistrosCabeceraManga(String orden, String lote) throws Exception {
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
        return buildMangaCabeceraDocumento(certJpa, linkBatch, idCertificateManga);
    }

    /**
     * Registros de Cabecera de Inspección Manga (información base de la
     * tabla registro/producto/línea/ficha_tecnica, sin la funcionalidad de
     * abrir/cerrar/firmar registro ni pasar rollos de su pantalla original —
     * ver LinkBatchRecord.InspeccionMangaRegistrosCabecera). Arma una única
     * tabla con un renglón por cada registro encontrado para el
     * lote_producto/lote_c del lote actual.
     */
    private static MangaResult buildMangaCabeceraDocumento(CertificatesJpaController certJpa, LinkBatchRecord linkBatch, int idCertificateManga) throws Exception {
        LoteMangaResolucion lote = resolverLoteManga(certJpa, idCertificateManga);
        if (lote.diagnostico != null) {
            return MangaResult.fallo(lote.diagnostico);
        }
        List<Map<String, Object>> registros = linkBatch.InspeccionMangaRegistrosCabecera(lote.loteProducto, lote.loteC);
        if (registros == null) {
            String motivo = linkBatch.getDiagnosticoCabecera();
            return MangaResult.fallo(motivo != null ? motivo
                    : "No se pudieron obtener los Registros de Cabecera de Inspección Manga para Lote Producto '"
                    + lote.loteProducto + "' y Lote C '" + lote.loteC + "'.");
        }
        if (registros.isEmpty()) {
            return MangaResult.fallo("No se encontraron Registros de Cabecera en Inspección Manga para Lote Producto '"
                    + lote.loteProducto + "' y Lote C '" + lote.loteC + "'.");
        }
        Map<String, Object> doc = new HashMap<String, Object>();
        doc.put("origen", "Inspección Manga");
        doc.put("tipo", "Registros de Cabecera");
        doc.put("nombre", "Registros de Cabecera - Lote C " + lote.loteC);
        doc.put("categoria", "manga");
        doc.put("html", buildMangaCabeceraHtml(registros));
        return MangaResult.ok(doc);
    }

    /**
     * Arma la tabla de Registros de Cabecera, un renglón por registro. Los
     * campos "Factor de Medida" y "Prueba Funcional" replican la misma regla
     * de "N/A según tipo de material" que usa la pantalla original de
     * Inspección Manga (material PP o sin ventana de estría no aplica).
     * "Dureza" se simplifica a mostrar el valor de registro.dureza cuando es
     * mayor a 0 (si no, N/A) — la pantalla original además consulta una
     * tabla de fórmulas/control de durezas aparte como respaldo cuando ese
     * valor es 0, que aquí no se reproduce.
     */
    private static String buildMangaCabeceraHtml(List<Map<String, Object>> registros) {
        StringBuilder filas = new StringBuilder();
        for (Map<String, Object> r : registros) {
            int material = toInt(r.get("material"));
            int estriaVentana = toInt(r.get("estriaVentana"));
            String factorMedida = (material == 1 || estriaVentana <= 1) ? "N/A" : fmt(r.get("factorMedida"));
            String pruebaFuncional = material == 1 ? "N/A" : fmt(r.get("pruebaFuncional"));
            String dureza = toDouble(r.get("dureza")) > 0 ? fmt(r.get("dureza")) : "N/A";
            filas.append("<tr>")
                    .append("<td>").append(esc(r.get("fechaTurno"))).append("</td>")
                    .append("<td>").append(esc(r.get("turnoProduccion"))).append("</td>")
                    .append("<td>").append(esc(r.get("linea"))).append("</td>")
                    .append("<td>").append(esc(r.get("loteProducto"))).append("</td>")
                    .append("<td>").append(esc(r.get("loteC"))).append("</td>")
                    .append("<td>").append(esc(r.get("loteP"))).append("</td>")
                    .append("<td class='num'>").append(factorMedida).append("</td>")
                    .append("<td>").append(formatResponsables(r.get("responsablesProduccion"))).append("</td>")
                    .append("<td>").append(esc(r.get("turnoCalidad"))).append("</td>")
                    .append("<td class='num'>").append(pruebaFuncional).append("</td>")
                    .append("<td class='num'>").append(dureza).append("</td>")
                    .append("<td>").append(formatRollos(r.get("rangoRollos"))).append("</td>")
                    .append("<td>").append(formatResponsables(r.get("responsablesCalidad"))).append("</td>")
                    .append("</tr>");
        }
        return "<html><head><meta charset='UTF-8'>"
                + "<style>"
                + "@page { size: A4 landscape; margin: 10mm; }"
                + "body { font-family: Arial, sans-serif; margin:0; padding:0; color:#212529; }"
                + "table { width:100%; border-collapse: collapse; font-size:11px; margin-top:12px; }"
                + "th, td { border:1px solid #ccc; padding:5px 6px; text-align:left; vertical-align:top; }"
                + "th { background:#e9ecef; text-align:center; }"
                + "td.num { text-align:center; }"
                + "</style></head><body>"
                + "<div style='background:#0b0025;color:#fff;padding:20px;border-radius:4px;'>"
                + "<h2 style='margin:0 0 5px 0;'>PLASTITEC S.A.S</h2>"
                + "<h4 style='margin:0;font-weight:normal;opacity:0.9;'>REGISTROS DE CABECERA POR GENERACIÓN DE LOTES</h4>"
                + "</div>"
                + "<table>"
                + "<tr><th>Fecha</th><th>Turno</th><th>Línea</th><th>Lote Producto</th><th>Lote C</th><th>Lote P</th>"
                + "<th>Factor de Medida</th><th>Responsables PI</th><th>Turno Calidad</th><th>Prueba Funcional</th>"
                + "<th>Dureza</th><th>Rollos</th><th>Responsables GC</th></tr>"
                + filas
                + "</table>"
                + "</body></html>";
    }

    /**
     * "Rol/Nombre,Rol/Nombre" (formato de registro.responsables_produccion y
     * responsables_calidad en Inspección Manga) → solo los nombres, uno por
     * línea, sin el color por rol que usa su pantalla original.
     */
    private static String formatResponsables(Object raw) {
        if (raw == null) {
            return "N/A";
        }
        String[] partes = String.valueOf(raw).split(",");
        StringBuilder sb = new StringBuilder();
        for (String parte : partes) {
            if (parte.trim().isEmpty()) {
                continue;
            }
            // Se toma el ÚLTIMO segmento (no el índice 1) porque a veces el rol
            // viene duplicado en el dato crudo, ej. "Operario_extrusion/Operario_extrusion/NOMBRE".
            String[] rolNombre = parte.split("/");
            String nombre = rolNombre[rolNombre.length - 1];
            if (sb.length() > 0) {
                sb.append("<br>");
            }
            sb.append(esc(nombre.trim()));
        }
        return sb.length() > 0 ? sb.toString() : "N/A";
    }

    /**
     * "[1036-1054][1055-1059]" (formato de registro.rango_rollos) →
     * "1036-1054<br>1055-1059", igual que la pantalla original.
     */
    private static String formatRollos(Object raw) {
        if (raw == null) {
            return "N/A";
        }
        String s = esc(raw).replace("][", "<br>").replace("[", "").replace("]", "");
        return s.isEmpty() ? "N/A" : s;
    }

    private static int toInt(Object value) {
        if (value == null) {
            return 0;
        }
        try {
            return Integer.parseInt(String.valueOf(value).trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private static double toDouble(Object value) {
        if (value == null) {
            return 0;
        }
        try {
            return Double.parseDouble(String.valueOf(value).trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * Consulta únicamente los registros R-PI-004 de Control Fórmulas para un
     * orden/lote, sin construir el resto del manifiesto. La búsqueda es por
     * el mismo lote_c del certificado que ya usamos para Inspección Manga
     * (Control Fórmulas identifica sus propios registros por
     * "lote_generacion", que coincide con ese mismo valor — no hace falta
     * ninguna derivación adicional, a diferencia de Inspección Manga que
     * necesitaba también la Referencia).
     */
    public static MangaListResult consultarRegistrosFormula(String orden, String lote) throws Exception {
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
        return buildFormulaDocumentos(certJpa, linkBatch, idCertificateManga);
    }

    /**
     * Registros R-PI-004 ("Control Lotes de Materias Primas en Fórmulas") de
     * Control Fórmulas — un documento por cada registro encontrado para el
     * lote_c del lote actual (ver LinkBatchRecord.ControlFormulasRegistrosPorLote).
     * Reutiliza resolverLoteManga solo para obtener el lote_c (no se necesita
     * la Referencia ni el lote_producto que sí hacen falta para Inspección
     * Manga).
     */
    private static MangaListResult buildFormulaDocumentos(CertificatesJpaController certJpa, LinkBatchRecord linkBatch, int idCertificateManga) throws Exception {
        MangaListResult resultado = new MangaListResult();
        LoteMangaResolucion lote = resolverLoteManga(certJpa, idCertificateManga);
        if (lote.diagnostico != null) {
            resultado.diagnostico = lote.diagnostico;
            return resultado;
        }
        List<Map<String, Object>> registros = linkBatch.ControlFormulasRegistrosPorLote(lote.loteC);
        if (registros == null) {
            String motivo = linkBatch.getDiagnosticoFormula();
            resultado.diagnostico = motivo != null ? motivo
                    : "No se pudieron obtener los registros R-PI-004 de Control Fórmulas para el lote '" + lote.loteC + "'.";
            return resultado;
        }
        if (registros.isEmpty()) {
            resultado.diagnostico = "No se encontraron registros R-PI-004 en Control Fórmulas para el lote '" + lote.loteC + "'.";
            return resultado;
        }
        for (Map<String, Object> registro : registros) {
            Map<String, Object> doc = new HashMap<String, Object>();
            doc.put("origen", "Control Fórmulas");
            doc.put("tipo", "R-PI-004");
            doc.put("nombre", "R-PI-004 - Lote " + esc(registro.get("loteGeneracion")) + " - " + esc(registro.get("nombreFormula")));
            doc.put("categoria", "manga");
            doc.put("html", buildFormulaHtml(registro));
            resultado.documentos.add(doc);
        }
        return resultado;
    }

    /**
     * Arma la página HTML de un registro R-PI-004 individual (cabecera +
     * tabla transpuesta de materiales, un renglón por atributo y una columna
     * por material), replicando el mismo formato y las mismas reglas de
     * Control Fórmulas: la barra "COPIA NO CONTROLADA" y el encabezado
     * "REGISTRO"/"VERSIÓN 1" solo aparecen para fechas >= 2015-04-22 (antes
     * era "MANUAL DE REGISTROS"/"VERSION 0"), y la columna de Clasificación
     * en Observaciones también depende de esa fecha.
     */
    private static String buildFormulaHtml(Map<String, Object> registro) {
        String fechaGeneracion = String.valueOf(registro.get("fechaGeneracion"));
        double version = 0;
        try {
            String[] partesFecha = fechaGeneracion.split("-");
            version = Double.parseDouble(partesFecha[0] + "." + partesFecha[1] + partesFecha[2]);
        } catch (Exception ignored) {
        }

        List<Map<String, Object>> detalles = (List<Map<String, Object>>) registro.get("detalles");
        if (detalles == null) {
            detalles = new ArrayList<Map<String, Object>>();
        }
        int contadorMaestra = 0;
        int contadorEquivalente = 0;
        for (Map<String, Object> detalle : detalles) {
            if (toInt(detalle.get("idTipoMateriaPrima")) == 1) {
                contadorMaestra++;
            } else {
                contadorEquivalente++;
            }
        }

        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset='UTF-8'>")
                .append("<style>")
                .append("@page { size: A4 landscape; margin: 8mm; }")
                .append("body { font-family: Arial, sans-serif; font-size:11px; color:#212529; margin:0; }")
                .append("table.rpi { width:100%; border-collapse: collapse; margin-top:8px; }")
                .append("table.rpi td, table.rpi th { border:1px solid #444; padding:4px 6px; }")
                .append("td.vacia { background:#ddd; }")
                .append(".rojo { color:#c0392b; } .calidad { color:#1a7a4c; } .negro { color:#000; }")
                .append("</style></head><body>")
                .append("<table class='rpi'>");

        if (version >= 2016.0101) {
            html.append("<tr><td colspan='6' style='background:#979595;text-align:center;'><b style='color:#fff;'>COPIA NO CONTROLADA</b></td></tr>");
        }

        html.append("<tr>")
                .append("<td align='center' colspan='2' rowspan='2'>");
        if (LOGO_DATA_URI != null) {
            html.append("<img src='").append(LOGO_DATA_URI).append("' alt='Logo' style='width:202.5px;height:67.5px' />");
        }
        html.append("</td>");
        if (version >= 2015.0422) {
            html.append("<td align='center' colspan='2'>REGISTRO</td>")
                    .append("<td align='center' colspan='2'>CODIGO <b>R-PI-004</b></td>");
        } else {
            html.append("<td align='center' colspan='2'>MANUAL DE REGISTROS</td>")
                    .append("<td align='center' colspan='2'>CODIGO <b>R-PI-004</b></td>");
        }
        html.append("</tr>")
                .append("<tr>")
                .append("<td align='center' colspan='2'>CONTROL LOTES DE MATERIAS<br />PRIMAS EN FORMULAS</td>");
        if (version >= 2015.0422) {
            html.append("<td align='center' colspan='2'>VERSIÓN: <b>1</b></td>");
        } else {
            html.append("<td align='center' colspan='2'>VERSION <b>0</b></td>");
        }
        html.append("</tr>")
                .append("<tr>")
                .append("<th colspan='2'>FECHA</th>")
                .append("<th colspan='2'>RESPONSABLE POR PRODUCCIÓN</th>")
                .append("<th colspan='2'>RESPONSABLE POR CALIDAD</th>")
                .append("</tr>")
                .append("<tr>")
                .append("<td align='center' colspan='2'>").append(esc(registro.get("fechaGeneracion"))).append("</td>")
                .append("<td align='center' colspan='2'>").append(esc(registro.get("responsableProduccion"))).append("</td>");
        String responsableCalidad = String.valueOf(registro.get("responsableCalidad"));
        String claseCalidad = "PENDIENTE".equals(responsableCalidad) ? "rojo" : "calidad";
        html.append("<td align='center' colspan='2'><b class='").append(claseCalidad).append("'>")
                .append(esc(responsableCalidad)).append("</b></td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td align='center'><b>FORMULA</b></td>")
                .append("<td align='center'>").append(esc(registro.get("nombreFormula"))).append("</td>")
                .append("<td align='center'><b>LOTE</b></td>")
                .append("<td align='center'><b class='negro'>").append(esc(registro.get("loteGeneracion"))).append("</b></td>")
                .append("<td align='center'><b>COD DEL COMPUESTO</b></td>")
                .append("<td align='center'>").append(esc(registro.get("codigoCompuesto"))).append("</td>")
                .append("</tr>")
                .append("</table>")
                .append("<table class='rpi'>")
                .append("<tr><th></th>");
        if (contadorMaestra > 0) {
            html.append("<th colspan='").append(contadorMaestra).append("'>M DESCRITOS EN LA FORMULA MAESTRA</th>");
        }
        if (contadorEquivalente > 0) {
            html.append("<th colspan='").append(contadorEquivalente).append("'>M EQUIVALENTES</th>");
        }
        html.append("</tr>")
                .append(filaDetalleFormula("CONSECUTIVO", detalles, "consecutivo"))
                .append(filaDetalleFormula("LOTE PRINCIPAL", detalles, "lotePrincipal"))
                .append(filaDetalleFormula("CONSECUTIVO DE CALIDAD", detalles, "consecutivoCalidadPrincipal"))
                .append(filaDetalleFormula("SUBLOTES DE MATERIA PRIMA", detalles, "subLote"))
                .append(filaDetalleFormula("CONSECUTIVO DE CALIDAD", detalles, "consecutivoCalidadSub"))
                .append("<tr><th>OBSERVACIONES</th>");
        int totalColumnas = contadorMaestra + contadorEquivalente;
        Object observacion = registro.get("observacion");
        String textoObservacion = observacion == null ? "<b class='rojo'>NINGUNA</b>" : esc(observacion);
        if (version >= 2015.0422) {
            html.append("<td colspan='").append(Math.max(totalColumnas - 2, 1)).append("'>").append(textoObservacion).append("</td>")
                    .append("<td colspan='2'><b>Clasificación</b><br /><b class='negro'>").append(esc(registro.get("clasificacion"))).append("</b></td>");
        } else {
            html.append("<td colspan='").append(Math.max(totalColumnas, 1)).append("'>").append(textoObservacion).append("</td>");
        }
        html.append("</tr>")
                .append("</table>")
                .append("</body></html>");
        return html.toString();
    }

    /**
     * Un renglón de la tabla transpuesta de materiales: la etiqueta y luego
     * una celda por cada material en {@code detalles}, con fondo gris si el
     * valor de ese campo viene vacío (igual que la pantalla original).
     */
    private static String filaDetalleFormula(String etiqueta, List<Map<String, Object>> detalles, String campo) {
        StringBuilder fila = new StringBuilder("<tr><td>").append(etiqueta).append("</td>");
        for (Map<String, Object> detalle : detalles) {
            Object valor = detalle.get(campo);
            String texto = valor == null ? "" : String.valueOf(valor).trim();
            if (texto.isEmpty()) {
                fila.append("<td class='vacia'></td>");
            } else {
                fila.append("<td>").append(esc(texto)).append("</td>");
            }
        }
        fila.append("</tr>");
        return fila.toString();
    }

    /**
     * Deja el HTML del despeje (registro_despeje.formato, ya con todos los
     * valores diligenciados) listo para imprimirse como PDF:
     * <ul>
     * <li>Desactiva cualquier campo/checkbox editable, igual que
     * CertifiedView.jsp hace con el HTML propio de un certificado antes de
     * mostrarlo/imprimirlo.</li>
     * <li>Reemplaza el logo de Inspección Manga (ruta relativa a su propio
     * servidor, rota desde COA) por el mismo logo ya empaquetado en
     * COA.</li>
     * <li>Lo envuelve en una página con un CSS de respaldo que reconstruye
     * el mismo look (encabezados de sección en verde, bordes de tabla,
     * campos subrayados) que este formato tiene en Inspección Manga —
     * a diferencia de "Certificado COA" (que sí se puede pedir por su
     * propia URL dentro de COA, con Bootstrap ya cargado), este HTML llega
     * crudo desde la base de datos de Inspección Manga sin su hoja de
     * estilos ni Bootstrap disponibles, así que hay que suministrar todo el
     * estilo visual aquí.</li>
     * </ul>
     */
    private static String limpiarHtmlDespeje(String html) {
        String limpio = html
                .replace("contenteditable=\"true\"", "contenteditable=\"false\"")
                .replaceAll("<input type=\"checkbox\"", "<input type=\"checkbox\" disabled=\"disabled\" ")
                .replaceAll("<input([^>]*)type=\"radio\"", "<input$1type=\"radio\" disabled=\"disabled\"");
        if (LOGO_DATA_URI != null) {
            limpio = limpio.replaceAll("src=\"[^\"]*Logo\\.png\"", "src=\"" + LOGO_DATA_URI + "\"");
        }
        return "<html><head><meta charset='UTF-8'>"
                + "<style>"
                + "@page { size: A4 portrait; margin: 8mm; }"
                + "body { font-family: Arial, sans-serif; font-size:11px; color:#212529; margin:0; }"
                + "table.table { width:100%; border-collapse: collapse; margin-bottom:6px; }"
                + "table.table td, table.table th { border: 1px solid #444; padding: 3px 6px; vertical-align: top; font-size:11px; }"
                + "table.table th { background:#1a7a4c; color:#fff; text-align:center; font-size:11px; }"
                + "table.table i { font-style: normal; }"
                + "table.table u { text-decoration: underline; }"
                + "</style></head><body>"
                + limpio
                + "</body></html>";
    }

    /**
     * Resultado de derivar el lote_producto/lote_c de Inspección Manga a
     * partir del certificado COA del lote (ver la clase base
     * BatchRecordManifest.buildMangaDocumento para el porqué de la fórmula):
     * o bien {@code loteProducto}/{@code loteC} resueltos, o
     * {@code diagnostico} explicando por qué no se pudo.
     */
    private static class LoteMangaResolucion {

        String loteProducto;
        String loteC;
        String diagnostico;

        private static LoteMangaResolucion ok(String loteProducto, String loteC) {
            LoteMangaResolucion r = new LoteMangaResolucion();
            r.loteProducto = loteProducto;
            r.loteC = loteC;
            return r;
        }

        private static LoteMangaResolucion fallo(String diagnostico) {
            LoteMangaResolucion r = new LoteMangaResolucion();
            r.diagnostico = diagnostico;
            return r;
        }
    }

    /**
     * Inspección Manga identifica sus registros con su PROPIO "lote_producto"
     * (ej. "8835-36H12"), que NO es ni el lote de COA (ej. "4824-36H11") ni el
     * "LOTE #" del certificado (ej. "17601-36H12") por sí solo — es
     * "&lt;Referencia del material&gt;-&lt;mismo sufijo de fecha que el LOTE #&gt;",
     * confirmado consultando su base de datos directamente. La "Referencia"
     * (ej. "8835") no se guarda en ningún lado de COA salvo dentro del HTML del
     * certificado ya almacenado (columna certificates.format), así que se
     * extrae de ahí en vez de tocar el flujo de creación del certificado.
     * Compartido por el Resumen Estadístico y los Registros de Despeje, que
     * se filtran por el mismo lote_producto/lote_c.
     */
    private static LoteMangaResolucion resolverLoteManga(CertificatesJpaController certJpa, int idCertificateManga) throws Exception {
        if (idCertificateManga <= 0) {
            return LoteMangaResolucion.fallo("No se encontró un certificado COA para este lote, por lo que no se puede determinar "
                    + "la Referencia necesaria para consultar Inspección Manga.");
        }
        List lstHtml = certJpa.ConsultCertificatesIdHtml(idCertificateManga);
        if (lstHtml == null || lstHtml.isEmpty()) {
            return LoteMangaResolucion.fallo("No se pudo leer el contenido del certificado (id " + idCertificateManga
                    + ") para extraer la Referencia y el Lote #.");
        }
        Object[] certRow = (Object[]) lstHtml.get(0);
        String htmlCertificado = certRow.length > 3 && certRow[3] != null ? certRow[3].toString() : "";
        String[] materialManga = extraerPrimerMaterial(htmlCertificado);
        if (materialManga == null) {
            return LoteMangaResolucion.fallo("No se pudo extraer la Referencia y el Lote # del certificado (id " + idCertificateManga
                    + "): el formato del HTML no coincidió con lo esperado.");
        }
        String referencia = materialManga[0];
        String loteC = materialManga[1];
        int guionIdx = loteC.lastIndexOf('-');
        if (referencia.isEmpty() || referencia.equals("----") || loteC.isEmpty()
                || loteC.equals("----") || guionIdx < 0) {
            return LoteMangaResolucion.fallo("El certificado no tiene una Referencia ('" + referencia + "') o Lote # ('" + loteC
                    + "') válidos para consultar Inspección Manga.");
        }
        String loteProducto = referencia + loteC.substring(guionIdx);
        return LoteMangaResolucion.ok(loteProducto, loteC);
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
