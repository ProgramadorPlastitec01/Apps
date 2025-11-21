package Metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Controladores.ParamJpaController;

public class Connection_metrologia {

    ParamJpaController ParametroJpa = new ParamJpaController();
    static String login = "";
    static String password = "";
    static String url = "";

    public List Seriales_metrologia() throws Exception {
        List lst_Parametro = ParametroJpa.ConsultarParametrosxCategoria("SeverMetrologia");
        if (lst_Parametro != null) {
            Object[] obj_data = (Object[]) lst_Parametro.get(0);
            String[] arr_data =  obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            login = arr_data[0];
            password = arr_data[1];
            url = "jdbc:mysql://"+ arr_data[2];;
        } else {
            return null;
        }
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                String query = "select i.id_instrumento_medicion,"
                        + " ti.tipo,"
                        + " i.instrumento,"
                        + " i.numero_serial,"
                        + "DATE_FORMAT(i.fch_ultima_verificacion_int, '%Y-%m-%d') as fch_ultima_verificacion_int, "
                        + "DATE_FORMAT(DATE_ADD(i.fch_ultima_verificacion_int,INTERVAL ti.frecuencia_interna day), '%Y-%m-%d') as fch_proxima_verificacion_int, "
                        + "DATE_FORMAT(DATE_ADD(i.fch_ultima_verificacion_int,INTERVAL ti.frecuencia_interna day), '%Y-%m-%d') as fch_tolerancia_int, "
                        + "DATE_FORMAT(i.fch_ultima_verificacion_ext,'%Y-%m-%d') as fch_ultima_verificacion_ext, "
                        + "DATE_FORMAT(DATE_ADD(i.fch_ultima_verificacion_ext,INTERVAL ti.frecuencia_externa day), '%Y-%m-%d') as fch_proxima_verificacion_ext, "
                        + "DATE_FORMAT(DATE_ADD(i.fch_ultima_verificacion_ext,INTERVAL ti.frecuencia_externa day), '%Y-%m-%d') as fch_tolerancia_ext, "
                        + "i.estado, "
                        + "if(if(ti.frecuencia_interna > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_int,INTERVAL ti.frecuencia_interna day))-ti.tolerancia_interna),null) > 0 or  if(ti.frecuencia_externa > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_ext,INTERVAL ti.frecuencia_externa day))-ti.tolerancia_externa),null) > 0,'0', "
                        + "if(if(ti.frecuencia_interna > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_int,INTERVAL ti.frecuencia_interna day))-ti.tolerancia_interna),null) >= 5 or  if(ti.frecuencia_externa > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_ext,INTERVAL ti.frecuencia_externa day))-ti.tolerancia_externa),null) >= 0,'1','2')) as semaforo, "
                        + "if(if(ti.frecuencia_interna > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_int,INTERVAL ti.frecuencia_interna day))-ti.tolerancia_interna),null) > 0 and if(ti.frecuencia_externa > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_ext,INTERVAL ti.frecuencia_externa day))-ti.tolerancia_externa),null) > 0,'Todos',  "
                        + "if(if(ti.frecuencia_interna > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_int,INTERVAL ti.frecuencia_interna day))-ti.tolerancia_interna),null) > 0,'Interna',if(if(ti.frecuencia_externa > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_ext,INTERVAL ti.frecuencia_externa day))-ti.tolerancia_externa),null) > 0,'Externa', "
                        + "if(if(ti.frecuencia_interna > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_int,INTERVAL ti.frecuencia_interna day))-ti.tolerancia_interna),null) >= 5 and if(ti.frecuencia_externa > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_ext,INTERVAL ti.frecuencia_externa day))-ti.tolerancia_externa),null) >= 5,'Todos', "
                        + "if(if(ti.frecuencia_interna > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_int,INTERVAL ti.frecuencia_interna day))-ti.tolerancia_interna),null) >= 5,'Interna',if(if(ti.frecuencia_externa > 0, (DATEDIFF(NOW(),DATE_ADD(i.fch_ultima_verificacion_ext,INTERVAL ti.frecuencia_externa day))-ti.tolerancia_externa),null) >= 5,'Externa','N/A')))))) as tipoAlerta, "
                        + "if(ti.tipo_frecuencia > 0,'Verificación-calibración','Inspección-Verificación') as tipo_verificacion, "
                        + "if(DATE_FORMAT(i.fch_ultima_verificacion_int, '%Y-%m-%d') = DATE_FORMAT(DATE_ADD(i.fch_ultima_verificacion_int,INTERVAL ti.frecuencia_interna day), '%Y-%m-%d'),'N-A',concat(DATE_FORMAT(i.fch_ultima_verificacion_int, '%Y-%m-%d') ,'|', DATE_FORMAT(DATE_ADD(i.fch_ultima_verificacion_int,INTERVAL ti.frecuencia_interna day), '%Y-%m-%d'))) AS aprupa_int, "
                        + "if(DATE_FORMAT(i.fch_ultima_verificacion_ext, '%Y-%m-%d') = DATE_FORMAT(DATE_ADD(i.fch_ultima_verificacion_ext,INTERVAL ti.frecuencia_externa day), '%Y-%m-%d'),'N-A',concat(DATE_FORMAT(i.fch_ultima_verificacion_ext, '%Y-%m-%d') ,'|', DATE_FORMAT(DATE_ADD(i.fch_ultima_verificacion_ext,INTERVAL ti.frecuencia_externa day), '%Y-%m-%d'))) AS aprupa_ext "
                        + "from metrologia.instrumento_medicion i inner join metrologia.tipo_instrumento ti "
                        + "on i.id_tipo_instrumento = ti.id_tipo_instrumento inner join metrologia.area a "
                        + "on ti.id_area = a.id_area inner join metrologia.tipo t "
                        + "on i.id_tipo = t.id_tipo  inner join metrologia.plantilla p "
                        + "on i.id_plantilla_verificacion = p.id_plantilla "
                        + "where (ti.id_tipo_instrumento = 4 or ti.id_tipo_instrumento = 5 or ti.id_tipo_instrumento = 6 or ti.id_tipo_instrumento = 30 or ti.id_tipo_instrumento = 31) "
                        + "order by ti.tipo , i.numero_serial asc";
                Statement sttm = conn.createStatement();
                ResultSet rs = sttm.executeQuery(query);
                List<String> lst_seriales = new ArrayList<String>();
                int count = 0;
                while (rs.next()) {
                    lst_seriales.add(count, rs.getString("id_instrumento_medicion").toString().trim()
                            + "---" + rs.getString("tipo").toString().trim()
                            + "---" + rs.getString("instrumento").toString().trim()
                            + "---" + rs.getString("numero_serial").toString().trim()
                            + "---" + rs.getString("fch_ultima_verificacion_int").toString().trim()
                            + "---" + rs.getString("fch_proxima_verificacion_int").trim()
                            + "---" + rs.getString("fch_tolerancia_int").toString().trim()
                            + "---" + rs.getString("fch_ultima_verificacion_ext").toString().trim()
                            + "---" + rs.getString("fch_proxima_verificacion_ext").toString().trim()
                            + "---" + rs.getString("fch_tolerancia_ext").toString().trim()
                            + "---" + rs.getString("estado").toString().trim()
                            + "---" + rs.getString("semaforo").toString().trim()
                            + "---" + rs.getString("tipoAlerta").toString().trim()
                            + "---" + rs.getString("tipo_verificacion").toString().trim()
                            + "---" + rs.getString("aprupa_int").toString().trim()
                            + "---" + rs.getString("aprupa_ext").toString().trim() + "////");
                    count++;
                }
                conn.close();
                return lst_seriales;
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
