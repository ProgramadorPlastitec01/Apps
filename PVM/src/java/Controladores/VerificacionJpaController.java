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
import Entidades.PlantillaInstrumento;
import Entidades.TipoVerificacion;
import Entidades.Verificacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Programador.TI2
 */
public class VerificacionJpaController implements Serializable {

    public VerificacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Verificacion verificacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InstrumentoMedicion instrumentoMedicion = verificacion.getInstrumentoMedicion();
            if (instrumentoMedicion != null) {
                instrumentoMedicion = em.getReference(instrumentoMedicion.getClass(), instrumentoMedicion.getIdInstrumentoMedicion());
                verificacion.setInstrumentoMedicion(instrumentoMedicion);
            }
            PlantillaInstrumento plantillaInstrumento = verificacion.getPlantillaInstrumento();
            if (plantillaInstrumento != null) {
                plantillaInstrumento = em.getReference(plantillaInstrumento.getClass(), plantillaInstrumento.getIdPlantillaInstrumento());
                verificacion.setPlantillaInstrumento(plantillaInstrumento);
            }
            TipoVerificacion tipoVerificacion = verificacion.getTipoVerificacion();
            if (tipoVerificacion != null) {
                tipoVerificacion = em.getReference(tipoVerificacion.getClass(), tipoVerificacion.getIdTipoVerificacion());
                verificacion.setTipoVerificacion(tipoVerificacion);
            }
            em.persist(verificacion);
            if (instrumentoMedicion != null) {
                instrumentoMedicion.getVerificacionCollection().add(verificacion);
                instrumentoMedicion = em.merge(instrumentoMedicion);
            }
            if (plantillaInstrumento != null) {
                plantillaInstrumento.getVerificacionCollection().add(verificacion);
                plantillaInstrumento = em.merge(plantillaInstrumento);
            }
            if (tipoVerificacion != null) {
                tipoVerificacion.getVerificacionCollection().add(verificacion);
                tipoVerificacion = em.merge(tipoVerificacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Verificacion verificacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Verificacion persistentVerificacion = em.find(Verificacion.class, verificacion.getIdVerificacion());
            InstrumentoMedicion instrumentoMedicionOld = persistentVerificacion.getInstrumentoMedicion();
            InstrumentoMedicion instrumentoMedicionNew = verificacion.getInstrumentoMedicion();
            PlantillaInstrumento plantillaInstrumentoOld = persistentVerificacion.getPlantillaInstrumento();
            PlantillaInstrumento plantillaInstrumentoNew = verificacion.getPlantillaInstrumento();
            TipoVerificacion tipoVerificacionOld = persistentVerificacion.getTipoVerificacion();
            TipoVerificacion tipoVerificacionNew = verificacion.getTipoVerificacion();
            if (instrumentoMedicionNew != null) {
                instrumentoMedicionNew = em.getReference(instrumentoMedicionNew.getClass(), instrumentoMedicionNew.getIdInstrumentoMedicion());
                verificacion.setInstrumentoMedicion(instrumentoMedicionNew);
            }
            if (plantillaInstrumentoNew != null) {
                plantillaInstrumentoNew = em.getReference(plantillaInstrumentoNew.getClass(), plantillaInstrumentoNew.getIdPlantillaInstrumento());
                verificacion.setPlantillaInstrumento(plantillaInstrumentoNew);
            }
            if (tipoVerificacionNew != null) {
                tipoVerificacionNew = em.getReference(tipoVerificacionNew.getClass(), tipoVerificacionNew.getIdTipoVerificacion());
                verificacion.setTipoVerificacion(tipoVerificacionNew);
            }
            verificacion = em.merge(verificacion);
            if (instrumentoMedicionOld != null && !instrumentoMedicionOld.equals(instrumentoMedicionNew)) {
                instrumentoMedicionOld.getVerificacionCollection().remove(verificacion);
                instrumentoMedicionOld = em.merge(instrumentoMedicionOld);
            }
            if (instrumentoMedicionNew != null && !instrumentoMedicionNew.equals(instrumentoMedicionOld)) {
                instrumentoMedicionNew.getVerificacionCollection().add(verificacion);
                instrumentoMedicionNew = em.merge(instrumentoMedicionNew);
            }
            if (plantillaInstrumentoOld != null && !plantillaInstrumentoOld.equals(plantillaInstrumentoNew)) {
                plantillaInstrumentoOld.getVerificacionCollection().remove(verificacion);
                plantillaInstrumentoOld = em.merge(plantillaInstrumentoOld);
            }
            if (plantillaInstrumentoNew != null && !plantillaInstrumentoNew.equals(plantillaInstrumentoOld)) {
                plantillaInstrumentoNew.getVerificacionCollection().add(verificacion);
                plantillaInstrumentoNew = em.merge(plantillaInstrumentoNew);
            }
            if (tipoVerificacionOld != null && !tipoVerificacionOld.equals(tipoVerificacionNew)) {
                tipoVerificacionOld.getVerificacionCollection().remove(verificacion);
                tipoVerificacionOld = em.merge(tipoVerificacionOld);
            }
            if (tipoVerificacionNew != null && !tipoVerificacionNew.equals(tipoVerificacionOld)) {
                tipoVerificacionNew.getVerificacionCollection().add(verificacion);
                tipoVerificacionNew = em.merge(tipoVerificacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = verificacion.getIdVerificacion();
                if (findVerificacion(id) == null) {
                    throw new NonexistentEntityException("The verificacion with id " + id + " no longer exists.");
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
            Verificacion verificacion;
            try {
                verificacion = em.getReference(Verificacion.class, id);
                verificacion.getIdVerificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The verificacion with id " + id + " no longer exists.", enfe);
            }
            InstrumentoMedicion instrumentoMedicion = verificacion.getInstrumentoMedicion();
            if (instrumentoMedicion != null) {
                instrumentoMedicion.getVerificacionCollection().remove(verificacion);
                instrumentoMedicion = em.merge(instrumentoMedicion);
            }
            PlantillaInstrumento plantillaInstrumento = verificacion.getPlantillaInstrumento();
            if (plantillaInstrumento != null) {
                plantillaInstrumento.getVerificacionCollection().remove(verificacion);
                plantillaInstrumento = em.merge(plantillaInstrumento);
            }
            TipoVerificacion tipoVerificacion = verificacion.getTipoVerificacion();
            if (tipoVerificacion != null) {
                tipoVerificacion.getVerificacionCollection().remove(verificacion);
                tipoVerificacion = em.merge(tipoVerificacion);
            }
            em.remove(verificacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Verificacion> findVerificacionEntities() {
        return findVerificacionEntities(true, -1, -1);
    }

    public List<Verificacion> findVerificacionEntities(int maxResults, int firstResult) {
        return findVerificacionEntities(false, maxResults, firstResult);
    }

    private List<Verificacion> findVerificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Verificacion.class));
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

    public Verificacion findVerificacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Verificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getVerificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Verificacion> rt = cq.from(Verificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
