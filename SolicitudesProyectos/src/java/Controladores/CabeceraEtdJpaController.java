package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CabeceraEtdJpaController implements Serializable {

    public CabeceraEtdJpaController() {
        emf = Persistence.createEntityManagerFactory("SolicitudesProyectosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultarUltimaCabecera(String solicitud) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_c_ultima`('" + solicitud + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarVerificaciones(int val, int anio) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_c_verificaciones`('" + val + "','" + anio + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public List consultarVerificacionesAnio() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_c_verificaciones_anio`()");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarVerificacionesFiltro(String filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_c_verificaciones_filtro`('" + filtro + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarVerificacionId(int id_verificacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_c_verificacion_id`('" + id_verificacion + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarCalificacionVerificacionId(int id_verificacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cfc_c_calificacion_verificacion_id`('" + id_verificacion + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarVerificacionPieza(String solicitud, String pieza) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_c_verificacion_pieza`('" + solicitud + "','" + pieza + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarItemsVerificacionIdPlano(int id_plano) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_clc_c_verificacion_plano_id`('" + id_plano + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean registroVerificacionPieza(String fecha, String solicitud, String verificado, int id_pieza) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_r_verificacion`('" + fecha + "','" + solicitud + "','" + verificado + "','" + id_pieza + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registroRelacion(int id_verificacion, int id_itemV) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rlc_r_relacion`('" + id_verificacion + "','" + id_itemV + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List consultaRelacionVerificacionId(int id_verificacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rlc_c_relacion_verificacion_id`('" + id_verificacion + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean registroCalificacion(int relacion, String cumple, String aplica, String observa) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cfc_r_calificacion`('" + relacion + "','" + cumple + "','" + aplica + "','" + observa + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
