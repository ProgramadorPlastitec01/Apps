package Connection;

import Controller.SettingJpaController;
import Method.EstadisticosInspeccionManga;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LinkBatchRecord {

    private static final Logger LOGGER = Logger.getLogger(LinkBatchRecord.class.getName());

    SettingJpaController SettingJpa = new SettingJpaController();

    static String login = "";
    static String password = "";
    static String url = "";

    public List LinkBatchRecord(String Order, String Batch) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="LinkBatchRecord">
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerRegistrosLab");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            LOGGER.severe("LinkBatchRecord.LinkBatchRecord: no se encontró configuración 'ServerRegistrosLab' en Setting");
            return null;
        }
        List lst_link = SettingJpa.ConsultSettingCategorie("BatchRecordLAB");
        String Qry = "";
        if (lst_link != null) {
            Object[] obj_link = (Object[]) lst_link.get(0);
            Qry = obj_link[2].toString();
        } else {
            LOGGER.severe("LinkBatchRecord.LinkBatchRecord: no se encontró configuración 'BatchRecordLAB' en Setting");
            Qry = "";
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null && !Qry.equals("")) {
                Qry = Qry.replace("XOrderX", Order);
                Qry = Qry.replace("XBatchX", Batch);
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Qry);
                List<String> lst_batch = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_batch.add(count, rs.getString("Type").trim() + " /// " + rs.getString("TypeReg").trim() + " /// " + rs.getString("Data").trim() + " /// " + rs.getString("Link").trim() + "");
                    count++;
                }
                conn.close();
                return lst_batch;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.LinkBatchRecord: SQLException conectando a " + url, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.LinkBatchRecord: ClassNotFoundException", ex);
            return null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.LinkBatchRecord: Exception", ex);
            return null;
        }
        //</editor-fold>
    }

    public List AttachmentBatchRecord(String DataBatch) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="LinkBatchRecord GENERACION LOTES">
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerGeneracionLotes");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            LOGGER.severe("LinkBatchRecord.AttachmentBatchRecord: no se encontró configuración 'ServerGeneracionLotes' en Setting");
            return null;
        }
        List lst_link = SettingJpa.ConsultSettingCategorie("BatchRecordGeneracionLotes");
        String Qry = "";
        if (lst_link != null) {
            Object[] obj_link = (Object[]) lst_link.get(0);
            Qry = obj_link[2].toString();
        } else {
            LOGGER.severe("LinkBatchRecord.AttachmentBatchRecord: no se encontró configuración 'BatchRecordGeneracionLotes' en Setting");
            Qry = "";
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null && !Qry.equals("")) {
                if (!DataBatch.equals("")) {
                    String[] ArgBatch = DataBatch.split(",");
                    List<String> lst_lotes = new ArrayList<String>();
                    for (int i = 0; i < ArgBatch.length; i++) {
                        String[] SubBatch = ArgBatch[i].split("/");
                        for (int j = 0; j < SubBatch.length; j++) {
                            lst_lotes.add(SubBatch[j]);
                        }
                    }
                    String Batch = "";
                    Batch = "(";
                    for (int i = 0; i < lst_lotes.size(); i++) {
                        if (i == lst_lotes.size() - 1) {
                            Batch += "'" + lst_lotes.get(i) + "'";
                        } else {
                            Batch += "'" + lst_lotes.get(i) + "',";
                        }
                    }
                    Batch += ")";
                    Qry = Qry.replace("XDataBatchX", Batch);
                    Statement sttm = conn.createStatement();
                    ResultSet rs = sttm.executeQuery(Qry);
                    List<String> lst_batch = new ArrayList<String>();
                    int count = 0;
                    while (rs.next()) {
                        lst_batch.add(count, rs.getString("Id_anexos").trim() + " /// " + rs.getString("Registro").trim() + " /// " + rs.getString("Nombre").trim() + " /// " + rs.getString("Descripcion").trim() + "");
                        count++;
                    }
                    conn.close();
                    return lst_batch;
                }
            } else {
                return null;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.AttachmentBatchRecord: SQLException conectando a " + url, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.AttachmentBatchRecord: ClassNotFoundException", ex);
            return null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.AttachmentBatchRecord: Exception", ex);
            return null;
        }
        //</editor-fold>
        return null;
    }

    public List RGC17BatchRecord(String Order, String DataBatch) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="RGC17BatchRecord">
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerRegistrosLab");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            LOGGER.severe("LinkBatchRecord.RGC17BatchRecord: no se encontró configuración 'ServerRegistrosLab' en Setting");
            return null;
        }
        List lst_link = SettingJpa.ConsultSettingCategorie("BatchRecordResumen");
        String Qry = "";
        if (lst_link != null) {
            Object[] obj_link = (Object[]) lst_link.get(0);
            Qry = obj_link[2].toString();
        } else {
            LOGGER.severe("LinkBatchRecord.RGC17BatchRecord: no se encontró configuración 'BatchRecordResumen' en Setting");
            Qry = "";
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null && !Qry.equals("")) {
                Qry = Qry.replace("XOrderX", Order);
                Qry = Qry.replace("XBatchX", DataBatch);
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Qry);
                List<String> lst_batch = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_batch.add(count, rs.getString("Tipo").trim() + " /// " + rs.getString("Txt_orden").trim() + " /// " + rs.getString("Cbx_producto").trim() + " /// " + rs.getString("Cbx_lote").trim() + " /// "
                            + rs.getString("Txt_fecha_inicio").trim() + " /// " + rs.getString("Txt_fecha_fin").trim() + " /// " + rs.getString("Txt_hora_inicio").trim() + " /// "
                            + rs.getString("Txt_hora_fin").trim() + " /// " + rs.getString("Txt_numero_certificado").trim() + " /// " + rs.getString("Txt_fecha_despacho").trim() + " /// "
                            + rs.getString("Txt_datos_totales").trim() + " /// " + rs.getString("Txt_usuario_responsable").trim() + " /// " + rs.getString("Id_resumen").trim() + " /// "
                            + rs.getString("Informacion").trim() + "");
                    count++;
                }
                conn.close();
                return lst_batch;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.RGC17BatchRecord: SQLException conectando a " + url, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.RGC17BatchRecord: ClassNotFoundException", ex);
            return null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.RGC17BatchRecord: Exception", ex);
            return null;
        }
        //</editor-fold>
    }

    /**
     * Resumen estadístico de Inspección Manga (Reporte por lote, "RESUMEN PVC
     * Y PP" / Tipo_consulta=3) para un lote_producto + lote_c, tomando
     * lote_p="TODOS" (misma semántica que
     * sp_rlo_t_rollo_lotes_todos_p_estadistico/sp_cep..._todos_p en la app
     * original: agrega todos los lote_p de esa referencia).
     *
     * Se conecta DIRECTO a la base de datos de Inspección Manga en vez de
     * pedirle la página HTML: su servlet "Reporte" siempre exige sesión de
     * usuario logeado (correcto para sus propios usuarios), y COA no tiene ni
     * debe tener esa sesión. Por eso se reimplementa aquí, con datos crudos,
     * el mismo cálculo que hace Metodos.Estadisticos en esa app (ver
     * Method.EstadisticosInspeccionManga).
     */
    /**
     * Explica por qué la última llamada a InspeccionMangaResumenEstadistico
     * devolvió null (config faltante, error de conexión, o simplemente no
     * hay datos para ese lote_producto/lote_c) — usado por el listado de
     * FileManager.jsp para mostrarle al usuario una razón concreta en vez de
     * simplemente omitir la fila en silencio. null si la última llamada tuvo
     * éxito o si el método aún no se ha invocado.
     */
    private String diagnosticoManga;

    public String getDiagnosticoManga() {
        return diagnosticoManga;
    }

    public Map<String, Object> InspeccionMangaResumenEstadistico(String LoteProducto, String LoteC) throws Exception {
        diagnosticoManga = null;
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerInspeccionManga");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            LOGGER.severe("LinkBatchRecord.InspeccionMangaResumenEstadistico: no se encontró configuración 'ServerInspeccionManga' en Setting");
            diagnosticoManga = "No hay configuración de conexión a Inspección Manga (falta la categoría 'ServerInspeccionManga' en Setting).";
            return null;
        }

        String lpd = LoteProducto == null ? "" : LoteProducto.replace("'", "''");
        String ltc = LoteC == null ? "" : LoteC.replace("'", "''");

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);

            Statement sttmRollo = conn.createStatement();
            ResultSet rs = sttmRollo.executeQuery("CALL sp_rlo_t_rollo_lotes_todos_p_estadistico('" + lpd + "','" + ltc + "')");
            // Al ser una consulta con MIN/MAX/AVG sin GROUP BY, SIEMPRE devuelve
            // una fila incluso sin ningún dato que coincida — pero con todas las
            // columnas en NULL. rs.getString(2) (nombre del producto) es NULL
            // solo en ese caso, así que sirve para distinguir "no hay coincidencia
            // para este lote_producto/lote_c" de un resultado real.
            if (!rs.next() || rs.getString(2) == null) {
                rs.close();
                sttmRollo.close();
                conn.close();
                diagnosticoManga = "No se encontraron datos en Inspección Manga para Lote Producto '" + LoteProducto
                        + "' y Lote C '" + LoteC + "' (verifique que los lotes coincidan con los registrados en Inspección Manga).";
                return null;
            }

            Map<String, Object> resumen = new HashMap<String, Object>();
            resumen.put("producto", rs.getString(2));
            resumen.put("fichaCodigo", rs.getString(3));
            resumen.put("fichaVersion", rs.getString(4));
            resumen.put("loteProducto", rs.getString(5));
            resumen.put("loteC", rs.getString(6));
            resumen.put("loteP", rs.getString(7));
            resumen.put("minPD", rs.getObject(8));
            resumen.put("maxPD", rs.getObject(9));
            resumen.put("avgPD", rs.getObject(10));
            resumen.put("minPS", rs.getObject(11));
            resumen.put("maxPS", rs.getObject(12));
            resumen.put("avgPS", rs.getObject(13));
            resumen.put("minAM", rs.getObject(14));
            resumen.put("maxAM", rs.getObject(15));
            resumen.put("avgAM", rs.getObject(16));
            resumen.put("minAB", rs.getObject(17));
            resumen.put("maxAB", rs.getObject(18));
            resumen.put("avgAB", rs.getObject(19));
            resumen.put("minPB", rs.getObject(20));
            resumen.put("maxPB", rs.getObject(21));
            resumen.put("avgPB", rs.getObject(22));
            resumen.put("minPN", rs.getObject(23));
            resumen.put("maxPN", rs.getObject(24));
            resumen.put("avgPN", rs.getObject(25));
            resumen.put("minPC", rs.getObject(26));
            resumen.put("maxPC", rs.getObject(27));
            resumen.put("avgPC", rs.getObject(28));
            resumen.put("minPI", rs.getObject(29));
            resumen.put("maxPI", rs.getObject(30));
            resumen.put("avgPI", rs.getObject(31));
            int cantidadEvaluar = rs.getInt(38);
            int material = rs.getInt(39);
            int aplicaPd = rs.getInt(40);
            resumen.put("minCV", rs.getObject(41));
            resumen.put("maxCV", rs.getObject(42));
            resumen.put("avgCV", rs.getObject(43));
            resumen.put("minDR", rs.getObject(44));
            resumen.put("maxDR", rs.getObject(45));
            resumen.put("avgDR", rs.getObject(46));
            rs.close();
            sttmRollo.close();

            String procControles = (material == 1) ? "sp_cepp_t_control_espesor_pp_lotes_todos_p" : "sp_cep_t_control_espesor_lotes_todos_p";
            Statement sttmControles = conn.createStatement();
            ResultSet rsControles = sttmControles.executeQuery("CALL " + procControles + "('" + lpd + "','" + ltc + "')");
            ResultSetMetaData meta = rsControles.getMetaData();
            int cols = meta.getColumnCount();
            List<Object[]> lstControles = new ArrayList<Object[]>();
            while (rsControles.next()) {
                Object[] fila = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    fila[i] = rsControles.getObject(i + 1);
                }
                lstControles.add(fila);
            }
            rsControles.close();
            sttmControles.close();
            conn.close();

            EstadisticosInspeccionManga.Resultado calc = EstadisticosInspeccionManga.calcular(cantidadEvaluar, lstControles, material, aplicaPd);
            resumen.put("minPSCalidad", calc.minPS);
            resumen.put("maxPSCalidad", calc.maxPS);
            resumen.put("promPSCalidad", calc.promPS);
            resumen.put("minPDCalidad", calc.minPD);
            resumen.put("maxPDCalidad", calc.maxPD);
            resumen.put("promPDCalidad", calc.promPD);

            return resumen;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.InspeccionMangaResumenEstadistico: SQLException conectando a " + url, ex);
            diagnosticoManga = "No se pudo conectar a la base de datos de Inspección Manga (" + url + "): " + ex.getMessage();
            return null;
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.InspeccionMangaResumenEstadistico: ClassNotFoundException", ex);
            diagnosticoManga = "No se encontró el driver de conexión a la base de datos de Inspección Manga.";
            return null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.InspeccionMangaResumenEstadistico: Exception", ex);
            diagnosticoManga = "Error inesperado al consultar Inspección Manga: " + ex.getMessage();
            return null;
        }
    }

}
