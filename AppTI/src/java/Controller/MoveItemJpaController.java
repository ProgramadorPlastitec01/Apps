package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class MoveItemJpaController implements Serializable {

    public MoveItemJpaController() {
        emf = Persistence.createEntityManagerFactory("AppTIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Object[]> compareItems(List<String> itms, String DatIn, String DatFn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();

        try {
            String ids = String.join(",", itms);

            Query q = etm.createNativeQuery(
                    "SELECT m.id_mov_item, m.id_item, m.mov_type, m.mov_num, m.mov_date "
                    + "FROM mov_item m "
                    + "WHERE m.mov_num IN (" + ids + ") AND m.mov_date BETWEEN '" + DatIn + "' AND '" + DatFn + "'"
            );

            List<Object[]> consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();

            return consulta.isEmpty() ? null : consulta;
        } catch (Exception ex) {
            return null;
        }
    }

    public List ConsultSetting(String vlid) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT m.id_mov_item, m.mov_date, m.id_item, i.item_num, f.cod_reference, f.ref_name, m.mov_type, m.mov_num, m.mov_location, "
                    + "i.model, i.i_serial, m.mov_obs, m.user_reg, CONCAT(u.name, ' ', u.lastname) AS 'Nombres', m.user_verify "
                    + "FROM mov_item m  "
                    + "INNER JOIN user u ON m.user_reg = u.id_user "
                    + "INNER JOIN item i ON m.id_item = i.id_item "
                    + "INNER JOIN fact_reference f ON i.id_reference = f.id_reference "
                    + "WHERE " + vlid + "");

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

    //<editor-fold defaultstate="collapsed" desc="PROCESZ">
    public boolean RegisterItemMoveNew(int item, String mov_type, String num_mov, String dateMov, String locationMov, String obsMov, int userReg, int userMod) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_mim_r_RegisterItemMove`(" + item + ",'" + mov_type + "','" + num_mov + "','" + dateMov + "','" + locationMov + "', '" + obsMov + "', " + userReg + ", " + userMod + ")");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean RegisterItemMove(String data) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("INSERT INTO mov_item (id_item, mov_type, mov_num, mov_date, mov_location, mov_obs, user_reg, user_mod) "
                    + "VALUES " + data + ";");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
//</editor-fold>

}
