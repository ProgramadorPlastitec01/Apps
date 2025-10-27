package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Controladoras.ParametroJpaController;

public class Connection_mysql_sirh {

    ParametroJpaController ParametroJpa = new ParametroJpaController();
    static String login = "";
    static String password = "";
    static String url = "";

    public List Empleado_sirh() throws Exception {
        List lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("SeverSirh");
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
//                String query = "SELECT p.documento,p.nombres,p.apellidos,p.codigo_firma FROM personal p INNER JOIN personal_datos pd ON p.documento = pd.documento WHERE pd.estado = 1 AND pd.vigencia = 1 ";
                String query = "SELECT p.documento,p.nombres,p.apellidos,p.codigo_firma, c.nombre FROM personal p INNER JOIN personal_datos pd ON p.documento = pd.documento INNER JOIN cargo c ON pd.id_cargo = c.id_cargo WHERE pd.estado = 1 AND pd.vigencia = 1 ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("documento").toString().trim() + " - " + rs.getString("nombres").toString().trim() + " " + rs.getString("apellidos").toString().trim() + " - " + rs.getString("nombre").toString().trim() + " - " + rs.getString("codigo_firma") + " - " + "XXFIRMASXX" + "///");
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

    public List Empleado_sirh_comparacion(int id_documento) throws Exception {
        List lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("SeverSirh");
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
//                String query = "SELECT p.documento,p.nombres,p.apellidos,p.codigo_firma FROM personal p INNER JOIN personal_datos pd ON p.documento = pd.documento WHERE pd.estado = 1 AND pd.vigencia = 1 ";
                String query = "SELECT p.documento,p.nombres,p.apellidos,p.codigo_firma, c.nombre FROM personal p INNER JOIN personal_datos pd ON p.documento = pd.documento INNER JOIN cargo c ON pd.id_cargo = c.id_cargo WHERE pd.estado = 1 AND pd.vigencia = 1 AND pd.documento = " + id_documento + " ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("documento").toString().trim() + " - " + rs.getString("nombres").toString().trim() + " " + rs.getString("apellidos").toString().trim() + " - " + rs.getString("nombre").toString().trim() + " - " + rs.getString("codigo_firma") + " - " + "XXFIRMASXX" + "///");
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

    public List Empleado_sirh_nombre_area() throws Exception {
        List lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("SeverSirh");
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
//                String query = "SELECT p.documento,p.nombres,p.apellidos,p.codigo_firma FROM personal p INNER JOIN personal_datos pd ON p.documento = pd.documento WHERE pd.estado = 1 AND pd.vigencia = 1 ";
                String query = "SELECT p.nombres,p.apellidos,p.codigo_firma, a.sigla \n"
                        + "FROM personal p \n"
                        + "INNER JOIN personal_datos pd ON p.documento = pd.documento \n"
                        + "INNER JOIN cargo c ON pd.id_cargo = c.id_cargo \n"
                        + "INNER JOIN area a ON c.id_area = a.id_area \n"
                        + "WHERE pd.estado = 1 AND pd.vigencia = 1 ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("nombres").toString().trim() + " " + rs.getString("apellidos").toString().trim() + " - " + rs.getString("codigo_firma") + " - " + rs.getString("sigla") + "///");
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

    public List Empleado_sirh_nombre_area_R() throws Exception {
        List lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("SeverSirh");
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
//                String query = "SELECT p.documento,p.nombres,p.apellidos,p.codigo_firma FROM personal p INNER JOIN personal_datos pd ON p.documento = pd.documento WHERE pd.estado = 1 AND pd.vigencia = 1 ";
                String query = "SELECT p.nombres,p.apellidos,p.codigo_firma, a.nombre "
                        + "FROM personal p "
                        + "INNER JOIN personal_datos pd ON p.documento = pd.documento "
                        + "INNER JOIN cargo c ON pd.id_cargo = c.id_cargo "
                        + "INNER JOIN area a ON c.id_area = a.id_area "
                        + "WHERE pd.estado = 1 AND pd.vigencia = 1 ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("nombres").toString().trim() + " " + rs.getString("apellidos").toString().trim() + " - " + rs.getString("codigo_firma") + " - " + rs.getString("nombre") + "///");
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

    public List consultar_Areas_Xcodido(int cod) throws Exception {
        List lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("SeverSirh");
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
//                String query = "SELECT p.documento,p.nombres,p.apellidos,p.codigo_firma FROM personal p INNER JOIN personal_datos pd ON p.documento = pd.documento WHERE pd.estado = 1 AND pd.vigencia = 1 ";
                String query = "SELECT d.id_cargo, a.nombre "
                        + "FROM personal p "
                        + "INNER JOIN personal_datos d ON p.documento = d.documento "
                        + "INNER JOIN cargo c ON d.id_cargo = c.id_cargo "
                        + "INNER JOIN area a ON c.id_area = a.id_area "
                        + "WHERE p.codigo_firma = " + cod + " "
                        + "ORDER BY d.id_cargo desc "
                        + "LIMIT 1";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_documentos.add(count, rs.getString("nombre").toString().trim());
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
