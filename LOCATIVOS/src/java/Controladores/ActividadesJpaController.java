package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ActividadesJpaController {

    public ActividadesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("LocativosPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    public boolean Registrar_Actividades(int iprmd, String act, String arl, String uss) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_act_r_registrar_actividades`('" + iprmd + "','" + act + "','" + arl + "','" + uss + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public List Consultar_actividades() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_act_c_consultar_actividades`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            }
            return consulta;
        } catch (Exception ex) {
        }
        return null;
    }

    public List Consultar_actividades_programacion(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_act_c_consultar_actividades_programacion`('" + ipd + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            }
            return consulta;
        } catch (Exception ex) {
        }
        return null;
    }

    public boolean Modificar_actividad(int iact, String act, String arl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_act_m_modificar_actividad`('" + iact + "','" + act + "','" + arl + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public boolean Eliminar_actividad(int iact) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_act_e_eliminar_actividad`('" + iact + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public boolean registrar_ejecucion(int pdt, int act, String eje, String arl, String obs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_act_m_registrar_ejecucion`('" + pdt + "','" + act + "','" + eje + "','" + arl + "','" + obs + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public List Solicitudes_no_ejecutadas(int isol, int prm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_act_c_solicitudes_no_ejecutadas`('" + isol + "','" + prm + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            }
            return consulta;
        } catch (Exception ex) {
        }
        return null;
    }

    public List Actividades_pendientes(int ipdt) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_act_t_traer_actividades_pendientes`('" + ipdt + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            }
            return consulta;
        } catch (Exception ex) {
        }
        return null;
    }

    public List Filtro_Solicitud(int Id_Programacion, int Id_solicitud) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("Select * from solicitud inner join programacion_detalle\non solicitud.id_solicitudes = programacion_detalle.id_solicitud\ninner join programacion \non programacion_detalle.id_programacion = programacion.id_programacion\nwhere programacion.id_programacion = " + Id_Programacion + " and  solicitud.id_solicitudes = " + Id_solicitud + ";");

            List Filtro = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (Filtro != null) {
                return Filtro;
            }
            return null;
        } catch (Exception e) {
        }
        return null;
    }

    public List Lista_Proveedores() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select * from proveedor;");
            List Proveedores = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (Proveedores != null) {
                return Proveedores;
            }
            return null;
        } catch (Exception e) {
        }
        return null;
    }

    public List Lista_Id_Actividades(int Id_Actividad) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select a.id_actividades,a.actividad,a.area_lista,concat(s.planta,'<br />',pd.ubicacion_final),pd.id_usuario_entrega,Concat(u.nombres,' ',u.apellidos),pd.id_solicitud,pd.id_programacion_detalle,a.ejecucion,a.observacion,s.id_solicitudes from actividad a left join programacion_detalle pd on pd.id_programacion_detalle = a.id_programacion_detalle left join usuario u on pd.id_usuario_entrega = u.id_usuario left join solicitud s on pd.id_solicitud = s.id_solicitudes where a.id_actividades = " + Id_Actividad + " order by concat(pd.ubicacion_final,'<br />',s.planta);");
            List Proveedores = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (Proveedores != null) {
                return Proveedores;
            }
            return null;
        } catch (Exception e) {
        }
        return null;
    }

    public List Lista_No_Ejecutadas(int Programacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT pd.id_solicitud, pd.id_programacion, pd.id_programacion_detalle, a.ejecucion from actividad a inner join programacion_detalle pd on a.id_programacion_detalle = pd.id_programacion_detalle where  pd.id_programacion = " + Programacion + " order by pd.id_solicitud ;");
            List Proveedores = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (Proveedores.isEmpty()) {
                return null;
            }
            return Proveedores;
        } catch (Exception e) {
        }
        return null;
    }
}
