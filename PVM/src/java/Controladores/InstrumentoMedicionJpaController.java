package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class InstrumentoMedicionJpaController {

    public InstrumentoMedicionJpaController() {
        emf = Persistence.createEntityManagerFactory("PVMPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaTipo() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpo_c_tipo`()");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaEstadisticoInforme(String query) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("" + query + "");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaInformeIdVerIdPlan(int id_verificacion, int id_plantilla) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ifm_c_informe_idP_idV`('" + id_verificacion + "','" + id_plantilla + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaInstrumentos(int dias) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_c_instrumentos`('" + (dias * -1) + "')");
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

    public List consultaInstrumentosFiltro(String filtro, int dias) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_c_instrumentos_filtro`('" + filtro + "','" + (dias * -1) + "')");
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

    public List consultaInstrumentosTipoInstrumento(int id_tipo_instrumento, int dias) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_c_instrumento_tipo_instrumento`('" + id_tipo_instrumento + "','" + (dias * -1) + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaInstrumentosFiltroTipoInstrumento(String filtro, int id_tipo_instrumento, int dias) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_c_instrumento_filtro_tipo_instrumento`('" + filtro + "','" + id_tipo_instrumento + "','" + (dias * -1) + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean registrarIntrumento(int id_tipoI, int id_tipo, int id_plant, String codigo, String instrumento, String fabricante, String modelo, String numero_serial, String rango_medida, String division_escala, String exactitud, String clasificacion, String observaciones, String ultima_verificacion_interna, String ultima_verificacion_externa, String usuario_registro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_r_instrumento`('" + id_tipoI + "','" + id_tipo + "','"+ id_plant+"','" + codigo + "','" + instrumento + "','" + fabricante + "','" + modelo + "','" + numero_serial + "','" + rango_medida + "','" + division_escala + "','" + exactitud + "','" + clasificacion + "','" + observaciones + "','" + ultima_verificacion_interna + "','" + ultima_verificacion_externa + "','" + usuario_registro + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean ActualizarPlantilla(int id_tipoI, int id_plantilla) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpi_m_plantilla`('" + id_tipoI + "','" + id_plantilla + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarIntrumento(int id_Instrumento, int id_tipoI, int id_tipo, int id_plantilla_verificacion, String codigo, String instrumento, String fabricante, String modelo, String numero_serial, String rango_medida, String division_escala, String exactitud, String clasificacion, String observaciones, String ultima_verificacion_interna, String ultima_verificacion_externa) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_m_instrumento`('" + id_Instrumento + "','" + id_tipoI + "','" + id_tipo + "','" + id_plantilla_verificacion + "','" + codigo + "','" + instrumento + "','" + fabricante + "','" + modelo + "','" + numero_serial + "','" + rango_medida + "','" + division_escala + "','" + exactitud + "','" + clasificacion + "','" + observaciones + "','" + ultima_verificacion_interna + "','" + ultima_verificacion_externa + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean AnularVerificaciones(int idVerif) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_vrf_m_CambiarEstadoVerificacion_anuladas`('" + idVerif + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean RegistrarEvento(int idVerif, String justify, String userReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_his_r_RegistrarHistorial`('" + idVerif + "','" + justify + "','" + userReg + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public List consultaPlantillasInstrumentoS(int id_instrumento, int id_tipo_plantilla) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_c_plantilla_instrumento_id_s`('" + id_instrumento + "','" + id_tipo_plantilla + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaPlantillasInstrumento(int id_instrumento, int id_tipo_plantilla) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_c_plantilla_instrumento_id`('" + id_instrumento + "','" + id_tipo_plantilla + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaPlantillaVerificacion(int id_plantilla) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("select p.plantilla, 0 as id_plantilla_instrumento, 0 as estado  from plantilla p where p.id_plantilla = " + id_plantilla + "");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaPlantillaVerificacionInstrumento(int id_plantillaI) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("select p.plantilla, p.id_plantilla_instrumento , p.estado from plantilla_instrumento p where p.id_plantilla_instrumento = " + id_plantillaI + "");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaVerificacionId(int id_verificacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_vrf_c_verificacion_id`('" + id_verificacion + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaUltimaPlantillaVerificacionInstrumento(int id_instrumento, int id_tipo_plantilla) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_c_plantilla_verificacion_id`('" + id_instrumento + "','" + id_tipo_plantilla + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaVerificacionInstrumento(int id_instrumento, int id_tipo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_vrf_c_verificacion_intrumento_id`('" + id_instrumento + "','" + id_tipo + "')"
            );
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaVerificacionExternaInstrumento(int id_instrumento) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_vrv_c_verificacion_externa_idInstrumento`('" + id_instrumento + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List traerPlantillaFichaTecnica(int id_tipo_Instrumento) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_c_traer_plantilla`('" + id_tipo_Instrumento + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaInstrumentoId(int id_Instrumento) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_c_instrumento_id`('" + id_Instrumento + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean registrarPlantillaInstrumento(int id_instrumento, int id_tipoP, String plantilla) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pnt_r_plantilla_instrumento`('" + id_instrumento + "','" + id_tipoP + "','" + plantilla + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarPlantillaInstrumento(int id_PlantillaI, String plantilla) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pnt_m_plantilla_instrumento`('" + id_PlantillaI + "','" + plantilla + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarEstadoPlantillaInstrumento(int id_PlantillaI, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pnt_m_estado_plantilla`('" + id_PlantillaI + "','" + estado + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarFechaVerificacion(int id_verificacion, String fecha) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_vrf_m_fecha`('" + id_verificacion + "','" + fecha + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registrarVerificacionInstrumento(int id_instrumento, int id_tipo, String justificacion, String adjunto, String usuario_registro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_vrf_r_verificacion`('" + id_instrumento + "','" + id_tipo + "','" + justificacion + "','" + adjunto + "','" + usuario_registro + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarFechasVerificacionInterna(int id_Instrumento, String fecha_int) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("update instrumento_medicion i set i.fch_ultima_verificacion_int = '" + fecha_int + "' where i.id_instrumento_medicion = " + id_Instrumento + "");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarFechasVerificacionExterna(int id_Instrumento, String fecha_ext) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("update instrumento_medicion i set i.fch_ultima_verificacion_ext = '" + fecha_ext + "' where i.id_instrumento_medicion = " + id_Instrumento + "");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarEstadoInstrumento(int id_Instrumento, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_itm_m_estado_instrumento`('" + id_Instrumento + "','" + estado + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarVerificacionIntrumento(int id_PlantillaI, int id_verificacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("update verificacion v set v.id_plantilla = " + id_PlantillaI + " where v.id_verificacion = " + id_verificacion + "");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarVerificacionInstrumento(int id_verificacion) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("delete from verificacion where id_verificacion = " + id_verificacion + "");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarEstadoVerificacion(int id_verificacion, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_vrf_m_estado_verificacion`('" + id_verificacion + "','" + estado + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean finalizarVerificacion(int id_verificacion, String fecha, String justificacion, String adjunto) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_vrf_m_finalizar_verificacion`('" + id_verificacion + "','" + fecha + "','" + justificacion + "','" + adjunto + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registrarDatosEstadisticos(int id_verificacion, int id_plantilla, String datos, String usuario_registro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ifm_r_datos`('" + id_verificacion + "','" + id_plantilla + "','" + datos + "','" + usuario_registro + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarDatosInforme(int id_informe, String datos) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ifm_m_datos`('" + id_informe + "','" + datos + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public List consultaInformeInstrumento(int id_Instrumento, String anio) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ifm_c_datos_id_instrumento`('" + id_Instrumento + "','" + anio + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaAniosInforme(int id_Instrumento) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ifm_c_anios`('" + id_Instrumento + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }
}
