package Metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Controladores_BD.ParametrosJpaController;

public class Connection_mysql_sirh {

    ParametrosJpaController JpaParametros = new ParametrosJpaController();
    static String login = "";
    static String password = "";
    static String url = "";

    public List Datos_empleado(String cod) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametros = JpaParametros.ConsultarParametrosxCategoria("ServerSIRH");
            if (lst_parametros != null) {
                Object[] obj_parametro = (Object[]) lst_parametros.get(0);
                String[] DataServer = obj_parametro[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                login = DataServer[0];
                password = DataServer[1];
                url = "jdbc:mysql://" + DataServer[2];
            } else {
                return null;
            }
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String query = "select p.Documento,p.Apellidos,p.Nombre,p.codigo_firma, p.`Área`, p.Cargo, p.id_cargo,p.codigo_firma from sirh.vw_personal_activo p where (p.codigo_firma+10000) = " + cod + "  OR (p.codigo_firma) = " + cod;
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("Documento").toString().trim() + " / " + rs.getString("Apellidos").toString().trim()
                            + " / " + rs.getString("Nombre").toString().trim() + " / " + rs.getString("codigo_firma").toString().trim()
                            + " / " + rs.getString("Área").toString().trim() + " / " + rs.getString("Cargo").toString().trim()
                            + " / " + rs.getString("id_cargo").toString().trim() + " / " + rs.getString("codigo_firma").toString().trim());
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
