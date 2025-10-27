package Controladoras;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class BitacoraJpaController implements Serializable {

    public BitacoraJpaController() {
        emf = Persistence.createEntityManagerFactory("REDEACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaultimasBitacoras() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_btc_c_ultimas_bitacoras`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaActividadesBitacora(int id_usuario, String fecha_inicio, String fecha_fin, int estado) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_agn_t_actividades_usuario_bitacora`('" + id_usuario + "','" + fecha_inicio + "','" + fecha_fin + "','" + estado + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaActividadesReportadasBitacora(int id_usuario, String fecha_inicio, String fecha_fin, int estado) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_arp_t_actividades_usuario_bitacora`('" + id_usuario + "','" + fecha_inicio + "','" + fecha_fin + "','" + estado + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaCasosBitacora(int id_usuario, String fecha_inicio, String fecha_fin, int estado) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cso_c_solucionados_usuario_bitacora`('" + id_usuario + "','" + fecha_inicio + "','" + fecha_fin + "','" + estado + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaPendientesSolucionadosBitacora(int id_usuario, String fecha_inicio, String fecha_fin, int estado) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pde_c_solucionados_usuario_bitacora`('" + id_usuario + "','" + fecha_inicio + "','" + fecha_fin + "','" + estado + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
    public List consultaActividadesdiariasBitacora(int id_tec, String fecha_inicio, String fecha_fin) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_bta_c_actividades_diarias`('" + id_tec + "','" + fecha_inicio + "','" + fecha_fin + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaPendientesBitacora(int id_rol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pde_c_pendiente_usuario_bitacora`('" + id_rol + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaCasosPendientesBitacora() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cso_c_casos_pendientes_bitacora`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaMovimientosEquiposBitacora(String usuario, String fecha_inicio, String fecha_fin) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_eqp_c_movimientos_equipos_bitacora`('" + usuario + "','" + fecha_inicio + "','" + fecha_fin + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean registroBitacora(String asunto, String turno, String fechaI, String fechaF, int cantAct, int cantActR, int cantCas, int cantCasP, int cantPndS, int cantPnd, int cantacD, int cantMovEqp, int id_suario) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_btc_r_bitacora`('" + asunto + "','" + turno + "','" + fechaI + "','" + fechaF + "','" + cantAct + "','" + cantActR + "','" + cantCas + "','" + cantCasP + "','" + cantPndS + "','" + cantPnd + "','" + cantacD + "','" + cantMovEqp + "','" + id_suario + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarEstadoActividadBitacora(int id_usuario, String fechaI, String fechaF) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_agn_m_actividades_usuario_bitacora`('" + id_usuario + "','" + fechaI + "','" + fechaF + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado > 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarEstadoActividadesReportadasBitacora(int id_usuario, String fechaI, String fechaF) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_arp_m_actividades_usuario_bitacora`('" + id_usuario + "','" + fechaI + "','" + fechaF + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado > 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarEstadoCasosBitacora(int id_usuario, String fechaI, String fechaF) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_cso_m_casos_usuario_bitacora`('" + id_usuario + "','" + fechaI + "','" + fechaF + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado > 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarEstadoPendientesBitacora(int id_usuario, String fechaI, String fechaF) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_pde_m_pendientes_usuario_bitacora`('" + id_usuario + "','" + fechaI + "','" + fechaF + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado > 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List consultaBitacorasUsuario(int id_usuario, String fecha_inicio, String fecha_fin) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_btc_t_bitacoras_usuario`('" + id_usuario + "','" + fecha_inicio + "','" + fecha_fin + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaBitacoraId(int id_bitacora) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_btc_t_bitacora_id_bitacora`('" + id_bitacora + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
    
    public boolean revizarBitacora(int id_bitacora) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_btc_m_revisar_bitacora`('" + id_bitacora + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
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
