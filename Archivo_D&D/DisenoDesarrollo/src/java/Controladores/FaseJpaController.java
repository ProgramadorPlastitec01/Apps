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
import Entidades.Etapa;
import Entidades.Fase;
import Entidades.MemoriaC;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Prog.Aprendiz1
 */
public class FaseJpaController implements Serializable {

    public FaseJpaController() {
        emf = Persistence.createEntityManagerFactory("DisenoDesarrolloPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Consultar_fase_etapa(int id_etapa) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fse_c_fase`('" + id_etapa + "')");
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
    public List Consultar_fases() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fse_c_fases`()");
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

    public List Consultar_fase_a(int id_etapa) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fse_c_a_fase`('" + id_etapa + "')");
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

    public List Traer_fase(int id_fase) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fse_t_id_fase`('" + id_fase + "')");
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

    public List Consultar_etapas() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fse_c_etapa`()");
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

    public boolean Registrar_fase(String responsable, String letra, String fase, int id_etapa, String norma) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fse_r_fase`('"+responsable+"', '"+letra+"', '"+fase+"', '"+id_etapa+"', '"+norma+"')");
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

    public boolean Modificar_fase(String responsable, String letra, String fase, int id_fase) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fse_m_fase`('" + responsable + "','" + letra + "','" + fase + "','" + id_fase + "')");
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

    public boolean Estado_fase(int id_fase, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_fse_m_estado`('" + id_fase + "','" + estado + "')");
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
}
