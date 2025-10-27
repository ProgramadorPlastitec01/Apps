package Controladoras;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EquipoJpaController implements Serializable {

    public EquipoJpaController() {
        emf = Persistence.createEntityManagerFactory("REDEACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaEquipos() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_eqp_c_equipos`()");
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

    public List consultarEquiposFiltro(String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_eqp_t_filtrar_equipos`('" + filtro + "')");
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

    public boolean registroEquipo(String equipo, String responsable, String tipo_equipo, String tipo, int id_area, String cargo, String estado, String observaciones, String fecha_asignacion, String usuario_registro, int protocolo, String correo, String aplicativo) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_eqp_r_equipo`('" + equipo + "', '" + responsable + "', '" + tipo_equipo + "','" + tipo + "', '" + id_area + "', '" + cargo + "', '" + estado + "', '" + observaciones + "', '" + fecha_asignacion + "', '" + usuario_registro + "', '" + protocolo + "', '" + correo + "','" + aplicativo + "')");
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

    public List consultaEquipoId(int id_equipo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_eqp_t_equipo_id`('" + id_equipo + "')");
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

    public List consultaEquipoIdRegistro(int registro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_hve_c_hoja_de_vida_idR`('" + registro + "')");
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

    public boolean ModificarEquipo(int id_equipo, String equipo, String responsable, String tipo_equipo, String tipo, int id_area, String cargo, String estado, String observaciones, String fecha_movimiento, int protocolo, String correo, String usuario_registro, String aplicativo) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_eqp_m_equipoF`('" + id_equipo + "','" + equipo + "','" + responsable + "','" + tipo_equipo + "','" + tipo + "','" + id_area + "','" + cargo + "','" + estado + "','" + observaciones + "','" + fecha_movimiento + "','" + protocolo + "','" + correo + "','" + usuario_registro + "','" + aplicativo + "')");
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

    public List consultaMovimientosEquipoId(int id_equipo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_leq_t_movimientos_equipo`('" + id_equipo + "')");
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

    public List consultaMovimientosId(int id_movimiento) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_hve_c_movimiento_id`('" + id_movimiento + "')");
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

    public boolean registrarMovimientoEquipo(int id_equipo, String equipo, String responsable, String tipoE, int id_area, String cargo, String estado, String descripcion, String usuario_registro, String fecha_asignacion, String responsable_old, String tipoE_old, int id_area_old, String cargo_old, String estado_old, String observaciones_old, String usuario_registro_old, String fecha_old, String fecha_asignacion_old) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_leq_r_log_equipo`('" + id_equipo + "','" + equipo + "','" + responsable + "','" + tipoE + "','" + id_area + "','" + cargo + "','" + estado + "','" + descripcion + "','" + usuario_registro + "','" + fecha_asignacion + "','" + responsable_old + "','" + tipoE_old + "','" + id_area_old + "','" + cargo_old + "','" + estado_old + "','" + observaciones_old + "','" + usuario_registro_old + "','" + fecha_old + "','" + fecha_asignacion_old + "')");
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

    public boolean ModificarMovimientoEquipo(int id_movimiento, int id_equipo, String equipo, String responsable, String tipoE, int id_area, String cargo, String estado, String descripcion, String fecha_asignacion) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_leq_m_log_equipo`('" + id_movimiento + "','" + id_equipo + "','" + equipo + "','" + responsable + "','" + tipoE + "','" + id_area + "','" + cargo + "','" + estado + "','" + descripcion + "','" + fecha_asignacion + "')");
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

    public List consultarEquiposEncuesta() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_eqp_c_equipos_encuesta`()");
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

    public List consultaCorreoEquipo(int id_equipo, String codigo, int id_usuario) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_eqp_t_equipo_correo`('" + id_equipo + "','" + codigo + "','" + id_usuario + "')");
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

    public List consultaAniosEncuestas() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select distinct(YEAR(p.fecha_registro)),(SELECT count(ps.id_programacion) from programacion ps where YEAR(ps.fecha_registro) = YEAR(p.fecha_registro)) from programacion p order by YEAR (p.fecha_registro) desc");
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

    public List consultarMesesEncuestas(int anio) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select MONTH(p.fecha_registro),count(p.id_programacion) from programacion p where YEAR(p.fecha_registro) = " + anio + " group by MONTH(p.fecha_registro)");
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

    public List consultaProgramacionEncuesta(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pro_c_programacion_fechas`(" + anio + "," + mes + ")");
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

    public List consultaProgramacionEquipos(int ano, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pro_c_programacion_equipos`(" + ano + "," + mes + ")");
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

    public List consultaActividadesIdEquipo(int id_equipo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_arp_c_actividades_id_equipo`('" + id_equipo + "')");
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

    public List ConsultaProgramacionIdEquipo(int anio, int mes, int id_equipo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select p.id_programacion, p.id_usuario, CONCAT(u.nombres,' ',u.apellidos), p.id_equipo, p.copias from programacion p inner join usuario u on p.id_usuario = u.id_usuario where YEAR(p.fecha_registro) = " + anio + " and MONTH(p.fecha_registro) = " + mes + " and p.id_equipo = " + id_equipo + "");
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

    public List ConsultaTotalCalificacion(int id_equipo, int id_usuario, int id_programacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select c.id_calificacion, c.id_equipo , c.id_usuario, c.id_programacion, c.copia from calificacion c where c.id_equipo = " + id_equipo + " and c.id_usuario = " + id_usuario + " and c.id_programacion = " + id_programacion + "");
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

    public List ContadorProtocoloEquipo(String aplicativo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "SELECT COUNT(e.id_equipo),e.nombre "
                    + "FROM equipo e "
                    + "WHERE e.aplicativo_pt LIKE '%[" + aplicativo + "]%'";
            Query q = etm.createNativeQuery(query);
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
     public List EquiposProtocolo(String aplicativo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "SELECT e.id_equipo,e.nombre,e.protocolo,e.aplicativo_pt "
                    + "FROM equipo e "
                    + "WHERE e.aplicativo_pt LIKE '%[" + aplicativo + "]%'";
            Query q = etm.createNativeQuery(query);
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

}
