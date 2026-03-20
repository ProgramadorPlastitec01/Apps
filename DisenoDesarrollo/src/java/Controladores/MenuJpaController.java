/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.Menu;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Opcion;
import Entidades.Usuario;
import Entidades.Modulo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prog.Aprendiz1
 */
public class MenuJpaController implements Serializable {

    public MenuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Menu menu) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modulo fkModulo = menu.getFkModulo();
            if (fkModulo != null) {
                fkModulo = em.getReference(fkModulo.getClass(), fkModulo.getIdModulo());
                menu.setFkModulo(fkModulo);
            }
            Usuario fkUsuario = menu.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario = em.getReference(fkUsuario.getClass(), fkUsuario.getIdUsuario());
                menu.setFkUsuario(fkUsuario);
            }
            Opcion fkOpcion = menu.getFkOpcion();
            if (fkOpcion != null) {
                fkOpcion = em.getReference(fkOpcion.getClass(), fkOpcion.getIdOpcion());
                menu.setFkOpcion(fkOpcion);
            }
            em.persist(menu);
            if (fkModulo != null) {
                fkModulo.getMenuCollection().add(menu);
                fkModulo = em.merge(fkModulo);
            }
            if (fkUsuario != null) {
                fkUsuario.getMenuCollection().add(menu);
                fkUsuario = em.merge(fkUsuario);
            }
            if (fkOpcion != null) {
                fkOpcion.getMenuCollection().add(menu);
                fkOpcion = em.merge(fkOpcion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Menu menu) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Menu persistentMenu = em.find(Menu.class, menu.getIdMenu());
            Modulo fkModuloOld = persistentMenu.getFkModulo();
            Modulo fkModuloNew = menu.getFkModulo();
            Usuario fkUsuarioOld = persistentMenu.getFkUsuario();
            Usuario fkUsuarioNew = menu.getFkUsuario();
            Opcion fkOpcionOld = persistentMenu.getFkOpcion();
            Opcion fkOpcionNew = menu.getFkOpcion();
            if (fkModuloNew != null) {
                fkModuloNew = em.getReference(fkModuloNew.getClass(), fkModuloNew.getIdModulo());
                menu.setFkModulo(fkModuloNew);
            }
            if (fkUsuarioNew != null) {
                fkUsuarioNew = em.getReference(fkUsuarioNew.getClass(), fkUsuarioNew.getIdUsuario());
                menu.setFkUsuario(fkUsuarioNew);
            }
            if (fkOpcionNew != null) {
                fkOpcionNew = em.getReference(fkOpcionNew.getClass(), fkOpcionNew.getIdOpcion());
                menu.setFkOpcion(fkOpcionNew);
            }
            menu = em.merge(menu);
            if (fkModuloOld != null && !fkModuloOld.equals(fkModuloNew)) {
                fkModuloOld.getMenuCollection().remove(menu);
                fkModuloOld = em.merge(fkModuloOld);
            }
            if (fkModuloNew != null && !fkModuloNew.equals(fkModuloOld)) {
                fkModuloNew.getMenuCollection().add(menu);
                fkModuloNew = em.merge(fkModuloNew);
            }
            if (fkUsuarioOld != null && !fkUsuarioOld.equals(fkUsuarioNew)) {
                fkUsuarioOld.getMenuCollection().remove(menu);
                fkUsuarioOld = em.merge(fkUsuarioOld);
            }
            if (fkUsuarioNew != null && !fkUsuarioNew.equals(fkUsuarioOld)) {
                fkUsuarioNew.getMenuCollection().add(menu);
                fkUsuarioNew = em.merge(fkUsuarioNew);
            }
            if (fkOpcionOld != null && !fkOpcionOld.equals(fkOpcionNew)) {
                fkOpcionOld.getMenuCollection().remove(menu);
                fkOpcionOld = em.merge(fkOpcionOld);
            }
            if (fkOpcionNew != null && !fkOpcionNew.equals(fkOpcionOld)) {
                fkOpcionNew.getMenuCollection().add(menu);
                fkOpcionNew = em.merge(fkOpcionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = menu.getIdMenu();
                if (findMenu(id) == null) {
                    throw new NonexistentEntityException("The menu with id " + id + " no longer exists.");
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
            Menu menu;
            try {
                menu = em.getReference(Menu.class, id);
                menu.getIdMenu();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The menu with id " + id + " no longer exists.", enfe);
            }
            Modulo fkModulo = menu.getFkModulo();
            if (fkModulo != null) {
                fkModulo.getMenuCollection().remove(menu);
                fkModulo = em.merge(fkModulo);
            }
            Usuario fkUsuario = menu.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario.getMenuCollection().remove(menu);
                fkUsuario = em.merge(fkUsuario);
            }
            Opcion fkOpcion = menu.getFkOpcion();
            if (fkOpcion != null) {
                fkOpcion.getMenuCollection().remove(menu);
                fkOpcion = em.merge(fkOpcion);
            }
            em.remove(menu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Menu> findMenuEntities() {
        return findMenuEntities(true, -1, -1);
    }

    public List<Menu> findMenuEntities(int maxResults, int firstResult) {
        return findMenuEntities(false, maxResults, firstResult);
    }

    private List<Menu> findMenuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Menu.class));
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

    public Menu findMenu(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Menu.class, id);
        } finally {
            em.close();
        }
    }

    public int getMenuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Menu> rt = cq.from(Menu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
