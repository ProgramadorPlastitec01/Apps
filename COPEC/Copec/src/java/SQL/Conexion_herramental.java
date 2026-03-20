package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Conexion_herramental {

    static String login = "APPS";
    static String password = "Sirh";
    static String url = "jdbc:mysql://172.16.2.117:3306/herramental_proceso";

    public List Consulta_herramental_defecto() throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String query = "select d.id_defecto, d.id_clasificacion, c.clasificacion, d.id_familia, f.nombre, d.defecto,d.consecutivo,d.descripcion "
                        + "from herramental_proceso.defecto d "
                        + "inner join herramental_proceso.clasificacion_defecto c on d.id_clasificacion = c.id_clasificacion "
                        + "inner join herramental_proceso.familia_producto f on d.id_familia = f.id_familia_producto "
                        + "order by d.id_defecto asc ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("id_defecto").toString().trim()+ "///////" + rs.getString("id_clasificacion").toString().trim() + "///////"
                            + rs.getString("clasificacion").toString().trim()+ "///////" + rs.getString("id_familia").toString().trim()+ "///////"
                            + rs.getString("nombre").toString().trim()+ "///////" + rs.getString("defecto").toString().trim()+ "///////"
                            + rs.getString("consecutivo").toString().trim()+ "///////" + rs.getString("descripcion").toString().trim()+ "-------");
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
