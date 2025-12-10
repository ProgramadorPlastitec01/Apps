/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladoras;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Prog.sistemas2
 */
public class TipoNivelJpaController implements Serializable {

    public TipoNivelJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlesMicrobiologicosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean RegistrarTipoNivel(int id_tipoN,String tipo, int dato, int cumple, int alerta, int accion, int incumplimiento) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpn_r_tipo_nivel`('" + id_tipoN + "','" + tipo + "','" + dato + "','" + cumple + "','" + alerta + "','" + accion + "','" + incumplimiento + "')");
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
    public boolean EstadoTipoNivel(int id_tipoN) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpn_m_tipo_nivel_estado`('" + id_tipoN + "')");
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

    public List ConsultaTiposNivel() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpn_c_tipos_nivel`()");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados.isEmpty()) {
                return null;
            } else {
                return resultados;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultaTipoNivelId(int id_tipoNivel) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_tpn_c_tipo_nivel_id`('" + id_tipoNivel + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados.isEmpty()) {
                return null;
            } else {
                return resultados;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
