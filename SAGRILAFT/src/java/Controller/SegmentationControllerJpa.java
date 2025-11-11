package Controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class SegmentationControllerJpa implements Serializable {

    public SegmentationControllerJpa() {
        emf = Persistence.createEntityManagerFactory("SAGRILAFTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="LIST">
    public List ConsultSegmentation(String Format) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_Smt_c_ConsultSegmentation`('" + Format + "')");
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

    public List ConsultSegmentationId(int IdSegmentation) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_Smt_c_ConsultSegmentationId`('" + IdSegmentation + "')");
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

    public List ConsultSegmentationReport() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_Smt_c_ConsultSegmentationReport`()");
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

    public List ConsultSegmentationList() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_seg_c_ConsultSegList`()");
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

    public List ConsultSegmentationCompany(String BusinessAssociate) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_Smt_c_ConsultSegmentationCompany`('" + BusinessAssociate + "')");
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

    public List ConsultQueryMysql(String QueryMysql) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery(QueryMysql);
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PROCESS">
    public boolean UpdateControlSegmentation(int IdSegmentation, int Code, String Area, String PersonType, String DateMonitoring, String Pep, int Qualcustom, int QualMarket, int QualCommercial, int QualShipment, String ConcactPerson, String PerformsPost, int AnnualFrequency, long ValueSalesPurchases, int Antiquity, String SupplyChain, String BeneficiaryFinal, String TypeServiceOffered, String Observation, int idDoc, String nameBus) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_Smt_u_UpdateControlSegmentation`('" + IdSegmentation + "','" + Code + "','" + Area + "','" + PersonType + "','" + DateMonitoring + "','" + Pep + "','" + Qualcustom + "','" + QualMarket + "','" + QualCommercial + "','" + QualShipment + "','" + ConcactPerson + "','" + PerformsPost + "','" + AnnualFrequency + "','" + ValueSalesPurchases + "','" + Antiquity + "','" + SupplyChain + "','" + BeneficiaryFinal + "','" + TypeServiceOffered + "','" + Observation + "', " + idDoc + ", '" + nameBus + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean UpdateControlSegmentationInternational(int IdSegmentation, int Code, String Area, String PersonType, String DateMonitoring, String Pep, int Qualcustom, int QualMarket, int QualCommercial, int QualShipment, int BaselIndex, int CorruptionIndex, int BriberyIndex, String ConcactPerson, String PerformsPost, int AnnualFrequency, long ValueSalesPurchases, int Antiquity, String SupplyChain, String BeneficiaryFinal, String TypeServiceOffered, String Observation, int idDoc, String nameBus) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_Smt_u_UpdateControlSegmentationInternational`('" + IdSegmentation + "','" + Code + "','" + Area + "','" + PersonType + "','" + DateMonitoring + "','" + Pep + "','" + Qualcustom + "','" + QualMarket + "','" + QualCommercial + "','" + QualShipment + "','" + BaselIndex + "','" + CorruptionIndex + "','" + BriberyIndex + "','" + ConcactPerson + "','" + PerformsPost + "','" + AnnualFrequency + "','" + ValueSalesPurchases + "','" + Antiquity + "','" + SupplyChain + "','" + BeneficiaryFinal + "','" + TypeServiceOffered + "','" + Observation + "', " + idDoc + ", '" + nameBus + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean UpdateStateSegmentation(int IdSegmentation, int State) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_Smt_u_StateSegmentation`('" + IdSegmentation + "','" + State + "')");
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

    public boolean RegisterSegmentationByDocument(int idDoc, String NameBuss, String NitTx, String TypeBuss, String Benef, int codeOne, int CodeTwo, String City, String LegalR, String isPep, String xformat, String UserReg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_seg_r_RegisterSegmentationByDocument`(" + idDoc + ",'" + NameBuss + "','" + NitTx + "','" + TypeBuss + "','" + Benef + "','" + codeOne + "','" + CodeTwo + "','" + City + "','" + LegalR + "','" + isPep + "','" + xformat + "','" + UserReg + "')");
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

    public boolean UpdatePreviousSegmentation(String NameBuss, String NitTx) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_seg_u_UpdatePreviousSegmentation`('" + NameBuss + "','" + NitTx + "')");
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

    public boolean UpdateSegmentationNewDoc(int idSeg, String dte) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `Sp_seg_u_UpdateSegmentationNewDoc`(" + idSeg + ",'" + dte + "')");
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
    //</editor-fold>
}
