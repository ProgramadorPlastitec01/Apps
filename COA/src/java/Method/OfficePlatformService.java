// =========================================================================================
// UBICACIÓN EN NETBEANS: Source Packages -> Metodos -> OfficePlatformService.java
// Cero dependencias externas: Compatible con Java 7, 8, 11, 17, 21 (Nativo HttpURLConnection)
// =========================================================================================
package Method;

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
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.nio.charset.StandardCharsets;

public class OfficePlatformService {

    public static String SERVER_URL = "http://172.16.1.182:8080";
    private static String API_KEY = "opk_VjxckrMO0CmPWAL1-Lfre_V4M_MejXshbU4Lgs6cltY";

    // Cache en memoria de carpetas ya resueltas/creadas (clave = ruta completa,
    // ej. "COA/ClienteX/2026/OC-123/L-45") para no repetir /api/folders/search
    // en cada archivo subido al mismo lote. El listado plano de la API
    // (GET /api/folders, /api/files, /api/files/all) no devuelve resultados
    // pase lo que pase (bug/limitación confirmada en pruebas manuales), así
    // que la única forma fiable de encontrar una carpeta existente es
    // /api/folders/search?q=nombre.
    private static final Map<String, Long> FOLDER_CACHE = new ConcurrentHashMap<String, Long>();

    static {
        try (InputStream is = OfficePlatformService.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                Properties prop = new Properties();
                prop.load(is);
                if (prop.getProperty("OFFICE_PLATFORM_URL") != null) {
                    SERVER_URL = prop.getProperty("OFFICE_PLATFORM_URL").trim();
                }
                if (prop.getProperty("OFFICE_PLATFORM_API_KEY") != null) {
                    API_KEY = prop.getProperty("OFFICE_PLATFORM_API_KEY").trim();
                }
            }
        } catch (Exception e) {
            // Si no existe config.properties, conserva las URLs por defecto
        }
    }

    /**
     * Inicializa o sobreescribe la configuracion (util si se lee desde web.xml
     * en un Servlet o JSP).
     */
    public static void init(String serverUrl, String apiKey) {
        if (serverUrl != null && !serverUrl.trim().isEmpty()) {
            SERVER_URL = serverUrl.trim();
        }
        if (apiKey != null && !apiKey.trim().isEmpty()) {
            API_KEY = apiKey.trim();
        }
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
            URL url = new URL(SERVER_URL + "/api/auth/resolve");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("X-Api-Key", API_KEY);
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
        URL url = new URL(SERVER_URL + "/api/folders/search?q=" + URLEncoder.encode(name, "UTF-8"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Api-Key", API_KEY);
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

        URL url = new URL(SERVER_URL + "/api/folders");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-Api-Key", API_KEY);
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

        URL url = new URL(SERVER_URL + query.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-Api-Key", API_KEY);
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
        URL url = new URL(SERVER_URL + "/api/files/" + fileId + "/download");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Api-Key", API_KEY);
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
        return llamarBorradoArchivo(SERVER_URL + "/api/files/" + fileId, userId, userName);
    }

    /**
     * Borrado definitivo (sin pasar por la papelera). Se usa cuando un
     * archivo queda reemplazado por otro y no debe seguir apareciendo en el
     * listado de "Archivos Activos" del gestor (ej. el pendiente de firma
     * una vez que ya se subió el firmado). Mismo requisito de {@code userId}
     * que {@link #eliminarArchivo(long, String, String)}.
     */
    public static boolean purgarArchivo(long fileId, String userId, String userName) throws IOException {
        return llamarBorradoArchivo(SERVER_URL + "/api/files/" + fileId + "/purge", userId, userName);
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
        conn.setRequestProperty("X-Api-Key", API_KEY);
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
// CÓMO CONSUMIRLO DESDE UN SERVLET (Source Packages -> Servlets -> TuServlet.java):
// -----------------------------------------------------------------------------------------
// // Si usas web.xml, puedes inicializar los parametros desde ServletContext:
// String ctxUrl = getServletContext().getInitParameter("OFFICE_PLATFORM_URL");
// String ctxKey = getServletContext().getInitParameter("OFFICE_PLATFORM_API_KEY");
// Metodos.OfficePlatformService.init(ctxUrl, ctxKey);
//
// String cedula = (String) session.getAttribute("Documento");
// String nombre = (String) session.getAttribute("Usuario");
// String token  = Metodos.OfficePlatformService.obtenerToken(cedula, nombre);
// request.setAttribute("widgetToken", token);
// request.setAttribute("serverUrl", Metodos.OfficePlatformService.SERVER_URL);
// request.getRequestDispatcher("Reporte.jsp").forward(request, response);
// =========================================================================================
