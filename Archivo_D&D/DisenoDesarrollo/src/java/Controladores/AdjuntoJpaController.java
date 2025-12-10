/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.Adjunto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Proyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Prog.Aprendiz1
 */
public class AdjuntoJpaController implements Serializable {

    public AdjuntoJpaController() {
        emf = Persistence.createEntityManagerFactory("DisenoDesarrolloPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TABAL ADJUNTOS
    public boolean Adjuntos_proyecto(int ipy, String taj, String etp, String fse, String ajt, String urg, String osb) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("INSERT INTO adjunto(id_proyecto,tipo,etapa,fase,adjunto,usuario_registro,observacion) VALUES ('" + ipy + "','" + taj + "', '" + etp + "', '" + fse + "','" + ajt + "','" + urg + "','" + osb + "');");
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

    public boolean Verificar_prueba(int iaj, String vrf, String uvr) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("UPDATE adjunto SET verificacion = '" + vrf + "',usuario_verificacion='" + uvr + "',fecha_verificacion = now() WHERE id_adjunto = " + iaj + "");
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

    public List Adjuntos_memoria(int ipy, String etp, String fse) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ajt_t_adjuntos_memoria_proyecto`('" + ipy + "','" + etp + "','" + fse + "')");
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

    public List Adjuntos_pruebas(int ipy, String fse) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ajt_t_adjuntos_pruebas_proyecto`('" + ipy + "','" + fse + "')");
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

    public List Adjuntos_entradas_pr(int ipy, String fse) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ajt_t_adjuntos_entradas_pr_proyecto`('" + ipy + "','" + fse + "')");
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

    public List Adjuntos_entradas_otro(int ipy, String fse) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ajt_t_adjuntos_entradas_otro_proyecto`('" + ipy + "','" + fse + "')");
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
