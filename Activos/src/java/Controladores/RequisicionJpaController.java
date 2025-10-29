package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RequisicionJpaController implements Serializable {

    public RequisicionJpaController() {
        emf = Persistence.createEntityManagerFactory("ActivosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean registrarRequisicion(String rep, String emt, double ctd, String mrc, String dtn, String fed, int cla, String und, int prd, String nombre, int iar, String ere, String ctn, String cec, String reg, String pro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_r_requisicion`('" + rep + "','" + emt + "','" + ctd + "','" + mrc + "','" + dtn + "','" + fed + "','" + cla + "','" + und + "','" + prd + "','" + nombre + "','" + iar + "','" + ere + "','" + ctn + "','" + cec + "','" + reg + "','" + pro + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registrarMasivo(String rep, String emt, double ctd, String mrc, String dtn, String fed, int cla, String und, int prd, String nombre, int iar, String ere, String ctn, String cec, String reg, String pro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_r_requisicion`('" + rep + "','" + emt + "','" + ctd + "','" + mrc + "','" + dtn + "','" + fed + "','" + cla + "','" + und + "','" + prd + "','" + nombre + "','" + iar + "','" + ere + "','" + ctn + "','" + cec + "','" + reg + "','" + pro + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registrarRequisicionPR(String emt, String ctd, String mrc, String dtn, String fed, int icc, String und, String nombre, int iar) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_r_requisicion`('" + emt + "','" + ctd + "','" + mrc + "','" + dtn + "','" + fed + "','" + icc + "','" + und + "','" + nombre + "','" + iar + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registrarRequisicionDuplicada(int idR, String urg, String obs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_d_duplicar_requisicion`('" + idR + "','" + urg + "','" + obs + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarCotizacion(int ids, String ctz, String det, String fchC, String nom) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE requisicion_material r SET r.referenciag='" + ctz + "', r.responsable_c='" + nom + "', r.fecha_cotizacion=CONCAT('" + fchC + "',' ',TIME(NOW())), r.detalle_cotizacion='" + det + "' WHERE r.id_requisicion='" + ids + "'");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean actualizarProcesoCompra(int ids, String det, String fchC, String nom) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE requisicion_material r SET r.responsable_pc='" + nom + "', r.fecha_procesocompra=CONCAT('" + fchC + "',' ',TIME(NOW())), r.dellate_proc_compra='" + det + "' WHERE r.id_requisicion='" + ids + "'");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List consultarRequisicion(int estado) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion`('" + estado + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarAreas() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ara_t_areas`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarRequisicionA(int estado) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicionA`('" + estado + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaCorreo(int iar) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion_correo`('" + iar + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaContadorEstadoAC() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String ConsutlaEst = "SELECT 'Solicitud' AS 'Est',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 1 AND m.id_area = 1 LIMIT 1) AS 'AU',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 1 AND m.id_area = 2 LIMIT 1) AS 'PR',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 1 AND m.id_area = 3 LIMIT 1) AS 'MTTO GN',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 1 AND m.id_area = 4 LIMIT 1) AS 'MTTO FARMA',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 1 AND m.id_area = 5 LIMIT 1) AS 'MTTO INS',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 1 LIMIT 1) AS 'TOTAL'\n"
                    + "UNION ALL \n"
                    + "SELECT 'Cotización' AS 'Est',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 2 AND m.id_area = 1 LIMIT 1) AS 'AU',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 2 AND m.id_area = 2 LIMIT 1) AS 'PR',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 2 AND m.id_area = 3 LIMIT 1) AS 'MTTO GN',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 2 AND m.id_area = 4 LIMIT 1) AS 'MTTO FARMA',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 2 AND m.id_area = 5 LIMIT 1) AS 'MTTO INS',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 2 LIMIT 1) AS 'TOTAL'\n"
                    + "UNION ALL \n"
                    + "SELECT 'Proceso compra' AS 'Est',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 8 AND m.id_area = 1 LIMIT 1) AS 'AU',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 8 AND m.id_area = 2 LIMIT 1) AS 'PR',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 8 AND m.id_area = 3 LIMIT 1) AS 'MTTO GN',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 8 AND m.id_area = 4 LIMIT 1) AS 'MTTO FARMA',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 8 AND m.id_area = 5 LIMIT 1) AS 'MTTO INS',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 8 LIMIT 1) AS 'TOTAL'\n"
                    + "UNION ALL \n"
                    + "SELECT 'Orden compra' AS 'Est',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 3 AND m.id_area = 1 LIMIT 1) AS 'AU',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 3 AND m.id_area = 2 LIMIT 1) AS 'PR',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 3 AND m.id_area = 3 LIMIT 1) AS 'MTTO GN',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 3 AND m.id_area = 4 LIMIT 1) AS 'MTTO FARMA',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 3 AND m.id_area = 5 LIMIT 1) AS 'MTTO INS',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 3 LIMIT 1) AS 'TOTAL'\n"
                    + "UNION ALL \n"
                    + "SELECT 'Generados' AS 'Est',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 4 AND m.id_area = 1 LIMIT 1) AS 'AU',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 4 AND m.id_area = 2 LIMIT 1) AS 'PR',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 4 AND m.id_area = 3 LIMIT 1) AS 'MTTO GN',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 4 AND m.id_area = 4 LIMIT 1) AS 'MTTO FARMA',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 4 AND m.id_area = 5 LIMIT 1) AS 'MTTO INS',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 4 LIMIT 1) AS 'TOTAL'\n"
                    + "UNION ALL \n"
                    + "SELECT 'Disponible' AS 'Est',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 5 AND m.id_area = 1 LIMIT 1) AS 'AU',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 5 AND m.id_area = 2 LIMIT 1) AS 'PR',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 5 AND m.id_area = 3 LIMIT 1) AS 'MTTO GN',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 5 AND m.id_area = 4 LIMIT 1) AS 'MTTO FARMA',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 5 AND m.id_area = 5 LIMIT 1) AS 'MTTO INS',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 5 LIMIT 1) AS 'TOTAL'\n"
                    + "UNION ALL \n"
                    + "SELECT 'Entregado' AS 'Est',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 6 AND m.id_area = 1 LIMIT 1) AS 'AU',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 6 AND m.id_area = 2 LIMIT 1) AS 'PR',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 6 AND m.id_area = 3 LIMIT 1) AS 'MTTO GN',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 6 AND m.id_area = 4 LIMIT 1) AS 'MTTO FARMA',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 6 AND m.id_area = 5 LIMIT 1) AS 'MTTO INS',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 6 LIMIT 1) AS 'TOTAL'\n"
                    + "UNION ALL \n"
                    + "SELECT 'Declinado' AS 'Est',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 0 AND m.id_area = 1 LIMIT 1) AS 'AU',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 0 AND m.id_area = 2 LIMIT 1) AS 'PR',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 0 AND m.id_area = 3 LIMIT 1) AS 'MTTO GN',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 0 AND m.id_area = 4 LIMIT 1) AS 'MTTO FARMA',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 0 AND m.id_area = 5 LIMIT 1) AS 'MTTO INS',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 0 LIMIT 1) AS 'TOTAL'\n"
                    + "UNION ALL \n"
                    + "SELECT 'Devolución' AS 'Est',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 7 AND m.id_area = 1 LIMIT 1) AS 'AU',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 7 AND m.id_area = 2 LIMIT 1) AS 'PR',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 7 AND m.id_area = 3 LIMIT 1) AS 'MTTO GN',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 7 AND m.id_area = 4 LIMIT 1) AS 'MTTO FARMA',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 7 AND m.id_area = 5 LIMIT 1) AS 'MTTO INS',\n"
                    + "		 (SELECT COUNT(m.id_requisicion) FROM requisicion_material m WHERE m.estado = 7 LIMIT 1) AS 'TOTAL'";
            Query q = etm.createNativeQuery(ConsutlaEst);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaContadorEstado(int iar) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String ConsutlaEst = "Select (SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 1 and r.id_area = " + iar + ") as 'Solicitud', "
                    + "			(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 2 and r.id_area = " + iar + ") as 'Cotización', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 8 and r.id_area = " + iar + ") as 'Proceso Compra', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 3 and r.id_area = " + iar + ") as 'Orden compra', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 4 and r.id_area = " + iar + ") as 'Generados', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 5 and r.id_area = " + iar + ") as 'Disponible', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 6 and r.id_area = " + iar + ") as 'Entregado', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 0 and r.id_area = " + iar + ") as 'Declinado', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 7 and r.id_area = " + iar + ") as 'Devolución'";
            Query q = etm.createNativeQuery(ConsutlaEst);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaContadorEstadoTotal() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String ConsutlaEst = "Select (SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 1) as 'Solicitud', "
                    + "			(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 2) as 'Cotización', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 8) as 'Proceso Compra', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 3) as 'Orden compra', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 4) as 'Generados', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 5) as 'Disponible', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 6) as 'Entregado', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 0) as 'Declinado', "
                    + "		 	(SELECT COUNT(r.id_requisicion) FROM requisicion_material r WHERE r.estado = 7) as 'Devolución'";
            Query q = etm.createNativeQuery(ConsutlaEst);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarArea() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select id_area from area ");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarAreaCorreo() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select correo from area ");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarGeneral(int id_area) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_contenido_general`('" + id_area + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarGeneralMTTO() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_contenido_general_mantenimiento`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaRequisicionArea(int estado, int id_area) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion_area`('" + estado + "','" + id_area + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public List consultaRequisicionAreaControl(int id_area) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion_area_control`('" + id_area + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaRequisicionAreaAnio(int estado, int id_area, int anio) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion_area_anio`('" + estado + "','" + id_area + "','" + anio + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List FiltroRequisicionAnio() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_c_filtro_anio`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaRequisicionEstado(int estado, int limit) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion_estado`('" + estado + "','" + limit + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaRequisicionEstadoFiltro(int estado, int campo, String busqueda) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion_estado_filtro`('" + estado + "','" + campo + "','" + busqueda + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaRequisicionEstadoAnio(int estado, int anio) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion_estado_anio`('" + estado + "','" + anio + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaRequisicionEstadoAnioArea(int estado, int anio, int id_area) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion_estado_anio_area`('" + estado + "','" + anio + "','" + id_area + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarRequisicionesTotal() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_c_requisiciones_total`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarRequisicionesTotalArea(int id_area) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_c_requisiciones_total_area`('" + id_area + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarDescargaDosEstadosArea2(String fechai, String fechaf, int id_area, int estado, int estados) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_descarga_2estA`('" + fechai + "','" + fechaf + "','" + id_area + "','" + estado + "','" + estados + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarDescargaDosEstadosAreaPrioridad(String fechai, String fechaf, int id_area, int estado, int estados, int prd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_c_disp_ent_2estAP`('" + fechai + "','" + fechaf + "','" + id_area + "','" + estado + "','" + estados + "','" + prd + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultarCorreoMasivo(String irs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        String query = "SELECT r.id_requisicion,r.elemento,r.marca,r.cantidad,r.destino,DATE_FORMAT(r.fecha_solictud,'%Y-%m-%d') as fecha_solicitud,DATE_FORMAT(r.fecha_estimada,'%Y-%m-%d') as fecha_estimada ,c.nombre,"
                + "	       r.usuario_registro,r.editor_req,r.estado,IF(r.prioridad = 0,'NORMAL','ALTA') as prioirdad,r.detalle_cotizacion,DATE_FORMAT(r.fecha_cotizacion,'%Y-%m-%d'),r.dellate_proc_compra,DATE_FORMAT(r.fecha_procesocompra,'%Y-%m-%d'),"
                + "	       r.dellate_ordecompra,DATE_FORMAT(r.fecha_ordencompra,'%Y-%m-%d'),r.detalle_disponibilidad,DATE_FORMAT(r.fecha_disponiblidad,'%Y-%m-%d'),"
                + "	       r.detalle_descarga,DATE_FORMAT(r.fecha_descarga,'%Y-%m-%d'),r.proveedor,DATE_FORMAT(r.fecha_etg_prov,'%Y-%m-%d'),a.nombre,r.cantidad1,r.unidad,r.orden_compra,r.referenciag,r.proveedor,a.correo,r.fecha_entrega,r.id_area"
                + " 	FROM requisicion_material r"
                + " 	INNER JOIN area a ON r.id_area = a.id_area"
                + " 	INNER JOIN clasificacion c ON r.clasificacion = c.id_clasificacion"
                + " 	WHERE r.id_requisicion IN (" + irs + ")";
        try {
            Query q = etm.createNativeQuery(query);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List consultarCorreoMasivoMTF(String irs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        String query = "SELECT r.id_requisicion,r.elemento,r.marca,r.cantidad,r.destino,DATE_FORMAT(r.fecha_solictud,'%Y-%m-%d') as fecha_solicitud,DATE_FORMAT(r.fecha_estimada,'%Y-%m-%d') as fecha_estimada ,c.nombre,"
                + "	       r.usuario_registro,r.editor_req,r.estado,IF(r.prioridad = 0,'NORMAL','ALTA') as prioirdad,r.detalle_cotizacion,DATE_FORMAT(r.fecha_cotizacion,'%Y-%m-%d'),r.dellate_proc_compra,DATE_FORMAT(r.fecha_procesocompra,'%Y-%m-%d'),"
                + "	       r.dellate_ordecompra,DATE_FORMAT(r.fecha_ordencompra,'%Y-%m-%d'),r.detalle_disponibilidad,DATE_FORMAT(r.fecha_disponiblidad,'%Y-%m-%d'),"
                + "	       r.detalle_descarga,DATE_FORMAT(r.fecha_descarga,'%Y-%m-%d'),r.proveedor,DATE_FORMAT(r.fecha_etg_prov,'%Y-%m-%d'),a.nombre,r.cantidad1,r.unidad,r.orden_compra,r.referenciag,r.proveedor,a.correo,r.fecha_entrega,r.id_area"
                + " 	FROM requisicion_material r"
                + " 	INNER JOIN area a ON r.id_area = a.id_area"
                + " 	INNER JOIN clasificacion c ON r.clasificacion = c.id_clasificacion"
                + " 	WHERE r.id_requisicion IN (" + irs + ") AND r.id_area = 4";
        try {
            Query q = etm.createNativeQuery(query);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List consultarRproveedor(int ipr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion_prov`('" + ipr + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List consultarRproveedorS(int idr, int est) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion_provS`('" + idr + "','" + est + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List consultarUnidad() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_und_t_unidad`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List rangoFechas(String fecha_i, String fecha_f, int estado) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_fechas`('" + fecha_i + "','" + fecha_f + "','" + estado + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List rangoFechaGeneralS(String fecha_i, String fecha_f) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_rango_f_s`('" + fecha_i + "','" + fecha_f + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List rangoFechaGeneral(String fecha_i, String fecha_f, int id_area) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_rango_f`('" + fecha_i + "','" + fecha_f + "','" + id_area + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List ConsultaRequsicionId(int idr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_c_requisicion`('" + idr + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List rangoFechaGeneralAreaEstado(String fecha_i, String fecha_f, int est, int prd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_rango_f_e`('" + fecha_i + "','" + fecha_f + "','" + est + "','" + prd + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List rangoFechaGeneralAreaEstadoPrioridad(String fecha_i, String fecha_f, int est, int prd, int iara) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_rango_f_area`('" + fecha_i + "','" + fecha_f + "','" + est + "','" + prd + "','" + iara + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List rangoFechasArea(String fecha_i, String fecha_f, int estado, int id_area) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_fechas_area`('" + fecha_i + "','" + fecha_f + "','" + estado + "','" + id_area + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List rangoFechaGeneralPrioridad(String fecha_i, String fecha_f, int id_area, int prd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_rango_fecha_prdad`('" + fecha_i + "','" + fecha_f + "','" + id_area + "','" + prd + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List TraerFechas() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_traer_fechas`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List rangoFechasConsultaGeneral(String fecha_i, String fecha_f) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String Cons = "SELECT r.id_requisicion,DATE_FORMAT(r.fecha_solictud,'%Y-%m-%d') as fecha_solictud,r.elemento,r.cantidad,c.nombre as Clasificacion,r.unidad,r.marca, "
                    + "r.destino,DATE_FORMAT(r.fecha_estimada,'%Y-%m-%d') as fecha_estimada,r.prioridad,r.estado,r.detalle_cotizacion,r.responsable_c,r.dellate_ordecompra, "
                    + "r.responsable_o,r.detalle_disponibilidad,r.responsable_d,r.proveedor, DATE_FORMAT(r.fecha_llegada,'%Y-%m-%d') as fecha_llegada,"
                    + "r.detalle_descarga,r.responsable_e,r.justificacion,r.usuario_registro,r.fecha_registro,r.id_area,a.siglatura, DATE_FORMAT(r.fecha_cotizacion,'%Y-%m-%d') as fecha_cotizacion, "
                    + "DATE_FORMAT(r.fecha_ordencompra,'%Y-%m-%d') as fecha_ordencompra, DATE_FORMAT(r.fecha_disponiblidad,'%Y-%m-%d') as  fecha_disponiblidad,DATE_FORMAT(r.fecha_descarga,'%Y-%m-%d') as fecha_descarga, "
                    + "DATE_FORMAT(r.fecha_etg_prov,'%Y-%m-%d') as fecha_etg_prov,r.editor_req, r.cantidad1,r.proyecto, r.referenciap, r.referenciag, r.centro_costo, r.responsable_r, "
                    + "DATE_FORMAT(r.fecha_entrega,'%Y-%m-%d') as fecha_entrega,r.orden_compra,r.e_cotizado,r.obs_reporte,r.importacion, "
                    + "r.dellate_proc_compra,r.responsable_pc,r.fecha_procesocompra "
                    + "FROM requisicion_material r "
                    + "INNER JOIN clasificacion c ON r.clasificacion=c.id_clasificacion "
                    + "INNER JOIN area a on r.id_area = a.id_area "
                    + "LEFT JOIN proveedor p ON r.proveedor=p.idproveedor "
                    + "WHERE (r.fecha_solictud BETWEEN if(length('" + fecha_i + "') < 11, CONCAT('" + fecha_i + "',' ','00:00:01'), " + fecha_i + ") AND if(length('" + fecha_f + "') < 11, CONCAT('" + fecha_f + "',' ','23:59:59'), '" + fecha_f + "') "
                    + "AND r.fecha_estimada BETWEEN if(length('" + fecha_i + "') < 11,CONCAT('" + fecha_i + "',' ','00:00:01'), " + fecha_i + ") AND if(length('" + fecha_f + "') < 11, CONCAT('" + fecha_f + "',' ','23:59:59'), '" + fecha_f + "')) "
                    + "order by r.id_requisicion desc";
            Query q = etm.createNativeQuery(Cons);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List rangoFechasAreaEstado(String fecha_i, String fecha_f, int estado) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_fechas_etd`('" + fecha_i + "','" + fecha_f + "','" + estado + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public boolean estadoRequisicion(int irs, int est, String jdc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_m_estado`('" + irs + "', '" + est + "','" + jdc + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarRequisicion(int irs, String fed, String emt, double ctd, String mrc, String dtn, int icc, String und, int prd, String ere, String rep, String reg, String cec, String det, String pro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_m_requisicion`('" + irs + "','" + fed + "', '" + emt + "', '" + ctd + "', '" + mrc + "', '" + dtn + "','"
                    + icc + "','" + und + "','" + prd + "','" + ere + "','" + rep + "','" + reg + "','" + cec + "','" + det + "','" + pro + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean AprobarCotizacion(int irs, int etd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_m_aprobar_requisicion`('" + irs + "','" + etd + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ModificarCantidades(int idr, String fll, Double can, String obs, String nom) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_m_cantidades_msv`('" + idr + "','" + fll + "','" + can + "','" + obs + "','" + nom + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean DetalleCotizacion(int id, String valor, String detalle, String nombre, String fecha, int etd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String condicion = "";
            String condicion2 = "";
            String condicion_u = "";
            String condicion_f = "";
            condicion = "referenciag= '" + valor + "'";
            condicion2 = "detalle_cotizacion = '" + detalle + "'";
            condicion_u = "responsable_c = '" + nombre + "'";
            condicion_f = "fecha_cotizacion=CONCAT('" + fecha + "',' ',TIME(NOW()))";
            Query q = etm.createNativeQuery("update requisicion_material set " + condicion + "," + condicion2 + " , " + condicion_u + "," + condicion_f + " where id_requisicion =" + id + " ");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean DetalleDE(int id, String detalle, String entrega, String nombre) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String condicion = "";
            String condicion_u = "";
            String condicion_f = "";
            String condicion_e = "";
            condicion = "detalle_descarga ='" + detalle + "'";
            condicion_f = "fecha_descarga=NOW()";
            condicion_u = "responsable_e ='" + nombre + "'";
            condicion_e = "responsable_r='" + entrega + "'";
            Query q = etm.createNativeQuery("update requisicion_material set " + condicion + " , " + condicion_f + " , " + condicion_u + " , " + condicion_e + " where id_requisicion =" + id + " ");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ElementoCotizado(String id) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_u_elementos_cotizados`('" + id + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean DetalleProcesoCompra(int id, String detalle, String nombre, String FechaC) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_u_detalle_proceso_compra`('" + id + "', '" + detalle + "','" + nombre + "','" + FechaC + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean DetalleOrdenCompra(int id, String detalle, String nombre, String FechaO, String fecha_prov, String proveedor, String Ocompra, int importacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_detalle_ordencompra`('" + id + "', '" + detalle + "','" + nombre + "','" + FechaO + "','" + proveedor + "','" + fecha_prov + "','" + Ocompra + "','" + importacion + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean DetalleGenerado(int id, String fecha, String detalle, Double cantidad, String nombre) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_detalle_generados`('" + id + "', '" + fecha + "', '" + detalle + "','" + cantidad + "','" + nombre + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public boolean DetalleDisponibilidad(int id, String detalle, String entrega, String nombre) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_detalle_disponibilidad`('" + id + "', '" + detalle + "','" + entrega + "','" + nombre + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public List Log_Requisicion(int irs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_requisicion_log`('" + irs + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Retorno_Log_Requisicion(int irs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_t_retorno_log`('" + irs + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean registrarLogRequisicion(int irs, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_r_log_requisicion`('" + irs + "','" + urg + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarDetalle(int irs, String dtc, String doc, String ddb, String ddc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_m_detalle`('" + irs + "','" + dtc + "', '" + doc + "', '" + ddb + "', '" + ddc + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean liberarRequisicion(int irs, int etd, String rps) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_m_liberar_requisicion`('" + irs + "', '" + etd + "','" + rps + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List ReporteRequisicion() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_c_reporte_oc`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ReporteRequisicionDiasVencidos(int DiasVencidos) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_c_reporte_oc_dias`(" + DiasVencidos + ")");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List consultaRequisicionesFiltro(String query) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("" + query + "");
            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (!resultados.isEmpty()) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean RegistrarObservacion(int irq, String orp) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rmt_a_observacion`('" + irq + "','" + orp + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
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
