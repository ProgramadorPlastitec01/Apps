package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TipoMateriaPrimaJpaController {

    public TipoMateriaPrimaJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlFormulasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Tipo_materia_prima() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_tmp_c_tipos_materia_prima`()");
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



}
