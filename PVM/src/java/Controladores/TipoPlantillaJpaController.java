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
import Entidades.Plantilla;
import Entidades.TipoPlantilla;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Programador.TI2
 */
public class TipoPlantillaJpaController implements Serializable {

    public TipoPlantillaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPlantilla tipoPlantilla) {
        if (tipoPlantilla.getPlantillaCollection() == null) {
            tipoPlantilla.setPlantillaCollection(new ArrayList<Plantilla>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Plantilla> attachedPlantillaCollection = new ArrayList<Plantilla>();
            for (Plantilla plantillaCollectionPlantillaToAttach : tipoPlantilla.getPlantillaCollection()) {
                plantillaCollectionPlantillaToAttach = em.getReference(plantillaCollectionPlantillaToAttach.getClass(), plantillaCollectionPlantillaToAttach.getIdPlantilla());
                attachedPlantillaCollection.add(plantillaCollectionPlantillaToAttach);
            }
            tipoPlantilla.setPlantillaCollection(attachedPlantillaCollection);
            em.persist(tipoPlantilla);
            for (Plantilla plantillaCollectionPlantilla : tipoPlantilla.getPlantillaCollection()) {
                TipoPlantilla oldTipoPlantillaOfPlantillaCollectionPlantilla = plantillaCollectionPlantilla.getTipoPlantilla();
                plantillaCollectionPlantilla.setTipoPlantilla(tipoPlantilla);
                plantillaCollectionPlantilla = em.merge(plantillaCollectionPlantilla);
                if (oldTipoPlantillaOfPlantillaCollectionPlantilla != null) {
                    oldTipoPlantillaOfPlantillaCollectionPlantilla.getPlantillaCollection().remove(plantillaCollectionPlantilla);
                    oldTipoPlantillaOfPlantillaCollectionPlantilla = em.merge(oldTipoPlantillaOfPlantillaCollectionPlantilla);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoPlantilla tipoPlantilla) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPlantilla persistentTipoPlantilla = em.find(TipoPlantilla.class, tipoPlantilla.getIdTipoPlantilla());
            Collection<Plantilla> plantillaCollectionOld = persistentTipoPlantilla.getPlantillaCollection();
            Collection<Plantilla> plantillaCollectionNew = tipoPlantilla.getPlantillaCollection();
            Collection<Plantilla> attachedPlantillaCollectionNew = new ArrayList<Plantilla>();
            for (Plantilla plantillaCollectionNewPlantillaToAttach : plantillaCollectionNew) {
                plantillaCollectionNewPlantillaToAttach = em.getReference(plantillaCollectionNewPlantillaToAttach.getClass(), plantillaCollectionNewPlantillaToAttach.getIdPlantilla());
                attachedPlantillaCollectionNew.add(plantillaCollectionNewPlantillaToAttach);
            }
            plantillaCollectionNew = attachedPlantillaCollectionNew;
            tipoPlantilla.setPlantillaCollection(plantillaCollectionNew);
            tipoPlantilla = em.merge(tipoPlantilla);
            for (Plantilla plantillaCollectionOldPlantilla : plantillaCollectionOld) {
                if (!plantillaCollectionNew.contains(plantillaCollectionOldPlantilla)) {
                    plantillaCollectionOldPlantilla.setTipoPlantilla(null);
                    plantillaCollectionOldPlantilla = em.merge(plantillaCollectionOldPlantilla);
                }
            }
            for (Plantilla plantillaCollectionNewPlantilla : plantillaCollectionNew) {
                if (!plantillaCollectionOld.contains(plantillaCollectionNewPlantilla)) {
                    TipoPlantilla oldTipoPlantillaOfPlantillaCollectionNewPlantilla = plantillaCollectionNewPlantilla.getTipoPlantilla();
                    plantillaCollectionNewPlantilla.setTipoPlantilla(tipoPlantilla);
                    plantillaCollectionNewPlantilla = em.merge(plantillaCollectionNewPlantilla);
                    if (oldTipoPlantillaOfPlantillaCollectionNewPlantilla != null && !oldTipoPlantillaOfPlantillaCollectionNewPlantilla.equals(tipoPlantilla)) {
                        oldTipoPlantillaOfPlantillaCollectionNewPlantilla.getPlantillaCollection().remove(plantillaCollectionNewPlantilla);
                        oldTipoPlantillaOfPlantillaCollectionNewPlantilla = em.merge(oldTipoPlantillaOfPlantillaCollectionNewPlantilla);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoPlantilla.getIdTipoPlantilla();
                if (findTipoPlantilla(id) == null) {
                    throw new NonexistentEntityException("The tipoPlantilla with id " + id + " no longer exists.");
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
            TipoPlantilla tipoPlantilla;
            try {
                tipoPlantilla = em.getReference(TipoPlantilla.class, id);
                tipoPlantilla.getIdTipoPlantilla();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPlantilla with id " + id + " no longer exists.", enfe);
            }
            Collection<Plantilla> plantillaCollection = tipoPlantilla.getPlantillaCollection();
            for (Plantilla plantillaCollectionPlantilla : plantillaCollection) {
                plantillaCollectionPlantilla.setTipoPlantilla(null);
                plantillaCollectionPlantilla = em.merge(plantillaCollectionPlantilla);
            }
            em.remove(tipoPlantilla);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoPlantilla> findTipoPlantillaEntities() {
        return findTipoPlantillaEntities(true, -1, -1);
    }

    public List<TipoPlantilla> findTipoPlantillaEntities(int maxResults, int firstResult) {
        return findTipoPlantillaEntities(false, maxResults, firstResult);
    }

    private List<TipoPlantilla> findTipoPlantillaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPlantilla.class));
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

    public TipoPlantilla findTipoPlantilla(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPlantilla.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPlantillaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPlantilla> rt = cq.from(TipoPlantilla.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
