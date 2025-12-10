package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ProgramacionJpaController
{
  public ProgramacionJpaController()
  {
    this.emf = Persistence.createEntityManagerFactory("LocativosPU");
  }
  
  private EntityManagerFactory emf = null;
  
  public EntityManager getEntityManager()
  {
    return this.emf.createEntityManager();
  }
  
  public boolean Registrar_programacion(String npro, String fchi, String fchf, String urg, String obs, String rei)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prm_r_programacion`('" + npro + "','" + fchi + "','" + fchf + "','" + urg + "','" + obs + "','" + rei + "')");
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
  
  public List Consultar_Programacion()
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prm_c_programacion`()");
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
  
  public List Traer_programacion_id(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prm_c_traer_programacion_id`('" + ipro + "')");
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
  
  public List Traer_solicitudes_programadas(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prm_t_solicitudes_id_programacion`(" + ipro + ")");
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
  
  public boolean Modificar_programacion(int ipro, String npro, String fin, String ffi, String obs, String rei)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prm_m_programacion`('" + ipro + "','" + npro + "','" + fin + "','" + ffi + "','" + obs + "','" + rei + "')");
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
  
  public boolean Modificar_estado_programacion(int ipro, int est)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("UPDATE programacion SET estado = " + est + " WHERE id_programacion = " + ipro + "");
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
  
  public List Traer_estado_de_programacion_programador(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("SELECT id_programacion,estado FROM programacion WHERE id_programacion =" + ipro + " AND estado = 3");
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
  
  public List Traer_estado_de_programacion_ejecutor(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("SELECT id_programacion,estado FROM programacion WHERE id_programacion =" + ipro + " AND estado = 2");
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
      Query q = etm.createNativeQuery("CALL `sp_test`('" + ipro + "')");
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
  
  public List Traer_progrmacion_p_solictud(int Id_solicitud)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("select p.id_programacion,p.nombre_programacion,p.fecha_inicio,p.fecha_fin,p.observacion,p.estado\nfrom programacion p inner join programacion_detalle \non p.id_programacion = programacion_detalle.id_programacion\nwhere programacion_detalle.id_programacion_detalle = " + Id_solicitud + "");
      
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
  
  public List Filtro_Imprimir(String Cery)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery(Cery);
      List consulta = q.getResultList();
      etm.clear();
      etm.close();
      if (consulta != null) {
        return consulta;
      }
      return null;
    }
    catch (Exception e) {}
    return null;
  }
  
  public List Traer_Sol_Prove(int id_programacion, int Proveedor)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("select pv.solicitudes from proveedor_detalle pv where pv.id_programacion = " + id_programacion + " and pv.id_proveedor = " + Proveedor + "");
      List consulta = q.getResultList();
      etm.clear();
      etm.close();
      if (consulta != null) {
        return consulta;
      }
      return null;
    }
    catch (Exception e) {}
    return null;
  }
  
  public boolean EnviarEmail(int Id_Programacion, int Email)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("UPDATE programacion p set p.Email = " + Email + " where p.id_programacion = " + Id_Programacion + ";");
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
  
  public List EstadoPrograma()
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("SELECT * FROM programacion p where p.estado = 1 or p.estado = 3;");
      List exitoso = q.getResultList();
      etm.getTransaction().commit();
      etm.clear();
      etm.close();
      if (exitoso.isEmpty()) {
        return null;
      }
      return exitoso;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public List Validar_actividades_programacion(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("select pd.id_solicitud,(select count(a.id_actividades) from actividad a where a.id_programacion_detalle = pd.id_programacion_detalle) from programacion p inner join programacion_detalle pd on p.id_programacion = pd.id_programacion where p.id_programacion = " + ipro + " and (select count(a.id_actividades) from actividad a where a.id_programacion_detalle = pd.id_programacion_detalle) = 0");
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
  
  public List Validar_solicitudes_programacion(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("select pd.id_solicitud,(select count(pdt.id_solicitud) from programacion pr inner join programacion_detalle pdt on pr.id_programacion = pdt.id_programacion  where pr.id_programacion = " + ipro + " and pdt.id_solicitud = pd.id_solicitud) from programacion p inner join programacion_detalle pd on p.id_programacion = pd.id_programacion  where p.id_programacion = " + ipro + " group by pd.id_solicitud");
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
