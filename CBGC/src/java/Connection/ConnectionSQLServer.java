package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Controller.SettingJpaController;

public class ConnectionSQLServer {

    SettingJpaController SettingJpa = new SettingJpaController();

    static String login = "";
    static String password = "";
    static String url = "";

    public List<String> Products(int Orden, String Producto, String Lote) throws Exception {
        Connection con = null;
        Statement sttm = null;
        ResultSet rs = null;

        try {
            // Obtener parámetros de conexión
            List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerSQLServer");
            if (lst_parameter == null || lst_parameter.isEmpty()) {
                return null;
            }

            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString()
                    .replace("][", "///")
                    .replace("[", "")
                    .replace("]", "")
                    .split("///");
            String login = arr_data[0];
            String password = arr_data[1];
            String url = "jdbc:sqlserver://" + arr_data[2];

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url, login, password);

            // Consulta SQL
            String query = "SELECT "
                    + "p.NUM AS [Op], "
                    + "c.NOM AS [Cliente], "
                    + "c.DIR AS [Direccion], "
                    + "c.TELCELULAR AS [Telefono], "
                    + "c.CIU AS [Ciudad], "
                    + "c.DEPTO AS [Pais], "
                    + "f.NUMERO AS [Factura], "
                    + "r.NUMERO AS [Remision], "
                    + "p.COD AS [Cod_producto], "
                    + "CASE "
                    + "    WHEN PATINDEX('%[0-9]%mL%', m.NOM) > 0 THEN "
                    + "        LTRIM(RTRIM(STUFF(m.NOM, "
                    + "            PATINDEX('%[0-9]%mL%', m.NOM), "
                    + "            CHARINDEX('mL', m.NOM, PATINDEX('%[0-9]%mL%', m.NOM)) - PATINDEX('%[0-9]%mL%', m.NOM) + 2, "
                    + "            ''))) "
                    + "    ELSE m.NOM "
                    + "END AS [Producto], "
                    + "CASE "
                    + "    WHEN PATINDEX('%[0-9]%mL%', m.NOM) > 0 THEN "
                    + "        SUBSTRING(m.NOM, PATINDEX('%[0-9]%mL%', m.NOM), "
                    + "            CHARINDEX('mL', m.NOM, PATINDEX('%[0-9]%mL%', m.NOM)) - PATINDEX('%[0-9]%mL%', m.NOM) + 2) "
                    + "    ELSE NULL "
                    + "END AS [Volumen], "
                    + "FORMAT(d.CANT, 'N0', 'es-CO') AS [Cantidad], "
                    + "p.ORDEN as [OrdenC] "
                    + "FROM PEDIDOS p "
                    + "INNER JOIN CLIENTES c ON p.CLIENTE = c.COD "
                    + "INNER JOIN [EMP001_INV].[dbo].[MAESTRO] m ON p.COD = m.COD "
                    + "LEFT JOIN FACTURAS f ON p.NUM = f.PEDID "
                    + "LEFT JOIN REMISION r ON f.NUMERO = r.DCTOPP "
                    + "LEFT JOIN DETALLER d ON r.NUMERO = d.NUMERO "
                    + "WHERE p.NUM = " + Orden + " "
                    + "  AND p.COD LIKE '%" + Producto + "%' "
                    + "  AND d.LOTE = '" + Lote + "'";

            sttm = con.createStatement();
            rs = sttm.executeQuery(query);

            List<String> lst_resultado = new ArrayList<>();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Crear cabecera
            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) {
                    header.append("]///[");
                }
                header.append(metaData.getColumnName(i));
            }
            lst_resultado.add(header.toString());

            // Crear filas
            int rowCount = 0;
            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) {
                        row.append("]///[");
                    }
                    String value = rs.getString(i);
                    row.append(value != null ? value.trim() : "NULL");
                }
                lst_resultado.add(row.toString());
                rowCount++;
            }

            // Si hay cabecera pero no filas → devolver null
            if (rowCount == 0) {
                return null;
            }

            return lst_resultado;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            // Liberar recursos de forma segura
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ignore) {
                }
            }
            if (sttm != null) {
                try {
                    sttm.close();
                } catch (Exception ignore) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

}
