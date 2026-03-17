package Metodos;

import Controladores.ConfiguracionJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionCorreo {

    ConfiguracionJpaController Configuracion = new ConfiguracionJpaController();
    static String login = "";
    static String password = "";
    static String url = "";

    public List ConsultaConfCorreo() throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_Conf = Configuracion.ConsultarConfiguracionCategoria("ConfCorreo");
            if (lst_Conf != null) {
                Object[] obj_data = (Object[]) lst_Conf.get(0);
                String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                login = arr_data[0];
                password = arr_data[1];
                url = "jdbc:" + arr_data[2] + "://" + arr_data[3];
            } else {
                return null;
            }
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String query = "";
                List lst_Confx = Configuracion.ConsultarConfiguracionCategoria("ScriptCorreo");
                if (lst_Confx != null) {
                    Object[] obj_param = (Object[]) lst_Confx.get(0);
                    query = obj_param[2].toString();
                } else {
                    return null;
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_confg = new ArrayList<String>();
                int count = 0;

                while (rs.next()) {
                    lst_confg.add(count, rs.getString("id_parametro").toString().trim() + "///"
                            + rs.getString("categoria").toString().trim() + "///"
                            + rs.getString("valor").toString().trim() + "///"
                            + rs.getString("descripcion").toString().trim() + "///"
                            + rs.getString("estado").toString().trim() + "///"
                            + rs.getString("fecha_registro").toString().trim());
                    count++;
                }
                conn.close();
                return lst_confg;
            } else {
                return null;
            }
        } catch (ClassNotFoundException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

}