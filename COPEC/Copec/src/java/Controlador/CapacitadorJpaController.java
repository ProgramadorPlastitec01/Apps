package Controlador;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CapacitadorJpaController implements Serializable {

    public CapacitadorJpaController() {
        emf = Persistence.createEntityManagerFactory("CopecPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List cpr_t_login(String usuario, String contrasena) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cpr_t_login`('" + usuario + "','" + contrasena + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == null) {
                return null;
            } else {
                return resultado;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean cpr_r_capacitador(String nombre, String apellido, String documento, String codigo, String usuario, String contrasena, int id_area) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cpr_r_capacitador`('" + nombre + "', '" + apellido + "', '" + documento + "', '" + codigo + "', '" + usuario + "', '" + contrasena + "', '" + id_area + "')");
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

    public List cpr_c_capacitador(int id_capacitador) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpr_c_capacitador`('" + id_capacitador + "')");
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

    public List cpr_c_a_capacitador() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpr_c_a_capacitador`()");
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

    public List cpc_f_capacitacion(String filtro, int id_capacitador) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpc_f_capacitacion`('" + filtro + "','" + id_capacitador + "')");
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

    public boolean cpr_m_capacitador(int id_capacitador, String nombre, String apellido, String codigo, String usuario, String contrasena) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cpr_m_capacitador`('" + id_capacitador + "','" + nombre + "','" + apellido + "','" + codigo + "','" + usuario + "','" + contrasena + "')");
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

    public boolean cpr_m_estado(int id_capacitador, int estado) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpr_m_estado`('" + id_capacitador + "','" + estado + "')");
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

    public List cpr_c_a_capacitador_filtro(String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpr_c_a_capacitador_filtro`('" + filtro + "')");
            List resultados = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
    
    public boolean CambiarPass(int isa, String ctn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update capacitador set contrasena= '" + ctn + "' where id_capacitador=" + isa + "");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean RestablecerPass(int isa) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update capacitador set contrasena=YEAR(CURDATE()) where id_capacitador="+isa+"");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
