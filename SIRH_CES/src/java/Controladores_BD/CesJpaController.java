package Controladores_BD;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CesJpaController implements Serializable {

    public CesJpaController() {
        emf = Persistence.createEntityManagerFactory("SIRH_CESPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Login_temp(String usa, String pas) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_t_login`('" + usa + "','" + pas + "');");
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

    public List Datos_empleados(String cfm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ces_datos_empleado`('" + cfm + "');");
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

    public List Verificacion_existencia(String dcm, String icg, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ces_verificacion_existencia`('" + dcm + "','" + icg + "','" + anio + "','" + mes + "');");
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
    public List ConsultarEntradasSaldias(String dcm, String icg, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_consultarEntradasSalidas`('" + dcm + "','" + icg + "','" + anio + "','" + mes + "');");
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

    public boolean Registrar_marcacion_anio_mes(String dcm, String icg, String dts, String mtv, String etd, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ces_registrar`('" + dcm + "', '" + icg + "', '" + dts + "', '" + mtv + "', '" + etd + "', '" + anio + "', '" + mes + "');");
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

    public boolean Registrar_error_ces(String cbr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("INSERT INTO ces_error (codebar_scan) VALUES ('" + cbr + "');");
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

    public boolean Registrar_UbicacionMarcaicon_ENT(String ent, int dcm, int icg, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ces_regsitrarUbicacionMarcacionEnt`('" + ent + "', '" + dcm + "', '" + icg + "', '" + anio + "', '" + mes + "');");
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
    public boolean Registrar_UbicacionMarcaicon_Sal(String sal, int dcm, int icg, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ces_regsitrarUbicacionMarcacionSal`('" + sal + "', '" + dcm + "', '" + icg + "', '" + anio + "', '" + mes + "');");
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
    
    public String Registrar_marcacion_anio_mes(String dcm, String etd, int anio, String mes, String dia, String icg) {
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
                    consulta = "UPDATE ces SET sal_" + Integer.parseInt(arg_etd[3]) + " = now(), estado = 'End_" + anio + "_" + mes + "_" + dia + "' WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + arg_etd[1] + " AND mes = '" + Integer.parseInt(arg_etd[2]) + "' ;";
                } else {
                    consulta = "UPDATE ces SET sal_" + Integer.parseInt(arg_etd[3]) + " = now(), estado = 'End_" + arg_etd[1] + "_" + arg_etd[2] + "_" + arg_etd[3] + "' WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + arg_etd[1] + " AND mes = '" + Integer.parseInt(arg_etd[2]) + "' ;";
                }
                resultado = "SALIDA";
            } else if (etd.contains("End")) {
                String[] arg_etd = etd.split("_");
                if (arg_etd[3].equals(dia)) {
                    return "COMPLETA";
                } else {
                    consulta = "UPDATE ces SET ent_" + Integer.parseInt(dia) + " = now(), estado = 'Start_" + anio + "_" + mes + "_" + dia + "' WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + anio + " AND mes = '" + Integer.parseInt(mes) + "' ;";
                    resultado = "ENTRADA";
                }
            } else if (etd.contains("Old")) {
                String[] arg_etd = etd.split("_");
                consulta = "UPDATE ces SET sal_" + Integer.parseInt(arg_etd[3]) + " = now(), estado = 'End_" + arg_etd[1] + "_" + arg_etd[2] + "_" + arg_etd[3] + "' WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + arg_etd[1] + " AND mes = '" + Integer.parseInt(arg_etd[2]) + "' ;";
                resultado = "SALIDA";
            } else if (etd.contains("Generar")) {
                consulta = "UPDATE ces SET ent_" + Integer.parseInt(dia) + " = now(), estado = 'Start_" + anio + "_" + mes + "_" + dia + "' WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + anio + "  AND mes = '" + Integer.parseInt(mes) + "' ;";
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

    public List Evaluar_tiempo_marcacion_anio_mes(String dcm, String etd, String icg) {
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
            String query = "select c.ent_" + Integer.parseInt(estado_marcacion[3]) + ", if(now() > DATE_ADD(c.ent_" + Integer.parseInt(estado_marcacion[3]) + ", interval 10 minute), 'SI','NO') AS 'Permitir_marcar',if(now() > DATE_ADD(c.ent_" + Integer.parseInt(estado_marcacion[3]) + ", interval 13 hour),'CIERRE','NO_CERRAR') AS 'Cierre_marcacion' from ces c where c.documento = " + dcm + " and c.id_cargo = " + icg + " and c.anio = " + Integer.parseInt(estado_marcacion[1]) + " and c.mes = " + Integer.parseInt(estado_marcacion[2]) + ";";
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
    public boolean Cierre_automatico_marcacion_anio_mes(String dcm, String etd, String icg) {
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
            String query = "update ces set estado='End_" + estado_marcacion[1] + "_" +estado_marcacion[2]+ "_" + estado_marcacion[3] + "',sal_" + Integer.parseInt(estado_marcacion[3]) + " = DATE_ADD(ent_" + Integer.parseInt(estado_marcacion[3]) + ", interval 8 hour) where documento = " + dcm + " and id_cargo = " + icg + " and anio = " + Integer.parseInt(estado_marcacion[1]) + " and mes = " + Integer.parseInt(estado_marcacion[2]) + ";";
            Query q = etm.createNativeQuery(query);
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
}