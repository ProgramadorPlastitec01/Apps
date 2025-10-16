package Connection;

import static Connection.ConnectionRegistrosLAB.login;
import Controller.SettingJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionSignature {
      SettingJpaController SettingJpa = new SettingJpaController();

    static String login = "";
    static String password = "";
    static String url = "";
    
    public List ConsultSignature(int docx, int codx) throws Exception {
        List lst_parametro = SettingJpa.ConsultSettingCategorie("ServerSignature");
        if (lst_parametro != null) {
            Object[] obj_data = (Object[]) lst_parametro.get(0);
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
                String query = "SELECT f.id_firma,f.documento,f.codigo,f.firma, f.fch_registro "
                        + "FROM signature.firma f "
                        + "WHERE f.documento = " + docx + " AND f.codigo = " + codx + "";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count,
                            rs.getString("id_firma").toString().trim() + "///"
                            + rs.getString("Documento").toString().trim() + "///"
                            + rs.getString("Codigo").toString().trim() + "///"
                            + rs.getString("firma").toString().trim() + "///"
                            + rs.getString("fch_registro"));
                    count++;
                }
                conn.close();
                return lst_documentos;
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
    }
    public List ConsultSignatureId(int IdSign) throws Exception {
        List lst_parametro = SettingJpa.ConsultSettingCategorie("ServerSignature");
        if (lst_parametro != null) {
            Object[] obj_data = (Object[]) lst_parametro.get(0);
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
                String query = "SELECT f.id_firma,f.documento,f.codigo,f.firma, f.fch_registro "
                        + "FROM signature.firma f "
                        + "WHERE f.id_firma = " + IdSign;
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count,
                            rs.getString("id_firma").toString().trim() + "///"
                            + rs.getString("Documento").toString().trim() + "///"
                            + rs.getString("Codigo").toString().trim() + "///"
                            + rs.getString("firma").toString().trim() + "///"
                            + rs.getString("fch_registro"));
                    count++;
                }
                conn.close();
                return lst_documentos;
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
    }
}
