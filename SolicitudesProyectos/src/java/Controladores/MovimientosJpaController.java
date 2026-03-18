package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MovimientosJpaController implements Serializable {

    public MovimientosJpaController() {
        emf = Persistence.createEntityManagerFactory("SolicitudesProyectosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaMovimientos(int id_solicitud) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mvm_c_movimientos`('" + id_solicitud + "')");
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

    public boolean registroMovimiento(int id_solicitud, String fecha, String pieza, String tipo, String descripcion, String usuario_registro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mvm_r_movimiento`('" + id_solicitud + "','" + fecha + "','" + pieza + "','" + tipo + "','" + descripcion + "','" + usuario_registro + "')");
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

    public boolean registroEntrega(int id_movimiento, String fecha, int id_defecto, String tipo, String descripcion, String usuario_registro, String fechaF, String encargado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mvm_r_entrega`('" + id_movimiento + "','" + fecha + "','" + id_defecto + "','" + tipo + "','" + descripcion + "','" + usuario_registro + "','" + fechaF + "','" + encargado + "')");
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

    public List consultaPiezasEntregadas(int id_solicitud) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mvm_c_piezas_entregadas`('" + id_solicitud + "')");
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

    public boolean registroDevolucion(int id_movimiento, String fecha, int id_defecto, String tipo, String descrip, String ejecutor) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mvm_r_devolucion`('" + id_movimiento + "','" + fecha + "','" + id_defecto + "','" + tipo + "','" + descrip + "','" + ejecutor + "')");
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

    public List consultaMovimientoSolicitud(String solicitud, String pieza) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mvm_c_movimiento_solicitud`('" + solicitud + "','" + pieza + "')");
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

    public List consultaMovimientoSolicitudId(int solicitud, String pieza) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mvm_c_movimiento_solicitud_id`('" + solicitud + "','" + pieza + "')");
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

    public List consultaMovimientoId(int id_movimiento) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mvm_c_movimiento_id`('" + id_movimiento + "')");
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
    
    public List consultaMovimiento_ficha_id(int id_movimiento) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mvm_c_movimiento_ficha_id`('" + id_movimiento + "')");
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
