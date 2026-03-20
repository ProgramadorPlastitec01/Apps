package Controlador;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CapacitacionJpaController {

    public CapacitacionJpaController() {
        emf = Persistence.createEntityManagerFactory("CopecPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean cpc_r_capacitacion(String nombre, String contenido, String fch_inicio, String hr_inicio, int id_capacitador, int id_cargo_capacitar, int id_tipo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cpc_r_capacitacion`('" + nombre + "', '" + contenido + "', '" + fch_inicio + "', '" + hr_inicio + "', '" + id_capacitador + "','" + id_cargo_capacitar + "','" + id_tipo + "')");
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

    public boolean cpc_m_capacitacion(String nombre, String contenido, String fch_inicio, String hr_inicio, int id_capacitacion, int tipo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cpc_m_capacitacion`('" + nombre + "', '" + contenido + "', '" + fch_inicio + "', '" + hr_inicio + "', '" + id_capacitacion + "','" + tipo + "')");
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

    public boolean RegistrarPruebasProgramacion(int id_capacitacion, String pruebas) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cpc_r_pruebas_programacion`('" + id_capacitacion + "', '" + pruebas + "')");
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

    public List cpc_c_capacitacion(int id_area) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpc_c_capacitacion`('" + id_area + "')");
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

    public List cpc_c_pruebas(int id_area) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpc_c_pruebas`('" + id_area + "')");
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

    public List cpc_t_id_capacitacion(int id_capacitacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpc_t_id_capacitacion`('" + id_capacitacion + "')");
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

    public boolean cpc_m_cerrar(int id_capacitacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cpc_m_cerrar`('" + id_capacitacion + "')");
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

    public List cpc_c_capacitacion_filtro(int id_area, String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpc_c_capacitacion_filtro`('" + id_area + "','" + filtro + "')");
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

    public List cpc_c_pruebas_filtro(int id_area, String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpc_c_pruebas_filtro`('" + id_area + "','" + filtro + "')");
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

    public List cpc_c_capacitacion_personal(int id_capacitacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpc_c_capacitacion_personal`('" + id_capacitacion + "')");
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

    public List cpc_c_prueba_personal(int id_capacitacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cpc_c_prueba_personal`('" + id_capacitacion + "')");
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

    public boolean cpc_i_capacitacion_documentos(String lst_personal) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cpc_i_capacitacion_documentos`('" + lst_personal + "')");
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

    public boolean cpc_u_capacitacion_documentos(int id_capacitacion, String lst_personal) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cpc_u_capacitacion_documentos`('" + id_capacitacion + "','" + lst_personal + "')");
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

    public boolean cpc_u_capacitacion_personal(int id_capacitacion, String lst_personal) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cpc_u_capacitacion_personal`('" + id_capacitacion + "','" + lst_personal + "')");
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

    public boolean cpc_m_contenido(String contenido, int id_capacitacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cpc_m_contenido`('" + contenido + "','" + id_capacitacion + "')");
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
