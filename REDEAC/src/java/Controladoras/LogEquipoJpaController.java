/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import Controladoras.exceptions.NonexistentEntityException;
import Entidades.LogEquipo;
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
 * @author Prog.sistemas2
 */
public class LogEquipoJpaController implements Serializable {

    public LogEquipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LogEquipo logEquipo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(logEquipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LogEquipo logEquipo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            logEquipo = em.merge(logEquipo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = logEquipo.getIdLogEquipo();
                if (findLogEquipo(id) == null) {
                    throw new NonexistentEntityException("The logEquipo with id " + id + " no longer exists.");
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
            LogEquipo logEquipo;
            try {
                logEquipo = em.getReference(LogEquipo.class, id);
                logEquipo.getIdLogEquipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logEquipo with id " + id + " no longer exists.", enfe);
            }
            em.remove(logEquipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LogEquipo> findLogEquipoEntities() {
        return findLogEquipoEntities(true, -1, -1);
    }

    public List<LogEquipo> findLogEquipoEntities(int maxResults, int firstResult) {
        return findLogEquipoEntities(false, maxResults, firstResult);
    }

    private List<LogEquipo> findLogEquipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LogEquipo.class));
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

    public LogEquipo findLogEquipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LogEquipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogEquipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LogEquipo> rt = cq.from(LogEquipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
