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
import Entidades.Traslado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Programador.TI2
 */
public class TrasladoJpaController implements Serializable {

    public TrasladoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Traslado traslado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InstrumentoMedicion instrumentoMedicion = traslado.getInstrumentoMedicion();
            if (instrumentoMedicion != null) {
                instrumentoMedicion = em.getReference(instrumentoMedicion.getClass(), instrumentoMedicion.getIdInstrumentoMedicion());
                traslado.setInstrumentoMedicion(instrumentoMedicion);
            }
            em.persist(traslado);
            if (instrumentoMedicion != null) {
                instrumentoMedicion.getTrasladoCollection().add(traslado);
                instrumentoMedicion = em.merge(instrumentoMedicion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Traslado traslado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Traslado persistentTraslado = em.find(Traslado.class, traslado.getIdTraslado());
            InstrumentoMedicion instrumentoMedicionOld = persistentTraslado.getInstrumentoMedicion();
            InstrumentoMedicion instrumentoMedicionNew = traslado.getInstrumentoMedicion();
            if (instrumentoMedicionNew != null) {
                instrumentoMedicionNew = em.getReference(instrumentoMedicionNew.getClass(), instrumentoMedicionNew.getIdInstrumentoMedicion());
                traslado.setInstrumentoMedicion(instrumentoMedicionNew);
            }
            traslado = em.merge(traslado);
            if (instrumentoMedicionOld != null && !instrumentoMedicionOld.equals(instrumentoMedicionNew)) {
                instrumentoMedicionOld.getTrasladoCollection().remove(traslado);
                instrumentoMedicionOld = em.merge(instrumentoMedicionOld);
            }
            if (instrumentoMedicionNew != null && !instrumentoMedicionNew.equals(instrumentoMedicionOld)) {
                instrumentoMedicionNew.getTrasladoCollection().add(traslado);
                instrumentoMedicionNew = em.merge(instrumentoMedicionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = traslado.getIdTraslado();
                if (findTraslado(id) == null) {
                    throw new NonexistentEntityException("The traslado with id " + id + " no longer exists.");
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
            Traslado traslado;
            try {
                traslado = em.getReference(Traslado.class, id);
                traslado.getIdTraslado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The traslado with id " + id + " no longer exists.", enfe);
            }
            InstrumentoMedicion instrumentoMedicion = traslado.getInstrumentoMedicion();
            if (instrumentoMedicion != null) {
                instrumentoMedicion.getTrasladoCollection().remove(traslado);
                instrumentoMedicion = em.merge(instrumentoMedicion);
            }
            em.remove(traslado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Traslado> findTrasladoEntities() {
        return findTrasladoEntities(true, -1, -1);
    }

    public List<Traslado> findTrasladoEntities(int maxResults, int firstResult) {
        return findTrasladoEntities(false, maxResults, firstResult);
    }

    private List<Traslado> findTrasladoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Traslado.class));
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

    public Traslado findTraslado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Traslado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrasladoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Traslado> rt = cq.from(Traslado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
