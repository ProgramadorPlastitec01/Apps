package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class FactorMedidaJpaController {

    public FactorMedidaJpaController() {
        emf = Persistence.createEntityManagerFactory("Inspeccion_mangaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Factores_medida(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_fmd_t_factor_medida_id_producto`('" + ipd + "')");
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

    public boolean Registrar_factor(int irg, double fmd, String obv, String pmc, String pss, String pdf, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("INSERT INTO factor_medida(id_registro,factor_medida,observacion,promedio_micrometro,promedio_sensor,promedio_diferencia,usuario_registro) VALUES (" + irg + "," + fmd + ",'" + obv + "','" + pmc + "','" + pss + "','" + pdf + "','" + urg + "')");
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
    public boolean Registrar_factor(double fmd, String obv, String pmc, String pss, String pdf, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("INSERT INTO factor_medida(id_registro,factor_medida,observacion,promedio_micrometro,promedio_sensor,promedio_diferencia,usuario_registro) VALUES ((SELECT MAX(id_registro) FROM registro)," + fmd + ",'" + obv + "','" + pmc + "','" + pss + "','" + pdf + "','" + urg + "')");
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
