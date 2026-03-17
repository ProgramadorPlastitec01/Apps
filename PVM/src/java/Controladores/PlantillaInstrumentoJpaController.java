/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.PlantillaInstrumento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Verificacion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Programador.TI2
 */
public class PlantillaInstrumentoJpaController implements Serializable {

    public PlantillaInstrumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlantillaInstrumento plantillaInstrumento) {
        if (plantillaInstrumento.getVerificacionCollection() == null) {
            plantillaInstrumento.setVerificacionCollection(new ArrayList<Verificacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Verificacion> attachedVerificacionCollection = new ArrayList<Verificacion>();
            for (Verificacion verificacionCollectionVerificacionToAttach : plantillaInstrumento.getVerificacionCollection()) {
                verificacionCollectionVerificacionToAttach = em.getReference(verificacionCollectionVerificacionToAttach.getClass(), verificacionCollectionVerificacionToAttach.getIdVerificacion());
                attachedVerificacionCollection.add(verificacionCollectionVerificacionToAttach);
            }
            plantillaInstrumento.setVerificacionCollection(attachedVerificacionCollection);
            em.persist(plantillaInstrumento);
            for (Verificacion verificacionCollectionVerificacion : plantillaInstrumento.getVerificacionCollection()) {
                PlantillaInstrumento oldPlantillaInstrumentoOfVerificacionCollectionVerificacion = verificacionCollectionVerificacion.getPlantillaInstrumento();
                verificacionCollectionVerificacion.setPlantillaInstrumento(plantillaInstrumento);
                verificacionCollectionVerificacion = em.merge(verificacionCollectionVerificacion);
                if (oldPlantillaInstrumentoOfVerificacionCollectionVerificacion != null) {
                    oldPlantillaInstrumentoOfVerificacionCollectionVerificacion.getVerificacionCollection().remove(verificacionCollectionVerificacion);
                    oldPlantillaInstrumentoOfVerificacionCollectionVerificacion = em.merge(oldPlantillaInstrumentoOfVerificacionCollectionVerificacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlantillaInstrumento plantillaInstrumento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlantillaInstrumento persistentPlantillaInstrumento = em.find(PlantillaInstrumento.class, plantillaInstrumento.getIdPlantillaInstrumento());
            Collection<Verificacion> verificacionCollectionOld = persistentPlantillaInstrumento.getVerificacionCollection();
            Collection<Verificacion> verificacionCollectionNew = plantillaInstrumento.getVerificacionCollection();
            Collection<Verificacion> attachedVerificacionCollectionNew = new ArrayList<Verificacion>();
            for (Verificacion verificacionCollectionNewVerificacionToAttach : verificacionCollectionNew) {
                verificacionCollectionNewVerificacionToAttach = em.getReference(verificacionCollectionNewVerificacionToAttach.getClass(), verificacionCollectionNewVerificacionToAttach.getIdVerificacion());
                attachedVerificacionCollectionNew.add(verificacionCollectionNewVerificacionToAttach);
            }
            verificacionCollectionNew = attachedVerificacionCollectionNew;
            plantillaInstrumento.setVerificacionCollection(verificacionCollectionNew);
            plantillaInstrumento = em.merge(plantillaInstrumento);
            for (Verificacion verificacionCollectionOldVerificacion : verificacionCollectionOld) {
                if (!verificacionCollectionNew.contains(verificacionCollectionOldVerificacion)) {
                    verificacionCollectionOldVerificacion.setPlantillaInstrumento(null);
                    verificacionCollectionOldVerificacion = em.merge(verificacionCollectionOldVerificacion);
                }
            }
            for (Verificacion verificacionCollectionNewVerificacion : verificacionCollectionNew) {
                if (!verificacionCollectionOld.contains(verificacionCollectionNewVerificacion)) {
                    PlantillaInstrumento oldPlantillaInstrumentoOfVerificacionCollectionNewVerificacion = verificacionCollectionNewVerificacion.getPlantillaInstrumento();
                    verificacionCollectionNewVerificacion.setPlantillaInstrumento(plantillaInstrumento);
                    verificacionCollectionNewVerificacion = em.merge(verificacionCollectionNewVerificacion);
                    if (oldPlantillaInstrumentoOfVerificacionCollectionNewVerificacion != null && !oldPlantillaInstrumentoOfVerificacionCollectionNewVerificacion.equals(plantillaInstrumento)) {
                        oldPlantillaInstrumentoOfVerificacionCollectionNewVerificacion.getVerificacionCollection().remove(verificacionCollectionNewVerificacion);
                        oldPlantillaInstrumentoOfVerificacionCollectionNewVerificacion = em.merge(oldPlantillaInstrumentoOfVerificacionCollectionNewVerificacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = plantillaInstrumento.getIdPlantillaInstrumento();
                if (findPlantillaInstrumento(id) == null) {
                    throw new NonexistentEntityException("The plantillaInstrumento with id " + id + " no longer exists.");
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
            PlantillaInstrumento plantillaInstrumento;
            try {
                plantillaInstrumento = em.getReference(PlantillaInstrumento.class, id);
                plantillaInstrumento.getIdPlantillaInstrumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The plantillaInstrumento with id " + id + " no longer exists.", enfe);
            }
            Collection<Verificacion> verificacionCollection = plantillaInstrumento.getVerificacionCollection();
            for (Verificacion verificacionCollectionVerificacion : verificacionCollection) {
                verificacionCollectionVerificacion.setPlantillaInstrumento(null);
                verificacionCollectionVerificacion = em.merge(verificacionCollectionVerificacion);
            }
            em.remove(plantillaInstrumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlantillaInstrumento> findPlantillaInstrumentoEntities() {
        return findPlantillaInstrumentoEntities(true, -1, -1);
    }

    public List<PlantillaInstrumento> findPlantillaInstrumentoEntities(int maxResults, int firstResult) {
        return findPlantillaInstrumentoEntities(false, maxResults, firstResult);
    }

    private List<PlantillaInstrumento> findPlantillaInstrumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlantillaInstrumento.class));
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

    public PlantillaInstrumento findPlantillaInstrumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlantillaInstrumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlantillaInstrumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlantillaInstrumento> rt = cq.from(PlantillaInstrumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
