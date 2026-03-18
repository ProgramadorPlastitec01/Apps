package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DescripcionJpaController implements Serializable {

    public DescripcionJpaController() {
        emf = Persistence.createEntityManagerFactory("SolicitudesProyectosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaDescripcion() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_desc_c_descripcion`()");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaDescripcionFiltro(String filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_desc_c_descripcionFiltro`('" + filtro + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaDescripcionId(int id_descripcion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_desc_c_descripcion_id`('" + id_descripcion + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean registroDescripcion(String descripcion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_desc_r_descripcion`('" + descripcion + "')");
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

    public boolean modificarDescripcion(int id_descripcion, String descripcion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_desc_m_descripcion`('" + id_descripcion + "','" + descripcion + "')");
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
    
    public boolean modificarDescripcionEstado(int id_descripcion, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_desc_m_descripcion_estado`('" + id_descripcion + "','" + estado + "')");
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
}
