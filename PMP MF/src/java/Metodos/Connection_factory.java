package Metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//el resto
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection_factory {

    public List Productos(String cdg) throws Exception {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_INV", "sa", "plast");
            Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://172.16.2.116:1433/EMP001_INV;user=sa;password=plast;");
            String query = "SELECT TOP(1) COD,NOM FROM MAESTRO WHERE COD LIKE '%" + cdg + "'";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_productos = new ArrayList<String>();
            int count = 0;
            while (rs.next()) {
                lst_productos.add(count, rs.getString("COD").toString().trim() + " / " + rs.getString("NOM").toString().trim());
                count++;
            }
            con.close();
            return lst_productos;
        } catch (Exception ex) {
            // Queda en el log de Tomcat (catalina) para diagnosticar fallos de conexion al ERP
            Logger.getLogger(Connection_factory.class.getName()).log(Level.SEVERE,
                    "No se pudo consultar el producto '" + cdg + "' en el ERP (172.16.2.116): " + ex, ex);
            return null;
        }
    }
}
