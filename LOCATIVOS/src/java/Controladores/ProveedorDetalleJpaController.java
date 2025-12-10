package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ProveedorDetalleJpaController
{
  public ProveedorDetalleJpaController()
  {
    this.emf = Persistence.createEntityManagerFactory("LocativosPU");
  }
  
  private EntityManagerFactory emf = null;
  
  public EntityManager getEntityManager()
  {
    return this.emf.createEntityManager();
  }
  
  public boolean Registrar_proveedor_detalle(int id_pro, int id_ext, String pers, String solc)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prvd_r_proveedor_detalle`('" + id_pro + "','" + id_ext + "','" + pers + "','" + solc + "')");
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
  
  public List Traer_proveedor_detalle(int id_prov, int id_pro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prvd_t_proveedor_detalle`('" + id_prov + "','" + id_pro + "')");
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
  
  public boolean Modificar_proveedor_detalle(int id_pro, int id_ext, String pers, String solc)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prvd_m_proveedor_detalle`('" + id_pro + "','" + id_ext + "','" + pers + "','" + solc + "')");
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
  
  public List Traer_todas_las_empresas_externas(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prvd_t_empresas_externas`('" + ipro + "')");
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
  
  public List Traer_personal_externo(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prvd_t_personal_externo`('" + ipro + "')");
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
  
  public boolean ModificarVinculoExterno(int Id_Proveedor, String Vector)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("update proveedor_detalle pd set pd.solicitudes = '" + Vector + "'  where pd.id_proveedor_detalle = " + Id_Proveedor + "");
      int consulta = q.executeUpdate();
      etm.getTransaction().commit();
      etm.clear();
      etm.close();
      if (consulta == 1) {
        return true;
      }
      return false;
    }
    catch (Exception ex) {}
    return false;
  }
  
  public boolean EliminarVinculoExterno(int Id_ProveedorDtalle)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("DELETE from proveedor_detalle where id_proveedor_detalle = " + Id_ProveedorDtalle + "");
      int consulta = q.executeUpdate();
      etm.getTransaction().commit();
      etm.clear();
      etm.close();
      if (consulta == 1) {
        return true;
      }
      return false;
    }
    catch (Exception ex) {}
    return false;
  }
  
  public List ConsultarEliminacion(int Id_Proveedor, int Id_Programacion)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("select pvd.id_proveedor_detalle,pvd.id_programacion,pvd.id_proveedor,pvd.personal,pvd.solicitudes,pv.empresa,pv.descripcion,pv.telefono,pv.correo from proveedor_detalle pvd inner join proveedor pv on pv.id_proveedor = pvd.id_proveedor where pvd.id_programacion = " + Id_Programacion + " and pvd.id_proveedor = " + Id_Proveedor + "");
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
