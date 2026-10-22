package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class MaquinaJpaController implements Serializable {

    public MaquinaJpaController() {
        emf = Metodos.JPA.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaMaquinas() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_mqn_c_maquinas`()");
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

    
    public List consultaMaquinaId(int id_maquina) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_mqn_c_maquina_id`(?)");
            q.setParameter(1, id_maquina);
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

    public boolean registroMaquina(String maquina) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_mqn_r_maquina`(?)");
            q.setParameter(1, maquina);
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

    public boolean modificarMaquina(int id_maquina, String nombre) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_mqn_m_maquina`(?,?)");
            q.setParameter(1, id_maquina);
            q.setParameter(2, nombre);
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
    
    public boolean modificarMaquinaEstado(int id_maquina, int estado) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_mqn_m_maquina_estado`(?,?)");
            q.setParameter(1, id_maquina);
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

}
