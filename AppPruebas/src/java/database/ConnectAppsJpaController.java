package database;

import controlador.settingControllerJpa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectAppsJpaController {

    settingControllerJpa SettingJpa = new settingControllerJpa();
    static String login = "";
    static String password = "";
    static String url = "";

    public List ConsultData(String consult, int idSett) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CONSULT DATA">
        try {
            Class.forName("com.mysql.jdbc.Driver");
            List lst_setting = SettingJpa.ConsultSettingId(idSett);
            if (lst_setting != null) {
                Object[] obj_auth = (Object[]) lst_setting.get(0);
                String[] DataServer = obj_auth[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                url = "jdbc:mysql://" + DataServer[0];
                login = DataServer[1];
                try {
                    password = DataServer[2];
                } catch (Exception e) {
                    password = "";
                }
            } else {
                return null;
            }
            Connection con = DriverManager.getConnection(url, login, password);
            String query = consult;
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_resultado = new ArrayList<String>();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) {
                    header.append("]-[");
                }
                header.append(metaData.getColumnName(i));
            }
            lst_resultado.add(header.toString());

            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) {
                        row.append("]--[");
                    }
                    row.append(rs.getString(i) != null ? rs.getString(i).trim() : "NULL");
                }
                lst_resultado.add(row.toString());
            }
            con.close();
            return lst_resultado;
        } catch (Exception ex) {
            return null;
        }
//</editor-fold>
    }

    public boolean ejectData(int idSett, String ejection) throws Exception {
        try {
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_setting = SettingJpa.ConsultSettingId(idSett);
            if (lst_setting != null) {
                Object[] obj_auth = (Object[]) lst_setting.get(0);
                String[] DataServer = obj_auth[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                url = "jdbc:mysql://" + DataServer[0];
                login = DataServer[1];
                try {
                    password = DataServer[2];
                } catch (Exception e) {
                    password = "";
                }
            } else {
                return false;
            }
            con = DriverManager.getConnection(url, login, password);
            try (Statement sttm = con.createStatement()) {
                int rowsAffected = sttm.executeUpdate(ejection);
                return rowsAffected > 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
