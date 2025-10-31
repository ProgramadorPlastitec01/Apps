package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistroJpaController implements Serializable {

    public RegistroJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultarRegistro(int iod) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_c_consultar_registro`('" + iod + "')");
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
    public List ConsultarRegistroFiltro(int iod) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_c_consultar_registro_filtro`('" + iod + "')");
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

    public List ConsultarRegistroId(int id_registro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_c_consulta_registro_id`('" + id_registro + "')");
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

    public List Consultar_estadosxOrden(int id_orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pesaje_c_consulta_EstadosxOrden`('" + id_orden + "')");
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

    public List Consultar_estadosxDetalle(int id_reg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pesaje_c_consultaEstadoxDetalle`('" + id_reg + "')");
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

    public List Consultar_pesoTotal_registro(int id_ord) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_c_consultar_pesoTotal_registro`('" + id_ord + "')");
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

    public List ConsultarPlantillaDespeje() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pll_c_ConsultaPlantilla`()");
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

    public List ConsultarFormatoDespeje(int idReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_c_ConsultarPlantillaRegistro`('" + idReg + "')");
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

    public boolean RegistarRegistro(int iod, int irc, String fda, String lte, int etb, int id_maquina, String obv, String urg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_r_registrar_registro`('" + iod + "','" + irc + "','" + fda + "','" + lte + "','" + etb + "','" + id_maquina + "','" + obv + "','" + urg + "')");
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

    public boolean ModificarRegistro(int irg, int iod, int irc, String fda, String lte, int etb, String obv, String urg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_m_modificar_registro`('" + irg + "','" + iod + "','" + irc + "','" + fda + "','" + lte + "','" + etb + "','" + obv + "','" + urg + "')");
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

    public boolean CambiarEstado(int irg, int etd) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_m_cambiar_estado`('" + irg + "','" + etd + "')");
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

    public boolean RegistroDespeje(int irg, String format, String UserReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_Rdp_r_RegistrarDespeje`('" + irg + "','" + format + "', '" + UserReg + "')");
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

    public boolean ActaulizarRegistro(int irg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_r_a_ActualizarRegistroAddDespeje`('" + irg + "')");
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

    public boolean ActualizarDespeje(int irg, String format) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_a_ActualizarDespeje`('" + irg + "', '" + format + "')");
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

    public boolean ActualizarEstadoDespeje(int irg, int est) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_a_ActualizarEsatoDespeje`('" + irg + "', '" + est + "')");
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

    public boolean ActualizarObservacionEncargada(int irg, String obs) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_m_modificar_registro_observacion`('" + irg + "', '" + obs + "')");
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
