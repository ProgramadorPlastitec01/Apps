package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SolicitudJpaController implements Serializable {

    public SolicitudJpaController() {
        emf = Persistence.createEntityManagerFactory("SolicitudesProyectosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaSolicitudes(String tipo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes`('" + tipo + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudesEstado(String tipo, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_est`('" + tipo + "','" + estado + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudesFicha(String tipo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_ficha`('" + tipo + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudesFichaEstado(String tipo, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_ficha_estado`('" + tipo + "','" + estado + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultasSolicitudidfichas(int id_ficha) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_fichas_tecnicas`('" + id_ficha + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudId(int id_solicitud) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitud_id`('" + id_solicitud + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudesFiltro(String tipo, String filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_filtro`('" + tipo + "','" + filtro + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudesFiltroFicha(String tipo, String filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_filtro_ficha`('" + tipo + "','" + filtro + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudesRol(String rol) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_rol`('" + rol + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudesRolEstado(String rol, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_rol_estado`('" + rol + "', '" + estado + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudesRolFiltro(String filtro, String rol) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_rol_filtro`('" + filtro + "','" + rol + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudesRango(String fecha_inicial, String fecha_final, int estado, String busqueda) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solitudes_rango`('" + fecha_inicial + "','" + fecha_final + "','" + estado + "','" + busqueda + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudesRangoFiltro(String fecha_inicial, String fecha_final, int estado, String busqueda) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solitudes_rango`('" + fecha_inicial + "','" + fecha_final + "','" + estado + "','" + busqueda + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaSolicitudNumero(String numero_solicitud) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitud_numero`('" + numero_solicitud + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaContadorSolicitudes(String fecha_inicial, String fecha_final) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_contador_solicitudes`('" + fecha_inicial + "','" + fecha_final + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaUltimoNumeroFicha() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_ficha_contador`()");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List sumaCantidadIdRegistro(int ids, int idm) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("select COUNT(r.id_registro) ,SUM(r.cantidad),r.pieza  from registro r where r.id_solicitud = " + ids + " and r.id_maquina =  " + idm + " group by r.pieza");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaPendientesHerramental() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pdt_c_pendientes_herramental`()");
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

    public List consultaSolicitudIdPendiente(int id_pendiente) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitud_id_Pendiente`('" + id_pendiente + "')");
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

    public List consultaPendientesHerramental(int id_pendiente) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pdt_consulta_pendientes_herramental`('" + id_pendiente + "')");
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

    public List consultaSolicitudIdFichaT(int id_solicitud) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitud_id_ficha_proyecto`('" + id_solicitud + "')");
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

    public List consultaSolicitudIdFichaTHerramental(int id_solicitud) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitud_id_ficha`('" + id_solicitud + "')");
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

    public List consultaSolicitudfichaPendientes() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pdt_c_pendientes_fichas_tecnicas`()");
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

    public List consultaSolicitud_filtro_todos() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_filtro_all`()");
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

    public boolean registroSolicitud(int usuario, String numerosolicitud, String priori, int fich, int plano, String cantidad, String descripcion, String pieza) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_r_solicitud`('" + usuario + "','" + numerosolicitud + "','" + priori + "','" + fich + "','" + plano + "','" + cantidad + "','" + descripcion + "','" + pieza + "')");
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

    public boolean registroSolicitudFicha(int id_usuari, String solicitud, String prioridad, String descripcion, int id_pendiente) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_r_ficha_tecnica`('" + id_usuari + "','" + solicitud + "','" + prioridad + "','" + descripcion + "','" + id_pendiente + "','S')");
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

    public boolean modificarSolicitud(int id_solicitud, String prioridad, int id_plano, String pieza, String cantidad, String descripcion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_m_solicitud`('" + id_solicitud + "','" + prioridad + "','" + id_plano + "','" + pieza + "','" + cantidad + "','" + descripcion + "')");
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

    public boolean registroSolicitudIdPendiente(int usuario, String numerosolicitud, String priori, int fich, int plano, String cantidad, String descripcion, String pieza, int id_pendiente) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_r_solicitud_pendiente`('" + usuario + "','" + numerosolicitud + "','" + priori + "','" + fich + "','" + plano + "','" + cantidad + "','" + descripcion + "','" + pieza + "','" + id_pendiente + "')");
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

    public boolean registroLogSolicitud(int id_solicitud, String prioridad, int ficha, int id_plano, String pieza, String cantidad, String descripcion, String solicitante, String justificacion, String usuario_registro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_r_log_solicitud`('" + id_solicitud + "','" + prioridad + "','" + ficha + "','" + id_plano + "','" + pieza + "','" + cantidad + "','" + descripcion + "','" + solicitante + "','" + justificacion + "','" + usuario_registro + "')");
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

    public boolean modificarFicha(int id_solicitud, int ficha) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_m_ficha`('" + id_solicitud + "','" + ficha + "')");
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

    public boolean estadoSolicitud(int id_solicitud, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_m_estado`('" + id_solicitud + "','" + estado + "')");
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

    public boolean registrarRegistro(String usuarioR, int idsolicitud, int idmaquina, int idplano, String pieza, int cantidad, String descripcion, String horaI, String horaF) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_r_registro`('" + usuarioR + "','" + idsolicitud + "','" + idmaquina + "','" + idplano + "','" + pieza + "','" + cantidad + "','" + descripcion + "','" + horaI + "','" + horaF + "')");
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

    public boolean Registrar_solicitud_fichaTec(int id_usuario, String nro_sol, String prio, String descr) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_r_registrar_solicitud_fichaTec`('" + id_usuario + "','" + nro_sol + "','" + prio + "','" + descr + "')");
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

    public boolean Modificar_solicitudFicha(int id_sol, String nro_sol, String prio, String descr) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_modificarSolicitud_Id`('" + id_sol + "','" + nro_sol + "','" + prio + "','" + descr + "')");
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

    public List consultaRegistrosFiltro(String query) {
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
