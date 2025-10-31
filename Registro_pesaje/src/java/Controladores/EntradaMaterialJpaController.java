package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntradaMaterialJpaController {

    public EntradaMaterialJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultaEntradaMaterial() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_emt_c_consulta_entrada`()");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultaEntradaMaterial_Id(int id_entrada) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_emt_c_consulta_entrada_id`('" + id_entrada + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean RegistrarEntradaMaterial(String fch, String trn, String lna, String pdt, String lpt, String ltc, String ltp, int ctd, String obs, String urg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_emt_r_registrar_entrada`('" + fch + "','" + trn + "','" + lna + "','" + pdt + "','" + lpt + "','" + ltc + "','" + ltp + "','" + ctd + "','" + obs + "','" + urg + "')");
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

    public boolean ModificarEntradaMaterial(int idEn, String fch, String trn, String lna, String pdt, String lpt, String ltc, String ltp, int ctd, String obs) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_emt_m_modificar_entrada`('" + idEn + "','" + fch + "','" + trn + "','" + lna + "','" + pdt + "','" + lpt + "','" + ltc + "','" + ltp + "','" + ctd + "','" + obs + "')");
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
    public boolean ActualizarFirmaEntrada(int idEn, int val, String frm) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_emt_a_actualizar_firmas_entrada_id`('" + idEn + "','" + val + "','" + frm + "')");
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
