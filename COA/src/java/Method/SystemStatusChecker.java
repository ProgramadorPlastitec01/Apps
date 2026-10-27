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
        resultados.addAll(checkSqlServerPlastitec());
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

    // Blips de red puntuales (ej. firewall que limita conexiones concurrentes
    // por IP origen) pueden tumbar un solo intento aunque el host sí esté
    // disponible. Un segundo intento, tras una espera corta, evita marcar
    // "Sin conexión" por algo transitorio sin ocultar una caída real (si el
    // segundo intento también falla, se reporta igual que antes).
    private static final int INTENTOS_SQL_SERVER = 2;
    private static final long ESPERA_ENTRE_INTENTOS_MS = 1000;

    // "ERP Plastitec (Productos)" y "ERP Plastitec (Tags)" son la MISMA instancia
    // de SQL Server (mismo host:puerto), solo cambia la base de datos. Antes cada
    // una abría su propio login contra el servidor, y era posible que al abrir una
    // la otra se cayera por competir contra el mismo servidor. Ahora se abre una
    // única conexión al servidor y, ya establecida, se distribuye hacia cada BD
    // con setCatalog, sin volver a autenticar.
    private static final String SETTING_CATEGORY_PLASTITEC = "ServerSQLServerPlastitec";
    private static final String DB_FACTORY = "EMP001_FACT";
    private static final String DB_TAGS = "TagsLogic";

    private static List<CheckResult> checkSqlServerPlastitec() {
        long inicio = System.currentTimeMillis();
        String ultimoError = null;
        for (int intento = 1; intento <= INTENTOS_SQL_SERVER; intento++) {
            Connection con = null;
            try {
                List lst_parameter = SETTING_JPA.ConsultSettingCategorie(SETTING_CATEGORY_PLASTITEC);
                if (lst_parameter == null || lst_parameter.isEmpty()) {
                    ultimoError = "No hay configuración en Setting para la categoría '" + SETTING_CATEGORY_PLASTITEC + "'.";
                    break;
                }
                Object[] obj_data = (Object[]) lst_parameter.get(0);
                String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                String login = arr_data[0];
                String password = arr_data[1];
                String url = "jdbc:sqlserver://" + arr_data[2];

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                DriverManager.setLoginTimeout(5);
                con = DriverManager.getConnection(url, login, password);
                if (!con.isValid(4)) {
                    throw new Exception("La conexión al servidor no respondió a tiempo.");
                }

                List<CheckResult> resultados = new ArrayList<CheckResult>();
                resultados.add(probarBaseDeDatos(con, "ERP Plastitec (Productos)", DB_FACTORY, inicio));
                resultados.add(probarBaseDeDatos(con, "ERP Plastitec (Tags)", DB_TAGS, inicio));
                return resultados;
            } catch (Exception ex) {
                ultimoError = ex.getMessage();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception ignore) {
                    }
                }
            }
            if (intento < INTENTOS_SQL_SERVER) {
                try {
                    Thread.sleep(ESPERA_ENTRE_INTENTOS_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        String detalle = ultimoError + " (falló tras " + INTENTOS_SQL_SERVER + " intentos)";
        long tiempoMs = System.currentTimeMillis() - inicio;
        List<CheckResult> fallo = new ArrayList<CheckResult>();
        fallo.add(new CheckResult("ERP Plastitec (Productos)", false, detalle, tiempoMs));
        fallo.add(new CheckResult("ERP Plastitec (Tags)", false, detalle, tiempoMs));
        return fallo;
    }

    private static CheckResult probarBaseDeDatos(Connection con, String nombre, String baseDatos, long inicio) {
        try {
            con.setCatalog(baseDatos);
            boolean valido = con.isValid(4);
            return new CheckResult(nombre, valido, valido ? "Conexión establecida." : "La conexión no respondió a tiempo.", System.currentTimeMillis() - inicio);
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
