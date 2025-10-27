package Controladoras;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ListasVerificacionJpaController implements Serializable {

    public ListasVerificacionJpaController() {
        emf = Persistence.createEntityManagerFactory("REDEACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean registrarEquipoVerificacion(String nombre, String descripcion, String usuario) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_r_tipo_verificacion`('" + nombre + "', '" + descripcion + "','" + usuario + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registrarAdjuntoHoja(int idV, String nombre,  String fechaA, String descripcion, String usuario) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_r_registro_hoja_vida`('" + idV + "','" + nombre + "','" + fechaA + "','" + descripcion + "','" + usuario + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registrarEquipoDetalle(int consecutivo, int id_tipo_v, String equipo, String item, String serial, String estado, String responsable, String ubicacion, int id_area, String fecha_movimiento, String cargo, String usuario_registro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_r_equipo_verificacion`('" + consecutivo + "', '" + id_tipo_v + "','" + equipo + "','" + item + "','" + serial + "','"
                    + estado + "','" + responsable + "','" + ubicacion + "','" + id_area + "','" + fecha_movimiento + "','" + cargo + "','" + usuario_registro + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registrarEquipoMovimiento(int id_verificacion, int consecutivo, int id_tipo_v, String equipo, String item, String serial, String estado, String responsable, String ubicacion, int id_area, String fecha_movimiento, String cargo, String usuario_registro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_r_equipo_movimiento`('" + id_verificacion + "','" + consecutivo + "', '" + id_tipo_v + "','" + equipo + "','" + item + "','" + serial + "','"
                    + estado + "','" + responsable + "','" + ubicacion + "','" + id_area + "','" + fecha_movimiento + "','" + cargo + "','" + usuario_registro + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registrarLogEquipo(int id_verificacion, int id_tipo_v, int consecutivo, String equipo, String item, String serial, String estado, String responsable, String ubicacion, int id_area, String fecha_movimiento, String cargo, String usuario_registro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_r_registrar_historial`('" + id_verificacion + "','" + id_tipo_v + "', '" + consecutivo + "','" + equipo + "','" + item + "','" + serial + "','"
                    + estado + "','" + responsable + "','" + ubicacion + "','" + id_area + "','" + fecha_movimiento + "','" + cargo + "','" + usuario_registro + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarListaVerificacion(int idTV, String nombre, String descripcion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_m_tipo_verificacion`('" + idTV + "','" + nombre + "','" + descripcion + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarDetalleListaVerificacion(int idV, int consecutivo, String equipo, String item, String serial, String estado, String reponsable, String ubicacion, int id_area, String fecha_movimiento, String cargo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_m_equipo_verificacion`('" + idV + "','" + consecutivo + "','" + equipo + "','" + item + "','" + serial + "','" + estado + "','" + reponsable + "','" + ubicacion + "','" + id_area + "','" + fecha_movimiento + "','" + cargo + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarEquipoMovimiento(int id_movimiento, int idV, int consecutivo, String equipo, String item, String serial, String estado, String reponsable, String ubicacion, int id_area, String fecha_movimiento, String cargo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_m_equipo_movimiento`('" + id_movimiento + "','" + idV + "','" + consecutivo + "','" + equipo + "','" + item + "','" + serial + "','" + estado + "','" + reponsable + "','" + ubicacion + "','" + id_area + "','" + fecha_movimiento + "','" + cargo + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarAjuntoHoja(int idH, String nombre, String fechaA, String descripcion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_m_modificar_adjunto`('" + idH + "','" + nombre + "','" + fechaA + "','" + descripcion + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean EliminarAdjunto(int idH) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_e_eliminar_adjunto`('" + idH + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List consultaEquipoVerificacion(int id_tipo_verificacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_t_equipo_verificacion`('" + id_tipo_verificacion + "')");
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

    public List consultaEquipoContadorVerificacion(int id_tipo_verificacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_c_contador_equipo`('" + id_tipo_verificacion + "')");
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

    public List consultaTipoVerificacion(int id_tipo_verificacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_c_tipo_verificacion`('" + id_tipo_verificacion + "')");
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

    public List consultaModificarVerificacion(int id_tipo_verificacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_t_modificar_verificacion`('" + id_tipo_verificacion + "')");
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

    public List consultaModificarMovimiento(int id_movimiento) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_c_modificar_movimiento`('" + id_movimiento + "')");
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

    public List consultaListasVerificacionR5() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_t_listas_verificacion_R5`()");
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

    public List consultaListasTipoVerificacion() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_t_tipo_verificacion`()");
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

    public List consultaListasTipoVerificacionFiltro(String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_t_tipo_verificacion_filtro`('" + filtro + "')");
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

    public List consultaListaDetalleVerificacionId(int id_lst_verificacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_c_detalle_lista_verificacion`('" + id_lst_verificacion + "')");
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

    public List consultaEquipoMovimiento(int id_verificacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_c_equipo_movimiento`('" + id_verificacion + "')");
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

    public List consultaListaDetalleVerificacionGeneral() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_c_detalle_lista_verificacion_general`()");
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

    public List consultaListaDetalleVerificacionIFiltro(int id_lst_verificacion, String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_c_detalle_lista_verificacion_filtro`('" + id_lst_verificacion + "','" + filtro + "')");
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

    public List consultaListaEquipoMovimientoFiltro(int id_verificacion, String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_c_equipo_movimiento_filtro`('" + id_verificacion + "','" + filtro + "')");
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

    public List consultaEquiposListaVerificacionId(int id_lst_verificacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_c_lista_verificacion`('" + id_lst_verificacion + "')");
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

    public List consultaEquipoHojaVerificacion(int id_equipo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_c_hoja_vida`('" + id_equipo + "')");
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

    public List consultaAdjuntoHoja(int id_verificacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_c_adjunto_hoja_vida`('" + id_verificacion + "')");
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

    public List consultaModificarAdjunto(int id_adjunto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_t_adjunto`('" + id_adjunto + "')");
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

    public List consultaLogEquipo(int id_verificacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_t_log_verificacion`('" + id_verificacion + "')");
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

    public List consultaLogEquipoFiltro(int id_verificacion, String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lvn_t_log_verificacion_filtro`('" + id_verificacion + "','" + filtro + "')");
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

    public List consultaActividadesIdListaEquipo(int id_l_equipo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_arp_c_actividades_id_equipo_verificacion`('" + id_l_equipo + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultarCasosidListaEquipo(int id_l_equipo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cso_c_casos_id_equipo_v`('" + id_l_equipo + "')");
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
    

}
