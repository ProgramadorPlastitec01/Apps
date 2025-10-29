package controlador;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class caseLogControllerJpa implements Serializable {

    public caseLogControllerJpa() {
        emf = Persistence.createEntityManagerFactory("AppSupportPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="LIST CASE LOG">
    public List ConsultCaseLogByApp(int idApp) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_clg_c_ConsultCaseLogByApp`(" + idApp + ")");
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
    public List ConsultCaseLogByCase(int idCase) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_clg_c_ConsultCaseLogByIdCase`(" + idCase + ")");
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

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PROCESS CASE">
    public boolean RegisterCaseLog(int idCase, int idUser, int ticket, String obs, String scriptExe, String result) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_clg_r_registerCaseLog`(" + idCase + ", " + idUser + ", " + ticket + ", '" + obs + "', '" + scriptExe + "', '" + result + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

//</editor-fold>
}
