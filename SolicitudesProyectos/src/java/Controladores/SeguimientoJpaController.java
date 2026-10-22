package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class SeguimientoJpaController implements Serializable {

    public SeguimientoJpaController() {
        emf = Metodos.JPA.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaSeguimientos(int id_solicitud) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_sgm_c_seguimientos`(?)");
            q.setParameter(1, id_solicitud);
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

    public boolean solucionPendienteHerramental(int id_pendiente, String fecha, String solucion) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_pdt_m_pendiente_herramental_solucion`(?,?,?)");
            q.setParameter(1, id_pendiente);
            q.setParameter(2, fecha);
            q.setParameter(3, solucion);
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

    public boolean estadoPendienteHerramental(int id_pendiente, int estado) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_pdt_m_pendiente_herramental_estado`(?,?)");
            q.setParameter(1, id_pendiente);
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
    
    public boolean estadoHerramental(int id_herramental, int estado) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_pdt_herramental_estado`(?,?)");
            q.setParameter(1, id_herramental);
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
    public boolean estadoFichaTecnica(int id_ficha, int estado) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_m_cambio_estado_Ficha`(?,?)");
            q.setParameter(1, id_ficha);
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
    
    public boolean Actualizar_estado_solicitud(int id_solic) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_slt_m_estado_solicitud_100`(?)");
            q.setParameter(1, id_solic);
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

    public List consultaPendienteId(int id_pendiente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_pdt_c_pendiente_id`(?)");
            q.setParameter(1, id_pendiente);
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
    public List consultaPendientesFichasHerramental(int id_pendiente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_c_consulta_pendientes_ficha`(?)");
            q.setParameter(1, id_pendiente);
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

}
