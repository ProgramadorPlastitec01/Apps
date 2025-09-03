package Controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DeviceJpaController implements Serializable {

    public DeviceJpaController() {
        emf = Persistence.createEntityManagerFactory("AppTIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="CONSULTS">
    public List ConsultAllTypeDeviceCount() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_dce_c_ConsultTypeDevice`()");
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

    public List ConsultAllStateDeviceCount() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_dce_c_ConsultDeviceStateCount`()");
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

    public List ConsultDevice_type_All(int idTypeDv) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_dce_c_ConsultDeviceAll`(" + idTypeDv + ")");
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

    public List ConsultDevice_type_ste(int idTypeDv, int ste) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_dce_c_ConsultDevice_ste`(" + idTypeDv + ")");
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

    public List ConsultDevice_type_D(int idTypeDv) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_dce_c_ConsultDevice_steD`(" + idTypeDv + ")");
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

    public List ConsultDevice_type_R(int idTypeDv) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_dce_c_ConsultDevice_steR`(" + idTypeDv + ")");
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
    //<editor-fold defaultstate="collapsed" desc="EJECTIONS">
//</editor-fold>
}
