package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FichaTecnicaJpaController implements Serializable {

    public FichaTecnicaJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlGrafadoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean registroFichaTecnica(String codigo_ficha, int version, String codigo_producto, String nombre_producto, double y1, double x1, double y2, double x2, double x3, double desvMx_y1, double desvMn_y1, double desvMx_x1, double desvMn_x1, double desvMx_y2, double desvMn_y2, double desvMx_x2, double desvMn_x2, double desvMx_x3, double desvMn_x3, int id_cliente, double atr, double atrMx, double atrMn, String tipo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fch_r_ficha`('" + codigo_ficha + "','" + version + "','" + codigo_producto + "','" + nombre_producto + "','" + y1 + "','" + x1 + "','" + y2 + "','" + x2 + "','" + x3 + "','" + desvMx_y1 + "','" + desvMn_y1 + "','" + desvMx_x1 + "','" + desvMn_x1 + "','" + desvMx_y2 + "','" + desvMn_y2 + "','" + desvMx_x2 + "','" + desvMn_x2 + "','" + desvMx_x3 + "','" + desvMn_x3 + "','" + id_cliente + "','" + atr + "','" + atrMx + "','" + atrMn + "','" + tipo + "')");
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

    public boolean modificarFichaTecnica(int id_ficha, String codigo_producto, String nombre_producto, String codigo_ficha, String version, int id_cliente) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fch_m_ficha`('" + id_ficha + "','" + codigo_producto + "','" + nombre_producto + "','" + codigo_ficha + "','" + version + "','" + id_cliente + "')");
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

    public List consultaFichasTecnicas() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fch_c_ficha`()");
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

    public List consultaFichasTecnicasFiltro(String filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fch_c_ficha_filtro`('" + filtro + "')");
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

    public boolean estadoFichaTecnica(int id_ficha, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fch_m_estado`('" + id_ficha + "','" + estado + "')");
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

    public List consultaFichaTecnicaId(int id_ficha) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fch_t_id_ficha`('" + id_ficha + "')");
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

    public List consultaUltVersionficha(String codigoFicha) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        String queryCUVF = "select f.id_ficha,f.codigo_ficha, max(f.version) from ficha_tecnica f where f.codigo_ficha = '" + codigoFicha + "'";
        try {
            Query q = em.createNativeQuery(queryCUVF);
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

    public List consultaFichaTecnica(String ficha) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fch_t_ficha`('" + ficha + "')");
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

}
