package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ParametrosJpaController implements Serializable {

    public ParametrosJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Consultar_categorias(String categoria) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pmt_c_categoria`('" + categoria + "')");
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
    
    public List Consultar_Basculas_Disponibles() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pmt_c_Consultar_ImpresorasDisponibles`()");
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
