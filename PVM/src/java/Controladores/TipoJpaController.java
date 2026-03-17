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
import Entidades.InstrumentoMedicion;
import Entidades.Tipo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Programador.TI2
 */
public class TipoJpaController implements Serializable {

    public TipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipo tipo) {
        if (tipo.getInstrumentoMedicionCollection() == null) {
            tipo.setInstrumentoMedicionCollection(new ArrayList<InstrumentoMedicion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<InstrumentoMedicion> attachedInstrumentoMedicionCollection = new ArrayList<InstrumentoMedicion>();
            for (InstrumentoMedicion instrumentoMedicionCollectionInstrumentoMedicionToAttach : tipo.getInstrumentoMedicionCollection()) {
                instrumentoMedicionCollectionInstrumentoMedicionToAttach = em.getReference(instrumentoMedicionCollectionInstrumentoMedicionToAttach.getClass(), instrumentoMedicionCollectionInstrumentoMedicionToAttach.getIdInstrumentoMedicion());
                attachedInstrumentoMedicionCollection.add(instrumentoMedicionCollectionInstrumentoMedicionToAttach);
            }
            tipo.setInstrumentoMedicionCollection(attachedInstrumentoMedicionCollection);
            em.persist(tipo);
            for (InstrumentoMedicion instrumentoMedicionCollectionInstrumentoMedicion : tipo.getInstrumentoMedicionCollection()) {
                Tipo oldTipoOfInstrumentoMedicionCollectionInstrumentoMedicion = instrumentoMedicionCollectionInstrumentoMedicion.getTipo();
                instrumentoMedicionCollectionInstrumentoMedicion.setTipo(tipo);
                instrumentoMedicionCollectionInstrumentoMedicion = em.merge(instrumentoMedicionCollectionInstrumentoMedicion);
                if (oldTipoOfInstrumentoMedicionCollectionInstrumentoMedicion != null) {
                    oldTipoOfInstrumentoMedicionCollectionInstrumentoMedicion.getInstrumentoMedicionCollection().remove(instrumentoMedicionCollectionInstrumentoMedicion);
                    oldTipoOfInstrumentoMedicionCollectionInstrumentoMedicion = em.merge(oldTipoOfInstrumentoMedicionCollectionInstrumentoMedicion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipo tipo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo persistentTipo = em.find(Tipo.class, tipo.getIdTipo());
            Collection<InstrumentoMedicion> instrumentoMedicionCollectionOld = persistentTipo.getInstrumentoMedicionCollection();
            Collection<InstrumentoMedicion> instrumentoMedicionCollectionNew = tipo.getInstrumentoMedicionCollection();
            Collection<InstrumentoMedicion> attachedInstrumentoMedicionCollectionNew = new ArrayList<InstrumentoMedicion>();
            for (InstrumentoMedicion instrumentoMedicionCollectionNewInstrumentoMedicionToAttach : instrumentoMedicionCollectionNew) {
                instrumentoMedicionCollectionNewInstrumentoMedicionToAttach = em.getReference(instrumentoMedicionCollectionNewInstrumentoMedicionToAttach.getClass(), instrumentoMedicionCollectionNewInstrumentoMedicionToAttach.getIdInstrumentoMedicion());
                attachedInstrumentoMedicionCollectionNew.add(instrumentoMedicionCollectionNewInstrumentoMedicionToAttach);
            }
            instrumentoMedicionCollectionNew = attachedInstrumentoMedicionCollectionNew;
            tipo.setInstrumentoMedicionCollection(instrumentoMedicionCollectionNew);
            tipo = em.merge(tipo);
            for (InstrumentoMedicion instrumentoMedicionCollectionOldInstrumentoMedicion : instrumentoMedicionCollectionOld) {
                if (!instrumentoMedicionCollectionNew.contains(instrumentoMedicionCollectionOldInstrumentoMedicion)) {
                    instrumentoMedicionCollectionOldInstrumentoMedicion.setTipo(null);
                    instrumentoMedicionCollectionOldInstrumentoMedicion = em.merge(instrumentoMedicionCollectionOldInstrumentoMedicion);
                }
            }
            for (InstrumentoMedicion instrumentoMedicionCollectionNewInstrumentoMedicion : instrumentoMedicionCollectionNew) {
                if (!instrumentoMedicionCollectionOld.contains(instrumentoMedicionCollectionNewInstrumentoMedicion)) {
                    Tipo oldTipoOfInstrumentoMedicionCollectionNewInstrumentoMedicion = instrumentoMedicionCollectionNewInstrumentoMedicion.getTipo();
                    instrumentoMedicionCollectionNewInstrumentoMedicion.setTipo(tipo);
                    instrumentoMedicionCollectionNewInstrumentoMedicion = em.merge(instrumentoMedicionCollectionNewInstrumentoMedicion);
                    if (oldTipoOfInstrumentoMedicionCollectionNewInstrumentoMedicion != null && !oldTipoOfInstrumentoMedicionCollectionNewInstrumentoMedicion.equals(tipo)) {
                        oldTipoOfInstrumentoMedicionCollectionNewInstrumentoMedicion.getInstrumentoMedicionCollection().remove(instrumentoMedicionCollectionNewInstrumentoMedicion);
                        oldTipoOfInstrumentoMedicionCollectionNewInstrumentoMedicion = em.merge(oldTipoOfInstrumentoMedicionCollectionNewInstrumentoMedicion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipo.getIdTipo();
                if (findTipo(id) == null) {
                    throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.");
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
            Tipo tipo;
            try {
                tipo = em.getReference(Tipo.class, id);
                tipo.getIdTipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.", enfe);
            }
            Collection<InstrumentoMedicion> instrumentoMedicionCollection = tipo.getInstrumentoMedicionCollection();
            for (InstrumentoMedicion instrumentoMedicionCollectionInstrumentoMedicion : instrumentoMedicionCollection) {
                instrumentoMedicionCollectionInstrumentoMedicion.setTipo(null);
                instrumentoMedicionCollectionInstrumentoMedicion = em.merge(instrumentoMedicionCollectionInstrumentoMedicion);
            }
            em.remove(tipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipo> findTipoEntities() {
        return findTipoEntities(true, -1, -1);
    }

    public List<Tipo> findTipoEntities(int maxResults, int firstResult) {
        return findTipoEntities(false, maxResults, firstResult);
    }

    private List<Tipo> findTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipo.class));
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

    public Tipo findTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipo> rt = cq.from(Tipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
