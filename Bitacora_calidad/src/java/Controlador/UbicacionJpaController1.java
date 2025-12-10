/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidad.Actividad;
import java.util.ArrayList;
import java.util.Collection;
import Entidad.Maquinas;
import Entidad.Ubicacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prog.sistemas2
 */
public class UbicacionJpaController1 implements Serializable {

    public UbicacionJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ubicacion ubicacion) {
        if (ubicacion.getActividadCollection() == null) {
            ubicacion.setActividadCollection(new ArrayList<Actividad>());
        }
        if (ubicacion.getMaquinasCollection() == null) {
            ubicacion.setMaquinasCollection(new ArrayList<Maquinas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Actividad> attachedActividadCollection = new ArrayList<Actividad>();
            for (Actividad actividadCollectionActividadToAttach : ubicacion.getActividadCollection()) {
                actividadCollectionActividadToAttach = em.getReference(actividadCollectionActividadToAttach.getClass(), actividadCollectionActividadToAttach.getIdActividad());
                attachedActividadCollection.add(actividadCollectionActividadToAttach);
            }
            ubicacion.setActividadCollection(attachedActividadCollection);
            Collection<Maquinas> attachedMaquinasCollection = new ArrayList<Maquinas>();
            for (Maquinas maquinasCollectionMaquinasToAttach : ubicacion.getMaquinasCollection()) {
                maquinasCollectionMaquinasToAttach = em.getReference(maquinasCollectionMaquinasToAttach.getClass(), maquinasCollectionMaquinasToAttach.getIdMaquina());
                attachedMaquinasCollection.add(maquinasCollectionMaquinasToAttach);
            }
            ubicacion.setMaquinasCollection(attachedMaquinasCollection);
            em.persist(ubicacion);
            for (Actividad actividadCollectionActividad : ubicacion.getActividadCollection()) {
                Ubicacion oldUbicacionOfActividadCollectionActividad = actividadCollectionActividad.getUbicacion();
                actividadCollectionActividad.setUbicacion(ubicacion);
                actividadCollectionActividad = em.merge(actividadCollectionActividad);
                if (oldUbicacionOfActividadCollectionActividad != null) {
                    oldUbicacionOfActividadCollectionActividad.getActividadCollection().remove(actividadCollectionActividad);
                    oldUbicacionOfActividadCollectionActividad = em.merge(oldUbicacionOfActividadCollectionActividad);
                }
            }
            for (Maquinas maquinasCollectionMaquinas : ubicacion.getMaquinasCollection()) {
                Ubicacion oldUbicacionOfMaquinasCollectionMaquinas = maquinasCollectionMaquinas.getUbicacion();
                maquinasCollectionMaquinas.setUbicacion(ubicacion);
                maquinasCollectionMaquinas = em.merge(maquinasCollectionMaquinas);
                if (oldUbicacionOfMaquinasCollectionMaquinas != null) {
                    oldUbicacionOfMaquinasCollectionMaquinas.getMaquinasCollection().remove(maquinasCollectionMaquinas);
                    oldUbicacionOfMaquinasCollectionMaquinas = em.merge(oldUbicacionOfMaquinasCollectionMaquinas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ubicacion ubicacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ubicacion persistentUbicacion = em.find(Ubicacion.class, ubicacion.getIdUbicacion());
            Collection<Actividad> actividadCollectionOld = persistentUbicacion.getActividadCollection();
            Collection<Actividad> actividadCollectionNew = ubicacion.getActividadCollection();
            Collection<Maquinas> maquinasCollectionOld = persistentUbicacion.getMaquinasCollection();
            Collection<Maquinas> maquinasCollectionNew = ubicacion.getMaquinasCollection();
            List<String> illegalOrphanMessages = null;
            for (Maquinas maquinasCollectionOldMaquinas : maquinasCollectionOld) {
                if (!maquinasCollectionNew.contains(maquinasCollectionOldMaquinas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Maquinas " + maquinasCollectionOldMaquinas + " since its ubicacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Actividad> attachedActividadCollectionNew = new ArrayList<Actividad>();
            for (Actividad actividadCollectionNewActividadToAttach : actividadCollectionNew) {
                actividadCollectionNewActividadToAttach = em.getReference(actividadCollectionNewActividadToAttach.getClass(), actividadCollectionNewActividadToAttach.getIdActividad());
                attachedActividadCollectionNew.add(actividadCollectionNewActividadToAttach);
            }
            actividadCollectionNew = attachedActividadCollectionNew;
            ubicacion.setActividadCollection(actividadCollectionNew);
            Collection<Maquinas> attachedMaquinasCollectionNew = new ArrayList<Maquinas>();
            for (Maquinas maquinasCollectionNewMaquinasToAttach : maquinasCollectionNew) {
                maquinasCollectionNewMaquinasToAttach = em.getReference(maquinasCollectionNewMaquinasToAttach.getClass(), maquinasCollectionNewMaquinasToAttach.getIdMaquina());
                attachedMaquinasCollectionNew.add(maquinasCollectionNewMaquinasToAttach);
            }
            maquinasCollectionNew = attachedMaquinasCollectionNew;
            ubicacion.setMaquinasCollection(maquinasCollectionNew);
            ubicacion = em.merge(ubicacion);
            for (Actividad actividadCollectionOldActividad : actividadCollectionOld) {
                if (!actividadCollectionNew.contains(actividadCollectionOldActividad)) {
                    actividadCollectionOldActividad.setUbicacion(null);
                    actividadCollectionOldActividad = em.merge(actividadCollectionOldActividad);
                }
            }
            for (Actividad actividadCollectionNewActividad : actividadCollectionNew) {
                if (!actividadCollectionOld.contains(actividadCollectionNewActividad)) {
                    Ubicacion oldUbicacionOfActividadCollectionNewActividad = actividadCollectionNewActividad.getUbicacion();
                    actividadCollectionNewActividad.setUbicacion(ubicacion);
                    actividadCollectionNewActividad = em.merge(actividadCollectionNewActividad);
                    if (oldUbicacionOfActividadCollectionNewActividad != null && !oldUbicacionOfActividadCollectionNewActividad.equals(ubicacion)) {
                        oldUbicacionOfActividadCollectionNewActividad.getActividadCollection().remove(actividadCollectionNewActividad);
                        oldUbicacionOfActividadCollectionNewActividad = em.merge(oldUbicacionOfActividadCollectionNewActividad);
                    }
                }
            }
            for (Maquinas maquinasCollectionNewMaquinas : maquinasCollectionNew) {
                if (!maquinasCollectionOld.contains(maquinasCollectionNewMaquinas)) {
                    Ubicacion oldUbicacionOfMaquinasCollectionNewMaquinas = maquinasCollectionNewMaquinas.getUbicacion();
                    maquinasCollectionNewMaquinas.setUbicacion(ubicacion);
                    maquinasCollectionNewMaquinas = em.merge(maquinasCollectionNewMaquinas);
                    if (oldUbicacionOfMaquinasCollectionNewMaquinas != null && !oldUbicacionOfMaquinasCollectionNewMaquinas.equals(ubicacion)) {
                        oldUbicacionOfMaquinasCollectionNewMaquinas.getMaquinasCollection().remove(maquinasCollectionNewMaquinas);
                        oldUbicacionOfMaquinasCollectionNewMaquinas = em.merge(oldUbicacionOfMaquinasCollectionNewMaquinas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ubicacion.getIdUbicacion();
                if (findUbicacion(id) == null) {
                    throw new NonexistentEntityException("The ubicacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ubicacion ubicacion;
            try {
                ubicacion = em.getReference(Ubicacion.class, id);
                ubicacion.getIdUbicacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ubicacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Maquinas> maquinasCollectionOrphanCheck = ubicacion.getMaquinasCollection();
            for (Maquinas maquinasCollectionOrphanCheckMaquinas : maquinasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ubicacion (" + ubicacion + ") cannot be destroyed since the Maquinas " + maquinasCollectionOrphanCheckMaquinas + " in its maquinasCollection field has a non-nullable ubicacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Actividad> actividadCollection = ubicacion.getActividadCollection();
            for (Actividad actividadCollectionActividad : actividadCollection) {
                actividadCollectionActividad.setUbicacion(null);
                actividadCollectionActividad = em.merge(actividadCollectionActividad);
            }
            em.remove(ubicacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ubicacion> findUbicacionEntities() {
        return findUbicacionEntities(true, -1, -1);
    }

    public List<Ubicacion> findUbicacionEntities(int maxResults, int firstResult) {
        return findUbicacionEntities(false, maxResults, firstResult);
    }

    private List<Ubicacion> findUbicacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ubicacion.class));
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

    public Ubicacion findUbicacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ubicacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getUbicacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ubicacion> rt = cq.from(Ubicacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
