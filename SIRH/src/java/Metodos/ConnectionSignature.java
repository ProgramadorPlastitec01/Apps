package Metodos;

import Controladores_BD.ParametrosJpa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionSignature {

    ParametrosJpa Jpaparameter = new ParametrosJpa();

    static String login = "";
    static String password = "";
    static String url = "";
    List lst_parameter = Jpaparameter.ConsultarParametrosxCategoria("ServerSignature");

    public List TraerFirmas(long dcm, long cdg) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_parameter != null) {
                try {
                    Object[] obj_auth = (Object[]) lst_parameter.get(0);
                    String[] DataServer = obj_auth[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0];
                    password = DataServer[1];
                    url = "jdbc:mysql://" + DataServer[2] + ":" + DataServer[3] + "/" + DataServer[4];
                    conn = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/signature";
                    conn = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/signature";
                conn = DriverManager.getConnection(url, login, password);
            }

            if (conn != null) {
                String Query = "SELECT f.id_firma, f.documento, f.codigo, f.firma, f.estado, f.fch_registro "
                        + "FROM firma f "
                        + "WHERE f.documento = " + dcm + " AND f.codigo = " + cdg + " "
                        + "ORDER BY f.fch_registro desc "
                        + "LIMIT 1";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Query);
                List<String> lst_firma = new ArrayList<String>();
                int count = 0;

                while (rs.next()) {
                    lst_firma.add(count, rs.getString("id_firma").toString().trim()
                            + "---" + rs.getString("documento").toString().trim()
                            + "---" + rs.getString("codigo").toString().trim()
                            + "---" + rs.getString("firma").toString().trim()
                            + "---" + rs.getString("estado").toString().trim()
                            + "---" + rs.getString("fch_registro").toString().trim());
                    count++;
                }
                conn.close();
                return lst_firma;
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

    public boolean RegistrarFirmas(long dcm, long cdg, String fma) throws Exception {
        try {
            Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_parameter != null) {
                try {
                    Object[] obj_auth = (Object[]) lst_parameter.get(0);
                    String[] DataServer = obj_auth[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0];
                    password = DataServer[1];
                    url = "jdbc:mysql://" + DataServer[2] + ":" + DataServer[3] + "/" + DataServer[4];
                    conn = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/signature";
                    conn = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/signature";
                conn = DriverManager.getConnection(url, login, password);
            }
            Statement sttm = conn.createStatement();
            sttm.executeUpdate("INSERT INTO firma(documento,codigo,firma) "
                    + "VALUES('" + dcm + "','" + cdg + "','" + fma + "')");
            if (sttm != null) {
                try {
                    sttm.close();
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ActualizarFirmas(long dcm, int cdg, String fma) throws Exception {
        try {
            Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_parameter != null) {
                try {
                    Object[] obj_auth = (Object[]) lst_parameter.get(0);
                    String[] DataServer = obj_auth[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0];
                    password = DataServer[1];
                    url = "jdbc:mysql://" + DataServer[2] + ":" + DataServer[3] + "/" + DataServer[4];
                    conn = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/signature";
                    conn = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/signature";
                conn = DriverManager.getConnection(url, login, password);
            }
            Statement sttm = conn.createStatement();
            sttm.executeUpdate("UPDATE firma f "
                    + "SET f.firma = '" + fma + "' "
                    + "WHERE f.documento = " + dcm + " AND f.codigo = " + cdg + " AND f.estado = 1 ");
            if (sttm != null) {
                try {
                    sttm.close();
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
