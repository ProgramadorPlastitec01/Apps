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
import Entidades.Modulo;
import Entidades.Menu;
import Entidades.Opcion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prog.Aprendiz1
 */
public class OpcionJpaController implements Serializable {

    public OpcionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Opcion opcion) {
        if (opcion.getMenuCollection() == null) {
            opcion.setMenuCollection(new ArrayList<Menu>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modulo fkModulo = opcion.getFkModulo();
            if (fkModulo != null) {
                fkModulo = em.getReference(fkModulo.getClass(), fkModulo.getIdModulo());
                opcion.setFkModulo(fkModulo);
            }
            Collection<Menu> attachedMenuCollection = new ArrayList<Menu>();
            for (Menu menuCollectionMenuToAttach : opcion.getMenuCollection()) {
                menuCollectionMenuToAttach = em.getReference(menuCollectionMenuToAttach.getClass(), menuCollectionMenuToAttach.getIdMenu());
                attachedMenuCollection.add(menuCollectionMenuToAttach);
            }
            opcion.setMenuCollection(attachedMenuCollection);
            em.persist(opcion);
            if (fkModulo != null) {
                fkModulo.getOpcionCollection().add(opcion);
                fkModulo = em.merge(fkModulo);
            }
            for (Menu menuCollectionMenu : opcion.getMenuCollection()) {
                Opcion oldFkOpcionOfMenuCollectionMenu = menuCollectionMenu.getFkOpcion();
                menuCollectionMenu.setFkOpcion(opcion);
                menuCollectionMenu = em.merge(menuCollectionMenu);
                if (oldFkOpcionOfMenuCollectionMenu != null) {
                    oldFkOpcionOfMenuCollectionMenu.getMenuCollection().remove(menuCollectionMenu);
                    oldFkOpcionOfMenuCollectionMenu = em.merge(oldFkOpcionOfMenuCollectionMenu);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Opcion opcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Opcion persistentOpcion = em.find(Opcion.class, opcion.getIdOpcion());
            Modulo fkModuloOld = persistentOpcion.getFkModulo();
            Modulo fkModuloNew = opcion.getFkModulo();
            Collection<Menu> menuCollectionOld = persistentOpcion.getMenuCollection();
            Collection<Menu> menuCollectionNew = opcion.getMenuCollection();
            if (fkModuloNew != null) {
                fkModuloNew = em.getReference(fkModuloNew.getClass(), fkModuloNew.getIdModulo());
                opcion.setFkModulo(fkModuloNew);
            }
            Collection<Menu> attachedMenuCollectionNew = new ArrayList<Menu>();
            for (Menu menuCollectionNewMenuToAttach : menuCollectionNew) {
                menuCollectionNewMenuToAttach = em.getReference(menuCollectionNewMenuToAttach.getClass(), menuCollectionNewMenuToAttach.getIdMenu());
                attachedMenuCollectionNew.add(menuCollectionNewMenuToAttach);
            }
            menuCollectionNew = attachedMenuCollectionNew;
            opcion.setMenuCollection(menuCollectionNew);
            opcion = em.merge(opcion);
            if (fkModuloOld != null && !fkModuloOld.equals(fkModuloNew)) {
                fkModuloOld.getOpcionCollection().remove(opcion);
                fkModuloOld = em.merge(fkModuloOld);
            }
            if (fkModuloNew != null && !fkModuloNew.equals(fkModuloOld)) {
                fkModuloNew.getOpcionCollection().add(opcion);
                fkModuloNew = em.merge(fkModuloNew);
            }
            for (Menu menuCollectionOldMenu : menuCollectionOld) {
                if (!menuCollectionNew.contains(menuCollectionOldMenu)) {
                    menuCollectionOldMenu.setFkOpcion(null);
                    menuCollectionOldMenu = em.merge(menuCollectionOldMenu);
                }
            }
            for (Menu menuCollectionNewMenu : menuCollectionNew) {
                if (!menuCollectionOld.contains(menuCollectionNewMenu)) {
                    Opcion oldFkOpcionOfMenuCollectionNewMenu = menuCollectionNewMenu.getFkOpcion();
                    menuCollectionNewMenu.setFkOpcion(opcion);
                    menuCollectionNewMenu = em.merge(menuCollectionNewMenu);
                    if (oldFkOpcionOfMenuCollectionNewMenu != null && !oldFkOpcionOfMenuCollectionNewMenu.equals(opcion)) {
                        oldFkOpcionOfMenuCollectionNewMenu.getMenuCollection().remove(menuCollectionNewMenu);
                        oldFkOpcionOfMenuCollectionNewMenu = em.merge(oldFkOpcionOfMenuCollectionNewMenu);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = opcion.getIdOpcion();
                if (findOpcion(id) == null) {
                    throw new NonexistentEntityException("The opcion with id " + id + " no longer exists.");
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
            Opcion opcion;
            try {
                opcion = em.getReference(Opcion.class, id);
                opcion.getIdOpcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The opcion with id " + id + " no longer exists.", enfe);
            }
            Modulo fkModulo = opcion.getFkModulo();
            if (fkModulo != null) {
                fkModulo.getOpcionCollection().remove(opcion);
                fkModulo = em.merge(fkModulo);
            }
            Collection<Menu> menuCollection = opcion.getMenuCollection();
            for (Menu menuCollectionMenu : menuCollection) {
                menuCollectionMenu.setFkOpcion(null);
                menuCollectionMenu = em.merge(menuCollectionMenu);
            }
            em.remove(opcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Opcion> findOpcionEntities() {
        return findOpcionEntities(true, -1, -1);
    }

    public List<Opcion> findOpcionEntities(int maxResults, int firstResult) {
        return findOpcionEntities(false, maxResults, firstResult);
    }

    private List<Opcion> findOpcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Opcion.class));
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

    public Opcion findOpcion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Opcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getOpcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Opcion> rt = cq.from(Opcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
