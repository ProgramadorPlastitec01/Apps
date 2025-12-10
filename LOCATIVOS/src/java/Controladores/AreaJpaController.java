package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class AreaJpaController
{
  public AreaJpaController()
  {
    this.emf = Persistence.createEntityManagerFactory("LocativosPU");
  }
  
  private EntityManagerFactory emf = null;
  
  public EntityManager getEntityManager()
  {
    return this.emf.createEntityManager();
  }
  
  public List Areas()
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_area_c_area`()");
      List consulta = q.getResultList();
      etm.getTransaction().commit();
      etm.clear();
      etm.close();
      if (consulta.isEmpty()) {
        return null;
      }
      return consulta;
    }
    catch (Exception ex) {}
    return null;
  }
  
  public List Traer_area_id(int iar)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("SELECT a.id_area, a.siglatura,a.nombre FROM area a WHERE a.id_area = " + iar + "");
      List consulta = q.getResultList();
      etm.getTransaction().commit();
      etm.clear();
      etm.close();
      if (consulta.isEmpty()) {
        return null;
      }
      return consulta;
    }
    catch (Exception ex) {}
    return null;
  }
}
