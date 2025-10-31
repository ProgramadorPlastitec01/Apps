package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DefectoJpaController implements Serializable {

    public DefectoJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultarDefectos() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dft_c_consultar_defectos`()");
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

    public boolean RegistarDefecto(String dft, int etd) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dft_r_registrar_defecto`('" + dft + "','" + etd + "')");
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

    public boolean ModificarDefecto(int idf, String dft, int etd) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dft_m_modificar_defecto`('" + idf + "','" + dft + "','" + etd + "')");
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

    public boolean Cambiar_estado_defecto(int idf, int etd) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dft_m_estado_defecto`('" + idf + "','" + etd + "')");
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

    public List ConsultarDefectoId(int idf) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dft_c_consultar_defecto_id`('" + idf + "')");
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

    public List ConsultarDefectoActivos() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dft_c_consultar_dectos_activos`()");
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

    public List ConsultarDefectoAgrupados(int id_registro, String Defecto) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cua_c_consulta_cuarentena_agrupadas_id`('" + id_registro + "','" + Defecto + "')");
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
}
