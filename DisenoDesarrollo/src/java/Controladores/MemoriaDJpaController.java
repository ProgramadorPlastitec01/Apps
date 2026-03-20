/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.MemoriaC;
import Entidades.MemoriaD;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Prog.Aprendiz1
 */
public class MemoriaDJpaController implements Serializable {

    public MemoriaDJpaController() {
        emf = Persistence.createEntityManagerFactory("DisenoDesarrolloPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Traer_memoria(int id_memoria_c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_d_t_memoria`('" + id_memoria_c + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Traer_memoria_eventos_proyecto(int ipy) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_d_t_memoria_eventos`('" + ipy + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Traer_memoria_pendientes(int id_memoria_c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_d_t_memoria_pendiente`('" + id_memoria_c + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Traer_progreso_proyecto(int ipy) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_d_t_progreso_proyecto`('" + ipy + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Traer_ultima_memoria(int id_memoria_c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_d_t_memoria_ultimo`('" + id_memoria_c + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Traer_memoria_a(int id_memoria) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_d_t_id_memoria`('" + id_memoria + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean Registrar_memoria_d(int id_usuario, int id_norma, String memoria, String fecha, String responsable, int etd) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_d_r_memoria`('" + fecha + "','" + id_usuario + "','" + id_norma + "','" + memoria + "','" + responsable + "','" + etd + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Modificar_autoridad(String autoridad, int id_memoria) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_d_m_autoridad`('" + autoridad + "','" + id_memoria + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Modificar_memoria_d(int id_memoria, String memoria, String fecha, String usuario, String autoridad, int imc) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_d_m_memoria`('" + id_memoria + "','" + memoria + "','" + fecha + "','" + usuario + "','" + autoridad + "','" + imc + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Modificar_numeral(int imc, int id_memoria) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_d_m_memoria_numeral`('" + imc + "', '" + id_memoria + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Responder_actividad(int id_memoria, String observacion, String usuario) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("UPDATE memoria_d SET respuesta = '" + observacion + "' ,usu_respuesta = '" + usuario + "' ,fch_respuesta = now(),correo_respuesta = 0 WHERE id_memoria_d = " + id_memoria);
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Cambiar_actividad_registrada(int id_memoria, String txt_ant, String txt_nuevo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_memod_u_respuesta`('" + txt_nuevo + "', '" + txt_ant + "', '" + id_memoria + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Cambiar_estado_actividad(int id_memoria, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("UPDATE memoria_d SET estado = " + estado + " WHERE id_memoria_d = " + id_memoria);
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Enviar_correo_respuesta(int id_memoria) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("UPDATE memoria_d SET correo_respuesta = 1 WHERE id_memoria_d = " + id_memoria);
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Enviar_correo_actividad(int id_memoria) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("UPDATE memoria_d SET correo_actividad = 1 WHERE id_memoria_d = " + id_memoria);
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    //TABLA MEMORIA_D_LOG

    public boolean Log_memoria_d(int imd, String tlg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mdl_r_memoria_log`('" + imd + "','" + tlg + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List Traer_log_memoria_d(int imd, String tlg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_mdl_t_memoria_log`('" + imd + "','" + tlg + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Tareas_proyectos(int etd, int ius) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            String select = "select p.numero,p.proyecto,e.numero,e.etapa,f.letra,f.fase,d.memoria,d.usu_registro,d.respuesta,d.autoridad,d.usu_respuesta,d.estado,p.id_proyecto from proyecto p inner join memoria_c c on p.id_proyecto = c.fk_proyecto inner join memoria_d d on c.id_memoria_c = d.fk_memoria_c inner join etapa e on c.fk_etapa = e.id_etapa inner join fase f on c.fk_fase = f.id_fase";
            Query q = em.createNativeQuery(select + " where d.estado = " + etd + " and d.usu_registro = " + ius + "");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List faseporidmemoria(int id_memoria) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("SELECT md.id_memoria_d, md.fk_memoria_c, mc.fk_fase, f.letra, f.fase, f.norma, e.id_etapa, e.numero, e.etapa\n"
                    + "FROM memoria_d md\n"
                    + "INNER JOIN memoria_c mc ON md.fk_memoria_c = mc.id_memoria_c\n"
                    + "INNER JOIN fase f ON mc.fk_fase = f.id_fase\n"
                    + "INNER JOIN etapa e ON f.fk_etapa = e.id_etapa\n"
                    + "WHERE md.id_memoria_d = "+id_memoria+";");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
