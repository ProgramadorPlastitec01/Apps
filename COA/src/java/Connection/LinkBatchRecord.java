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

    /**
     * Explica por qué la última llamada a InspeccionMangaRegistrosDespeje
     * devolvió null (mismo propósito que {@link #getDiagnosticoManga()}, pero
     * para los registros de despeje en vez del resumen estadístico).
     */
    private String diagnosticoDespeje;

    public String getDiagnosticoDespeje() {
        return diagnosticoDespeje;
    }

    /**
     * Registros de Despeje de Línea de Inspección Manga ("VER REGISTROS DE
     * DESPEJE" de su Reporte por lote) para un lote_producto + lote_c, con
     * lote_p="TODOS" (misma semántica que sp_rgt_t_registro_depeje_lotes_todos_p:
     * trae todos los despejes de esa referencia, no uno solo).
     * <p>
     * A diferencia del Resumen Estadístico (que hay que recalcular a partir
     * de datos crudos), cada despeje ya tiene su HTML final completo
     * guardado en registro_despeje.formato — el mismo documento que se abre
     * con el ícono de la lupa/copia en su listado (Orden?opc=14) — así que
     * solo hace falta traerlo tal cual, sin reconstruir nada.
     */
    public List<Map<String, Object>> InspeccionMangaRegistrosDespeje(String LoteProducto, String LoteC) throws Exception {
        diagnosticoDespeje = null;
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerInspeccionManga");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            LOGGER.severe("LinkBatchRecord.InspeccionMangaRegistrosDespeje: no se encontró configuración 'ServerInspeccionManga' en Setting");
            diagnosticoDespeje = "No hay configuración de conexión a Inspección Manga (falta la categoría 'ServerInspeccionManga' en Setting).";
            return null;
        }

        String lpd = LoteProducto == null ? "" : LoteProducto.replace("'", "''");
        String ltc = LoteC == null ? "" : LoteC.replace("'", "''");

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            Statement sttm = conn.createStatement();
            ResultSet rs = sttm.executeQuery("CALL sp_rgt_t_registro_depeje_lotes_todos_p('" + lpd + "','" + ltc + "')");

            List<Map<String, Object>> lstDespejes = new ArrayList<Map<String, Object>>();
            while (rs.next()) {
                Map<String, Object> despeje = new HashMap<String, Object>();
                despeje.put("idRegistro", rs.getObject(1));
                despeje.put("fechaTurno", rs.getObject(2));
                despeje.put("turnoProduccion", rs.getObject(3));
                despeje.put("responsablesProduccion", rs.getObject(4));
                despeje.put("loteProducto", rs.getString(5));
                despeje.put("loteC", rs.getString(6));
                despeje.put("loteP", rs.getString(7));
                despeje.put("turnoCalidad", rs.getObject(8));
                despeje.put("responsablesCalidad", rs.getObject(9));
                despeje.put("estadoPi", rs.getObject(10));
                despeje.put("estadoGc", rs.getObject(11));
                despeje.put("idRegistroDespeje", rs.getObject(12));
                despeje.put("formato", rs.getString(13));
                despeje.put("estado", rs.getObject(14));
                lstDespejes.add(despeje);
            }
            rs.close();
            sttm.close();
            conn.close();
            return lstDespejes;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.InspeccionMangaRegistrosDespeje: SQLException conectando a " + url, ex);
            diagnosticoDespeje = "No se pudo conectar a la base de datos de Inspección Manga (" + url + "): " + ex.getMessage();
            return null;
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.InspeccionMangaRegistrosDespeje: ClassNotFoundException", ex);
            diagnosticoDespeje = "No se encontró el driver de conexión a la base de datos de Inspección Manga.";
            return null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.InspeccionMangaRegistrosDespeje: Exception", ex);
            diagnosticoDespeje = "Error inesperado al consultar los registros de despeje de Inspección Manga: " + ex.getMessage();
            return null;
        }
    }

    /**
     * Explica por qué la última llamada a InspeccionMangaRegistrosCabecera
     * devolvió null (mismo propósito que {@link #getDiagnosticoManga()}).
     */
    private String diagnosticoCabecera;

    public String getDiagnosticoCabecera() {
        return diagnosticoCabecera;
    }

    /**
     * Registros de Cabecera de Inspección Manga: la misma información base
     * de la tabla {@code registro} (join con producto/línea/ficha técnica)
     * que arma su listado principal ("Producción Turno X" / "Calidad Turno
     * Y"), pero solo los datos informativos — sin los botones/formularios de
     * abrir-cerrar registro, firmar, pasar rollos, etc. de esa pantalla, que
     * no aplican en un PDF de auditoría de solo lectura.
     * <p>
     * A diferencia de {@code sp_rgt_c_registro_orden_producto} (que filtra
     * por {@code id_producto}/{@code numero} de ORDEN, datos que COA no
     * tiene ni debe intentar mapear 1:1 — la orden de LAB puede diferir de
     * la de Manga), esta es una consulta propia con el mismo JOIN pero
     * filtrada por {@code lote_producto}/{@code lote_c}, igual que el
     * Resumen Estadístico y los Registros de Despeje.
     */
    public List<Map<String, Object>> InspeccionMangaRegistrosCabecera(String LoteProducto, String LoteC) throws Exception {
        diagnosticoCabecera = null;
        List lst_parameter = SettingJpa.ConsultSettingCategorie("ServerInspeccionManga");
        if (lst_parameter != null) {
            Object[] obj_data = (Object[]) lst_parameter.get(0);
            String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://" + arr_data[2];
        } else {
            LOGGER.severe("LinkBatchRecord.InspeccionMangaRegistrosCabecera: no se encontró configuración 'ServerInspeccionManga' en Setting");
            diagnosticoCabecera = "No hay configuración de conexión a Inspección Manga (falta la categoría 'ServerInspeccionManga' en Setting).";
            return null;
        }

        String lpd = LoteProducto == null ? "" : LoteProducto.replace("'", "''");
        String ltc = LoteC == null ? "" : LoteC.replace("'", "''");

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            Statement sttm = conn.createStatement();
            String sql = "SELECT r.fecha_turno, r.turno_produccion, r.responsables_produccion, "
                    + "r.lote_producto, r.lote_c, r.lote_p, l.nombre, r.factor_medida, r.turno_calidad, "
                    + "r.responsables_calidad, r.prueba_funcional, r.dureza, r.rango_rollos, ft.material, ft.estria_ventana "
                    + "FROM registro r "
                    + "INNER JOIN producto p ON r.id_producto = p.id_producto "
                    + "INNER JOIN ficha_tecnica ft ON ft.id_ficha_tecnica = p.id_ficha_tecnica "
                    + "INNER JOIN linea l ON l.id_linea = r.id_linea "
                    + "WHERE r.lote_producto = '" + lpd + "' AND r.lote_c = '" + ltc + "' "
                    + "ORDER BY r.fecha_turno DESC, r.turno_produccion DESC";
            ResultSet rs = sttm.executeQuery(sql);

            List<Map<String, Object>> lstRegistros = new ArrayList<Map<String, Object>>();
            while (rs.next()) {
                Map<String, Object> registro = new HashMap<String, Object>();
                registro.put("fechaTurno", rs.getObject(1));
                registro.put("turnoProduccion", rs.getObject(2));
                registro.put("responsablesProduccion", rs.getString(3));
                registro.put("loteProducto", rs.getString(4));
                registro.put("loteC", rs.getString(5));
                registro.put("loteP", rs.getString(6));
                registro.put("linea", rs.getString(7));
                registro.put("factorMedida", rs.getObject(8));
                registro.put("turnoCalidad", rs.getString(9));
                registro.put("responsablesCalidad", rs.getString(10));
                registro.put("pruebaFuncional", rs.getString(11));
                registro.put("dureza", rs.getObject(12));
                registro.put("rangoRollos", rs.getString(13));
                registro.put("material", rs.getObject(14));
                registro.put("estriaVentana", rs.getObject(15));
                lstRegistros.add(registro);
            }
            rs.close();
            sttm.close();
            conn.close();
            return lstRegistros;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.InspeccionMangaRegistrosCabecera: SQLException conectando a " + url, ex);
            diagnosticoCabecera = "No se pudo conectar a la base de datos de Inspección Manga (" + url + "): " + ex.getMessage();
            return null;
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.InspeccionMangaRegistrosCabecera: ClassNotFoundException", ex);
            diagnosticoCabecera = "No se encontró el driver de conexión a la base de datos de Inspección Manga.";
            return null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "LinkBatchRecord.InspeccionMangaRegistrosCabecera: Exception", ex);
            diagnosticoCabecera = "Error inesperado al consultar los registros de cabecera de Inspección Manga: " + ex.getMessage();
            return null;
        }
    }

}
