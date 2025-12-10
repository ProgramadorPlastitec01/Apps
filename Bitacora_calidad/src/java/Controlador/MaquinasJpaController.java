package Controlador;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class MaquinasJpaController {

    public MaquinasJpaController() {
        emf = Persistence.createEntityManagerFactory("Bitacora_cPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaMaquinaria() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mqn_c_todas_las_maquinas`()");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean estadoMaquina(int Id_maquina, int Estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mqn_m_estado`(" + Id_maquina + ", " + Estado + ")");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registarMaquina(String Nombre, int Ubicacion, int Estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mqn_r_maquina`('" + Nombre + "', '" + Ubicacion + "', '" + Estado + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarMaquina(int Id_maquina, String Nombre, int Ubicacion, int Estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mqn_m_maquina`('" + Id_maquina + "', '" + Nombre + "', '" + Ubicacion + "', '" + Estado + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public List consultarMauinaId(int Id_maquina) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mqn_c_una_maquina`('" + Id_maquina + "')");
            List Resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (Resultado.isEmpty()) {
                return null;
            } else {
                return Resultado;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarMauinaUbicacion(int Id_Ubicacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mqn_c_maquinas_por_ubicacion`('" + Id_Ubicacion + "')");
            List Resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (Resultado.isEmpty()) {
                return null;
            } else {
                return Resultado;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

}
