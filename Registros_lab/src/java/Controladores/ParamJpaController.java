package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ParamJpaController {

    public ParamJpaController() {
        emf = Persistence.createEntityManagerFactory("RegistrosLaboratorioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List ConsultarParametrosxCategoria(String categorie) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            List consulta = null;
            Query q = etm.createNativeQuery("CALL `sp_c_consularParametroxCategoria`('" + categorie + "')");
            consulta = q.getResultList();
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
    
    
    
}