package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistroCalidadJpaController implements Serializable {

    public RegistroCalidadJpaController() {
        emf = Persistence.createEntityManagerFactory("Sistema_TuboPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="lIST">
    public List ActiveOrder() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_orden_c_consultarOrdenesActivas`()");
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

    public List consultOrder_id(int idOrder) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_orden_c_consultarOrden_id`('" + idOrder + "')");
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

    public List consultLotesxRegistro(int idOrder, String TxtFecha) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_registro_c_ConsultarLotesxRegistro`('" + idOrder + "','" + TxtFecha + "')");
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

    public List ConsultParameterxCategory(String TxtCategory) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_c_consultarParametrosxCategoria`('" + TxtCategory + "')");
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

    public List ConsultRegistroCalidadVigente(String TxtFecha) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_c_consultarRegistrosCalidad`('" + TxtFecha + "')");
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

    public List ConsultLotesxOrder(int idOrder) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_certificate_c_consultarLotesXOrden_all_v2`('" + idOrder + "')");
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

    public List ConsultRegistersxOrder_all(int idOrder, String txtLote) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_certificate_c_consultarLotesXOrden`('" + idOrder + "','" + txtLote + "')");
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

    public List ConsultRegistroFechasxOrden(int NroOrden) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_c_registroConsultarFechasxOrden`('" + NroOrden + "')");
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

    public List ConsultRegistroXfechaXlote(String TxtFecha, String TxtLote, int Line) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_registro_c_consultarRegistrosxFechaxLote`('" + TxtFecha + "','" + TxtLote + "','" + Line + "')");
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

    public List ConsultOrdenxRegistroXfechaXlote(int idOrden, String TxtFecha, String TxtLote) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_registro_c_consultarOrdenXRegistroxFechaxLotexLine`('" + idOrden + "','" + TxtFecha + "','" + TxtLote + "')");
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

    public List ConsultFootageVerificationxRegister(int idparam, String txtRegister) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        String query = "SELECT\n"
                + "(SELECT items \n"
                + "	 FROM verificacion_metraje v\n"
                + "	 WHERE v.id_parametro = " + idparam + " AND v.id_registro IN (" + txtRegister + ") AND v.turnos = 'Turno 1') AS 'Turno 1',\n"
                + "(SELECT items \n"
                + "	 FROM verificacion_metraje v\n"
                + "	 WHERE v.id_parametro = " + idparam + " AND v.id_registro IN (" + txtRegister + ") AND v.turnos = 'Turno 2') AS 'Turno 2',\n"
                + "(SELECT items \n"
                + "	 FROM verificacion_metraje v\n"
                + "	 WHERE v.id_parametro = " + idparam + " AND v.id_registro IN (" + txtRegister + ") AND v.turnos = 'Turno 3') AS 'Turno 3'\n"
                + "";
        try {
            Query q = etm.createNativeQuery(query);
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
//    public List ConsultFootageVerificationxRegister(int idparam, String txtRegister) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        String query = "SELECT id_verificacion,id_registro,id_parametro,items,turnos,usuario_registro,fecha_registros "
//                + "FROM verificacion_metraje v "
//                + "WHERE v.id_parametro = " + idparam + " AND v.id_registro IN (" + txtRegister + ")";
//        try {
//            Query q = etm.createNativeQuery(query);
//            List consulta = q.getResultList();
//            etm.getTransaction().commit();
//            etm.clear();
//            etm.close();
//            if (!consulta.isEmpty()) {
//                return consulta;
//            } else {
//                return null;
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//    }

    public List ConsultControlCoilxRegister(String txtRegister) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        String query = "SELECT c.id_control,c.rollo, r.numero_rollo ,c.id_registro,c.turno_hora,c.diametro_medida_1,c.diametro_medida_2,c.codigo_galga,c.codigo_tambor, "
                + "c.concepto, if(c.concepto = 1, 'Cumple', if(c.concepto = 2, 'No cumple', 'N/A')) AS 'Concepto' ,c.usuario_registros,c.fecha_registro "
                + "FROM control_interno_bobina c "
                + "INNER JOIN rollo r ON c.rollo = r.id_rollo "
                + "WHERE c.id_registro IN (" + txtRegister + ")";
        try {
            Query q = etm.createNativeQuery(query);
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

    public List ConsultRolloxRegister(int idorder, String txtRegister) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        String query = "SELECT r.id_rollo, r.id_registro, r.numero_rollo, r.interno_sin_presurizar, r.interno_prezurizado, r.externo_sin_presurizar, r.externo_presurizado, r.espesor_pared_1, r.espesor_pared_2, "
                + "r.espesor_pared_3, r.espesor_pared_4, r.presion_inyectada, r.peso_rollo, rugosidad_1, r.rugosidad_2, r.rugosidad_3, r.rugosidad_4, "
                + "r.inspeccion_visual, r.estado, r.usuario_registro, r.fecha_registro "
                + "FROM rollo r "
                + "INNER JOIN registro rt ON r.id_registro = rt.id_registro "
                + "INNER JOIN orden_produccion o ON rt.id_orden = o.id_orden "
                + "WHERE r.estado = 1 AND  o.id_orden = " + idorder + " and r.id_registro IN (" + txtRegister + ")"
                + "ORDER BY r.numero_rollo ASC";
        try {
            Query q = etm.createNativeQuery(query);
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

    public List ConsultResponsiblexRegisterPI(String txtRegister, String shift) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        String query = "SELECT rl.id_registro,rl.usuario_registro "
                + "FROM rollo rl "
                + "INNER JOIN registro r ON rl.id_registro = r.id_registro "
                + "WHERE r.id_registro IN (" + txtRegister + ") AND r.turno_pr = '" + shift + "' "
                + "AND rl.rugosidad_1 IS NULL "
                + "GROUP BY rl.usuario_registro";
        try {
            Query q = etm.createNativeQuery(query);
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

    public List ConsultResponsiblexRegisterGC(String txtRegister, String shift) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        String query = "SELECT rl.id_registro,rl.usuario_registro "
                + "FROM rollo rl "
                + "INNER JOIN registro r ON rl.id_registro = r.id_registro "
                + "WHERE r.id_registro IN (" + txtRegister + ") AND r.turno_pr = '" + shift + "' "
                + "AND rl.rugosidad_1 IS NOT NULL "
                + "GROUP BY rl.usuario_registro";
        try {
            Query q = etm.createNativeQuery(query);
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

    public List ConsultNozzleGC(String txtRegister) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        String query = "SELECT c.id_boquilla,c.id_rollo,r.numero_rollo,c.turno_hora,c.realizado,c.verificado,c.usuario_registro,c.fecha_registro "
                + "FROM control_boquilla c "
                + "INNER JOIN rollo r ON c.id_rollo = r.id_rollo "
                + "INNER JOIN registro rg ON r.id_registro = rg.id_registro "
                + "WHERE rg.id_registro IN (" + txtRegister + ")";
        try {
            Query q = etm.createNativeQuery(query);
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

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PROCESS">
//</editor-fold>
}
