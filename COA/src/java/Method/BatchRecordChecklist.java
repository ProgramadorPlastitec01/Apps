package Method;

import Connection.LinkBatchRecord;
import Controller.ChecklistJpaController;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lista de verificación de documentos del lote R-GC-094 V11 (requerimiento
 * 1.1.U04): qué documentos deben ir en el Batch Record de un lote, cuáles ya
 * trae el sistema y cuáles faltan.
 * <ul>
 * <li>La sección (Bocas, Colpitts, Plumatt, Bolsa EVA...) se elige por el
 * formato del certificado COA firmado del lote y por los registros de línea
 * que tiene en Registros LAB (checklist_section). Si aplican varias (un lote
 * que pasó por Bocas y por Colpitt) se unen en una sola lista, sin repetir
 * los documentos comunes.</li>
 * <li>Cada ítem se da por verificado "por el sistema" si el manifiesto del
 * Batch Record (Method.BatchRecordManifest) trae un documento de su fuente y
 * código; así la lista y el PDF unificado nunca se contradicen. Esto se
 * calcula en vivo, no se guarda.</li>
 * <li>Lo que el sistema no encuentra lo marca el usuario: verificado (con
 * archivo adjunto opcional) o N/A (checklist_lote_item).</li>
 * </ul>
 */
public final class BatchRecordChecklist {

    private static final Logger LOGGER = Logger.getLogger(BatchRecordChecklist.class.getName());

    public static final String ESTADO_SISTEMA = "SISTEMA";
    public static final String ESTADO_VERIFICADO = "VERIFICADO";
    public static final String ESTADO_NA = "NA";
    public static final String ESTADO_PENDIENTE = "PENDIENTE";

    public static final String FUENTE_LAB_RESUMEN = "LAB_RESUMEN";
    public static final String FUENTE_MANUAL = "MANUAL";

    /**
     * Textos que la portada del Batch Record escribe según el estado de la
     * lista; BatchRecordSignServlet los busca en el PDF para no aprobar uno
     * generado con pendientes. INCOMPLETA contiene COMPLETA: buscar primero
     * INCOMPLETA.
     */
    public static final String PORTADA_COMPLETA = "LISTA DE VERIFICACIÓN COMPLETA";
    public static final String PORTADA_INCOMPLETA = "LISTA DE VERIFICACIÓN INCOMPLETA";

    /**
     * Sufijo del PDF del Batch Record generado con la lista incompleta
     * ("BatchRecord_..._BORRADOR.pdf"): es un borrador que no se puede
     * firmar; para aprobar hay que completar la lista y generarlo de nuevo.
     */
    public static final String SUFIJO_BORRADOR = "_BORRADOR";

    public static boolean esBorrador(String nombreArchivo) {
        return nombreArchivo != null && nombreArchivo.contains(SUFIJO_BORRADOR) && !nombreArchivo.contains("_FIRMADO_");
    }

