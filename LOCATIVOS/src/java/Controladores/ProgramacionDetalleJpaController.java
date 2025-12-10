package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ProgramacionDetalleJpaController
{
  public ProgramacionDetalleJpaController()
  {
    this.emf = Persistence.createEntityManagerFactory("LocativosPU");
  }
  
  private EntityManagerFactory emf = null;
  
  public EntityManager getEntityManager()
  {
    return this.emf.createEntityManager();
  }
  
  public boolean Registrar_programacion_detalle(int isol, int ipro, String dvl)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_r_programacion_detalle`('" + isol + "','" + ipro + "','" + dvl + "')");
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
  
  public boolean Registrar_programacion_detalle_seguimiento(int isol, int ipro, String dvl, String desr)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_r_programacion_detalle_seguimiento`('" + isol + "','" + ipro + "','" + dvl + "','" + desr + "')");
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
  
  public List Programacion_calendario()
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prm_consultar_calendario`()");
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
  
  public List Consultar_programaciones_de_solicitud(int iar, String cls)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_c_programaciones_de_solicitud`('" + iar + "','" + cls + "')");
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
  
  public List Consultar_solicitudes_por_area()
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_c_solicitudes_areas`()");
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
  
  public List Consultar_areas_solicitud(int iar, String tsl)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_c_areas_prioridades`('" + iar + "','" + tsl + "')");
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
  
  public List Traer_id_Programacion_detalle()
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_c_traer_id_detalle`()");
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
  
  public boolean Registrar_programacion_d_actividades(int ipdt, String ubf)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_m_actualizar_registro_actividad`('" + ipdt + "','" + ubf + "')");
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
  
  public List Consultar_programacion_detalle(int ipd)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_c_programacion_detalle`('" + ipd + "')");
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
  
  public boolean Modificar_registro_actividad(int ipdt, String ubf)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_m_actualizar_registro_actividad`('" + ipdt + "','" + ubf + "')");
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
  
  public boolean Modificar_registro_actividad_en_tabla_usuario(int isol, String ubf)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_m_ubicacion_final`('" + isol + "', '" + ubf + "')");
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
  
  public boolean Registrar_ejecucion(int ipd, String fce, String dse)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_m_registrar_ejecucion`('" + ipd + "','" + fce + "','" + dse + "')");
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
  
  public List Consultar_prograciones_pendientes(int isol)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prm_c_consultar_programaciones_pendientes`('" + isol + "')");
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
  
  public boolean Registrar_detalles_de_solicitud_pendiente(int isol, int iuse, String ubf)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_m_programacion_detalle_pendiente`('" + isol + "','" + iuse + "','" + ubf + "')");
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
  
  public boolean Registrar_actividades_de_solicitud_pendiente(int ipmd, String act, String arl)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_act_m_modificar_solicitud_pendiente`('" + ipmd + "','" + act + "','" + arl + "')");
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
  
  public boolean Modificar_estado_programacion_detalle(int isol, int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("UPDATE programacion_detalle pd SET pd.estado = 2 where pd.id_solicitud = " + isol + " AND pd.id_programacion = " + ipro + "");
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
  
  public List Traer_programacion_detalle_pendiente(int isol, int ipg)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("SELECT pd.id_programacion_detalle,pd.id_programacion FROM programacion_detalle pd where pd.id_programacion = '" + ipg + "' AND pd.id_solicitud = '" + isol + "'");
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
  
  public List Traer_ultima_solicitud_pendiente(int isol)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("SELECT id_programacion_detalle,id_programacion,id_solicitud,estado FROM programacion_detalle WHERE id_solicitud =" + isol + " ORDER BY id_programacion_detalle DESC LIMIT 1");
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
  
  public List Traer_ubicacion_de_solicitud(int ipro, int isol)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prm_t_ubicacion_de solicitudes`('" + ipro + "', '" + isol + "')");
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
  
  public boolean Eliminar_programacion_detalle(int iprog, int isol)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_e_eliminar_solicitud`(" + iprog + "," + isol + ")");
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
  
  public List traer_actividades_por_solicitud(int ipd)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_t_traer_cantidad_actividades_por_solicitud`('" + ipd + "')");
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
  
  public List traer_Solicitudes_segun_estado(int isol, int iprog)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_t_solicitudes_segun_estado`('" + isol + "','" + iprog + "')");
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
  
  public List contador_de_solicitudes_por_programacion(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_t_contador_de_areas_por_programacion`('" + ipro + "')");
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
  
  public List contador_de_actividades_por_programacion(int ipro)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_t_contador_de_actividades_por_programacion`('" + ipro + "')");
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
  
  public List Consultar_informacion_de_solicitud(int ipd)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("CALL `sp_prmd_t_informacion_solicitud`('" + ipd + "')");
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
  
  public boolean Registrar_observacion(int id_sol, String obs)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("update programacion_detalle pd set pd.clasificacion_entrega =" + obs + " where pd.id_solicitud = " + id_sol + "");
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
  
  public List Progrmacion_detalle_id_Actividad(int Id_Actividad)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("select a.id_actividades,a.actividad,a.area_lista,concat(s.planta,'<br />',pd.ubicacion_final),pd.id_usuario_entrega,Concat(u.nombres,' ',u.apellidos),pd.id_solicitud,pd.id_programacion_detalle,a.ejecucion,a.observacion,s.id_solicitudes from actividad a left join programacion_detalle pd on pd.id_programacion_detalle = a.id_programacion_detalle left join usuario u on pd.id_usuario_entrega = u.id_usuario left join solicitud s on pd.id_solicitud = s.id_solicitudes where a.id_actividades = " + Id_Actividad + " order by concat(pd.ubicacion_final,'<br />',s.planta)");
      List Consilta = q.getResultList();
      etm.getTransaction().commit();
      etm.clear();
      etm.close();
      if (Consilta.isEmpty()) {
        return null;
      }
      return Consilta;
    }
    catch (Exception ex) {}
    return null;
  }
  
  public boolean ModificarDiv(String division, int Id_Pogra_Deta)
  {
    EntityManager etm = getEntityManager();
    etm.getTransaction().begin();
    try
    {
      Query q = etm.createNativeQuery("update programacion_detalle p set p.division_locativos = '" + division + "' where p.id_programacion_detalle = " + Id_Pogra_Deta + "");
      int resultado = q.executeUpdate();
      etm.getTransaction().commit();
      etm.clear();
      etm.close();
      if (resultado == 1) {
        return false;
      }
      return true;
    }
    catch (Exception ex) {}
    return false;
  }
}
