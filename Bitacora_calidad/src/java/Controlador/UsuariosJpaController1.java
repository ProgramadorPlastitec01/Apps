/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidad.Notas;
import java.util.ArrayList;
import java.util.Collection;
import Entidad.Actividad;
import Entidad.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prog.sistemas2
 */
public class UsuariosJpaController1 implements Serializable {

    public UsuariosJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getNotasCollection() == null) {
            usuarios.setNotasCollection(new ArrayList<Notas>());
        }
        if (usuarios.getActividadCollection() == null) {
            usuarios.setActividadCollection(new ArrayList<Actividad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Notas> attachedNotasCollection = new ArrayList<Notas>();
            for (Notas notasCollectionNotasToAttach : usuarios.getNotasCollection()) {
                notasCollectionNotasToAttach = em.getReference(notasCollectionNotasToAttach.getClass(), notasCollectionNotasToAttach.getIdNota());
                attachedNotasCollection.add(notasCollectionNotasToAttach);
            }
            usuarios.setNotasCollection(attachedNotasCollection);
            Collection<Actividad> attachedActividadCollection = new ArrayList<Actividad>();
            for (Actividad actividadCollectionActividadToAttach : usuarios.getActividadCollection()) {
                actividadCollectionActividadToAttach = em.getReference(actividadCollectionActividadToAttach.getClass(), actividadCollectionActividadToAttach.getIdActividad());
                attachedActividadCollection.add(actividadCollectionActividadToAttach);
            }
            usuarios.setActividadCollection(attachedActividadCollection);
            em.persist(usuarios);
            for (Notas notasCollectionNotas : usuarios.getNotasCollection()) {
                Usuarios oldUsuariosOfNotasCollectionNotas = notasCollectionNotas.getUsuarios();
                notasCollectionNotas.setUsuarios(usuarios);
                notasCollectionNotas = em.merge(notasCollectionNotas);
                if (oldUsuariosOfNotasCollectionNotas != null) {
                    oldUsuariosOfNotasCollectionNotas.getNotasCollection().remove(notasCollectionNotas);
                    oldUsuariosOfNotasCollectionNotas = em.merge(oldUsuariosOfNotasCollectionNotas);
                }
            }
            for (Actividad actividadCollectionActividad : usuarios.getActividadCollection()) {
                Usuarios oldUsuariosOfActividadCollectionActividad = actividadCollectionActividad.getUsuarios();
                actividadCollectionActividad.setUsuarios(usuarios);
                actividadCollectionActividad = em.merge(actividadCollectionActividad);
                if (oldUsuariosOfActividadCollectionActividad != null) {
                    oldUsuariosOfActividadCollectionActividad.getActividadCollection().remove(actividadCollectionActividad);
                    oldUsuariosOfActividadCollectionActividad = em.merge(oldUsuariosOfActividadCollectionActividad);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getIdUsuario());
            Collection<Notas> notasCollectionOld = persistentUsuarios.getNotasCollection();
            Collection<Notas> notasCollectionNew = usuarios.getNotasCollection();
            Collection<Actividad> actividadCollectionOld = persistentUsuarios.getActividadCollection();
            Collection<Actividad> actividadCollectionNew = usuarios.getActividadCollection();
            Collection<Notas> attachedNotasCollectionNew = new ArrayList<Notas>();
            for (Notas notasCollectionNewNotasToAttach : notasCollectionNew) {
                notasCollectionNewNotasToAttach = em.getReference(notasCollectionNewNotasToAttach.getClass(), notasCollectionNewNotasToAttach.getIdNota());
                attachedNotasCollectionNew.add(notasCollectionNewNotasToAttach);
            }
            notasCollectionNew = attachedNotasCollectionNew;
            usuarios.setNotasCollection(notasCollectionNew);
            Collection<Actividad> attachedActividadCollectionNew = new ArrayList<Actividad>();
            for (Actividad actividadCollectionNewActividadToAttach : actividadCollectionNew) {
                actividadCollectionNewActividadToAttach = em.getReference(actividadCollectionNewActividadToAttach.getClass(), actividadCollectionNewActividadToAttach.getIdActividad());
                attachedActividadCollectionNew.add(actividadCollectionNewActividadToAttach);
            }
            actividadCollectionNew = attachedActividadCollectionNew;
            usuarios.setActividadCollection(actividadCollectionNew);
            usuarios = em.merge(usuarios);
            for (Notas notasCollectionOldNotas : notasCollectionOld) {
                if (!notasCollectionNew.contains(notasCollectionOldNotas)) {
                    notasCollectionOldNotas.setUsuarios(null);
                    notasCollectionOldNotas = em.merge(notasCollectionOldNotas);
                }
            }
            for (Notas notasCollectionNewNotas : notasCollectionNew) {
                if (!notasCollectionOld.contains(notasCollectionNewNotas)) {
                    Usuarios oldUsuariosOfNotasCollectionNewNotas = notasCollectionNewNotas.getUsuarios();
                    notasCollectionNewNotas.setUsuarios(usuarios);
                    notasCollectionNewNotas = em.merge(notasCollectionNewNotas);
                    if (oldUsuariosOfNotasCollectionNewNotas != null && !oldUsuariosOfNotasCollectionNewNotas.equals(usuarios)) {
                        oldUsuariosOfNotasCollectionNewNotas.getNotasCollection().remove(notasCollectionNewNotas);
                        oldUsuariosOfNotasCollectionNewNotas = em.merge(oldUsuariosOfNotasCollectionNewNotas);
                    }
                }
            }
            for (Actividad actividadCollectionOldActividad : actividadCollectionOld) {
                if (!actividadCollectionNew.contains(actividadCollectionOldActividad)) {
                    actividadCollectionOldActividad.setUsuarios(null);
                    actividadCollectionOldActividad = em.merge(actividadCollectionOldActividad);
                }
            }
            for (Actividad actividadCollectionNewActividad : actividadCollectionNew) {
                if (!actividadCollectionOld.contains(actividadCollectionNewActividad)) {
                    Usuarios oldUsuariosOfActividadCollectionNewActividad = actividadCollectionNewActividad.getUsuarios();
                    actividadCollectionNewActividad.setUsuarios(usuarios);
                    actividadCollectionNewActividad = em.merge(actividadCollectionNewActividad);
                    if (oldUsuariosOfActividadCollectionNewActividad != null && !oldUsuariosOfActividadCollectionNewActividad.equals(usuarios)) {
                        oldUsuariosOfActividadCollectionNewActividad.getActividadCollection().remove(actividadCollectionNewActividad);
                        oldUsuariosOfActividadCollectionNewActividad = em.merge(oldUsuariosOfActividadCollectionNewActividad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getIdUsuario();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Collection<Notas> notasCollection = usuarios.getNotasCollection();
            for (Notas notasCollectionNotas : notasCollection) {
                notasCollectionNotas.setUsuarios(null);
                notasCollectionNotas = em.merge(notasCollectionNotas);
            }
            Collection<Actividad> actividadCollection = usuarios.getActividadCollection();
            for (Actividad actividadCollectionActividad : actividadCollection) {
                actividadCollectionActividad.setUsuarios(null);
                actividadCollectionActividad = em.merge(actividadCollectionActividad);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
