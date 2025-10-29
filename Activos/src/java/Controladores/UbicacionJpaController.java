package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UbicacionJpaController implements Serializable {

    public UbicacionJpaController() {
        emf = Persistence.createEntityManagerFactory("ActivosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean registrarUbicacion(String pta, String bga, String pso) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ubc_r_ubicacion`('"+pta+"', '"+bga+"', '"+pso+"')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean modificarUbicacion(int iub, String pta, String bga, String pso) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ubc_m_ubicacion`('"+iub+"','"+pta+"', '"+bga+"', '"+pso+"')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean desactivarUbicacion(int iub) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ubc_m_desactivar`('"+iub+"')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean activarUbicacion(int iub) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ubc_m_activar`('"+iub+"')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public List consultarUbicaciones(){
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try{
            Query q = etm.createNativeQuery("CALL `sp_ubc_t_ubicaciones`()");
               List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public List consultarUbicacion(int iub){
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try{
            Query q = etm.createNativeQuery("CALL `sp_ubc_c_ubicacion`('"+iub+"')");
               List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
