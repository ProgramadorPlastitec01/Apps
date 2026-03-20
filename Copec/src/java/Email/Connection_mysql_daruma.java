package Email;

import Controlador.PreguntaJpaController;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Connection_mysql_daruma {

    private static final Logger logger = LoggerFactory.getLogger(Connection_mysql_daruma.class);
    private PreguntaJpaController jpapregunta = new PreguntaJpaController();
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:mysql://172.16.2.117:3306/registro_actividades"
                + "?useSSL=false"
                + "&useUnicode=true"
                + "&characterEncoding=UTF-8"
        );
        config.setUsername("APPS");
        config.setPassword("Sirh");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(60000);

        dataSource = new HikariDataSource(config);
    }

    private Connection getConnection() throws SQLException {
        List<?> lst_server = jpapregunta.Consult_server("ServerLinux");
        if (lst_server != null && !lst_server.isEmpty()) {
            Object[] obj_sppr = (Object[]) lst_server.get(0);
            String[] DataServer = obj_sppr[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            String login = DataServer[0];
            String password = DataServer[1];
            String url = "jdbc:mysql://" + DataServer[2] + ":" + DataServer[3] + "/" + DataServer[4];
            return DriverManager.getConnection(url, login, password);
        } else {
            return dataSource.getConnection();
        }
    }

    public List<String> ConsultaDocumento(String codigo, int version) throws Exception {
        String query = "SELECT id, document_index, code, name FROM document_base WHERE code = ? AND version = ? AND state = 6";
        List<String> lst_documentos = new ArrayList<String>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, codigo);
            pstmt.setInt(2, version);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                lst_documentos.add(rs.getString("id").trim() + " / " + rs.getString("document_index").trim());
            }
        } catch (SQLException e) {
            logger.error("Error en consulta: {}", e.getMessage());
            throw new Exception("Error en consulta", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar ResultSet: {}", e.getMessage());
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar PreparedStatement: {}", e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar Connection: {}", e.getMessage());
                }
            }
        }

        return lst_documentos;
    }

    public List<String> ConsultaDocumentosArea(int id_area) throws Exception {

        String query
                = "SELECT GROUP_CONCAT( "
                + "    CONCAT('[', d.code, '//', d.version, ']') "
                + "    ORDER BY d.code ASC SEPARATOR ',' "
                + ") AS documentos "
                + "FROM document_base d "
                + "WHERE d.state = 6 "
                + "AND d.is_format = 0 "
                + "AND d.code_department_id = ?";

        List<String> lst_documentos = new ArrayList<String>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id_area);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                lst_documentos.add(rs.getString("documentos").trim());
            }
        } catch (SQLException e) {
            logger.error("Error en consulta: {}", e.getMessage());
            throw new Exception("Error en consulta", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar ResultSet: {}", e.getMessage());
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar PreparedStatement: {}", e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar Connection: {}", e.getMessage());
                }
            }
        }

        return lst_documentos;
    }

    public List<String> ConsultaDocumentosAreaLO() throws Exception {
        String query = "SELECT GROUP_CONCAT(CONCAT('[', d.code, '//', d.version, ']') ORDER BY d.code ASC) AS documentos FROM document_base d WHERE d.state = 6 AND d.is_format = 0 AND (d.code_department_id = 13 OR d.code_department_id = 11)";
        List<String> lst_documentos = new ArrayList<String>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                lst_documentos.add(rs.getString("documentos").trim());
            }
        } catch (SQLException e) {
            logger.error("Error en consulta: {}", e.getMessage());
            throw new Exception("Error en consulta", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar ResultSet: {}", e.getMessage());
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar PreparedStatement: {}", e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar Connection: {}", e.getMessage());
                }
            }
        }

        return lst_documentos;
    }

    public List<String> ConsultaControlCDocumento(int id_documento, int version) throws Exception {
        String query = "SELECT d.document_index, d.version, d.version_at, d.changes, d.justification FROM document_datasheet d WHERE d.document_index = ? AND d.version = ?";
        List<String> lst_documentos = new ArrayList<String>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id_documento);
            pstmt.setInt(2, version);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                lst_documentos.add(rs.getString("document_index").trim() + " / "
                        + rs.getString("version").trim() + " / "
                        + rs.getString("version_at").trim() + " / "
                        + rs.getString("changes").trim() + " / "
                        + rs.getString("justification").trim());
            }
        } catch (SQLException e) {
            logger.error("Error en consulta: {}", e.getMessage());
            throw new Exception("Error en consulta", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar ResultSet: {}", e.getMessage());
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar PreparedStatement: {}", e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar Connection: {}", e.getMessage());
                }
            }
        }

        return lst_documentos;
    }
}
