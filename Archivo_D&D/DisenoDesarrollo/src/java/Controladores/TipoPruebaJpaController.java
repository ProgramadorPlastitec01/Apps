/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.TipoPrueba;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Prog.Aprendiz1
 */
public class TipoPruebaJpaController implements Serializable {

    public TipoPruebaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPrueba tipoPrueba) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoPrueba);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoPrueba tipoPrueba) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoPrueba = em.merge(tipoPrueba);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoPrueba.getIdTipoPrueba();
                if (findTipoPrueba(id) == null) {
                    throw new NonexistentEntityException("The tipoPrueba with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPrueba tipoPrueba;
            try {
                tipoPrueba = em.getReference(TipoPrueba.class, id);
                tipoPrueba.getIdTipoPrueba();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPrueba with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoPrueba);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoPrueba> findTipoPruebaEntities() {
        return findTipoPruebaEntities(true, -1, -1);
    }

    public List<TipoPrueba> findTipoPruebaEntities(int maxResults, int firstResult) {
        return findTipoPruebaEntities(false, maxResults, firstResult);
    }

    private List<TipoPrueba> findTipoPruebaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPrueba.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TipoPrueba findTipoPrueba(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPrueba.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPruebaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPrueba> rt = cq.from(TipoPrueba.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
