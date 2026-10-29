package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import Controller.SettingJpaController;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionSQLServer {

    SettingJpaController SettingJpa = new SettingJpaController();

    static String login = "";
    static String password = "";
    static String url = "";

    // Factory y Tags son la MISMA instancia de SQL Server (mismo host:puerto),
    // solo cambia la base de datos. Se unifica en una única categoría de Setting
    // para no abrir dos conexiones/login independientes contra el mismo servidor
    // (eso podía hacer que una se cayera al competir con la otra): primero se
    // abre la conexión al servidor y luego se distribuye hacia la BD puntual
    // con setCatalog.
    private static final String SETTING_CATEGORY_PLASTITEC = "ServerSQLServerPlastitec";
    private static final String DB_FACTORY = "EMP001_FACT";
    private static final String DB_TAGS = "TagsLogic";

    private Connection obtenerConexionPlastitec(String baseDatos) throws Exception {
        List lst_parameter = SettingJpa.ConsultSettingCategorie(SETTING_CATEGORY_PLASTITEC);
        if (lst_parameter == null || lst_parameter.isEmpty()) {
            throw new Exception("No hay configuración en Setting para la categoría '" + SETTING_CATEGORY_PLASTITEC + "'.");
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
        Connection con = DriverManager.getConnection(url, login, password);
        con.setCatalog(baseDatos);
        return con;
    }

    // Cómo se llega del pedido al detalle por lote (DETALLER):
    // - Por factura (consulta original): PEDIDOS -> FACTURAS (f.PEDID) -> REMISION (r.DCTOPP = factura).
    // - Por remisión: PEDIDOS -> REMISION (r.PEDID) directamente, para pedidos
    //   que ya tienen remisión pero aún no se han facturado. Si la remisión
    //   apunta a una factura (r.DCTOPP) igual se trae su número. Una remisión
    //   sin facturar tiene DCTOPP = 0, y en FACTURAS existe un registro con
    //   NUMERO 0: se excluye para que la Factura quede pendiente y no "0".
    private static final String JOIN_POR_FACTURA = "LEFT JOIN FACTURAS f ON p.NUM = f.PEDID "
            + "LEFT JOIN REMISION r ON f.NUMERO = r.DCTOPP ";
    private static final String JOIN_POR_REMISION = "INNER JOIN REMISION r ON p.NUM = r.PEDID "
            + "LEFT JOIN FACTURAS f ON f.NUMERO = r.DCTOPP AND r.DCTOPP <> 0 ";

    /**
     * Cabecera del certificado desde Factory por orden, producto y lote.
     * Primero busca por la factura del pedido; si no encuentra nada, repite
     * la misma consulta por la remisión del pedido. Devuelve null si no hay
     * datos por ninguna de las dos vías.
     */
    public List<String> Products(int Orden, String Producto, String Lote) throws Exception {
        List<String> resultado = consultarProducts(Orden, Producto, Lote, JOIN_POR_FACTURA);
        if (resultado == null) {
            resultado = consultarProducts(Orden, Producto, Lote, JOIN_POR_REMISION);
        }
        return resultado;
    }

    private List<String> consultarProducts(int Orden, String Producto, String Lote, String joinDocumento) throws Exception {
        Connection con = null;
        Statement sttm = null;
        ResultSet rs = null;

        try {
            con = obtenerConexionPlastitec(DB_FACTORY);

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
                    + "p.ORDEN as [OrdenC], "
                    + "CASE WHEN pe.CMPBAR_CLIE IS NULL THEN 'N/A' WHEN LTRIM(pe.CMPBAR_CLIE) = '' THEN 'N/A' ELSE pe.CMPBAR_CLIE END as [CodeSpeed] "
                    + "FROM PEDIDOS p "
                    + "INNER JOIN CLIENTES c ON p.CLIENTE = c.COD "
                    + "INNER JOIN [EMP001_INV].[dbo].[MAESTRO] m ON p.COD = m.COD "
                    + joinDocumento
                    + "LEFT JOIN DETALLER d ON r.NUMERO = d.NUMERO "
                    + "LEFT JOIN PREC_ESP pe "
                    + "       ON REPLACE(pe.CLIENTE, ' ', '') "
                    + "          LIKE CONCAT('%', LEFT(REPLACE(c.NIT, ' ', ''), "
                    + "                                CHARINDEX('-', REPLACE(c.NIT, ' ', '') + '-') - 1), '%') "
                    + "      AND pe.COD LIKE '%" + Producto + "%'"
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

    // Campos que se extraen del JSON de AdditionalInformation del cliente (od.AdditionalInformation)
    private static final String[] CAMPOS_JSON = {"Code", "Name", "Address", "City", "Country"};

    public List<String> ConsultTags(int Orden, String Producto, String Lote) throws Exception {
        Connection con = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;

        try {
            con = obtenerConexionPlastitec(DB_TAGS);

            // TotalQuantity suma las cajas (ExternalTag) de ese mismo OrderLine+Lote
            // puntual, no el total de toda la orden de producción (que es lo que
            // traía antes el campo Quantity del JSON AdditionalInformation del
            // producto). La última columna sigue siendo JSON: AdditionalInformation
            // del cliente (od).
            String query = "SELECT TOP(1) o.OrderNumber, e.OrderLineId, o.ProductCode, e.BoxNumber, e.Batch, "
                    + "FORMAT(e.CreationDate, 'yyyy-MM-dd') AS CreationDate, "
                    + "FORMAT(e.ExpirationDate, 'yyyy-MM-dd') AS ExpirationDate, "
                    + "od.AddresseeCode, od.CustomerName, od.PurchaseOrderNumber, "
                    + "o.ProductName, "
                    + "(SELECT SUM(e2.Quantity) FROM ExternalTag e2 "
                    + "     WHERE e2.OrderLineId = e.OrderLineId AND e2.Batch = e.Batch) AS TotalQuantity, "
                    + "od.AdditionalInformation AS CustomerInfo "
                    + "FROM ExternalTag e "
                    + "INNER JOIN OrderLine o ON e.OrderLineId = o.OrderLineId "
                    + "INNER JOIN [dbo].[Order] od ON o.OrderNumber = od.OrderNumber "
                    + "WHERE o.OrderNumber = ? AND o.ProductCode = ? AND e.Batch = ? "
                    + "ORDER BY e.BoxNumber ASC";

            sttm = con.prepareStatement(query);
            sttm.setInt(1, Orden);
            sttm.setString(2, Producto);
            sttm.setString(3, Lote);
            rs = sttm.executeQuery();

            List<String> lst_resultado = new ArrayList<>();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            int columnaJsonCliente = columnCount;

            // Cabecera: columnas normales + campos del JSON del cliente
            StringBuilder header = new StringBuilder();
            for (int i = 1; i < columnaJsonCliente; i++) {
                if (i > 1) {
                    header.append("]///[");
                }
                header.append(metaData.getColumnName(i));
            }
            for (String campo : CAMPOS_JSON) {
                header.append("]///[").append("AI_").append(campo);
            }
            lst_resultado.add(header.toString());

            // Filas
            int rowCount = 0;
            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i < columnaJsonCliente; i++) {
                    if (i > 1) {
                        row.append("]///[");
                    }
                    String value = rs.getString(i);
                    row.append(value != null ? value.trim() : "NULL");
                }

                String jsonCliente = rs.getString(columnaJsonCliente);
                for (String campo : CAMPOS_JSON) {
                    String valor = obtenerValorJson(jsonCliente, campo);
                    row.append("]///[").append(valor != null ? valor : "NULL");
                }

                lst_resultado.add(row.toString());
                rowCount++;
            }

            if (rowCount == 0) {
                return null;
            }
            return lst_resultado;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
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

    /**
     * Extrae el valor de una clave en un JSON simple, p. ej. "City":"BOGOTÁ
     * (D.C)" o "Quantity":1478400.0. Tolera JSON mal formado (como la falta de
     * '{') y comillas escapadas dentro del valor, y además valores
     * numéricos/booleanos/null sin comillas. Devuelve null si la clave no
     * existe o el valor está vacío.
     */
    private static String obtenerValorJson(String json, String clave) {
        if (json == null || json.isEmpty()) {
            return null;
        }

        Pattern p = Pattern.compile("\"" + Pattern.quote(clave) + "\"\\s*:\\s*"
                + "(?:\"((?:[^\"\\\\]|\\\\.)*)\"|([^,}\\]]+))");
        Matcher m = p.matcher(json);
        if (!m.find()) {
            return null;
        }

        String valor = m.group(1) != null
                ? m.group(1).replace("\\\"", "\"").replace("\\\\", "\\")
                : m.group(2);
        valor = valor == null ? null : valor.trim();
        return (valor == null || valor.isEmpty() || valor.equalsIgnoreCase("null")) ? null : valor;
    }

}
