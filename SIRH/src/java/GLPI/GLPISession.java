package GLPI;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import GLPI.ConfigLoader;

public class GLPISession {

    private ConfigLoader config = new ConfigLoader();

    String urlBase = config.get("glpi.url");
    String appToken = config.get("glpi.app.token");
    String userToken = config.get("glpi.user.token");
//    String urlBase ="http://172.16.1.242/apirest.php";
//    String appToken = "FruJ43VPMncRHyMFBPmNLvaMAMRfjaFpAU4xxEng";
//    String userToken = "sa8M1ocr7PNE4AFKfvcKD5C3pLbnjO5ChwHkYw9C";

    private String sessionToken;

    public String initSession() throws Exception {

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
