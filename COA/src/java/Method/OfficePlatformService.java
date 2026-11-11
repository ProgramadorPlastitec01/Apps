// =========================================================================================
// UBICACIÓN EN NETBEANS: Source Packages -> Metodos -> OfficePlatformService.java
// Cero dependencias externas: Compatible con Java 7, 8, 11, 17, 21 (Nativo HttpURLConnection)
// =========================================================================================
package Method;

import Controller.SettingJpaController;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.nio.charset.StandardCharsets;

public class OfficePlatformService {

    /**
     * Categoria de la tabla Setting con la configuracion del servicio.
     * Formato del value: [http://IP:PUERTO][API_KEY]
     */
    public static final String SETTING_CATEGORIE = "ServerOfficePlatform";

    // Valores por defecto (solo si la tabla Setting no tiene la categoria).
    private static final String DEFAULT_URL = "http://172.16.1.166:8080";
    private static final String DEFAULT_API_KEY = "opk_i0ss8GlbDqYFBNwCOkK7Dh_xfs4RHXYM_FbYPucENKA";

    /**
     * Se mantiene publico por compatibilidad; usar {@link #getServerUrl()}
     * para garantizar que la configuracion de Setting ya este cargada.
     */
    public static volatile String SERVER_URL = DEFAULT_URL;
    private static volatile String API_KEY = DEFAULT_API_KEY;
    private static volatile String CONFIG_ORIGEN = "Valor por defecto (no existe la categoria en Setting)";

    // Se relee la configuracion de Setting cada 5 minutos, asi un cambio de
    // IP/API key en la tabla aplica sin reiniciar Tomcat.
    private static final long CONFIG_TTL_MS = 5 * 60 * 1000L;
    private static volatile long configCargadaEn = 0L;

    // Cache en memoria de carpetas ya resueltas/creadas (clave = ruta completa,
    // ej. "COA/ClienteX/2026/OC-123/L-45") para no repetir /api/folders/search
    // en cada archivo subido al mismo lote. El listado plano de la API
    // (GET /api/folders, /api/files, /api/files/all) no devuelve resultados
    // pase lo que pase (bug/limitación confirmada en pruebas manuales), así
    // que la única forma fiable de encontrar una carpeta existente es
    // /api/folders/search?q=nombre.
    private static final Map<String, Long> FOLDER_CACHE = new ConcurrentHashMap<String, Long>();

    /**
     * Carga la configuracion desde la tabla Setting (categoria
     * {@link #SETTING_CATEGORIE}). Si no existe o falla la consulta, conserva
     * los valores actuales.
     */
    public static synchronized void recargarConfiguracion() {
        configCargadaEn = System.currentTimeMillis();
        try {
            List lst = new SettingJpaController().ConsultSettingCategorie(SETTING_CATEGORIE);
            if (lst == null) {
                return;
            }
            for (Object fila : lst) {
                Object[] obj = (Object[]) fila;
                // Solo registros activos (state = 1)
                if (obj.length > 4 && obj[4] != null
                        && !"1".equals(obj[4].toString()) && !"true".equalsIgnoreCase(obj[4].toString())) {
                    continue;
                }
                if (obj[2] == null) {
                    continue;
                }
                String[] arr = obj[2].toString().trim().replace("][", "///").replace("[", "").replace("]", "").split("///");
                String url = arr.length > 0 ? normalizarUrl(arr[0]) : "";
                String key = arr.length > 1 ? arr[1].trim() : "";
                if (url.isEmpty()) {
                    continue;
                }
                String anterior = SERVER_URL;
                SERVER_URL = url;
                if (!key.isEmpty()) {
                    API_KEY = key;
                }
                CONFIG_ORIGEN = "Setting (" + SETTING_CATEGORIE + ")";
                if (!url.equals(anterior)) {
                    // Los folderId cacheados pertenecen al servidor anterior.
                    FOLDER_CACHE.clear();
                }
                return;
            }
        } catch (Exception ex) {
            System.err.println("[OfficePlatformService] No se pudo leer la configuracion de Setting: " + ex.getMessage());
        }
    }

