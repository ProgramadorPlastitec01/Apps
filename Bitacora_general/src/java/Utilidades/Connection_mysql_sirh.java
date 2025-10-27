package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Controladoras.UsuarioJpaController;

public class Connection_mysql_sirh {
    
    UsuarioJpaController JpaUsuario = new UsuarioJpaController();
    static String login = "";
    static String password = "";
    static String url = "";
    
    public List Empleado_sirh(int doc, int codigo) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            List lst_parametros = JpaUsuario.ConsultaParametros("ServerSIRH");
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
                List lst_parametros2 = JpaUsuario.ConsultaParametros("ConsultaSIRH");
                String query = "";
                if (lst_parametros2 != null) {
                    Object[] obj_parametro2 = (Object[]) lst_parametros2.get(0);
                    query = obj_parametro2[2].toString().replace("dcm", String.valueOf(doc)).replace("cdg", String.valueOf(codigo));
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_documentos = new ArrayList<String>();
                
                if (rs.next()) {
                    lst_documentos.add(rs.getString("nombres").toString().trim() + " " + rs.getString("apellidos").toString().trim() + " - " + rs.getString("codigo_firma").toString().trim());
                } else {
                    return null;
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
