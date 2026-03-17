package Controladores;

import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class NoConformidadJpaController {

    public NoConformidadJpaController() {
        emf = Persistence.createEntityManagerFactory("PVMPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaSerial(String bus) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_c_serial_nconforme`('" + bus + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaConsecutivos() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("select id_instrumento_medicion , consecutivo from no_conformidad order by consecutivo desc ");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaPlantilla() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_c_plantilla_noconforme`()");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultasRgtNoConformes() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_c_rgtnoconformes`()");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List mailNoConforme() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_c_email_noconforme`()");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultado.isEmpty()) {
                return resultado;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List registroNoConforme(int id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_c_rgtnoconforme_id`('" + id + "')");
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

    public boolean registroNoConformidad(int id_ins, int cns, String fch, String usu_rgt) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query rgt = em.createNativeQuery("CALL `sp_r_noconformidad`('" + id_ins + "','" + cns + "','" + fch + "','" + usu_rgt + "')");
            int rta = rgt.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (rta == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarRegistroNoConformidad(int id_nocon, String plt) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query rgt = em.createNativeQuery("CALL `sp_u_rgtnoconforme_id`('" + id_nocon + "','" + plt + "')");
            int rta = rgt.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (rta == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarEstadoRegistroNoConformidad(int id, int est) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query rgt = em.createNativeQuery("CALL `sp_u_estado_rgtnoconforme`('" + id + "','" + est + "')");
            int rta = rgt.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (rta == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
