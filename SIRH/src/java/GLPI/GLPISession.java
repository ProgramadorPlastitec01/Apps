package GLPI;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import GLPI.GetData;
import java.util.List;

public class GLPISession {

    private GetData config = new GetData();

    String urlBase = "";
    String appToken = "";
    String userToken = "";

    private String sessionToken;

    public String initSession() throws Exception {
        List lst_conf = config.Consultar_Configuracion_glpi("GlpiData");

        if (lst_conf != null) {
            Object[] confData = (Object[]) lst_conf.get(0);
            String[] DataGlpi = confData[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            urlBase = "http://172.16.1.245/apirest.php";
            appToken = DataGlpi[1].toString();
            userToken = DataGlpi[2].toString();
        }

        URL url = new URL(urlBase + "/initSession");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("App-Token", appToken);
        conn.setRequestProperty("Authorization", "user_token " + userToken);

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();

        sessionToken = json.get("session_token").getAsString();

        return sessionToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