    /**
     * ¿Este Batch Record pendiente se puede firmar? No, si es un borrador o si
     * se generó con la lista R-GC-094 incompleta (también los generados antes
     * del sufijo _BORRADOR): se mira el evento REGISTRADO del historial, que
     * guarda con qué estado de la lista se generó ("(completa)" / "(N
     * pendientes)"). Si al lote no le aplica la lista, siempre se puede.
     * Es la misma regla que aplica BatchRecordSignServlet al leer la portada,
     * pero sin descargar el PDF.
     */
    public static boolean esFirmable(String nombreArchivo, boolean listaAplica) {
        if (nombreArchivo == null || nombreArchivo.contains("_FIRMADO_") || esBorrador(nombreArchivo)) {
            return false;
        }
        if (!listaAplica) {
            return true;
        }
        try {
            List<Object[]> eventos = new Controller.SignatureHistoryJpaController()
                    .consult(SignatureHistory.TIPO_BATCH_RECORD, SignatureHistory.batchRecordKey(nombreArchivo));
            for (Object[] evento : eventos) {
                if (SignatureHistory.EVENTO_REGISTRADO.equals(str(evento[1]))) {
                    return str(evento[2]).contains("(completa)");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "No se pudo consultar el historial del Batch Record " + nombreArchivo, ex);
        }
        return false;
    }

    private BatchRecordChecklist() {
    }

    /** Un renglón de la lista de verificación del lote. */
    public static class Item {

        public int numero;
        public int idItem;
        public String key;
        public String nombre;
        public String codigoRegistro;
        public String fuente;
        public String codigoFuente;
        public String nota;
        public boolean obligatorio;
        public String estado = ESTADO_PENDIENTE;
        /** Documentos del manifiesto que respaldan el ítem (en el orden del manifiesto). */
        public List<Map<String, Object>> documentos = new ArrayList<Map<String, Object>>();
        /** Texto corto de lo que se encontró / quién lo marcó. */
        public String detalle = "";
        public String usuario = "";
        public String fecha = "";
        public String observacion = "";
        public Long idFile;
        public String nombreArchivo;
        /** Hay una marca manual guardada (se puede deshacer). */
        public boolean marcaManual;

        public boolean isAutomatico() {
            return !FUENTE_MANUAL.equals(fuente);
        }

        public boolean isPendiente() {
            return ESTADO_PENDIENTE.equals(estado);
        }
    }

    /** Resultado completo para un lote. */
    public static class Resultado {

        /** false si no hay una sección configurada para este lote (ver diagnostico). */
        public boolean aplica;
        public String codigoRegistro = "R-GC-094";
        public int version = 11;
        public String titulo = "";
        public List<String> secciones = new ArrayList<String>();
        public List<Item> items = new ArrayList<Item>();
        public String diagnostico;
        public int total;
        public int verificados;
        public int pendientes;

        public boolean isCompleta() {
            return aplica && pendientes == 0;
        }

        public Item buscar(String key) {
            for (Item item : items) {
                if (item.key.equals(key)) {
                    return item;
                }
            }
            return null;
        }
    }

    /** Arma la lista con un manifiesto liviano (sin Inspección Manga ni Control Fórmulas). */
    @SuppressWarnings("unchecked")
    public static Resultado evaluar(String cliente, String anio, String orden, String lote) throws Exception {
        Map<String, Object> manifest = BatchRecordManifest.build(orden, lote, cliente, anio, "", false);
        return evaluar(cliente, anio, orden, lote, (List<Map<String, Object>>) manifest.get("documentos"));
    }

    /** Arma la lista sobre un manifiesto ya construido (el mismo que se usa para el PDF). */
    public static Resultado evaluar(String cliente, String anio, String orden, String lote,
            List<Map<String, Object>> documentos) {
        Resultado resultado = new Resultado();
        if (documentos == null) {
            documentos = new ArrayList<Map<String, Object>>();
        }

        // 1. Qué hay en el lote: formatos de certificado y códigos de Registros LAB.
        Set<String> formatos = new HashSet<String>();
        Set<String> codigosLab = new HashSet<String>();
        Map<Long, Map<String, Object>> archivosLote = new HashMap<Long, Map<String, Object>>();
        for (Map<String, Object> doc : documentos) {
            String fuente = str(doc.get("fuente"));
            if (BatchRecordManifest.FUENTE_COA_CERTIFICADO.equals(fuente)) {
                formatos.add(normalizarCodigo(str(doc.get("codigo"))));
            } else if (BatchRecordManifest.FUENTE_LAB_REGISTRO.equals(fuente)
                    || BatchRecordManifest.FUENTE_LAB_DESPEJE.equals(fuente)) {
                codigosLab.addAll(expandirCodigos(str(doc.get("codigo"))));
            } else if (BatchRecordManifest.FUENTE_FISICO.equals(fuente) && doc.get("idFile") instanceof Number) {
                archivosLote.put(((Number) doc.get("idFile")).longValue(), doc);
            }
        }

        // 2. Plantilla: secciones activas con sus ítems.
        Map<Integer, Seccion> seccionesActivas = new LinkedHashMap<Integer, Seccion>();
        for (Object[] fila : new ChecklistJpaController().consultActiveItems()) {
            int idSeccion = toInt(fila[0]);
            Seccion seccion = seccionesActivas.get(idSeccion);
            if (seccion == null) {
                seccion = new Seccion();
                seccion.codigoRegistro = str(fila[1]);
                seccion.version = toInt(fila[2]);
                seccion.nombre = str(fila[3]);
                seccion.formatos = lista(str(fila[4]));
                seccion.disparadores = lista(str(fila[5]));
                seccionesActivas.put(idSeccion, seccion);
            }
            Item item = new Item();
            item.idItem = toInt(fila[7]);
            item.nombre = str(fila[9]);
            item.codigoRegistro = str(fila[10]);
            item.fuente = str(fila[11]).toUpperCase();
            item.codigoFuente = normalizarCodigo(str(fila[12]));
            item.nota = str(fila[13]);
            item.obligatorio = toInt(fila[14]) == 1;
            item.key = clave(item.codigoRegistro, item.nombre);
            seccion.items.add(item);
        }

        // 3. Secciones que aplican: formato del certificado + línea por la que pasó.
        List<Seccion> aplicables = new ArrayList<Seccion>();
        for (Seccion seccion : seccionesActivas.values()) {
            boolean formatoOk = false;
            for (String f : seccion.formatos) {
                if (formatos.contains(f)) {
                    formatoOk = true;
                    break;
                }
            }
            boolean lineaOk = seccion.disparadores.isEmpty();
            for (String d : seccion.disparadores) {
                if (codigosLab.contains(d)) {
                    lineaOk = true;
                    break;
                }
            }
            if (formatoOk && lineaOk) {
                aplicables.add(seccion);
            }
        }

        if (aplicables.isEmpty()) {
            resultado.aplica = false;
            if (seccionesActivas.isEmpty()) {
                resultado.diagnostico = "No hay secciones de la lista de verificación R-GC-094 configuradas.";
            } else if (formatos.isEmpty()) {
                resultado.diagnostico = "El lote no tiene un certificado COA firmado; la sección del R-GC-094 se determina por el formato del certificado y las líneas del lote.";
            } else {
                resultado.diagnostico = "No hay una sección del R-GC-094 configurada para el certificado " + formatos
                        + (codigosLab.isEmpty() ? " (el lote no tiene registros de línea en Registros LAB)."
                        : " con los registros de línea del lote " + codigosLab + ".");
            }
            return resultado;
        }

        // 4. Unión de secciones sin repetir documentos comunes: cada ítem nuevo
        //    queda justo después del ítem que lo precede en su propia sección.
        resultado.aplica = true;
        resultado.codigoRegistro = aplicables.get(0).codigoRegistro;
        resultado.version = aplicables.get(0).version;
        StringBuilder titulo = new StringBuilder(aplicables.get(0).nombre);
        for (int i = 1; i < aplicables.size(); i++) {
            titulo.append(" + ").append(aplicables.get(i).nombre
                    .replaceFirst("(?i)^DOCUMENTOS\\s+(DEL\\s+)?(PRODUCTO\\s+TERMINADO\\s+)?", ""));
        }
        resultado.titulo = titulo.toString();
        List<Item> unidos = new ArrayList<Item>();
        for (Seccion seccion : aplicables) {
            resultado.secciones.add(seccion.nombre);
            int ultimaPosicion = -1;
            for (Item item : seccion.items) {
                int existente = -1;
                for (int i = 0; i < unidos.size(); i++) {
                    if (unidos.get(i).key.equals(item.key)) {
                        existente = i;
                        break;
                    }
                }
                if (existente >= 0) {
                    ultimaPosicion = existente;
                    unidos.get(existente).obligatorio |= item.obligatorio;
                } else {
                    ultimaPosicion = ultimaPosicion < 0 ? unidos.size() : ultimaPosicion + 1;
                    if (ultimaPosicion > unidos.size()) {
                        ultimaPosicion = unidos.size();
                    }
                    unidos.add(ultimaPosicion, item);
                }
            }
        }
        for (int i = 0; i < unidos.size(); i++) {
            unidos.get(i).numero = i + 1;
        }
        resultado.items = unidos;

        // 5. Marcas manuales del lote.
        Map<String, Object[]> marcas = new HashMap<String, Object[]>();
        for (Object[] fila : new ChecklistJpaController().consultLoteMarks(cliente, anio, orden, lote)) {
            marcas.put(str(fila[0]), fila);
        }

        // 6. Resumen R-GC-017 (no es un documento del manifiesto: solo se verifica que exista).
        List<String> resumenes = null;
        for (Item item : unidos) {
            if (FUENTE_LAB_RESUMEN.equals(item.fuente)) {
                resumenes = consultarResumenes(orden, lote);
                break;
            }
        }

        // 7. Estado de cada ítem.
        for (Item item : unidos) {
            if (BatchRecordManifest.FUENTE_LAB_REGISTRO.equals(item.fuente)
                    || BatchRecordManifest.FUENTE_LAB_DESPEJE.equals(item.fuente)) {
                for (Map<String, Object> doc : documentos) {
                    if (item.fuente.equals(doc.get("fuente"))
                            && (item.codigoFuente.isEmpty() || expandirCodigos(str(doc.get("codigo"))).contains(item.codigoFuente))) {
                        item.documentos.add(doc);
                    }
                }
                if (!item.documentos.isEmpty()) {
                    item.detalle = item.documentos.size() == 1 ? "1 registro en Registros LAB"
                            : item.documentos.size() + " registros en Registros LAB";
                }
            } else if (BatchRecordManifest.FUENTE_COA_CERTIFICADO.equals(item.fuente)
                    || BatchRecordManifest.FUENTE_GL_ANEXO.equals(item.fuente)) {
                for (Map<String, Object> doc : documentos) {
                    if (item.fuente.equals(doc.get("fuente"))
                            && (item.codigoFuente.isEmpty() || normalizarCodigo(str(doc.get("codigo"))).equals(item.codigoFuente))) {
                        item.documentos.add(doc);
                    }
                }
                if (!item.documentos.isEmpty()) {
                    boolean uno = item.documentos.size() == 1;
                    item.detalle = item.documentos.size() + " " + (BatchRecordManifest.FUENTE_COA_CERTIFICADO.equals(item.fuente)
                            ? (uno ? "certificado COA firmado" : "certificados COA firmados")
                            : (uno ? "anexo de Generación de Lotes" : "anexos de Generación de Lotes"));
                }
            }
            boolean resumenEncontrado = FUENTE_LAB_RESUMEN.equals(item.fuente) && resumenes != null && !resumenes.isEmpty();
            if (resumenEncontrado) {
                item.detalle = "Resumen en Registros LAB: " + join(resumenes);
            }
            if (!item.documentos.isEmpty() || resumenEncontrado) {
                item.estado = ESTADO_SISTEMA;
            }

            Object[] marca = marcas.get(item.key);
            if (marca != null) {
                item.marcaManual = true;
                item.usuario = str(marca[5]);
                item.fecha = str(marca[6]);
                item.observacion = str(marca[4]);
                Long idFile = marca[3] instanceof Number ? ((Number) marca[3]).longValue() : null;
                Map<String, Object> archivo = idFile != null ? archivosLote.get(idFile) : null;
                if (archivo != null) {
                    item.idFile = idFile;
                    item.nombreArchivo = str(archivo.get("nombre"));
                    if (!item.documentos.contains(archivo)) {
                        item.documentos.add(archivo);
                    }
                }
                if (!ESTADO_SISTEMA.equals(item.estado)) {
                    String status = str(marca[2]);
                    if (ESTADO_NA.equals(status)) {
                        item.estado = ESTADO_NA;
                    } else if (idFile != null && archivo == null) {
                        // Se adjuntó un archivo y luego se eliminó del lote: ya no hay evidencia.
                        item.estado = ESTADO_PENDIENTE;
                        item.detalle = "El archivo adjunto fue eliminado del lote.";
                    } else {
                        item.estado = ESTADO_VERIFICADO;
                    }
                }
            }
        }

        resultado.total = unidos.size();
        for (Item item : unidos) {
            if (item.isPendiente()) {
                if (item.obligatorio) {
                    resultado.pendientes++;
                }
            } else {
                resultado.verificados++;
            }
        }
        return resultado;
    }

    /**
     * Reordena los documentos del Batch Record según el consecutivo de la
     * lista (los que no pertenecen a ningún ítem van al final, en su orden
     * original) y deja en cada uno la clave "itemChecklist" con el número
     * del ítem al que respalda.
     */
    public static List<Map<String, Object>> ordenarDocumentos(Resultado resultado, List<Map<String, Object>> documentos) {
        if (resultado == null || !resultado.aplica || documentos == null) {
            return documentos;
        }
        IdentityHashMap<Map<String, Object>, Boolean> enManifiesto = new IdentityHashMap<Map<String, Object>, Boolean>();
        for (Map<String, Object> doc : documentos) {
            enManifiesto.put(doc, Boolean.TRUE);
        }
        IdentityHashMap<Map<String, Object>, Boolean> agregados = new IdentityHashMap<Map<String, Object>, Boolean>();
        List<Map<String, Object>> ordenados = new ArrayList<Map<String, Object>>();
        for (Item item : resultado.items) {
            for (Map<String, Object> doc : item.documentos) {
                if (enManifiesto.containsKey(doc) && !agregados.containsKey(doc)) {
                    doc.put("itemChecklist", item.numero);
                    ordenados.add(doc);
                    agregados.put(doc, Boolean.TRUE);
                }
            }
        }
        for (Map<String, Object> doc : documentos) {
            if (!agregados.containsKey(doc)) {
                ordenados.add(doc);
            }
        }
        return ordenados;
    }

    /**
     * Número de ítem de la lista para cada documento del lote, con la misma
     * regla que ordenarDocumentos (un documento queda en el primer ítem que lo
     * respalda). Llave = claveArchivo / claveAnexo / claveUrl, para que
     * FileManager.jsp ordene sus filas igual que el PDF unificado.
     */
    public static Map<String, Integer> indicePorDocumento(Resultado resultado) {
        Map<String, Integer> indice = new HashMap<String, Integer>();
        if (resultado == null || !resultado.aplica) {
            return indice;
        }
        for (Item item : resultado.items) {
            for (Map<String, Object> doc : item.documentos) {
                String clave = claveDocumento(doc);
                if (clave != null && !indice.containsKey(clave)) {
                    indice.put(clave, item.numero);
                }
            }
        }
        return indice;
    }

    public static String claveArchivo(long idFile) {
        return "FILE|" + idFile;
    }

    public static String claveAnexo(String archivo) {
        return "GL|" + (archivo == null ? "" : archivo.trim());
    }

    public static String claveUrl(String url) {
        return "URL|" + (url == null ? "" : url.trim());
    }

    private static String claveDocumento(Map<String, Object> doc) {
        if (doc.get("idFile") instanceof Number) {
            return claveArchivo(((Number) doc.get("idFile")).longValue());
        }
        if (BatchRecordManifest.FUENTE_GL_ANEXO.equals(doc.get("fuente"))) {
            return claveAnexo(str(doc.get("archivo")));
        }
        Object url = doc.get("url");
        return url == null ? null : claveUrl(String.valueOf(url));
    }

    /** "R-PRF-001|orden de trabajo": código + nombre sin tildes, minúsculas, espacios simples. */
    public static String clave(String codigoRegistro, String nombre) {
        String k = normalizarTexto(codigoRegistro) + "|" + normalizarTexto(nombre);
        return k.length() > 255 ? k.substring(0, 255) : k;
    }

    /** "R-PRF-011/13" (tipo de línea "Todos") → {R-PRF-011, R-PRF-013}. */
    static Set<String> expandirCodigos(String raw) {
        Set<String> codigos = new HashSet<String>();
        if (raw == null) {
            return codigos;
        }
        String[] partes = raw.split("/");
        String base = normalizarCodigo(partes[0]);
        if (base.isEmpty()) {
            return codigos;
        }
        codigos.add(base);
        int guion = base.lastIndexOf('-');
        String prefijo = guion >= 0 ? base.substring(0, guion + 1) : "";
        for (int i = 1; i < partes.length; i++) {
            String p = normalizarCodigo(partes[i]);
            if (p.matches("\\d+")) {
                while (p.length() < 3) {
                    p = "0" + p;
                }
                codigos.add(prefijo + p);
            } else if (!p.isEmpty()) {
                codigos.add(p);
            }
        }
        return codigos;
    }

    static String normalizarCodigo(String codigo) {
        return codigo == null ? "" : codigo.trim().toUpperCase().replaceAll("\\s+", "");
    }

    private static String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        String sinTildes = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return sinTildes.trim().toLowerCase().replaceAll("\\s+", " ");
    }

