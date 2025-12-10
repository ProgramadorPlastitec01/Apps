package controladoras;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TipoAreaJpaController {

    public TipoAreaJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlesMicrobiologicosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean Registrar_tipo_area(String tar, int id_tipo_nivel) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tar_r_tipo_area`('" + tar + "','" + id_tipo_nivel + "')");
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

    public boolean EstadoTipoArea(int id_tipo_area, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("update tipo_area a set a.estado =   " + estado + " where a.idTipo_Area = " + id_tipo_area + "");
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

    public List Consultar_tipos_areas() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tar_c_tipos_area`()");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados.isEmpty()) {
                return null;
            } else {
                return resultados;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarTiposAreasIdtipoNivel(int id_tipo_nivel) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tar_c_tipos_area_id_tipo_n`('" + id_tipo_nivel + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados.isEmpty()) {
                return null;
            } else {
                return resultados;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
