package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class NovedadOrdenJpaController {

    public NovedadOrdenJpaController() {
        emf = Persistence.createEntityManagerFactory("PMPPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Traer_novedades_orden(int iot) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_nod_t_novedades_id`('" + iot + "')");
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

    public boolean Registrar_novedad(int iot, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_nod_r_novedad`('" + iot + "','" + urg + "')");
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

    public boolean Actualizar_novedad_OT(int ino, String vlr1, String vlr2, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE novedad_orden SET asunto = '" + vlr1 + "', descripcion = '" + vlr2 + "',usuario_registro = '" + urg + "',fecha_registro = now() WHERE id_novedad_orden = " + ino + "");
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
//    public boolean Actualizar_novedad_OT(int ino, String atb, String vlr, String urg) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("UPDATE novedad_orden SET " + atb + " = '" + vlr + "',usuario_registro = '" + urg + "',fecha_registro = now() WHERE id_novedad_orden = " + ino + "");
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
}
