package Metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Controladores.SoporteJpaController;

public class Server_redeac {

    SoporteJpaController SupportJpa = new SoporteJpaController();
    List lst_support = SupportJpa.Consult_server("SeverRedeac");

    static String login = "";
    static String password = "";
    static String url = "";

    //<editor-fold defaultstate="collapsed" desc="LIST">
    public List consulCase(int idReport) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_support != null) {
                try {
                    Object[] obj_sppr = (Object[]) lst_support.get(0);
                    String[] DataServer = obj_sppr[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0].toString();
                    password = DataServer[1].toString();
                    url = "jdbc:mysql://" + DataServer[2].toString() + ":" + DataServer[3].toString() + "/" + DataServer[4].toString();
                    conn = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                    conn = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                conn = DriverManager.getConnection(url, login, password);

            }
            if (conn != null) {
                String query = "SELECT c.id_caso, c.fecha_envio, c.id_area, a.nombre, c.id_tecnico_asignado, "
                        + "c.id_reportante, c.solicitud, c.prioridad, "
                        + "IF(c.id_tipo_soporte IS NULL, 'N/A', c.id_tipo_soporte) AS 'id_soporte', "
                        + "IF(c.fecha_ejecucion IS NULL, 'N/A', c.fecha_ejecucion) AS 'fecha_ejecucion', "
                        + "IF(c.id_tecnico_solucion IS NULL, 'N/A', c.id_tecnico_solucion) AS 'id_tecnico_sol', "
                        + "if(c.id_tecnico_solucion IS NULL, 'N/A', "
                        + "(SELECT CONCAT(u.nombres, ' ', u.apellidos) FROM usuario u WHERE u.id_usuario = c.id_tecnico_solucion))  "
                        + "AS 'nombre_tecnico', "
                        + "IF(c.fecha_solucion IS NULL, 'N/A', c.fecha_solucion) AS 'fecha_solucion', "
                        + "IF(c.solucion IS NULL, 'N/A', c.solucion) AS 'solucion', "
                        + "c.parada_equipo, c.parada_produccion,  "
                        + "IF(c.id_equipo IS NULL, 'N/A', c.id_equipo) AS 'id_equipo', "
                        + "IF(c.id_equipo_v IS NULL, 'N/A', c.id_equipo_v) AS 'id_equipo_v', "
                        + "c.bitacora, c.puntuacion,  "
                        + "if(c.opinion IS NULL, 'N/A',c.opinion) AS 'opinion', "
                        + "if(c.firma_calificacion IS NULL, 'N/A', c.firma_calificacion) AS 'firma_califi' "
                        + "FROM caso c "
                        + "INNER JOIN area a ON c.id_area = a.id_area "
                        + "WHERE c.id_reportante = " + idReport + " "
                        + "ORDER BY c.fecha_envio DESC ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_seriales = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_seriales.add(count, rs.getString("id_caso").toString().trim()
                            + "---" + rs.getString("fecha_envio").toString().trim()
                            + "---" + rs.getString("id_area").toString().trim()
                            + "---" + rs.getString("nombre").toString().trim()
                            + "---" + rs.getString("id_tecnico_asignado").toString().trim()
                            + "---" + rs.getString("id_reportante").trim()
                            + "---" + rs.getString("solicitud").toString().trim()
                            + "---" + rs.getString("prioridad").toString().trim()
                            + "---" + rs.getString("id_soporte").toString().trim()
                            + "---" + rs.getString("fecha_ejecucion").toString().trim()
                            + "---" + rs.getString("id_tecnico_sol").toString().trim()
                            + "---" + rs.getString("nombre_tecnico").toString().trim()
                            + "---" + rs.getString("fecha_solucion").toString().trim()
                            + "---" + rs.getString("solucion").toString().trim()
                            + "---" + rs.getString("parada_equipo").toString().trim()
                            + "---" + rs.getString("parada_produccion").toString().trim()
                            + "---" + rs.getString("id_equipo").toString().trim()
                            + "---" + rs.getString("id_equipo_v").toString().trim()
                            + "---" + rs.getString("bitacora").toString().trim()
                            + "---" + rs.getString("puntuacion").toString().trim()
                            + "---" + rs.getString("opinion").toString().trim()
                            + "---" + rs.getString("firma_califi").toString().trim() + "////");
                    count++;
                }
                conn.close();
                return lst_seriales;
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

    public List consulCaseId(int idSupport) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_support != null) {
                try {
                    Object[] obj_sppr = (Object[]) lst_support.get(0);
                    String[] DataServer = obj_sppr[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0].toString();
                    password = DataServer[1].toString();
                    url = "jdbc:mysql://" + DataServer[2].toString() + ":" + DataServer[3].toString() + "/" + DataServer[4].toString();
                    conn = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                    conn = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                conn = DriverManager.getConnection(url, login, password);

            }
            if (conn != null) {
                String query = "SELECT c.id_caso, c.fecha_envio, c.id_area, a.nombre, c.id_tecnico_asignado, "
                        + "c.id_reportante, c.solicitud, c.prioridad, "
                        + "IF(c.id_tipo_soporte IS NULL, 'N/A', c.id_tipo_soporte) AS 'id_soporte', "
                        + "IF(c.fecha_ejecucion IS NULL, 'N/A', c.fecha_ejecucion) AS 'fecha_ejecucion', "
                        + "IF(c.id_tecnico_solucion IS NULL, 'N/A', c.id_tecnico_solucion) AS 'id_tecnico_sol', "
                        + "if(c.id_tecnico_solucion IS NULL, 'N/A', "
                        + "(SELECT CONCAT(u.nombres, ' ', u.apellidos) FROM usuario u WHERE u.id_usuario = c.id_tecnico_solucion))  "
                        + "AS 'nombre_tecnico', "
                        + "IF(c.fecha_solucion IS NULL, 'N/A', c.fecha_solucion) AS 'fecha_solucion', "
                        + "IF(c.solucion IS NULL, 'N/A', c.solucion) AS 'solucion', "
                        + "c.parada_equipo, c.parada_produccion,  "
                        + "IF(c.id_equipo IS NULL, 'N/A', c.id_equipo) AS 'id_equipo', "
                        + "IF(c.id_equipo_v IS NULL, 'N/A', c.id_equipo_v) AS 'id_equipo_v', "
                        + "c.bitacora, c.puntuacion,  "
                        + "if(c.opinion IS NULL, 'N/A',c.opinion) AS 'opinion', "
                        + "if(c.firma_calificacion IS NULL, 'N/A', c.firma_calificacion) AS 'firma_califi' "
                        + "FROM caso c "
                        + "INNER JOIN area a ON c.id_area = a.id_area "
                        + "WHERE c.id_caso = " + idSupport + "";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_seriales = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_seriales.add(count, rs.getString("id_caso").toString().trim()
                            + "---" + rs.getString("fecha_envio").toString().trim()
                            + "---" + rs.getString("id_area").toString().trim()
                            + "---" + rs.getString("nombre").toString().trim()
                            + "---" + rs.getString("id_tecnico_asignado").toString().trim()
                            + "---" + rs.getString("id_reportante").trim()
                            + "---" + rs.getString("solicitud").toString().trim()
                            + "---" + rs.getString("prioridad").toString().trim()
                            + "---" + rs.getString("id_soporte").toString().trim()
                            + "---" + rs.getString("fecha_ejecucion").toString().trim()
                            + "---" + rs.getString("id_tecnico_sol").toString().trim()
                            + "---" + rs.getString("nombre_tecnico").toString().trim()
                            + "---" + rs.getString("fecha_solucion").toString().trim()
                            + "---" + rs.getString("solucion").toString().trim()
                            + "---" + rs.getString("parada_equipo").toString().trim()
                            + "---" + rs.getString("parada_produccion").toString().trim()
                            + "---" + rs.getString("id_equipo").toString().trim()
                            + "---" + rs.getString("id_equipo_v").toString().trim()
                            + "---" + rs.getString("bitacora").toString().trim()
                            + "---" + rs.getString("puntuacion").toString().trim()
                            + "---" + rs.getString("opinion").toString().trim()
                            + "---" + rs.getString("firma_califi").toString().trim() + "////");
                    count++;
                }
                conn.close();
                return lst_seriales;
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

    public List consulReportant(int nroDocu) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_support != null) {
                try {
                    Object[] obj_sppr = (Object[]) lst_support.get(0);
                    String[] DataServer = obj_sppr[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0].toString();
                    password = DataServer[1].toString();
                    url = "jdbc:mysql://" + DataServer[2].toString() + ":" + DataServer[3].toString() + "/" + DataServer[4].toString();
                    conn = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                    conn = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                conn = DriverManager.getConnection(url, login, password);
            }
            if (conn != null) {
                String query = "SELECT r.id_reportante, r.nombre_reportante, r.correo, r.id_area, r.documento, r.codigo, r.fecha_registro "
                        + "FROM reportante r "
                        + "WHERE r.documento = " + nroDocu + "";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_seriales = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_seriales.add(count, rs.getString("id_reportante").toString().trim()
                            + "---" + rs.getString("nombre_reportante").toString().trim()
                            + "---" + rs.getString("correo").toString().trim()
                            + "---" + rs.getString("id_area").toString().trim()
                            + "---" + rs.getString("documento").toString().trim()
                            + "---" + rs.getString("codigo").toString().trim()
                            + "---" + rs.getString("fecha_registro").trim() + "////");
                    count++;
                }
                conn.close();
                return lst_seriales;
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

    public List consulArea() throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_support != null) {
                try {
                    Object[] obj_sppr = (Object[]) lst_support.get(0);
                    String[] DataServer = obj_sppr[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0].toString();
                    password = DataServer[1].toString();
                    url = "jdbc:mysql://" + DataServer[2].toString() + ":" + DataServer[3].toString() + "/" + DataServer[4].toString();
                    conn = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                    conn = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                conn = DriverManager.getConnection(url, login, password);
            }
            if (conn != null) {
                String query = "SELECT a.id_area, a.nombre, a.Sigla, a.estado "
                        + "FROM area a ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_seriales = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_seriales.add(count, rs.getString("id_area").toString().trim()
                            + "---" + rs.getString("nombre").toString().trim()
                            + "---" + rs.getString("Sigla").toString().trim()
                            + "---" + rs.getString("estado").toString().trim() + "////");
                    count++;
                }
                conn.close();
                return lst_seriales;
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

    public List consulUserShift() throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_support != null) {
                try {
                    Object[] obj_sppr = (Object[]) lst_support.get(0);
                    String[] DataServer = obj_sppr[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0].toString();
                    password = DataServer[1].toString();
                    url = "jdbc:mysql://" + DataServer[2].toString() + ":" + DataServer[3].toString() + "/" + DataServer[4].toString();
                    conn = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                    conn = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                conn = DriverManager.getConnection(url, login, password);
            }
            if (conn != null) {
                String query = "select u.id_usuario,u.nombres,u.apellidos,u.documento,u.codigo,u.usuario,u.id_rol,r.nombre,u.correo,u.estado,u.turno "
                        + "from usuario u "
                        + "inner join rol r on u.id_rol = r.id_rol "
                        + "where u.turno = 1 ";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_seriales = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_seriales.add(count, rs.getString("id_usuario").toString().trim()
                            + "---" + rs.getString("nombres").toString().trim()
                            + "---" + rs.getString("apellidos").toString().trim()
                            + "---" + rs.getString("documento").toString().trim()
                            + "---" + rs.getString("codigo").toString().trim()
                            + "---" + rs.getString("usuario").toString().trim()
                            + "---" + rs.getString("id_rol").toString().trim()
                            + "---" + rs.getString("nombre").toString().trim()
                            + "---" + rs.getString("correo").toString().trim()
                            + "---" + rs.getString("estado").toString().trim()
                            + "---" + rs.getString("turno").toString().trim() + "////");
                    count++;
                }
                conn.close();
                return lst_seriales;
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

    public List consulMail(String module) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_support != null) {
                try {
                    Object[] obj_sppr = (Object[]) lst_support.get(0);
                    String[] DataServer = obj_sppr[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0].toString();
                    password = DataServer[1].toString();
                    url = "jdbc:mysql://" + DataServer[2].toString() + ":" + DataServer[3].toString() + "/" + DataServer[4].toString();
                    conn = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                    conn = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                conn = DriverManager.getConnection(url, login, password);

            }
            if (conn != null) {
                String query = "select c.id_correo,c.funcion,c.emisor,c.password,c.receptor,c.estado,c.host,c.port,c.usuario_registro,c.fecha_registro "
                        + "from correo c "
                        + "where c.funcion = '" + module + "' and c.estado = 1";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_seriales = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_seriales.add(count, rs.getString("id_correo").toString().trim()
                            + "---" + rs.getString("funcion").toString().trim()
                            + "---" + rs.getString("emisor").toString().trim()
                            + "---" + rs.getString("password").toString().trim()
                            + "---" + rs.getString("receptor").toString().trim()
                            + "---" + rs.getString("estado").toString().trim()
                            + "---" + rs.getString("host").toString().trim()
                            + "---" + rs.getString("port").toString().trim()
                            + "---" + rs.getString("usuario_registro").toString().trim()
                            + "---" + rs.getString("fecha_registro").toString().trim() + "////");
                    count++;
                }
                conn.close();
                return lst_seriales;
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

    public List consul_caseMail(int idArea, int idUserRe) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_support != null) {
                try {
                    Object[] obj_sppr = (Object[]) lst_support.get(0);
                    String[] DataServer = obj_sppr[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0].toString();
                    password = DataServer[1].toString();
                    url = "jdbc:mysql://" + DataServer[2].toString() + ":" + DataServer[3].toString() + "/" + DataServer[4].toString();
                    conn = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                    conn = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                conn = DriverManager.getConnection(url, login, password);
            }
            if (conn != null) {
                String query = "select c.id_caso,DATE_FORMAT(c.fecha_envio, '%Y/%m/%d | %H:%i') AS 'fecha_envio',a.id_area,a.nombre, "
                        + "r.nombre_reportante,REPLACE(REPLACE(TILDES_HTML(STRIP_TAGS(c.solicitud)),\"Asunto\",\"Asunto: \"),\"->\",\"\") AS 'Asunto',c.prioridad, "
                        + "r.correo "
                        + "from caso c "
                        + "inner join area a on c.id_area = a.id_area "
                        + "inner join reportante r on c.id_reportante = r.id_reportante "
                        + "where c.id_area = " + idArea + " and c.id_reportante = " + idUserRe + " "
                        + "order by c.id_caso desc "
                        + "limit 1";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_seriales = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_seriales.add(count, rs.getString("id_caso").toString().trim()
                            + "---" + rs.getString("fecha_envio").toString().trim()
                            + "---" + rs.getString("id_area").toString().trim()
                            + "---" + rs.getString("nombre").toString().trim()
                            + "---" + rs.getString("nombre_reportante").toString().trim()
                            + "---" + rs.getString("Asunto").toString().trim()
                            + "---" + rs.getString("prioridad").toString().trim()
                            + "---" + rs.getString("correo").toString().trim() + "////");
                    count++;
                }
                conn.close();
                return lst_seriales;
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

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PROCESS">
    public boolean RegisterReportant(String nmReport, String mail, int idArea, int docume, int codex) throws Exception {
        try {
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_support != null) {
                try {
                    Object[] obj_sppr = (Object[]) lst_support.get(0);
                    String[] DataServer = obj_sppr[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0].toString();
                    password = DataServer[1].toString();
                    url = "jdbc:mysql://" + DataServer[2].toString() + ":" + DataServer[3].toString() + "/" + DataServer[4].toString();
                    con = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                    con = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                con = DriverManager.getConnection(url, login, password);
            }
            Statement sttm = con.createStatement();
            sttm.executeUpdate("INSERT INTO reportante (nombre_reportante,correo,id_area,documento,codigo) "
                    + "VALUES ('" + nmReport + "', '" + mail + "', " + idArea + ", " + docume + ", " + codex + ")");
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
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean RegisterCase(int id_area, String Tecnicos, int idReport, String solic, String prior) throws Exception {
        try {
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (lst_support != null) {
                try {
                    Object[] obj_sppr = (Object[]) lst_support.get(0);
                    String[] DataServer = obj_sppr[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    login = DataServer[0].toString();
                    password = DataServer[1].toString();
                    url = "jdbc:mysql://" + DataServer[2].toString() + ":" + DataServer[3].toString() + "/" + DataServer[4].toString();
                    con = DriverManager.getConnection(url, login, password);
                } catch (Exception e) {
                    login = "APPS";
                    password = "Sirh";
                    url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                    con = DriverManager.getConnection(url, login, password);
                }
            } else {
                login = "APPS";
                password = "Sirh";
                url = "jdbc:mysql://172.16.2.117:3306/registro_actividades";
                con = DriverManager.getConnection(url, login, password);
            }
            Statement sttm = con.createStatement();
            sttm.executeUpdate("INSERT INTO caso (id_area,id_tecnico_asignado,id_reportante,solicitud,prioridad) "
                    + "VALUES (" + id_area + ", '" + Tecnicos + "', " + idReport + ", '" + solic + "', '" + prior + "')");
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
        } catch (Exception ex) {
            return false;
        }
    }
//</editor-fold>

}
