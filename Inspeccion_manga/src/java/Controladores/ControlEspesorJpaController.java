package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ControlEspesorJpaController {

    public ControlEspesorJpaController() {
        emf = Persistence.createEntityManagerFactory("Inspeccion_mangaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Traer_controles_espesor_id_rollo(int irl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cep_t_control_espesor`('" + irl + "')");
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

    public List Traer_controles_espesor_id_producto(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cep_t_control_espesor_id_producto`('" + ipd + "')");
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

    public List Traer_controles_espesor_lotes(String lpd, String ltc, String ltp) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cep_t_control_espesor_lotes`('" + lpd + "','" + ltc + "','" + ltp + "')");
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

    public List Traer_controles_espesor_lotes_todos_p(String lpd, String ltc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cep_t_control_espesor_lotes_todos_p`('" + lpd + "','" + ltc + "')");
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

    public boolean Controles_vacios(int irl, int tma) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("INSERT INTO control_espesor (id_rollo,toma) VALUES (" + irl + "," + tma + ")");
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

    public boolean Registrar_controles_espesor(int ice, int psc, int cpo, double vlr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE control_espesor SET ps_" + psc + "_" + cpo + " = " + vlr + " WHERE id_control_espesor = " + ice);
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

    public boolean Registrar_controles_espesor_pared_doble(int ice, int psc, double vlr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE control_espesor SET pd_" + psc + " = " + vlr + " WHERE id_control_espesor = " + ice);
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

    public boolean Asignar_indicador_control(int ice, String idg, String urg, String amg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE control_espesor SET equipo_medicion = '" + idg + "', usuario_registro = '" + urg + "',ancho_manga = '" + amg + "' WHERE id_control_espesor = " + ice);
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

    public List Datos_estadisticos_controles_espesor(String nod, int ipd, String lpd, int iln, String fin, String ffn, int rin, int rfn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cep_generacion_estadistico`('" + nod + "'," + ipd + ",'" + lpd + "'," + iln + ",'" + fin.replace("/", "-") + "','" + ffn.replace("/", "-") + "'," + rin + "," + rfn + ")");
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

    public List Datos_estadisticos_controles_espesor_resumido(String nod, int ipd, String lpd, int iln, String fin, String ffn, int rin, int rfn, int rsm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cep_generacion_estadistico_resumido`('" + nod + "'," + ipd + ",'" + lpd + "'," + iln + ",'" + fin.replace("/", "-") + "','" + ffn.replace("/", "-") + "'," + rin + "," + rfn + "," + rsm + ")");
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
    public List Datos_estadisticos_controles_espesor_resumido_pp(String nod, int ipd, String lpd, int iln, String fin, String ffn, int rin, int rfn, int rsm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cep_generacion_estadistico_resumido_pp`('" + nod + "'," + ipd + ",'" + lpd + "'," + iln + ",'" + fin.replace("/", "-") + "','" + ffn.replace("/", "-") + "'," + rin + "," + rfn + "," + rsm + ")");
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
}
