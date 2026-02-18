package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//el resto
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

import Metodos.Producto;
import Metodos.ProductoStock;
import java.sql.PreparedStatement;

public class ReferenciasMANT {

    public List Productos() throws Exception {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://172.16.2.119:1433;databaseName=EMP002_MANT;encrypt=false;trustServerCertificate=true;", "sa", "plast");
            Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://172.16.2.116:1433/EMP001_MANT;user=sa;password=plast;");
            String query = "SELECT COD,NOM FROM MAESTRO WHERE PRESENT LIKE 'M%' ";
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

    public List<ProductoStock> StockMinimo(
            int offset,
            int limite,
            String busqueda) throws Exception {

        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        List<ProductoStock> lista
                = new ArrayList<ProductoStock>();

        if (busqueda == null) {
            busqueda = "";
        }

        String query
                = "SELECT Estado, COD, NOM, MINIMO, EXIST FROM ( "
                + " SELECT ROW_NUMBER() OVER (ORDER BY "
                + " CASE WHEN (m.[EXIST] < m.MINIMO) THEN 0 ELSE 1 END, "
                + " m.COD "
                + " ) AS RowNum, "
                + " CASE WHEN (m.[EXIST] < m.MINIMO) THEN 'Alerta' "
                + " ELSE 'Stock' END AS Estado, "
                + " m.COD, m.NOM, m.MINIMO, m.[EXIST] AS EXIST "
                + " FROM MAESTRO m "
                + " WHERE m.PRESENT LIKE 'M%' "
                + " AND (m.COD LIKE ? OR m.NOM LIKE ?) "
                + ") AS T "
                + "WHERE RowNum >= ? AND RowNum <= ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = DriverManager.getConnection("jdbc:jtds:sqlserver://172.16.2.116:1433/EMP001_MANT;user=sa;password=plast;");

            ps = con.prepareStatement(query);

            String filtro = "%" + busqueda + "%";

            ps.setString(1, filtro);
            ps.setString(2, filtro);

            int inicio = offset + 1;
            int fin = offset + limite;

            ps.setInt(3, inicio);
            ps.setInt(4, fin);

            rs = ps.executeQuery();

            while (rs.next()) {

                ProductoStock p = new ProductoStock(
                        rs.getString("Estado"),
                        rs.getString("COD"),
                        rs.getString("NOM"),
                        rs.getDouble("MINIMO"),
                        rs.getDouble("EXIST")
                );

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 ver error en consola
            throw e;
        } finally {

            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return lista;
    }

    public Map<String, Producto> ExistenciaFactory() throws Exception {
        HashMap<String, Producto> productos = new HashMap<String, Producto>();

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:jtds:sqlserver://172.16.2.116:1433/EMP001_MANT;user=sa;password=plast;");
            String query = "SELECT m.COD,m.NOM,m.EXIST,m.UD "
                    + "FROM MAESTRO m "
                    + "WHERE PRESENT LIKE 'M%' ";
            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            while (rs.next()) {
                String cod = rs.getString("COD").trim();
                Producto p = new Producto(
                        cod,
                        rs.getString("NOM").trim(),
                        String.valueOf(rs.getInt("EXIST")),
                        rs.getString("UD").trim()
                );
                productos.put(cod, p);
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return productos;
    }
}
