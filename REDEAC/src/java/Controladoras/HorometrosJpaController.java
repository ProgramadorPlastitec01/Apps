package Controladoras;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Persistence;

public class HorometrosJpaController implements Serializable {

    public HorometrosJpaController() {
        emf = Persistence.createEntityManagerFactory("REDEACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaHorormetroidEquipo(int id_equipo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("select * from horometros h where h.Id_Equipo = " + id_equipo + "");
            List Resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (Resultado.isEmpty()) {
                return null;
            } else {
                return Resultado;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
