package Controladoras;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Persistence;

public class AplicativoJpaController implements Serializable {

    public AplicativoJpaController() {
        emf = Persistence.createEntityManagerFactory("REDEACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultarAplicativos() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_apc_c_aplicativos`()");
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
        }
    }

    public List consultarAplicativosProtocolo() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_apc_c_aplicativo_protocolo`()");
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
        }
    }

    public List consultarAplicativosProtocoloId(int id_aplicativo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_apc_c_aplicativo_protocolo_id`('" + id_aplicativo + "')");
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
        }
    }

    public List consultarAplicativosNOTIN(String aplicativo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "SELECT a.id_aplicativo,a.nombre,a.encargado,a.estado,a.aplican_pt,a.protocolo  "
                    + "FROM aplicativo a  "
                    + "WHERE a.estado = 1 AND a.id_aplicativo <> 1 AND a.aplican_pt = 1 AND a.id_aplicativo NOT IN (" + aplicativo + ")";
            Query q = etm.createNativeQuery(query);
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
        }
    }
}
