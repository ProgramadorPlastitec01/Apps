package Controlador;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class NovedadesJpaController {

    public NovedadesJpaController() {
        emf = Persistence.createEntityManagerFactory("Bitacora_cPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultarNovendad(int Id_Actividad) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_nvd_c_novedad_por_id_actividad`('" + Id_Actividad + "')");
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

    public boolean registrarNovedad(int Id_Maquina, int Id_Actividad, String Novedad, String Producto) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_nvd_r_novedad`('" + Id_Maquina + "', '" + Id_Actividad + "', '" + Novedad + "', '" + Producto + "')");
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

    public List consultarNovendadId(int Id_Novedad) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_nvd_c_una_novedad_por_id`('" + Id_Novedad + "')");
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

    public boolean modificarNovedad(int Id_Novedad, int Id_Maquina, String Producto, String Novedad) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_nvd_m_novedad_maquina`('" + Id_Novedad + "', '" + Id_Maquina + "', '" + Producto + "', '" + Novedad + "')");
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

    public List consultaFiltroNovedades(int Id_Maquina, String Date_I, String Hora_I, String Date_F, String Hora_F) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_nvd_c_novedades_fechas`('" + Id_Maquina + "', '" + Date_I + "', '" + Hora_I + "', '" + Date_F + "', '" + Hora_F + "')");
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
}
