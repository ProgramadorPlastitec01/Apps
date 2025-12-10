package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UbicacionJpaController
{
  public UbicacionJpaController()
  {
    this.emf = Persistence.createEntityManagerFactory("LocativosPU");
  }
  
  private EntityManagerFactory emf = null;
  
  public EntityManager getEntityManager()
  {
    return this.emf.createEntityManager();
  }
  
  public List Ubicaciones()
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CAll `sp_ubicacion_list_Ubicaciones`()");
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
  
  public boolean Registrar_ubicacion(String Nombre, String Tipo, String Area)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_ubc_r_ubicacion`('" + Nombre + "','" + Tipo + "','" + Area + "')");
      int exitoso = q.executeUpdate();
      etm.getTransaction().commit();
      etm.clear();
      etm.close();
      if (exitoso == 0) {
        return false;
      }
      return true;
    }
    catch (Exception ex) {}
    return false;
  }
  
  public List Traer_ubicacion(int Id_Ubicacion)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_ubc_t_traer_ubc`(" + Id_Ubicacion + ")");
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
  
  public boolean Modificar_ubicacion(int Id_Ubicacion, String Nombre, String Tipo, String Area)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_ubc_m_ubicacion`(" + Id_Ubicacion + ",'" + Nombre + "','" + Tipo + "','" + Area + "')");
      int exitoso = q.executeUpdate();
      etm.getTransaction().commit();
      etm.clear();
      etm.close();
      if (exitoso == 0) {
        return false;
      }
      return true;
    }
    catch (Exception ex) {}
    return false;
  }
  
  public boolean Activar_ubicacion(int Id_Ubicacion, int Estado)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_ubc_m_est_ubicacion`(" + Id_Ubicacion + "," + Estado + ")");
      int exitoso = q.executeUpdate();
      etm.getTransaction().commit();
      etm.clear();
      etm.close();
      if (exitoso == 0) {
        return false;
      }
      return true;
    }
    catch (Exception ex) {}
    return false;
  }
}
