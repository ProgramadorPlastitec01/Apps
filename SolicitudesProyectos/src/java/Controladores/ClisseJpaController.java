package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.Serializable;

public class ClisseJpaController implements Serializable {

    public ClisseJpaController() {
        emf = Persistence.createEntityManagerFactory("SolicitudesProyectosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Consulta_Clisse() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
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
        }
    }

    public List Consulta_Clisse_Id(int id_clisse) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cls_c_consultar_clisse_id`(" + id_clisse + ")");
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

    public List Detalle_Clisse(int id_clisse) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dcl_c_detalle_clisse_id`('" + id_clisse + "')");
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

    public List Consultar_parametro(String id_clisse) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_consultarParametrosxCategoria`('" + id_clisse + "')");
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

    public List Consultar_detalleIdClisse(int id_clisse) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dcl_c_detalle_clisse_id`('" + id_clisse + "')");
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

    public List Consultar_detalleId(int id_detalle) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dcl_c_detalle_id`('" + id_detalle + "')");
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

    public List Consultar_detalleIdxFila(int id_clisse, int id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_dcl_c_consulta_detalle_id`('" + id_clisse + "','" + id + "')");
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

    public List Consultar_Diff_valores(int id_clisse) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_dcl_c_consultarDiferenciaValores`('" + id_clisse + "')");
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

    public List Consulta_MinMax_Letra(int id_clisse, String letra) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
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
        }
    }

    public boolean Registro_Clisse(String fecha, String codigo, String producto, String usuario_registro, String Observacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cls_r_registrar_clisse`('" + fecha + "','" + codigo + "','" + producto + "','" + usuario_registro + "','" + Observacion + "')");
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

    public boolean Modificar_Clisse(int id_clisse, String fecha, String codigo, String producto, String observacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cls_m_modificar_clisse`('" + id_clisse + "','" + fecha + "','" + codigo + "','" + producto + "','" + observacion + "')");
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

    public boolean Registrar_Control(int id_clisse, int id, int estadoD, String a1, String responsable) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dcl_r_registrar_control`('" + id_clisse + "','" + id + "','" + estadoD + "','" + a1 + "','" + responsable + "')");
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

    public boolean Actualizar_Clisse_Control(int id_clisse, String ejecutor, String observacion, int estadoV) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cls_m_actualizar_control`('" + id_clisse + "','" + ejecutor + "','" + observacion + "','" + estadoV + "')");
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

    public boolean Registrar_Control_cuaretena(int id_detalle, String ca, String cb, String cc, String cd, int estadoV) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dcl_m_actualizar_control_cuarentena`('" + id_detalle + "','" + ca + "','" + cb + "','" + cc + "','" + cd + "','" + estadoV + "')");
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

    public boolean Actualizar_Estado(int id_clisse, int estadoV, int estado, String observacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cls_m_actualizar_estado`('" + id_clisse + "','" + estadoV + "','" + estado + "','" + observacion + "')");
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

    public boolean Verificar_registro(int id_clisse, String verificador, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cls_a_firmaV_estado_clisse`('" + id_clisse + "','" + verificador + "','" + estado + "')");
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
    public boolean ActualizarEstadoNoCumple(int id_clisse) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cls_m_actualizarEstadoVerificacionNC`('" + id_clisse + "')");
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

    public boolean ActualizarValorXLetra(int id_detalle, String letra, String valor) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
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
        }
    }

}
