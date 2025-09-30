package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Controller.SettingJpaController;

public class ConnectionAdminUser {

    SettingJpaController SettingJpa = new SettingJpaController();

    static String login = "";
    static String password = "";
    static String url = "";

    public List ConsultUserPassword(String user, String pass) throws Exception {
        List lst_parametro = SettingJpa.ConsultSettingCategorie("SeverAdminitradorUsuarios");
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
                String query = "SELECT u.id_usuario, u.nombres, u.apellidos,u.documento,u.codigo,u.usuario,u.contrasena,u.estado,r.id_rol,r.nombre_rol, "
                        + "       IF(u.contrasena = YEAR(CURDATE()) OR CHAR_LENGTH(u.contrasena) < 30, 'YES',  'NO') AS `CountCaracter`, "
                        + "       u.usuario_registro,u.fecha_registro,u.fecha_modificacion "
                        + "FROM usuario u "
                        + "INNER JOIN usuario_app ua ON u.id_usuario = ua.id_usuario AND ua.id_app = 1 "
                        + "INNER JOIN rol r ON ua.id_rol = r.id_rol "
                        + "WHERE u.usuario = '" + user + "' "
                        + "AND u.contrasena = '" + pass + "' ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_user = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_user.add(count, rs.getString("id_usuario").toString().trim() + " /// " + rs.getString("nombres").toString().trim() + " /// "
                            + rs.getString("apellidos").toString().trim() + " /// " + rs.getString("documento").toString().trim() + " /// "
                            + rs.getString("codigo").toString().trim() + "///" + rs.getString("usuario").toString().trim() + "///"
                            + rs.getString("contrasena").toString().trim() + "///" + rs.getString("estado").toString().trim() + "///"
                            + rs.getString("id_rol").toString().trim() + "///" + rs.getString("nombre_rol").toString().trim() + "///"
                            + rs.getString("CountCaracter").toString().trim() + "///" + rs.getString("usuario_registro").toString().trim() + "///"
                            + rs.getString("fecha_registro").toString().trim() + "///" + rs.getString("fecha_modificacion").toString().trim() + "");
                    count++;
                }
                conn.close();
                return lst_user;
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