    /** Resúmenes R-GC-017 del lote en Registros LAB ("certificado - línea"); null si falló la consulta. */
    private static List<String> consultarResumenes(String orden, String lote) {
        try {
            List lst = new LinkBatchRecord().RGC17BatchRecord(orden, lote);
            if (lst == null) {
                return null;
            }
            List<String> resumenes = new ArrayList<String>();
            for (Object fila : lst) {
                String[] arg = Util.parseResult(fila);
                resumenes.add(arg.length > 13 ? arg[13].trim() : "R-GC-017");
            }
            return resumenes;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "No se pudo consultar el resumen R-GC-017 del lote " + lote, ex);
            return null;
        }
    }

    /** "[R-GC-046][R-GC-220]" → {R-GC-046, R-GC-220}. */
    private static List<String> lista(String raw) {
        List<String> valores = new ArrayList<String>();
        if (raw == null) {
            return valores;
        }
        for (String v : raw.replace("][", ",").replace("[", "").replace("]", "").split(",")) {
            String n = normalizarCodigo(v);
            if (!n.isEmpty()) {
                valores.add(n);
            }
        }
        return valores;
    }

    private static String join(List<String> valores) {
        StringBuilder sb = new StringBuilder();
        for (String v : valores) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(v);
        }
        return sb.toString();
    }

    private static String str(Object o) {
        return o == null ? "" : String.valueOf(o).trim();
    }

    private static int toInt(Object o) {
        if (o instanceof Number) {
            return ((Number) o).intValue();
        }
        try {
            return Integer.parseInt(str(o));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private static class Seccion {

        String codigoRegistro;
        int version;
        String nombre;
        List<String> formatos;
        List<String> disparadores;
        List<Item> items = new ArrayList<Item>();
    }
}
