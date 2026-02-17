package Connection;

import Controller.SettingJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Tag.Product;

public class ProductDAO {

    private static SettingJpaController SettingJpa = new SettingJpaController();

    static String login = "";
    static String password = "";
    static String url = "";

    private static boolean initConnectionParams() throws Exception {
        List lst_parametro = SettingJpa.ConsultSettingCategorie("ServerRegistrosLab");
        if (lst_parametro != null) {
            Object[] obj_data = (Object[]) lst_parametro.get(0);
            String[] arr_data = obj_data[2].toString()
                    .replace("][", "///")
                    .replace("[", "")
                    .replace("]", "")
                    .split("///");

            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
            return true;
        }
        return false;
    }

    public static List<Product> buscarPorOrden(String orden) throws Exception {
        if (!initConnectionParams()) {
            return null;
        }

        List<Product> lista = new ArrayList<>();
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);

            if (conn != null) {
//                String sql = "SELECT  "
//                        + "    p.codigo, "
//                        + "    p.nombre, "
//                        + "    COALESCE( "
//                        + "        GROUP_CONCAT( "
//                        + "            DISTINCT r.lote_producto "
//                        + "            ORDER BY r.lote_producto SEPARATOR ', ' "
//                        + "        ), "
//                        + "    '') AS lotes "
//                        + "FROM producto p "
//                        + "INNER JOIN orden_produccion o  "
//                        + "    ON p.id_orden_produccion = o.id_orden_produccion "
//                        + "LEFT JOIN registro r  "
//                        + "    ON r.id_producto = p.id_producto "
//                        + "WHERE  "
//                        + "    o.numero = ? "
//                        + "    AND r.lote_producto IS NOT NULL "
//                        + "    AND r.lote_producto <> 'N/A' "
//                        + "    AND r.lote_producto IN ( "
//                        + "        SELECT lote_producto "
//                        + "        FROM registro r2 "
//                        + "        WHERE r2.id_producto = p.id_producto "
//                        + "        GROUP BY lote_producto "
//                        + "        HAVING SUM(r2.estado = 1) = 0 "
//                        + "    ) "
//                        + "GROUP BY p.codigo, p.nombre;";
                
                 String sql = "SELECT  "
                        + "    p.codigo, "
                        + "    p.nombre, "
                        + "    COALESCE( "
                        + "        GROUP_CONCAT( "
                        + "            DISTINCT  "
                        + "            CASE  "
                        + "                WHEN r.ciclo_esterilizacion IS NOT NULL  "
                        + "                     AND r.ciclo_esterilizacion <> 'N/A' "
                        + "                THEN CONCAT(r.lote_producto, '///', r.ciclo_esterilizacion) "
                        + "                ELSE r.lote_producto "
                        + "            END "
                        + "            ORDER BY r.lote_producto "
                        + "            SEPARATOR ', ' "
                        + "        ), "
                        + "        '' "
                        + "    ) AS lotes "
                        + "FROM producto p "
                        + "INNER JOIN orden_produccion o  "
                        + "    ON p.id_orden_produccion = o.id_orden_produccion "
                        + "LEFT JOIN registro r  "
                        + "    ON r.id_producto = p.id_producto "
                        + "WHERE  "
                        + "    o.numero = ? "
                        + "    AND r.lote_producto IS NOT NULL "
                        + "    AND r.lote_producto <> 'N/A' "
                        + "    AND r.lote_producto IN ( "
                        + "        SELECT lote_producto "
                        + "        FROM registro r2 "
                        + "        WHERE r2.id_producto = p.id_producto "
                        + "        GROUP BY lote_producto "
                        + "        HAVING SUM(r2.estado = 1) = 0 "
                        + "    ) "
                        + "GROUP BY p.codigo, p.nombre;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, orden);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Product p = new Product();
                    p.setCodigo(rs.getString("codigo"));   // <-- en vez de "producto"
                    p.setProducto(rs.getString("nombre"));   // <-- campo nombre
                    p.setLote(rs.getString("lotes"));     // <-- el GROUP_CONCAT
                    lista.add(p);
                }

                rs.close();
                ps.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }

        return lista;
    }
}
