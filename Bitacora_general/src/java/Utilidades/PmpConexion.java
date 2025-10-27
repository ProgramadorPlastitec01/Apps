package Utilidades;

import Controladoras.UsuarioJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PmpConexion {

    UsuarioJpaController JpaUsuario = new UsuarioJpaController();
    static String login = "";
    static String password = "";
    static String url = "";

    public List consultaZonas() throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametros = JpaUsuario.ConsultaParametros("ServerPMP_MTF");
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
                List lst_parametros2 = JpaUsuario.ConsultaParametros("ConsultaPMP_MTF");
                String query = "";
                if (lst_parametros2 != null) {
                    Object[] obj_parametro2 = (Object[]) lst_parametros2.get(0);
                    query = obj_parametro2[2].toString();
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lstZona = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lstZona.add(count, rs.getString("id_zona").toString().trim() + "---" + rs.getString("zona").toString().trim() + "///");
                    count++;
                }
                conn.close();
                return lstZona;
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

    public List consultaLineas(String idZona) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametros = JpaUsuario.ConsultaParametros("ServerPMP_MTF");
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
                List lst_parametros2 = JpaUsuario.ConsultaParametros("ConsultaPMP_MTF_IZN");
                String query = "";
                if (lst_parametros2 != null) {
                    Object[] obj_parametro2 = (Object[]) lst_parametros2.get(0);
                    query = obj_parametro2[2].toString().replace("izn", idZona);
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lstLinea = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lstLinea.add(count, rs.getString("id_linea").toString().trim() + "---" + rs.getString("nombre").toString().trim() + "///");
                    count++;
                }
                conn.close();
                return lstLinea;
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

    public List consultaLineasId(String idLn) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametros = JpaUsuario.ConsultaParametros("ServerPMP_MTF");
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
                List lst_parametros2 = JpaUsuario.ConsultaParametros("ConsultaPMP_MTF_ILN");
                String query = "";
                if (lst_parametros2 != null) {
                    Object[] obj_parametro2 = (Object[]) lst_parametros2.get(0);
                    query = obj_parametro2[2].toString().replace("idLn", idLn);
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lstLinea = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lstLinea.add(count, rs.getString("id_linea").toString().trim() + "---" + rs.getString("nombre").toString().trim() + "///");
                    count++;
                }
                conn.close();
                return lstLinea;
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

    public List consultaZonaEquipo(String idZona) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametros = JpaUsuario.ConsultaParametros("ServerPMP_MTF");
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
                List lst_parametros2 = JpaUsuario.ConsultaParametros("ConsultaPMP_MTF_ZONAEQUIPOS");
                String query = "";
                if (lst_parametros2 != null) {
                    Object[] obj_parametro2 = (Object[]) lst_parametros2.get(0);
                    query = obj_parametro2[2].toString().replace("izn", idZona);
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lstLinea = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lstLinea.add(count, rs.getString("e.id_equipo").toString().trim() + "---" + rs.getString("e.nombre").toString().trim() + "///");
                    count++;
                }
                conn.close();
                return lstLinea;
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

    public List consultaZonasId(String id) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametros = JpaUsuario.ConsultaParametros("ServerPMP_MTF");
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
                List lst_parametros2 = JpaUsuario.ConsultaParametros("ConsultaPMP_MTF_ZONA_ID");
                String query = "";
                if (lst_parametros2 != null) {
                    Object[] obj_parametro2 = (Object[]) lst_parametros2.get(0);
                    query = obj_parametro2[2].toString().replace("izn", id);
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lstZona = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lstZona.add(rs.getString("zona").toString().trim());
                    count++;
                }
                conn.close();
                return lstZona;
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

    public List consultaEquipoId(String idequipo) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametros = JpaUsuario.ConsultaParametros("ServerPMP_MTF");
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
                List lst_parametros2 = JpaUsuario.ConsultaParametros("ConsultarPMP_MTF_EQUIPO");
                String query = "";
                if (lst_parametros2 != null) {
                    Object[] obj_parametro2 = (Object[]) lst_parametros2.get(0);
                    query = obj_parametro2[2].toString().replace("idequipo", idequipo);
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lstLinea = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lstLinea.add(count, rs.getString("id_equipo").toString().trim() + "---" + rs.getString("nombre").toString().trim() + "///");
                    count++;
                }
                conn.close();
                return lstLinea;
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
