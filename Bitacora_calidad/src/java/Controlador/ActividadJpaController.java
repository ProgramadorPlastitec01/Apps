package Controlador;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ActividadJpaController {

    public ActividadJpaController() {
        emf = Persistence.createEntityManagerFactory("Bitacora_cPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List TotalActividades() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("SELECT a.id_actividad, a.fecha_insercion, a.fecha, a.hora, a.asunto, a.urgentes, a.personal, a.observaciones,\n"
                    + "a.consecutivo, a.estado, a.aux2, u.nombre, u.apellido, u.id_usuario, a.ubicacion, ub.nombre\n"
                    + "FROM actividad a\n"
                    + "	INNER JOIN usuarios u\n"
                    + "	on a.fk_id_usuario = u.id_usuario\n"
                    + "	INNER JOIN ubicacion ub\n"
                    + "	ON a.ubicacion = ub.id_ubicacion\n"
                    + "	ORDER BY a.fecha desc\n"
                    + "LIMIT 150");
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

    public List consultarActividades() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_todas_actividades`()");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarActividadesRol(String rol) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_actividad_por_rol`('" + rol + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarConsecutivo() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_consecutivo`()");
            List resultado  = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
              if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean registrarActividad(String Fecha, String Hora, int Consecutivo, String Asunto, int Ubicacion, String Actividad, String Novedad, String Nota, String Tipo, int Id_usuario) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_r_actividad`('" + Fecha + "', '" + Hora + "','" + Consecutivo + "','" + Asunto + "','" + Ubicacion + "','" + Actividad + "', '" + Novedad + "','" + Nota + " ','" + Tipo + "','" + Id_usuario + "')");
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

    public List actividadId(int Id_Actividad) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_actividad_abierta_por_id`('" + Id_Actividad + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean modificarActividad(int Id_Actividad, String Asunto, int Ubicacion, String Actividad, String Novedades, String Notas) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_m_actividad`('" + Id_Actividad + "', '" + Asunto + "', '" + Ubicacion + "', '" + Actividad + "', '" + Novedades + "', '" + Notas + "')");
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

    public boolean estadoActividad(int Id_Actividad, String estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("update actividad set estado = '" + estado + "' where id_actividad = '" + Id_Actividad + "' ");
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

    public boolean revisarActividad(int Id_Actividad, String Informacion, String Estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_m_ingreso_revision`('" + Id_Actividad + "', '" + Informacion + "', '" + Estado + "')");
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

    public List filtroActividades(String Filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_campos_filtrada`('" + Filtro + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List filtroActividadesRol(String Filtro, String rol) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_campos_filtrada_rol`('" + Filtro + "','" + rol + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List filtroRango(String fecha_i, String fecha_f, String Filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_f_actividad`('" + fecha_i + "','" + fecha_f + "','" + Filtro + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List filtroRangoPorRol(String fecha_i, String fecha_f, String Filtro, String Roll) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_f_actividad_por_rol`('" + fecha_i + "', '" + fecha_f + "', '" + Filtro + "', '" + Roll + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
