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
import Entidades.CabeceraEtdHasVerificarEtd;
import Entidades.CalificarEtd;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Programador.TI1
 */
public class CalificarEtdJpaController implements Serializable {

    public CalificarEtdJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CalificarEtd calificarEtd) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CabeceraEtdHasVerificarEtd idcabeceraetdhasVerificaretd = calificarEtd.getIdcabeceraetdhasVerificaretd();
            if (idcabeceraetdhasVerificaretd != null) {
                idcabeceraetdhasVerificaretd = em.getReference(idcabeceraetdhasVerificaretd.getClass(), idcabeceraetdhasVerificaretd.getIdcabeceraetdhasVerificaretd());
                calificarEtd.setIdcabeceraetdhasVerificaretd(idcabeceraetdhasVerificaretd);
            }
            em.persist(calificarEtd);
            if (idcabeceraetdhasVerificaretd != null) {
                idcabeceraetdhasVerificaretd.getCalificarEtdCollection().add(calificarEtd);
                idcabeceraetdhasVerificaretd = em.merge(idcabeceraetdhasVerificaretd);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CalificarEtd calificarEtd) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CalificarEtd persistentCalificarEtd = em.find(CalificarEtd.class, calificarEtd.getIdcalificarEtd());
            CabeceraEtdHasVerificarEtd idcabeceraetdhasVerificaretdOld = persistentCalificarEtd.getIdcabeceraetdhasVerificaretd();
            CabeceraEtdHasVerificarEtd idcabeceraetdhasVerificaretdNew = calificarEtd.getIdcabeceraetdhasVerificaretd();
            if (idcabeceraetdhasVerificaretdNew != null) {
                idcabeceraetdhasVerificaretdNew = em.getReference(idcabeceraetdhasVerificaretdNew.getClass(), idcabeceraetdhasVerificaretdNew.getIdcabeceraetdhasVerificaretd());
                calificarEtd.setIdcabeceraetdhasVerificaretd(idcabeceraetdhasVerificaretdNew);
            }
            calificarEtd = em.merge(calificarEtd);
            if (idcabeceraetdhasVerificaretdOld != null && !idcabeceraetdhasVerificaretdOld.equals(idcabeceraetdhasVerificaretdNew)) {
                idcabeceraetdhasVerificaretdOld.getCalificarEtdCollection().remove(calificarEtd);
                idcabeceraetdhasVerificaretdOld = em.merge(idcabeceraetdhasVerificaretdOld);
            }
            if (idcabeceraetdhasVerificaretdNew != null && !idcabeceraetdhasVerificaretdNew.equals(idcabeceraetdhasVerificaretdOld)) {
                idcabeceraetdhasVerificaretdNew.getCalificarEtdCollection().add(calificarEtd);
                idcabeceraetdhasVerificaretdNew = em.merge(idcabeceraetdhasVerificaretdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = calificarEtd.getIdcalificarEtd();
                if (findCalificarEtd(id) == null) {
                    throw new NonexistentEntityException("The calificarEtd with id " + id + " no longer exists.");
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
            CalificarEtd calificarEtd;
            try {
                calificarEtd = em.getReference(CalificarEtd.class, id);
                calificarEtd.getIdcalificarEtd();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calificarEtd with id " + id + " no longer exists.", enfe);
            }
            CabeceraEtdHasVerificarEtd idcabeceraetdhasVerificaretd = calificarEtd.getIdcabeceraetdhasVerificaretd();
            if (idcabeceraetdhasVerificaretd != null) {
                idcabeceraetdhasVerificaretd.getCalificarEtdCollection().remove(calificarEtd);
                idcabeceraetdhasVerificaretd = em.merge(idcabeceraetdhasVerificaretd);
            }
            em.remove(calificarEtd);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CalificarEtd> findCalificarEtdEntities() {
        return findCalificarEtdEntities(true, -1, -1);
    }

    public List<CalificarEtd> findCalificarEtdEntities(int maxResults, int firstResult) {
        return findCalificarEtdEntities(false, maxResults, firstResult);
    }

    private List<CalificarEtd> findCalificarEtdEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CalificarEtd.class));
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

    public CalificarEtd findCalificarEtd(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CalificarEtd.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalificarEtdCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CalificarEtd> rt = cq.from(CalificarEtd.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
