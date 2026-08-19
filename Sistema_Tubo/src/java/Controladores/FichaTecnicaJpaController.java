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

    public List Consult_Data_sheet_counter() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_contarfichas`()");
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

    public List ConsultDataSheet_filter(String condition) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT f.id_ficha_tecnica,f.codigo,f.producto,f.nombre,f.version, "
                    + "         f.interno_sin_presurizar, f.interno_sin_presurizar_min, f.interno_sin_presurizar_max, "
                    + "         f.interno_presurizado, f.interno_presurizado_min, f.interno_presurizado_max, "
                    + "         f.externo_sin_presurizar, f.externo_sin_presurizar_min, f.externo_sin_presurizar_max, "
                    + "         f.externo_presurizado, f.externo_presurizado_min, f.externo_presurizado_max, "
                    + "			f.espesor_pared,f.espesor_pared_min,f.espesor_pared_max, "
                    + "			f.diametro_exterior_bobina,f.diametro_exterior_bobina_min,f.diametro_exterior_bobina_max, "
                    + "			f.diametro_interior_bobina,f.diametro_interior_bobina_min,f.diametro_interior_bobina_max, "
                    + "			f.peso_rollo, f.peso_rollo_min, f.peso_rollo_max, f.min_rugosidad, f.max_rugosidad, f.observacion, "
                    + "			f.estado,f.usuario_registro,f.fecha_registro, f.pressure, f.pressure_min, f.pressure_max, f.tipo "
                    + "FROM ficha_tecnica f "
                    + "WHERE  " + condition + "; ");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

//    CONSULTAS PP
    public List Consult_Data_sheet_PP() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_c_consultarFichasTecnicasPP`()");
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
            double roll_weight_max, double pressure, double press_min, double press_max,
            double min_rugosity, double max_rugosity, String observation, String user_name) {
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
                    + pressure + "','" + press_min + "','" + press_max + "','" + min_rugosity + "', '"
                    + max_rugosity + "','" + observation + "','" + user_name + "')");
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
            double diameter_coil_in_max, double rollweight, double rollweightmin, double rollweightmax,
            double pressure, double press_min, double press_max, double min_rugosity, double max_rugosity, String observation) {
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
                    + diameter_coil_in_max + "','" + rollweight + "','" + rollweightmin + "','" + rollweightmax + "','"
                    + pressure + "','" + press_min + "','" + press_max + "','"
                    + min_rugosity + "','" + max_rugosity + "','" + observation + "')");
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

//    PRODUCTO PP
    public boolean DataSheetRegisterPP(
            String name_sheet, int version, String code, String product,
            double diameter_int, double diameter_int_min, double diameter_int_max,
            double diameter_ext, double diameter_ext_min, double diameter_ext_max,
            double wall_thickness_der, double wall_thickness_der_min, double wall_thickness_der_max,
            double wall_thickness_izq, double wall_thickness_izq_min, double wall_thickness_izq_max,
            double galga, double galga_min, double galga_max, double roll_weight, double roll_weight_min,
            double roll_weight_max, String observation, String user_name) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery(
                    "CALL `sp_ficha_tecnica_pp_registrar`('" + name_sheet + "','" + version + "','" + code + "','" + product + "','" + diameter_int + "','" + diameter_int_min + "','" + diameter_int_max + "','"
                    + diameter_ext + "','" + diameter_ext_min + "','" + diameter_ext_max + "','" + wall_thickness_der + "','" + wall_thickness_der_min + "','" + wall_thickness_der_max + "','" + wall_thickness_izq + "','"
                    + wall_thickness_izq_min + "','" + wall_thickness_izq_max + "','" + galga + "','" + galga_min + "','" + galga_max + "','"
                    + roll_weight + "','" + roll_weight_min + "','" + roll_weight_max + "','" + observation + "','" + user_name + "')");
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
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            return false;
        }
    }

    public boolean DataSheetUpdatePP(
            int id_data_sheet, String code, String product, String name_sheet, int version, double diameter_int,
            double diameter_int_min, double diameter_int_max, double diameter_ext, double diameter_ext_min, double diameter_ext_max,
            double wall_thickness_der, double wall_thickness_der_min, double wall_thickness_der_max, double wall_thickness_izq,
            double wall_thickness_izq_min, double wall_thickness_izq_max, double galga, double galga_min, double galga_max, double roll_weight,
            double roll_weight_min, double roll_weight_max, String observation) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ficha_tecnica_pp_actualizar`('"
                    + id_data_sheet + "','" + name_sheet + "','" + version + "','" + code + "','" + product + "','" + diameter_int + "','" + diameter_int_min + "','" + diameter_int_max + "','"
                    + diameter_ext + "','" + diameter_ext_min + "','" + diameter_ext_max + "','" + wall_thickness_der + "','" + wall_thickness_der_min + "','"
                    + wall_thickness_der_max + "','" + wall_thickness_izq + "','" + wall_thickness_izq_min + "','" + wall_thickness_izq_max + "','" + galga + "','"
                    + galga_min + "','" + galga_max + "','" + roll_weight + "','" + roll_weight_min + "','" + roll_weight_max + "','" + observation + "')");

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
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            return false;
        }
    }

    //</editor-fold>
}
