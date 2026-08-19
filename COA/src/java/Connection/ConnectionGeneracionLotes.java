package Connection;

import Controller.SettingJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionGeneracionLotes {

    SettingJpaController SettingJpa = new SettingJpaController();

    static String login = "";
    static String password = "";
    static String url = "";

    public List ConsultarCC_GeneracionLote(String Lote) throws Exception {
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerGeneracionLotes");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String query = "";
                if (Lote.contains("/")) {
                    String[] BatchN = Lote.split("/");
                    query = "SELECT GROUP_CONCAT( "
                            + "    c.consecutivo "
                            + "    ORDER BY FIELD(c.lote, '" + BatchN[0].trim() + "', '" + BatchN[1].trim() + "') "
                            + "    SEPARATOR ' - CC' "
                            + ") AS consecutivo "
                            + "FROM control_consecutivos c "
                            + "WHERE c.lote IN ('" + BatchN[0].trim() + "', '" + BatchN[1].trim() + "');";
                } else {
                    query = "SELECT c.consecutivo FROM control_consecutivos c WHERE c.lote LIKE '%" + Lote.trim() + "%'";
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_consecutivos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    String consecutivo = rs.getString("consecutivo");
                    if (consecutivo != null) {
                        lst_consecutivos.add(count, consecutivo.trim());
                    }
                    count++;
                }

                conn.close();
                return lst_consecutivos;
            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            return null;
        }
    }

    public List ConsultarCC_RepcecionMaterial(String Lote) throws Exception {
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerGeneracionLotes");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            return null;
        }
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String query = "SELECT r.consecutivo,r.codigo FROM recepcion_material r  WHERE r.lote LIKE '%" + Lote.trim() + "%'";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_consecutivos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_consecutivos.add(count, rs.getString("consecutivo").trim() + "///" + rs.getString("codigo").trim());
                    count++;
                }
                conn.close();
                return lst_consecutivos;
            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            return null;
        }
    }
}
