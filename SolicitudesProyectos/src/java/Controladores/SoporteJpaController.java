package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SoporteJpaController implements Serializable {

    public SoporteJpaController() {
        emf = Metodos.JPA.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    //<editor-fold defaultstate="collapsed" desc="lIST">
    public List Consult_server(String txtParame) {
        EntityManager etm = getEntityManager();
        try {
            etm.getTransaction().begin();
            Query q = etm.createNativeQuery("CALL `sp_parametros_c_consultaServers`(?)");
            q.setParameter(1, txtParame);
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

//</editor-fold>
}
