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
import Entidades.Menu;
import Entidades.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Prog.Aprendiz1
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
        emf = Persistence.createEntityManagerFactory("DisenoDesarrolloPU");
    }

    private EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    public List Login(String usuario, String password) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_usa_t_usuario`('" + usuario + "','" + password + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            }
            return retorna;
        } catch (Exception e) {
            return null;
        }
    }

    public List Traer_usuario(int id_usuario) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_usa_t_id_usuario`('" + id_usuario + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            }
            return retorna;
        } catch (Exception e) {
            return null;
        }
    }

    public List Consultar_usuarios() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_usa_c_usuario`()");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            }
            return retorna;
        } catch (Exception e) {
            return null;
        }
    }

    public List Consultar_usuario_linea() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_usa_c_l_usuario`()");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            }
            return retorna;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean Registrar_usuario(String responsable, String nombre, String apellido, int identificacion, String usuario, String password, int id_cargo, String linea, String mail) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_usa_r_usuario`('" + responsable + "', '" + nombre + "', '" + apellido + "', '" + identificacion + "', '" + usuario + "', '" + password + "', '" + id_cargo + "', '" + linea + "', '" + mail + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Modificar_usuario(int id_usuario, String responsable, String nombre, String apellido, int identificacion, String usuario, String password, int id_cargo, String linea, String correo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_usa_m_usuario`('" + id_usuario + "', '" + responsable + "', '" + nombre + "', '" + apellido + "', '" + identificacion + "', '" + usuario + "', '" + password + "', '" + id_cargo + "', '" + linea + "', '" + correo + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Estado_usuario(int id_usuario, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_usa_m_estado`('" + id_usuario + "','" + estado + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cambiarPass(int isa, String ctn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update usuario set password= '" + ctn + "' where id_usuario=" + isa + "");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean reestablecePass(int isa) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_m_restablecer_password_usuarios`('"+isa+"')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
