package Method;

import Controller.SignatureHistoryJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Registro del historial de firmas (requerimiento 1.1.U03): quién, cuándo y
 * desde dónde (IP, aplicativo, navegador/equipo) se registró, finalizó o
 * firmó un Certificado, un Documento de soporte o un Batch Record.
 *
 * Registrar el historial NUNCA debe tumbar la operación principal (firmar,
 * finalizar...): cualquier error se registra en el log y se ignora.
 */
public final class SignatureHistory {

    public static final String TIPO_CERTIFICADO = "CERTIFICADO";
    public static final String TIPO_SOPORTE = "SOPORTE";
    public static final String TIPO_BATCH_RECORD = "BATCH_RECORD";

    public static final String EVENTO_REGISTRADO = "REGISTRADO";
    public static final String EVENTO_FINALIZADO = "FINALIZADO";
    public static final String EVENTO_FIRMADO = "FIRMADO";

    /** Nombre del aplicativo que queda en el historial ("desde qué aplicativo"). */
    public static final String APLICATIVO = "COA - Certificados de Calidad";

    private SignatureHistory() {
    }

    /**
     * Llave estable del Batch Record: el nombre del PDF generado sin ".pdf".
     * El aprobado ("..._FIRMADO_{usuario}_{fecha}.pdf") es un archivo nuevo,
     * pero conserva ese mismo prefijo.
     */
    public static String batchRecordKey(String fileName) {
        if (fileName == null) {
            return "";
        }
        int firmado = fileName.indexOf("_FIRMADO_");
        if (firmado > 0) {
            return fileName.substring(0, firmado);
        }
        return fileName.toLowerCase().endsWith(".pdf") ? fileName.substring(0, fileName.length() - 4) : fileName;
    }

    public static void registrar(HttpServletRequest request, String tipo, String llave, Integer idCertificate,
            String nombreDocumento, String cliente, String anio, String orden, String lote,
            String evento, String detalle) {
        try {
            HttpSession session = request.getSession(false);
            Integer idUser = null;
            String documento = null, nombre = null, rol = null;
            if (session != null) {
                try {
                    idUser = Integer.valueOf(session.getAttribute("idUsuario").toString());
                } catch (Exception e) {
                    idUser = null;
                }
                documento = attr(session, "Documento");
                nombre = attr(session, "Nombres");
                rol = attr(session, "NombreRol");
            }
            new SignatureHistoryJpaController().register(tipo, llave, idCertificate, cortar(nombreDocumento, 500),
                    cortar(cliente, 300), cortar(anio, 20), cortar(orden, 100), cortar(lote, 100),
                    evento, cortar(detalle, 500), idUser, documento, nombre, rol,
                    cortar(clientIp(request), 64), APLICATIVO, cortar(request.getHeader("User-Agent"), 500));
        } catch (Exception ex) {
            Logger.getLogger(SignatureHistory.class.getName()).log(Level.WARNING,
                    "No se pudo registrar el historial de firmas (" + tipo + " " + llave + " " + evento + ")", ex);
        }
    }

    /** IP del cliente; si COA queda detrás de un proxy, la primera de X-Forwarded-For. */
    public static String clientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.trim().isEmpty()) {
            return forwarded.split(",")[0].trim();
        }
        String ip = request.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            return "127.0.0.1";
        }
        return ip;
    }

    /** "Chrome 130 · Windows 10/11" a partir del User-Agent, para mostrar en el historial. */
    public static String describirEquipo(String userAgent) {
        if (userAgent == null || userAgent.trim().isEmpty()) {
            return "";
        }
        String ua = userAgent;
        String navegador;
        if (ua.contains("Edg/")) {
            navegador = "Edge " + version(ua, "Edg/");
        } else if (ua.contains("OPR/")) {
            navegador = "Opera " + version(ua, "OPR/");
        } else if (ua.contains("Firefox/")) {
            navegador = "Firefox " + version(ua, "Firefox/");
        } else if (ua.contains("Chrome/")) {
            navegador = "Chrome " + version(ua, "Chrome/");
        } else if (ua.contains("Safari/") && ua.contains("Version/")) {
            navegador = "Safari " + version(ua, "Version/");
        } else if (ua.contains("Trident/") || ua.contains("MSIE")) {
            navegador = "Internet Explorer";
        } else {
            navegador = "Otro navegador";
        }

        String sistema;
        if (ua.contains("Windows NT 10.0")) {
            sistema = "Windows 10/11";
        } else if (ua.contains("Windows NT 6.3")) {
            sistema = "Windows 8.1";
        } else if (ua.contains("Windows NT 6.1")) {
            sistema = "Windows 7";
        } else if (ua.contains("Windows")) {
            sistema = "Windows";
        } else if (ua.contains("Android")) {
            sistema = "Android";
        } else if (ua.contains("iPhone") || ua.contains("iPad")) {
            sistema = "iOS";
        } else if (ua.contains("Mac OS X")) {
            sistema = "macOS";
        } else if (ua.contains("Linux")) {
            sistema = "Linux";
        } else {
            sistema = "";
        }
        return sistema.isEmpty() ? navegador : navegador + " · " + sistema;
    }

    private static String version(String ua, String marca) {
        int i = ua.indexOf(marca);
        if (i < 0) {
            return "";
        }
        String resto = ua.substring(i + marca.length());
        int fin = 0;
        while (fin < resto.length() && Character.isDigit(resto.charAt(fin))) {
            fin++;
        }
        return resto.substring(0, fin);
    }

    private static String attr(HttpSession session, String nombre) {
        Object v = session.getAttribute(nombre);
        return v == null ? null : v.toString();
    }

    private static String cortar(String valor, int max) {
        if (valor == null) {
            return null;
        }
        return valor.length() > max ? valor.substring(0, max) : valor;
    }
}
