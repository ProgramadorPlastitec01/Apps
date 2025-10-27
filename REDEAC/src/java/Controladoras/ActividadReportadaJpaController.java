package Controladoras;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ActividadReportadaJpaController implements Serializable {

    public ActividadReportadaJpaController() {
        emf = Persistence.createEntityManagerFactory("REDEACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultarActividadesReportante() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_arp_t_actividades_usuario`()");
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

    public boolean registrarActividadR(String reporte, int id_equipo, int id_lista_equipo, int id_tipoS, int id_aplicativo, String fecha_inicio, String fecha_ejecucion, String fecha_fin, String actividad, String solucion, int id_usuario, int paradaE, int paradaP,  int codigo, int id_area) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_arp_r_actividad`('" + reporte + "', '" + id_equipo + "','" + id_lista_equipo + "', '" + id_tipoS + "', '" + id_aplicativo + "', '" + fecha_inicio + "', '" + fecha_ejecucion + "', '" + fecha_fin + "', '" + actividad + "', '" + solucion + "', '" + id_usuario + "', '" + paradaE + "','" + paradaP + "','" + codigo + "','" + id_area + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List consultaActividadReportante(int id_actividad) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_arp_t_actividad_id_actividad`('" + id_actividad + "')");
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

    public boolean modificarActividad(int id_actividad, String reporte, int id_equipo, int id_l_equipo, int id_tipoS, int id_aplicativo, String fecha_inicio, String fecha_ejecucion, String fecha_fin, String actividad, String solucion, int paradaE, int paradaP, int id_area) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_arp_m_actividad`('" + id_actividad + "','" + reporte + "','" + id_equipo + "','" + id_l_equipo + "','" + id_tipoS + "','" + id_aplicativo + "','" + fecha_inicio + "','" + fecha_ejecucion + "','" + fecha_fin + "','" + actividad + "','" + solucion + "','" + paradaE + "','" + paradaP + "','" + id_area + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List consultaFirmaUsuario(int documento, int codigo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL sirh.sp_signature_documento_codigo('" + documento + "','" + codigo + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
    public List consultaFirmaUsuarioCodigo( int codigo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL sp_sirh_signature_codigo('" + codigo + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaRegistro001IdArea(int id_usuario, int id_area, String fecha_inicio, String fecha_fin) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_arp_c_registro_001_area`('" + id_usuario + "','" + id_area + "','" + fecha_inicio + "','" + fecha_fin + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaRegistro001(int id_usuario, String fecha_inicial, String fecha_fin) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_arp_c_registro_001`('" + id_usuario + "','" + fecha_inicial + "','" + fecha_fin + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaRegistro001All(String fecha_inicial, String fecha_fin) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_arp_c_registro_001_all`('" + fecha_inicial + "','" + fecha_fin + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaRegistro001AreaAll(int idArea, String fecha_inicial, String fecha_fin) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_arp_c_registro_001_area_all`('" + idArea + "','" + fecha_inicial + "','" + fecha_fin + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

}
