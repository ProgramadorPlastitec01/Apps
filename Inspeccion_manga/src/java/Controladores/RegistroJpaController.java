package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RegistroJpaController {

    public RegistroJpaController() {
        emf = Persistence.createEntityManagerFactory("Inspeccion_mangaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Registros_producto_orden(int ipd, String odn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_c_registro_orden_producto`('" + ipd + "','" + odn + "')");
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

    public List consultarRegistrosAbiertos(int ipd, String odn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reg_c_ConsultarUltimoRegistroCerrado`('" + ipd + "','" + odn + "')");
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

    public List Filtrar_registros_producto_orden(int ipd, String odn, String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registro_orden_producto_filtro`('" + ipd + "','" + odn + "','" + fto + "')");
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

    public List Traer_producto_orden(int ipd, String odn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registro_orden_producto`('" + ipd + "','" + odn + "')");
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

    public List Traer_registro_id_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registro_id_registro`('" + irg + "')");
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

    public List Traer_registros_id_producto(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registro_id_producto`('" + ipd + "')");
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

    public List Traer_ultimo_rango_rollos(int ipd, String odn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_c_ultimo_rango`('" + ipd + "', '" + odn + "')");
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

    public List Traer_info_rollos(int ireg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_c_registro_rollos_info`(" + ireg + ")");
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

    public boolean Registrar_turno(int ipd, String ftn, String tpd, String rpd, String lpd, String ltc, String ltp, int iln, String fmd, String tcl, String rcl, String drz, String cvt, String pfc, String range) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_r_registro`('" + ipd + "','" + ftn + "','" + tpd + "','" + rpd + "','" + lpd + "','" + ltc + "','" + ltp + "','" + iln + "','" + fmd + "','" + tcl + "','" + rcl + "','" + drz + "','" + cvt + "','" + pfc + "', '" + range + "')");
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

    public boolean Modificar_turno(int irg, String ftn, String tpd, String lpd, String ltc, String ltp, int iln, String fmd, String tcl, String drz, String cvt, String pfc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_registro`('" + irg + "','" + ftn + "','" + tpd + "','" + lpd + "','" + ltc + "','" + ltp + "','" + iln + "','" + fmd + "','" + tcl + "','" + drz + "','" + cvt + "','" + pfc + "')");
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

    public boolean Modificar_turno_rolls(int irg, String ftn, String tpd, String lpd, String ltc, String ltp, int iln, String fmd, String tcl, String drz, String cvt, String pfc, String rang, int validPos) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_registro_rolls`('" + irg + "','" + ftn + "','" + tpd + "','" + lpd + "','" + ltc + "','" + ltp + "','" + iln + "','" + fmd + "','" + tcl + "','" + drz + "','" + cvt + "','" + pfc + "', '" + rang + "', " + validPos + ")");
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

    public boolean Firmar_turno(int irg, String urg, String rol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = null;
            if (rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad")) {
                q = etm.createNativeQuery("UPDATE registro SET responsables_calidad = '" + urg + "' WHERE id_registro = " + irg + "");
            } else if (rol.equals("Coordinador_extrusion") || rol.equals("Operario_extrusion")) {
                q = etm.createNativeQuery("UPDATE registro SET responsables_produccion = '" + urg + "' WHERE id_registro = " + irg + "");
            } else if (rol.equals("Administrador")) {
                q = etm.createNativeQuery("UPDATE registro SET responsables_produccion = '" + urg + "', responsables_calidad = '" + urg + "'  WHERE id_registro = " + irg + "");
            }
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

    public boolean Activar_registro_pi(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_activar_pi`('" + irg + "')");
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

    public boolean Desactivar_registro_pi(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_desactivar_pi`('" + irg + "')");
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

    public boolean Activar_registro_gc(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_activar_gc`('" + irg + "')");
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

    public boolean Desactivar_registro_gc(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_desactivar_gc`('" + irg + "')");
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

    public List Registros_dia(String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = null;
            if (fto == null ? "" == null : fto.equals("")) {
                q = etm.createNativeQuery("SELECT o.numero,o.cliente,p.codigo,p.nombre,f.codigo,f.version,r.fecha_turno,r.turno_produccion,r.turno_calidad,l.nombre,r.responsables_produccion,r.responsables_calidad,r.estado_pi,r.estado_gc,r.id_registro,r.lote_producto FROM registro r inner join producto p ON r.id_producto = p.id_producto INNER JOIN orden_produccion o ON p.id_orden_produccion = o.id_orden_produccion INNER JOIN linea l ON r.id_linea = l.id_linea INNER JOIN ficha_tecnica f ON p.id_ficha_tecnica = f.id_ficha_tecnica WHERE r.fecha_turno = DATE_FORMAT(NOW(), '%Y-%m-%d') ORDER BY o.numero,r.fecha_turno");
                //q = etm.createNativeQuery("SELECT o.numero,o.cliente,p.codigo,p.nombre,f.codigo,f.version,r.fecha_turno,r.turno,l.nombre,r.usuario_registro,r.estado,r.id_registro FROM registro r inner join producto p ON r.id_producto = p.id_producto INNER JOIN orden_produccion o ON p.id_orden_produccion = o.id_orden_produccion INNER JOIN linea l ON r.id_linea = l.id_linea INNER JOIN ficha_tecnica f ON p.id_ficha_tecnica = f.id_ficha_tecnica WHERE r.fecha_turno = '2014-12-15' ORDER BY o.numero,r.turno");
            } else {
                q = etm.createNativeQuery("SELECT o.numero,o.cliente,p.codigo,p.nombre,f.codigo,f.version,r.fecha_turno,r.turno_produccion,r.turno_calidad,l.nombre,r.responsables_produccion,r.responsables_calidad,r.estado_pi,r.estado_gc,r.id_registro,r.lote_producto FROM registro r inner join producto p ON r.id_producto = p.id_producto INNER JOIN orden_produccion o ON p.id_orden_produccion = o.id_orden_produccion INNER JOIN linea l ON r.id_linea = l.id_linea INNER JOIN ficha_tecnica f ON p.id_ficha_tecnica = f.id_ficha_tecnica WHERE r.fecha_turno = '" + fto + "' ORDER BY o.numero,r.fecha_turno");
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

    public List Traer_lotes_id_producto(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_lotes_registro`('" + ipd + "')");
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

    public List Traer_lotes_cod_producto(String cpd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT DISTINCT(r.lote_producto),r.lote_producto FROM registro r WHERE r.lote_p LIKE '" + cpd + "%' order by r.lote_producto");
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

    public List Traer_lotes_cod_producto_lote_c(String ltp) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT DISTINCT(r.lote_c),r.lote_c FROM registro r WHERE r.lote_producto = '" + ltp + "'");
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

    public List Traer_lotes_cod_producto_lote_p(String ltp, String ltc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT DISTINCT(r.lote_p),r.lote_p FROM registro r WHERE r.lote_producto = '" + ltp + "' AND r.lote_c = '" + ltc + "'");
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

    public List Traer_lotes_id_producto_resumidos(int ipd, int rsm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_lotes_registro_resumido`('" + ipd + "','" + rsm + "')");
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

//CONTROL DE REGISTRO DESPEJE
    public boolean Estado_aplica_despeje(int irg, int vlr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update registro set aplica_despeje = " + vlr + " where id_registro = " + irg + "");
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

    //TABLAS PLANTILLA Y REGISTRO_DESPEJE
    public List Plantillas_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT rd.id_registro_despeje,rd.id_registro,rd.formato,rd.estado,rd.fecha_registro,rd.usuario_registro FROM registro r inner join registro_despeje rd on r.id_registro = rd.id_registro where r.id_registro =" + irg);
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

    public List Registro_depeje(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT rd.id_registro_despeje,rd.id_registro,rd.formato,rd.estado,rd.fecha_registro,rd.usuario_registro FROM registro_despeje rd inner join registro r on rd.id_registro = r.id_registro WHERE rd.id_registro = " + irg);
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

    public boolean Registrar_despeje(int irg, String trd, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("INSERT INTO registro_despeje(id_registro,formato,estado,usuario_registro) VALUES(" + irg + ",(SELECT p.formato FROM plantilla p WHERE p.codigo = '" + trd.replace("_", "-") + "' and p.estado = 1),0,'" + urg + "')");
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

    public boolean Actualizar_despeje(int irg, String fmt, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update registro_despeje r set r.formato = '" + fmt + "', r.usuario_registro = '" + urg + "' where r.id_registro = " + irg + "");
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

    public boolean Liberar_despeje(int ird) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update registro_despeje set estado = 1 where id_registro_despeje = " + ird + "");
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

    public boolean Firmar_despeje(int ird, String urg, String rol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update registro_despeje set formato = replace(formato,'" + rol + "','<b style=\"color:" + ((rol.equals("Inspectora_calidad")) ? "blue" : (rol.equals("Coordinadora_calidad")) ? "blue" : "black") + "\">" + urg + "</b>') where id_registro_despeje = " + ird + "");
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

    public boolean Eliminar_despeje(int ird) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("delete from registro_despeje where id_registro_despeje = " + ird + "");
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

    public boolean registro_incrementar_posicion_del_rango(int idReg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_rgt_u_actualizarRegistro_positionRango`('" + idReg + "')");
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

    public boolean modificar_registro_rollo_info(int idReg, String rango, int pos) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_modificar_rollos_info`(" + idReg + ", '" + rango + "', " + pos + ")");
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
    //FIN TABLAS PLANTILLA Y REGISTRO_DESPEJE

    //REPORTE POR LOTES REGISTRO DESPEJE
    public List Traer_registro_despeje_lotes(String lpd, String ltc, String ltp) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registro_depeje_lotes`('" + lpd + "','" + ltc + "','" + ltp + "')");
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

    public List Traer_registro_despeje_lotes_todos_p(String lpd, String ltc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registro_depeje_lotes_todos_p`('" + lpd + "','" + ltc + "')");
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

    ///DUREZAS
    public List Traer_control_durezas_lote(String ltc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_drz_t_durezas_lote`('" + ltc + "')");
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

    public List Traer_lote_control_formulas(String ltc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_fml_t_lote_formula`('" + ltc + "')");
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

    public List Traer_lote_control_durezas(String ltc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_fml_t_lote_dureza`('" + ltc + "')");
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

    public List ConsultarRollosxRegistro(int idR) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_rll_c_ConsultarRollosRegistro`('" + idR + "')");
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
//    
//    public List registro_incrementar_posicion_del_rango(int idReg) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `Sp_rgt_u_actualizarRegistro_positionRango`('" + idReg + "')");
//            List consulta = q.getResultList();
//            etm.getTransaction().commit();
//            etm.clear();
//            etm.close();
//            if (consulta.isEmpty()) {
//                return null;
//            } else {
//                return consulta;
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//    }
}
