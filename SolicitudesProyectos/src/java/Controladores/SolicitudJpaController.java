package Controladores;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class SolicitudJpaController implements Serializable {

    public SolicitudJpaController() {
        emf = Metodos.JPA.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaSolicitudes(String tipo) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes`(?)");
            q.setParameter(1, tipo);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudesEstado(String tipo, int estado) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_est`(?,?)");
            q.setParameter(1, tipo);
            q.setParameter(2, estado);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudesFicha(String tipo) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_ficha`(?)");
            q.setParameter(1, tipo);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudesFichaEstado(String tipo, int estado) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_ficha_estado`(?,?)");
            q.setParameter(1, tipo);
            q.setParameter(2, estado);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List ConsultasSolicitudidfichas(int id_ficha) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_fichas_tecnicas`(?)");
            q.setParameter(1, id_ficha);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudId(int id_solicitud) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitud_id`(?)");
            q.setParameter(1, id_solicitud);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudesFiltro(String tipo, String filtro) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_filtro`(?,?)");
            q.setParameter(1, tipo);
            q.setParameter(2, filtro);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudesFiltroFicha(String tipo, String filtro) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_filtro_ficha`(?,?)");
            q.setParameter(1, tipo);
            q.setParameter(2, filtro);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudesRol(String rol) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_rol`(?)");
            q.setParameter(1, rol);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudesRolEstado(String rol, int estado) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_rol_estado`(?,?)");
            q.setParameter(1, rol);
            q.setParameter(2, estado);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudesRolFiltro(String filtro, String rol) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitudes_rol_filtro`(?,?)");
            q.setParameter(1, filtro);
            q.setParameter(2, rol);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudesRango(String fecha_inicial, String fecha_final, int estado, String busqueda) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solitudes_rango`(?,?,?,?)");
            q.setParameter(1, fecha_inicial);
            q.setParameter(2, fecha_final);
            q.setParameter(3, estado);
            q.setParameter(4, busqueda);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudesRangoFiltro(String fecha_inicial, String fecha_final, int estado, String busqueda) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solitudes_rango`(?,?,?,?)");
            q.setParameter(1, fecha_inicial);
            q.setParameter(2, fecha_final);
            q.setParameter(3, estado);
            q.setParameter(4, busqueda);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudNumero(String numero_solicitud) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitud_numero`(?)");
            q.setParameter(1, numero_solicitud);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaContadorSolicitudes(String fecha_inicial, String fecha_final) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_contador_solicitudes`(?,?)");
            q.setParameter(1, fecha_inicial);
            q.setParameter(2, fecha_final);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaUltimoNumeroFicha() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List sumaCantidadIdRegistro(int ids, int idm) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaPendientesHerramental() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudIdPendiente(int id_pendiente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitud_id_Pendiente`(?)");
            q.setParameter(1, id_pendiente);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaPendientesHerramental(int id_pendiente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_pdt_consulta_pendientes_herramental`(?)");
            q.setParameter(1, id_pendiente);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudIdFichaT(int id_solicitud) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitud_id_ficha_proyecto`(?)");
            q.setParameter(1, id_solicitud);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudIdFichaTHerramental(int id_solicitud) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_c_solicitud_id_ficha`(?)");
            q.setParameter(1, id_solicitud);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitudfichaPendientes() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List consultaSolicitud_filtro_todos() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public boolean registroSolicitud(int usuario, String numerosolicitud, String priori, int fich, int plano, String cantidad, String descripcion, String pieza) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_r_solicitud`(?,?,?,?,?,?,?,?)");
            q.setParameter(1, usuario);
            q.setParameter(2, numerosolicitud);
            q.setParameter(3, priori);
            q.setParameter(4, fich);
            q.setParameter(5, plano);
            q.setParameter(6, cantidad);
            q.setParameter(7, descripcion);
            q.setParameter(8, pieza);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public boolean registroSolicitudFicha(int id_usuari, String solicitud, String prioridad, String descripcion, int id_pendiente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_r_ficha_tecnica`(?,?,?,?,?)");
            q.setParameter(1, id_usuari);
            q.setParameter(2, solicitud);
            q.setParameter(3, prioridad);
            q.setParameter(4, descripcion);
            q.setParameter(5, id_pendiente);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public boolean modificarSolicitud(int id_solicitud, String prioridad, int id_plano, String pieza, String cantidad, String descripcion) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_m_solicitud`(?,?,?,?,?,?)");
            q.setParameter(1, id_solicitud);
            q.setParameter(2, prioridad);
            q.setParameter(3, id_plano);
            q.setParameter(4, pieza);
            q.setParameter(5, cantidad);
            q.setParameter(6, descripcion);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public boolean registroSolicitudIdPendiente(int usuario, String numerosolicitud, String priori, int fich, int plano, String cantidad, String descripcion, String pieza, int id_pendiente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_r_solicitud_pendiente`(?,?,?,?,?,?,?,?,?)");
            q.setParameter(1, usuario);
            q.setParameter(2, numerosolicitud);
            q.setParameter(3, priori);
            q.setParameter(4, fich);
            q.setParameter(5, plano);
            q.setParameter(6, cantidad);
            q.setParameter(7, descripcion);
            q.setParameter(8, pieza);
            q.setParameter(9, id_pendiente);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public boolean registroLogSolicitud(int id_solicitud, String prioridad, int ficha, int id_plano, String pieza, String cantidad, String descripcion, String solicitante, String justificacion, String usuario_registro) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_r_log_solicitud`(?,?,?,?,?,?,?,?,?,?)");
            q.setParameter(1, id_solicitud);
            q.setParameter(2, prioridad);
            q.setParameter(3, ficha);
            q.setParameter(4, id_plano);
            q.setParameter(5, pieza);
            q.setParameter(6, cantidad);
            q.setParameter(7, descripcion);
            q.setParameter(8, solicitante);
            q.setParameter(9, justificacion);
            q.setParameter(10, usuario_registro);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public boolean modificarFicha(int id_solicitud, int ficha) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_m_ficha`(?,?)");
            q.setParameter(1, id_solicitud);
            q.setParameter(2, ficha);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public boolean estadoSolicitud(int id_solicitud, int estado) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_m_estado`(?,?)");
            q.setParameter(1, id_solicitud);
            q.setParameter(2, estado);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public boolean registrarRegistro(String usuarioR, int idsolicitud, int idmaquina, int idplano, String pieza, int cantidad, String descripcion, String horaI, String horaF, String numeroParte) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_rgt_r_registro`(?,?,?,?,?,?,?,?,?,?)");
            q.setParameter(1, usuarioR);
            q.setParameter(2, idsolicitud);
            q.setParameter(3, idmaquina);
            q.setParameter(4, idplano);
            q.setParameter(5, pieza);
            q.setParameter(6, cantidad);
            q.setParameter(7, descripcion);
            q.setParameter(8, horaI);
            q.setParameter(9, horaF);
            q.setParameter(10, numeroParte);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public boolean Registrar_solicitud_fichaTec(int id_usuario, String nro_sol, String prio, String descr) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_r_registrar_solicitud_fichaTec`(?,?,?,?)");
            q.setParameter(1, id_usuario);
            q.setParameter(2, nro_sol);
            q.setParameter(3, prio);
            q.setParameter(4, descr);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public boolean Modificar_solicitudFicha(int id_sol, String nro_sol, String prio, String descr) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_modificarSolicitud_Id`(?,?,?,?)");
            q.setParameter(1, id_sol);
            q.setParameter(2, nro_sol);
            q.setParameter(3, prio);
            q.setParameter(4, descr);
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
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    /**
     * Ejecuta una consulta nativa parametrizada del filtro dinamico R-PM-001.
     *
     * Los marcadores posicionales (?1, ?2, ...) ya vienen en la query y los
     * valores viajan en la lista aparte: nunca se concatenan strings del
     * usuario directamente en el SQL.
     *
     * @param query  SQL nativo con marcadores posicionales JPA
     * @param params lista de valores en el mismo orden que los marcadores
     * @return lista de resultados o null si esta vacia
     */
    public List consultaRegistrosFiltro(String query) {
        return consultaRegistrosFiltro(query, null);
    }

    public List consultaRegistrosFiltro(String query, java.util.List<Object> params) {
        if (query == null || query.isEmpty()) {
            return null;
        }
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery(query);
            // Enlazar cada parametro posicional: ?1, ?2, ...
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    q.setParameter(i + 1, params.get(i));
                }
            }
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
            Logger.getLogger(SolicitudJpaController.class.getName()).log(
                    Level.WARNING, "Error en filtro dinamico R-PM-001", e);
            return null;
        } finally {
            // Rollback ANTES de cerrar. Si la consulta fallo, la transaccion
            // queda abierta y EclipseLink descarta la conexion en vez de
            // devolverla al pool: medido, cada consulta fallida quemaba una
            // conexion para siempre hasta agotar max_connections. Cerrar en el
            // finally no alcanza; hay que deshacer la transaccion primero.
            // En el camino exitoso el commit ya cerro la transaccion y este
            // bloque no hace nada.
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    /**
     * Top 10 de planos por horas entregadas en el rango, mas una fila agregada
     * 'Varios' con id_plano = 0 que suma el resto. El agregado y los
     * porcentajes se arman en el procedimiento para que las 11 filas siempre
     * cuadren con el total del rango.
     *
     * @param fechaInicio fecha inicial del periodo (yyyy-MM-dd)
     * @param fechaFin fecha final del periodo (yyyy-MM-dd)
     * @param estado 1 = abiertas, 2 = cerradas, cualquier otro valor = todas
     * @param tipoPlano tipo de plano (Moldes, Electrodo, ...); vacio para todos
     * @param area rol del area solicitante; cadena vacia para todas
     * @return filas con id_plano, nombre_plano, numero_parte, tipo,
     *         descripcion, solicitudes, piezas, horas y porcentaje; o null
     * @see sp_slt_c_top_planos
     */
    public List consultaTopPlanos(String fechaInicio, String fechaFin, int estado, String tipoPlano, String area) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_top_planos`(?,?,?,?,?)");
            q.setParameter(1, fechaInicio);
            q.setParameter(2, fechaFin);
            q.setParameter(3, estado);
            q.setParameter(4, tipoPlano);
            q.setParameter(5, area);
            List resultado = q.getResultList();
            return resultado.isEmpty() ? null : resultado;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Detalle de piezas trabajadas de un plano en el rango. Alimenta el
     * drill-down del indicador al hacer clic en una porcion del pastel.
     *
     * @param fechaInicio fecha inicial del periodo (yyyy-MM-dd)
     * @param fechaFin fecha final del periodo (yyyy-MM-dd)
     * @param idPlano identificador del plano a detallar
     * @return filas con numero_parte, pieza, cantidad, solicitudes, tecnico,
     *         horas, porcentaje_del_plano y estado; o null
     * @see sp_slt_c_piezas_plano
     */
    public List consultaPiezasPlano(String fechaInicio, String fechaFin, int idPlano) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("CALL `sp_slt_c_piezas_plano`(?,?,?)");
            q.setParameter(1, fechaInicio);
            q.setParameter(2, fechaFin);
            q.setParameter(3, idPlano);
            List resultado = q.getResultList();
            return resultado.isEmpty() ? null : resultado;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

}
