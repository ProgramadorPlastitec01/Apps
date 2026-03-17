package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TipoInstrumentoJpaController {

    public TipoInstrumentoJpaController() {
        emf = Persistence.createEntityManagerFactory("PVMPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaTipoInstrumentos() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpi_c_tipo_intrumentos`()");
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

    public List consultaTipoInstrumentoId(int id_tipoI) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpi_c_tipo_intrumento_id`('" + id_tipoI + "')");
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

    public List consultaTipoInstrumentoFiltro(String filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpi_c_tipo_intrumentos_filtro`('" + filtro + "')");
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

    public List consultaVerificaciones() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ptl_c_verificaciones`()");
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

    public boolean registroTipoIntrumentos(int id_area, String tipo, int frecuensiaInt, int toleranciaInt, int frecuensiaExt, int toleranciaExt, String usuario_registro, int tipoFrecuencia, int grafica, int plantilla) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpi_r_tipo_intrumentos`('" + id_area + "','" + tipo + "','" + frecuensiaInt + "','" + toleranciaInt + "','" + frecuensiaExt + "','" + toleranciaExt + "','" + usuario_registro + "','" + tipoFrecuencia + "','" + grafica + "','" + plantilla + "')");
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

    public boolean modificarTipoIntrumento(int id_tipoI, int id_area, String tipo, int frecuensiaInt, int toleranciaInt, int frecuensiaExt, int toleranciaExt, int tipoFrecuencia, int grafica, int plantilla) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpi_m_tipo_intrumentos`('" + id_tipoI + "','" + id_area + "','" + tipo + "','" + frecuensiaInt + "','" + toleranciaInt + "','" + frecuensiaExt + "','" + toleranciaExt + "','" + tipoFrecuencia + "','" + grafica + "','" + plantilla + "')");
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

    public boolean modificarTipoIntrumentoEstado(int id_tipoI, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpi_m_tipo_intrumento_estado`('" + id_tipoI + "','" + estado + "')");
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
