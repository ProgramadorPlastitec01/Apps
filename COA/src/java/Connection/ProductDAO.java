package Connection;

import Controller.SettingJpaController;
import Tag.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private static final SettingJpaController settingJpa = new SettingJpaController();

    private static String login;
    private static String password;
    private static String url;

    /*==========================================================
      CONSULTA PRODUCTOS
    ==========================================================*/
    private static final String SQL_BUSCAR_PRODUCTOS
            = "SELECT "
            + "    p.id_orden_produccion, "
            + "    p.codigo, "
            + "    p.nombre, "
            + "    GROUP_CONCAT( "
            + "        DISTINCT CASE "
            + "            WHEN r.ciclo_esterilizacion IS NOT NULL "
            + "                 AND r.ciclo_esterilizacion <> 'N/A' "
            + "            THEN CONCAT( "
            + "                r.lote_producto, "
            + "                '///', "
            + "                r.ciclo_esterilizacion "
            + "            ) "
            + "            ELSE r.lote_producto "
            + "        END "
            + "        ORDER BY r.lote_producto, r.ciclo_esterilizacion "
            + "        SEPARATOR ', ' "
            + "    ) AS lotes "
            + "FROM producto p "
            + "INNER JOIN orden_produccion o "
            + "    ON o.id_orden_produccion = p.id_orden_produccion "
            + "INNER JOIN registro r "
            + "    ON r.id_producto = p.id_producto "
            + "WHERE o.numero = ? "
            + "    AND r.lote_producto IS NOT NULL "
            + "    AND r.lote_producto <> 'N/A' "
            + "    AND EXISTS ( "
            + "        SELECT 1 "
            + "        FROM registro r2 "
            + "        WHERE r2.id_producto = r.id_producto "
            + "          AND r2.lote_producto = r.lote_producto "
            + "          AND r2.estado = 0 "
            + "    ) "
            + "GROUP BY "
            + "    p.id_orden_produccion, "
            + "    p.codigo, "
            + "    p.nombre "
            + "ORDER BY "
            + "    p.codigo;";

    /*==========================================================
      CONSULTA FECHAS
    ==========================================================*/
    private static final String SQL_BUSCAR_FECHAS
            = "SELECT "
            + "MIN(r.fecha_turno) AS fecha_inicio, "
            + "MAX(r.fecha_turno) AS fecha_fin "
            + "FROM registro r "
            + "INNER JOIN producto p ON r.id_producto=p.id_producto "
            + "INNER JOIN orden_produccion o ON p.id_orden_produccion=o.id_orden_produccion "
            + "WHERE o.numero=? "
            + "AND p.codigo=? "
            + "AND ("
            + "r.lote_producto=? "
            + "OR CONCAT(r.lote_producto,'///',r.ciclo_esterilizacion)=?"
            + ")";

    /*==========================================================
      CONTAR REGISTROS
    ==========================================================*/
    private static final String SQL_CONTAR_REGISTROS
            = "SELECT COUNT(r.id_registro) total_registros "
            + "FROM registro r "
            + "INNER JOIN producto p ON r.id_producto=p.id_producto "
            + "INNER JOIN orden_produccion o ON p.id_orden_produccion=o.id_orden_produccion "
            + "WHERE o.numero=? "
            + "AND p.codigo=? "
            + "AND r.estado = 0 "
            + "AND ("
            + "r.lote_producto=? "
            + "OR CONCAT(r.lote_producto,'///',r.ciclo_esterilizacion)=?"
            + ") "
            + "AND DATE(r.fecha_turno) BETWEEN ? AND ?";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static boolean initConnectionParams() throws Exception {

        List lst = settingJpa.ConsultSettingCategorie("ServerRegistrosLab");

        if (lst == null || lst.isEmpty()) {
            return false;
        }

        Object[] data = (Object[]) lst.get(0);

        String[] arr = data[2].toString()
                .replace("][", "///")
                .replace("[", "")
                .replace("]", "")
                .split("///");

        login = arr[0];
        password = arr[1];
        url = "jdbc:mysql://" + arr[2];

        return true;
    }

    private static Connection getConnection() throws Exception {

        if (!initConnectionParams()) {
            return null;
        }

        return DriverManager.getConnection(url, login, password);

    }

    /*==========================================================
      BUSCAR PRODUCTOS
    ==========================================================*/
    public static List<Product> buscarPorOrden(String orden) throws Exception {

        List<Product> productos = new ArrayList<>();

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_PRODUCTOS)) {

            ps.setString(1, orden);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Product p = new Product();

                    p.setCodigo(rs.getString("codigo"));
                    p.setProducto(rs.getString("nombre"));
                    p.setLote(rs.getString("lotes"));

                    productos.add(p);

                }

            }

        }

        return productos;
    }

    /*==========================================================
      BUSCAR FECHAS
    ==========================================================*/
    public static String[] buscarFechas(String orden,
            String codigoProducto,
            String lote) throws Exception {

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_FECHAS)) {

            ps.setString(1, orden);
            ps.setString(2, codigoProducto);
            ps.setString(3, lote);
            ps.setString(4, lote);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new String[]{
                        rs.getString("fecha_inicio"),
                        rs.getString("fecha_fin")
                    };

                }

            }

        }

        return null;
    }

    /*==========================================================
      CONTAR REGISTROS
    ==========================================================*/
    public static int contarRegistros(String orden,
            String codigoProducto,
            String lote,
            String fechaInicio,
            String fechaFin) throws Exception {

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_CONTAR_REGISTROS)) {

            ps.setString(1, orden);
            ps.setString(2, codigoProducto);
            ps.setString(3, lote);
            ps.setString(4, lote);
            ps.setString(5, fechaInicio);
            ps.setString(6, fechaFin);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("total_registros");
                }

            }

        }

        return 0;
    }

}
