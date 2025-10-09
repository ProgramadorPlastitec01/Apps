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
                String Qry = "WITH datos_base AS (\n"
                        + "    SELECT \n"
                        + "        SUBSTRING(r.lote_manga_p, 1, 4) AS `REF LAY FLAT`,\n"
                        + "        r.lote_manga_c,\n"
                        + "        SUBSTRING(r.lote_dto_drc_p, 1, 4) AS `REF DD`,\n"
                        + "        r.lote_dto_drc_c,\n"
                        + "        SUBSTRING(r.lote_dto_iqe_p, 1, 4) AS `REF DI`,\n"
                        + "        r.lote_dto_iqe_c,\n"
                        + "        SUBSTRING(r.lote_ensamble, 1, 4) AS `REF CONECTOR`,\n"
                        + "        r.lote_ensamble AS `Lote CO`,\n"
                        + "        r.lote_tinta,\n"
                        + "        SUBSTRING(r.lote_dto_ctl_p, 1, 4) AS `REF CL`,\n"
                        + "        r.lote_dto_ctl_c\n"
                        + "    FROM orden_produccion o\n"
                        + "    INNER JOIN producto p ON o.id_orden_produccion = p.id_orden_produccion\n"
                        + "    INNER JOIN registro r ON p.id_producto = r.id_producto\n"
                        + "    INNER JOIN linea l ON r.id_linea = l.id_linea "
                        + "    WHERE o.numero = " + Order + " \n"
                        + "      AND p.codigo LIKE '%" + Product + "%'\n"
                        + "      AND r.lote_producto LIKE '%" + Batch + "%'\n"
                        + "      AND l.nombre NOT LIKE '%SCREEN%'"
                        + "    LIMIT 1\n"
                        + ")\n"
                        + "\n"
                        + "SELECT \n"
                        + "    `REF LAY FLAT`,\n"
                        + "\n"
                        + "    -- Lote LF\n"
                        + "    CASE \n"
                        + "        WHEN lote_manga_c LIKE '%/%' THEN lote_manga_c\n"
                        + "        WHEN lote_manga_c LIKE '%V%' OR lote_manga_c LIKE '%E%' OR lote_manga_c LIKE '%R%' OR lote_manga_c LIKE '%P%' THEN \n"
                        + "            REGEXP_REPLACE(\n"
                        + "                REGEXP_REPLACE(lote_manga_c, '^[A-Z0-9]+-', ''),\n"
                        + "                'V[0-9A-Z]+', ''\n"
                        + "            )\n"
                        + "        ELSE lote_manga_c\n"
                        + "    END AS `Lote LF`,\n"
                        + "    \n"
                        + "    `REF DD`,\n"
                        + "    CASE \n"
                        + "        WHEN lote_dto_drc_c LIKE '%/%' THEN lote_dto_drc_c\n"
                        + "        WHEN lote_dto_drc_c LIKE '%V%' OR lote_dto_drc_c LIKE '%E%' OR lote_dto_drc_c LIKE '%R%' OR lote_dto_drc_c LIKE '%P%' THEN \n"
                        + "            REGEXP_REPLACE(\n"
                        + "                REGEXP_REPLACE(lote_dto_drc_c, '^[A-Z0-9]+-', ''),\n"
                        + "                'V[0-9A-Z]+', ''\n"
                        + "            )\n"
                        + "        ELSE lote_dto_drc_c\n"
                        + "    END AS `Lote DD`,\n"
                        + "\n"
                        + "    `REF DI`,\n"
                        + "    CASE \n"
                        + "        WHEN lote_dto_iqe_c LIKE '%/%' THEN lote_dto_iqe_c\n"
                        + "        WHEN lote_dto_iqe_c LIKE '%V%' OR lote_dto_iqe_c LIKE '%E%' OR lote_dto_iqe_c LIKE '%R%' OR lote_dto_iqe_c LIKE '%P%' THEN \n"
                        + "            REGEXP_REPLACE(\n"
                        + "                REGEXP_REPLACE(lote_dto_iqe_c, '^[A-Z0-9]+-', ''),\n"
                        + "                'V[0-9A-Z]+', ''\n"
                        + "            )\n"
                        + "        ELSE lote_dto_iqe_c\n"
                        + "    END AS `Lote DI`,\n"
                        + "\n"
                        + "    `REF CONECTOR`,\n"
                        + "    `Lote CO`,\n"
                        + "    lote_tinta,\n"
                        + "\n"
                        + "    `REF CL`,\n"
                        + "    CASE \n"
                        + "        WHEN lote_dto_ctl_c LIKE '%/%' THEN lote_dto_ctl_c\n"
                        + "        WHEN lote_dto_ctl_c LIKE '%V%' OR lote_dto_ctl_c LIKE '%E%' OR lote_dto_ctl_c LIKE '%R%' OR lote_dto_ctl_c LIKE '%P%' THEN \n"
                        + "            REGEXP_REPLACE(\n"
                        + "                REGEXP_REPLACE(lote_dto_ctl_c, '^[A-Z0-9]+-', ''),\n"
                        + "                'V[0-9A-Z]+', ''\n"
                        + "            )\n"
                        + "        ELSE lote_dto_ctl_c\n"
                        + "    END AS `Lote CL`\n"
                        + "\n"
                        + "FROM datos_base;";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Qry);
                List<String> lst_user = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_user.add(count, rs.getString("REF LAY FLAT").trim() + " /// "
                            + rs.getString("Lote LF").trim() + " /// " + rs.getString("REF DD").trim() + " /// "
                            + rs.getString("Lote DD").trim() + " /// " + rs.getString("REF DI").trim() + "///"
                            + rs.getString("Lote DI").trim() + "///" + rs.getString("REF CONECTOR").trim() + "///"
                            + rs.getString("Lote CO").trim() + "///" + rs.getString("lote_tinta").trim() + "///"
                            + rs.getString("REF CL").trim() + "///" + rs.getString("Lote CL").trim() + "");
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
    }

    public List DimensionalQuery(int Order, String Product, String Batch, String Comparator, String OrderBy) throws Exception {
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
                String Qry = "WITH datos_filtrados AS ( "
                        + "    SELECT  "
                        + "        p.comparador, "
                        + "        h.toma1, "
                        + "        h.toma2, "
                        + "        h.toma3, "
                        + "        h.toma4, "
                        + "        h.toma5, "
                        + "        h.toma6, "
                        + "        h.toma7, "
                        + "        h.toma8 "
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
                        + ") "
                        + "SELECT  "
                        + "    comparador, "
                        + "    MIN(valor) AS minimo, "
                        + "    MAX(valor) AS maximo, "
                        + "    ROUND(AVG(valor), 2) AS promedio "
                        + "FROM todas_las_tomas "
                        + "GROUP BY comparador "
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
    }

    public List QueryWelds(int Order, String Product, String Batch, String Comparator) throws Exception {
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
    }

    public List QueryTechnicalSheet(int Order, String Product, String Qrt) throws Exception {
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
                            + rs.getString("Dia. Ext. ducto central").trim() + " /// "
                            + rs.getString("Dia. Int. ducto central").trim() + " /// "
                            + rs.getString("Ducto central").trim() + " /// "
                            + rs.getString("Soldadura bocas").trim() + " /// "
                            + rs.getString("Soldadura colas").trim() + "");
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
    }

}
