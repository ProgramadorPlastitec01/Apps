package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Persistence;

public class ReunionJpaController implements Serializable {

    public ReunionJpaController() {
        emf = Persistence.createEntityManagerFactory("ReunionPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Reuniones_area_id(int iar) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_run_t_reuniones_area`('" + iar + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Reuniones_area_filtro(int iar, String fin, String ffn, String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "select r.id_reunion,r.fecha_reunion,r.hora_inicio,r.hora_final,r.asunto,r.descripcion,r.participes,r.estado,a.area,a.siglatura,concat(u.nombres,' ',u.apellidos),r.areas,\n"
                    + "(select count(p.id_pendiente) from pendiente p inner join reunion ru on p.id_reunion = ru.id_reunion\n"
                    + "where p.correo = 0 and ru.id_reunion = r.id_reunion)\n"
                    + "from reunion r inner join usuario u on r.usuario_registro = u.id_usuario\n"
                    + "	 inner join area a on u.id_area = a.id_area\n"
                    + "  where (r.fecha_reunion between '" + fin + "' and '" + ffn + "')  \n";
            String atributos = "";
            if (fto.length() > 0) {
                if (fto.contains("+")) {
                    String[] arg_filtro = fto.replace("+", "---").split("---");
                    for (int i = 0; i < arg_filtro.length; i++) {
                        if (i == 0) {
                            atributos = " and (r.asunto like concat('%" + arg_filtro[i] + "%') or r.descripcion like concat('%" + arg_filtro[i] + "%')";
                        } else {
                            atributos = atributos + " or r.asunto like concat('%" + arg_filtro[i] + "%') or r.descripcion like concat('%" + arg_filtro[i] + "%')";
                        }
                    }
                } else {
                    atributos = " and (r.asunto like concat('%" + fto + "%') or r.descripcion like concat('%" + fto + "%')";
                }
            }
            query = query + atributos + ") and (u.id_area = " + iar + " or r.participes like concat('%/'," + iar + ",']%')) order by r.fecha_reunion desc,r.id_reunion desc";
            Query q = etm.createNativeQuery(query);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Reuniones_area_filtro_fecha(int iar, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "select r.id_reunion,r.fecha_reunion,r.hora_inicio,r.hora_final,r.asunto,r.descripcion,r.participes,r.estado,a.area,a.siglatura,concat(u.nombres,' ',u.apellidos),r.areas,\n"
                    + "(select count(p.id_pendiente) from pendiente p inner join reunion ru on p.id_reunion = ru.id_reunion\n"
                    + "where p.correo = 0 and ru.id_reunion = r.id_reunion)\n"
                    + "from reunion r inner join usuario u on r.usuario_registro = u.id_usuario\n"
                    + "	 inner join area a on u.id_area = a.id_area\n"
                    + "  where (u.id_area = " + iar + " or r.participes like concat('%/'," + iar + ",']%')) and (r.fecha_reunion between '" + fin + "' and '" + ffn + "') \n"
                    + "order by r.fecha_reunion desc,r.id_reunion desc";
            Query q = etm.createNativeQuery(query);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Reunion_id(int iru) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_run_t_reunion_id`('" + iru + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean Registrar_reunion(String fru, String hin, String hfn, String ast, String dcc, String ptc, String ara, int urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_run_r_reunion`('" + fru + "','" + hin + "','" + hfn + "','" + ast + "','" + dcc + "','" + ptc + "','" + ara + "','" + urg + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Modificar_reunion(int iru, String fru, String hin, String hfn, String ast, String dcc, String ptc, String ara) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_run_m_reunion`('" + iru + "','" + fru + "','" + hin + "','" + hfn + "','" + ast + "','" + dcc + "','" + ptc + "','" + ara + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public List Cronograma(int iar, int anio) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ifm_cronograma_calificaciones`('" + iar + "','" + anio + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Traer_anios_historial() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select YEAR(i.fecha_registro),count(i.id_informe) from informe i group by YEAR(i.fecha_registro) order by YEAR(i.fecha_registro) desc");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }

    }
}
