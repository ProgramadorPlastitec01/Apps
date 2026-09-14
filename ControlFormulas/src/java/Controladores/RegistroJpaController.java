package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RegistroJpaController {

    public RegistroJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlFormulasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Traer_registro_formula_id(int ifm, String lte) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_c_registros`('" + ifm + "','" + lte + "')");
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

    public List Traer_registro_formula_id_filtro(int ifm, String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registros`('" + ifm + "','" + fto + "')");
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

    public List Traer_registro_formula_lote_app(String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registros_lote_app`('" + fto + "')");
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

    public List Traer_registro_formula_id_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registro`('" + irg + "')");
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

    public List Traer_lotes_formula(int ifm, int gad) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT r.id_formula,r.lote_generacion FROM registro r WHERE r.id_formula = " + ifm + "  AND r.guardado = " + gad + " GROUP BY  r.id_formula,r.lote_generacion ORDER BY r.lote_generacion DESC");
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

    public List Traer_lotes_formula_filtro(int ifm, String fto, int gad) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT r.id_formula,r.lote_generacion FROM registro r WHERE (r.id_formula = " + ifm + " AND r.guardado = " + gad + ") AND (r.fecha_generacion = '" + fto + "' OR r.lote_generacion LIKE ('%" + fto + "%') OR r.codigo_compuesto LIKE ('%" + fto + "%') OR r.responsable_produccion LIKE ('%" + fto + "%') OR r.responsable_calidad LIKE ('%" + fto + "%')) GROUP BY  r.id_formula,r.lote_generacion ORDER BY r.lote_generacion DESC");
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

    public boolean Registrar_registro_cabecera(int ifm, String fgn, String lgn, String ccp, String rpd, String rcl, String csf, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_r_registro`('" + ifm + "','" + fgn + "','" + lgn + "','" + ccp + "','" + rpd + "','" + rcl + "','" + csf + "','" + urg + "')");
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

    public boolean Guardar_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_guardar`('" + irg + "')");
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

    public boolean Observaciones_registro(int irg, String rcl, String osv) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_observacion`('" + irg + "','" + rcl + "','" + osv + "')");
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
