package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class AreaJpaController implements Serializable {

      public AreaJpaController() {
        emf = Persistence.createEntityManagerFactory("ActivosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultarAreas() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ara_t_areas`()");
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
    public List consultarArea (int iar){
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try{
            Query q = etm.createNativeQuery("CALL `sp_ara_c_area`('"+iar+"')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            }else{
                return consulta;
            }
       }catch(Exception e){
            return null;
        }
    }
    public boolean registrarArea(String nom, String sgl, String cro){
        EntityManager etm = getEntityManager( );
        etm.getTransaction().begin();
        try{
            Query q = etm.createNativeQuery("CALL `sp_ara_r_area`('"+nom+"', '"+sgl+"', '"+cro+"')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public boolean modificarArea(int iar, String nbe, String sgl, String cro){
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try{
            Query q = etm.createNativeQuery("CALL `sp_ara_m_area`('"+iar+"', '"+nbe+"', '"+sgl+"', '"+cro+"')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public boolean desactivarArea(int iar){
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try{
            Query q = etm.createNativeQuery("CALL `sp_ara_m_desactivar`('"+iar+"')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public boolean activarArea(int iar){
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try{
            Query q = etm.createNativeQuery("CALL `sp_ara_m_activar`('"+iar+"')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
