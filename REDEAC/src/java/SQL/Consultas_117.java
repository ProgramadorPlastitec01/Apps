package SQL;

import static SQL.Connection_mysql_sirh.login;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Controladoras.ParametroJpaController;

public class Consultas_117 {
    
    ParametroJpaController ParametroJpa = new ParametroJpaController();
    
    static String login = "APPS";
    static String password = "signature";
    static String url = "jdbc:mysql://172.16.2.117:3306/signature";

    public List Consultar_firmas(int firm) throws Exception {
        List lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("SeverSignature");
        if (lst_parametro != null) {
            Object[] obj_data = (Object[]) lst_parametro.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];;
        } else {
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String query = "SELECT f.id_firma,f.documento,f.codigo,f.firma, f.fch_registro"
                        + "FROM signature.firma f"
                        + "WHERE  f.id_firma = "+ firm +"";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("id_firma").toString().trim() + " " + rs.getString("Documento").toString().trim() + " " + rs.getString("Codigo").toString().trim() + " " + rs.getString("firma").toString().trim() + " " + rs.getString("fecha_registro") +"");
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
