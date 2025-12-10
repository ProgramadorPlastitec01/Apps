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
import Entidades.HerramentalC;
import Entidades.HerramentalD;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Prog.Aprendiz1
 */
public class HerramentalDJpaController implements Serializable {

    public HerramentalDJpaController() {
        emf = Persistence.createEntityManagerFactory("DisenoDesarrolloPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List traer_d_cc_herramental(int id_herramental_c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_hrm_d_cc_herramental`('" + id_herramental_c + "')");
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

    public List traer_d_cd_herramental(int id_herramental_c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_hrm_d_cd_herramental`('" + id_herramental_c + "')");
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

    public List traer_id_herramental_d(int id_herramental_c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_hrm_d_t_id_herramental`('" + id_herramental_c + "')");
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

    public boolean modificar_herrmental_d(int id_herramental_d, String t_titulo, String texto) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_hrm_d_m_herramental`('" + id_herramental_d + "','" + t_titulo + "','" + texto + "')");
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

    public boolean insertar_herrmental_d(String responsable, int id_herramental_c, String tipo_categoria, String categoria) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_hrm_d_r_herramental`('" + responsable + "','" + id_herramental_c + "','" + tipo_categoria + "','" + categoria + "')");
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

    public boolean Adjuntos_herramental(String ajt, int ihd) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("UPDATE herramental_d SET adjunto = '" + ajt + "' WHERE id_herramental_d = " + ihd);
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
