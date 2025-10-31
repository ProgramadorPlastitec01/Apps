package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Conexion_Factory {

    public List ConsultaVersionFT(int Op) throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_INV", "sa", "plast");
            String query = "SELECT p.OP as 'Orden',p.COD as 'Codigo', m.NOM as 'Producto',CAST(p.NPLAN as decimal (10,0)) as 'Plan',p.LOTE as 'Lote',p.CEN as 'Centro', "
                    + "d.NOM as 'Centro_costo',CAST(p.CANTP as decimal(15,0)) as 'Cantidad_prog',p.CANTE as 'Cantidad_final',M.UD as 'Unidad',P.FECHA_I as 'Fecha_i', "
                    + "p.FECHA_T as 'Fecha_f', (SELECT o.CustomerName FROM [TagsLogic].[dbo].[Order] o WHERE CAST(p.NPLAN as decimal (10,0)) = o.OrderNumber) as 'Cliente' ,m.CMPFT as 'Ficha_tecnica', m.CMPVER_FT as 'Version_FT'  "
                    + "FROM OP p  "
                    + "INNER JOIN MAESTRO m ON p.COD = m.COD  "
                    + "INNER JOIN DPTO d ON p.CEN = d.COD "
                    + "WHERE p.NPLAN = " + Op + "";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_orden_factory = new ArrayList<String>();
            int count = 0;
            while (rs.next()) {
                lst_orden_factory.add(count, rs.getString("Orden") + " / " + rs.getString("Codigo") + " / " + rs.getString("Producto") + ""
                        + " / " + rs.getString("Plan") + " / " + rs.getString("Lote") + " / " + rs.getString("Centro") + " / "
                        + " / " + rs.getString("Centro_costo") + " / " + rs.getString("Cantidad_prog") + " / " + rs.getString("Cantidad_final")
                        + " / " + rs.getString("Unidad") + " / " + rs.getString("Fecha_i") + " / " + rs.getString("Fecha_f") + " / " + rs.getString("Cliente")
                        + " / " + rs.getString("Ficha_tecnica") + " / " + rs.getString("Version_FT"));
                count++;
            }
            con.close();
            return lst_orden_factory;
        } catch (Exception ex) {
            return null;
        }
    }

    public List ConsultaCodigosProducto(String cod) throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_INV", "sa", "plast");
            String query = "select p.COD as 'Codigo', m.NOM as 'Producto' "
                    + "from op p "
                    + "inner join MAESTRO m on p.COD = m.COD "
                    + "where p.COD like '%" + cod + "' "
                    + "group by p.COD, m.NOM";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_orden_factory = new ArrayList<String>();
            int count = 0;
            while (rs.next()) {
                lst_orden_factory.add(count, rs.getString("Codigo") + " / " + rs.getString("Producto"));
                count++;
            }
            con.close();
            return lst_orden_factory;
        } catch (Exception ex) {
            return null;
        }
    }

    public List ConsultaCodigosCod(String cod) throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_INV", "sa", "plast");
            String query = "select m.COD as 'codigo', m.NOM as 'Producto', CMPFT, CMPVER_FT "
                    + "from MAESTRO m  "
                    + "where m.COD like '%" + cod + "' ";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_orden_factory = new ArrayList<String>();
            int count = 0;
            while (rs.next()) {
                lst_orden_factory.add(count, rs.getString("Codigo").trim() + " / " + rs.getString("Producto").trim() + " / " + rs.getString("CMPFT").trim() + " / " + rs.getString("CMPVER_FT").trim());
                count++;
            }
            con.close();
            return lst_orden_factory;
        } catch (Exception ex) {
            return null;
        }
    }

    public List ConsultaCodigosProductoMaestro(String cod) throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_INV", "sa", "plast");
            String query = "select m.COD as 'Codigo', m.NOM as 'Producto' "
                    + "from MAESTRO m on p.COD = m.COD "
                    + "where m.COD like '%" + cod + "%' "
                    + "order by 1 desc";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_orden_factory = new ArrayList<String>();
            int count = 0;
            while (rs.next()) {
                lst_orden_factory.add(count, rs.getString("Codigo") + " / " + rs.getString("Producto"));
                count++;
            }
            con.close();
            return lst_orden_factory;
        } catch (Exception ex) {
            return null;
        }
    }

    public List ConsultaClientes() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_FACT", "sa", "plast");
            String query = "SELECT c.COD as 'Codigo', c.NOM as 'Cliente' FROM CLIENTES c GROUP BY c.nom, c.COD ORDER BY c.nom ASC";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_orden_factory = new ArrayList<String>();
            int count = 0;
            while (rs.next()) {
                lst_orden_factory.add(count, rs.getString("Codigo").trim() + " / " + rs.getString("Cliente").trim());
                count++;
            }
            con.close();
            return lst_orden_factory;
        } catch (Exception ex) {
            return null;
        }
    }
    public List ConsultaCodigosEntrada(String cod) throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_INV", "sa", "plast");
            String query = "select m.COD, m.NOM "
                    + "from MAESTRO m  "
                    + "where p.COD like '%" + cod + "' ";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_orden_factory = new ArrayList<String>();
            int count = 0;
            while (rs.next()) {
                lst_orden_factory.add(count, rs.getString("Codigo") + " /// " + rs.getString("Producto"));
                count++;
            }
            con.close();
            return lst_orden_factory;
        } catch (Exception ex) {
            return null;
        }
    }
}
