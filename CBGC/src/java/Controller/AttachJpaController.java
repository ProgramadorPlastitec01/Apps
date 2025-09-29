/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Entity.Attach;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Certificates;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Programador.TI1
 */
public class AttachJpaController implements Serializable {

    public AttachJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Attach attach) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Certificates idCertificate = attach.getIdCertificate();
            if (idCertificate != null) {
                idCertificate = em.getReference(idCertificate.getClass(), idCertificate.getIdCertificate());
                attach.setIdCertificate(idCertificate);
            }
            em.persist(attach);
            if (idCertificate != null) {
                idCertificate.getAttachCollection().add(attach);
                idCertificate = em.merge(idCertificate);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Attach attach) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attach persistentAttach = em.find(Attach.class, attach.getIdAttached());
            Certificates idCertificateOld = persistentAttach.getIdCertificate();
            Certificates idCertificateNew = attach.getIdCertificate();
            if (idCertificateNew != null) {
                idCertificateNew = em.getReference(idCertificateNew.getClass(), idCertificateNew.getIdCertificate());
                attach.setIdCertificate(idCertificateNew);
            }
            attach = em.merge(attach);
            if (idCertificateOld != null && !idCertificateOld.equals(idCertificateNew)) {
                idCertificateOld.getAttachCollection().remove(attach);
                idCertificateOld = em.merge(idCertificateOld);
            }
            if (idCertificateNew != null && !idCertificateNew.equals(idCertificateOld)) {
                idCertificateNew.getAttachCollection().add(attach);
                idCertificateNew = em.merge(idCertificateNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = attach.getIdAttached();
                if (findAttach(id) == null) {
                    throw new NonexistentEntityException("The attach with id " + id + " no longer exists.");
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
            Attach attach;
            try {
                attach = em.getReference(Attach.class, id);
                attach.getIdAttached();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attach with id " + id + " no longer exists.", enfe);
            }
            Certificates idCertificate = attach.getIdCertificate();
            if (idCertificate != null) {
                idCertificate.getAttachCollection().remove(attach);
                idCertificate = em.merge(idCertificate);
            }
            em.remove(attach);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Attach> findAttachEntities() {
        return findAttachEntities(true, -1, -1);
    }

    public List<Attach> findAttachEntities(int maxResults, int firstResult) {
        return findAttachEntities(false, maxResults, firstResult);
    }

    private List<Attach> findAttachEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Attach.class));
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

    public Attach findAttach(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attach.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttachCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Attach> rt = cq.from(Attach.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
