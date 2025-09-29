/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Attach;
import Entity.Certificates;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Programador.TI1
 */
public class CertificatesJpaController implements Serializable {

    public CertificatesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Certificates certificates) throws PreexistingEntityException, Exception {
        if (certificates.getAttachCollection() == null) {
            certificates.setAttachCollection(new ArrayList<Attach>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Attach> attachedAttachCollection = new ArrayList<Attach>();
            for (Attach attachCollectionAttachToAttach : certificates.getAttachCollection()) {
                attachCollectionAttachToAttach = em.getReference(attachCollectionAttachToAttach.getClass(), attachCollectionAttachToAttach.getIdAttached());
                attachedAttachCollection.add(attachCollectionAttachToAttach);
            }
            certificates.setAttachCollection(attachedAttachCollection);
            em.persist(certificates);
            for (Attach attachCollectionAttach : certificates.getAttachCollection()) {
                Certificates oldIdCertificateOfAttachCollectionAttach = attachCollectionAttach.getIdCertificate();
                attachCollectionAttach.setIdCertificate(certificates);
                attachCollectionAttach = em.merge(attachCollectionAttach);
                if (oldIdCertificateOfAttachCollectionAttach != null) {
                    oldIdCertificateOfAttachCollectionAttach.getAttachCollection().remove(attachCollectionAttach);
                    oldIdCertificateOfAttachCollectionAttach = em.merge(oldIdCertificateOfAttachCollectionAttach);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCertificates(certificates.getIdCertificate()) != null) {
                throw new PreexistingEntityException("Certificates " + certificates + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Certificates certificates) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Certificates persistentCertificates = em.find(Certificates.class, certificates.getIdCertificate());
            Collection<Attach> attachCollectionOld = persistentCertificates.getAttachCollection();
            Collection<Attach> attachCollectionNew = certificates.getAttachCollection();
            Collection<Attach> attachedAttachCollectionNew = new ArrayList<Attach>();
            for (Attach attachCollectionNewAttachToAttach : attachCollectionNew) {
                attachCollectionNewAttachToAttach = em.getReference(attachCollectionNewAttachToAttach.getClass(), attachCollectionNewAttachToAttach.getIdAttached());
                attachedAttachCollectionNew.add(attachCollectionNewAttachToAttach);
            }
            attachCollectionNew = attachedAttachCollectionNew;
            certificates.setAttachCollection(attachCollectionNew);
            certificates = em.merge(certificates);
            for (Attach attachCollectionOldAttach : attachCollectionOld) {
                if (!attachCollectionNew.contains(attachCollectionOldAttach)) {
                    attachCollectionOldAttach.setIdCertificate(null);
                    attachCollectionOldAttach = em.merge(attachCollectionOldAttach);
                }
            }
            for (Attach attachCollectionNewAttach : attachCollectionNew) {
                if (!attachCollectionOld.contains(attachCollectionNewAttach)) {
                    Certificates oldIdCertificateOfAttachCollectionNewAttach = attachCollectionNewAttach.getIdCertificate();
                    attachCollectionNewAttach.setIdCertificate(certificates);
                    attachCollectionNewAttach = em.merge(attachCollectionNewAttach);
                    if (oldIdCertificateOfAttachCollectionNewAttach != null && !oldIdCertificateOfAttachCollectionNewAttach.equals(certificates)) {
                        oldIdCertificateOfAttachCollectionNewAttach.getAttachCollection().remove(attachCollectionNewAttach);
                        oldIdCertificateOfAttachCollectionNewAttach = em.merge(oldIdCertificateOfAttachCollectionNewAttach);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = certificates.getIdCertificate();
                if (findCertificates(id) == null) {
                    throw new NonexistentEntityException("The certificates with id " + id + " no longer exists.");
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
            Certificates certificates;
            try {
                certificates = em.getReference(Certificates.class, id);
                certificates.getIdCertificate();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The certificates with id " + id + " no longer exists.", enfe);
            }
            Collection<Attach> attachCollection = certificates.getAttachCollection();
            for (Attach attachCollectionAttach : attachCollection) {
                attachCollectionAttach.setIdCertificate(null);
                attachCollectionAttach = em.merge(attachCollectionAttach);
            }
            em.remove(certificates);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Certificates> findCertificatesEntities() {
        return findCertificatesEntities(true, -1, -1);
    }

    public List<Certificates> findCertificatesEntities(int maxResults, int firstResult) {
        return findCertificatesEntities(false, maxResults, firstResult);
    }

    private List<Certificates> findCertificatesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Certificates.class));
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

    public Certificates findCertificates(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Certificates.class, id);
        } finally {
            em.close();
        }
    }

    public int getCertificatesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Certificates> rt = cq.from(Certificates.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
