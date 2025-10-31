package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RolJpaController implements Serializable {

    public RolJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultarRolActivo() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rol_c_consulta_rol_activo`()");
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
    public List ConsultarRol() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rol_c_consultar_rol`()");
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

    public List Consultar_rol(int id_rol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rol_c_consultar_rol_id`('" + id_rol + "')");
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

    public boolean RegistrarRol(String nombre, String usuario_registro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rol_r_registrar_rol`('" + nombre + "','" + usuario_registro + "')");
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

    public boolean ModificarRol(int id_rol, String nombre) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rol_m_modificar_rol`('" + id_rol + "','" + nombre + "')");
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

    public boolean CambiarEstadoRol(int id_rol, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rol_m_cambiar_estado_rol`('" + id_rol + "','" + estado + "')");
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
