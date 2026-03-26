package GLPI;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import GLPI.GetData;
import java.util.List;

public class GLPIClient {

    private GetData config = new GetData();

    String urlBase = "";
    String appToken = "";
    String userToken = "";

    GLPISession session = new GLPISession();
    String sessionToken;

    public GLPIClient() throws Exception {
        this.sessionToken = session.initSession();
    }

    private HttpURLConnection createConnection(String endpoint, String method) throws Exception {
        List lst_conf = config.Consultar_Configuracion_glpi("GlpiData");

        if (lst_conf != null) {
            Object[] confData = (Object[]) lst_conf.get(0);
            String[] DataGlpi = confData[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            urlBase = "http://172.16.1.245/apirest.php";
            appToken = DataGlpi[1].toString();
            userToken = DataGlpi[2].toString();
        }

        URL url = new URL(urlBase + endpoint);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod(method);

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("App-Token", appToken);
        conn.setRequestProperty("Session-Token", sessionToken);

        conn.setDoInput(true);

        return conn;
    }

    //<editor-fold defaultstate="collapsed" desc="SEARCH USERS">
    public String buscarUsuario(String name) throws Exception {

        HttpURLConnection conn = createConnection(
                "/search/User?criteria[0][field]=1&criteria[0][searchtype]=equals&criteria[0][value]=" + name,
                "GET"
        );

        InputStream is;

        if (conn.getResponseCode() == 200) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        return response.toString();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CREATE USER">
    public String crearUsuario(String login, String nombre, String apellido, String password) throws Exception {
        String busqueda = buscarUsuario(login);
        if (!busqueda.contains("\"totalcount\":0")) {
            return "El usuario ya existe en GLPI";
        }
        String endpoint = "/User";
        HttpURLConnection conn = createConnection(endpoint, "POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        String json = "{ \"input\": {"
                + "\"name\":\"" + login + "\","
                + "\"realname\":\"" + apellido + "\","
                + "\"firstname\":\"" + nombre + "\","
                + "\"password\":\"" + password + "\","
                + "\"password2\":\"" + password + "\""
                + "} }";
        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();
        int responseCode = conn.getResponseCode();
        InputStream is;
        if (responseCode == 200 || responseCode == 201) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        String resultado = response.toString();
        if (responseCode == 200 || responseCode == 201) {
            Pattern pattern = Pattern.compile("\"id\":(\\d+)");
            Matcher matcher = pattern.matcher(resultado);
            int idUsuario = 0;
            if (matcher.find()) {
                idUsuario = Integer.parseInt(matcher.group(1));
            }
            asignarPerfilUsuario(idUsuario, 1, 0);
            forzarCambioPassword(idUsuario);
        }

        return resultado;
    }
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="PROFILE USER">
    public String asignarPerfilUsuario(int userId, int profileId, int entityId) throws Exception {

        String endpoint = "/Profile_User";

        HttpURLConnection conn = createConnection(endpoint, "POST");

        conn.setDoOutput(true);

        String json = "{ \"input\": {"
                + "\"users_id\":" + userId + ","
                + "\"profiles_id\":" + profileId + ","
                + "\"entities_id\":" + entityId + ","
                + "\"is_recursive\":1"
                + "} }";

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        int responseCode = conn.getResponseCode();

        InputStream is;

        if (responseCode == 200 || responseCode == 201) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        StringBuilder response = new StringBuilder();

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        br.close();

        return response.toString();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EDIT USER">
    public String editarUsuario(int id, String nombre, String apellido) throws Exception {

        String endpoint = "/User/" + id;

        HttpURLConnection conn = createConnection(endpoint, "PUT");

        conn.setDoOutput(true);

        String json = "{ \"input\": {"
                + "\"id\":" + id + ","
                + "\"firstname\":\"" + nombre + "\","
                + "\"realname\":\"" + apellido + "\""
                + "} }";

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        int responseCode = conn.getResponseCode();

        InputStream is;

        if (responseCode == 200 || responseCode == 201) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        StringBuilder response = new StringBuilder();

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        br.close();

        return response.toString();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="INACTIVE USER">
    public String inactivarUsuario(int id) throws Exception {
        String endpoint = "/User/" + id;
        HttpURLConnection conn = createConnection(endpoint, "PUT");
        conn.setDoOutput(true);
        String json = "{ \"input\": {"
                + "\"id\":" + id + ","
                + "\"is_active\":0"
                + "} }";
        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();
        int responseCode = conn.getResponseCode();
        InputStream is;
        if (responseCode == 200 || responseCode == 201) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        return response.toString();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FORCE PASSWORD">
    public String forzarCambioPassword(int id) throws Exception {

        String endpoint = "/User/" + id;
        HttpURLConnection conn = createConnection(endpoint, "PUT");
        conn.setDoOutput(true);

        String json = "{ \"input\": {"
                + "\"id\":" + id + ","
                + "\"password_last_update\":\"2000-01-01 01:02:03\""
                + "} }";

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        int responseCode = conn.getResponseCode();

        InputStream is;
        if (responseCode == 200 || responseCode == 201) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        br.close();

        return response.toString();
    }
    //</editor-fold>
}
