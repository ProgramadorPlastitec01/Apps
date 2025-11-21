package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RegistroImplementoJpaController {

    public RegistroImplementoJpaController() {
        emf = Persistence.createEntityManagerFactory("RegistrosLaboratorioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Implementos_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rip_t_implemento_registro`('" + irg + "')");
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

    public boolean Registrar_implemento_registro(int irg, String usa) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rip_r_implemento_registro`('" + irg + "','" + usa + "')");
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

    public boolean Registrar_implemento_registro(int irg, String scp, String vcp, String srl, String vrl, String src, String vrc, String ebc, String ecl, int tjr, int ept, int lve, int pza, String usa, String sid, String vid,String sln, String vln) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rip_m_implemento_registro`('" + irg + "','" + scp + "','" + vcp + "','" + srl + "','" + vrl + "','" + src + "','" + vrc + "','" + ebc + "','" + ecl + "','" + tjr + "','" + ept + "','" + lve + "','" + pza + "','" + usa + "','" + sid + "','" + vid + "','" + sln + "','" + vln + "')");
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
