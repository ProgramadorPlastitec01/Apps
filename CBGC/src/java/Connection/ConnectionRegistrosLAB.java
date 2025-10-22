package Connection;

import Controller.SettingJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionRegistrosLAB {

    SettingJpaController SettingJpa = new SettingJpaController();

    static String login = "";
    static String password = "";
    static String url = "";

    public List ConsultMaterials(int Order, String Product, String Batch) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CONSULT_MATERIALS R-GC-064">
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerRegistrosLab");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String Qry = "WITH datos_base AS ( "
                        + "    SELECT  "
                        + "        SUBSTRING(r.lote_manga_p, 1, 4) AS `FUNDA / LAY FLAT`, "
                        + "        r.lote_manga_c AS `Lote LF`, "
                        + "        SUBSTRING(r.lote_manga_p, 1, 4) AS `PP FILM CTS-DW`, "
                        + "        r.lote_manga_c AS `Lote PP`, "
                        + "        SUBSTRING(r.lote_dto_drc_p, 1, 4) AS `DUCTO DERECHO / RIGHT TUBE`, "
                        + "        r.lote_dto_drc_c AS `Lote DD`, "
                        + "        SUBSTRING(r.lote_dto_iqe_p, 1, 4) AS `DUCTO IZQUIERDO / LEFT TUBE`, "
                        + "        r.lote_dto_iqe_c AS `Lote DI`, "
                        + "        SUBSTRING(r.lote_dto_ctl_p, 1, 4) AS `DUCTO CENTRAL / CENTRAL TUBE`, "
                        + "        r.lote_dto_ctl_c AS `Lote CL`, "
                        + "        SUBSTRING(r.lote_ensamble, 1, 4) AS `CONECTOR / TWIST OFF CONNECTOR`, "
                        + "        r.lote_ensamble AS `Lote CO1`, "
                        + "        SUBSTRING(r.lote_ensamble, 1, 4) AS `CONECTOR / CONNECTOR`, "
                        + "        r.lote_ensamble AS `Lote CO2`, "
                        + "        r.lote_tinta AS `TINTA / INK`, "
                        + "        r.lote_tinta AS `FOIL NEGRO / BLACK FOIL` "
                        + "    FROM orden_produccion o "
                        + "    INNER JOIN producto p ON o.id_orden_produccion = p.id_orden_produccion "
                        + "    INNER JOIN registro r ON p.id_producto = r.id_producto "
                        + "    INNER JOIN linea l ON r.id_linea = l.id_linea "
                        + "    WHERE o.numero = " + Order + " "
                        + "      AND p.codigo LIKE CONCAT('%','" + Product + "', '%') "
                        + "      AND r.lote_producto LIKE CONCAT('%', '" + Batch + "', '%') "
                        + "      AND l.nombre NOT LIKE '%SCREEN%' "
                        + "    LIMIT 1 "
                        + ") "
                        + "SELECT "
                        + "    `FUNDA / LAY FLAT`, "
                        + "    CASE  "
                        + "        WHEN `Lote LF` LIKE '%/%' THEN `Lote LF` "
                        + "        WHEN `Lote LF` REGEXP '[VERP]' THEN  "
                        + "            REGEXP_REPLACE( "
                        + "                REGEXP_REPLACE(`Lote LF`, '^[A-Z0-9]+-', ''), "
                        + "                'V[0-9A-Z]+', '' "
                        + "            ) "
                        + "        ELSE `Lote LF` "
                        + "    END AS `Lote LF`, "
                        + " "
                        + "    `PP FILM CTS-DW`, "
                        + "    CASE  "
                        + "        WHEN `Lote PP` LIKE '%/%' THEN `Lote PP` "
                        + "        WHEN `Lote PP` REGEXP '[VERP]' THEN  "
                        + "            REGEXP_REPLACE( "
                        + "                REGEXP_REPLACE(`Lote PP`, '^[A-Z0-9]+-', ''), "
                        + "                'V[0-9A-Z]+', '' "
                        + "            ) "
                        + "        ELSE `Lote PP` "
                        + "    END AS `Lote PP`, "
                        + " "
                        + "    `DUCTO DERECHO / RIGHT TUBE`, "
                        + "    CASE  "
                        + "        WHEN `Lote DD` LIKE '%/%' THEN `Lote DD` "
                        + "        WHEN `Lote DD` REGEXP '[VERP]' THEN  "
                        + "            REGEXP_REPLACE( "
                        + "                REGEXP_REPLACE(`Lote DD`, '^[A-Z0-9]+-', ''), "
                        + "                'V[0-9A-Z]+', '' "
                        + "            ) "
                        + "        ELSE `Lote DD` "
                        + "    END AS `Lote DD`, "
                        + " "
                        + "    `DUCTO IZQUIERDO / LEFT TUBE`, "
                        + "    CASE  "
                        + "        WHEN `Lote DI` LIKE '%/%' THEN `Lote DI` "
                        + "        WHEN `Lote DI` REGEXP '[VERP]' THEN  "
                        + "            REGEXP_REPLACE( "
                        + "                REGEXP_REPLACE(`Lote DI`, '^[A-Z0-9]+-', ''), "
                        + "                'V[0-9A-Z]+', '' "
                        + "            ) "
                        + "        ELSE `Lote DI` "
                        + "    END AS `Lote DI`, "
                        + " "
                        + "    `DUCTO CENTRAL / CENTRAL TUBE`, "
                        + "    CASE  "
                        + "        WHEN `Lote CL` LIKE '%/%' THEN `Lote CL` "
                        + "        WHEN `Lote CL` REGEXP '[VERP]' THEN  "
                        + "            REGEXP_REPLACE( "
                        + "                REGEXP_REPLACE(`Lote CL`, '^[A-Z0-9]+-', ''), "
                        + "                'V[0-9A-Z]+', '' "
                        + "            ) "
                        + "        ELSE `Lote CL` "
                        + "    END AS `Lote CL`, "
                        + " "
                        + "    `CONECTOR / TWIST OFF CONNECTOR`, "
                        + "    `Lote CO1` AS `Lote TWIST OFF`, "
                        + " "
                        + "    `CONECTOR / CONNECTOR`, "
                        + "    `Lote CO2` AS `Lote CONNECTOR`, "
                        + " "
                        + "    `TINTA / INK`, "
                        + "    `FOIL NEGRO / BLACK FOIL` "
                        + "FROM datos_base;";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Qry);
                List<String> lst_user = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_user.add(count, rs.getString("FUNDA / LAY FLAT").trim() + " /// " + rs.getString("Lote LF").trim() + " /// "
                            + rs.getString("PP FILM CTS-DW").trim() + " /// " + rs.getString("Lote PP").trim() + " /// "
                            + rs.getString("DUCTO DERECHO / RIGHT TUBE").trim() + "///" + rs.getString("Lote DD").trim() + "///"
                            + rs.getString("DUCTO IZQUIERDO / LEFT TUBE").trim() + "///" + rs.getString("Lote DI").trim() + "///"
                            + rs.getString("DUCTO CENTRAL / CENTRAL TUBE").trim() + "///" + rs.getString("Lote CL").trim() + "///"
                            + rs.getString("CONECTOR / TWIST OFF CONNECTOR").trim() + "///" + rs.getString("Lote TWIST OFF").trim() + "///"
                            + rs.getString("CONECTOR / CONNECTOR").trim() + "///" + rs.getString("Lote CONNECTOR").trim() + "///"
                            + rs.getString("TINTA / INK").trim() + "///" + rs.getString("FOIL NEGRO / BLACK FOIL").trim() + "");
                    count++;
                }
                conn.close();
                return lst_user;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
        //</editor-fold>
    }

    public List ConsultMaterialsRGC74(int Order, String Product, String Batch) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CONSULT MATERIALS R-GC-074">
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerRegistrosLab");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            return null;
        }

        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {

                String Qry
                        = "WITH datos_base AS ( "
                        + "    SELECT  "
                        + "        SUBSTRING(r.lote_manga_p, 1, 4) AS `CAPA INTERNA / INNER LAYER`, "
                        + "        r.lote_manga_c AS `Lote LF`, "
                        + "        SUBSTRING(r.lote_manga_p, 1, 4) AS `CAPA EXTERNA / OUTER LAYER`, "
                        + "        r.lote_manga_c_alt AS `Lote PP`, "
                        + "        SUBSTRING(r.lote_dto_ctl_p, 1, 4) AS `DUCTOS CAPA EXTERNA / OUTER LAYER TUBE`, "
                        + "        r.lote_dto_c_alt AS `Lote DD`, "
                        + "        SUBSTRING(r.lote_dto_ctl_p, 1, 4) AS `DUCTOS CAPA INTERNA / INNER LAYER TUBE`, "
                        + "        r.lote_dto_ctl_c AS `Lote DI`, "
                        + "        SUBSTRING(r.lote_ensamble, 1, 4) AS `CONECTOR / CONNECTOR`, "
                        + "        r.lote_ensamble AS `Lote CONNECTOR`, "
                        + "        SUBSTRING(r.lote_ensamble_2, 1, 4) AS `SITIO INYECCION / INJECTION SITE`, "
                        + "        r.lote_ensamble_2 AS `Lote INYECCION`, "
                        + "        SUBSTRING(r.lote_ensamble_3, 1, 4) AS `ENSAMBLE BIG BORE`, "
                        + "        r.lote_ensamble_3 AS `Lote BIG BORE`, "
                        + "        SUBSTRING(r.lote_ensamble_4, 1, 4) AS `SISTEMA DE 3 VIAS PN`, "
                        + "        r.lote_ensamble_4 AS `Lote SISTEMA VIAS`, "
                        + "        r.lote_tinta AS `FOIL NEGRO / BLACK FOIL` "
                        + "    FROM orden_produccion o "
                        + "    INNER JOIN producto p ON o.id_orden_produccion = p.id_orden_produccion "
                        + "    INNER JOIN registro r ON p.id_producto = r.id_producto "
                        + "    INNER JOIN linea l ON r.id_linea = l.id_linea "
                        + "    WHERE o.numero = " + Order + " "
                        + "      AND p.codigo LIKE CONCAT('%','" + Product + "', '%') "
                        + "      AND r.lote_producto LIKE CONCAT('%', '" + Batch + "', '%') "
                        + "      AND l.nombre NOT LIKE '%SCREEN%' "
                        + "    LIMIT 1 "
                        + ") "
                        + "SELECT "
                        + "    `CAPA INTERNA / INNER LAYER`, "
                        + "    CASE  "
                        + "        WHEN `Lote LF` LIKE '%/%' THEN `Lote LF` "
                        + "        WHEN `Lote LF` REGEXP '[VERP]' THEN  "
                        + "            REGEXP_REPLACE( "
                        + "                REGEXP_REPLACE(`Lote LF`, '^[A-Z0-9]+-', ''), "
                        + "                'V[0-9A-Z]+', '' "
                        + "            ) "
                        + "        ELSE `Lote LF` "
                        + "    END AS `Lote LF`, "
                        + " "
                        + "    `CAPA EXTERNA / OUTER LAYER`, "
                        + "    CASE  "
                        + "        WHEN `Lote PP` LIKE '%/%' THEN `Lote PP` "
                        + "        WHEN `Lote PP` REGEXP '[VERP]' THEN  "
                        + "            REGEXP_REPLACE( "
                        + "                REGEXP_REPLACE(`Lote PP`, '^[A-Z0-9]+-', ''), "
                        + "                'V[0-9A-Z]+', '' "
                        + "            ) "
                        + "        ELSE `Lote PP` "
                        + "    END AS `Lote PP`, "
                        + " "
                        + "    `DUCTOS CAPA EXTERNA / OUTER LAYER TUBE`, "
                        + "    CASE  "
                        + "        WHEN `Lote DD` LIKE '%/%' THEN `Lote DD` "
                        + "        WHEN `Lote DD` REGEXP '[VERP]' THEN  "
                        + "            REGEXP_REPLACE( "
                        + "                REGEXP_REPLACE(`Lote DD`, '^[A-Z0-9]+-', ''), "
                        + "                'V[0-9A-Z]+', '' "
                        + "            ) "
                        + "        ELSE `Lote DD` "
                        + "    END AS `Lote DD`, "
                        + " "
                        + "    `DUCTOS CAPA INTERNA / INNER LAYER TUBE`, "
                        + "    CASE  "
                        + "        WHEN `Lote DI` LIKE '%/%' THEN `Lote DI` "
                        + "        WHEN `Lote DI` REGEXP '[VERP]' THEN  "
                        + "            REGEXP_REPLACE( "
                        + "                REGEXP_REPLACE(`Lote DI`, '^[A-Z0-9]+-', ''), "
                        + "                'V[0-9A-Z]+', '' "
                        + "            ) "
                        + "        ELSE `Lote DI` "
                        + "    END AS `Lote DI`, "
                        + " "
                        + "    `CONECTOR / CONNECTOR`, "
                        + "    `Lote CONNECTOR`, "
                        + "    `SITIO INYECCION / INJECTION SITE`, "
                        + "    `Lote INYECCION`, "
                        + "    `ENSAMBLE BIG BORE`, "
                        + "    `Lote BIG BORE`, "
                        + "    `SISTEMA DE 3 VIAS PN`, "
                        + "    `Lote SISTEMA VIAS`, "
                        + "    `FOIL NEGRO / BLACK FOIL` "
                        + "FROM datos_base;";

                sttm = conn.createStatement();
                rs = sttm.executeQuery(Qry);

                List<String> lst_user = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    // Uso safeTrim para evitar NPE si columna es null
                    lst_user.add(count, safeTrim(rs.getString("CAPA INTERNA / INNER LAYER")) + " /// " + safeTrim(rs.getString("Lote LF")) + " /// "
                            + safeTrim(rs.getString("CAPA EXTERNA / OUTER LAYER")) + " /// " + safeTrim(rs.getString("Lote PP")) + " /// "
                            + safeTrim(rs.getString("DUCTOS CAPA EXTERNA / OUTER LAYER TUBE")) + " /// " + safeTrim(rs.getString("Lote DD")) + " /// "
                            + safeTrim(rs.getString("DUCTOS CAPA INTERNA / INNER LAYER TUBE")) + " /// " + safeTrim(rs.getString("Lote DI")) + " /// "
                            + safeTrim(rs.getString("CONECTOR / CONNECTOR")) + " /// " + safeTrim(rs.getString("Lote CONNECTOR")) + " /// "
                            + safeTrim(rs.getString("SITIO INYECCION / INJECTION SITE")) + " /// " + safeTrim(rs.getString("Lote INYECCION")) + " /// "
                            + safeTrim(rs.getString("ENSAMBLE BIG BORE")) + " /// " + safeTrim(rs.getString("Lote BIG BORE")) + " /// "
                            + safeTrim(rs.getString("SISTEMA DE 3 VIAS PN")) + " /// " + safeTrim(rs.getString("Lote SISTEMA VIAS")) + " /// "
                            + safeTrim(rs.getString("FOIL NEGRO / BLACK FOIL")));
                    count++;
                }

                conn.close();
                return lst_user;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
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
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ignore) {
                }
            }
        }
        //</editor-fold>
    }

