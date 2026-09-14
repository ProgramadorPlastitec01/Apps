package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class FormulaMateriaPrimaJpaController {

    public FormulaMateriaPrimaJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlFormulasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Traer_formula_materia_prima_id(int ifm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_fmp_t_formula_materia_prima_id_formula`('" + ifm + "')");
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

    public List Traer_formula_materia_prima_id_mp(int ifm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_fmp_t_formula_materia_prima_id`('" + ifm + "')");
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

    public boolean Registrar_formula_materia_prima(int ifm, int imp, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_fmp_r_formula_materia_prima`('" + ifm + "','" + imp + "','" + urg + "')");
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

    public boolean Activar_materia_prima_formula(int imf) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_fmp_m_activar`('" + imf + "')");
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

    public boolean Desactivar_materia_prima_formula(int imf) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_fmp_m_desactivar`('" + imf + "')");
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

    public boolean Posicion_mp_formula(int imf, int psc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_fmp_m_posicion`('" + imf + "','" + psc + "')");
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
