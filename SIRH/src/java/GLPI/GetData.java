
package GLPI;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class GetData {
    public GetData() {
        emf = Persistence.createEntityManagerFactory("SIRHPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List Consultar_Configuracion_glpi(String cat) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_cfg_c_ConsultarConfiguracionCategoria`('" + cat + "')");
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
