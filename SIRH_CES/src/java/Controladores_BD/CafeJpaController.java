package Controladores_BD;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CafeJpaController {

    public CafeJpaController() {
        emf = Persistence.createEntityManagerFactory("SIRH_CESPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultarEntradasSalidas_Cafe(String dcm, String icg, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cafe_consultarEntradasSalidas`('" + dcm + "','" + icg + "','" + anio + "','" + mes + "');");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean Registrar_marcacion_anio_mes_Cafe(String dcm, String icg, String dts, String mtv, String etd, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cafe_registrar`('" + dcm + "', '" + icg + "', '" + dts + "', '" + mtv + "', '" + etd + "', '" + anio + "', '" + mes + "');");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Registrar_UbicacionMarcaicon_ENT_Cafe(String ent, int dcm, int icg, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cafe_regsitrarUbicacionMarcacionEnt`('" + ent + "', '" + dcm + "', '" + icg + "', '" + anio + "', '" + mes + "');");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Registrar_UbicacionMarcaicon_Sal_Cafe(String sal, int dcm, int icg, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cafe_regsitrarUbicacionMarcacionSal`('" + sal + "', '" + dcm + "', '" + icg + "', '" + anio + "', '" + mes + "');");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public String Registrar_marcacion_anio_mes_Cafe(String dcm, String etd, int anio, String mes, String dia, String icg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String consulta = "";
            String resultado = "";
            if (etd.contains(",")) {
                String[] arg_etd = etd.split(",");
                if (arg_etd[1].contains("Start")) {
                    etd = arg_etd[1].replace("Start", "Old");
                } else {
                    etd = arg_etd[0];
                }
            }
            if (etd.contains("Start")) {
                String[] arg_etd = etd.split("_");
                if (arg_etd[3].equals(dia)) {
                    consulta = "UPDATE cafe SET sal_" + Integer.parseInt(arg_etd[3]) + " = now(), estado = 'End_" + anio + "_" + mes + "_" + dia + "' WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + arg_etd[1] + " AND mes = '" + Integer.parseInt(arg_etd[2]) + "' ;";
                } else {
                    consulta = "UPDATE cafe SET sal_" + Integer.parseInt(arg_etd[3]) + " = now(), estado = 'End_" + arg_etd[1] + "_" + arg_etd[2] + "_" + arg_etd[3] + "' WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + arg_etd[1] + " AND mes = '" + Integer.parseInt(arg_etd[2]) + "' ;";
                }
                resultado = "SALIDA";
            } else if (etd.contains("End")) {
                String[] arg_etd = etd.split("_");
                if (arg_etd[3].equals(dia)) {
                    return "COMPLETA";
                } else {
                    consulta = "UPDATE cafe SET ent_" + Integer.parseInt(dia) + " = now(), estado = 'Start_" + anio + "_" + mes + "_" + dia + "' WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + anio + " AND mes = '" + Integer.parseInt(mes) + "' ;";
                    resultado = "ENTRADA";
                }
            } else if (etd.contains("Old")) {
                String[] arg_etd = etd.split("_");
                consulta = "UPDATE cafe SET sal_" + Integer.parseInt(arg_etd[3]) + " = now(), estado = 'End_" + arg_etd[1] + "_" + arg_etd[2] + "_" + arg_etd[3] + "' WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + arg_etd[1] + " AND mes = '" + Integer.parseInt(arg_etd[2]) + "' ;";
                resultado = "SALIDA";
            } else if (etd.contains("Generar")) {
                consulta = "UPDATE cafe SET ent_" + Integer.parseInt(dia) + " = now(), estado = 'Start_" + anio + "_" + mes + "_" + dia + "' WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + anio + "  AND mes = '" + Integer.parseInt(mes) + "' ;";
                resultado = "ENTRADA";
            }
            Query q = etm.createNativeQuery(consulta);
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return "FALLIDO";
            } else {
                return resultado;
            }
        } catch (Exception ex) {
            return "FALLIDO";
        }
    }

    public List Evaluar_tiempo_marcacion_anio_mes_Cafe(String dcm, String etd, String icg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            if (etd.contains(",")) {
                String[] arg_etd = etd.split(",");
                if (arg_etd[1].contains("Start")) {
                    etd = arg_etd[1];
                } else {
                    etd = arg_etd[0];
                }
            }
            String[] estado_marcacion = etd.split("_");
            String query = "select c.ent_" + Integer.parseInt(estado_marcacion[3]) + ", if(now() > DATE_ADD(c.ent_" + Integer.parseInt(estado_marcacion[3]) + ", interval 5 minute), 'SI','NO') AS 'Permitir_marcar',if(DATE_FORMAT(NOW(), '%Y-%m-%d')  = DATE_FORMAT(c.ent_" + Integer.parseInt(estado_marcacion[3]) + ", '%Y-%m-%d'),'CIERRE', 'NO_CIERRE') from cafe c where c.documento = " + dcm + " and c.id_cargo = " + icg + " and c.anio = " + Integer.parseInt(estado_marcacion[1]) + " and c.mes = " + Integer.parseInt(estado_marcacion[2]) + ";";
            Query q = etm.createNativeQuery(query);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Verificacion_existencia_Cafe(String dcm, String icg, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cafe_verificacion_existencia`('" + dcm + "','" + icg + "','" + anio + "','" + mes + "');");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean Registrar_seguimiento_anio_mes(String dcm, String icg, String dts, String mtv, String etd, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cfsg_registrar`('" + dcm + "', '" + icg + "', '" + dts + "', '" + mtv + "', '" + etd + "', '" + anio + "', '" + mes + "');");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public String Calculos_marcacion(String dcm, String icg, int anio, String mes, String dia) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String dia_seguimiento = "seg_" + Integer.parseInt(dia);
            String dia_inicio = "ent_" + Integer.parseInt(dia);
            String dia_fin = "sal_" + Integer.parseInt(dia);
            String sentencia = "UPDATE cafe_seguimiento SET seg_19 = "
                    + "(SELECT concat('Dia_entrada:',(ELT(WEEKDAY(c.ent_19) + 1, 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado', 'Domingo')),'/Entrada:',c.ent_19,\n"
                    + "	'/Dia_salida:',(ELT(WEEKDAY(c.sal_19) + 1, 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado', 'Domingo')),'/Salida:', c.sal_19,\n"
                    + "	'/Turno:',IF(c.ent_19 >= concat(date(c.ent_19),' 20:00:00'),'Turno 3', IF(c.ent_19 >= concat(date(c.ent_19),' 12:00:00'),'Turno 2','Turno 1')),\n"
                    + "	'/Minutos_trabajo:',ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)),2))\n"
                    + "FROM cafe c WHERE c.documento = " + dcm + " AND c.id_cargo = " + icg + " AND c.anio = " + anio + " AND c.mes = '" + Integer.parseInt(mes) + "')"
                    + " WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + anio + " AND mes = '" + Integer.parseInt(mes) + "';";
            sentencia = sentencia.replace("seg_19", dia_seguimiento);
            sentencia = sentencia.replace("ent_19", dia_inicio);
            sentencia = sentencia.replace("sal_19", dia_fin);
            Query q = etm.createNativeQuery(sentencia);
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return "FALLO";
            } else {
                return "EXITO";
            }
        } catch (Exception ex) {
            return "FALLO";
        }
    }

}
