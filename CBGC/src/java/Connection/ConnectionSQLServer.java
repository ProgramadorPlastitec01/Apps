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
        try {

            List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerSQLServer");
            if (lst_parameter != null) {
                Object[] obj_data = (Object[]) lst_parameter.get(0);
                String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                login = arr_data[0];
                password = arr_data[1];
                url = "jdbc:sqlserver://" + arr_data[2];
            } else {
                return null;
            }
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, login, password);

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
                    + "FORMAT(d.CANT, 'N0', 'es-CO') AS [Cantidad] " // 👈 formatea 20000.0000 → 20.000
                    + "FROM PEDIDOS p "
                    + "INNER JOIN CLIENTES c ON p.CLIENTE = c.COD "
                    + "INNER JOIN [EMP001_INV].[dbo].[MAESTRO] m ON p.COD = m.COD "
                    + "LEFT JOIN FACTURAS f ON p.NUM = f.PEDID "
                    + "LEFT JOIN REMISION r ON f.NUMERO = r.DCTOPP "
                    + "LEFT JOIN DETALLER d ON r.NUMERO = d.NUMERO "
                    + "WHERE p.NUM = " + Orden + " "
                    + "  AND p.COD LIKE '%" + Producto + "%' "
                    + "  AND d.LOTE = '" + Lote + "'";

            Statement sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(query);

            List<String> lst_resultado = new ArrayList<>();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Cabecera
            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) {
                    header.append("]///[");
                }
                header.append(metaData.getColumnName(i));
            }
            lst_resultado.add(header.toString());

            // Filas
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
            }

            con.close();
            return lst_resultado;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
