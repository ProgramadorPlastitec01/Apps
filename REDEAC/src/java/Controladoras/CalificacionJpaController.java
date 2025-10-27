package Controladoras;

import Controladoras.exceptions.NonexistentEntityException;
import Entidades.Calificacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Programacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CalificacionJpaController implements Serializable {

    public CalificacionJpaController() {
        emf = Persistence.createEntityManagerFactory("REDEACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaCalificacionCaso(int id_programacion, int copias) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_clf_t_calificacion_id_caso`('" + id_programacion + "','" + copias + "')");
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

    public boolean registrarCalificacion(int id_equipo, int id_usuario, int id_programacion, String preguntas, int pregunta1, int pregunta2, int pregunta3, int pregunta4, int pregunta5, String observaciones, String usuario_registro, int copia) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_clf_r_calificacion`('" + id_equipo + "','" + id_usuario + "','" + id_programacion + "','" + preguntas + "','" + pregunta1 + "','" + pregunta2 + "','" + pregunta3 + "','" + pregunta4 + "','" + pregunta5 + "','" + observaciones + "','" + usuario_registro + "','" + copia + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List ConsultaCalificaconEncuesta(int anio, int mes, int id_equipo, int id_usuario) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_clf_c_consulta_calificacion_slider`('" + anio + "','" + mes + "','" + id_equipo + "','" + id_usuario + "')");
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

    public List consultaPromedioEquipos(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_clf_c_reporte_promedio`('" + anio + "','" + mes + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaPromedioEquiposFiltro(int anio, int mes, String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_clf_c_reporte_promedio_filtro`('" + anio + "','" + mes + "','" + filtro + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaPromedioTotal(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_clf_c_reporte_promedio_total`('" + anio + "','" + mes + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaMaximo(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_clf_c_reporte_Maximo`('" + anio + "','" + mes + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaMinimo(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_clf_c_reporte_Minimo`('" + anio + "','" + mes + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

}
