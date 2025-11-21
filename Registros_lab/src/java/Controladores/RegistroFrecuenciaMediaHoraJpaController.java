package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author prog.sistemas1
 */
public class RegistroFrecuenciaMediaHoraJpaController {

    public RegistroFrecuenciaMediaHoraJpaController() {
        emf = Persistence.createEntityManagerFactory("RegistrosLaboratorioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Parametros_registro_frecuencia_media_hora(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfm_t_parametros_registro`('" + irg + "')");
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

    public List Registros_lote_media(String lte, int ipd, int nmr, int iln, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfm_t_registros_lote`('" + lte + "','" + ipd + "','" + nmr + "','" + iln + "','" + fin + "','" + ffn + "')");
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

    public List Registros_lote_resumido(String lte, int ipd, int nmr, int iln, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfm_t_registros_lote_resumido`('" + lte + "','" + ipd + "','" + nmr + "','" + iln + "','" + fin + "','" + ffn + "')");
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

    public List Registros_lote(String lte, int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfm_t_parametros_tomas_registro_lote`('" + lte + "','" + irg + "')");
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

    public List Parametros_tomas_registro_frecuencia_hora(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfm_c_parametro_tomas_registro`('" + irg + "')");
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

    public List Responsables_tomas_registro_frecuencia_hora(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfm_t_responsables_registro`('" + irg + "')");
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

    public boolean Registrar_frecuencia_hora(int irg, int ipr, String vlr, int fce, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE `registro_frecuencia_media_hora` SET `toma" + fce + "`='" + vlr + "',`usuario_toma" + fce + "`= CONCAT((DATE_FORMAT(NOW(), '%H:%i:%p')),'/" + urg + "') WHERE `id_registro`='" + irg + "' AND `id_parametro`='" + ipr + "'");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Registrar_frecuencia_hora(int irg, int ipr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfm_r_frecuencia_hora_registro`('" + irg + "','" + ipr + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public List Registros_lote(int nmr, int ipd, int irg, String prm, String lte, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfm_t_registros_lote_datos`('" + nmr + "','" + ipd + "','" + irg + "','" + prm + "','" + lte + "','" + fin + "','" + ffn + "')");
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

    public boolean Eliminar_frecuencia_hora_registro(int irg, int fce) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE registro_frecuencia_media_hora SET toma" + fce + " = NULL , usuario_toma" + fce + " = NULL WHERE id_registro = " + irg + " ");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    //DATOS ESTADISTICOS R-GC-017
    public List Datos_estadisticos_frecuencia_hora(String prm, String irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT r.id_registro,p.comparador,rf.toma1,rf.toma2,rf.toma3,rf.toma4,rf.toma5,rf.toma6,rf.toma7,rf.toma8,rf.toma9,rf.toma10 FROM registro_frecuencia_media_hora rf INNER JOIN registro r ON rf.id_registro = r.id_registro INNER JOIN parametro p ON rf.id_parametro = p.id_parametro WHERE (p.comparador like '" + prm + "') and (" + irg + ")");
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

    public List Datos_estadisticos_frecuencia_hora_avt(String prm, String irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT r.id_registro,p.nombre,rf.toma1,rf.toma2,rf.toma3,rf.toma4,rf.toma5,rf.toma6,rf.toma7,rf.toma8,rf.toma9,rf.toma10 FROM registro_frecuencia_media_hora rf INNER JOIN registro r ON rf.id_registro = r.id_registro INNER JOIN parametro p ON rf.id_parametro = p.id_parametro WHERE (p.nombre like '%" + prm + "%') and (" + irg + ")");
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
