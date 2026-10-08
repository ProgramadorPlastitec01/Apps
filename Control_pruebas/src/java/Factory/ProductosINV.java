package Factory;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//el resto
import java.util.ArrayList;
import java.util.List;

public class ProductosINV {

    public List Productos(int cdg) {
        InetAddress ping;
        String ip = "172.16.2.116"; // Ip de la máquina remota
        try {
            ping = InetAddress.getByName(ip);
            if (ping.isReachable(1500)) {
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_INV", "sa", "plast");
                    String query = "SELECT COD,NOM FROM MAESTRO WHERE COD LIKE '%" + cdg + "' AND GRUP LIKE '2%'";
                    //String query = "SELECT COD,NOM FROM MAESTRO WHERE COD LIKE '%" + cdg + "'";
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
                    return null;
                }
            } else {
                return null;
            }
        } catch (IOException ex) {
            return null;
        }
    }
}
