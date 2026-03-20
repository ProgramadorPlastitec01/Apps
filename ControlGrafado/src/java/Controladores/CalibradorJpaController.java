package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CalibradorJpaController implements Serializable {

    public CalibradorJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlGrafadoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaCalibradores() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
//            Query q = em.createNativeQuery("CALL `sp_clbd_c_calibrador`()");
            Query q = em.createNativeQuery("CALL `sp_clbd_c_calibrador_metrologia`()");
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

    public List consultaCalibradoresFiltro(String filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
//            Query q = em.createNativeQuery("CALL `sp_clbd_c_calibrador_filtro`('" + filtro + "')");
            Query q = em.createNativeQuery("CALL `sp_clbd_c_calibrador_filtro_metrologia`('" + filtro + "')");
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
}
