package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ControlDmsCJpaController implements Serializable {

    public ControlDmsCJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlGrafadoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultaTurnosSeguimiento(int id_orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pbf_c_turnos_seguimiento`(" + id_orden + ")");
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

    public List consultaTurnos(int id_orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_turnos`(" + id_orden + ")");
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
    public List consultaTurnosFiltro(int id_orden, String filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_turnos_filtro`('" + id_orden + "','" + filtro + "')");
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
    public List consultaTurnosFiltroSeguimiento(int id_orden, String filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_turnos_filtro_seguimiento`('" + id_orden + "','" + filtro + "')");
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

    public List consultaUltimoTurno(int id_orden, String lote) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_t_ultimo_turno`('" + id_orden + "','" + lote + "')");
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

    public List consultaUltimoTurnoMuestra(int id_orden, String lote, int id_prueba_Fallida) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_t_ultimo_turno_muestra`('" + id_orden + "','" + lote + "','" + id_prueba_Fallida + "')");
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

    public List consultaTurnoId(int id_turno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_t_id_control_dmsc`('" + id_turno + "')");
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

    public boolean registroTurno14(String fecha_turno, String turno, int id_orden, String lote_basec, String lote_basep, String lote_pistonc, String lote_pistonp, String lote_ensamble, int id_maquina, int id_cliente, String registro, int consecutivo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_r_control_dmsc_e`('" + fecha_turno + "','" + turno + "','" + id_orden + "','" + lote_basec.trim() + "','" + lote_basep.trim() + "','" + lote_pistonc.trim() + "','" + lote_pistonp.trim() + "','" + lote_ensamble.trim() + "','" + id_maquina + "','" + id_cliente + "','" + registro + "','" + consecutivo + "')");
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

    public boolean registroSeguimientoPF(int id_turno, int id_pruebaF) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        String queryRPF = "INSERT INTO control_dms_c (fecha_registro,fecha_turno,turno,lote_grafado_c, lote_grafado_p, "
                    + "lote_piston_c, lote_piston_p, lote_ensamble, obs_est1,obs_est2, obs_est3, obs_est4,obs_est5,obs_est6, "
                    + "prueba, estado, resumen,tipo,calidad,seguimiento,id_orden,id_cliente,id_maquina,resultado_seguimiento,inf_seguimiento) "
                    + "SELECT NOW(),NOW(),c.turno,c.lote_grafado_c, c.lote_grafado_p, "
                    + "c.lote_piston_c, c.lote_piston_p, c.lote_ensamble, c.obs_est1, c.obs_est2, c.obs_est3, c.obs_est4, c.obs_est5, c.obs_est6, "
                    + "c.prueba, c.estado, c.resumen, c.tipo,c.calidad,1,c.id_orden,c.id_cliente,c.id_maquina,1, "
                    + "(SELECT CONCAT('[',GROUP_CONCAT(cc.id_dimensional_c),'][',GROUP_CONCAT(cc.consecutivo),']') "
                    + " FROM control_dms_c cc"
                    + "	WHERE cc.id_pruebaf = " + id_pruebaF + ") "
                    + "	FROM control_dms_c c "
                    + "	INNER JOIN orden o ON c.id_orden = o.id_orden "
                    + "	INNER JOIN cliente cl ON c.id_cliente = cl.id_cliente "
                    + "	INNER JOIN maquina m ON c.id_maquina = m.id_maquina "
                    + "	LEFT JOIN prueba_funcional p ON c.id_pruebaf = p.id_prueba_funcional "
                    + "	WHERE c.id_dimensional_c = " + id_turno + " AND c.id_pruebaf = " + id_pruebaF + "";
        try {
            Query q = em.createNativeQuery(queryRPF);
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

    public boolean registroSeguimientoValidarPF(int id_turno, int id_pruebaF) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        String queryRSV = "INSERT INTO control_dms_c (fecha_registro,fecha_turno,turno,lote_grafado_c, lote_grafado_p, "
                    + "lote_piston_c, lote_piston_p, lote_ensamble, obs_est1,obs_est2, obs_est3, obs_est4,obs_est5,obs_est6, "
                    + "prueba, estado, resumen,tipo,calidad,seguimiento,id_orden,id_cliente,id_maquina,resultado_seguimiento,inf_seguimiento) "
                    + "SELECT NOW(),NOW(),c.turno,c.lote_grafado_c, c.lote_grafado_p, "
                    + "c.lote_piston_c, c.lote_piston_p, c.lote_ensamble, c.obs_est1, c.obs_est2, c.obs_est3, c.obs_est4, c.obs_est5, c.obs_est6, "
                    + "c.prueba, c.estado, c.resumen, c.tipo,c.calidad,1,c.id_orden,c.id_cliente,c.id_maquina,2,inf_seguimiento "
                    + "	FROM control_dms_c c "
                    + "	INNER JOIN orden o ON c.id_orden = o.id_orden "
                    + "	INNER JOIN cliente cl ON c.id_cliente = cl.id_cliente "
                    + "	INNER JOIN maquina m ON c.id_maquina = m.id_maquina "
                    + "	LEFT JOIN prueba_funcional p ON c.id_pruebaf = p.id_prueba_funcional "
                    + "	WHERE c.id_dimensional_c = " + id_turno + " AND c.id_pruebaf = " + id_pruebaF + "";
        try {
            Query q = em.createNativeQuery(queryRSV);
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

    public boolean registroTurno14Muestra(String fecha_turno, String turno, int id_orden, String lote_basec, String lote_basep, String lote_pistonc, String lote_pistonp, String lote_ensamble, int id_maquina, int id_cliente, String registro, int consecutivo, int consecutivo_prueba) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_r_control_dmsc_e`('" + fecha_turno + "','" + turno + "','" + id_orden + "','" + lote_basec.trim() + "','" + lote_basep.trim() + "','" + lote_pistonc.trim() + "','" + lote_pistonp.trim() + "','" + lote_ensamble.trim() + "','" + id_maquina + "','" + id_cliente + "','" + registro + "','" + consecutivo + "')");
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

    public boolean registroTurno16(String fecha_turno, String turno, int id_orden, String lote_basec, String lote_basep, String lote_pistonc, String lote_pistonp, String lote_ensamble, int id_maquina, int id_cliente, String registro, String molde, int consecutivo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_r_control_dmsc_u`('" + fecha_turno + "','" + turno + "','" + id_orden + "','" + lote_basec.trim() + "','" + lote_basep.trim() + "','" + lote_pistonc.trim() + "','" + lote_pistonp.trim() + "','" + lote_ensamble.trim() + "','" + id_maquina + "','" + id_cliente + "','" + registro + "','" + molde + "','" + consecutivo + "')");
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

    public boolean modificarTurno14(int id_turno, String fecha, String turno, String lote_basec, String lote_basep, String lote_pistonc, String lote_pistonp, String lote_ensamble, int id_maquina) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_m_control_dmsc_e`('" + id_turno + "','" + fecha + "','" + turno + "','" + lote_basec + "','" + lote_basep + "','" + lote_pistonc + "','" + lote_pistonp + "','" + lote_ensamble + "','" + id_maquina + "')");
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

    public boolean registroSeriales(int id_turno, String seriales, String fechaUltV, String fechaProxV) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_m_seriales`('" + id_turno + "','" + seriales + "','" + fechaUltV + "','" + fechaProxV + "')");
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

    public boolean modificarTurno16(int id_turno, String fecha, String turno, String lote_basec, String lote_basep, String lote_pistonc, String lote_pistonp, String lote_ensamble, int id_maquina, String molde) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_m_control_dmsc_u`('" + id_turno + "','" + fecha + "','" + turno + "','" + lote_basec + "','" + lote_basep + "','" + lote_pistonc + "','" + lote_pistonp + "','" + lote_ensamble + "','" + id_maquina + "','" + molde + "')");
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

    public List consultaToma(int id_turno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdd_c_control_dmsc_d`('" + id_turno + "')");
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

    public List consultarParametrosFichaTecnica(int id_turno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_t_parameter`('" + id_turno + "')");
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

    public List consultaTurnosSeguimiento(int id_orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_visual`('" + id_orden + "')");
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

    public List consultaTurnosSeguimientoFiltro(int id_orden, String filtro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_c_visual_filtro`('" + id_orden + "','" + filtro + "')");
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

    public List consultaTurnosEstadoSeguimiento(int id_turno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        String queryCTE = "SELECT c.id_dimensional_c,c.seguimiento,c.resultado_seguimiento,replace(SPLIT_STR(c.inf_seguimiento, '][', 1),'[',''), replace(SPLIT_STR(c.inf_seguimiento, '][', 2),']','') FROM control_dms_c c WHERE c.id_dimensional_c = " + id_turno + "";
        try {
            Query q = em.createNativeQuery(queryCTE);
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

    public boolean estadoEstacionTurno(int id_turno, String estacion, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("UPDATE control_dms_c SET est" + estacion + "=" + estado + "  WHERE id_dimensional_c=" + id_turno + "");
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

    public boolean validarTomasDimencional(int id_turno, int validar) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_m_prueba`('" + id_turno + "', '" + validar + "')");
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

    public boolean modificarEstado(int id_turno, String estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_m_estado`('" + id_turno + "','" + estado + "')");
            int resultado = q.executeUpdate();
            if (resultado == 1) {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return true;
            } else {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean DefinirEstado(int id_turno, String estadoC, String justificacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cdc_calidad`('" + id_turno + "','" + estadoC + "','" + justificacion + "')");
            int resultado = q.executeUpdate();
            if (resultado == 1) {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return true;
            } else {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List consultaPlantillaDespeje() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ptl_c_platilla`('R-PRF-009')");
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

    public boolean registrarDespeje(int id_turno, String plantilla, String usuario_registro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dpj_r_despeje`('" + id_turno + "','" + plantilla + "','" + usuario_registro + "')");
            int resultado = q.executeUpdate();
            if (resultado == 1) {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return true;
            } else {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List consultaDespejeTurno(int id_despeje) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dpj_c_despeje_id`('" + id_despeje + "')");
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

    public boolean modificarDespeje(int id_despeje, String plantilla) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dpj_m_despeje`('" + id_despeje + "','" + plantilla + "')");
            int resultado = q.executeUpdate();
            if (resultado == 1) {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return true;
            } else {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean liberarDespeje(int id_despeje, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dpj_m_liberar`('" + id_despeje + "','" + estado + "')");
            int resultado = q.executeUpdate();
            if (resultado == 1) {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return true;
            } else {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean observacionDespeje(int id_despeje, int observacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dpj_m_estado_observacion`('" + id_despeje + "','" + observacion + "')");
            int resultado = q.executeUpdate();
            if (resultado == 1) {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return true;
            } else {
                em.getTransaction().commit();
                em.clear();
                em.close();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
