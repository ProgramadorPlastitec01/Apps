package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ProveedorJpaController
{
  public ProveedorJpaController()
  {
    this.emf = Persistence.createEntityManagerFactory("LocativosPU");
  }
  
  private EntityManagerFactory emf = null;
  
  public EntityManager getEntityManager()
  {
    return this.emf.createEntityManager();
  }
  
  public List proveedores()
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_pro_c_proveedor`()");
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
  
  public List Proveedores_filtro(String fto)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_pro_t_proveedor_filtro`('" + fto + "')");
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
  
  public boolean Registrar_proveedor(String nbe, String emp, String tel, String cor, String des, String uss)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_pro_r_proveedor`('" + nbe + "','" + emp + "','" + tel + "','" + cor + "','" + des + "','" + uss + "')");
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
  
  public List Traer_proveedor(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_pro_t_proveedor`('" + ipro + "')");
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
  
  public boolean Modificar_provedor(int ipro, String nbe, String emp, String tel, String cor, String des, String uss)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_pro_m_proveedor`('" + ipro + "','" + nbe + "','" + emp + "','" + tel + "','" + cor + "','" + des + "','" + uss + "')");
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
  
  public boolean Activar_proveedor(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_pro_m_activar`('" + ipro + "')");
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
  
  public boolean Desactivar_proveedor(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_pro_m_desactivar`('" + ipro + "')");
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
