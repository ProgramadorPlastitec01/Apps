package Connection;

import Controller.SettingJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LinkBatchRecord {

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
            return null;
        }
        List lst_link = SettingJpa.ConsultSettingCategorie("BatchRecordLAB");
        String Qry = "";
        if (lst_link != null) {
            Object[] obj_link = (Object[]) lst_link.get(0);
            Qry = obj_link[2].toString();
        } else {
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
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
        //</editor-fold>
    }

    public List AttachmentBatchRecord(String DataBatch) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="LinkBatchRecord">
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
        List lst_link = SettingJpa.ConsultSettingCategorie("BatchRecordGeneracionLotes");
        String Qry = "";
        if (lst_link != null) {
            Object[] obj_link = (Object[]) lst_link.get(0);
            Qry = obj_link[2].toString();
        } else {
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
                    String Batch = "";
                    Batch = "(";
                    for (int i = 0; i < ArgBatch.length; i++) {
                        if (i == ArgBatch.length - 1) {
                            Batch += "'" + ArgBatch[i] + "'";
                        } else {
                            Batch += "'" + ArgBatch[i] + "',";
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
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (Exception ex) {
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
            return null;
        }
        List lst_link = SettingJpa.ConsultSettingCategorie("BatchRecordResumen");
        String Qry = "";
        if (lst_link != null) {
            Object[] obj_link = (Object[]) lst_link.get(0);
            Qry = obj_link[2].toString();
        } else {
            Qry = "";
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null && !Qry.equals("")) {
                Qry = Qry.replace("XOrderX", Order);
                Qry = Qry.replace("XBatchX",  DataBatch);
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(Qry);
                List<String> lst_batch = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_batch.add(count,  rs.getString("Tipo").trim() + " /// " + rs.getString("Txt_orden").trim() + " /// " + rs.getString("Cbx_producto").trim() + " /// " + rs.getString("Cbx_lote").trim() + " /// "
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
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
        //</editor-fold>
    }

}
