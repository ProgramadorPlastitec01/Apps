/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import Controladoras.exceptions.IllegalOrphanException;
import Controladoras.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.TipoSoporte;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.Pendiente;
import Entidades.Rol;
import Entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prog.sistemas2
 */
public class RolJpaController implements Serializable {

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getTipoSoporteCollection() == null) {
            rol.setTipoSoporteCollection(new ArrayList<TipoSoporte>());
        }
        if (rol.getPendienteCollection() == null) {
            rol.setPendienteCollection(new ArrayList<Pendiente>());
        }
        if (rol.getUsuarioCollection() == null) {
            rol.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TipoSoporte> attachedTipoSoporteCollection = new ArrayList<TipoSoporte>();
            for (TipoSoporte tipoSoporteCollectionTipoSoporteToAttach : rol.getTipoSoporteCollection()) {
                tipoSoporteCollectionTipoSoporteToAttach = em.getReference(tipoSoporteCollectionTipoSoporteToAttach.getClass(), tipoSoporteCollectionTipoSoporteToAttach.getIdTipoSoporte());
                attachedTipoSoporteCollection.add(tipoSoporteCollectionTipoSoporteToAttach);
            }
            rol.setTipoSoporteCollection(attachedTipoSoporteCollection);
            Collection<Pendiente> attachedPendienteCollection = new ArrayList<Pendiente>();
            for (Pendiente pendienteCollectionPendienteToAttach : rol.getPendienteCollection()) {
                pendienteCollectionPendienteToAttach = em.getReference(pendienteCollectionPendienteToAttach.getClass(), pendienteCollectionPendienteToAttach.getIdPendiente());
                attachedPendienteCollection.add(pendienteCollectionPendienteToAttach);
            }
            rol.setPendienteCollection(attachedPendienteCollection);
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : rol.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdUsuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            rol.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(rol);
            for (TipoSoporte tipoSoporteCollectionTipoSoporte : rol.getTipoSoporteCollection()) {
                Rol oldIdRolOfTipoSoporteCollectionTipoSoporte = tipoSoporteCollectionTipoSoporte.getIdRol();
                tipoSoporteCollectionTipoSoporte.setIdRol(rol);
                tipoSoporteCollectionTipoSoporte = em.merge(tipoSoporteCollectionTipoSoporte);
                if (oldIdRolOfTipoSoporteCollectionTipoSoporte != null) {
                    oldIdRolOfTipoSoporteCollectionTipoSoporte.getTipoSoporteCollection().remove(tipoSoporteCollectionTipoSoporte);
                    oldIdRolOfTipoSoporteCollectionTipoSoporte = em.merge(oldIdRolOfTipoSoporteCollectionTipoSoporte);
                }
            }
            for (Pendiente pendienteCollectionPendiente : rol.getPendienteCollection()) {
                Rol oldIdUsuarioRecibeOfPendienteCollectionPendiente = pendienteCollectionPendiente.getIdUsuarioRecibe();
                pendienteCollectionPendiente.setIdUsuarioRecibe(rol);
                pendienteCollectionPendiente = em.merge(pendienteCollectionPendiente);
                if (oldIdUsuarioRecibeOfPendienteCollectionPendiente != null) {
                    oldIdUsuarioRecibeOfPendienteCollectionPendiente.getPendienteCollection().remove(pendienteCollectionPendiente);
                    oldIdUsuarioRecibeOfPendienteCollectionPendiente = em.merge(oldIdUsuarioRecibeOfPendienteCollectionPendiente);
                }
            }
            for (Usuario usuarioCollectionUsuario : rol.getUsuarioCollection()) {
                Rol oldIdRolOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getIdRol();
                usuarioCollectionUsuario.setIdRol(rol);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldIdRolOfUsuarioCollectionUsuario != null) {
                    oldIdRolOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldIdRolOfUsuarioCollectionUsuario = em.merge(oldIdRolOfUsuarioCollectionUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getIdRol());
            Collection<TipoSoporte> tipoSoporteCollectionOld = persistentRol.getTipoSoporteCollection();
            Collection<TipoSoporte> tipoSoporteCollectionNew = rol.getTipoSoporteCollection();
            Collection<Pendiente> pendienteCollectionOld = persistentRol.getPendienteCollection();
            Collection<Pendiente> pendienteCollectionNew = rol.getPendienteCollection();
            Collection<Usuario> usuarioCollectionOld = persistentRol.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = rol.getUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            for (TipoSoporte tipoSoporteCollectionOldTipoSoporte : tipoSoporteCollectionOld) {
                if (!tipoSoporteCollectionNew.contains(tipoSoporteCollectionOldTipoSoporte)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TipoSoporte " + tipoSoporteCollectionOldTipoSoporte + " since its idRol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TipoSoporte> attachedTipoSoporteCollectionNew = new ArrayList<TipoSoporte>();
            for (TipoSoporte tipoSoporteCollectionNewTipoSoporteToAttach : tipoSoporteCollectionNew) {
                tipoSoporteCollectionNewTipoSoporteToAttach = em.getReference(tipoSoporteCollectionNewTipoSoporteToAttach.getClass(), tipoSoporteCollectionNewTipoSoporteToAttach.getIdTipoSoporte());
                attachedTipoSoporteCollectionNew.add(tipoSoporteCollectionNewTipoSoporteToAttach);
            }
            tipoSoporteCollectionNew = attachedTipoSoporteCollectionNew;
            rol.setTipoSoporteCollection(tipoSoporteCollectionNew);
            Collection<Pendiente> attachedPendienteCollectionNew = new ArrayList<Pendiente>();
            for (Pendiente pendienteCollectionNewPendienteToAttach : pendienteCollectionNew) {
                pendienteCollectionNewPendienteToAttach = em.getReference(pendienteCollectionNewPendienteToAttach.getClass(), pendienteCollectionNewPendienteToAttach.getIdPendiente());
                attachedPendienteCollectionNew.add(pendienteCollectionNewPendienteToAttach);
            }
            pendienteCollectionNew = attachedPendienteCollectionNew;
            rol.setPendienteCollection(pendienteCollectionNew);
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            rol.setUsuarioCollection(usuarioCollectionNew);
            rol = em.merge(rol);
            for (TipoSoporte tipoSoporteCollectionNewTipoSoporte : tipoSoporteCollectionNew) {
                if (!tipoSoporteCollectionOld.contains(tipoSoporteCollectionNewTipoSoporte)) {
                    Rol oldIdRolOfTipoSoporteCollectionNewTipoSoporte = tipoSoporteCollectionNewTipoSoporte.getIdRol();
                    tipoSoporteCollectionNewTipoSoporte.setIdRol(rol);
                    tipoSoporteCollectionNewTipoSoporte = em.merge(tipoSoporteCollectionNewTipoSoporte);
                    if (oldIdRolOfTipoSoporteCollectionNewTipoSoporte != null && !oldIdRolOfTipoSoporteCollectionNewTipoSoporte.equals(rol)) {
                        oldIdRolOfTipoSoporteCollectionNewTipoSoporte.getTipoSoporteCollection().remove(tipoSoporteCollectionNewTipoSoporte);
                        oldIdRolOfTipoSoporteCollectionNewTipoSoporte = em.merge(oldIdRolOfTipoSoporteCollectionNewTipoSoporte);
                    }
                }
            }
            for (Pendiente pendienteCollectionOldPendiente : pendienteCollectionOld) {
                if (!pendienteCollectionNew.contains(pendienteCollectionOldPendiente)) {
                    pendienteCollectionOldPendiente.setIdUsuarioRecibe(null);
                    pendienteCollectionOldPendiente = em.merge(pendienteCollectionOldPendiente);
                }
            }
            for (Pendiente pendienteCollectionNewPendiente : pendienteCollectionNew) {
                if (!pendienteCollectionOld.contains(pendienteCollectionNewPendiente)) {
                    Rol oldIdUsuarioRecibeOfPendienteCollectionNewPendiente = pendienteCollectionNewPendiente.getIdUsuarioRecibe();
                    pendienteCollectionNewPendiente.setIdUsuarioRecibe(rol);
                    pendienteCollectionNewPendiente = em.merge(pendienteCollectionNewPendiente);
                    if (oldIdUsuarioRecibeOfPendienteCollectionNewPendiente != null && !oldIdUsuarioRecibeOfPendienteCollectionNewPendiente.equals(rol)) {
                        oldIdUsuarioRecibeOfPendienteCollectionNewPendiente.getPendienteCollection().remove(pendienteCollectionNewPendiente);
                        oldIdUsuarioRecibeOfPendienteCollectionNewPendiente = em.merge(oldIdUsuarioRecibeOfPendienteCollectionNewPendiente);
                    }
                }
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    usuarioCollectionOldUsuario.setIdRol(null);
                    usuarioCollectionOldUsuario = em.merge(usuarioCollectionOldUsuario);
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Rol oldIdRolOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getIdRol();
                    usuarioCollectionNewUsuario.setIdRol(rol);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldIdRolOfUsuarioCollectionNewUsuario != null && !oldIdRolOfUsuarioCollectionNewUsuario.equals(rol)) {
                        oldIdRolOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldIdRolOfUsuarioCollectionNewUsuario = em.merge(oldIdRolOfUsuarioCollectionNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rol.getIdRol();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
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
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getIdRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TipoSoporte> tipoSoporteCollectionOrphanCheck = rol.getTipoSoporteCollection();
            for (TipoSoporte tipoSoporteCollectionOrphanCheckTipoSoporte : tipoSoporteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rol (" + rol + ") cannot be destroyed since the TipoSoporte " + tipoSoporteCollectionOrphanCheckTipoSoporte + " in its tipoSoporteCollection field has a non-nullable idRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Pendiente> pendienteCollection = rol.getPendienteCollection();
            for (Pendiente pendienteCollectionPendiente : pendienteCollection) {
                pendienteCollectionPendiente.setIdUsuarioRecibe(null);
                pendienteCollectionPendiente = em.merge(pendienteCollectionPendiente);
            }
            Collection<Usuario> usuarioCollection = rol.getUsuarioCollection();
            for (Usuario usuarioCollectionUsuario : usuarioCollection) {
                usuarioCollectionUsuario.setIdRol(null);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRol(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
