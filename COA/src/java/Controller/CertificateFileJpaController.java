package Controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Metadatos de los archivos de Batch Record que hoy vive en el filesystem
 * local (web/Certificates/...). El binario ya no se guarda ahí: vive en
 * Office Platform (Method.OfficePlatformService); esta tabla es la única
 * fuente de verdad para "qué archivos hay en este lote", ya que el listado
 * de la API externa no es confiable (ver Sp_cff_* / migración SQL).
 *
 * Sigue el mismo molde que Controller.SettingJpaController /
 * Controller.CertificatesJpaController: EntityManager sobre la
 * persistence-unit COAPU, cada operación es un stored procedure MySQL
 * (Sp_cff_x_Nombre) invocado con parámetros posicionales (?) — nunca
 * concatenación de strings.
 */
public class CertificateFileJpaController implements Serializable {

    public CertificateFileJpaController() {
        emf = Persistence.createEntityManagerFactory("COAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean registerFile(String cliente, String anio, String orden, String lote, String carpeta,
            String originalFileName, long officeFileId, String officeUuid, String mimeType, Long sizeBytes,
            String uploadedById, String uploadedByName) {

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_cff_r_RegisterCertificateFile`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            q.setParameter(1, cliente);
            q.setParameter(2, anio);
            q.setParameter(3, orden);
            q.setParameter(4, lote);
            q.setParameter(5, carpeta);
            q.setParameter(6, originalFileName);
            q.setParameter(7, officeFileId);
            q.setParameter(8, officeUuid);
            q.setParameter(9, mimeType);
            q.setParameter(10, sizeBytes);
            q.setParameter(11, uploadedById);
            q.setParameter(12, uploadedByName);
            q.executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            em.close();
        }
    }

    public List<CertificateFileRow> consultFilesByLote(String cliente, String anio, String orden, String lote, String carpeta) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_cff_c_ConsultCertificateFilesByLote`(?, ?, ?, ?, ?)");
            q.setParameter(1, cliente);
            q.setParameter(2, anio);
            q.setParameter(3, orden);
            q.setParameter(4, lote);
            q.setParameter(5, carpeta);
            List filas = q.getResultList();
            em.getTransaction().commit();
            return mapRows(filas);
        } catch (Exception e) {
            return new ArrayList<CertificateFileRow>();
        } finally {
            em.close();
        }
    }

    public CertificateFileRow consultFileById(long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_cff_c_ConsultCertificateFileById`(?)");
            q.setParameter(1, id);
            List filas = q.getResultList();
            em.getTransaction().commit();
            List<CertificateFileRow> mapeadas = mapRows(filas);
            return mapeadas.isEmpty() ? null : mapeadas.get(0);
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean deleteFile(long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_cff_d_DeleteCertificateFile`(?)");
            q.setParameter(1, id);
            q.executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            em.close();
        }
    }

    public List<String> consultDistinctClientes() {
        return consultDistinctSingleColumn("CALL `Sp_cff_c_ConsultDistinctClientes`()", new Object[0]);
    }

    public List<String> consultDistinctAnios(String cliente) {
        return consultDistinctSingleColumn("CALL `Sp_cff_c_ConsultDistinctAnios`(?)", new Object[]{cliente});
    }

    public List<String> consultDistinctOrdenes(String cliente, String anio) {
        return consultDistinctSingleColumn("CALL `Sp_cff_c_ConsultDistinctOrdenes`(?, ?)", new Object[]{cliente, anio});
    }

    public List<String> consultDistinctLotes(String cliente, String anio, String orden) {
        return consultDistinctSingleColumn("CALL `Sp_cff_c_ConsultDistinctLotes`(?, ?, ?)", new Object[]{cliente, anio, orden});
    }

    private List<String> consultDistinctSingleColumn(String sql, Object[] params) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery(sql);
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i + 1, params[i]);
            }
            List filas = q.getResultList();
            em.getTransaction().commit();

            List<String> resultado = new ArrayList<String>();
            if (filas != null) {
                for (Object filaObj : filas) {
                    if (filaObj instanceof Object[]) {
                        Object valor = ((Object[]) filaObj)[0];
                        if (valor != null) {
                            resultado.add(String.valueOf(valor));
                        }
                    } else if (filaObj != null) {
                        resultado.add(String.valueOf(filaObj));
                    }
                }
            }
            return resultado;
        } catch (Exception e) {
            return new ArrayList<String>();
        } finally {
            em.close();
        }
    }

    private List<CertificateFileRow> mapRows(List filas) {
        List<CertificateFileRow> resultado = new ArrayList<CertificateFileRow>();
        if (filas == null) {
            return resultado;
        }
        for (Object filaObj : filas) {
            Object[] fila = (Object[]) filaObj;
            long id = toLong(fila[0]);
            long officeFileId = toLong(fila[1]);
            String name = fila[2] != null ? String.valueOf(fila[2]) : "";
            String mimeType = fila[3] != null ? String.valueOf(fila[3]) : null;
            Long sizeBytes = fila[4] != null ? Long.valueOf(toLong(fila[4])) : null;
            long lastModified = (fila.length > 5 && fila[5] instanceof Timestamp)
                    ? ((Timestamp) fila[5]).getTime() : System.currentTimeMillis();
            String uploadedById = (fila.length > 6 && fila[6] != null) ? String.valueOf(fila[6]) : null;
            String uploadedByName = (fila.length > 7 && fila[7] != null) ? String.valueOf(fila[7]) : null;
            resultado.add(new CertificateFileRow(id, officeFileId, name, mimeType, sizeBytes, lastModified, uploadedById, uploadedByName));
        }
        return resultado;
    }

    private long toLong(Object value) {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return 0L;
    }
}
