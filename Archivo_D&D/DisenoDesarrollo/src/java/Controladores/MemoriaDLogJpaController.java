/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.MemoriaDLog;
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
public class MemoriaDLogJpaController implements Serializable {

    public MemoriaDLogJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MemoriaDLog memoriaDLog) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(memoriaDLog);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MemoriaDLog memoriaDLog) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            memoriaDLog = em.merge(memoriaDLog);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = memoriaDLog.getIdMemoriaDLog();
                if (findMemoriaDLog(id) == null) {
                    throw new NonexistentEntityException("The memoriaDLog with id " + id + " no longer exists.");
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
            MemoriaDLog memoriaDLog;
            try {
                memoriaDLog = em.getReference(MemoriaDLog.class, id);
                memoriaDLog.getIdMemoriaDLog();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The memoriaDLog with id " + id + " no longer exists.", enfe);
            }
            em.remove(memoriaDLog);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MemoriaDLog> findMemoriaDLogEntities() {
        return findMemoriaDLogEntities(true, -1, -1);
    }

    public List<MemoriaDLog> findMemoriaDLogEntities(int maxResults, int firstResult) {
        return findMemoriaDLogEntities(false, maxResults, firstResult);
    }

    private List<MemoriaDLog> findMemoriaDLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MemoriaDLog.class));
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

    public MemoriaDLog findMemoriaDLog(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MemoriaDLog.class, id);
        } finally {
            em.close();
        }
    }

    public int getMemoriaDLogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MemoriaDLog> rt = cq.from(MemoriaDLog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
