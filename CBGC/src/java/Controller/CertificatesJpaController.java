package Controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CertificatesJpaController implements Serializable {

    public CertificatesJpaController() {
        emf = Persistence.createEntityManagerFactory("CBGCPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultCeritcateType(String Type) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_ctf_c_ConsultCertificates`('" + Type + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
    public List ConsultCeritcateTypeId(String Type) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_ctf_c_ConsultCertificatesId`('" + Type + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
    public List ConsultCertificatesIdHtml(int IdCertificates) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_ctf_c_ConsultCertificatesIdHtml`('" + IdCertificates + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean CertificatesRegister(String Tpe, String Cde, int Ord, String Pdt, String Btc, String Cql, String Ctm, String Urg, String Fmt) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_cft_r_RegisterCertificates`('" + Tpe + "','" + Cde + "','" + Ord + "','" + Pdt + "','" + Btc + "','" + Cql + "','" + Ctm + "','" + Urg + "','" + Fmt + "')");
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
    public boolean CertificatesUpdate(int IdC, String Cql, String Fmt) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_ctf_u_UpdateCertificates`('" + IdC + "','" + Cql + "','" + Fmt + "')");
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
    public boolean CertificatesUpdateSignature(int IdC, String Asg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_ctf_u_UpdateCertificatesSignature`('" + IdC + "','" + Asg + "')");
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
