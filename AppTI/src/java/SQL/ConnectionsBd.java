package SQL;

//import static SQL.Connection_mysql_sirh.login;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Controller.SettingControllerJpa;
//import Controladoras.ParametroJpaController;

public class ConnectionsBd {

    SettingControllerJpa SettingJpa = new SettingControllerJpa();

    static String login = "";
    static String password = "";
    static String url = "";

    public List Consultar_firmas(int firm) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CONECTIONS SIGNAURE">
        List lst_parametro = SettingJpa.ConsultSettingCategorie("SeverSignature");
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
                String query = "SELECT id_firma, documento, codigo, firma, fch_registro "
                        + "FROM signature.firma  "
                        + "WHERE id_firma = " + firm + "; ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("id_firma").toString().trim() + " /// " + rs.getString("documento").toString().trim() + " /// " + rs.getString("codigo").toString().trim() + " /// " + rs.getString("firma").toString().trim() + " /// " + rs.getString("fch_registro") + "");
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
//</editor-fold>
    }

    public List Consultar_firmasDoc(int docx, int codx) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CONECTIONS SIGNAURE">
        List lst_parametro = SettingJpa.ConsultSettingCategorie("SeverSignature");
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
                String query = "SELECT f.id_firma,f.documento,f.codigo,f.firma, f.fch_registro "
                        + "FROM signature.firma f "
                        + "WHERE f.documento = " + docx + " AND f.codigo = " + codx + "";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count,
                            rs.getString("id_firma").toString().trim() + "///"
                            + rs.getString("Documento").toString().trim() + "///"
                            + rs.getString("Codigo").toString().trim() + "///"
                            + rs.getString("firma").toString().trim() + "///"
                            + rs.getString("fch_registro"));
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
//</editor-fold>
    }

    public List Consultar_SIRH(int dox, int cox) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CONNECTIONS SIRH">
        List lst_parametro = SettingJpa.ConsultSettingCategorie("SeverSIRH");
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
                String query = "SELECT CONCAT(p.nombres, ' ', p.apellidos) AS 'Nombres', p.documento, p.codigo_firma, a.nombre as 'Area', c.nombre as 'Cargo' "
                        + "FROM personal p "
                        + "INNER JOIN personal_datos pd ON p.documento = pd.documento "
                        + "INNER JOIN cargo c ON pd.id_cargo = c.id_cargo "
                        + "INNER JOIN area a ON c.id_area = a.id_area "
                        + "WHERE pd.estado = 1 AND pd.vigencia = 1 AND p.documento = " + dox + " AND p.codigo_firma = " + cox + " ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("Nombres").toString().trim() + "///" + rs.getString("Documento").toString().trim() + "///"
                            + rs.getString("codigo_firma").toString().trim() + "///" + rs.getString("Area").toString().trim() + "///" + rs.getString("Cargo").toString().trim() + "");
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
//</editor-fold>
    }

    public List Consultar_SIRHPersonal() throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CONNECTIONS SIRH">
        List lst_parametro = SettingJpa.ConsultSettingCategorie("SeverSIRH");
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
                String query = "SELECT CONCAT(p.nombres, ' ', p.apellidos) AS 'Nombres', p.documento, p.codigo_firma, c.nombre "
                        + "FROM personal p "
                        + "INNER JOIN personal_datos pd ON p.documento = pd.documento "
                        + "INNER JOIN cargo c ON pd.id_cargo = c.id_cargo "
                        + "WHERE pd.estado = 1 AND pd.vigencia = 1 "
                        + "ORDER BY Nombres ASC ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, "[" + rs.getString("Nombres").toString().trim() + "][" + rs.getString("Documento").toString().trim() + "]["
                            + rs.getString("codigo_firma").toString().trim() + "][" + rs.getString("nombre").toString().trim() + "]///");
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
//</editor-fold>
    }

    public boolean New_signature(int doc, int cod, String signa) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CONNECCIONS SIGNATURE">
        try {
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametro = SettingJpa.ConsultSettingCategorie("SeverSignature");
            if (lst_parametro != null) {
                Object[] obj_auth = (Object[]) lst_parametro.get(0);
                String[] DataServer = obj_auth[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                url = "jdbc:mysql://" + DataServer[2];
                login = DataServer[0];
                password = DataServer[1];
            } else {
                return false;
            }
            con = DriverManager.getConnection(url, login, password);
            try (Statement sttm = con.createStatement()) {
                int rowsAffected = sttm.executeUpdate("INSERT INTO firma (documento, codigo, firma) "
                        + "VALUES (" + doc + ", " + cod + ", '" + signa + "'); ");
                return rowsAffected > 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        //</editor-fold>
    }

    public List ConsultarGLPI() throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CONNECTIONS GLPI">
        List lst_parametro = SettingJpa.ConsultSettingCategorie("ServerGLPI");
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
                String query = "SELECT \n"
                        + "    SUM(CASE WHEN status = 5 THEN 1 ELSE 0 END) AS tickets_cerrados,\n"
                        + "    SUM(CASE WHEN status != 5 THEN 1 ELSE 0 END) AS tickets_abiertos,\n"
                        + "    COUNT(*) AS tickets_totales\n"
                        + "FROM glpi_tickets; ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, "[" + rs.getString("tickets_cerrados").toString().trim() + "][" + rs.getString("tickets_abiertos").toString().trim() + "]["
                            + rs.getString("tickets_totales").toString().trim() + "]///");
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
        //</editor-fold>
    }
}
