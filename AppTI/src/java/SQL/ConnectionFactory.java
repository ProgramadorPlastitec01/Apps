package SQL;

import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ConnectionFactory {

    public List ConsulNewReference(String Ref) throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_MANT", "sa", "plast");
            String query = "SELECT  k.COD as 'FactCod', m.NOM as 'NameProd', c.CPROV as 'codSupplier', c.NOMBRE as 'NameSupplier' "
                    + "FROM kardexhi k "
                    + "LEFT JOIN maestro m ON k.COD = m.COD "
                    + "LEFT JOIN [EMP001_COMP].[dbo].COMPRAS2 c ON k.OC = c.OC "
                    + "WHERE k.COD LIKE '" + Ref + "' AND k.tipo = '1051' and c.CPROV is not null "
                    + "GROUP BY k.COD, m.NOM, c.CPROV, c.NOMBRE";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_orden_factory = new ArrayList<String>();

            int count = 0;
            while (rs.next()) {
                lst_orden_factory.add(count, rs.getString("FactCod").trim() + " / " + rs.getString("NameProd").trim() + " / " + rs.getString("codSupplier").trim()
                        + " / " + rs.getString("NameSupplier").trim());
                count++;
            }
            return lst_orden_factory;
        } catch (Exception ex) {
            return null;
        }

    }

    public List ConsulReferenceENT(String Ref, String dtIn, String dtFn) throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_MANT", "sa", "plast");
            String query = "SELECT k.COD as 'Cod', k.DOC as 'Doc', CONVERT(DATE, k.FECHA) as 'Date', k.DES as 'desc', k.tipo as 'Type', cast(k.cant as INT) as 'cant' "
                    + "FROM kardex k "
                    + "WHERE k.COD LIKE '" + Ref + "%' AND (k.tipo = '8051' OR k.tipo = '1051' OR k.tipo = '3051' OR k.tipo = '1151') AND  k.FECHA BETWEEN CONVERT(DATETIME,'" + dtIn + "', 120) AND CONVERT(DATETIME,'" + dtFn + "', 120) "
                    + "GROUP BY k.FECHA, k.DES, k.COD, k.DOC, k.tipo, k.cant  "
                    + "UNION ALL  "
                    + "SELECT k.COD as 'Cod', k.DOC as 'Doc', CONVERT(DATE, k.FECHA) as 'Date', k.DES as 'desc', k.tipo as 'Type', cast(k.cant as INT) as 'Cantidad' "
                    + "FROM kardexa k "
                    + "WHERE k.COD LIKE '" + Ref + "%' AND (k.tipo = '8051' OR k.tipo = '1051' OR k.tipo = '3051' OR k.tipo = '1151') AND  k.FECHA BETWEEN CONVERT(DATETIME,'" + dtIn + "', 120) AND CONVERT(DATETIME,'" + dtFn + "', 120) "
                    + "GROUP BY k.FECHA, k.DES, k.COD, k.DOC, k.tipo, k.cant  "
                    + "UNION ALL "
                    + "SELECT k.COD as 'Cod', k.DOC as 'Doc', CONVERT(DATE, k.FECHA) as 'Date', k.DES as 'desc', k.tipo as 'Type', cast(k.cant as INT) as 'Cantidad' "
                    + "FROM kardexhi k "
                    + "WHERE k.COD LIKE '" + Ref + "%' AND (k.tipo = '8051' OR k.tipo = '1051' OR k.tipo = '3051' OR k.tipo = '1151') AND  k.FECHA BETWEEN CONVERT(DATETIME,'" + dtIn + "', 120) AND CONVERT(DATETIME,'" + dtFn + "', 120) "
                    + "GROUP BY k.FECHA, k.DES, k.COD, k.DOC, k.tipo, k.cant  "
                    + "ORDER BY k.DOC ASC ";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_orden_factory = new ArrayList<String>();

            int count = 0;
            while (rs.next()) {
                lst_orden_factory.add(count, rs.getString("Cod").trim() + " / " + rs.getString("Doc").trim() + " / " + rs.getString("Date").trim()
                        + " / " + rs.getString("desc").trim() + " / " + rs.getString("Type").trim() + " / " + rs.getString("cant").trim() + "---");
                count++;
            }
            return lst_orden_factory;
        } catch (Exception ex) {
            return null;
        }
    }

    public List ConsulReferenceSAL(String Ref, String dtIn, String dtFn) throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://172.16.2.116:1433;databaseName=EMP001_MANT", "sa", "plast");
            String query = "SELECT k.COD as 'Cod', k.DOC as 'Doc', CONVERT(DATE, k.FECHA) as 'Date', k.DES as 'desc', k.tipo as 'Type' "
                    + "FROM kardex k "
                    + "WHERE k.COD LIKE '" + Ref + "%' AND k.tipo = '3051' AND  k.FECHA BETWEEN CONVERT(DATETIME,'" + dtIn + "', 120) AND CONVERT(DATETIME,'" + dtFn + "', 120) "
                    + "GROUP BY k.FECHA, k.DES, k.COD, k.DOC, k.tipo  "
                    + "UNION ALL  "
                    + "SELECT k.COD as 'Cod', k.DOC as 'Doc', CONVERT(DATE, k.FECHA) as 'Date', k.DES as 'desc', k.tipo as 'Type' "
                    + "FROM kardexa k "
                    + "WHERE k.COD LIKE '" + Ref + "%' AND k.tipo = '3051' AND  k.FECHA BETWEEN CONVERT(DATETIME,'" + dtIn + "', 120) AND CONVERT(DATETIME,'" + dtFn + "', 120) "
                    + "GROUP BY k.FECHA, k.DES, k.COD, k.DOC, k.tipo  "
                    + "UNION ALL "
                    + "SELECT k.COD as 'Cod', k.DOC as 'Doc', CONVERT(DATE, k.FECHA) as 'Date', k.DES as 'desc', k.tipo as 'Type' "
                    + "FROM kardexhi k "
                    + "WHERE k.COD LIKE '" + Ref + "%' AND k.tipo = '3051' AND  k.FECHA BETWEEN CONVERT(DATETIME,'" + dtIn + "', 120) AND CONVERT(DATETIME,'" + dtFn + "', 120) "
                    + "GROUP BY k.FECHA, k.DES, k.COD, k.DOC, k.tipo  "
                    + "ORDER BY k.DOC ASC ";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_orden_factory = new ArrayList<String>();

            int count = 0;
            while (rs.next()) {
                lst_orden_factory.add(count, rs.getString("Cod").trim() + " / " + rs.getString("Doc").trim() + " / " + rs.getString("Date").trim()
                        + " / " + rs.getString("desc").trim() + " / " + rs.getString("Type").trim() + "---");
                count++;
            }
            return lst_orden_factory;
        } catch (Exception ex) {
            return null;
        }
    }

}
