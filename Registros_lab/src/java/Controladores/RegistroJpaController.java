package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RegistroJpaController {

    public RegistroJpaController() {
        emf = Persistence.createEntityManagerFactory("RegistrosLaboratorioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Registros_producto_orden(int ipd, int odn) {
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

    public List Filtrar_registros_producto_orden(int ipd, int odn, String fto) {
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

    public List Traer_producto_orden(int ipd, int odn) {
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

    public List Traer_lotes_id_producto_verificar(int ipd, String lte, int iln, String cet) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_lotes_registro_verificar`('" + ipd + "','" + lte + "','" + iln + "','" + cet + "')");
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

    public List ConsultarRegistroxLoteCola(String lteCola) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reg_c_ConsultarRegistrosxLoteCola`('" + lteCola + "')");
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

    public List ConsultarLineaRegistros(int idRegistro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_consultLineByRegister`(" + idRegistro + ")");
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

    public List Traer_lotes_id_producto_resumidos(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_lotes_registro_resumido`('" + ipd + "')");
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

    public boolean Registrar_turno(int ipd, String ftn, String lpd, String tno, int iln, String vlm, String lmc, String lmp, String lgtcs, String lddc, String lddp, String lgt1, String ldic, String ldip, String lgt2, String esb, String les, String ltt, int vrf, String lcl, String ctt, int pat, String urg, String esb2, String les2, String lmca, String ldcc, String ldcp, String lbc, String ldca, String ltr, String cet, String esb3, String les3, String esb4, String les4, String sltc, String sltcalt, String sltp, String ltm, String huv, String lld) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_r_registro`('" + ipd + "','" + ftn + "','" + lpd + "','" + tno + "','" + iln + "','" + vlm + "','" + lmc + "','" + lmp + "','" + lgtcs + "','" + lddc + "','" + lddp + "','" + lgt1 + "','" + ldic + "','" + ldip + "','" + lgt2 + "','" + esb + "','" + les + "','" + ltt + "','" + vrf + "','" + lcl + "','" + ctt + "','" + pat + "','" + urg + "','" + esb2 + "','" + les2 + "','" + lmca + "','" + ldcc + "','" + ldcp + "','" + lbc + "','" + ldca + "','" + ltr + "','" + cet + "','" + esb3 + "','" + les3 + "','" + esb4 + "','" + les4 + "','" + sltc + "','" + sltcalt + "','" + sltp + "','" + ltm + "','" + huv + "','" + lld + "')");
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

//    public boolean Modificar_turno(int irg, int ipd, String ftn, String lpd, String tno, int iln, String lmc, String lmp, String lddc, String lddp, String ldic, String ldip, String esb, String les, String ltt, String lcl, String ctt, int pat, String urg, String esb2, String les2, String lmca, String ldcc, String ldcp, String lbc, String ldca, String ltr, String cet, String esb3, String les3, String esb4, String les4, String sltc, String sltcalt, String sltp,String ltm, String huv, String lld) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_rgt_m_registro`('" + irg + "','" + ipd + "','" + ftn + "','" + lpd + "','" + tno + "','" + iln + "','" + lmc + "','" + lmp + "','" + lddc + "','" + lddp + "','" + ldic + "','" + ldip + "','" + esb + "','" + les + "','" + ltt + "','" + lcl + "','" + ctt + "','" + pat + "','" + urg + "','" + esb2 + "','" + les2 + "','" + lmca + "','" + ldcc + "','" + ldcp + "','" + lbc + "','" + ldca + "','" + ltr + "','" + cet + "','" + esb3 + "','" + les3 + "','" + esb4 + "','" + les4 + "','" + sltc + "','" + sltcalt + "','" + sltp + "','" + ltm + "','" + huv + "','" + lld + "')");
//            int exitoso = q.executeUpdate();
//            etm.getTransaction().commit();
//            etm.clear();
//            etm.close();
//            if (exitoso == 0) {
//                return false;
//            } else {
//                return true;
//            }
//        } catch (Exception ex) {
//            return false;
//        }
//    }
    public boolean Modificar_turno_new(int irg, int ipd, String ftn, String lpd, String vlm, String tno, int iln, String lmc, String lmp, String lcp, String lddc, String lddp, String lng, String ldic, String ldip, String lng2, String esb, String les, String ltt, String lcl, String ctt, int pat, String urg, String esb2, String les2, String lmca, String ldcc, String ldcp, String lbc, String ldca, String ltr, String cet, String esb3, String les3, String esb4, String les4, String sltc, String sltcalt, String sltp, String ltm, String huv, String lld, String lng3) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_registro`('" + irg + "','" + ipd + "','" + ftn + "','" + lpd + "','" + vlm + "','" + tno + "','" + iln + "','" + lmc + "','" + lmp + "','" + lcp + "','" + lddc + "','" + lddp + "','" + lng + "','" + ldic + "','" + ldip + "','" + lng2 + "','" + esb + "','" + les + "','" + ltt + "','" + lcl + "','" + ctt + "','" + pat + "','" + urg + "','" + esb2 + "','" + les2 + "','" + lmca + "','" + ldcc + "','" + ldcp + "','" + lbc + "','" + ldca + "','" + ltr + "','" + cet + "','" + esb3 + "','" + les3 + "','" + esb4 + "','" + les4 + "','" + sltc + "','" + sltcalt + "','" + sltp + "','" + ltm + "','" + huv + "','" + lld + "','" + lng3 + "')");
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

    public boolean Firmar_turno(int irg, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_firmar_registro`('" + irg + "','" + urg + "')");
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

    public boolean Activar_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_activar`('" + irg + "')");
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

    public boolean Desactivar_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_desactivar`('" + irg + "')");
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

    public boolean Resumir_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_resumir`('" + irg + "')");
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

    public boolean Verificar_registro(int irg, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_verificar`('" + irg + "','" + urg + "')");
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

    public List Registros_lote(String lte, int ipd, int nmr, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfh_t_registros_lote`('" + lte + "','" + ipd + "','" + nmr + "','" + fin + "','" + ffn + "')");
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
    //REGISTROS DEL DIA

    public List Registros_dia(String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = null;
            if (fto == null ? "" == null : fto.equals("")) {
                q = etm.createNativeQuery("SELECT o.numero,o.cliente,p.codigo,p.nombre,f.codigo,f.version,r.fecha_turno,r.turno,l.nombre,r.usuario_registro,r.estado,r.id_registro,r.lote_producto,tl.tipo_registro FROM registro r inner join producto p ON r.id_producto = p.id_producto INNER JOIN orden_produccion o ON p.id_orden_produccion = o.id_orden_produccion INNER JOIN linea l ON r.id_linea = l.id_linea INNER JOIN ficha_tecnica f ON p.id_ficha_tecnica = f.id_ficha_tecnica INNER JOIN tipo_linea tl ON l.id_tipo_linea = tl.id_tipo_linea WHERE r.fecha_turno = DATE_FORMAT(NOW(), '%Y-%m-%d') ORDER BY o.numero,r.turno");
                //q = etm.createNativeQuery("SELECT o.numero,o.cliente,p.codigo,p.nombre,f.codigo,f.version,r.fecha_turno,r.turno,l.nombre,r.usuario_registro,r.estado,r.id_registro FROM registro r inner join producto p ON r.id_producto = p.id_producto INNER JOIN orden_produccion o ON p.id_orden_produccion = o.id_orden_produccion INNER JOIN linea l ON r.id_linea = l.id_linea INNER JOIN ficha_tecnica f ON p.id_ficha_tecnica = f.id_ficha_tecnica WHERE r.fecha_turno = '2014-12-15' ORDER BY o.numero,r.turno");
            } else {
                q = etm.createNativeQuery("SELECT o.numero,o.cliente,p.codigo,p.nombre,f.codigo,f.version,r.fecha_turno,r.turno,l.nombre,r.usuario_registro,r.estado,r.id_registro,r.lote_producto,tl.tipo_registro FROM registro r inner join producto p ON r.id_producto = p.id_producto INNER JOIN orden_produccion o ON p.id_orden_produccion = o.id_orden_produccion INNER JOIN linea l ON r.id_linea = l.id_linea INNER JOIN ficha_tecnica f ON p.id_ficha_tecnica = f.id_ficha_tecnica INNER JOIN tipo_linea tl ON l.id_tipo_linea = tl.id_tipo_linea WHERE r.fecha_turno = '" + fto + "' ORDER BY o.numero,r.turno");
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

    public boolean Bloquear_estacion(int irg, String cet) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE registro SET control_estaciones = '" + cet + "' WHERE id_registro = " + irg + "");
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

    public boolean Resaltar_responsables(int irg, String cet) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE registro SET control_estaciones = '" + cet + "' WHERE id_registro = " + irg + "");
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
    //CONTADOR DE REGISTROS POR ORDEN

    public List Contador_registros_orden(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select rg.lote_producto,(select count(r.id_registro) from registro r where r.id_producto = " + ipd + " and r.lote_producto = rg.lote_producto and r.estado = 1) AS ABIERTOS,(select count(r.id_registro) from registro r where r.id_producto = " + ipd + " and r.lote_producto = rg.lote_producto and r.estado = 0) AS CERRADOS from registro rg where rg.id_producto = " + ipd + " group by rg.lote_producto");
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

    //TABLAS PLANTILLA Y REGISTRO_DESPEJE
    public List Plantillas_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT rd.id_registro_despeje,rd.id_registro,rd.formato,rd.liberado,rd.observacion,rd.fecha_registro,rd.usuario_registro, r.id_linea FROM registro r inner join registro_despeje rd on r.id_registro = rd.id_registro where r.id_registro =" + irg);
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
    //TRAER DESPEJE ID_REGISTRO

    public List Registro_despeje(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT rd.id_registro_despeje,rd.id_registro,rd.formato,rd.liberado,rd.fecha_registro,rd.usuario_registro FROM registro_despeje rd inner join registro r on rd.id_registro = r.id_registro WHERE rd.id_registro = " + irg);
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

    public boolean Cambios_verificar_registro(int irg, int cet) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE registro SET verificado = '" + cet + "' WHERE id_registro = " + irg + "");
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
    //CREAR DESPEJE

    public boolean Registrar_despeje(int irg, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("INSERT INTO registro_despeje(id_registro,formato,usuario_registro) VALUES(" + irg + ",(SELECT p.formato FROM registro r inner join linea l on r.id_linea = l.id_linea inner join tipo_linea tl on l.id_tipo_linea = tl.id_tipo_linea inner join plantilla p on p.id_tipo_linea = tl.id_tipo_linea WHERE r.id_registro = " + irg + " and p.estado = 1),'" + urg + "')");
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
    //ACTUALIZAR DESPEJE

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
            Query q = etm.createNativeQuery("update registro_despeje set liberado = 1 where id_registro_despeje = " + ird + "");
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

    public boolean Observaciones_despeje(int ird, int tpo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update registro_despeje set observacion = " + tpo + " where id_registro_despeje = " + ird + "");
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

    public boolean Firmar_despeje(int ird, String urg, String rol, String rolo, String rolo2) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update registro_despeje set formato = replace(formato,'" + rol + "','" + urg + "'),formato = replace(formato,'" + rolo + "','" + urg + "'),formato = replace(formato,'" + rolo2 + "','" + urg + "') where id_registro_despeje = " + ird + "");
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

    public boolean Devolver_despeje(int ird) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update registro_despeje set liberado=0 where id_registro =" + ird + "");
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
    //TRAER DESPEJE ID_REGISTRO

    public List Traer_registros_despeje_id_producto(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rdp_t_registros_despeje_id_produto`('" + ipd + "')");
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

    public List Traer_registros_despeje_id_producto_linea(int ipd, int iln) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rdp_t_registros_despeje_id_produto_linea`('" + ipd + "','" + iln + "')");
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

    public List Screen_resumen_lote(String lte, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registros_screen`('" + lte + "','" + fin + "','" + ffn + "')");
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
    //VACIAR MODULOS AUTOMATICOS

    public boolean Limpiar_modulo_automatico(int irg, int mdl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "";
            if (mdl == 1) {
                query = "delete from registro_frecuencia_hora where id_registro = " + irg;
            } else if (mdl == 2) {
                query = "delete from registro_lote_codigo where id_registro = " + irg;
            } else if (mdl == 3) {
                query = "delete from registro_prueba_calidad where id_registro = " + irg;
            } else if (mdl == 4) {
                query = "delete from registro_frecuencia_media_hora where id_registro = " + irg;
            } else if (mdl == 5) {
                query = "delete from registro_despeje where id_registro = " + irg;
            } else if (mdl == 6) {
                query = "delete from registro where id_registro = " + irg;
            }
            Query q = etm.createNativeQuery(query);
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

    public boolean Cerrar_turnos_automatico(String tno) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "UPDATE registro rt "
                    + "SET rt.estado = 0, "
                    + "    rt.verificado = 1 "
                    + "WHERE rt.turno = 'Turno 1' "
                    + "  AND NOT EXISTS ( "
                    + "        SELECT 1 "
                    + "        FROM registro_entrada_material r "
                    + "        WHERE r.id_registro = rt.id_registro "
                    + "          AND r.producto_entrante IS NULL "
                    + "  ) "
                    + "  AND NOT EXISTS ( "
                    + "        SELECT 1 "
                    + "        FROM ( "
                    + "            SELECT  "
                    + "                f.id_registro, "
                    + "                COUNT(f.id_parametro) AS total, "
                    + "                SUM(f.toma1 IS NOT NULL) AS toma1, "
                    + "                SUM(f.toma2 IS NOT NULL) AS toma2, "
                    + "                SUM(f.toma3 IS NOT NULL) AS toma3, "
                    + "                SUM(f.toma4 IS NOT NULL) AS toma4, "
                    + "                SUM(f.toma5 IS NOT NULL) AS toma5, "
                    + "                SUM(f.toma6 IS NOT NULL) AS toma6, "
                    + "                SUM(f.toma7 IS NOT NULL) AS toma7, "
                    + "                SUM(f.toma8 IS NOT NULL) AS toma8, "
                    + "                SUM(f.toma9 IS NOT NULL) AS toma9, "
                    + "                SUM(f.toma10 IS NOT NULL) AS toma10 "
                    + "            FROM registro_frecuencia_hora f "
                    + "            GROUP BY f.id_registro "
                    + "        ) x "
                    + "        WHERE x.id_registro = rt.id_registro "
                    + "          AND ( "
                    + "                (x.toma1 > 0 AND x.toma1 < x.total) OR "
                    + "                (x.toma2 > 0 AND x.toma2 < x.total) OR "
                    + "                (x.toma3 > 0 AND x.toma3 < x.total) OR "
                    + "                (x.toma4 > 0 AND x.toma4 < x.total) OR "
                    + "                (x.toma5 > 0 AND x.toma5 < x.total) OR "
                    + "                (x.toma6 > 0 AND x.toma6 < x.total) OR "
                    + "                (x.toma7 > 0 AND x.toma7 < x.total) OR "
                    + "                (x.toma8 > 0 AND x.toma8 < x.total) OR "
                    + "                (x.toma9 > 0 AND x.toma9 < x.total) OR "
                    + "                (x.toma10 > 0 AND x.toma10 < x.total) "
                    + "          ) "
                    + "  );";
            Query q = etm.createNativeQuery(query);
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

    public boolean Log_data_registro(String tabla, String id_columna, int registro_id, String cambio, String usuario) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ldt_r_copia_datos`('" + tabla + "','" + id_columna + "','" + registro_id + "','" + cambio + "','" + usuario + "')");
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

    public boolean UpdateMasivoResumen(String IdsRegistro, String lote_cola, String ensamble, String ensamble_2, String lote_ensamble, String lote_ensamble_2, String ensamble_3, String ensamble_4, String lote_ensamble_3, String lote_ensamble_4, String ciclo_esterilizacion, String lote_tubo_refuerzo, String lote_manga_c, String lote_manga_p, String lote_dto_drc_c, String lote_dto_drc_p, String lote_dto_ctl_c, String lote_dto_ctl_p, String lote_dto_izq_c, String lote_dto_izq_p, String color_tinta, String lote_tinta) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            int contador = 0;
            String QueryInicial = "UPDATE registro r ";
            if (!lote_cola.equals("")) {
                QueryInicial = QueryInicial + "SET lote_cola = '" + lote_cola + "'";
                contador++;
            }
            if (!ensamble.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", ensamble = '" + ensamble + "'";
                contador++;
            } else if (!ensamble.equals("")) {
                QueryInicial = QueryInicial + "SET ensamble = '" + ensamble + "'";
                contador++;
            }
            if (!ensamble_2.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", ensamble_2 = '" + ensamble_2 + "'";
                contador++;
            } else if (!ensamble_2.equals("")) {
                QueryInicial = QueryInicial + "SET ensamble_2 = '" + ensamble_2 + "'";
                contador++;
            }
            if (!lote_ensamble.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_ensamble = '" + lote_ensamble + "'";
                contador++;
            } else if (!lote_ensamble.equals("")) {
                QueryInicial = QueryInicial + "SET lote_ensamble = '" + lote_ensamble + "'";
                contador++;
            }
            if (!lote_ensamble_2.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ",  lote_ensamble_2 = '" + lote_ensamble_2 + "'";
                contador++;
            } else if (!lote_ensamble_2.equals("")) {
                QueryInicial = QueryInicial + "SET lote_ensamble_2 = '" + lote_ensamble_2 + "'";
                contador++;
            }
            if (!ensamble_3.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", ensamble_3 = '" + ensamble_3 + "'";
                contador++;
            } else if (!ensamble_3.equals("")) {
                QueryInicial = QueryInicial + "SET ensamble_3 = '" + ensamble_3 + "'";
                contador++;
            }
            if (!ensamble_4.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", ensamble_4 = '" + ensamble_4 + "'";
                contador++;
            } else if (!ensamble_4.equals("")) {
                QueryInicial = QueryInicial + "SET ensamble_4 = '" + ensamble_4 + "'";
                contador++;
            }
            if (!lote_ensamble_3.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_ensamble_3 = '" + lote_ensamble_3 + "'";
                contador++;
            } else if (!lote_ensamble_3.equals("")) {
                QueryInicial = QueryInicial + "SET lote_ensamble_3 = '" + lote_ensamble_3 + "'";
                contador++;
            }
            if (!lote_ensamble_4.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_ensamble_4 = '" + lote_ensamble_4 + "'";
                contador++;
            } else if (!lote_ensamble_4.equals("")) {
                QueryInicial = QueryInicial + "SET lote_ensamble_4 = '" + lote_ensamble_4 + "'";
                contador++;
            }
            if (!ciclo_esterilizacion.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", ciclo_esterilizacion = '" + ciclo_esterilizacion + "'";
                contador++;
            } else if (!ciclo_esterilizacion.equals("")) {
                QueryInicial = QueryInicial + "SET ciclo_esterilizacion = '" + ciclo_esterilizacion + "'";
                contador++;
            }
            if (!lote_tubo_refuerzo.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_tubo_refuerzo = '" + lote_tubo_refuerzo + "'";
                contador++;
            } else if (!lote_tubo_refuerzo.equals("")) {
                QueryInicial = QueryInicial + "SET lote_tubo_refuerzo = '" + lote_tubo_refuerzo + "'";
                contador++;
            }
            if (!lote_manga_c.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_manga_c = '" + lote_manga_c + "'";
                contador++;
            } else if (!lote_manga_c.equals("")) {
                QueryInicial = QueryInicial + "SET lote_manga_c = '" + lote_manga_c + "'";
                contador++;
            }
            if (!lote_manga_p.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_manga_c = '" + lote_manga_p + "'";
                contador++;
            } else if (!lote_manga_p.equals("")) {
                QueryInicial = QueryInicial + "SET lote_manga_c = '" + lote_manga_p + "'";
                contador++;
            }
            if (!lote_dto_drc_c.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_dto_drc_c = '" + lote_dto_drc_c + "'";
                contador++;
            } else if (!lote_dto_drc_c.equals("")) {
                QueryInicial = QueryInicial + "SET lote_dto_drc_c = '" + lote_dto_drc_c + "'";
                contador++;
            }
            if (!lote_dto_drc_p.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_dto_drc_p = '" + lote_dto_drc_p + "'";
                contador++;
            } else if (!lote_dto_drc_p.equals("")) {
                QueryInicial = QueryInicial + "SET lote_dto_drc_p = '" + lote_dto_drc_p + "'";
                contador++;
            }
            if (!lote_dto_ctl_c.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_dto_ctl_c = '" + lote_dto_ctl_c + "'";
                contador++;
            } else if (!lote_dto_ctl_c.equals("")) {
                QueryInicial = QueryInicial + "SET lote_dto_ctl_c = '" + lote_dto_ctl_c + "'";
                contador++;
            }
            if (!lote_dto_ctl_p.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_dto_ctl_p = '" + lote_dto_ctl_p + "'";
                contador++;
            } else if (!lote_dto_ctl_p.equals("")) {
                QueryInicial = QueryInicial + "SET lote_dto_ctl_p = '" + lote_dto_ctl_p + "'";
                contador++;
            }
            if (!lote_dto_izq_c.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_dto_iqe_c = '" + lote_dto_izq_c + "'";
                contador++;
            } else if (!lote_dto_izq_c.equals("")) {
                QueryInicial = QueryInicial + "SET lote_dto_iqe_c = '" + lote_dto_izq_c + "'";
                contador++;
            }
            if (!lote_dto_izq_p.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_dto_iqe_p = '" + lote_dto_izq_p + "'";
                contador++;
            } else if (!lote_dto_izq_p.equals("")) {
                QueryInicial = QueryInicial + "SET lote_dto_iqe_p = '" + lote_dto_izq_p + "'";
                contador++;
            }
            if (!color_tinta.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", color_tinta = '" + color_tinta + "'";
                contador++;
            } else if (!color_tinta.equals("")) {
                QueryInicial = QueryInicial + "SET color_tinta = '" + color_tinta + "'";
                contador++;
            }
            if (!lote_tinta.equals("") && contador > 0) {
                QueryInicial = QueryInicial + ", lote_tinta = '" + lote_tinta + "'";
                contador++;
            } else if (!lote_tinta.equals("")) {
                QueryInicial = QueryInicial + "SET lote_tinta = '" + lote_tinta + "'";
                contador++;
            }
            String QueryFinal = " WHERE lote_producto = '" + IdsRegistro + "'";
            String Query = QueryInicial + QueryFinal;
            Query q = etm.createNativeQuery(Query);
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

    //<editor-fold defaultstate="collapsed" desc="LIST ENSAMBLES">
    public List Traer_nombre_ensambles() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_ensambles`()");
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
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="VALIDADORES DE CABECERA">

    public List Validar_fce_bocas_eva_colpitt(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT count(f.id_parametro) as PARAMETROS,count(f.toma1) AS TOMA1,count(f.toma2) AS TOMA2,count(f.toma3) AS TOMA3,count(f.toma4) AS TOMA4,count(f.toma5) AS TOMA5,count(f.toma6) AS TOMA6,count(f.toma7) AS TOMA7,count(f.toma8) AS TOMA8,count(f.toma9) AS TOMA9,count(f.toma10) AS TOMA10 FROM registro_frecuencia_hora f inner join registro r on f.id_registro = r.id_registro where r.id_registro = " + irg + "");
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

    public List Validar_fce_screen(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT count(f.id_parametro) as PARAMETROS,count(f.toma1) AS TOMA1,count(f.toma2) AS TOMA2,count(f.toma3) AS TOMA3,count(f.toma4) AS TOMA4,"
                    + "	count(f.toma5) AS TOMA5,count(f.toma6) AS TOMA6,count(f.toma7) AS TOMA7,count(f.toma8) AS TOMA8,count(f.toma9) AS TOMA9,count(f.toma10) AS TOMA10,"
                    + "	count(f.toma11) AS TOMA11,count(f.toma12) AS TOMA12,count(f.toma13) AS TOMA13,count(f.toma14) AS TOMA14,count(f.toma15) AS TOMA15,count(f.toma16) AS TOMA16,"
                    + "	count(f.toma17) AS TOMA17,count(f.toma18) AS TOMA18"
                    + " FROM registro_frecuencia_media_hora f inner join registro r on f.id_registro = r.id_registro "
                    + " where r.id_registro = " + irg + "");
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

    public List Validar_entrada_materiales(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT e.* FROM registro_entrada_material e inner join registro r on e.id_registro = r.id_registro where r.id_registro = " + irg + " and e.producto_entrante is null");
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
//</editor-fold>
}
