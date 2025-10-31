package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RecipienteJpaController implements Serializable {

    public RecipienteJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultarRecipientes() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rcp_c_consultarRecipiente`()");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
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

    public List ConsultarRecipientesId(int id_rec) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rcp_c_consultarRecipienteId`('" + id_rec + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
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

    public boolean RegistrarRecipiente(String txt_recipiente, String peso_reciente, String txt_bolsa, String peso_bolsa, int est, String usureg, String medida_recipiente, String medida_bolsa) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rcp_r_registrarRecipiente`('" + txt_recipiente + "','" + peso_reciente + "','" + txt_bolsa + "','" + peso_bolsa + "','" + est + "','" + usureg + "','" + medida_recipiente + "','" + medida_bolsa + "')");
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

    public boolean ModificarRecipiente(int id_rec, String txt_recipiente, String peso_reciente, String txt_bolsa, String peso_bolsa, String medida_recipiente, String medida_bolsa) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rec_m_modificarRecipiente`('" + id_rec + "','" + txt_recipiente + "','" + peso_reciente + "','" + txt_bolsa + "','" + peso_bolsa + "','" + medida_recipiente + "','" + medida_bolsa + "')");
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

    public boolean ModificarEstadoRecipienteId(int id_rec, int est) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rcp_m_ModificarEstadoRecipiente`('" + id_rec + "','" + est + "')");
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
