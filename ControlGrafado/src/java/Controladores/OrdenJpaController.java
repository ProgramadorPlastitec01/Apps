package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OrdenJpaController implements Serializable {

    public OrdenJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlGrafadoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean registroOrden(String orden, String descripcion, int id_ficha) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_r_orden`('" + orden + "', '" + descripcion + "', '" + id_ficha + "')");
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

    public List consultaLoteEnsambleSeguimiento(int orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_o_seguimiento`('" + orden + "')");
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

    public List consultaLoteEnsambleSeguimientoPF(int orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_lote_sin_prueba_seguimiento`('" + orden + "')");
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

    public List consultaLoteEnsambleEstados(int iop, String les) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_t_estados_lote`('" + iop + "','" + les + "')");
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

    public List consultaLoteEnsamble(int orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_lote_o`('" + orden + "')");
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

    public List consultaLoteEnsambleConsecutivo(String idC) {
        if (idC.equals("[0]")) {
            return null;
        } else {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            String queryLE = "SELECT c.id_dimensional_c,c.id_orden,c.lote_ensamble,c.consecutivo FROM control_dms_c c WHERE c.id_dimensional_c in (" + idC + ") AND c.seguimiento = 0 ORDER BY c.consecutivo DESC;";
            try {
                Query q = em.createNativeQuery(queryLE);
//            Query q = em.createNativeQuery("CALL `sp_cdc_c_lote_sin_prueba`('" + orden + "','" + idC + "')");
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

    public List consultaLoteEnsambleConsecutivoSeguimiento(String idCS) {
        if (idCS.equals("[0]")) {
            return null;
        } else {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            String queryLES = "SELECT c.id_dimensional_c,c.id_orden,c.lote_ensamble,c.consecutivo FROM control_dms_c c WHERE c.id_dimensional_c IN (" + idCS + ") AND c.seguimiento = 1 ORDER BY c.consecutivo DESC;";
            try {
                Query q = em.createNativeQuery(queryLES);
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

    public List consultaOrdenes() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_c_orden`()");
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

    public List consultaOrdenesIdFicha(int idFicha) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_c_orden_idficha`('" + idFicha + "')");
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

    public List consultaLotesIdOrdenes(String sentencia) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("" + sentencia + "");
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

    public List consultaOrdenId(int id_orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_c_orden_id`(" + id_orden + ")");
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

    public List consultaOrdenesFiltro(String filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_f_orden`('" + filtro + "')");
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

    public List ListaFichaTecnica() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fch_t_datos`()");
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

    public boolean estadoOrden(int id_orden, String estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_m_estado`('" + id_orden + "', '" + estado + "')");
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

    public boolean registroLogDimensional(int id_orden, String lote_ensamble, String parametro, String condicion, String valor, String justificacion, String usuario_registro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prmt_r_log`('" + id_orden + "', '" + lote_ensamble + "', '" + parametro + "', '" + condicion + "', '" + valor + "', '" + justificacion + "', '" + usuario_registro + "')");
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

    public int modificarDimensional(int id_orden, String lote_ensamble, String parametro, String condicion, String valor) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("UPDATE control_dms_d d INNER JOIN control_dms_c c ON c.id_dimensional_c = d.id_dimensional_c SET d." + parametro + " = " + valor + " WHERE c.id_orden = " + id_orden + " AND c.lote_ensamble = '" + lote_ensamble + "' AND d." + parametro + " " + condicion + " " + valor + " ");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado > 0) {
                return resultado;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

}
