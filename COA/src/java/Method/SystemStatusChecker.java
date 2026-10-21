package Method;

import Controller.SettingJpaController;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Chequeo de disponibilidad ("ping") de los aplicativos externos con los que
 * se integra COA. Reutiliza el mismo patron de lectura de credenciales de la
 * tabla Setting que ya usan las clases de Connection (categoria ->
 * "[login][password][host:puerto/bd]"), pero sin tocar esas clases: aqui solo
 * se abre y cierra la conexion para medir disponibilidad, no se ejecuta
 * ninguna consulta de negocio.
 */
public class SystemStatusChecker {

    public static class CheckResult {

        public final String nombre;
        public final boolean ok;
        public final String detalle;
        public final long tiempoMs;

        public CheckResult(String nombre, boolean ok, String detalle, long tiempoMs) {
            this.nombre = nombre;
            this.ok = ok;
            this.detalle = detalle;
            this.tiempoMs = tiempoMs;
        }
    }

    private static final SettingJpaController SETTING_JPA = new SettingJpaController();

    public static List<CheckResult> checkAll() {
        List<CheckResult> resultados = new ArrayList<CheckResult>();
        resultados.add(checkMySql("Registros LAB", "ServerRegistrosLab", "com.mysql.jdbc.Driver"));
        resultados.add(checkMySql("Generación de Lotes", "ServerGeneracionLotes", "com.mysql.jdbc.Driver"));
        resultados.add(checkMySql("Inspección Manga", "ServerInspeccionManga", "com.mysql.jdbc.Driver"));
        resultados.add(checkMySql("Control Fórmulas", "ServerControlFormulas", "com.mysql.jdbc.Driver"));
        resultados.add(checkMySql("Firmas digitales", "ServerSignature", "com.mysql.jdbc.Driver"));
        resultados.add(checkSqlServer("ERP Plastitec (Productos)", "ServerSQLServerFactory"));
        resultados.add(checkSqlServer("ERP Plastitec (Tags)", "ServerSQLServerTags"));
        resultados.add(checkOfficePlatform());
        return resultados;
    }

    private static CheckResult checkMySql(String nombre, String categoria, String driver) {
        long inicio = System.currentTimeMillis();
        try {
            List lst_parameter = SETTING_JPA.ConsultSettingCategorie(categoria);
            if (lst_parameter == null || lst_parameter.isEmpty()) {
                return new CheckResult(nombre, false, "No hay configuración en Setting para la categoría '" + categoria + "'.", System.currentTimeMillis() - inicio);
            }
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            String login = arr_data[0];
            String password = arr_data[1];
            String url = "jdbc:mysql://" + arr_data[2];

            Class.forName(driver).newInstance();
            Connection conn = null;
            try {
                DriverManager.setLoginTimeout(5);
                conn = DriverManager.getConnection(url, login, password);
                boolean valido = conn.isValid(4);
                return new CheckResult(nombre, valido, valido ? "Conexión establecida." : "La conexión no respondió a tiempo.", System.currentTimeMillis() - inicio);
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (Exception ex) {
            return new CheckResult(nombre, false, ex.getMessage(), System.currentTimeMillis() - inicio);
        }
    }

    private static CheckResult checkSqlServer(String nombre, String categoria) {
        long inicio = System.currentTimeMillis();
        try {
            List lst_parameter = SETTING_JPA.ConsultSettingCategorie(categoria);
            if (lst_parameter == null || lst_parameter.isEmpty()) {
                return new CheckResult(nombre, false, "No hay configuración en Setting para la categoría '" + categoria + "'.", System.currentTimeMillis() - inicio);
            }
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            String login = arr_data[0];
            String password = arr_data[1];
            String url = "jdbc:sqlserver://" + arr_data[2];

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = null;
            try {
                DriverManager.setLoginTimeout(5);
                conn = DriverManager.getConnection(url, login, password);
                boolean valido = conn.isValid(4);
                return new CheckResult(nombre, valido, valido ? "Conexión establecida." : "La conexión no respondió a tiempo.", System.currentTimeMillis() - inicio);
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (Exception ex) {
            return new CheckResult(nombre, false, ex.getMessage(), System.currentTimeMillis() - inicio);
        }
    }

    private static CheckResult checkOfficePlatform() {
        long inicio = System.currentTimeMillis();
        try {
            URL url = new URL(OfficePlatformService.SERVER_URL + "/api/folders/search?q=__healthcheck__");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Api-Key", getApiKey());
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(6000);
            int code = conn.getResponseCode();
            InputStream stream = (code >= 200 && code < 400) ? conn.getInputStream() : conn.getErrorStream();
            if (stream != null) {
                leer(stream);
            }
            boolean ok = code >= 200 && code < 500;
            return new CheckResult("Office Platform", ok, "HTTP " + code + " - " + OfficePlatformService.SERVER_URL, System.currentTimeMillis() - inicio);
        } catch (Exception ex) {
            return new CheckResult("Office Platform", false, ex.getMessage(), System.currentTimeMillis() - inicio);
        }
    }

    private static String getApiKey() {
        try {
            java.lang.reflect.Field f = OfficePlatformService.class.getDeclaredField("API_KEY");
            f.setAccessible(true);
            return (String) f.get(null);
        } catch (Exception ex) {
            return "";
        }
    }

    private static void leer(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                sb.append(linea);
            }
            reader.close();
        } catch (Exception ex) {
            // La disponibilidad ya quedó determinada por el código HTTP; el cuerpo es solo informativo.
        }
    }
}
