package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Historial de firmas (tabla signature_history): un registro por evento
 * REGISTRADO / FINALIZADO / FIRMADO de un Certificado, Documento de soporte o
 * Batch Record. Ver sql/2026-11-03_signature_history.sql y
 * Method.SignatureHistory (que arma los datos del request/sesión).
 */
public class SignatureHistoryJpaController implements Serializable {

    public SignatureHistoryJpaController() {
        emf = Persistence.createEntityManagerFactory("COAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean register(String documentType, String documentKey, Integer idCertificate, String documentName,
            String cliente, String anio, String orden, String lote, String event, String detail,
            Integer idUser, String userDocument, String userName, String userRole,
            String ipAddress, String application, String userAgent) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_sgh_r_RegisterSignatureHistory`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            q.setParameter(1, documentType);
            q.setParameter(2, documentKey);
            q.setParameter(3, idCertificate);
            q.setParameter(4, documentName);
            q.setParameter(5, cliente);
            q.setParameter(6, anio);
            q.setParameter(7, orden);
            q.setParameter(8, lote);
            q.setParameter(9, event);
            q.setParameter(10, detail);
            q.setParameter(11, idUser);
            q.setParameter(12, userDocument);
            q.setParameter(13, userName);
            q.setParameter(14, userRole);
            q.setParameter(15, ipAddress);
            q.setParameter(16, application);
            q.setParameter(17, userAgent);
            q.executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Eventos del documento en orden cronológico. Cada fila: {id, event,
     * detail, user_name, user_role, ip_address, application, user_agent,
     * fecha (dd/MM/yyyy), hora (HH:mm:ss), document_name}.
     */
    public List<Object[]> consult(String documentType, String documentKey) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_sgh_c_ConsultSignatureHistory`(?, ?)");
            q.setParameter(1, documentType);
            q.setParameter(2, documentKey);
            List filas = q.getResultList();
            em.getTransaction().commit();
            List<Object[]> resultado = new ArrayList<Object[]>();
            if (filas != null) {
                for (Object fila : filas) {
                    resultado.add((Object[]) fila);
                }
            }
            return resultado;
        } catch (Exception e) {
            return new ArrayList<Object[]>();
        } finally {
            em.close();
        }
    }
}
