// =========================================================================================
// UBICACIÓN EN NETBEANS: Source Packages -> Metodos -> OfficePlatformService.java
// Cero dependencias externas: Compatible con Java 7, 8, 11, 17, 21 (Nativo HttpURLConnection)
// =========================================================================================
package Method;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.nio.charset.StandardCharsets;

public class OfficePlatformService {

    public static String SERVER_URL = "http://172.16.1.182:8080";
    private static String API_KEY = "opk_VjxckrMO0CmPWAL1-Lfre_V4M_MejXshbU4Lgs6cltY";

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
