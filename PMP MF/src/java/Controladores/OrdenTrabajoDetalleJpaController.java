package Controladores;

import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OrdenTrabajoDetalleJpaController {

    public OrdenTrabajoDetalleJpaController() {
        emf = Persistence.createEntityManagerFactory("PMP_MFPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Consulta_detalle_orden_trabajo(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otd_t_detalle_orden_trabajo`('" + iot + "')");
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

    public boolean Registrar_detalle_orden_trabajo(int iot, String gat, String iat, String atv, int cpg, int cej, String pde, String dcc, String rpe, String npm, String lrp, String iap, String ilz) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otd_r_detale_orden_trabajo`('" + iot + "','" + gat + "','" + iat + "','" + atv + "','" + cpg + "','" + cej + "','" + pde + "','" + dcc + "','" + rpe + "','" + npm + "','" + lrp + "','" + iap + "','" + ilz + "')");
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

    public List Traer_pendientes(int iot, int itm, int ior) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otd_t_pendientes`('" + iot + "','" + itm + "','" + ior + "')");
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

    public boolean Actualizar_detalle_orden_trabajo(int iotd, String gat, String iat, String atv, int cpg, int cej, String pde, String dcc, String rpe, String npm, String lrp, String iap, String ilz) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otd_m_detale_orden_trabajo`('" + iotd + "','" + gat + "','" + iat + "','" + atv + "','" + cpg + "','" + cej + "','" + pde + "','" + dcc + "','" + rpe + "','" + npm + "','" + lrp + "','" + iap + "','" + ilz + "')");
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

    public boolean Actualizar_detalle_orden_trabajo_Autoclave(int iotd, String gat, String iat, String atv, int cpg, int cej, String pde, String dcc, String rpe, String npm, String lrp, String iap, String ilz, int vrf) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otd_m_detalle_orden_trabajo_autoclave`('" + iotd + "','" + gat + "','" + iat + "','" + atv + "','" + cpg + "','" + cej + "','" + pde + "','" + dcc + "','" + rpe + "','" + npm + "','" + lrp + "','" + iap + "','" + ilz + "','" + vrf + "')");
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

    public boolean Actualizar_firmas_fecha(int id, String nombre_user) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {

            String query = "UPDATE orden_trabajo SET " + ((id == 8) ? nombre_user : (id == 54) ? nombre_user : "") + "";
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

    public List Consulta_actividades_grupo_ids(String gat, String iat) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT p.id_parametro,p.grupo,p.actividad,p.orden,p.estado FROM parametro p WHERE p.grupo = '" + gat + "' and p.id_parametro in (" + iat + ") ORDER BY p.orden ASC");
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

    public List Consulta_actividades_grupo_ids_zona(String gat, String iat) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT p.id_parametro_zona,p.grupo,p.actividad,p.orden,p.estado FROM parametro_zona p WHERE p.grupo = '" + gat + "' and p.id_parametro_zona in (" + iat + ") ORDER BY p.orden ASC");
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

    public List Consulta_actividades_grupo_ids_general(String gat, String iat) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT p.id_parametro_general,p.grupo,p.actividad,p.orden,p.estado FROM parametro_general p WHERE p.grupo = '" + gat + "' and p.id_parametro_general in (" + iat + ") ORDER BY p.orden ASC");
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

    public boolean RegistrarDevolucion(int iotd, String jtf, int eat, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ldv_r_registrar_devolucion`('" + iotd + "','" + jtf + "','" + eat + "','" + urg + "')");
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
