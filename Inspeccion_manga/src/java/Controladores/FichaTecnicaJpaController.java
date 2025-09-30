package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class FichaTecnicaJpaController {

    public FichaTecnicaJpaController() {
        emf = Persistence.createEntityManagerFactory("Inspeccion_mangaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Fichas_tecnicas() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_c_fichas`()");
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

    public List Fichas_tecnicas_codigo(String cdg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_t_fichas_codigo`('" + cdg + "')");
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

    public List Fichas_tecnicas_filtro(String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_t_fichas_filtro`('" + fto + "')");
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

    public List Fichas_tecnicas_id_producto(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_t_fichas_id_producto`('" + ipd + "')");
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

    public boolean Registrar_ficha(String pdt, String cdg, int vso, String pdb, String pdbmax, String pdbmin, String psc, String pscmax, String pscmin,
            String amg, String amgmax, String amgmin, String abb, String abbmax, String abbmin, String drz, String drzmax, String drzmin, String vep, String cvt,
            String dpr, String pso, String psomax, String psomin, String pam, String pnc, String pbs, int fct, int ctm, int cev, String osv, String urg, int apd, int mtr,
            String pdbe,String pdbemax,String pdbemin,String psce,String pscemax,String pscemin,String avt,String avtmax,String avtmin,int etvt,String cvtn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_r_ficha`('" + pdt + "','" + cdg + "','" + vso + "','" + pdb + "','" + pdbmax + "','" + pdbmin + "','" + psc + "'"
                    + ",'" + pscmax + "','" + pscmin + "','" + amg + "','" + amgmax + "','" + amgmin + "','" + abb + "','" + abbmax + "','" + abbmin + "','" + drz + "','" + drzmax + "','" + drzmin + "','" + vep + "','" + cvt + "','" + dpr + "'"
                    + ",'" + pso + "','" + psomax + "','" + psomin + "','" + pam + "','" + pnc + "','" + pbs + "','" + fct + "','" + ctm + "','" + cev + "'"
                    + ",'" + osv + "','" + urg + "','" + apd + "','" + mtr + "'"
                    + ",'" + pdbe + "','" + pdbemax + "','" + pdbemin + "','" + psce + "','" + pscemax + "','" + pscemin + "','" + avt + "','" + avtmax + "','" + avtmin + "','" + etvt + "','" + cvtn + "')");
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

    public boolean Activar_ficha(int ift) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_m_activar`('" + ift + "')");
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

    public boolean Desactivar_ficha(int ftn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_m_desactivar`('" + ftn + "')");
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

    public boolean Desactivar_ficha_version_old(String cdg, int vrs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_m_version`('" + cdg + "','" + (vrs - 1) + "')");
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
