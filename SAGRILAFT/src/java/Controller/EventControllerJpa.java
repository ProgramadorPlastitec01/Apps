package Controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EventControllerJpa implements Serializable {

    public EventControllerJpa() {
        emf = Persistence.createEntityManagerFactory("SAGRILAFTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="LIST">
    public List ConsultEvent(int IdSegmentation) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_Evt_c_ConsultEvent`('" + IdSegmentation + "')");
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
    public List ConsultEventId(int IdVisit) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_Evt_c_ConsultEventId`('" + IdVisit + "')");
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
    //<editor-fold defaultstate="collapsed" desc="PROCESS">
    public boolean RegisterEvent(int IdSegmentation, String DateEvent, String Affair, String Description, String Attach, String UserRegister) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_Evt_r_RegisterEvent`('" + IdSegmentation + "','" + DateEvent + "','" + Affair + "','" + Description + "','" + Attach + "','" + UserRegister + "')");
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
    public boolean UpdateEvent(int IdEvent, String DateEvent, String Affair, String Description, String Attach) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_Evt_u_UpdateEvent`('" + IdEvent + "','" + DateEvent + "','" + Affair + "','" + Description + "','" + Attach + "')");
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
