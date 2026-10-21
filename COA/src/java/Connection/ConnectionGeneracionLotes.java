package Connection;

import Controller.SettingJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionGeneracionLotes {

    SettingJpaController SettingJpa = new SettingJpaController();

    static String login = "";
    static String password = "";
    static String url = "";

    public List ConsultarCC_GeneracionLote(String Lote) throws Exception {
        return ConsultarCC_GeneracionLote(Lote, null);
    }

    public List ConsultarCC_GeneracionLote(String Lote, String Proceso) throws Exception {
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerGeneracionLotes");
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
                // El nombre del proceso en Generación de Lotes trae el material como
                // sufijo (MANGA PVC, MANGA PP, MANGA EVA...) y varía según la línea de
                // producto del certificado, pero COA solo conoce la familia de
                // proceso (MANGA/DUCTO/CONECTOR), no el material exacto. Filtrar por
                // igualdad exacta contra un solo nombre (ej. "MANGA PVC") dejaba sin
                // datos a certificados de líneas PP/EVA que comparten el mismo lote
                // ambiguo pero con otro sufijo de proceso. Se filtra por la familia
                // completa (p.nombre IN (...)) para seguir separando MANGA de DUCTO
                // de CONECTOR (que es el objetivo real del filtro), sin importar el
                // material.
                String procesoFilter = "";
                if (Proceso != null && !Proceso.trim().isEmpty()) {
                    String[] variantes = variantesProceso(Proceso.trim());
                    StringBuilder nombresIn = new StringBuilder();
                    for (int i = 0; i < variantes.length; i++) {
                        if (i > 0) {
                            nombresIn.append(",");
                        }
                        nombresIn.append("'").append(variantes[i].replace("'", "''")).append("'");
                    }
                    procesoFilter = "INNER JOIN procesos p ON p.id_procesos = c.id_proceso AND p.nombre IN (" + nombresIn + ") ";
                }
                String query = "";
                if (Lote.contains("/")) {
                    String[] BatchN = Lote.split("/");
                    // El separador deja un salto de línea (en vez de " - ")
                    // para que el consecutivo de un lote compuesto ("A/B")
                    // quede alineado, línea por línea, con el lote (ver
                    // Tag.Visual, reemplazo de los placeholders "LBTn").
                    query = "SELECT GROUP_CONCAT( "
                            + "    c.consecutivo "
                            + "    ORDER BY FIELD(c.lote, '" + BatchN[0].trim() + "', '" + BatchN[1].trim() + "') "
                            + "    SEPARATOR '<br>CC' "
                            + ") AS consecutivo "
                            + "FROM control_consecutivos c "
                            + procesoFilter
                            + "WHERE c.lote IN ('" + BatchN[0].trim() + "', '" + BatchN[1].trim() + "');";
                } else {
                    query = "SELECT c.consecutivo FROM control_consecutivos c "
                            + procesoFilter
                            + "WHERE c.lote LIKE '%" + Lote.trim() + "%'";
                }
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_consecutivos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    String consecutivo = rs.getString("consecutivo");
                    if (consecutivo != null) {
                        lst_consecutivos.add(count, consecutivo.trim());
                    }
                    count++;
                }

                conn.close();
                return lst_consecutivos;
            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            return null;
        }
    }

    /**
     * Salvedad de {@link #ConsultarCC_GeneracionLote(String, String)}: cuando
     * ese filtro por familia de proceso no encuentra nada (puede haber un
     * nombre de proceso nuevo que variantesProceso() todavía no conoce, o el
     * material de Registros LAB puede estar nombrado distinto al de
     * Generación de Lotes), es mejor traer el dato sin filtrar por proceso
     * que dejar el consecutivo vacío. Devuelve TODOS los consecutivos que
     * coincidan solo por lote, cada uno como "consecutivo///proceso///fecha",
     * para que el llamador decida: si hay uno solo, se usa directo; si hay
     * varios, se deja elegir al usuario (igual que ya pasa con la tinta en
     * Util.replaceLoteReception). No cubre lotes compuestos ("A/B"): ese caso
     * sigue mostrando "sin datos" si la búsqueda por proceso falla, es un
     * escenario más raro que no vale la pena resolver con un selector.
     */
    public List<String> ConsultarCC_GeneracionLoteDetalle(String Lote) throws Exception {
        if (Lote == null || Lote.trim().isEmpty() || Lote.contains("/")) {
            return null;
        }
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerGeneracionLotes");
        if (lst_parameter == null) {
            return null;
        }
        Object[] obj_data = (Object[]) lst_parameter.get(0);
        String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
        login = arr_data[0];
        password = arr_data[1];
        url = "jdbc:mysql://" + arr_data[2];

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn == null) {
                return null;
            }
            String query = "SELECT c.consecutivo, p.nombre, c.fecha "
                    + "FROM control_consecutivos c "
                    + "LEFT JOIN procesos p ON p.id_procesos = c.id_proceso "
                    + "WHERE c.lote LIKE '%" + Lote.trim().replace("'", "''") + "%' "
                    + "ORDER BY c.fecha_registro DESC";
            Statement sttm = conn.createStatement();
            ResultSet rs = sttm.executeQuery(query);
            List<String> lst_detalle = new ArrayList<String>();
            while (rs.next()) {
                String consecutivo = rs.getString("consecutivo");
                if (consecutivo == null || consecutivo.trim().isEmpty()) {
                    continue;
                }
                String proceso = rs.getString("nombre");
                String fecha = rs.getString("fecha");
                lst_detalle.add(consecutivo.trim() + "///" + (proceso == null ? "" : proceso.trim())
                        + "///" + (fecha == null ? "" : fecha.trim()));
            }
            conn.close();
            return lst_detalle;
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Nombres reales (tabla procesos) que corresponden a una familia de
     * proceso. Los llamadores (Tag.Visual) pasan nombres históricos como
     * "MANGA PVC" o "DUCTO PVC"; se reconoce la familia por el prefijo y se
     * expande a todas las variantes de material conocidas (PVC/PP/EVA), así
     * un certificado de línea PP sigue encontrando su consecutivo aunque el
     * código solo conozca el nombre "...PVC". Cualquier otro valor (p.ej.
     * "CONECTOR") se usa tal cual, sin expandir.
     */
    private static String[] variantesProceso(String proceso) {
        String familia = proceso.toUpperCase();
        if (familia.startsWith("MANGA")) {
            return new String[]{"MANGA PVC", "MANGA PP", "MANGA EVA"};
        }
        if (familia.startsWith("DUCTO")) {
            return new String[]{"DUCTO PVC", "DUCTO PP", "DUCTO EVA"};
        }
        return new String[]{proceso};
    }

    public List ConsultarCC_RepcecionMaterial(String Lote) throws Exception {
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerGeneracionLotes");
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
                String query = "SELECT r.consecutivo,r.codigo FROM recepcion_material r  WHERE r.lote LIKE '%" + Lote.trim() + "%'";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_consecutivos = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_consecutivos.add(count, rs.getString("consecutivo").trim() + "///" + rs.getString("codigo").trim());
                    count++;
                }
                conn.close();
                return lst_consecutivos;
            } else {
                return null;
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            return null;
        }
    }
}
