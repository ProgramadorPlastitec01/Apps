package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RolloJpaController implements Serializable {

    public RolloJpaController() {
        emf = Persistence.createEntityManagerFactory("Sistema_TuboPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="LIST">
    public List Consult_rollo() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_c_consultarRollos`()");
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

    public List Consult_rollo_irg(int idReg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_c_consultarRollos_idreg`(" + idReg + ")");
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

    public List Consult_rollo_lote(int idReg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_r_registro_ConsultarRegistro_lote`(" + idReg + ")");
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

    public List Consult_rollo_id(int idroll) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_c_consultarRollos_id`('" + idroll + "')");
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

    public List Consult_LastRollo(int idord, String Txt_lote) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rollo_c_consultarUltimoRollo`('" + idord + "', '" + Txt_lote + "')");
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

    public List Consult_LastRollo_v2(int idreg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rollo_c_consultarUltimoRolloxOrden`('" + idreg + "')");
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

    public List ContarRollosxOrderxLote(int idord, int idReg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_roll_contarRollosxOrderAndLote`('" + idord + "', '" + idReg + "')");
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

    public List ContarRollosxOrder(int idord) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_roll_contarRollosxOrder`('" + idord + "')");
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

    public List ContarRollosxOrderxlote(int idord, String lte) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_roll_contarRollosxOrderxlote`(" + idord + ", '" + lte + "')"
            );
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

    public List Consult_Datasheet(int idOrden) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rollo_c_consultarFichaTecnica`('" + idOrden + "')");
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

    public List Consult_rol(int idRol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rol_c_consultarRol_Id`('" + idRol + "')");
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

    public List Consult_LastRollRegistered(int idReg, int nroRoll) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_c_consultarRolloRecienRegistrado`('" + idReg + "','" + nroRoll + "')");
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

    public List ConsultRollXEstado(int id_order, String batch, int state) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rollo_h_c_consultaXEstado`('" + id_order + "','" + batch + "','" + state + "')");
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

    public List ConsultRollHXState(int id_order, String batch, int state) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rolloh_c_consultaRollHEstado`('" + id_order + "','" + batch + "','" + state + "')");
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

    public List ConsultRollHXStateAll(int id_order, String batch) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rolloh_c_consultaRollHEstadoALL`('" + id_order + "','" + batch + "')");
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

    public List ConsultStatistical(int id_order, String batch, int start, int end) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rollo_c_consultaEstadistico`('" + id_order + "','" + batch + "','" + start + "','" + end + "')");
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

    public List ConsultRollsByLote(int id_order) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rll_c_ConsulRollsByLote`('" + id_order + "')");
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

    public List ConsultPressData(int id_Rol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rll_c_ConsultRollPressData`('" + id_Rol + "')");
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

    public List RollComparation(int id_Rol, int nroRll) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_rll_c_ConsultRollComparation`('" + id_Rol + "', '" + nroRll + "')");
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

    public List RollValidation(int id_Reg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_rll_c_ConsultRollValid`('" + id_Reg + "')");
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

    public List LastRollRegistrer(int id_Reg, int NmbRoll) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rll_c_ConsultarxNumeroRollo`('" + id_Reg + "','" + NmbRoll + "')");
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
    public boolean RolloRegister(int idReg, int NoRll, double insp, double exsp, double spr_1, double spr_2, double spr_3, double spr_4, double rug_1, double rug_2, double rug_3, double rug_4, int inp_vis) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_r_registrarRollo`('" + idReg + "','" + NoRll + "','" + insp + "','" + exsp + "','" + spr_1 + "','" + spr_2 + "','" + spr_3 + "','" + spr_4 + "','" + rug_1 + "','" + rug_2 + "','" + rug_3 + "','" + rug_4 + "','" + inp_vis + "')");
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

    public boolean RolloRegister_est(int idReg, int NoRll, double insp, double exsp, double spr_1, double spr_2, double spr_3, double spr_4, double rug_1, double rug_2, double rug_3, double rug_4, int inp_vis, int est, String userReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_r_registroRollo_est`('" + idReg + "','" + NoRll + "','" + insp + "','" + exsp + "','" + spr_1 + "','" + spr_2 + "','" + spr_3 + "','" + spr_4 + "','" + rug_1 + "','" + rug_2 + "','" + rug_3 + "','" + rug_4 + "','" + inp_vis + "','" + est + "','" + userReg + "')");
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

    public boolean RolloUpdate(int idRoll, double insp, double exsp, double spr_1, double spr_2, double spr_3, double spr_4, double presIny, double pesRll, double rug_1, double rug_2, double rug_3, double rug_4, int inp_vis, String UserName) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_a_actualizarRoll`('" + idRoll + "','" + insp + "','" + exsp + "','" + spr_1 + "','" + spr_2 + "','" + spr_3 + "','" + spr_4 + "','" + presIny + "','" + pesRll + "','" + rug_1 + "','" + rug_2 + "','" + rug_3 + "','" + rug_4 + "','" + inp_vis + "','" + UserName + "')");
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

    public boolean RolloUpdate_est(int idRoll, double insp, double exsp, double spr_1, double spr_2, double spr_3, double spr_4, double rug_1, double rug_2, double rug_3, double rug_4, int inp_vis, int est, String UserReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_a_actualizarRollo_est`('" + idRoll + "','" + insp + "','" + exsp + "','" + spr_1 + "','" + spr_2 + "','" + spr_3 + "','" + spr_4 + "','" + rug_1 + "','" + rug_2 + "','" + rug_3 + "','" + rug_4 + "','" + inp_vis + "','" + est + "','" + UserReg + "')");
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
    public boolean RolloUpdateNoRoughness(int idRoll, double insp, double exsp, double spr_1, double spr_2, double spr_3, double spr_4, double presIny, double pesRll, int inp_vis, String UserReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_a_actualizarRolloSinRugosidad`('" + idRoll + "','" + insp + "','" + exsp + "','" + spr_1 + "','" + spr_2 + "','" + spr_3 + "','" + spr_4 + "','" + presIny + "','" + pesRll + "','" + inp_vis + "','" + UserReg + "')");
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
    
    public boolean RolloUpdate_press_est(int idRoll, double insp, double exsp, int est, String UserReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_a_actualizarRollo_pres_est`('" + idRoll + "','" + insp + "','" + exsp + "','" + est + "','" + UserReg + "')");
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

    public boolean RolloRegister_insu(int idReg, int NoRll, double insp, double exsp, double spr_1, double spr_2, double spr_3, double spr_4, double presIny, double pesRll) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_r_registrarRollo_insumos`('" + idReg + "','" + NoRll + "','" + insp + "','" + exsp + "','" + spr_1 + "','" + spr_2 + "','" + spr_3 + "','" + spr_4 + "','" + presIny + "','" + pesRll + "')");
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

    public boolean RolloRegister_insu_est(int idReg, int NoRll, double diaInt, double diaExt, double spr_1, double spr_2, double spr_3, double spr_4, double presIny, double pesRll, int est, String userReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_r_registrarRollo_insumos_est`('" + idReg + "','" + NoRll + "','" + diaInt + "','" + diaExt + "','" + spr_1 + "','" + spr_2 + "','" + spr_3 + "','" + spr_4 + "','" + presIny + "','" + pesRll + "','" + est + "','" + userReg + "')");
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

    public boolean RolloUpdate_insu(int idRoll, double diaInt, double diaExt, double spr_1, double spr_2, double spr_3, double spr_4, double presIny, double pesRll, String urg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_a_actualizarRoll_insu`('" + idRoll + "','" + diaInt + "','" + diaExt + "','" + spr_1 + "','" + spr_2 + "','" + spr_3 + "','" + spr_4 + "','" + presIny + "','" + pesRll + "','" + urg + "')");
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

    public boolean RolloUpdate_insu_est(int idRoll, double diaInt, double diaExt, double spr_1, double spr_2, double spr_3, double spr_4, double presIny, double pesRll, int est, String UserReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_a_actualizarRoll_insu_est`('" + idRoll + "','" + diaInt + "','" + diaExt + "','" + spr_1 + "','" + spr_2 + "','" + spr_3 + "','" + spr_4 + "','" + presIny + "','" + pesRll + "','" + est + "','" + UserReg + "')");
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

    public boolean RolloUpdate_press(int idRoll, double presIny, double pesRll) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_a_actualziarRollo`('" + idRoll + "','" + presIny + "','" + pesRll + "')");
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

    public boolean UpdateRollNumber(int idRoll, int number) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_m_actualizarNumeroRollo`('" + idRoll + "','" + number + "')");
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

    public boolean UpdateIdRegisterNumberRollStateAproved(int idRoll, int id_register, int number) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_m_actaulizarRegistroEvento`('" + idRoll + "','" + id_register + "','" + number + "')");
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

    public boolean UpdatePressureDataIdRoll(int idRoll, double inPress, double exPress) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rll_u_UpdatePressureData`(" + idRoll + ",'" + inPress + "','" + exPress + "')");
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

    public boolean RegisterRollRegister(int idReg, double numberRoll) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rll_r_RegistrarRolloRegistro`(" + idReg + ",'" + numberRoll + "')");
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

    //<editor-fold defaultstate="collapsed" desc="LIST HISTORY">
    public List Consult_rollo_history(int idRollo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rolloh_c_consultaRolloHistorial`(" + idRollo + ")");
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

    public List Consult_Lastrll_history() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rll_c_sonultarLastRollsHistory`()");
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

    public List Consult_rollHistory_Id(int idRll_h) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rll_c_consultarIdrll_h_id`('" + idRll_h + "')");
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
    //<editor-fold defaultstate="collapsed" desc="PROCESS HISTORY">

    public boolean RolloH_register(int idRoll, int est, String justify, String userReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rolloh_r_registrarRolloHistorial`('" + idRoll + "','" + est + "','" + justify + "', '" + userReg + "')");
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

    public boolean RolloH_updateCleaner(int idRoll, int idRll_H) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rll_a_ActualizarRollo`('" + idRoll + "','" + idRll_H + "')");
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

    public boolean Rollo_StatusUpdate(int idRoll, int est) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rollo_a_actualziarestadoRollo`('" + idRoll + "','" + est + "')");
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

    public boolean Rollo_Replace(int idRoll, int nroRoll) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_r_registrarRollxRechazo`('" + idRoll + "','" + nroRoll + "')");
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
