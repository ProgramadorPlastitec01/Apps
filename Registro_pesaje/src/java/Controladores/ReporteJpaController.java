package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ReporteJpaController implements Serializable {
    
    public ReporteJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="LIST">
    public List ConsultarControlesxRegistro(int id_reg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ctl_c_ConsultarControlesDetalles`('" + id_reg + "')");
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
    public List ConsultarCuarentenasxControles(int id_control) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cua_c_CosnultarDetalleCuarentena`('" + id_control + "')");
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
    
    
//</editor-fold>
}
