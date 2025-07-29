package Controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ActivityDetailJpaController implements Serializable {

    public ActivityDetailJpaController() {
        emf = Persistence.createEntityManagerFactory("AppTIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="LIST">
    public List ConsultActivityDetailIdAct(int idAct) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_adt_c_ConsultActivityDetailId`(" + idAct + ")");
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

    public List ConsultActivityDetail_Act(int idActDet) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_adt_c_ConsultActivityDetail_activity`(" + idActDet + ")");
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

    public List ConsultActivityDetail_Ver(int idActDet) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_adt_c_ConsultActivityDetail_verificatiion`(" + idActDet + ")");
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

    public List ConsultActivityDetailProcess(int idAct) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_adt_c_ConsultProcessByidAct`(" + idAct + ")");
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
//    public boolean UpdateActivityDetail_Act(int idActDet, String dateAct, String EjecAct, int user) {
//        EntityManager em = getEntityManager();
//        em.getTransaction().begin();
//        try {
//            Query q = em.createNativeQuery("CALL `Sp_adt_u_updateActivityDetail_activity`(" + idActDet + ",'" + dateAct + "','" + EjecAct + "'," + user + ")");
//            int resultado = q.executeUpdate();
//            em.getTransaction().commit();
//            em.clear();
//            em.close();
//            if (resultado == 1) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }
    public boolean UpdateActivityDetail_Act(String idActDet, String dateAct, String EjecAct, int user) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("UPDATE activity_detail ac "
                    + "SET ac.date_activity = '" + dateAct + "', ac.eject_activity = '" + EjecAct + "', ac.id_user_activity = " + user + " "
                    + "WHERE ac.id_activity_detail IN (" + idActDet + "); ");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean UpdateActivityDetail_Ver(String idActDet, String dateVer, String EjecVer, int user) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("UPDATE activity_detail ac "
                    + "SET ac.date_verification = '" + dateVer + "', ac.eject_verification = '" + EjecVer + "', ac.id_user_verification = " + user + " "
                    + "WHERE ac.id_activity_detail IN (" + idActDet + ")");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
//    public boolean UpdateActivityDetail_Ver(int idActDet, String dateVer, String EjecVer, int user) {
//        EntityManager em = getEntityManager();
//        em.getTransaction().begin();
//        try {
//            Query q = em.createNativeQuery("CALL `Sp_adt_u_updateActivityDetail_verification`(" + idActDet + ",'" + dateVer + "','" + EjecVer + "'," + user + ")");
//            int resultado = q.executeUpdate();
//            em.getTransaction().commit();
//            em.clear();
//            em.close();
//            if (resultado == 1) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public boolean RegisterActivityDetail(String script) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery(script);
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado > 0) {
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