    private static void asegurarConfiguracion() {
        if (System.currentTimeMillis() - configCargadaEn > CONFIG_TTL_MS) {
            recargarConfiguracion();
        }
    }

    private static String normalizarUrl(String url) {
        String u = url == null ? "" : url.trim();
        while (u.endsWith("/")) {
            u = u.substring(0, u.length() - 1);
        }
        if (!u.isEmpty() && !u.toLowerCase().startsWith("http://") && !u.toLowerCase().startsWith("https://")) {
            u = "http://" + u;
        }
        return u;
    }

    public static String getServerUrl() {
        asegurarConfiguracion();
        return SERVER_URL;
    }

    public static String getApiKey() {
        asegurarConfiguracion();
        return API_KEY;
    }

    public static String getConfigOrigen() {
        asegurarConfiguracion();
        return CONFIG_ORIGEN;
    }

    /**
     * Sobreescribe la configuracion manualmente (pruebas). En operacion
     * normal la fuente es la tabla Setting.
     */
    public static synchronized void init(String serverUrl, String apiKey) {
        if (serverUrl != null && !serverUrl.trim().isEmpty()) {
            SERVER_URL = normalizarUrl(serverUrl);
            FOLDER_CACHE.clear();
        }
        if (apiKey != null && !apiKey.trim().isEmpty()) {
            API_KEY = apiKey.trim();
        }
        CONFIG_ORIGEN = "Manual (init)";
        configCargadaEn = System.currentTimeMillis();
    }

    // =====================================================================
    // VERIFICACION DEL SERVICIO
    // =====================================================================

    public static class Verificacion {

        public String serverUrl;
        public String origen;
        public boolean servidorOk;      // responde la API
        public boolean apiKeyOk;        // la API key es aceptada (auth/resolve)
        public boolean widgetOk;        // se descarga office-platform-widget.js
        public String detalle = "";
        public long tiempoMs;

        public boolean isOk() {
            return servidorOk && apiKeyOk && widgetOk;
        }
    }

    /**
     * Verifica en tres pasos que el editor pueda funcionar: el servidor
     * responde, la API key es valida (se obtiene un widgetToken) y el script
     * del widget es descargable (es lo que el navegador carga para pintar el
     * editor).
     */
    public static Verificacion verificar() {
        long inicio = System.currentTimeMillis();
        recargarConfiguracion();
        Verificacion v = new Verificacion();
        v.serverUrl = SERVER_URL;
        v.origen = CONFIG_ORIGEN;
        StringBuilder det = new StringBuilder();

        // 1. Servidor
        try {
            int code = codigoHttp(SERVER_URL + "/api/folders/search?q=__healthcheck__", true);
            v.servidorOk = code >= 200 && code < 500;
            det.append("Servidor: HTTP ").append(code);
            if (code == 401 || code == 403) {
                det.append(" (API key rechazada)");
            }
        } catch (Exception ex) {
            det.append("Servidor: sin respuesta (").append(ex.getMessage()).append(")");
        }

        // 2. API key / token del widget
        if (v.servidorOk) {
            String token = obtenerToken("__healthcheck__", "Verificacion COA");
            v.apiKeyOk = token != null && !token.isEmpty();
            det.append(" | API key: ").append(v.apiKeyOk ? "valida" : "no se obtuvo token (verifique la API key)");
        }

        // 3. Script del widget del editor
        try {
            int code = codigoHttp(SERVER_URL + "/office-platform-widget.js", false);
            v.widgetOk = code >= 200 && code < 300;
            det.append(" | Widget editor: HTTP ").append(code);
        } catch (Exception ex) {
            det.append(" | Widget editor: sin respuesta (").append(ex.getMessage()).append(")");
        }

        v.detalle = det.toString();
        v.tiempoMs = System.currentTimeMillis() - inicio;
        return v;
    }

