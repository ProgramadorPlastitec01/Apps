package Metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Connection_mysql_sirh {

    static String login = "APPS";
    static String password = "Sirh";
    static String url = "jdbc:mysql://172.16.2.111:3307/sirh";

    public List Empleado_sirh(String doc) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String query = "SELECT documento,nombres,apellidos,codigo_firma FROM personal WHERE documento = " + doc;
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("nombres").toString().trim() + " " + rs.getString("apellidos").toString().trim() + " / " + rs.getString("codigo_firma").toString().trim());
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
