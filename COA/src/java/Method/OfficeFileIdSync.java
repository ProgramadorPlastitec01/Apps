package Method;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Re-mapea los office_file_id guardados en BD (user_signatures y
 * certificate_files) usando el UUID del archivo.
 *
 * Motivo: al migrar Office Platform a otro servidor los archivos conservan
 * su UUID pero reciben ids nuevos. Un id viejo puede no existir (404) o,
 * peor, apuntar a OTRO archivo; con eso las firmas no cargan y un borrado
 * podría eliminar un archivo equivocado. El UUID es el dato estable.
 */
public class OfficeFileIdSync {

    private static final String[][] TABLAS = {
        // tabla, columna PK, columna con el nombre del archivo
        {"user_signatures", "id", "file_name"},
        {"certificate_files", "id", "original_file_name"}
    };

    private static EntityManagerFactory emf;

    private static synchronized EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("COAPU");
        }
        return emf;
    }

    public static class Resultado {

        public int revisados;
        public int corregidos;
        public final List<String> noEncontrados = new ArrayList<String>();
        public final List<String> errores = new ArrayList<String>();

        public boolean isOk() {
            return noEncontrados.isEmpty() && errores.isEmpty();
        }

        public String resumen() {
            StringBuilder sb = new StringBuilder();
            sb.append(revisados).append(" archivo(s) revisados, ").append(corregidos).append(" id(s) corregidos");
            if (!noEncontrados.isEmpty()) {
                sb.append(", no encontrados en el gestor: ").append(noEncontrados);
            }
            if (!errores.isEmpty()) {
                sb.append(", errores: ").append(errores);
            }
            return sb.toString();
        }
    }

    public static synchronized Resultado sincronizar() {
        Resultado r = new Resultado();
        for (String[] t : TABLAS) {
            sincronizarTabla(t[0], t[1], t[2], r);
        }
        return r;
    }

    private static void sincronizarTabla(String tabla, String pk, String colNombre, Resultado r) {
        List filas;
        EntityManager em = getEmf().createEntityManager();
        try {
            filas = em.createNativeQuery("SELECT " + pk + ", " + colNombre + ", office_file_id, office_uuid FROM " + tabla
                    + " WHERE office_uuid IS NOT NULL AND office_uuid <> ''").getResultList();
        } catch (Exception ex) {
            r.errores.add(tabla + ": " + ex.getMessage());
            return;
        } finally {
            em.close();
        }

        for (Object o : filas) {
            Object[] f = (Object[]) o;
            long id = ((Number) f[0]).longValue();
            String nombre = f[1] == null ? null : f[1].toString();
            long idGuardado = f[2] == null ? 0L : ((Number) f[2]).longValue();
            String uuid = f[3].toString();
            r.revisados++;
            try {
                Long idActual = OfficePlatformService.buscarIdPorUuid(uuid, nombre);
                if (idActual == null) {
                    r.noEncontrados.add(tabla + ":" + nombre);
                    continue;
                }
                if (idActual != idGuardado) {
                    actualizar(tabla, pk, id, idActual);
                    r.corregidos++;
                    System.out.println("[OfficeFileIdSync] " + tabla + " id=" + id + " (" + nombre + "): office_file_id "
                            + idGuardado + " -> " + idActual);
                }
            } catch (Exception ex) {
                r.errores.add(tabla + ":" + nombre + " (" + ex.getMessage() + ")");
            }
        }
    }

    private static void actualizar(String tabla, String pk, long id, long nuevoOfficeId) {
        EntityManager em = getEmf().createEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("UPDATE " + tabla + " SET office_file_id = ? WHERE " + pk + " = ?");
            q.setParameter(1, nuevoOfficeId);
            q.setParameter(2, id);
            q.executeUpdate();
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}