    private static int codigoHttp(String direccion, boolean conApiKey) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(direccion).openConnection();
        conn.setRequestMethod("GET");
        if (conApiKey) {
            conn.setRequestProperty("X-Api-Key", API_KEY);
        }
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(6000);
        int code = conn.getResponseCode();
        try {
            leerStream(code >= 200 && code < 400 ? conn.getInputStream() : conn.getErrorStream());
        } catch (Exception ignore) {
            // solo interesa el codigo HTTP
        }
        return code;
    }

    /**
     * Resuelve el token seguro (JWT) para el usuario activo.
     *
     * @param cedula Identificador único (Cédula, documento o username)
     * @param nombre Nombre visible para autoría y comentarios
     * @return widgetToken firmado para incrustar en el front
     */
    public static String obtenerToken(String cedula, String nombre) {
        if (cedula == null || cedula.trim().isEmpty()) {
            cedula = "usr_anonimo";
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            nombre = "Usuario " + cedula;
        }
        try {
            URL url = new URL(getServerUrl() + "/api/auth/resolve");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("X-Api-Key", getApiKey());
            conn.setConnectTimeout(4000);
            conn.setReadTimeout(6000);
            conn.setDoOutput(true);

            String safeCedula = (cedula != null && !cedula.trim().isEmpty()) ? cedula.replace('"', ' ') : "usr_anonimo";
            String safeNombre = (nombre != null && !nombre.trim().isEmpty()) ? nombre.replace('"', ' ') : "Usuario " + safeCedula;
            String jsonBody = "{\"cedula\":\"" + safeCedula + "\",\"nombre\":\"" + safeNombre + "\"}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes("UTF-8"));
            }

            if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    String res = sb.toString();
                    int idx = res.indexOf("\"widgetToken\":\"");
                    if (idx != -1) {
                        int start = idx + 15;
                        int end = res.indexOf("\"", start);
                        return res.substring(start, end);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("[OfficePlatformService] Error al obtener token: " + ex.getMessage());
        }
        return "";
    }

    // =====================================================================
    // ALMACENAMIENTO DE ARCHIVOS (Gestor de Archivos / MinIO vía Office
    // Platform). Todo el binario vive del lado del gestor: estos métodos
    // solo hacen streaming HTTP, nunca escriben a disco local.
    // =====================================================================

    /**
     * Resuelve (o crea si no existe) la cadena de carpetas indicada,
     * anidando cada segmento dentro del anterior (ej. "COA", cliente, anio,
     * orden, lote). Devuelve el folderId de la última carpeta de la cadena.
     */
    public static long resolveOrCreateFolderPath(String... segments) throws IOException {
        Long parentId = null;
        StringBuilder pathKey = new StringBuilder();

        for (String segment : segments) {
            if (segment == null || segment.trim().isEmpty()) {
                continue;
            }
            if (pathKey.length() > 0) {
                pathKey.append("/");
            }
            pathKey.append(segment);

            Long cached = FOLDER_CACHE.get(pathKey.toString());
            if (cached == null) {
                cached = resolveOrCreateFolder(parentId, segment);
                FOLDER_CACHE.put(pathKey.toString(), cached);
            }
            parentId = cached;
        }

        if (parentId == null) {
            throw new IOException("No se recibió ningún segmento de carpeta válido para resolver la ruta.");
        }
        return parentId;
    }

    private static long resolveOrCreateFolder(Long parentId, String name) throws IOException {
        Long existente = buscarCarpeta(parentId, name);
        if (existente != null) {
            return existente;
        }
        return crearCarpeta(parentId, name);
    }

    private static Long buscarCarpeta(Long parentId, String name) throws IOException {
        URL url = new URL(getServerUrl() + "/api/folders/search?q=" + URLEncoder.encode(name, "UTF-8"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Api-Key", getApiKey());
        conn.setConnectTimeout(6000);
        conn.setReadTimeout(10000);

        int code = conn.getResponseCode();
        if (code < 200 || code >= 300) {
            return null;
        }

        JsonObject json = JsonParser.parseString(leerStream(conn.getInputStream())).getAsJsonObject();
        JsonArray data = json.has("data") && json.get("data").isJsonArray() ? json.getAsJsonArray("data") : null;
        if (data == null) {
            return null;
        }

        for (JsonElement el : data) {
            JsonObject folder = el.getAsJsonObject();
            String folderName = folder.has("name") && !folder.get("name").isJsonNull() ? folder.get("name").getAsString() : null;
            Long folderParentId = folder.has("parentId") && !folder.get("parentId").isJsonNull() ? folder.get("parentId").getAsLong() : null;

            boolean mismoNombre = name.equals(folderName);
            boolean mismoParent = (parentId == null && folderParentId == null)
                    || (parentId != null && parentId.equals(folderParentId));

            if (mismoNombre && mismoParent) {
                return folder.get("id").getAsLong();
            }
        }
        return null;
    }

    private static long crearCarpeta(Long parentId, String name) throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("name", name);
        if (parentId != null) {
            body.addProperty("parentId", parentId);
        }

        URL url = new URL(getServerUrl() + "/api/folders");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-Api-Key", getApiKey());
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setConnectTimeout(6000);
        conn.setReadTimeout(10000);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(new Gson().toJson(body).getBytes(StandardCharsets.UTF_8));
        }

        int code = conn.getResponseCode();
        String respBody = leerStream(code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream());
        if (code < 200 || code >= 300) {
            throw new IOException("Error creando carpeta '" + name + "' en Office Platform (HTTP " + code + "): " + respBody);
        }

        JsonObject json = JsonParser.parseString(respBody).getAsJsonObject();
        return json.getAsJsonObject("data").get("id").getAsLong();
    }

    /**
     * Sube el contenido de {@code contenido} (se consume por streaming, no
     * se materializa en disco) como un archivo nuevo dentro de la carpeta
     * {@code folderId}.
     */
    public static OfficeUploadResult subirArchivo(long folderId, String originalFileName, String description,
            InputStream contenido, String mimeType, String userId, String userName) throws IOException {

        String boundary = "----COABoundary" + System.currentTimeMillis();
        String LINE = "\r\n";

        StringBuilder query = new StringBuilder("/api/files/upload?folderId=").append(folderId);
        if (userId != null && !userId.trim().isEmpty()) {
            query.append("&userId=").append(URLEncoder.encode(userId, "UTF-8"));
        }
        if (userName != null && !userName.trim().isEmpty()) {
            query.append("&userName=").append(URLEncoder.encode(userName, "UTF-8"));
        }

        URL url = new URL(getServerUrl() + query.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-Api-Key", getApiKey());
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setDoOutput(true);
        conn.setChunkedStreamingMode(64 * 1024);
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(60000);

        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("originalFileName", originalFileName);
        if (description != null && !description.trim().isEmpty()) {
            requestJson.addProperty("description", description);
        }
        String requestPartJson = new Gson().toJson(requestJson);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(("--" + boundary + LINE).getBytes(StandardCharsets.UTF_8));
            os.write(("Content-Disposition: form-data; name=\"request\"" + LINE).getBytes(StandardCharsets.UTF_8));
            os.write(("Content-Type: application/json; charset=UTF-8" + LINE + LINE).getBytes(StandardCharsets.UTF_8));
            os.write(requestPartJson.getBytes(StandardCharsets.UTF_8));
            os.write(LINE.getBytes(StandardCharsets.UTF_8));

            os.write(("--" + boundary + LINE).getBytes(StandardCharsets.UTF_8));
            os.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + originalFileName + "\"" + LINE).getBytes(StandardCharsets.UTF_8));
            os.write(("Content-Type: " + (mimeType != null && !mimeType.trim().isEmpty() ? mimeType : "application/octet-stream") + LINE + LINE).getBytes(StandardCharsets.UTF_8));

            byte[] buffer = new byte[8192];
            int leidos;
            while ((leidos = contenido.read(buffer)) != -1) {
                os.write(buffer, 0, leidos);
            }
            os.write(LINE.getBytes(StandardCharsets.UTF_8));

            os.write(("--" + boundary + "--" + LINE).getBytes(StandardCharsets.UTF_8));
        }

        int code = conn.getResponseCode();
        String body = leerStream(code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream());
        if (code < 200 || code >= 300) {
            throw new IOException("Error subiendo archivo '" + originalFileName + "' a Office Platform (HTTP " + code + "): " + body);
        }

        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        JsonObject data = json.getAsJsonObject("data");

        OfficeUploadResult resultado = new OfficeUploadResult();
        resultado.fileId = data.get("fileId").getAsLong();
        resultado.uuid = data.has("uuid") && !data.get("uuid").isJsonNull() ? data.get("uuid").getAsString() : null;
        resultado.mimeType = data.has("mimeType") && !data.get("mimeType").isJsonNull() ? data.get("mimeType").getAsString() : mimeType;
        resultado.size = data.has("size") && !data.get("size").isJsonNull() ? data.get("size").getAsLong() : -1L;
        return resultado;
    }

    /**
     * Descarga el archivo {@code fileId} desde Office Platform. El
     * {@link InputStream} devuelto viene directo de la conexión HTTP: el
     * llamador debe copiarlo a la respuesta y cerrarlo, sin pasar por disco.
     */
    public static OfficeDownload descargarArchivo(long fileId) throws IOException {
        URL url = new URL(getServerUrl() + "/api/files/" + fileId + "/download");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Api-Key", getApiKey());
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(60000);

        int code = conn.getResponseCode();
        if (code < 200 || code >= 300) {
            throw new IOException("Error descargando archivo " + fileId + " de Office Platform (HTTP " + code + ")");
        }

        OfficeDownload descarga = new OfficeDownload();
        descarga.contentType = conn.getContentType();
        descarga.contentLength = conn.getContentLengthLong();
        descarga.inputStream = conn.getInputStream();
        return descarga;
    }

    /**
     * Envía el archivo {@code fileId} a la papelera del gestor (soft-delete,
     * recuperable desde la administración de Office Platform).
     *
     * IMPORTANTE: {@code userId} debe ser el mismo valor con el que se subió
     * el archivo (columna uploaded_by_id de certificate_files). Comprobado en
     * vivo: si se omite (o no coincide), la API responde 200 "éxito" pero no
     * borra nada — el archivo sigue activo y descargable. Sin userId, el
     * llamador queda resuelto a una identidad anónima distinta de la dueña
     * real del archivo, así que la operación no tiene sobre qué actuar.
     */
    public static boolean eliminarArchivo(long fileId, String userId, String userName) throws IOException {
        return llamarBorradoArchivo(getServerUrl() + "/api/files/" + fileId, userId, userName);
    }

    /**
     * Borrado definitivo (sin pasar por la papelera). Se usa cuando un
     * archivo queda reemplazado por otro y no debe seguir apareciendo en el
     * listado de "Archivos Activos" del gestor (ej. el pendiente de firma
     * una vez que ya se subió el firmado). Mismo requisito de {@code userId}
     * que {@link #eliminarArchivo(long, String, String)}.
     */
    public static boolean purgarArchivo(long fileId, String userId, String userName) throws IOException {
        return llamarBorradoArchivo(getServerUrl() + "/api/files/" + fileId + "/purge", userId, userName);
    }

    private static boolean llamarBorradoArchivo(String baseUrl, String userId, String userName) throws IOException {
        StringBuilder url = new StringBuilder(baseUrl);
        String separador = "?";
        if (userId != null && !userId.trim().isEmpty()) {
            url.append(separador).append("userId=").append(URLEncoder.encode(userId, "UTF-8"));
            separador = "&";
        }
        if (userName != null && !userName.trim().isEmpty()) {
            url.append(separador).append("userName=").append(URLEncoder.encode(userName, "UTF-8"));
        }

        HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("X-Api-Key", getApiKey());
        conn.setConnectTimeout(6000);
        conn.setReadTimeout(10000);
        int code = conn.getResponseCode();
        return code >= 200 && code < 300;
    }

    private static String leerStream(InputStream is) throws IOException {
        if (is == null) {
            return "";
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }

    public static class OfficeUploadResult {
        public long fileId;
        public String uuid;
        public String mimeType;
        public long size;
    }

    public static class OfficeDownload {
        public InputStream inputStream;
        public String contentType;
        public long contentLength;
    }
}

// =========================================================================================
// CONFIGURACION: tabla setting, categorie = 'ServerOfficePlatform',
// value = '[http://172.16.1.166:8080][API_KEY]'. Se relee cada 5 minutos.
// Uso: OfficePlatformService.getServerUrl(), obtenerToken(cedula, nombre), verificar().
// =========================================================================================
