package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RepuestoOrdenJpaController {

    public RepuestoOrdenJpaController() {
        emf = Persistence.createEntityManagerFactory("PMPPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // REPUESTOS ORDEN TRABAJO
    public List Traer_repuestos_orden(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rod_t_repuestos_orden`(" + iot + ")");
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

    public boolean Registrar_repuesto(int iot, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rod_r_repuesto`('" + iot + "','" + urg + "')");
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

//    public boolean Actualizar_repuesto_OT(int iro, String atb, String vlr, String urg) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("UPDATE repuesto_orden SET " + atb + " = '" + vlr + "',usuario_registro = '" + urg + "',fecha_registro = now() WHERE id_repuesto_orden = " + iro + "");
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

    public boolean Actualizar_repuesto_OT(int iro, String vlr1, String vlr2, String vlr3, String vlr4, String vlr5, String vlr6, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE repuesto_orden r SET r.referencia = '" + vlr1 + "',r.cumple_especificaciones = '" + vlr2 + "',r.cantidad = '" + vlr3 + "',r.requeridos = '" + vlr4 + "',r.utilizados = '" + vlr5 + "',r.justificacion = '" + vlr6 + "',r.usuario_registro = '" + urg + "',r.fecha_registro = now() WHERE r.id_repuesto_orden = " + iro + "");
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
}
