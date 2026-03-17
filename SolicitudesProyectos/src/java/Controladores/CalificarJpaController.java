/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.Calificar;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Plano;
import Entidades.VerificarEtd;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Programador.TI1
 */
public class CalificarJpaController implements Serializable {

    public CalificarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Calificar calificar) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plano idPlano = calificar.getIdPlano();
            if (idPlano != null) {
                idPlano = em.getReference(idPlano.getClass(), idPlano.getIdPlano());
                calificar.setIdPlano(idPlano);
            }
            VerificarEtd idVerificar = calificar.getIdVerificar();
            if (idVerificar != null) {
                idVerificar = em.getReference(idVerificar.getClass(), idVerificar.getIdVerificaretd());
                calificar.setIdVerificar(idVerificar);
            }
            em.persist(calificar);
            if (idPlano != null) {
                idPlano.getCalificarCollection().add(calificar);
                idPlano = em.merge(idPlano);
            }
            if (idVerificar != null) {
                idVerificar.getCalificarCollection().add(calificar);
                idVerificar = em.merge(idVerificar);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Calificar calificar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calificar persistentCalificar = em.find(Calificar.class, calificar.getIdCalificado());
            Plano idPlanoOld = persistentCalificar.getIdPlano();
            Plano idPlanoNew = calificar.getIdPlano();
            VerificarEtd idVerificarOld = persistentCalificar.getIdVerificar();
            VerificarEtd idVerificarNew = calificar.getIdVerificar();
            if (idPlanoNew != null) {
                idPlanoNew = em.getReference(idPlanoNew.getClass(), idPlanoNew.getIdPlano());
                calificar.setIdPlano(idPlanoNew);
            }
            if (idVerificarNew != null) {
                idVerificarNew = em.getReference(idVerificarNew.getClass(), idVerificarNew.getIdVerificaretd());
                calificar.setIdVerificar(idVerificarNew);
            }
            calificar = em.merge(calificar);
            if (idPlanoOld != null && !idPlanoOld.equals(idPlanoNew)) {
                idPlanoOld.getCalificarCollection().remove(calificar);
                idPlanoOld = em.merge(idPlanoOld);
            }
            if (idPlanoNew != null && !idPlanoNew.equals(idPlanoOld)) {
                idPlanoNew.getCalificarCollection().add(calificar);
                idPlanoNew = em.merge(idPlanoNew);
            }
            if (idVerificarOld != null && !idVerificarOld.equals(idVerificarNew)) {
                idVerificarOld.getCalificarCollection().remove(calificar);
                idVerificarOld = em.merge(idVerificarOld);
            }
            if (idVerificarNew != null && !idVerificarNew.equals(idVerificarOld)) {
                idVerificarNew.getCalificarCollection().add(calificar);
                idVerificarNew = em.merge(idVerificarNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = calificar.getIdCalificado();
                if (findCalificar(id) == null) {
                    throw new NonexistentEntityException("The calificar with id " + id + " no longer exists.");
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
            Calificar calificar;
            try {
                calificar = em.getReference(Calificar.class, id);
                calificar.getIdCalificado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calificar with id " + id + " no longer exists.", enfe);
            }
            Plano idPlano = calificar.getIdPlano();
            if (idPlano != null) {
                idPlano.getCalificarCollection().remove(calificar);
                idPlano = em.merge(idPlano);
            }
            VerificarEtd idVerificar = calificar.getIdVerificar();
            if (idVerificar != null) {
                idVerificar.getCalificarCollection().remove(calificar);
                idVerificar = em.merge(idVerificar);
            }
            em.remove(calificar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Calificar> findCalificarEntities() {
        return findCalificarEntities(true, -1, -1);
    }

    public List<Calificar> findCalificarEntities(int maxResults, int firstResult) {
        return findCalificarEntities(false, maxResults, firstResult);
    }

    private List<Calificar> findCalificarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Calificar.class));
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

    public Calificar findCalificar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Calificar.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalificarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Calificar> rt = cq.from(Calificar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
