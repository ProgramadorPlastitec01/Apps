package Metodos;

import Controladores.ParametroJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Consulta_formulas {

    ParametroJpaController ParametroJpa = new ParametroJpaController();
    static String login = "";
    static String password = "";
    static String url = "";

    public List Traer_control_durezas_lote(String lte) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("ServerControlFormulas");
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
                List lst_parametroCF = ParametroJpa.ConsultarParametrosxCategoria("ConsultaDurezasFormulas");
                if (lst_parametroCF != null) {
                    Object[] obj_param = (Object[]) lst_parametroCF.get(0);
                    query = obj_param[2].toString();
                    query = query.replace("xxxLotexxx", "'" + lte + "'");
                } else {
                    return null;
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_durezas = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_durezas.add(count, rs.getString("id_dureza").trim()
                            + "---" + rs.getString("id_formula").trim()
                            + "---" + rs.getString("nombre_formula").trim()
                            + "---" + rs.getString("fecha").trim()
                            + "---" + rs.getString("lote").trim()
                            + "---" + rs.getString("lectura_1").trim()
                            + "---" + rs.getString("lectura_2").trim()
                            + "---" + rs.getString("lectura_3").trim()
                            + "---" + rs.getString("lectura_4").trim()
                            + "---" + rs.getString("concepto").trim()
                            + "---" + rs.getString("responsable").trim()
                            + "---" + rs.getString("promedio").trim()
                            + "---" + rs.getString("max").trim()
                            + "---" + rs.getString("min").trim() + "////");
                    count++;
                }
                conn.close();
                return lst_durezas;
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

    public List Traer_lote_control_formulas(String lte) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("ServerControlFormulas");
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
                List lst_parametroCF = ParametroJpa.ConsultarParametrosxCategoria("TraerLoteControlFormulas");
                if (lst_parametroCF != null) {
                    Object[] obj_param = (Object[]) lst_parametroCF.get(0);
                    query = obj_param[2].toString();
                    query = query.replace("xxxLotexxx", "'" + lte + "'");
                } else {
                    return null;
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_lotecontrol = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_lotecontrol.add(count, rs.getString("id_registro").trim()
                            + "---" + rs.getString("id_formula").trim()
                            + "---" + rs.getString("lote_generacion").trim() + "////");
                    count++;
                }
                conn.close();
                return lst_lotecontrol;
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

    public List Traer_lote_control_durezas(String lte) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("ServerControlFormulas");
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
                List lst_parametroCF = ParametroJpa.ConsultarParametrosxCategoria("TraerLoteDurezasFormulas");
                if (lst_parametroCF != null) {
                    Object[] obj_param = (Object[]) lst_parametroCF.get(0);
                    query = obj_param[2].toString();
                    query = query.replace("xxxLotexxx", "'" + lte + "'");
                } else {
                    return null;
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_lotecontrol = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_lotecontrol.add(count, rs.getString("lote").trim()
                            + "---" + rs.getString("promedio").trim()
                            + "---" + rs.getString("max").trim()
                            + "---" + rs.getString("min").trim() + "////");
                    count++;
                }
                conn.close();
                return lst_lotecontrol;
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

    public List PromedioLecturasFormulas(String lte) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("ServerControlFormulas");
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
                query = "(SELECT round(((d.lectura_1+d.lectura_2+d.lectura_3+d.lectura_4)/4),3) as 'Promedio' from control_formulas.dureza d WHERE d.lote = '" + lte + "')";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_lotecontrol = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_lotecontrol.add(count, rs.getString("Promedio").trim());
                    count++;
                }
                conn.close();
                return lst_lotecontrol;
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
