/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Prog.sistemas2
 */
public class Connection_mysql_sirh {

    static String login = "APPS";
    static String password = "Sirh";
    static String url = "jdbc:mysql://172.16.2.111:3307/sirh";

    public List Empleado_sirh() throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                // Areas: produccion famaceutica == 9, produccion insumos == 8, gestion calidad == 5
                String query = "SELECT p.documento,p.nombres,p.apellidos,p.codigo_firma "
                        + " FROM personal p "
                        + " INNER JOIN personal_datos pd ON p.documento = pd.documento "
                        + " INNER JOIN cargo c ON pd.id_cargo = c.id_cargo "
                        + " WHERE pd.estado = 1 AND pd.vigencia = 1 AND c.id_area IN(9, 8, 5, 34, 35) AND p.genero = 'F'";
//                        + " WHERE pd.estado = 1 AND pd.vigencia = 1 AND c.id_area IN(9, 8)";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("nombres").toString().trim() + " " + rs.getString("apellidos").toString().trim() + " - " + rs.getString("codigo_firma").toString().trim() + "///");
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
