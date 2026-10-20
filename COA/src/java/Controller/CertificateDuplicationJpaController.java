package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Trazabilidad del botón "Duplicar" de un certificado: quién lo hizo, a
 * partir de cuál certificado original y en qué momento (certificate_duplications).
 */
public class CertificateDuplicationJpaController implements Serializable {

    public CertificateDuplicationJpaController() {
        emf = Persistence.createEntityManagerFactory("COAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean registerDuplication(int idCertificateOrigin, int idCertificateNew, String userId, String userName) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_cdp_r_RegisterDuplication`(?, ?, ?, ?)");
            q.setParameter(1, idCertificateOrigin);
            q.setParameter(2, idCertificateNew);
            q.setParameter(3, userId);
            q.setParameter(4, userName);
            q.executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            em.close();
        }
    }

    /** Historial de duplicaciones donde el certificado participó, como original o como copia. */
    public List<Object[]> consultByCertificate(int idCertificate) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_cdp_c_ConsultByCertificate`(?)");
            q.setParameter(1, idCertificate);
            List filas = q.getResultList();
            em.getTransaction().commit();
            return filas == null ? new ArrayList<Object[]>() : filas;
        } catch (Exception e) {
            return new ArrayList<Object[]>();
        } finally {
            em.close();
        }
    }
}
