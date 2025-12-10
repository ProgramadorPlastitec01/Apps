package controladoras;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class AnalisisPorAreaJpaController {

    public AnalisisPorAreaJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlesMicrobiologicosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean Registrar_analisis(int cabe, int uni, int desin, int tipare, int area, String anali, String volume, String lote, String produc, int am, int hongos, int levadura, String concep, String observa) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_apa_r_analisis`('" + cabe + "','" + uni + "','" + desin + "','" + tipare + "','" + area + "','" + anali + "','" + volume + "','" + lote + "','" + produc + "','" + am + "','" + hongos + "','" + levadura + "','" + concep + "','" + observa + "')");
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

    //CONSULTAS PARA EL INFORME.
    public List Informe_area_personal(String fin, String ffn, int iar) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_informe_fecha_personal`('" + fin + "','" + ffn + "','" + iar + "')");
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

    public List Informe_area_linea_produccion(String fin, String ffn, int iar) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_informe_fecha_linea_produccion`('" + fin + "','" + ffn + "','" + iar + "')");
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

    public List Informe_area_superficies(String fin, String ffn, int iar) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_informe_fecha_superficies`('" + fin + "','" + ffn + "','" + iar + "')");
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

    public List Informe_areas_lineas_produccion(String fin, String ffn) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_informe_areas_linea_produccion`('" + fin + "','" + ffn + "')");
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

    public List Informe_areas_personal(String fin, String ffn) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_informe_areas_personal`('" + fin + "','" + ffn + "')");
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

    public List Informe_areas_superficies(String fin, String ffn) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_informe_areas_superficies`('" + fin + "','" + ffn + "')");
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
    //FIN CONSULTAS PARA EL INFORME.

    public List Consulta_detalle_analisis(int icb) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_apa_t_detalle_analisis_id`('" + icb + "')");
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

    public List Consulta_detalle_analisis_Max(int icb) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_apa_t_detalle_analisis_id_max`('" + icb + "')");
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

    public boolean Modificar_analisis(int ict, int vl1, int tpo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        String atributo = "";
        if (tpo == 1) {
            atributo = "Micro_Orga_a_m";
        } else if (tpo == 2) {
            atributo = "Micro_Orga_Hongos";
        } else {
            atributo = "Micro_Orga_Levad";
        }
        try {
            Query q = em.createNativeQuery("UPDATE analisis_por_area SET " + atributo + " = '" + vl1 + "' WHERE idAnalisis_Por_Area = " + ict + "");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean Modificar_analisis_completo(int ict, String analisis, int vl1, int vl2, int vl3, String concepto) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("UPDATE analisis_por_area SET Analisis = '" + analisis + "' ,Micro_Orga_a_m = '" + vl1 + "',Micro_Orga_Hongos = '" + vl2 + "',Micro_Orga_Levad = '" + vl3 + "',Concepto ='" + concepto + "' WHERE idAnalisis_Por_Area = " + ict + "");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

}
