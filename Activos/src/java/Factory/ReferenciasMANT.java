package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//el resto
import java.util.ArrayList;
import java.util.List;

public class ReferenciasMANT {

    public List Productos() throws Exception {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://172.16.2.119:1433;databaseName=EMP002_MANT;encrypt=false;trustServerCertificate=true;", "sa", "plast");
            Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://172.16.2.119:1433/EMP002_MANT;user=sa;password=plast;");
            String query = "SELECT COD,NOM FROM MAESTRO WHERE GRUP LIKE 'M%' ";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_productos = new ArrayList<String>();
            int count = 0;
            while (rs.next()) {
                lst_productos.add(count, rs.getString("COD").toString().trim() + "_" + rs.getString("NOM").toString().trim());
                count++;
            }
            con.close();
            return lst_productos;
        } catch (Exception ex) {
//            return null;
            ex.printStackTrace(); 
            throw ex;
        }
    }

    public List StockMinimo() throws Exception {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://172.16.2.119:1433;databaseName=EMP002_MANT;encrypt=false;trustServerCertificate=true;", "sa", "plast");
            Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://172.16.2.119:1433/EMP002_MANT;user=sa;password=plast;");
            String query = "select case "
                    + "when (n.EXIST - m.MINIMO) = 0 "
                    + "then 'Alerta' "
                    + "else 'Stock' "
                    + "end as 'Estado',m.COD,m.NOM,m.MINIMO, n.EXIST "
                    + "from MAESTRO m "
                    + "INNER JOIN MAESTRO1 n on m.COD = n.COD "
                    + "where ALMA LIKE 'MI'";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_productos = new ArrayList<String>();
            int count = 0;
            while (rs.next()) {
                lst_productos.add(count, "<td>" + rs.getString("ESTADO").toString().trim() + "</td>"
                        + "<td><b>" + rs.getString("COD").toString().trim() + "</b></td>"
                        + "<td>" + rs.getString("NOM").toString().trim() + "</td>"
                        + "<td>" + rs.getString("MINIMO").toString().trim() + "</td>"
                        + "<td>" + rs.getString("EXIST").toString().trim());
                count++;
            }
            con.close();
            return lst_productos;
        } catch (Exception ex) {
//            return null;
            ex.printStackTrace(); 
            throw ex;
        }
    }
}
