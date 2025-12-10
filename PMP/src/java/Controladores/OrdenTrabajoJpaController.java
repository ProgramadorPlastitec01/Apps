package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class OrdenTrabajoJpaController {

    public OrdenTrabajoJpaController() {
        emf = Persistence.createEntityManagerFactory("PMPPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Traer_orden_trabajo_id_equipo(int ieq) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_t_orden_id_equipo`('" + ieq + "')");
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

    public List Traer_ordenes_trabajo(String opc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query_0 = "select o.id_orden_trabajo,o.numero_orden,o.id_equipo,e.equipo,o.horometro_mtto,o.tiempo_estimado,"
                    + "o.programado_por,DATE_FORMAT(o.fecha_programado, '%Y-%m-%d %H:%i'),o.ejecutado_por,"
                    + "DATE_FORMAT(o.fecha_ejecutado, '%Y-%m-%d %H:%i'),o.revisado_por,DATE_FORMAT(o.fecha_revisado, '%Y-%m-%d %H:%i'),"
                    + "o.observaciones,o.paro_produccion,o.estado,o.programacion,o.usuario_registro,DATE_FORMAT(o.fecha_registro, '%Y-%m-%d %H:%i'),o.ubicacion_equipo,"
                    + "DATEDIFF(now(),o.fecha_programado), "
                    + "ifnull((select sum(ld.id_log_devolucion) from log_devolucion ld where ld.id_orden_trabajo = o.id_orden_trabajo ),0)"
                    + "from orden_trabajo o inner join equipo e on o.id_equipo = e.id_equipo "
                    + "order by o.numero_orden desc";
            String query_1 = "select o.id_orden_trabajo,o.numero_orden,o.id_equipo,e.equipo,o.horometro_mtto,o.tiempo_estimado,"
                    + "o.programado_por,DATE_FORMAT(o.fecha_programado, '%Y-%m-%d %H:%i'),o.ejecutado_por,"
                    + "DATE_FORMAT(o.fecha_ejecutado, '%Y-%m-%d %H:%i'),o.revisado_por,DATE_FORMAT(o.fecha_revisado, '%Y-%m-%d %H:%i'),"
                    + "o.observaciones,o.paro_produccion,o.estado,o.programacion,o.usuario_registro,DATE_FORMAT(o.fecha_registro, '%Y-%m-%d %H:%i'),o.ubicacion_equipo,"
                    + "DATEDIFF(now(),o.fecha_programado),0 "
                    + "from orden_trabajo o inner join equipo e on o.id_equipo = e.id_equipo "
                    + "where o.programacion = 0 "
                    + "order by o.numero_orden desc";
            String query_3 = "select o.id_orden_trabajo,o.numero_orden,o.id_equipo,e.equipo,o.horometro_mtto,o.tiempo_estimado,"
                    + "o.programado_por,DATE_FORMAT(o.fecha_programado, '%Y-%m-%d %H:%i'),o.ejecutado_por,"
                    + "DATE_FORMAT(o.fecha_ejecutado, '%Y-%m-%d %H:%i'),o.revisado_por,DATE_FORMAT(o.fecha_revisado, '%Y-%m-%d %H:%i'),"
                    + "o.observaciones,o.paro_produccion,o.estado,o.programacion,o.usuario_registro,DATE_FORMAT(o.fecha_registro, '%Y-%m-%d %H:%i'),o.ubicacion_equipo,"
                    + "DATEDIFF(now(),o.fecha_programado), "
                    + "ifnull((select sum(ld.id_log_devolucion) from log_devolucion ld where ld.id_orden_trabajo = o.id_orden_trabajo ),0)"
                    + "from orden_trabajo o inner join equipo e on o.id_equipo = e.id_equipo "
                    + "where o.programacion = 1 and o.estado in (1,2,3)"
                    + "order by o.numero_orden desc";
            String query_4 = "select o.id_orden_trabajo,o.numero_orden,o.id_equipo,e.equipo,o.horometro_mtto,o.tiempo_estimado,"
                    + "o.programado_por,DATE_FORMAT(o.fecha_programado, '%Y-%m-%d %H:%i'),o.ejecutado_por,"
                    + "DATE_FORMAT(o.fecha_ejecutado, '%Y-%m-%d %H:%i'),o.revisado_por,DATE_FORMAT(o.fecha_revisado, '%Y-%m-%d %H:%i'),"
                    + "o.observaciones,o.paro_produccion,o.estado,o.programacion,o.usuario_registro,DATE_FORMAT(o.fecha_registro, '%Y-%m-%d %H:%i'),o.ubicacion_equipo,"
                    + "DATEDIFF(now(),o.fecha_programado),0 "
                    + "from orden_trabajo o inner join equipo e on o.id_equipo = e.id_equipo "
                    + "where o.programacion = 1 and o.estado = 4 "
                    + "order by o.numero_orden desc";
            String query_5 = "select o.id_orden_trabajo,o.numero_orden,o.id_equipo,e.equipo,o.horometro_mtto,o.tiempo_estimado,"
                    + "o.programado_por,DATE_FORMAT(o.fecha_programado, '%Y-%m-%d %H:%i'),o.ejecutado_por,"
                    + "DATE_FORMAT(o.fecha_ejecutado, '%Y-%m-%d %H:%i'),o.revisado_por,DATE_FORMAT(o.fecha_revisado, '%Y-%m-%d %H:%i'),"
                    + "o.observaciones,o.paro_produccion,o.estado,o.programacion,o.usuario_registro,DATE_FORMAT(o.fecha_registro, '%Y-%m-%d %H:%i'),o.ubicacion_equipo,"
                    + "DATEDIFF(now(),o.fecha_programado),0 "
                    + "from orden_trabajo o inner join equipo e on o.id_equipo = e.id_equipo "
                    + "where o.estado = 5 "
                    + "order by o.numero_orden desc";
            String query_6 = "select o.id_orden_trabajo,o.numero_orden,o.id_equipo,e.equipo,o.horometro_mtto,o.tiempo_estimado,"
                    + "o.programado_por,DATE_FORMAT(o.fecha_programado, '%Y-%m-%d %H:%i'),o.ejecutado_por,"
                    + "DATE_FORMAT(o.fecha_ejecutado, '%Y-%m-%d %H:%i'),o.revisado_por,DATE_FORMAT(o.fecha_revisado, '%Y-%m-%d %H:%i'),"
                    + "o.observaciones,o.paro_produccion,o.estado,o.programacion,o.usuario_registro,DATE_FORMAT(o.fecha_registro, '%Y-%m-%d %H:%i'),o.ubicacion_equipo,"
                    + "DATEDIFF(now(),o.fecha_programado),0 "
                    + "from orden_trabajo o inner join equipo e on o.id_equipo = e.id_equipo "
                    + "where o.estado = 6 "
                    + "order by o.numero_orden desc";
            Query q = null;
            if (opc.equals("1")) {
                q = etm.createNativeQuery(query_1);
            } else if (opc.equals("3")) {
                q = etm.createNativeQuery(query_3);
            } else if (opc.equals("4")) {
                q = etm.createNativeQuery(query_4);
            } else if (opc.equals("5")) {
                q = etm.createNativeQuery(query_5);
            } else if (opc.equals("6")) {
                q = etm.createNativeQuery(query_6);
            } else if (opc.equals("0")) {
                q = etm.createNativeQuery(query_0);
            }
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

    public List Traer_orden_trabajo_id_orden(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_t_orden_id_orden`('" + iot + "')");
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

    public List Traer_orden_trabajo_numero(int nmr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_t_orden_numero`('" + nmr + "')");
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

    public List Traer_orden_trabajo_filtro(String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_t_orden_filtro`('" + fto + "')");
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
    public List Consultar_OT_Emitidas_Correo() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_c_consultar_ot_emitidas_correo`()");
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

    public List Traer_plantilla_ot_seguridad() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT id_plantilla,tipo,formato FROM plantilla WHERE tipo = 'Instrucciones_seguridad'");
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

    public boolean Registrar_orden_trabajo(int nod, int ieq, int hmt, String tet, String ppr, String epr, String rpr, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_r_orden_trabajo`('" + nod + "','" + ieq + "','" + hmt + "','" + tet + "','" + ppr + "','" + epr + "','" + rpr + "','" + urg + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Cerrar_programacion_OT(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_programar`('" + iot + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Cerrar_ejecucion_OT(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_ejecutar`('" + iot + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Cerrar_revision_OT(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_revisar`('" + iot + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Nuevo_cambio_estado(int iot,int etd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE orden_trabajo SET estado = "+etd+" WHERE id_orden_trabajo = " + iot);
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }
    public boolean Volver_ejecutar_OT(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE orden_trabajo SET fecha_ejecutado = '0001-01-01 00:00:01', fecha_revisado = '0001-01-01 00:00:01', estado = 3  WHERE id_orden_trabajo = "+iot+";");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }
    public boolean Volver_programar_OT(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE orden_trabajo SET fecha_programado = now(),estado=3, programacion = 0 WHERE id_orden_trabajo = " + iot);
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Cerrar_OT(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_cerrar_ot`('" + iot + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Cambiar_responsables_OT(int iot, String tet, String upg, String uec, String urv) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_m_responsables`('" + iot + "','" + tet + "','" + upg + "','" + uec + "','" + urv + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public List Traer_anios_ot() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select YEAR(o.fecha_registro),count(o.id_orden_trabajo) from orden_trabajo o group by YEAR(o.fecha_registro) order by YEAR(o.fecha_registro) desc");
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

    public List Traer_anios_historial() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select YEAR(h.fecha_actualizacion),count(h.fecha_actualizacion) from historial_horometro h group by YEAR(h.fecha_actualizacion) order by YEAR(h.fecha_actualizacion) desc");
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

    public List Traer_meses_historial(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select h.fecha_actualizacion,count(h.fecha_actualizacion) from historial_horometro h where  YEAR(h.fecha_actualizacion) = " + anio + " and MONTH(h.fecha_actualizacion) = " + mes + " group by h.fecha_actualizacion");
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

    public List Informe_actividades(String teq, int anio) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_informe_actividades`('" + teq + "','" + anio + "')");
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

    public List Informe_actividades_estadisticos(String teq, int anio) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_informe_actividades_estadistico`('" + teq + "','" + anio + "')");
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

    public List Informe_actividades_mes_filtro(String fin, String ffn, String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_informe_actividades_mes_filtro`('" + fin + "','" + ffn + "','" + fto + "')");
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

    public List Informe_actividades_mes(String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_informe_actividades_mes`('" + fin + "','" + ffn + "')");
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
    public List Calculo_indicador(String num, String den) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_informe_actividades_mes_result`('" + num + "','" + den + "')");
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

    public List Informe_historial_horometros(String teq, List lsm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String constructor = "";
            constructor = "select e.id_equipo,e.equipo,";
            for (int i = 0; i < lsm.size(); i++) {
                Object[] obj_semanas = (Object[]) lsm.get(i);
                if (i == 0) {
                    constructor = constructor + "ifnull((select concat(h.horometro_anterior,' / ',h.horometro_actual) ";
                    constructor = constructor + "from historial_horometro h ";
                    constructor = constructor + "where h.fecha_actualizacion = '" + obj_semanas[0] + "' and h.id_equipo = e.id_equipo group by h.id_equipo),'0 / 0')";
                } else {
                    constructor = constructor + ",ifnull((select concat(h.horometro_anterior,' / ',h.horometro_actual) ";
                    constructor = constructor + "from historial_horometro h ";
                    constructor = constructor + "where h.fecha_actualizacion = '" + obj_semanas[0] + "' and h.id_equipo = e.id_equipo group by h.id_equipo),'0 / 0')";
                }
            }
            constructor = constructor + "from historial_horometro hh inner join equipo e on hh.id_equipo = e.id_equipo ";
            constructor = constructor + "inner join tipo_equipo te on e.id_tipo_equipo = te.id_tipo_equipo ";
            constructor = constructor + "where te.tipo_equipo = '" + teq + "' ";
            constructor = constructor + "group by e.id_equipo";
            Query q = etm.createNativeQuery("" + constructor);
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

    //<editor-fold defaultstate="collapsed" desc="LOGS OT">
    public boolean Justificar_devolucion(int iot, String jtf, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ldv_r_devolucion`('" + iot + "','" + jtf + "','" + urg + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public List Consultar_historial_devoluciones() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ldv_c_devoluciones`()");
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

    public List Consultar_historial_eliminacion() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lel_c_eliminaciones`()");
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

    public boolean Justificar_eliminacion(int ieq, int nmo, int hrm, String jtf, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_lel_r_eliminacion`('" + ieq + "','" + nmo + "','" + hrm + "','" + jtf + "','" + urg + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Eliminar_orden(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_otb_e_orden`('" + iot + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }
//</editor-fold>
}
