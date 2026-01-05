package Connection;

import Controller.SettingJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LinkBatchRecord {

    SettingJpaController SettingJpa = new SettingJpaController();

    static String login = "";
    static String password = "";
    static String url = "";

    public List LinkBatchRecord(String Order, String Batch) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="LinkBatchRecord">
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerRegistrosLab");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            return null;
        }
        List lst_link = SettingJpa.ConsultSettingCategorie("BatchRecordLAB");
        String Qry = "";
        if (lst_link != null) {
            Object[] obj_link = (Object[]) lst_link.get(0);
            Qry = obj_link[2].toString();
        } else {
            Qry = "";
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null && !Qry.equals("")) {
                Qry = Qry.replace("XOrderX", Order);
                Qry = Qry.replace("XBatchX", Batch);
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Qry);
                List<String> lst_batch = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_batch.add(count, rs.getString("Type").trim() + " /// " + rs.getString("TypeReg").trim() + " /// " + rs.getString("Data").trim() + " /// " + rs.getString("Link").trim() + "");
                    count++;
                }
                conn.close();
                return lst_batch;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
        //</editor-fold>
    }
    

}
