package Metodos;

import Controladores.ParametroJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Consultas_metrologia {

    ParametroJpaController ParametroJpa = new ParametroJpaController();
    static String login = "";
    static String password = "";
    static String url = "";

    public List ConsultaSerialesMetrologia() throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("SeverMetrologia");
            if (lst_parametro != null) {
                Object[] obj_data = (Object[]) lst_parametro.get(0);
                String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                login = arr_data[0];
                password = arr_data[1];
                url = "jdbc:mysql://" + arr_data[2];
            } else {
                return null;
            }
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String query = "";
                List lst_parametroMT = ParametroJpa.ConsultarParametrosxCategoria("ConsultaMetrologia");
                if (lst_parametroMT != null) {
                    Object[] obj_param = (Object[]) lst_parametroMT.get(0);
                    query = obj_param[2].toString();
                } else {
                    return null;
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_seriales = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_seriales.add(count, rs.getString("id_instrumento_medicion").toString().trim()
                            + "---" + rs.getString("tipo").toString().trim()
                            + "---" + rs.getString("instrumento").toString().trim()
                            + "---" + rs.getString("numero_serial").toString().trim()
                            + "---" + rs.getString("fch_ultima_verificacion_int").toString().trim()
                            + "---" + rs.getString("fch_proxima_verificacion_int").trim()
                            + "---" + rs.getString("fch_tolerancia_int").toString().trim()
                            + "---" + rs.getString("fch_ultima_verificacion_ext").toString().trim()
                            + "---" + rs.getString("fch_proxima_verificacion_ext").toString().trim()
                            + "---" + rs.getString("fch_tolerancia_ext").toString().trim()
                            + "---" + rs.getString("estado").toString().trim()
                            + "---" + rs.getString("semaforo").toString().trim()
                            + "---" + rs.getString("tipoAlerta").toString().trim()
                            + "---" + rs.getString("tipo_verificacion").toString().trim()
                            + "---" + rs.getString("aprupa_int").toString().trim()
                            + "---" + rs.getString("aprupa_ext").toString().trim() + "////");
                    count++;
                }
                conn.close();
                return lst_seriales;
            }else{
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
