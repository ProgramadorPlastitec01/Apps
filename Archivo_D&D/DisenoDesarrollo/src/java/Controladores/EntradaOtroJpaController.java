/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.EntradaOtro;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Prog.Aprendiz1
 */
public class EntradaOtroJpaController implements Serializable {

    public EntradaOtroJpaController() {
        emf = Persistence.createEntityManagerFactory("DisenoDesarrolloPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean Registrar_otra_entrada(int ipy, String fet,String ast, String osv, String urg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_eot_r_entrada`('" + ipy + "','" + fet + "','" + ast + "','" + osv + "','" + urg + "')");
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

    public boolean Modificar_otra_entrada(int ipy, String fet,String ast, String osv, String urg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_eot_m_entrada`('" + ipy + "','" + fet + "','" + ast + "','" + osv + "','" + urg + "')");
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

    public List Consultar_entradas_proyecto(int ipy) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_eot_t_entrada`('" + ipy + "')");
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
    public List Consultar_entrada_proyecto(int ieo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_eot_t_entrada_proyecto`('" + ieo + "')");
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
