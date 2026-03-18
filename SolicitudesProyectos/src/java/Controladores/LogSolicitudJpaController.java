/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.LogSolicitud;
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
 * @author Programador.TI1
 */
public class LogSolicitudJpaController implements Serializable {

    public LogSolicitudJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LogSolicitud logSolicitud) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(logSolicitud);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LogSolicitud logSolicitud) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            logSolicitud = em.merge(logSolicitud);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = logSolicitud.getIdLog();
                if (findLogSolicitud(id) == null) {
                    throw new NonexistentEntityException("The logSolicitud with id " + id + " no longer exists.");
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
            LogSolicitud logSolicitud;
            try {
                logSolicitud = em.getReference(LogSolicitud.class, id);
                logSolicitud.getIdLog();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logSolicitud with id " + id + " no longer exists.", enfe);
            }
            em.remove(logSolicitud);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LogSolicitud> findLogSolicitudEntities() {
        return findLogSolicitudEntities(true, -1, -1);
    }

    public List<LogSolicitud> findLogSolicitudEntities(int maxResults, int firstResult) {
        return findLogSolicitudEntities(false, maxResults, firstResult);
    }

    private List<LogSolicitud> findLogSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LogSolicitud.class));
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

    public LogSolicitud findLogSolicitud(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LogSolicitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LogSolicitud> rt = cq.from(LogSolicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
