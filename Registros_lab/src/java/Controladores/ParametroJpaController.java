package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ParametroJpaController {

    public ParametroJpaController() {
        emf = Persistence.createEntityManagerFactory("RegistrosLaboratorioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Parametros(String cdc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            List consulta = null;
            Query q = etm.createNativeQuery("CALL `sp_prm_c_parametros`('" + cdc + "')");
            consulta = q.getResultList();
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
    public List Parametros_bocas_colpitt(String cdc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            List consulta = null;
            Query q = etm.createNativeQuery("CALL `sp_prm_c_parametros_bocas_colpitt`('" + cdc + "')");
            consulta = q.getResultList();
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

    public List Parametros_screen(String cdc,int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            List consulta = null;
            Query q = etm.createNativeQuery("CALL `sp_prm_c_parametros_screen`('" + cdc + "','" + irg + "')");
            consulta = q.getResultList();
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

    public List Parametros(String cdc, String trg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            List consulta = null;
            Query q = etm.createNativeQuery("CALL `sp_prm_c_parametros_linea`('" + cdc + "','" + trg + "')");
            consulta = q.getResultList();
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

    public List Traer_parametro(int ipr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            List consulta = null;
            Query q = etm.createNativeQuery("CALL `sp_prm_t_parametro_id_parametro`('" + ipr + "')");
            consulta = q.getResultList();
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

    public List Parametros_linea(String cdc, int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            List consulta = null;
            Query q = etm.createNativeQuery("CALL `sp_prm_c_parametros_tipo_linea`('" + cdc + "','" + irg + "')");
            consulta = q.getResultList();
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

    public List Comparadores() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            List consulta = null;
            Query q = etm.createNativeQuery("CALL `sp_prm_t_comparadores`()");
            consulta = q.getResultList();
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

    public boolean Registrar_parametro(String nbe, int itp, int fce, String tdt, int itl, String cpr, String rps, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_prm_r_parametro`('" + nbe + "','" + itp + "','" + fce + "','" + tdt + "','" + itl + "','" + cpr + "','" + rps + "','" + urg + "')");
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

    public boolean Activar_parametro(int ipr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_prm_m_activar`('" + ipr + "')");
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

    public boolean Desactivar_parametro(int ipr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_prm_m_desactivar`('" + ipr + "')");
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

    public boolean Posicion_parametro(int ipr, int psc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE parametro SET posicion=" + psc + " WHERE id_parametro=" + ipr + "");
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
}
