package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//RETORNOS
import java.util.ArrayList;
import java.util.List;
//PING
import java.net.InetAddress;
import java.io.IOException;

public class ClientesFACT {

    public List Clientes() {
        InetAddress ping;
        String ip = "172.16.2.116"; // Ip de la máquina remota
        try {
            ping = InetAddress.getByName(ip);
            if (ping.isReachable(1100)) {
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_FACT", "sa", "plast");
                    String query = "SELECT COD,NOM FROM CLIENTES ORDER BY NOM ASC";
                    Statement sttm = con.createStatement();
                    ResultSet rs = sttm.executeQuery(query);
                    List<String> lst_clientes = new ArrayList<String>();
                    int count = 0;
                    while (rs.next()) {
                        lst_clientes.add(count, rs.getString("NOM").toString().trim());
                        count++;
                    }
                    con.close();
                    return lst_clientes;
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
