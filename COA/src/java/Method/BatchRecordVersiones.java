package Method;

import Controller.CertificateFileJpaController;
import Controller.CertificateFileRow;
import Controller.SignatureHistoryJpaController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Versiones del Batch Record unificado de un lote (archivos de la carpeta
 * "BatchRecord" en certificate_files) y el estado vigente del lote:
 * <ul>
 * <li>La versión VIGENTE es la más reciente. Si es un "_FIRMADO_" el lote
 * está APROBADO y la lista de verificación R-GC-094 queda en solo lectura;
 * para corregirla hay que generar un Batch Record nuevo (que pasa a ser la
 * vigente) y volver a firmarlo.</li>
 * <li>Solo la vigente se puede firmar, y solo si se generó con la lista
 * completa (BatchRecordChecklist.esFirmable); las anteriores quedan como
 * REEMPLAZADO (o BORRADOR si se generaron con la lista incompleta).</li>
 * </ul>
 * Lo usan FileManager.jsp (tarjeta de estado, aprobación e historial),
 * BatchRecordSignServlet (solo firma la vigente) y
 * BatchRecordChecklistServlet (no modifica una lista aprobada).
 */
public final class BatchRecordVersiones {

    public static final String APROBADO = "APROBADO";
    public static final String PENDIENTE = "PENDIENTE";
    public static final String BORRADOR = "BORRADOR";
    public static final String REEMPLAZADO = "REEMPLAZADO";
    /** Estado del lote cuando aún no se ha generado ningún Batch Record. */
    public static final String SIN_GENERAR = "SIN_GENERAR";

    private static final Pattern FECHA_NOMBRE = Pattern.compile("_(\\d{8})_(\\d{6})");
    private static final Pattern LISTA_DETALLE = Pattern.compile("R-GC-094: (\\d+/\\d+)");

    private BatchRecordVersiones() {
    }

    public static class Version {

        public CertificateFileRow archivo;
        public String estado;
        public boolean vigente;
        public boolean firmable;
        /** "dd/MM/yyyy HH:mm" de generación (del nombre del archivo). */
        public String generado = "";
        /** Solo aprobados: quién firmó, su cargo y cuándo. */
        public String firmadoPor = "";
        public String cargo = "";
        public String firmadoEl = "";
        /** Estado de la lista con que se generó, ej. "6/9" (vacío si no se sabe). */
        public String lista = "";

        public String getEtiqueta() {
            Matcher m = FECHA_NOMBRE.matcher(archivo.getName());
            return m.find() ? m.group(1) + "_" + m.group(2) : archivo.getName();
        }
    }

    public static class Estado {

        /** Más reciente primero. */
        public List<Version> versiones = new ArrayList<Version>();
        public Version vigente;

        /** APROBADO, PENDIENTE, BORRADOR o SIN_GENERAR. */
        public String getEstado() {
            return vigente == null ? SIN_GENERAR : vigente.estado;
        }

        public boolean isAprobado() {
            return APROBADO.equals(getEstado());
        }

        public Version buscar(long idArchivo) {
            for (Version v : versiones) {
                if (v.archivo.getId() == idArchivo) {
                    return v;
                }
            }
            return null;
        }
    }

    public static Estado consultar(String cliente, String anio, String orden, String lote, boolean listaAplica) {
        List<CertificateFileRow> archivos = new CertificateFileJpaController()
                .consultFilesByLote(cliente, anio, orden, lote, "BatchRecord");
        return consultar(archivos, listaAplica);
    }

    public static Estado consultar(List<CertificateFileRow> archivos, boolean listaAplica) {
        Estado estado = new Estado();
        if (archivos == null || archivos.isEmpty()) {
            return estado;
        }
        List<CertificateFileRow> ordenados = new ArrayList<CertificateFileRow>(archivos);
        Collections.sort(ordenados, new Comparator<CertificateFileRow>() {
            @Override
            public int compare(CertificateFileRow a, CertificateFileRow b) {
                int c = Long.compare(b.lastModified(), a.lastModified());
                return c != 0 ? c : Long.compare(b.getId(), a.getId());
            }
        });

        SignatureHistoryJpaController historial = new SignatureHistoryJpaController();
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (int i = 0; i < ordenados.size(); i++) {
            CertificateFileRow archivo = ordenados.get(i);
            Version v = new Version();
            v.archivo = archivo;
            v.vigente = i == 0;
            v.generado = fechaDelNombre(archivo.getName());
            boolean firmado = archivo.getName().contains("_FIRMADO_");

            List<Object[]> eventos;
            try {
                eventos = historial.consult(SignatureHistory.TIPO_BATCH_RECORD, SignatureHistory.batchRecordKey(archivo.getName()));
            } catch (Exception ex) {
                eventos = new ArrayList<Object[]>();
            }
            boolean generadoCompleto = false;
            for (Object[] evento : eventos) {
                String tipoEvento = str(evento[1]);
                if (SignatureHistory.EVENTO_REGISTRADO.equals(tipoEvento)) {
                    generadoCompleto = str(evento[2]).contains("(completa)");
                    Matcher m = LISTA_DETALLE.matcher(str(evento[2]));
                    if (m.find()) {
                        v.lista = m.group(1);
                    }
                } else if (SignatureHistory.EVENTO_FIRMADO.equals(tipoEvento)) {
                    v.firmadoPor = str(evento[3]);
                    v.cargo = str(evento[4]);
                    String hora = str(evento[9]);
                    v.firmadoEl = str(evento[8]) + (hora.length() >= 5 ? " " + hora.substring(0, 5) : "");
                }
            }
            if (firmado) {
                if (v.firmadoPor.isEmpty()) {
                    v.firmadoPor = archivo.getUploadedByName() == null ? "" : archivo.getUploadedByName();
                }
                if (v.firmadoEl.isEmpty()) {
                    v.firmadoEl = fecha.format(new Date(archivo.lastModified()));
                }
                v.estado = v.vigente ? APROBADO : REEMPLAZADO;
            } else {
                // Misma regla que BatchRecordChecklist.esFirmable, con los eventos ya consultados.
                boolean seFirma = !BatchRecordChecklist.esBorrador(archivo.getName()) && (!listaAplica || generadoCompleto);
                v.firmable = v.vigente && seFirma;
                // Generado antes de la lista de verificación (sin sufijo _BORRADOR ni dato de
                // la lista en el historial): si ya no es el vigente, solo quedó reemplazado.
                boolean anteriorALista = !BatchRecordChecklist.esBorrador(archivo.getName()) && v.lista.isEmpty();
                if (!seFirma && anteriorALista && !v.vigente) {
                    v.estado = REEMPLAZADO;
                } else if (!seFirma) {
                    v.estado = BORRADOR;
                } else {
                    v.estado = v.vigente ? PENDIENTE : REEMPLAZADO;
                }
            }
            estado.versiones.add(v);
        }
        estado.vigente = estado.versiones.get(0);
        return estado;
    }

    /** "BatchRecord_50982_7051-36I23_20261117_121702..." → "17/11/2026 12:17". */
    private static String fechaDelNombre(String nombre) {
        Matcher m = FECHA_NOMBRE.matcher(nombre == null ? "" : nombre);
        if (!m.find()) {
            return "";
        }
        String d = m.group(1), t = m.group(2);
        return d.substring(6, 8) + "/" + d.substring(4, 6) + "/" + d.substring(0, 4) + " " + t.substring(0, 2) + ":" + t.substring(2, 4);
    }

    private static String str(Object o) {
        return o == null ? "" : String.valueOf(o).trim();
    }
}
