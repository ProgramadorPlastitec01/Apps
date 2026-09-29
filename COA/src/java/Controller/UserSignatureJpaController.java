package Controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Referencias de las imágenes de firma de usuario que viven en Office
 * Platform (carpeta "COA/Firmas"). Traduce el nombre de archivo guardado en
 * user.signature / certificates.authorization_signature al office_file_id
 * del gestor. Ver sql/2026-09-23_user_signatures.sql.
 */
public class UserSignatureJpaController implements Serializable {

    public UserSignatureJpaController() {
        emf = Persistence.createEntityManagerFactory("COAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean registerSignature(int idUser, String fileName, long officeFileId, String officeUuid,
            String mimeType, Long sizeBytes) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_usg_r_RegisterUserSignature`(?, ?, ?, ?, ?, ?)");
            q.setParameter(1, idUser);
            q.setParameter(2, fileName);
            q.setParameter(3, officeFileId);
            q.setParameter(4, officeUuid);
            q.setParameter(5, mimeType);
            q.setParameter(6, sizeBytes);
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

    /** Devuelve {officeFileId, mimeType} o null si el nombre no está registrado en el gestor. */
    public Object[] consultByFileName(String fileName) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_usg_c_ConsultUserSignatureByName`(?)");
            q.setParameter(1, fileName);
            List filas = q.getResultList();
            em.getTransaction().commit();
            if (filas == null || filas.isEmpty()) {
                return null;
            }
            Object[] fila = (Object[]) filas.get(0);
            long officeFileId = fila[0] instanceof Number ? ((Number) fila[0]).longValue() : 0L;
            String mimeType = fila[1] != null ? String.valueOf(fila[1]) : null;
            return new Object[]{officeFileId, mimeType};
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}
