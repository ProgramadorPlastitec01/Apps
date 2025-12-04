package Controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class SettingJpaController implements Serializable {

    public SettingJpaController() {
        emf = Persistence.createEntityManagerFactory("COAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultSettingCategorie(String Category) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_stt_c_ConsultSettingCategorie`('" + Category + "')");
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
    public List ConsultSetting() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_stt_c_ConsultSetting`()");
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
    public List ConsultSettingId(int idStt) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_stt_c_ConsultSettingId`(" + idStt + ")");
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
    
    public boolean registerSetting(String categorie, String value, String decription) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_stt_r_RegisterSetting`('" + categorie + "','" + value + "','" + decription + "')");
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

    public boolean UpdateSetting(int idCtge, String categorie, String value, String decription) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_stt_u_UpdateSetting`(" + idCtge + ",'" + categorie + "','" + value + "','" + decription + "')");
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
    
    public boolean UpdateSettingState(int idCtge, int state) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_stt_u_UpdateSettingState`(" + idCtge + "," + state + ")");
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

}
