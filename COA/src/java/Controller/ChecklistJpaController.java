package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Lista de verificación de documentos del lote R-GC-094 (requerimiento
 * 1.1.U04). Tablas checklist_section / checklist_section_item (plantilla
 * parametrizable) y checklist_lote_item (lo que el usuario marcó a mano en
 * un lote). Ver sql/2026-11-17_checklist_rgc094_batch_record.sql y
 * Method.BatchRecordChecklist, que arma la lista con lo que el sistema ya
 * encuentra.
 */
public class ChecklistJpaController implements Serializable {

    public ChecklistJpaController() {
        emf = Persistence.createEntityManagerFactory("COAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Ítems de todas las secciones activas, ordenados por sección y
     * consecutivo. Cada fila: {s.id, s.record_code, s.version, s.name,
     * s.certificate_formats, s.trigger_codes, s.sort_order, i.id,
     * i.consecutive, i.record_name, i.record_code, i.source, i.source_code,
     * verification_note, i.required}.
     */
    public List<Object[]> consultActiveItems() {
        return consultRows("CALL `Sp_ckl_c_ConsultActiveItems`()", new Object[0]);
    }

    /**
     * Marcas manuales del lote. Cada fila: {item_key, id_item, status,
     * id_file, observation, user_name, fecha (dd/MM/yyyy HH:mm)}.
     */
    public List<Object[]> consultLoteMarks(String cliente, String anio, String orden, String lote) {
        return consultRows("CALL `Sp_ckl_c_ConsultLoteMarks`(?, ?, ?, ?)", new Object[]{cliente, anio, orden, lote});
    }

    public boolean saveLoteMark(String cliente, String anio, String orden, String lote, Integer idItem, String itemKey,
            String status, Long idFile, String observation, String userDocument, String userName) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_ckl_r_SaveLoteMark`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            q.setParameter(1, cliente);
            q.setParameter(2, anio);
            q.setParameter(3, orden);
            q.setParameter(4, lote);
            q.setParameter(5, idItem);
            q.setParameter(6, itemKey);
            q.setParameter(7, status);
            q.setParameter(8, idFile);
            q.setParameter(9, observation);
            q.setParameter(10, userDocument);
            q.setParameter(11, userName);
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

    public boolean deleteLoteMark(String cliente, String anio, String orden, String lote, String itemKey) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_ckl_d_DeleteLoteMark`(?, ?, ?, ?, ?)");
            q.setParameter(1, cliente);
            q.setParameter(2, anio);
            q.setParameter(3, orden);
            q.setParameter(4, lote);
            q.setParameter(5, itemKey);
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

    private List<Object[]> consultRows(String sql, Object[] params) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery(sql);
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i + 1, params[i]);
            }
            List filas = q.getResultList();
            em.getTransaction().commit();
            List<Object[]> resultado = new ArrayList<Object[]>();
            if (filas != null) {
                for (Object fila : filas) {
                    resultado.add(fila instanceof Object[] ? (Object[]) fila : new Object[]{fila});
                }
            }
            return resultado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return new ArrayList<Object[]>();
        } finally {
            em.close();
        }
    }
}
