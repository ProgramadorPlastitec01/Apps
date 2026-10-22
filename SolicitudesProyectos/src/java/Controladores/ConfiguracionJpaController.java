package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class ConfiguracionJpaController {
    
    public ConfiguracionJpaController() {
        emf = Metodos.JPA.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List ConsultarConfiguracionCategoria(String Caterogia) {
        EntityManager etm = getEntityManager();
        try {
            etm.getTransaction().begin();
            Query q = etm.createNativeQuery("CALL `Sp_cfg_c_ConsultarConfiguracionCategoria`(?)");
            q.setParameter(1, Caterogia);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (etm.isOpen()) {
                if (etm.getTransaction().isActive()) {
                    etm.getTransaction().rollback();
                }
                etm.close();
            }
        }
    }
    
    
}