// Helper para evitar NullPointerException al hacer trim()
    private static String safeTrim(String s) {
        return s != null ? s.trim() : "NULL";
    }

    public List DimensionalQuery(int Order, String Product, String Batch, String Comparator, String OrderBy) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="DIMESIONAL_QUERY R-GC-064">
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerRegistrosLab");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String ArgParameter = Comparator.replace("[", "").replace("]", "");
                String ArgOrderBy = OrderBy.replace("[", "").replace("]", "");
                String Qry = "WITH lista_comparadores AS ( "
                        + "    SELECT 'Longitud Total' AS comparador UNION ALL "
                        + "    SELECT 'Ancho de manga' UNION ALL "
                        + "    SELECT 'Pared doble' UNION ALL "
                        + "    SELECT 'Pared sencilla' UNION ALL "
                        + "    SELECT 'Dia. Ext. ducto derecho' UNION ALL "
                        + "    SELECT 'Dia. Int. ducto derecho' UNION ALL "
                        + "    SELECT 'Ducto derecho' UNION ALL "
                        + "    SELECT 'Dia. Ext. ducto izquierdo' UNION ALL "
                        + "    SELECT 'Dia. Int. ducto izquierdo' UNION ALL "
                        + "    SELECT 'Ducto izquierdo' UNION ALL "
                        + "    SELECT 'Dia. Ext. ducto central' UNION ALL "
                        + "    SELECT 'Dia. Int. ducto central' UNION ALL "
                        + "    SELECT 'Ducto central' "
                        + "), "
                        + "datos_filtrados AS ( "
                        + "    SELECT "
                        + "        p.comparador, "
                        + "        h.toma1, h.toma2, h.toma3, h.toma4, h.toma5, h.toma6, h.toma7, h.toma8 "
                        + "    FROM registro_frecuencia_hora h "
                        + "    INNER JOIN parametro p ON h.id_parametro = p.id_parametro "
                        + "    INNER JOIN registro r ON h.id_registro = r.id_registro "
                        + "    INNER JOIN producto pd ON r.id_producto = pd.id_producto "
                        + "    INNER JOIN orden_produccion o ON pd.id_orden_produccion = o.id_orden_produccion "
                        + "    WHERE o.numero = " + Order + " "
                        + "      AND pd.codigo LIKE '%" + Product + "%' "
                        + "      AND r.lote_producto LIKE '%" + Batch + "%' "
                        + "      AND p.comparador IN (" + ArgParameter + ") "
                        + "), "
                        + "todas_las_tomas AS ( "
                        + "    SELECT comparador, toma1 AS valor FROM datos_filtrados WHERE toma1 IS NOT NULL AND toma1 <> 0 "
                        + "    UNION ALL SELECT comparador, toma2 FROM datos_filtrados WHERE toma2 IS NOT NULL AND toma2 <> 0 "
                        + "    UNION ALL SELECT comparador, toma3 FROM datos_filtrados WHERE toma3 IS NOT NULL AND toma3 <> 0 "
                        + "    UNION ALL SELECT comparador, toma4 FROM datos_filtrados WHERE toma4 IS NOT NULL AND toma4 <> 0 "
                        + "    UNION ALL SELECT comparador, toma5 FROM datos_filtrados WHERE toma5 IS NOT NULL AND toma5 <> 0 "
                        + "    UNION ALL SELECT comparador, toma6 FROM datos_filtrados WHERE toma6 IS NOT NULL AND toma6 <> 0 "
                        + "    UNION ALL SELECT comparador, toma7 FROM datos_filtrados WHERE toma7 IS NOT NULL AND toma7 <> 0 "
                        + "    UNION ALL SELECT comparador, toma8 FROM datos_filtrados WHERE toma8 IS NOT NULL AND toma8 <> 0 "
                        + "), "
                        + "agregados AS ( "
                        + "    SELECT "
                        + "        comparador, "
                        + "        MIN(valor) AS minimo, "
                        + "        MAX(valor) AS maximo, "
                        + "        ROUND(AVG(valor), 2) AS promedio "
                        + "    FROM todas_las_tomas "
                        + "    GROUP BY comparador "
                        + ") "
                        + "SELECT  "
                        + "    lc.comparador, "
                        + "    COALESCE(a.minimo, 0) AS minimo, "
                        + "    COALESCE(a.maximo, 0) AS maximo, "
                        + "    COALESCE(a.promedio, 0) AS promedio "
                        + "FROM lista_comparadores lc "
                        + "LEFT JOIN agregados a ON lc.comparador = a.comparador "
                        + ArgOrderBy;
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Qry);
                List<String> lst_param = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_param.add(count, rs.getString("comparador").trim() + " /// "
                            + rs.getString("minimo").trim() + " /// "
                            + rs.getString("maximo").trim() + " /// "
                            + rs.getString("promedio").trim() + "");
                    count++;
                }
                conn.close();
                return lst_param;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
        //</editor-fold>
    }
     public List DimensionalQueryRGC74(int Order, String Product, String Batch, String Comparator, String OrderBy) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="DIMESIONAL_QUERY R-GC-064">
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerRegistrosLab");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String ArgParameter = Comparator.replace("[", "").replace("]", "");
                String ArgOrderBy = OrderBy.replace("[", "").replace("]", "");
                String Qry = "WITH lista_comparadores AS ( "
                        + "    SELECT 'Longitud Total' AS comparador UNION ALL "
                        + "    SELECT 'Ancho de manga' UNION ALL "
                        + "    SELECT 'Pared doble' UNION ALL "
                        + "    SELECT 'Pared sencilla' UNION ALL "
                        + "    SELECT 'Pared sencilla estriada' UNION ALL "
                        + "    SELECT 'Dia. Ext. ducto derecho' UNION ALL "
                        + "    SELECT 'Dia. Int. ducto derecho' UNION ALL "
                        + "    SELECT 'Ducto derecho' "
                        + "), "
                        + "datos_filtrados AS ( "
                        + "    SELECT "
                        + "        p.comparador, "
                        + "        h.toma1, h.toma2, h.toma3, h.toma4, h.toma5, h.toma6, h.toma7, h.toma8 "
                        + "    FROM registro_frecuencia_hora h "
                        + "    INNER JOIN parametro p ON h.id_parametro = p.id_parametro "
                        + "    INNER JOIN registro r ON h.id_registro = r.id_registro "
                        + "    INNER JOIN producto pd ON r.id_producto = pd.id_producto "
                        + "    INNER JOIN orden_produccion o ON pd.id_orden_produccion = o.id_orden_produccion "
                        + "    WHERE o.numero = " + Order + " "
                        + "      AND pd.codigo LIKE '%" + Product + "%' "
                        + "      AND r.lote_producto LIKE '%" + Batch + "%' "
                        + "      AND p.comparador IN (" + ArgParameter + ") "
                        + "), "
                        + "todas_las_tomas AS ( "
                        + "    SELECT comparador, toma1 AS valor FROM datos_filtrados WHERE toma1 IS NOT NULL AND toma1 <> 0 "
                        + "    UNION ALL SELECT comparador, toma2 FROM datos_filtrados WHERE toma2 IS NOT NULL AND toma2 <> 0 "
                        + "    UNION ALL SELECT comparador, toma3 FROM datos_filtrados WHERE toma3 IS NOT NULL AND toma3 <> 0 "
                        + "    UNION ALL SELECT comparador, toma4 FROM datos_filtrados WHERE toma4 IS NOT NULL AND toma4 <> 0 "
                        + "    UNION ALL SELECT comparador, toma5 FROM datos_filtrados WHERE toma5 IS NOT NULL AND toma5 <> 0 "
                        + "    UNION ALL SELECT comparador, toma6 FROM datos_filtrados WHERE toma6 IS NOT NULL AND toma6 <> 0 "
                        + "    UNION ALL SELECT comparador, toma7 FROM datos_filtrados WHERE toma7 IS NOT NULL AND toma7 <> 0 "
                        + "    UNION ALL SELECT comparador, toma8 FROM datos_filtrados WHERE toma8 IS NOT NULL AND toma8 <> 0 "
                        + "), "
                        + "agregados AS ( "
                        + "    SELECT "
                        + "        comparador, "
                        + "        MIN(valor) AS minimo, "
                        + "        MAX(valor) AS maximo, "
                        + "        ROUND(AVG(valor), 2) AS promedio "
                        + "    FROM todas_las_tomas "
                        + "    GROUP BY comparador "
                        + ") "
                        + "SELECT  "
                        + "    lc.comparador, "
                        + "    COALESCE(a.minimo, 0) AS minimo, "
                        + "    COALESCE(a.maximo, 0) AS maximo, "
                        + "    COALESCE(a.promedio, 0) AS promedio "
                        + "FROM lista_comparadores lc "
                        + "LEFT JOIN agregados a ON lc.comparador = a.comparador "
                        + ArgOrderBy;
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Qry);
                List<String> lst_param = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_param.add(count, rs.getString("comparador").trim() + " /// "
                            + rs.getString("minimo").trim() + " /// "
                            + rs.getString("maximo").trim() + " /// "
                            + rs.getString("promedio").trim() + "");
                    count++;
                }
                conn.close();
                return lst_param;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
        //</editor-fold>
    }

    public List QueryTechnicalSheet(int Order, String Product, String Qrt) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="QUERY TECHNICALSHEET R-GC-064">
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerRegistrosLab");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String Qry = Qrt;
                Qry = Qry.replace("XorderX", String.valueOf(Order));
                Qry = Qry.replace("XproductX", Product);
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Qry);
                List<String> lst_param = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_param.add(count, rs.getString("Cant").trim() + " /// "
                            + rs.getString("Longitud Total").trim() + " /// "
                            + rs.getString("Ancho de manga").trim() + " /// "
                            + rs.getString("Pared doble").trim() + " /// "
                            + rs.getString("Pared sencilla").trim() + " /// "
                            + rs.getString("Dia. Ext. ducto derecho").trim() + " /// "
                            + rs.getString("Dia. Int. ducto derecho").trim() + " /// "
                            + rs.getString("Ducto derecho").trim() + " /// "
                            + rs.getString("Dia. Ext. ducto izquierdo").trim() + " /// "
                            + rs.getString("Dia. Int. ducto izquierdo").trim() + " /// "
                            + rs.getString("Ducto izquierdo").trim() + " /// "
                            + rs.getString("Dia. Ext. ducto central").trim() + " /// "
                            + rs.getString("Dia. Int. ducto central").trim() + " /// "
                            + rs.getString("Ducto central").trim() + " /// "
                            + rs.getString("Soldadura bocas").trim() + " /// "
                            + rs.getString("Soldadura colas").trim() + " /// "
                            + rs.getString("Ficha Tecnica").trim() + "");
                    count++;
                }
                conn.close();
                return lst_param;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
        //</editor-fold>
    }
    
    public List QueryTechnicalSheetRGC74(int Order, String Product, String Qrt) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="QUERY TECHNICALSHEET R-GC-074">
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerRegistrosLab");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String Qry = Qrt;
                Qry = Qry.replace("XorderX", String.valueOf(Order));
                Qry = Qry.replace("XproductX", Product);
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Qry);
                List<String> lst_param = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_param.add(count, rs.getString("Cant").trim() + " /// "
                            + rs.getString("Longitud Total").trim() + " /// "
                            + rs.getString("Ancho de manga").trim() + " /// "
                            + rs.getString("Pared doble").trim() + " /// "
                            + rs.getString("Pared sencilla").trim() + " /// "
                            + rs.getString("Pared sencilla estriada").trim() + " /// "
                            + rs.getString("Dia. Ext. ducto derecho").trim() + " /// "
                            + rs.getString("Dia. Int. ducto derecho").trim() + " /// "
                            + rs.getString("Ducto derecho").trim() + " /// "
                            + rs.getString("Soldadura bocas").trim() + " /// "
                            + rs.getString("Soldadura colas").trim() + " /// "
                            + rs.getString("Ficha Tecnica").trim() + "");
                    count++;
                }
                conn.close();
                return lst_param;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
        //</editor-fold>
    }
    
     public List QueryWelds(int Order, String Product, String Batch, String Comparator) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="QUERY WELD">
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerRegistrosLab");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String Qry = "WITH datos_filtrados AS ( "
                        + "    SELECT h.toma1, h.toma2 "
                        + "    FROM registro_espesor_" + Comparator + " h "
                        + "    INNER JOIN registro r ON h.id_registro = r.id_registro "
                        + "    INNER JOIN producto pd ON r.id_producto = pd.id_producto "
                        + "    INNER JOIN orden_produccion o ON pd.id_orden_produccion = o.id_orden_produccion "
                        + "    WHERE o.numero = " + Order + " "
                        + "    AND pd.codigo LIKE '%" + Product + "%' "
                        + "    AND r.lote_producto LIKE '%" + Batch + "%' "
                        + ") "
                        + "SELECT  "
                        + "    MIN(valor) AS minimo_global, "
                        + "    MAX(valor) AS maximo_global, "
                        + "    ROUND(AVG(valor),2) AS promedio_global "
                        + "FROM ( "
                        + "    SELECT toma1 AS valor FROM datos_filtrados WHERE toma1 IS NOT NULL AND toma1 <> 0 "
                        + "    UNION ALL SELECT toma2 FROM datos_filtrados WHERE toma2 IS NOT NULL AND toma2 <> 0 "
                        + ") AS todas_las_tomas;";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Qry);
                List<String> lst_param = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_param.add(count, rs.getString("minimo_global").trim() + " /// "
                            + rs.getString("maximo_global").trim() + " /// "
                            + rs.getString("promedio_global").trim() + "");
                    count++;
                }
                conn.close();
                return lst_param;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
        //</editor-fold>
    }

}
