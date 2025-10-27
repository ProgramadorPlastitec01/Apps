package Controladoras;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ActividadJpaController {

    public ActividadJpaController() {
        emf = Persistence.createEntityManagerFactory("BitacoraPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultaAreas() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ara_c_consulta_area`()");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean RegistroActividad(int idUsuario, int consecutivo, String usuarioR, String turno, String nomAdjunto, int Tnovedad, int contCampos, String NomCampos, String campo1, String campo2, String campo3, String campo4, String campo5, String campo6, String campo7, String campo8, String campo9) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_r_registro_actividad`('" + idUsuario + "','" + consecutivo + "','" + usuarioR + "','" + turno + "','" + nomAdjunto + "','" + Tnovedad + "','" + contCampos + "','" + NomCampos + "','" + campo1 + "','" + campo2 + "','" + campo3 + "','" + campo4 + "','" + campo5 + "','" + campo6 + "','" + campo7 + "','" + campo8 + "','" + campo9 + "')");
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

    public List ConsultaConsecutivoPorIdArea(int idArea) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_consulta_consecutivo_por_area`('" + idArea + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultaContadorCamposPorIdCargo(int idCargo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_count_campos_actividad`('" + idCargo + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultaActividadPorIdCargo(int idCargo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_actividades_por_idCargo`('" + idCargo + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultaActividadPorIdUsuario(int idUsuario) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_actividades_por_idUsuario`('" + idUsuario + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultaActividadPorIdActividad(int idActividad) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_actividades_po_idActividad`('" + idActividad + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public List ConsultaActividadPorIdMaquina(int idMaquina) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_actividades_por_idmaquina`('" + idMaquina + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean ModificarActividad(int idActividad, String usuarioR, String fecha, String hora, String turno, String nomAdjunto, int tnovedad, String campo1, String campo2, String campo3, String campo4, String campo5, String campo6, String campo7, String campo8, String campo9) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_m_modificar_actividad`('" + idActividad + "','" + usuarioR + "','" + fecha + "','" + hora + "','" + turno + "','" + nomAdjunto + "','" + tnovedad + "','" + campo1 + "','" + campo2 + "','" + campo3 + "','" + campo4 + "','" + campo5 + "','" + campo6 + "','" + campo7 + "','" + campo8 + "','" + campo9 + "')");
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

    public boolean FinalizarActividad(int idActividad, int cierre) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_m_finalizar_actividad`('" + idActividad + "','" + cierre + "')");
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

    public boolean RevisarActividad(int idActividad, String revisar) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_m_revisar_actividad`('" + idActividad + "','" + revisar + "')");
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

    public List ConsultaActividadPorfiltro(int idCargo, String busqueda) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_consulta_actividades_por_filtro`('" + idCargo + "','" + busqueda + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultaActividadPoridAreafiltro(int idArea, String busqueda) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_consulta_actividades_filtro_por_idArea`('" + idArea + "','" + busqueda + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultaActividadPorFechas(int idCargo, String FechaI, String HoraI, String FechaF, String HoraF, String Busqueda) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_consulta_actividades_por_fechas`('" + idCargo + "','" + FechaI + "','" + HoraI + "','" + FechaF + "','" + HoraF + "','" + Busqueda + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultaActividadPorIdArea(int idArea) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_consulta_actividades_por_idArea`('" + idArea + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
public List ConsultaActividadPorFechasIdArea(int idArea, String FechaI, String HoraI, String FechaF, String HoraF, String Busqueda) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_atd_c_consulta_actividades_por_fechas_idArea`('" + idArea+ "','" + FechaI + "','" + HoraI + "','" + FechaF + "','" + HoraF + "','" + Busqueda + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public List consultaRequisicionesFiltro(String query) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("" + query + "");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultados.isEmpty()) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
