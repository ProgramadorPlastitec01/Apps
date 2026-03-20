package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Persistence;

public class ResumenJpaController implements Serializable {

    public ResumenJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlGrafadoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaLoteEnsamble(String orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_lote`('" + orden + "')");
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

    public List consultaLoteEnsambleFormulacion(String orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_lote_formulacion`('" + orden + "')");
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

    public List consultaCabeceraResumen(String orden, String lote) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_cabecera_r`('" + orden + "', '" + lote + "')");
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

    public List consultaCantidadResumen(int orden, String lote, String fch1, String fch2) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_detalle_r`('" + orden + "', '" + lote + "', '" + fch1 + "', '" + fch2 + "')");
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

    public List consultaPromedioTurno(int id_turno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdd_c_detalle_r`('" + id_turno + "')");
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

    public List consultaValorResumen(String orden, String lote, String fch1, String fch2) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdd_c_valor_t_r`('" + orden + "', '" + lote + "', '" + fch1 + "', '" + fch2 + "')");
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
    
    public List consultaValorResumenV2(String orden, String lote, String fch1, String fch2, int idResum) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdd_c_valor_t_r_v2`('" + orden + "', '" + lote + "', '" + fch1 + "', '" + fch2 + "','"+ idResum +"')");
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

    public boolean registroResumen(String num_certificado, String orden, String lote_ensamble, int cantidad_resumen, String fecha1, String hora1, String fecha2, String hora2, String fecha_despacho, String num_grafadora, String usa_registro, String orden_despacho, String cliente, String observaciones) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rsm_r_resumen`('" + num_certificado + "', '" + orden + "', '" + lote_ensamble + "', '" + cantidad_resumen + "', '" + fecha1 + "', '" + hora1 + "', '" + fecha2 + "',  '" + hora2 + "', '" + fecha_despacho + "', '" + num_grafadora + "', '" + usa_registro + "','" + orden_despacho + "','" + cliente + "','" + observaciones + "')");
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

    public List consultaResumenIdPorOrden(String orden, String lote_ensamble) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_c_id_resumen`('" + orden + "','" + lote_ensamble + "')");
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

    public boolean resumirRegistro(String orden, String lote, String fch1, String fch2, int idResumen) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_m_resumen`('" + orden + "', '" + lote + "', '" + fch1 + "', '" + fch2 + "','" + idResumen + "')");
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

    public List consultarResgistrosResumen(int idResumen) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_c_registros_resumen`('" + idResumen + "')");
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

    public List consultaResumenId(int id_resumen) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rsm_t_id_resumen`('" + id_resumen + "')");
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

    public List consultaResumenes(String anio) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rsm_c_resumen`('" + anio + "')");
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

    public List consultaAnioResumenes() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("SELECT YEAR(CURDATE()), YEAR(r.fch_registro), COUNT(r.id_resumen) FROM resumen r GROUP BY  YEAR(r.fch_registro) ORDER BY r.fch_registro DESC");
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

    public boolean completarResumen(int id_resumen, String num_certificado, String fecha_despacho, String orden_despacho, String cliente, String observaciones) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rsm_m_resumen`('" + id_resumen + "', '" + num_certificado + "', '" + fecha_despacho + "','" + orden_despacho + "','" + cliente + "','" + observaciones + "')");
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

    public List consultarFormulacion(String orden, String lote_ensamble) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdd_t_formulacion`('" + orden + "', '" + lote_ensamble + "')");
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

    public List consultarFrecuencia(String Query) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery(Query);
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

    public List consultarPremuestras(String condOrden, String condLote, String groupby) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("SELECT o.id_orden, o.orden, cc.lote_ensamble, (SELECT COUNT(ccc.id_dimensional_c) FROM control_dms_c ccc WHERE ccc.id_orden = o.id_orden and ccc.lote_ensamble = cc.lote_ensamble) as contador, ROUND((AVG(CD.Y2)), 2) AS 'PRM-Y2', ROUND((MIN(CD.Y2)), 2) AS 'MIN-Y2',ROUND(MAX(CD.Y2), 2) AS 'MAX-Y2',ROUND((AVG(CD.X1)), 2) AS 'PRM-X1',ROUND((MIN(CD.X1)), 2) AS 'MIN-X1',ROUND(MAX(CD.X1), 2) AS 'MAX-X1',ROUND((AVG(CD.Y1)), 2) AS 'PRM-Y1',ROUND((MIN(CD.Y1)), 2) AS 'MIN-Y1',ROUND(MAX(CD.Y1), 2) AS 'MAX-Y1',ROUND((AVG(CD.X2)), 2) AS 'PRM-X2',ROUND((MIN(CD.X2)), 2) AS 'MIN-X2',ROUND(MAX(CD.X2), 2) AS 'MAX-X2',ROUND((AVG(CD.X3)), 2) AS 'PRM-X3',ROUND((MIN(CD.X3)), 2) AS 'MIN-X3',ROUND(MAX(CD.X3), 2) AS 'MAX-X3', max(DATE_FORMAT(cc.fecha_registro,'%Y-%m-%d')) as 'fecha_max',min(DATE_FORMAT(cc.fecha_registro,'%Y-%m-%d')) as 'fecha_min' ,(SELECT GROUP_CONCAT(DISTINCT(ccc.molde) separator ',') FROM  control_dms_c ccc INNER JOIN orden oo ON ccc.id_orden = oo.id_orden WHERE ccc.id_orden = cc.id_orden) as 'moldes', (SELECT GROUP_CONCAT(DISTINCT(SPLIT_STR(m.maquina,' ',2)) separator ',') FROM  maquina m inner join control_dms_c ccc  on m.id_maquina = ccc.id_maquina JOIN orden oo ON ccc.id_orden = oo.id_orden WHERE (ccc.id_orden = 264 or ccc.id_orden = 265)) as 'grafadoras' FROM control_dms_d cd INNER JOIN control_dms_c cc ON cd.id_dimensional_c = cc.id_dimensional_c INNER JOIN orden o ON cc.id_orden = o.id_orden WHERE (" + condOrden + ") and (" + condLote + ") " + ((groupby != "") ? groupby : "") + "");
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

    public List consultarPremuestrasCabecera(String condOrden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("SELECT o.id_orden, o.orden, cc.lote_ensamble, cc.lote_grafado_c, cc.lote_grafado_p, cc.lote_piston_c, cc.lote_piston_p,SPLIT_STR(cc.lote_grafado_p,'-',1),SPLIT_STR(cc.lote_piston_p,'-',1) FROM control_dms_d cd INNER JOIN control_dms_c cc ON cd.id_dimensional_c = cc.id_dimensional_c INNER JOIN orden o ON cc.id_orden = o.id_orden WHERE (" + condOrden + ") group by cc.lote_ensamble order by cc.lote_grafado_c ,o.orden, cc.lote_piston_c ");
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
