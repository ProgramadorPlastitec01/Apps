package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MaquinaJpaController implements Serializable {

    public MaquinaJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultarMaquinas() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mq_c_consultarMaquina`()");
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
    
    public List ConsultarMaquinasActivas() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_c_ConsultarMaquinaActiva`()");
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

    public List ConsultarMaquinasXProd(String codigo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mq_c_consultarMaquinaXProd`('" + codigo + "')");
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

    public List ConsultarMaquinas_id(int id_maq) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_maq_c_consultarMaquina_id`('" + id_maq + "')");
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

    public boolean RegistrarMaquina(String nom_maquina, String cod_prod, String prod, String mold, String tar, int est, String usureg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_maq_r_registrarMaquina`('" + nom_maquina + "','" + cod_prod + "','" + prod + "','" + mold + "','" + tar + "','" + est + "','" + usureg + "')");
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

    public boolean EditarMaquina(int id_maq, String nom_maquina, String cod_prod, String prod, String mold, String tar) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_maq_a_actualizaMaquina`('" + id_maq + "','" + nom_maquina + "','" + cod_prod + "','" + prod + "','" + mold + "','" + tar + "')");
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

    public boolean CambiarEstadoMaquina(int id_maq, int est) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_maq_c_cambiarEstadoMaquina`('" + id_maq + "','" + est + "')");
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
