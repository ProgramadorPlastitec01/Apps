package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class FichaTecnicaJpaController implements Serializable {

    public FichaTecnicaJpaController() {
        emf = Persistence.createEntityManagerFactory("Sistema_TuboPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="LISTA">
    public List Consult_Data_sheet() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ficha_c_consultarFicha`()");
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

    public List Consult_Data_sheet_id(int ift) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ficha_c_consultarFichaId`('" + ift + "')");
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

    public List Consult_Data_sheet_State(int state) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ficha_c_consultarFichaEstado`('" + state + "')");
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
    //<editor-fold defaultstate="collapsed" desc="PROCESOS">
    public boolean DataSheetRegister(String code, String product, String name_sheet,
            int version,
            double unpres_internal, double unpres_internal_min, double unpres_internal_max,
            double pres_internal, double pres_internal_min, double pres_internal_max,
            double unpres_external, double unpres_external_min, double unpres_external_max,
            double pres_external, double pres_external_min, double pres_external_max,
            double wall_thcickness, double wall_thcickness_min,
            double wall_thcickness_max, double diameter_coil_ex, double diameter_coil_ex_min,
            double diameter_coil_ex_max, double diameter_coil_in, double diameter_coil_in_min,
            double diameter_coil_in_max, double roll_weight, double roll_weight_min,
            double roll_weight_max, double min_rugosity, double max_rugosity, String observation, String user_name) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ficha_r_registrarFicha`('" + code + "','" + product + "','" + name_sheet + "','" + version + "','"
                    + unpres_internal + "','" + unpres_internal_min + "','" + unpres_internal_max + "','"
                    + pres_internal + "','" + pres_internal_min + "','" + pres_internal_max + "','"
                    + unpres_external + "','" + unpres_external_min + "','" + unpres_external_max + "','"
                    + pres_external + "','" + pres_external_min + "','" + pres_external_max + "','"
                    + wall_thcickness + "','" + wall_thcickness_min + "','"
                    + wall_thcickness_max + "','" + diameter_coil_ex + "','" + diameter_coil_ex_min + "','"
                    + diameter_coil_ex_max + "','" + diameter_coil_in + "','" + diameter_coil_in_min + "','"
                    + diameter_coil_in_max + "','" + roll_weight + "','" + roll_weight_min + "','" + roll_weight_max + "','"
                    + min_rugosity + "', '"+ max_rugosity +"','" + observation + "','" + user_name + "')");
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

    public boolean DataSheetUpdate(int id_data_sheet, String code, String product, String name_sheet,
            int version,
            double unpres_internal, double unpres_internal_min, double unpres_internal_max,
            double pres_internal, double pres_internal_min, double pres_internal_max,
            double unpres_external, double unpres_external_min, double unpres_external_max,
            double pres_external, double pres_external_min, double pres_external_max,
            double wall_thcickness, double wall_thcickness_min,
            double wall_thcickness_max, double diameter_coil_ex, double diameter_coil_ex_min,
            double diameter_coil_ex_max, double diameter_coil_in, double diameter_coil_in_min,
            double diameter_coil_in_max, double rollweight, double rollweightmin, double rollweightmax, double min_rugosity, double max_rugosity,
            String observation) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ficha_m_modificarFicha`('" + id_data_sheet + "','" + code + "','" + product + "','" + name_sheet + "','"
                    + version + "','" 
                    + unpres_internal + "','" + unpres_internal_min + "','" + unpres_internal_max + "','"
                    + pres_internal + "','" + pres_internal_min + "','" + pres_internal_max + "','"
                    + unpres_external + "','" + unpres_external_min + "','" + unpres_external_max + "','"
                    + pres_external + "','" + pres_external_min + "','" + pres_external_max + "','"
                    + wall_thcickness + "','" + wall_thcickness_min + "','"
                    + wall_thcickness_max + "','" + diameter_coil_ex + "','" + diameter_coil_ex_min + "','"
                    + diameter_coil_ex_max + "','" + diameter_coil_in + "','" + diameter_coil_in_min + "','"
                    + diameter_coil_in_max + "','" + rollweight + "','" + rollweightmin + "','" + rollweightmax + "','" + min_rugosity + "','"+ max_rugosity +"','" + observation + "')");
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

    public boolean DataSheetChangeState(int id_data_sheet, int state) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ficha_m_estadoFicha`('" + id_data_sheet + "','" + state + "')");
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
