package Controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CertificatesJpaController implements Serializable {

    public CertificatesJpaController() {
        emf = Persistence.createEntityManagerFactory("COAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultCeritcateType(String Type, int State) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_ctf_c_ConsultCertificates`('" + Type + "','" + State + "')");
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

    public List ConsultCertificatesId(String Type, int IdCertificate) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_ctf_c_ConsultCertificatesId`('" + Type + "','" + IdCertificate + "')");
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

    public List ConsultCertificatesSignature(int State) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_ctf_c_ConsultCertificatesSignature`('" + State + "')");
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

    public List ConsultCertificatesSignatureId(int State, int IdCertificate) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_ctf_c_ConsultCertificatesSignatureId`('" + State + "','" + IdCertificate + "')");
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

    public List ConsultCeritcateTypeDelete(String Type) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_ctf_c_ConsultCertificatesDelete`('" + Type + "')");
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

    public List ConsultCertificatesIdType(String Type) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_ctf_c_ConsultCertificatesIdType`('" + Type + "')");
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

    /**
     * Datos crudos del certificado necesarios para "Actualizar desde
     * Factory": a diferencia de Sp_ctf_c_ConsultCertificatesId (que no trae
     * material_batch), esta consulta directa permite reenviar
     * CertificatesUpdate sin pisar el material_batch que usa Batch Record.
     * Es una SELECT simple (no un procedimiento almacenado) porque solo lee
     * columnas ya existentes en certificates, sin lógica de negocio.
     */
    public Object[] ConsultCertificateDataForUpdate(int IdCertificates) {
        EntityManager etm = getEntityManager();
        try {
            Query q = etm.createNativeQuery(
                    "SELECT consecutive_quality, amount, dispatch_date, material_batch, format, `order`, product, batch "
                    + "FROM certificates WHERE id_certificate = ?");
            q.setParameter(1, IdCertificates);
            List consulta = q.getResultList();
            if (consulta.isEmpty()) {
                return null;
            }
            return (Object[]) consulta.get(0);
        } catch (Exception ex) {
            return null;
        } finally {
            etm.close();
        }
    }

    public List ConsultCertificatesBatchRecord(String Order, String Batch) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_ctf_c_ConsultCertificatesBatchRecord`('" + Order + "','" + Batch + "')");
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

    /**
     * Registra el certificado y devuelve el id_certificate generado (0 si
     * falla). El id se toma con LAST_INSERT_ID() dentro de la misma
     * transacción (misma conexión): antes se buscaba "el último por tipo"
     * con Sp_ctf_c_ConsultCertificatesIdType, que ordena por state ASC y
     * devolvía un certificado eliminado (state 0) o el de otro usuario.
     */
    public int CertificatesRegister(String Tpe, String Cde, int Ord, String Pdt, String Btc, String Cql, String Ctm, String Amt, String Ddt, String Urg, String Mbt, int Arg ,String Fmt, int IdUserReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_cft_r_RegisterCertificates`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            q.setParameter(1, Tpe);
            q.setParameter(2, Cde);
            q.setParameter(3, Ord);
            q.setParameter(4, Pdt);
            q.setParameter(5, Btc);
            q.setParameter(6, Cql);
            q.setParameter(7, Ctm);
            q.setParameter(8, Amt);
            q.setParameter(9, Ddt);
            q.setParameter(10, Urg);
            q.setParameter(11, Mbt);
            q.setParameter(12, Arg);
            q.setParameter(13, Fmt);
            q.setParameter(14, IdUserReg);
            int resultado = q.executeUpdate();
            int idGenerado = 0;
            if (resultado == 1) {
                Object id = em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult();
                idGenerado = (id == null) ? 0 : ((Number) id).intValue();
            }
            em.getTransaction().commit();
            em.clear();
            em.close();
            return idGenerado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            return 0;
        }
    }

    public boolean CertificatesUpdate(int IdC, String Cql, String Amt, String Ddt, String Mbt, String Fmt, int IdUser) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_ctf_u_UpdateCertificates`(?, ?, ?, ?, ?, ?, ?)");
            q.setParameter(1, IdC);
            q.setParameter(2, Cql);
            q.setParameter(3, Amt);
            q.setParameter(4, Ddt);
            q.setParameter(5, Mbt);
            q.setParameter(6, Fmt);
            q.setParameter(7, IdUser);
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
            Query q = em.createNativeQuery("CALL `Sp_ctf_u_UpdateCertificatesSignature`(?, ?)");
            q.setParameter(1, IdC);
            q.setParameter(2, Asg);
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            return resultado == 1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public boolean RegisterNovelty(int IdC, String Ctg, String Dpc, String Urg, int Ste) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_nvt_r_RegisterNovelty`('" + IdC + "','" + IdC + "','" + Ctg + "','" + Dpc + "','" + Urg + "','" + Ste + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 2) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean UpdateCertificateFinish(int IdC) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_ctf_u_UpdateCertificateFinish`('" + IdC + "')");
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

    /** @return el id_certificate del duplicado recién creado, o null si falló. */
    public Integer DuplicateRegister(int IdC, String UserReg, int IdUserReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_ctf_r_RegistrerDuplicate`(?, ?, ?)");
            q.setParameter(1, IdC);
            q.setParameter(2, UserReg);
            q.setParameter(3, IdUserReg);
            List filas = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (filas != null && !filas.isEmpty() && filas.get(0) != null) {
                return ((Number) filas.get(0)).intValue();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /** @return [0]=email, [1]=nombre del usuario responsable del certificado, o null si no hay dato. */
    public List ConsultCertificateResponsible(int IdCertificate) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_ctf_c_ConsultCertificateResponsible`('" + IdCertificate + "')");
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

}
