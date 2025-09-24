package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ControlBoquillaJpaController implements Serializable {

    public ControlBoquillaJpaController() {
        emf = Persistence.createEntityManagerFactory("Sistema_TuboPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    //<editor-fold defaultstate="collapsed" desc="LIST">

    public List ConsultNozzle(int idOrden, String Txt_lote) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_controlBoquilla_c_consultarBoquilla`('" + idOrden + "','" + Txt_lote + "')");
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

    public List ConsultNozzlelId(int idRl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_controlBoquilla_c_consultarBoquillaIdRollo`('" + idRl + "')");
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
    public boolean ControlRegisterNozzle(int NoRll, String ShiftTime, String Done, String Verified, String userReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_controlBoquilla_r_registrarBoquilla`('" + NoRll + "','" + ShiftTime + "','" + Done + "','" + Verified + "','" + userReg + "')");
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
