/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.Parametros;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Prog.Aprendiz1
 */
public class ParametrosJpaController implements Serializable {

    public ParametrosJpaController() {
        emf = Persistence.createEntityManagerFactory("DisenoDesarrolloPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Info_correo() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Correo')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Mensaje_correo() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Mensaje_Correo')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Tipo_proyecto() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Tipo_Proyecto')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Tipo_E_produccion() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Tipo_E_produccion')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Tipo_Material_E_produccion() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Tipo_Material_E_produccion')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Capa_E_produccion() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Capa_E_produccion')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List tipo_prog_prueba() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Tipo_prog_prueba')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Ver_lista_distribucion() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Ver_lista_distribucion')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Ver_norma() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Norma')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List categoria_prueba() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Catego_pruebas')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Tipo_categoria() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Tipo_categoria')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Tipo_campo_categoria() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Tipo_campo_categoria')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    public List adjunto_catego() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Archivos_adj_categorias')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
