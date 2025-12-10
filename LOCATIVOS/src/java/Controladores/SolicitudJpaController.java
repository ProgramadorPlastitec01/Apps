package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class SolicitudJpaController {

    public SolicitudJpaController() {
        this.emf = Persistence.createEntityManagerFactory("LocativosPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    public List Solicitudes(int iare, String rol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = null;
            if (rol.equals("Administrador")) {
                q = etm.createNativeQuery("select s.id_solicitudes,CONCAT(DATE_FORMAT(s.fecha_registro, '%Y-%m-%d'),' ',DATE_FORMAT(s.fecha_registro, '%H:%i:%p')),concat(u.nombres,' ',u.apellidos),s.ubicacion_solicitante,s.descripcion_solicitud,c.nombre,s.estado as estado_solicitud,s.planta,s.solicitud_principal from solicitud s inner join usuario u on u.id_usuario = s.id_usuario_solicitud inner join clasificacion c on c.nombre = s.clasificacion_solicitud order by s.fecha_registro DESC");
            } else {
                q = etm.createNativeQuery("CALL `sp_sol_c_solicitudes`('" + iare + "')");
            }
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

    public List Traer_todas_las_solicitudes() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = null;
            q = etm.createNativeQuery("select s.id_solicitudes,CONCAT(DATE_FORMAT(s.fecha_registro, '%Y-%m-%d'),' ',DATE_FORMAT(s.fecha_registro, '%H:%i:%p')),concat(u.nombres,' ',u.apellidos),s.ubicacion_solicitante,s.descripcion_solicitud,c.nombre,s.estado as estado_solicitud,s.planta,s.solicitud_principal from solicitud s inner join usuario u on u.id_usuario = s.id_usuario_solicitud inner join clasificacion c on c.nombre = s.clasificacion_solicitud order by s.fecha_registro DESC;");
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

    public List Solicitudes_estado(int est, String fini, String ffin) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = null;
            q = etm.createNativeQuery("select s.id_solicitudes,CONCAT(DATE_FORMAT(s.fecha_registro, '%Y-%m-%d'),' ',DATE_FORMAT(s.fecha_registro, '%H:%i:%p')),concat(u.nombres,' ',u.apellidos),s.ubicacion_solicitante,s.descripcion_solicitud,c.nombre,s.estado as estado_solicitud,s.planta,s.solicitud_principal from solicitud s inner join usuario u on u.id_usuario = s.id_usuario_solicitud inner join clasificacion c on c.nombre = s.clasificacion_solicitud where s.estado =" + est + " and s.fecha_registro BETWEEN '" + fini + " 00:01:00' and '" + ffin + " 23:59:59'  order by s.fecha_registro DESC");

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

    public List Solicitudes_filtro(String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_t_solicitud_filtro`('" + fto + "')");
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

    public boolean Modificar_Solicitud(int isol, String ubs, String des, String cls, String pla) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_m_solicitud`('" + isol + "','" + ubs + "','" + des + "','" + cls + "','" + pla + "')");
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

    public List Traer_Solicitud(int isol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_t_solicitud`('" + isol + "')");
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

    public boolean Registrar_solicitud(int iusu, String ubs, String des, String cls, String pla) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_r_solicitud`('" + iusu + "','" + ubs + "','" + des + "','" + cls + "','" + pla + "')");
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

    public boolean Solicitud_estado(int isol, int est) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE solicitud SET estado = " + est + " WHERE id_solicitudes = " + isol + "");
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

    public boolean Declinar_solicitud(int isol, int est, String jdc, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE solicitud SET confirmacion_correo=0,estado_anterior = estado,estado = " + est + ",justificacion_declinacion='" + jdc + "',usuario_declina='" + urg + "' WHERE id_solicitudes = " + isol + "");
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

    public boolean Confirmar_declinar_solicitud(int isol, int tipo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE solicitud SET confirmacion_correo=1,estado = " + ((tipo == 0) ? "estado_anterior" : "10") + " WHERE id_solicitudes = " + isol + "");
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

    public List Finalizar_solicitud(int isol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select s.id_solicitudes,pd.id_programacion_detalle,a.id_actividades,a.ejecucion from actividad a inner join programacion_detalle pd on a.id_programacion_detalle = pd.id_programacion_detalle inner join solicitud s on pd.id_solicitud = s.id_solicitudes where s.id_solicitudes = " + isol + "");
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

    public List Traer_solicitudes_con_programacion_detalle(int isol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_t_traer solicitudes_con_programacion_detalle`('" + isol + "')");
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

    public List Traer_actividades_solicitudes(int ipmdt) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_t_traer_actividades_solicitudes`('" + ipmdt + "')");
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

    public boolean registrar_solicitud_en_seguimiento(int isol, int idusur, String fchr, String descr, String clsr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_prmd_r_solicitud_seguimiento`('" + isol + "','" + idusur + "','" + fchr + "','" + descr + "','" + clsr + "')");
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

    public List traer_solicitud_para_seguimiento(int isol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_t_traer solicitudes_en_seguimiento`('" + isol + "')");
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

    public boolean Solicitud_terminada(int isol, int iusu) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update programacion_detalle pd set pd.clasificacion_recibe = 'TERMINADO',pd.id_usuario_recibe = " + iusu + ",pd.fecha_recibe = now()  where pd.id_solicitud = " + isol + "");
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

    public List traer_correos_de_solicitudes() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_t_correos_de_solicitudes`()");
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

    public List traer_solicitudes_con_seguimiento(int isol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_t_solicitudes_con_seguimiento`('" + isol + "')");
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

    public List Consultar_solicitud_rango_fecha(String fini, String ffin) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_f_por_fecha`('" + fini + "','" + ffin + "')");
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

    public boolean Registrar_solicitudes_agrupadas(int isol, String agru) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_m_solicitudes_agrupadas`('" + isol + "','" + agru + "')");
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

    public boolean Registrar_solicitud_principal(int isol, int isolp, String sag) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update solicitud s set s.solicitud_agrupada = '" + sag + "',s.solicitud_principal =" + isolp + " where s.id_solicitudes =" + isol + "");
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

    public List Contador_act(int Id_Solicitud) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_sol_cont_actividades`('" + Id_Solicitud + "')");
            List Consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (Consulta == null) {
                return null;
            }
            return Consulta;
        } catch (Exception ex) {
            return null;
        }
    }

    public String Encontrar_Div(int Id_Solicitud) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String encont_div = "";
            Query q = etm.createNativeQuery("select u.tipo,u.tipo from solicitud s inner join ubicacion u on u.nombre = s.ubicacion_solicitante where s.id_solicitudes = " + Id_Solicitud + " limit 1");
            List Consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!Consulta.isEmpty()) {
                Object[] obj_encont_div = (Object[]) Consulta.get(0);
                encont_div = obj_encont_div[1].toString();
                if (encont_div.contains("Farmac")) {
                    encont_div = "F";
                } else {
                    encont_div = "I";
                }
                return encont_div;
            } else {
                return "I";
            }
        } catch (Exception ex) {
            return "I";
        }
    }

    public boolean Modificar_Ubi(int Id_Solicitud, String Ubicacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update solicitud s set s.ubicacion_solicitante = '" + Ubicacion + "' where s.id_solicitudes = " + Id_Solicitud + "");
            int Consulta = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (Consulta == 1) {
                return true;
            }
            return false;
        } catch (Exception ex) {
        }
        return false;
    }
}
