package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ActividadesAdicionalesJpaController
{
  public ActividadesAdicionalesJpaController()
  {
    this.emf = Persistence.createEntityManagerFactory("LocativosPU");
  }
  
  private EntityManagerFactory emf = null;
  
  public EntityManager getEntityManager()
  {
    return this.emf.createEntityManager();
  }
  
  public boolean Registrar_actividad_adicional(int ipro, String ubi, String acta)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_acta_r_actividad_adicional`('" + ipro + "','" + ubi + "','" + acta + "')");
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
  
  public boolean Eliminar_actividad_adicional(int actd)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_acta_e_eliminar_actividad_adicional`('" + actd + "')");
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
  
  public boolean Modificar_actividad(int iact, String ubi, String acta)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_acta_m_actividad_adicional`('" + iact + "','" + ubi + "','" + acta + "')");
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
  
  public List Consultar_actividades_adicionales(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_acta_c_actividades_adicionales_programacion`('" + ipro + "')");
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
  
  public List Contador_de_actividades(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_acta_c_contador_de_actividades_por_programacion`('" + ipro + "')");
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
  
  public List Traer_Activades_Id(int Id_ActA)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("select * from actividades_adicionales act where act.id_actividad_adicional = " + Id_ActA + " ");
      List Consulta = q.getResultList();
      etm.getTransaction().commit();
      etm.clear();
      etm.close();
      if (Consulta.isEmpty()) {
        return null;
      }
      return Consulta;
    }
    catch (Exception e) {}
    return null;
  }
}
