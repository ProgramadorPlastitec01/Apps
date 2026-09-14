package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RegistroDetalleJpaController {

    public RegistroDetalleJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlFormulasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Traer_registro_detalle_id_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rdt_t_registro_detalle`('" + irg + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean Registrar_registro_detalle(int irg, int itm, int psc, String csc, String lpc, String ccl, String slt, String ccs, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rdt_r_registro_detalle`('" + irg + "','" + itm + "','" + psc + "','" + csc + "','" + lpc + "','" + ccl + "','" + slt + "','" + ccs + "','" + urg + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Registrar_registro_detalle_id_formula(int ifm, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rdt_r_registro_detalle_id_formula`('" + ifm + "','" + urg + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Modificar_registro_detalle(int ird, String atb, String vlr, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE registro_detalle SET " + atb + " = '" + vlr + "', usuario_registro = '" + urg + "' WHERE id_registro_detalle = " + ird);
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Quitar_registro_detalle(int ird) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rdt_e_registro_detalle_id`('" + ird + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
