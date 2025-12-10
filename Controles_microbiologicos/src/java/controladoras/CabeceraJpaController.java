package controladoras;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class CabeceraJpaController {

    public CabeceraJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlesMicrobiologicosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean Registrar_cabecera(String analisis, String fechamuestreo, String mediocultivo, String laboratorio, String especificaciones, String muestradopor, String horamuestreo, String fecharesultado, String observaciones, String tecnicaAnalisis, int idusuario, int id_tipo_nivel) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_r_cabecera`('" + analisis + "','" + fechamuestreo + "','" + mediocultivo + "','" + laboratorio + "','" + especificaciones + "','" + muestradopor + "','" + horamuestreo + "','" + fecharesultado + "','" + observaciones + "','" + tecnicaAnalisis + "','" + idusuario + "','" + id_tipo_nivel + "')");
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
    public boolean Estado_cabecera(int id_cabecera, int estado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_m_cabecera_estado`('" + id_cabecera + "','" + estado + "')");
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
    public boolean Registro_analisis_cabecera(int id_cabecera, int tipoN, String analisis) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_r_analisis_error`('" + id_cabecera + "','" + tipoN + "','" + analisis + "')");
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
    
//       public boolean Modificar_complemento(int idu, String nbe, String apl, String usr, String cta, String rol,int idus) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_cpm_m_complemento`('" + idu + "','" + nbe + "','" + apl + "','" + usr + "','" + cta + "','" + rol + "','" + idus + "')");
//            int exitoso = q.executeUpdate();
//            etm.getTransaction().commit();
//            etm.clear();
//            etm.close();
//            if (exitoso == 0) {
//                return false;
//            } else {
//                return true;
//            }
//        } catch (Exception ex) {
//            return false;
//        }
//    }

    public List Traer_cabecera_id(int icb) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_t_cabecera_id`('" + icb + "')");
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

    public List Consultar_cabeceras() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_c_cabeceras`()");
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
     public boolean Cerrar_Analisis(int idc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cbc_m_cerrar`('" + idc + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }
    public boolean Abrir_Analisis(int idc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cbc_m_abrir`('" + idc + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public List ConsultaCabecerasporAnalisis(String anl) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cbc_t_cabeceras_analisis_filtro`('" + anl + "')");
            List resultados = q.getResultList();
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
