package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TiempoDescontableJpaController implements Serializable {

    public TiempoDescontableJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultarTiempoDescontable() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rcp_c_consultarTiempoDescontable`()");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    public List ConsultarTiempoDescontable_id(int id_tde) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tde_c_consultarTiempo_id`('"+ id_tde +"')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    public List Consultar_tiempoDescontable_id(int id_tde) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tde_consultar_TiempoDescontable_id`('"+ id_tde +"')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean registrarTiempoDescontable(String Txt_tiempo, String txt_descontable,String desc, int est, String usu_reg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tde_r_registrarTiempoDes`('" + Txt_tiempo + "','" + txt_descontable + "','" + desc + "','" + est + "','" + usu_reg + "')");
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
    
    public boolean ModificarTiempoDescontable(int id_tde, String Txt_tiempo, String txt_descontable,String desc) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tde_m_modificarTiempoDescontable`('" + id_tde + "','" + Txt_tiempo + "','" + txt_descontable + "','" + desc + "')");
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
    
    
    public boolean ModificarTiempoDescontable_estado(int id_tde, int est) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tde_m_modificarEstadoTiempo`('" + id_tde + "','" + est + "')");
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
