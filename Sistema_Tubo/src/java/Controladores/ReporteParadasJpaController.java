package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ReporteParadasJpaController implements Serializable {

    public ReporteParadasJpaController() {
        emf = Persistence.createEntityManagerFactory("Sistema_TuboPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultJustifies() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rollh_c_contadorDeJustificaicones`()");
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

    public List ConsultJustifies_v2(String consult) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        String query = "SELECT"
                + consult;
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

    public List ConsultRolloxRegister(String justi, String txtRegister) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        String query = "SELECT r.id_registro, r.id_rollo, r.numero_rollo, t.lote_producto, t.turno_pr "
                + "FROM rollo_h h  "
                + "INNER JOIN rollo r ON h.id_rollo = r.id_rollo  "
                + "INNER JOIN registro t ON r.id_registro = t.id_registro "
                + "WHERE h.justificacion LIKE '%" + justi + "%' AND h.estado = 4 AND t.id_registro IN (" + txtRegister + ")";
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

    public List ConsultRolloxRegister_clean(int idorder, String txtRegister) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
//        String query = "SELECT r.id_rollo, r.id_registro, r.numero_rollo, r.diametro_interno, r.diametro_externo, r.espesor_pared_1, r.espesor_pared_2, "
//                + "r.espesor_pared_3, r.espesor_pared_4, r.presion_inyectada, r.peso_rollo, rugosidad_1, r.rugosidad_2, "
//                + "r.rugosidad_3, r.rugosidad_4, r.limpieza, r.inspeccion_visual, r.estado, r.usuario_registro, r.fecha_registro "
//                + "FROM rollo r "
//                + "INNER JOIN registro rt ON r.id_registro = rt.id_registro "
//                + "INNER JOIN orden_produccion o ON rt.id_orden = o.id_orden "
//                + "WHERE o.id_orden = "+ idorder +" and r.id_registro IN ("+txtRegister+") AND r.limpieza > 0 "
//                + "ORDER BY r.numero_rollo ASC";
        String query = "SELECT o.id_orden, o.numero, r.id_registro, r.id_rollo,r.numero_rollo, r.limpieza, r.estado, "
                + "h.estado, h.justificacion, h.usuario_registro, h.fecha_registro "
                + "FROM rollo r  "
                + "INNER JOIN registro rt ON r.id_registro = rt.id_registro  "
                + "INNER JOIN orden_produccion o ON rt.id_orden = o.id_orden  "
                + "INNER JOIN rollo_h h ON r.limpieza = h.id_rollo_h "
                + "WHERE o.id_orden = " + idorder + " and r.id_registro IN (" + txtRegister + ") AND r.limpieza > 0  "
                + "GROUP BY r.limpieza "
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

}
