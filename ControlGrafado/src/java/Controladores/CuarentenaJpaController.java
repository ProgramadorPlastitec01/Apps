package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class CuarentenaJpaController implements Serializable {

    public CuarentenaJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlGrafadoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean RegistrarCuarentena(String id_turno, int id_usuario, String cuarentena, String aprobacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ctn_r_cuarentena`('" + id_turno + "', '" + id_usuario + "', '" + cuarentena + "', '" + aprobacion + "')");
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

    public List ConsultaTurnoAprovado(int id_turno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ctn_c_control_dmsc`('" + id_turno + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultaAprobarCuarentena(String Mcuarentena) {
        if (Mcuarentena.equals("[0]")) {
            return null;
        } else {
            EntityManager etm = getEntityManager();
            etm.getTransaction().begin();
            String queryCAC = "SELECT c.id_dimensional_c,c.lote_ensamble,c.consecutivo,c.calidad FROM control_dms_c c WHERE c.id_dimensional_c IN (" + Mcuarentena + ");";
            try {
                Query q = etm.createNativeQuery(queryCAC);
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
    }
}
