package Metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Controladores.ParametroJpaController;

public class Consultas_metrologia {

    ParametroJpaController JpaParameter = new ParametroJpaController();
    static String login = "";
    static String password = "";
    static String url = "";

    public List calibradores() throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parameterAuth = JpaParameter.ConsultaParametro("SeverMetrologia");
            if (lst_parameterAuth != null) {
                Object[] obj_auth = (Object[]) lst_parameterAuth.get(0);
                String[] DataServer = obj_auth[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                login = DataServer[0];
                password = DataServer[1];
                url = "jdbc:mysql://" + DataServer[2];
            } else {
                return null;
            }
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String query = "";
                List lst_parameterMRL = JpaParameter.ConsultaParametro("Seriales_Metrolgia");
                if (lst_parameterMRL != null) {
                    Object[] obj_metrology = (Object[]) lst_parameterMRL.get(0);
                    query = obj_metrology[2].toString();
                } else {
                    return null;
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_calibradores = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_calibradores.add(count, rs.getString("id_instrumento_medicion").toString().trim()
                            + "---" + rs.getString("tipo").toString().trim()
                            + "---" + rs.getString("instrumento").toString().trim()
                            + "---" + rs.getString("numero_serial").toString().trim()
                            + "---" + rs.getString("fch_ultima_verificacion_int").toString().trim()
                            + "---" + rs.getString("fch_proxima_verificacion_int").toString().trim()
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
                return lst_calibradores;
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

    public List calibradores_filtro(String txtbus) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parameterAuth = JpaParameter.ConsultaParametro("SeverMetrologia");
            if (lst_parameterAuth != null) {
                Object[] obj_auth = (Object[]) lst_parameterAuth.get(0);
                String[] DataServer = obj_auth[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                login = DataServer[0];
                password = DataServer[1];
                url = "jdbc:mysql://" + DataServer[2];
            } else {
                return null;
            }
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String query = "";
                List lst_parameterMRL = JpaParameter.ConsultaParametro("Seriales_MetrolgiaId");
                if (lst_parameterMRL != null) {
                    Object[] obj_metrology = (Object[]) lst_parameterMRL.get(0);
                    query = obj_metrology[2].toString() + " and (i.codigo like CONCAT( '%'," + txtbus + ",'%') or "
                            + "i.instrumento like CONCAT( '%'," + txtbus + ",'%') or "
                            + "i.modelo like CONCAT( '%'," + txtbus + ",'%') or "
                            + "i.numero_serial like CONCAT( '%'," + txtbus + ",'%') or "
                            + "ti.tipo like CONCAT( '%'," + txtbus + ",'%') or "
                            + "t.tipo like CONCAT( '%'," + txtbus + ",'%'))and a.id_area = 2 "
                            + "order by i.id_instrumento_medicion asc";
                } else {
                    return null;
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_calibradores = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_calibradores.add(count, rs.getString("tipo").toString().trim()
                            + "---" + rs.getString("codigo").toString().trim()
                            + "---" + rs.getString("numero_serial").toString().trim()
                            + "---" + rs.getString("area").toString().trim()
                            + "---" + rs.getString("fch_ultima_verificacion_int").toString().trim()
                            + "---" + rs.getString("fch_proxima_verificacion_int").toString().trim()
                            + "---" + rs.getString("semaforo").toString().trim()
                            + "---" + rs.getString("tipoAlerta").toString().trim() + "////");
                    count++;
                }
                conn.close();
                return lst_calibradores;
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
