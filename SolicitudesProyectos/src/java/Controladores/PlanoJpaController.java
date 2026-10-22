package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class PlanoJpaController implements Serializable {

    public PlanoJpaController() {
        emf = Metodos.JPA.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaPlanos() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_pno_c_plano`()");
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

    public List consultaPlanosFiltro(String bus) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_pno_c_plano_filtro`(?)");
            q.setParameter(1, bus);
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

    public List consultaPlanoId(int id_plano) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_pno_c_plano_id`(?)");
            q.setParameter(1, id_plano);
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

    public List consultaItemsIdplano(int id_plano) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_clc_c_verificacion_plano_id`(?)");
            q.setParameter(1, id_plano);
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

    public List consultaItemsVerificadosIdPlano(int id_plano) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_clc_c_verificar_etd`(?)");
            q.setParameter(1, id_plano);
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

    public List consultaTipoPlano() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_pno_c_tipo_plano`()");
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

    /**
     * Da de alta un plano.
     *
     * @param tipo tipo de plano (Moldes, Electrodo, ...)
     * @param plano nombre del plano
     * @param fecha fecha de ingreso (yyyy-MM-dd)
     * @param numeroParte No. de parte del R-PM-001; puede ir vacio
     * @return true si se inserto la fila
     * @see sp_pno_r_plano
     */
    public boolean registroPlano(String tipo, String plano, String fecha, String numeroParte) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            // setParameter y no concatenacion: el No. de parte es texto libre
            // que escribe el usuario y no puede terminar dentro del CALL.
            Query q = em.createNativeQuery("CALL `sp_pno_r_plano`(?,?,?,?)");
            q.setParameter(1, tipo);
            q.setParameter(2, plano);
            q.setParameter(3, fecha);
            q.setParameter(4, numeroParte);
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
     * Modifica un plano.
     *
     * @param id_plano identificador del plano
     * @param tipo tipo de plano
     * @param plano nombre del plano
     * @param numeroParte No. de parte del R-PM-001; vacio lo deja en NULL
     * @return true si se actualizo la fila
     * @see sp_pno_m_plano
     */
    public boolean modificarPlano(int id_plano, String tipo, String plano, String numeroParte, String usuario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_pno_m_plano`(?,?,?,?,?)");
            q.setParameter(1, id_plano);
            q.setParameter(2, tipo);
            q.setParameter(3, plano);
            q.setParameter(4, numeroParte);
            q.setParameter(5, usuario);
            q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            // El SP audita (INSERT en log_plano_version) y ademas actualiza el
            // plano, asi que executeUpdate ya no devuelve un unico contador
            // confiable. Sin excepcion, el UPDATE se aplico: se devuelve true.
            return true;
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

    public boolean registroCalificacionPlano(int idPlano, int idItems, String nomP, String desc, String medida, String Cumple, String Aplica) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_clc_r_verificar_plano`(?,?,?,?,?,?,?)");
            q.setParameter(1, idPlano);
            q.setParameter(2, idItems);
            q.setParameter(3, nomP);
            q.setParameter(4, desc);
            q.setParameter(5, medida);
            q.setParameter(6, Cumple);
            q.setParameter(7, Aplica);
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

    public List consultaPlanoNombre(String plano) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("CALL `sp_pno_c_plano_nombre`(?)");
            q.setParameter(1, plano);
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

    /**
     * Planos disponibles para el combo de filtro del indicador y del informe.
     *
     * @param tipo tipo de plano (Moldes, Electrodo, Platinas, Grafadoras,
     *             Otros...); cadena vacia para traer todos
     * @return filas con id_plano, nombre_plano, numero_parte y tipo; o null
     * @see sp_pln_c_planos_tipo
     */
    public List consultaPlanosTipo(String tipo) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("CALL `sp_pln_c_planos_tipo`(?)");
            q.setParameter(1, tipo);
            List resultados = q.getResultList();
            return resultados.isEmpty() ? null : resultados;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }


    /**
     * Obtiene el siguiente consecutivo para planos de tipo 'Generales Varios'
     * bajo la convencion 'GV-XXX' (ej. si existe GV-001 devuelve '002').
     *
     * @return String con el numero formateado a 3 digitos (ej. '001', '002')
     */
    public String siguienteConsecutivoGV() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery(
                "SELECT COALESCE(MAX(CAST(SUBSTRING(nombre_plano, 4) AS UNSIGNED)), 0) + 1 "
                + "FROM plano WHERE nombre_plano REGEXP '^GV-[0-9]+$'"
            );
            Number res = (Number) q.getSingleResult();
            int sig = (res != null) ? res.intValue() : 1;
            return String.format("%03d", sig);
        } catch (Exception e) {
            return "001";
        } finally {
            // Regla EclipseLink: rollback antes de cerrar para proteger el pool de conexiones
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

}
