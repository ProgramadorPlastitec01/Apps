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
import Entidades.Proyecto;
import Entidades.PPrueba;
import Entidades.PruebaC;
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
public class PruebaCJpaController implements Serializable {

    public PruebaCJpaController() {
        emf = Persistence.createEntityManagerFactory("DisenoDesarrolloPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Consultar_prueba_c(int id_proyecto) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pru_c_prueba_c`('" + id_proyecto + "')");
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

    public List Traer_prueba_c(int id_prueba_c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pru_t_id_prueba_c`('" + id_prueba_c + "')");
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

    public List Traer_prueba_c_limit() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pru_c_prueba_c_limit`()");
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

    public boolean Registrar_prueba_c(String usu_registro, String programacion, String fch_inicial, String consecutivo, String observacion, String t_programacion, int id_proyecto) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pru_c_r_prueba_c`('" + usu_registro + "','" + programacion + "','" + fch_inicial + "','" + consecutivo + "','" + observacion + "','" + t_programacion + "','" + id_proyecto + "')");
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

    public boolean Copiar_prueba_c(String usu_registro, String programacion, String fch_inicial, String consecutivo, String observacion, String t_programacion, int id_proyecto) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pru_c_c_prueba_c`('" + usu_registro + "','" + programacion + "','" + fch_inicial + "','" + consecutivo + "','" + observacion + "','" + t_programacion + "','" + id_proyecto + "')");
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

    public boolean Modificar_prueba_c(String usu_registro, String programacion, String fch_inicial, String consecutivo, String observacion, String t_programacion, int id_prueba_c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pru_m_prueba_c`('" + usu_registro + "','" + programacion + "','" + fch_inicial + "','" + consecutivo + "','" + observacion + "','" + t_programacion + "','" + id_prueba_c + "')");
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

    public boolean Estado_prueba_c(int id_prueba_c, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pru_c_m_prueba_c`('" + id_prueba_c + "','" + estado + "')");
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

    public boolean Estado_verifica(int id_prueba_c, int id_proyecto, String usu_verifica) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pru_c_verifica`('" + id_prueba_c + "','" + id_proyecto + "','" + usu_verifica + "')");
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
