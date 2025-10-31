package Controladores;

import java.io.Serializable;
import javax.persistence.Query;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OrdenJpaController implements Serializable {

    public OrdenJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultarOrden() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_c_consultar_orden`()");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
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

    public boolean Registar_orden(String nod, int imq, String cdg, String pdt, String pln, String lte, int ctd, String und, String cct, String ft_version, int pmt, int uxe, String urg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_r_registrar_orden`('" + nod + "','" + imq + "','" + cdg + "','" + pdt + "','" + pln + "','" + lte + "','" + ctd + "','" + und + "','" + cct + "','" + ft_version + "','" + pmt + "','" + uxe + "','" + urg + "')");
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

    public boolean ModificarOrden(int iod, int imq, int uxe, int pmt) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_m_modificar_orden`('" + iod + "','" + imq + "','" + uxe + "','" + pmt + "')");
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

    public boolean Cambiar_estado_estado(int nod, int iod) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_m_cambiar_estado`('" + nod + "','" + iod + "')");
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

    public List ConsultarOrdenId(int iod) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_c_consulta_orden_id`('" + iod + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
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

    public List ConsultarNumeroOrden(int iod) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_odn_c_consultar_numeroOrden_idOrden`('" + iod + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
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
            em.getTransaction().commit();;
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

    public List ReporteConsultaGeneral(int iod, int irg, int trn) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_consulta_reporte_general`('" + iod + "','" + irg + "','" + trn + "')");
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

    public List ContadorTotalTiempo(int iod, int irg, int dato) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            String query = "SELECT r.tiempo,CAST(SUM(SPLIT_STR(SPLIT_STR(REPLACE(REPLACE(REPLACE(r.tiempo,'][','-'),'[',''),']',''), '-', " + dato + "),'/',1)) AS DECIMAL (15,0))AS 'Desfrag' "
                    + "FROM registro_detalle r "
                    + "INNER JOIN registro rt ON r.id_registro = rt.id_registro "
                    + "WHERE rt.id_orden = " + iod + " AND r.id_registro = " + irg + " ";
            Query q = em.createNativeQuery(query);
            List resultados = q.getResultList();
            em.getTransaction().commit();;
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

    public List TotalTiempo(int iod, int irg, int tiempo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            int dato = 1;
            String contenido = "";
            for (int i = 0; i < tiempo; i++) {
                if (i != (tiempo - 1)) {
                    contenido = contenido + " CAST(SUM(SPLIT_STR(SPLIT_STR(REPLACE(REPLACE(REPLACE(r.tiempo,'][','-'),'[',''),']',''), '-', " + dato + "),'/',1)) AS DECIMAL (15,0)) + ";
                    dato++;
                } else {
                    contenido = contenido + " CAST(SUM(SPLIT_STR(SPLIT_STR(REPLACE(REPLACE(REPLACE(r.tiempo,'][','-'),'[',''),']',''), '-', " + dato + "),'/',1)) AS DECIMAL (15,0)) ";
                }
            }
            String query = "SELECT 1, " + contenido + ""
                    + "FROM registro_detalle r "
                    + "INNER JOIN registro rt ON r.id_registro = rt.id_registro "
                    + "WHERE rt.id_orden = " + iod + " AND r.id_registro = " + irg + " ";
            Query q = em.createNativeQuery(query);
            List resultados = q.getResultList();
            em.getTransaction().commit();;
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

    public List TotalDefecto(int iod, int defecto) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_dfc_c_defectosAgrupados`('" + iod + "','" + defecto + "')");
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
    public List TotalDefectoReporte(int iod, int defecto, int registro, int turno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_dfc_c_defectosAgrupadosReporte`('" + iod + "','" + defecto + "','" + registro + "','" + turno + "')");
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

    public List ContadorTotalDefecto(int iod, int dato, int id_registro, int turno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dft_c_ConsultaTotalDefectos`('" + iod + "','" + dato + "', '" + id_registro + "', '" + turno + "')");
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
}
