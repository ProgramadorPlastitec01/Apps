package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RolloEstriaVentanaJpaController {

    public RolloEstriaVentanaJpaController() {
        emf = Persistence.createEntityManagerFactory("Inspeccion_mangaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Traer_rollos_id_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rev_t_rollo_id_registro`('" + irg + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Traer_rollos_id_rollo(int irl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rev_t_rollo_id_rollo`('" + irl + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Traer_ultimo_rollo(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rev_t_ultimo_rollo`('" + ipd + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean Registrar_rollo(int irg, int nmr, String ecl, double pdi, double pdc, double pddf, double pdie, double pdce, double pdfe, double psmin, double psmax, double psmine, double psmaxs, double psfmin, double psfmax, double cv1, double cv2, double avt, double amg, double abb, String pbt, String ptc, double pr1, double pr2, int rsm, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rev_r_rollo`('" + irg + "', '" + nmr + "', '" + ecl + "', '" + pdi + "', '" + pdc + "', '" + pddf + "', '" + pdie + "', '" + pdce + "', '" + pdfe + "', '" + psmin + "', '" + psmax + "', '" + psmine + "', '" + psmaxs + "', '" + psfmin + "', '" + psfmax + "', '" + cv1 + "', '" + cv2 + "', '" + avt + "', '" + amg + "', '" + abb + "', '" + pbt + "', '" + ptc + "', '" + pr1 + "', '" + pr2 + "', '" + rsm + "', '" + urg + "')");
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

    public boolean Modificar_rollo(int irl, String ecl, double pdi, double pdc, double pddf, double pdie, double pdce, double pdfe, double psmin, double psmax, double psmine, double psmaxs, double psfmin, double psfmax, double cv1, double cv2, double avt, double amg, double abb, String pbt, String ptc, double pr1, double pr2, int rsm, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rev_m_rollo`('" + irl + "', '" + ecl + "', '" + pdi + "', '" + pdc + "', '" + pddf + "', '" + pdie + "', '" + pdce + "', '" + pdfe + "', '" + psmin + "', '" + psmax + "', '" + psmine + "', '" + psmaxs + "', '" + psfmin + "', '" + psfmax + "', '" + cv1 + "', '" + cv2 + "', '" + avt + "', '" + amg + "', '" + abb + "', '" + pbt + "', '" + ptc + "', '" + pr1 + "', '" + pr2 + "', '" + rsm + "', '" + urg + "')");
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

    public List Traer_rollos_id_producto(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rev_t_rollo_producto`('" + ipd + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Traer_rollos_lotes(String lpd, String ltc, String ltp) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rev_t_rollo_lotes`('" + lpd + "','" + ltc + "','" + ltp + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Traer_rollos_lotes_todos_p(String lpd, String ltc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rev_t_rollo_lotes_todos_p`('" + lpd + "','" + ltc + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Traer_rollos_lotes_estadistico(String lpd, String ltc, String ltp) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rev_t_rollo_lotes_estadistico`('" + lpd + "','" + ltc + "','" + ltp + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Traer_rollos_lotes_todos_p_estadistico(String lpd, String ltc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rev_t_rollo_lotes_todos_p_estadistico`('" + lpd + "','" + ltc + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }
    public boolean Cambiar_estado_calidad(int irl, String ecl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE rollo_estria_ventana SET estado_calidad = '" + ecl + "' WHERE id_rollo_estria_ventana = " + irl + "");
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
}
