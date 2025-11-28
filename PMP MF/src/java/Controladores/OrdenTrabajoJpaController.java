package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class OrdenTrabajoJpaController {

    public OrdenTrabajoJpaController() {
        emf = Persistence.createEntityManagerFactory("PMP_MFPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Verificar_tipo_ot(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT id_orden_trabajo,id_tipo_mtto FROM orden_trabajo WHERE id_orden_trabajo = " + iot + "");
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

    public List Numero_ot() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_t_numero_orden_trabajo`()");
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

    public List Reporte_OT(int anio) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpt_proxima_ot`('" + anio + "')");
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

    public List Reporte_OT_Filtro(int anio, String fecha, int linea) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "select l.id_linea,l.nombre,e.id_equipo as 'id_origen',e.nombre, "
                    + "	(select GROUP_CONCAT(o.id_orden_trabajo) from orden_trabajo o where year(o.fecha_programacion) = " + anio + " and o.id_origen = e.id_equipo and o.id_tipo_mtto in (1,2,5) and o.estado > 1 order by o.id_orden_trabajo desc  limit 1) as 'id_ot', "
                    + "	(select GROUP_CONCAT(o.numero) from orden_trabajo o where year(o.fecha_programacion) = " + anio + " and o.id_origen = e.id_equipo and o.id_tipo_mtto in (1,2,5) and o.estado > 1 order by o.numero desc limit 1) as 'num_ot', "
                    + "	(select GROUP_CONCAT(o.id_tipo_mtto) from orden_trabajo o inner join tipo_mtto t ON o.id_tipo_mtto = t.id_tipo_mtto where year(o.fecha_programacion) = " + anio + " and o.id_origen = e.id_equipo and o.id_tipo_mtto in (1,2,5) and o.estado > 1 order by o.id_tipo_mtto desc limit 1) as 'tipo_mtto_ot', "
                    + "	ifnull(week(DATE_FORMAT(DATE_ADD((SELECT o.fecha_programacion FROM orden_trabajo o where year(o.fecha_programacion) = " + anio + " and o.id_origen = e.id_equipo and o.id_tipo_mtto in (1) and o.estado > 1 ORDER BY o.fecha_programacion desc limit 1), INTERVAL t.frecuencia DAY), '%Y-%m-%d')),'N/A') as 'Proxima OT M', "
                    + "	IFNULL(week(DATE_FORMAT(DATE_ADD((SELECT o.fecha_programacion FROM orden_trabajo o where year(o.fecha_programacion) = " + anio + " and o.id_origen = e.id_equipo and o.id_tipo_mtto in (2) and o.estado > 1 ORDER BY o.fecha_programacion desc limit 1), INTERVAL t.frecuencia DAY), '%Y-%m-%d')),'N/A') as 'Proxima OT I', "
                    + "	IFNULL(week(DATE_FORMAT(DATE_ADD((SELECT o.fecha_programacion FROM orden_trabajo o where year(o.fecha_programacion) = " + anio + " and o.id_origen = e.id_equipo and o.id_tipo_mtto in (5) and o.estado > 1 ORDER BY o.fecha_programacion desc limit 1), INTERVAL t.frecuencia DAY), '%Y-%m-%d')),'N/A') as 'Proxima OT Z' "
                    + "from linea l inner join equipo e on l.id_linea = e.id_linea "
                    + "	inner join tipo_equipo t on e.id_tipo_equipo = t.id_tipo_equipo "
                    + "	WHERE WEEK(DATE_ADD((SELECT o.fecha_programacion FROM orden_trabajo o where year(o.fecha_programacion) = " + anio + " "
                    + "	and o.id_origen = e.id_equipo and o.id_tipo_mtto in (1,2,3,5) and o.estado > 1 ORDER BY o.fecha_programacion desc limit 1), INTERVAL t.frecuencia DAY)) = WEEK('" + fecha + "') " + ((linea != 0) ? "AND l.id_linea = " + linea + "" : "") + "";
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

    public List Contador_actividad(String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpt_t_contador_actividad`('" + fin + "','" + ffn + "')");
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
    public List Reporte_actividades(String fin, String ffn, int itm, String vlr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpt_t_actividades_ot`('" + fin + "','" + ffn + "','" + itm + "','" + vlr + "')");
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

    public List Reporte_total_actividades(String fin, String ffn, int itm, String vlr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpt_t_total_actividades_ot`('" + fin + "','" + ffn + "','" + itm + "','" + vlr + "')");
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

    public List Reporte_semanal_ot() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpt_t_correo_ot_abiertas`()");
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

    public List Orden_trabajo_equipo_id(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_eqp_t_orden_trabajo_id`('" + iot + "')");
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

    public List Orden_trabajo_equipo_id_equipo(int ieq) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_eqp_t_orden_trabajo_id_equipo`('" + ieq + "')");
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

    public List Orden_trabajo_zona_id(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_zna_t_orden_trabajo_id`('" + iot + "')");
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

    public List Orden_trabajo_zona_id_zona(int izn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_zna_t_orden_trabajo_id_zona`('" + izn + "')");
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

    public List Orden_trabajo_general_id(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_gnr_t_orden_trabajo_id`('" + iot + "')");
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

    public List Orden_trabajo_general_id_general(int ign) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_gnr_t_orden_trabajo_id_general`('" + ign + "')");
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

    public boolean Registrar_orden_trabajo(int nmr, int tmt, int ior, String upg, String uej, String uvr, String uap, int etd, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_r_orden_trabajo`('" + nmr + "','" + tmt + "','" + ior + "','" + upg + "','" + uej + "','" + uvr + "','" + uap + "','" + etd + "','" + urg + "')");
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

    public boolean Modificar_orden_trabajo(int iot, String upg, String uej, String uvr, String uap, String urg, int itm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_orden_trabajo`('" + iot + "','" + upg + "','" + uej + "','" + uvr + "','" + uap + "','" + urg + "','" + itm + "')");
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
    public boolean Modificar_orden_trabajo_zona(int iot, String upg, String uej, String uvr, String uap, String urg, int itm, String fpg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_orden_trabajo_zona`('" + iot + "','" + upg + "','" + uej + "','" + uvr + "','" + uap + "','" + urg + "','" + itm + "','" + fpg + "')");
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

    public boolean Cambiar_estado(int iot, int etd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_cambiar_estado`('" + iot + "','" + etd + "')");
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

    public boolean Ejecutar_ot(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_ejecutar`('" + iot + "')");
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

    public boolean Verificar_ot(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_verificar`('" + iot + "')");
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

    public boolean Aprobar_ot(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_aprobar`('" + iot + "')");
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

    public boolean Recibir_prf_ot(int iot, String urp) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_recibir_prf`('" + iot + "','" + urp + "')");
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

    public boolean Recibir_gc_ot(int iot, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_recibir_gc`('" + iot + "','" + urg + "')");
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

    public boolean Autorizacion(int iot, String fat, String jtf) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_autorizacion`('" + iot + "','" + fat + "','" + jtf + "')");
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

    public boolean Tiempo_estimado_ot(int iot, int tet) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_tiempo_estimado`('" + iot + "','" + tet + "')");
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
