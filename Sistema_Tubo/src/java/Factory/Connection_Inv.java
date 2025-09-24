package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Controladores.ParametrosJpaController;

public class Connection_Inv {

    ParametrosJpaController JpaParameter = new ParametrosJpaController();
    static String login = "";
    static String password = "";
    static String url = "";

    public List Productos(String cdg) throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            List lst_parameterAuth = JpaParameter.ConsultParametersCategory("ServerFactoryINV");
            if (lst_parameterAuth != null) {
                Object[] obj_auth = (Object[]) lst_parameterAuth.get(0);
                String[] DataServer = obj_auth[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                url = "jdbc:sqlserver://" + DataServer[0];
                login = DataServer[1];
                password = DataServer[2];
            } else {
                return null;
            }
            Connection con = DriverManager.getConnection(url, login, password);
            String query = "SELECT COD,NOM FROM MAESTRO WHERE COD LIKE '%" + cdg + "' AND (GRUP LIKE '3A%' OR GRUP LIKE 'A3%')";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_productos = new ArrayList<String>();
            int count = 0;
            while (rs.next()) {
                lst_productos.add(count, rs.getString("COD").toString().trim() + " //// " + rs.getString("NOM").toString().trim());
                count++;
            }
            con.close();
            return lst_productos;
        } catch (Exception ex) {
            return null;
        }
    }

    public List Clientes() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            List lst_parameterAuth = JpaParameter.ConsultParametersCategory("ServerFactoryFACT");
            if (lst_parameterAuth != null) {
                Object[] obj_auth = (Object[]) lst_parameterAuth.get(0);
                String[] DataServer = obj_auth[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                url = "jdbc:sqlserver://" + DataServer[0];
                login = DataServer[1];
                password = DataServer[2];
            } else {
                return null;
            }
            Connection con = DriverManager.getConnection(url, login, password);
            String query = "select COD,NOM from clientes ";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_productos = new ArrayList<String>();
            int count = 0;
            while (rs.next()) {
                lst_productos.add(count, rs.getString("COD").toString().trim() + " //// " + rs.getString("NOM").toString().trim());
                count++;
            }
            con.close();
            return lst_productos;
        } catch (Exception ex) {
            return null;
        }
    }
}
