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
import Entidades.Opcion;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.Menu;
import Entidades.Modulo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prog.Aprendiz1
 */
public class ModuloJpaController implements Serializable {

    public ModuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modulo modulo) {
        if (modulo.getMenuCollection() == null) {
            modulo.setMenuCollection(new ArrayList<Menu>());
        }
        if (modulo.getOpcionCollection() == null) {
            modulo.setOpcionCollection(new ArrayList<Opcion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Menu> attachedMenuCollection = new ArrayList<Menu>();
            for (Menu menuCollectionMenuToAttach : modulo.getMenuCollection()) {
                menuCollectionMenuToAttach = em.getReference(menuCollectionMenuToAttach.getClass(), menuCollectionMenuToAttach.getIdMenu());
                attachedMenuCollection.add(menuCollectionMenuToAttach);
            }
            modulo.setMenuCollection(attachedMenuCollection);
            Collection<Opcion> attachedOpcionCollection = new ArrayList<Opcion>();
            for (Opcion opcionCollectionOpcionToAttach : modulo.getOpcionCollection()) {
                opcionCollectionOpcionToAttach = em.getReference(opcionCollectionOpcionToAttach.getClass(), opcionCollectionOpcionToAttach.getIdOpcion());
                attachedOpcionCollection.add(opcionCollectionOpcionToAttach);
            }
            modulo.setOpcionCollection(attachedOpcionCollection);
            em.persist(modulo);
            for (Menu menuCollectionMenu : modulo.getMenuCollection()) {
                Modulo oldFkModuloOfMenuCollectionMenu = menuCollectionMenu.getFkModulo();
                menuCollectionMenu.setFkModulo(modulo);
                menuCollectionMenu = em.merge(menuCollectionMenu);
                if (oldFkModuloOfMenuCollectionMenu != null) {
                    oldFkModuloOfMenuCollectionMenu.getMenuCollection().remove(menuCollectionMenu);
                    oldFkModuloOfMenuCollectionMenu = em.merge(oldFkModuloOfMenuCollectionMenu);
                }
            }
            for (Opcion opcionCollectionOpcion : modulo.getOpcionCollection()) {
                Modulo oldFkModuloOfOpcionCollectionOpcion = opcionCollectionOpcion.getFkModulo();
                opcionCollectionOpcion.setFkModulo(modulo);
                opcionCollectionOpcion = em.merge(opcionCollectionOpcion);
                if (oldFkModuloOfOpcionCollectionOpcion != null) {
                    oldFkModuloOfOpcionCollectionOpcion.getOpcionCollection().remove(opcionCollectionOpcion);
                    oldFkModuloOfOpcionCollectionOpcion = em.merge(oldFkModuloOfOpcionCollectionOpcion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modulo modulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modulo persistentModulo = em.find(Modulo.class, modulo.getIdModulo());
            Collection<Menu> menuCollectionOld = persistentModulo.getMenuCollection();
            Collection<Menu> menuCollectionNew = modulo.getMenuCollection();
            Collection<Opcion> opcionCollectionOld = persistentModulo.getOpcionCollection();
            Collection<Opcion> opcionCollectionNew = modulo.getOpcionCollection();
            Collection<Menu> attachedMenuCollectionNew = new ArrayList<Menu>();
            for (Menu menuCollectionNewMenuToAttach : menuCollectionNew) {
                menuCollectionNewMenuToAttach = em.getReference(menuCollectionNewMenuToAttach.getClass(), menuCollectionNewMenuToAttach.getIdMenu());
                attachedMenuCollectionNew.add(menuCollectionNewMenuToAttach);
            }
            menuCollectionNew = attachedMenuCollectionNew;
            modulo.setMenuCollection(menuCollectionNew);
            Collection<Opcion> attachedOpcionCollectionNew = new ArrayList<Opcion>();
            for (Opcion opcionCollectionNewOpcionToAttach : opcionCollectionNew) {
                opcionCollectionNewOpcionToAttach = em.getReference(opcionCollectionNewOpcionToAttach.getClass(), opcionCollectionNewOpcionToAttach.getIdOpcion());
                attachedOpcionCollectionNew.add(opcionCollectionNewOpcionToAttach);
            }
            opcionCollectionNew = attachedOpcionCollectionNew;
            modulo.setOpcionCollection(opcionCollectionNew);
            modulo = em.merge(modulo);
            for (Menu menuCollectionOldMenu : menuCollectionOld) {
                if (!menuCollectionNew.contains(menuCollectionOldMenu)) {
                    menuCollectionOldMenu.setFkModulo(null);
                    menuCollectionOldMenu = em.merge(menuCollectionOldMenu);
                }
            }
            for (Menu menuCollectionNewMenu : menuCollectionNew) {
                if (!menuCollectionOld.contains(menuCollectionNewMenu)) {
                    Modulo oldFkModuloOfMenuCollectionNewMenu = menuCollectionNewMenu.getFkModulo();
                    menuCollectionNewMenu.setFkModulo(modulo);
                    menuCollectionNewMenu = em.merge(menuCollectionNewMenu);
                    if (oldFkModuloOfMenuCollectionNewMenu != null && !oldFkModuloOfMenuCollectionNewMenu.equals(modulo)) {
                        oldFkModuloOfMenuCollectionNewMenu.getMenuCollection().remove(menuCollectionNewMenu);
                        oldFkModuloOfMenuCollectionNewMenu = em.merge(oldFkModuloOfMenuCollectionNewMenu);
                    }
                }
            }
            for (Opcion opcionCollectionOldOpcion : opcionCollectionOld) {
                if (!opcionCollectionNew.contains(opcionCollectionOldOpcion)) {
                    opcionCollectionOldOpcion.setFkModulo(null);
                    opcionCollectionOldOpcion = em.merge(opcionCollectionOldOpcion);
                }
            }
            for (Opcion opcionCollectionNewOpcion : opcionCollectionNew) {
                if (!opcionCollectionOld.contains(opcionCollectionNewOpcion)) {
                    Modulo oldFkModuloOfOpcionCollectionNewOpcion = opcionCollectionNewOpcion.getFkModulo();
                    opcionCollectionNewOpcion.setFkModulo(modulo);
                    opcionCollectionNewOpcion = em.merge(opcionCollectionNewOpcion);
                    if (oldFkModuloOfOpcionCollectionNewOpcion != null && !oldFkModuloOfOpcionCollectionNewOpcion.equals(modulo)) {
                        oldFkModuloOfOpcionCollectionNewOpcion.getOpcionCollection().remove(opcionCollectionNewOpcion);
                        oldFkModuloOfOpcionCollectionNewOpcion = em.merge(oldFkModuloOfOpcionCollectionNewOpcion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = modulo.getIdModulo();
                if (findModulo(id) == null) {
                    throw new NonexistentEntityException("The modulo with id " + id + " no longer exists.");
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
            Modulo modulo;
            try {
                modulo = em.getReference(Modulo.class, id);
                modulo.getIdModulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modulo with id " + id + " no longer exists.", enfe);
            }
            Collection<Menu> menuCollection = modulo.getMenuCollection();
            for (Menu menuCollectionMenu : menuCollection) {
                menuCollectionMenu.setFkModulo(null);
                menuCollectionMenu = em.merge(menuCollectionMenu);
            }
            Collection<Opcion> opcionCollection = modulo.getOpcionCollection();
            for (Opcion opcionCollectionOpcion : opcionCollection) {
                opcionCollectionOpcion.setFkModulo(null);
                opcionCollectionOpcion = em.merge(opcionCollectionOpcion);
            }
            em.remove(modulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modulo> findModuloEntities() {
        return findModuloEntities(true, -1, -1);
    }

    public List<Modulo> findModuloEntities(int maxResults, int firstResult) {
        return findModuloEntities(false, maxResults, firstResult);
    }

    private List<Modulo> findModuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modulo.class));
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

    public Modulo findModulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getModuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modulo> rt = cq.from(Modulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
