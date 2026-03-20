/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.PPrueba;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.PruebaC;
import Entidades.Proyecto;
import Entidades.Prueba;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Prog.Aprendiz1
 */
public class PPruebaJpaController implements Serializable {

    public PPruebaJpaController() {
        emf = Persistence.createEntityManagerFactory("DisenoDesarrolloPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultar_p_prueba(int id_proyecto, int id_prueba_c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_p_pru_c_prueba`('" + id_proyecto + "', '" + id_prueba_c + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List traer_p_prueba_r(int id_prueba_c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_p_pru_t_prueba_d_r`('" + id_prueba_c + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List traer_p_prueba(int id_prueba_c, int id_prueba) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_p_pru_t_id_prueba`('" + id_prueba_c + "', '" + id_prueba + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List traer_p_prueba_cabecera(int id_proyecto, int id_p_prueba, int id_prueba_c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_p_pru_c_cabecera`('" + id_proyecto + "', '" + id_p_prueba + "', '" + id_prueba_c + "')");
            List retorna = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (retorna == null) {
                return null;
            } else {
                return retorna;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean insertar_p_prueba(String usuario, int id_proyecto, int id_prueba_c, int id_prueba) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_p_pru_r_prueba`('" + usuario + "','" + id_proyecto + "', '" + id_prueba_c + "', '" + id_prueba + "')");
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

    public boolean eliminar_p_prueba(int id_p_prueba) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_p_pru_e_prueba`('" + id_p_prueba + "')");
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

    public boolean Insert(String u_registro, int id_proyecto, int id_prueba_c, int id_prueba, double cuantitativo, double cuant_pos, double cuant_neg, String criterio) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("INSERT INTO p_prueba (usu_registro, fk_proyecto, fk_prueba_c, fk_prueba, cuantitativo, `cuantitativo+`, `cuantitativo-`, criterio) VALUES ('" + u_registro + "', " + id_proyecto + ", " + id_prueba_c + ", " + id_prueba + ", " + cuantitativo + ", " + cuant_pos + ", " + cuant_neg + ", '" + criterio + "')");
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
}
