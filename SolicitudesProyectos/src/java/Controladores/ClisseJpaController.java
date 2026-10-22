package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.Serializable;

public class ClisseJpaController implements Serializable {

    public ClisseJpaController() {
        emf = Metodos.JPA.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Consulta_Clisse() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_cls_c_consultar_clisse`()");
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

    public List Consulta_Clisse_Id(int id_clisse) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_cls_c_consultar_clisse_id`(?)");
            q.setParameter(1, id_clisse);
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

    public List Detalle_Clisse(int id_clisse) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_dcl_c_detalle_clisse_id`(?)");
            q.setParameter(1, id_clisse);
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

    public List Consultar_parametro(String id_clisse) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_prm_c_consultarParametrosxCategoria`(?)");
            q.setParameter(1, id_clisse);
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

    public List Consultar_detalleIdClisse(int id_clisse) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_dcl_c_detalle_clisse_id`(?)");
            q.setParameter(1, id_clisse);
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

    public List Consultar_detalleId(int id_detalle) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_dcl_c_detalle_id`(?)");
            q.setParameter(1, id_detalle);
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

    public List Consultar_detalleIdxFila(int id_clisse, int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `Sp_dcl_c_consulta_detalle_id`(?,?)");
            q.setParameter(1, id_clisse);
            q.setParameter(2, id);
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

    public List Consultar_Diff_valores(int id_clisse) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `Sp_dcl_c_consultarDiferenciaValores`(?)");
            q.setParameter(1, id_clisse);
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

    public List Consulta_MinMax_Letra(int id_clisse, String letra) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            String query = "SELECT 1, "
                    + "    COALESCE( "
                    + "        (SELECT MIN(identificacion) "
                    + "         FROM detalle_clisse d"
                    + "         WHERE id_clisse = " + id_clisse + " AND d." + letra + " IS NOT NULL), 0) AS 'MIN',\n"
                    + "    COALESCE(\n"
                    + "        (SELECT MAX(identificacion)  "
                    + "         FROM detalle_clisse d "
                    + "         WHERE id_clisse = " + id_clisse + " AND d." + letra + " IS NOT NULL), 0) AS 'MAX'";
            Query q = em.createNativeQuery(query);
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

    public boolean Registro_Clisse(String fecha, String codigo, String producto, String usuario_registro, String Observacion) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_cls_r_registrar_clisse`(?,?,?,?,?)");
            q.setParameter(1, fecha);
            q.setParameter(2, codigo);
            q.setParameter(3, producto);
            q.setParameter(4, usuario_registro);
            q.setParameter(5, Observacion);
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

    public boolean Modificar_Clisse(int id_clisse, String fecha, String codigo, String producto, String observacion) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_cls_m_modificar_clisse`(?,?,?,?,?)");
            q.setParameter(1, id_clisse);
            q.setParameter(2, fecha);
            q.setParameter(3, codigo);
            q.setParameter(4, producto);
            q.setParameter(5, observacion);
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

    public boolean Registrar_Control(int id_clisse, int id, int estadoD, String a1, String responsable) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_dcl_r_registrar_control`(?,?,?,?,?)");
            q.setParameter(1, id_clisse);
            q.setParameter(2, id);
            q.setParameter(3, estadoD);
            q.setParameter(4, a1);
            q.setParameter(5, responsable);
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

    public boolean Actualizar_Clisse_Control(int id_clisse, String ejecutor, String observacion, int estadoV) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_cls_m_actualizar_control`(?,?,?,?)");
            q.setParameter(1, id_clisse);
            q.setParameter(2, ejecutor);
            q.setParameter(3, observacion);
            q.setParameter(4, estadoV);
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

    public boolean Registrar_Control_cuaretena(int id_detalle, String ca, String cb, String cc, String cd, int estadoV) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_dcl_m_actualizar_control_cuarentena`(?,?,?,?,?,?)");
            q.setParameter(1, id_detalle);
            q.setParameter(2, ca);
            q.setParameter(3, cb);
            q.setParameter(4, cc);
            q.setParameter(5, cd);
            q.setParameter(6, estadoV);
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

    public boolean Actualizar_Estado(int id_clisse, int estadoV, int estado, String observacion) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_cls_m_actualizar_estado`(?,?,?,?)");
            q.setParameter(1, id_clisse);
            q.setParameter(2, estadoV);
            q.setParameter(3, estado);
            q.setParameter(4, observacion);
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

    public boolean Verificar_registro(int id_clisse, String verificador, int estado) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_cls_a_firmaV_estado_clisse`(?,?,?)");
            q.setParameter(1, id_clisse);
            q.setParameter(2, verificador);
            q.setParameter(3, estado);
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
    public boolean ActualizarEstadoNoCumple(int id_clisse) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_cls_m_actualizarEstadoVerificacionNC`(?)");
            q.setParameter(1, id_clisse);
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

    public boolean ActualizarValorXLetra(int id_detalle, String letra, String valor) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            String query = "UPDATE detalle_clisse d "
                    + "SET d." + letra + " = " + valor + ""
                    + "WHERE d.id_detalle = " + id_detalle + " ";
            Query q = em.createNativeQuery(query);
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
