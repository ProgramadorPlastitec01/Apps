package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RolloJpaController {

    public RolloJpaController() {
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
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_rollos_registro`('" + irg + "')");
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

    public List Consultar_ControlesEspesor_idRoll(int idRll) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cep_t_control_espesor`('" + idRll + "')");
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

    public List Traer_rollos_id_producto(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_rollo_producto`('" + ipd + "')");
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
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_rollo_lotes`('" + lpd + "','" + ltc + "','" + ltp + "')");
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
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_rollo_lotes_estadistico`('" + lpd + "','" + ltc + "','" + ltp + "')");
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
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_rollo_lotes_todos_p`('" + lpd + "','" + ltc + "')");
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
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_rollo_lotes_todos_p_estadistico`('" + lpd + "','" + ltc + "')");
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

    public List Traer_rollos_id_registro_filtro(int irg, String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_rollos_registro_filtro`('" + irg + "','" + fto + "')");
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

    public List Traer_rollo_id_registro(int irg, int nrl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_rollo_id_registro`('" + irg + "','" + nrl + "')");
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

    public List Traer_rollo_id_producto(int ipd, int nrl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_rollo_id_producto`('" + ipd + "','" + nrl + "')");
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

    public List Traer_ultimo_rollo(int ipd, int idReg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_ultimo_rollo`('" + ipd + "', '" + idReg + "')");
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

    public List proximo_rollo_idregistro(int idReg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_proximo_rollo`('" + idReg + "')");
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

    public List Resumen_factor_medida(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_t_resumen_factor_medida`('" + ipd + "')");
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

    public boolean Registrar_rollo(int irg, int nmr, double pdi, double pdc, double pdf, double pscmin, double pscmax, double amg, double abb, String pbt, String ptc, String upr, double prm1, double prm2, String nrf) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_r_rollo`('" + irg + "','" + nmr + "','" + pdi + "','" + pdc + "','" + pdf + "','" + pscmin + "','" + pscmax + "','" + amg + "','" + abb + "','" + pbt + "','" + ptc + "','" + upr + "','" + prm1 + "','" + prm2 + "','" + nrf + "')");
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

    public boolean Modificar_rollo(int irg, int nmr, double pdi, double pdc, double pdf, double pscmin, double pscmax, double amg, double abb, String pbt, String ptc, String upr, double prm1, double prm2, String nrf) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_m_rollo`('" + irg + "','" + nmr + "','" + pdi + "','" + pdc + "','" + pdf + "','" + pscmin + "','" + pscmax + "','" + amg + "','" + abb + "','" + pbt + "','" + ptc + "','" + upr + "','" + prm1 + "','" + prm2 + "','" + nrf + "')");
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

    public boolean Perimetros_rollo(int irl, double pr1, double pr2) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE rollo SET perimetro_calidad_1 = " + pr1 + ",perimetro_calidad_2 = " + pr2 + " WHERE id_rollo = " + irl + "");
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

    public boolean Americio_rollo(int irl, double mdg, double sep, String tso, String osv, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE rollo SET micrometro_digital = " + mdg + ",sensor_espesor = " + sep + ",tension = '" + tso + "',observaciones = '" + osv + "',usuario_calidad_sensor = '" + urg + "',fecha_registro_sensor = now() WHERE id_rollo = " + irl + "");
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

    public boolean Bajar_rollo(int irg, int nrl, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("INSERT INTO rollo(id_registro,numero,estado_calidad,usuario_calidad_rollo) VALUES(" + irg + "," + nrl + ",'P','" + urg + "')");
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

    public boolean Cambiar_estado_calidad(int irl, String ecl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE rollo SET estado_calidad = '" + ecl + "' WHERE id_rollo = " + irl + "");
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

    public boolean Eliminar_rollo_pendiente(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("DELETE r.* FROM rollo r WHERE id_rollo IN (SELECT id_rollo FROM(SELECT id_rollo FROM rollo WHERE id_registro = " + irg + " AND estado_calidad = 'P' ORDER BY id_rollo DESC LIMIT 1)x);");
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

    public boolean Curvatura_rollo(int irl, double curv) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE rollo SET curvatura = " + curv + " WHERE id_rollo = " + irl + "");
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

    //DATOS_ESTADISTICOS
    public List Generacion_estadistica(String nod, int ipd, String lpd, int iln, String fin, String ffn, int rin, int rfn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_generacion_estadistico`('" + nod + "'," + ipd + ",'" + lpd + "'," + iln + ",'" + fin.replace("/", "-") + "','" + ffn.replace("/", "-") + "'," + rin + "," + rfn + ")");
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

    public List Generacion_estadistica_resumido(String nod, int ipd, String lpd, int iln, String fin, String ffn, int rin, int rfn, int rsm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_generacion_estadistico_resumido`('" + nod + "'," + ipd + ",'" + lpd + "'," + iln + ",'" + fin.replace("/", "-") + "','" + ffn.replace("/", "-") + "'," + rin + "," + rfn + "," + rsm + ")");
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

    public List Generacion_estadistica_resultado(String nod, int ipd, String lpd, int iln, String fin, String ffn, int rin, int rfn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_generacion_estadistico_resultado`('" + nod + "'," + ipd + ",'" + lpd + "'," + iln + ",'" + fin.replace("/", "-") + "','" + ffn.replace("/", "-") + "'," + rin + "," + rfn + ")");
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

    public List Generacion_estadistica_resultado_resumido(String nod, int ipd, String lpd, int iln, String fin, String ffn, int rin, int rfn, int rsm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rlo_generacion_estadistico_resultado_resumido`('" + nod + "'," + ipd + ",'" + lpd + "'," + iln + ",'" + fin.replace("/", "-") + "','" + ffn.replace("/", "-") + "'," + rin + "," + rfn + "," + rsm + ")");
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

//    public List Generacion_estadistica_lista_PD(int nod, int ipd, String lpd, int iln, String fin, String ffn, int rin, int rfn) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_rlo_generacion_estadistico_PD`(" + nod + "," + ipd + ",'" + lpd + "'," + iln + ",'" + fin.replace("/", "-") + "','" + ffn.replace("/", "-") + "'," + rin + "," + rfn + ")");
//            List consulta = q.getResultList();
//            etm.getTransaction().commit();
//            etm.clear();
//            etm.close();
//            if (consulta.isEmpty()) {
//                return null;
//            } else {
//                return consulta;
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public List Generacion_estadistica_lista_PD_resumido(int nod, int ipd, String lpd, int iln, String fin, String ffn, int rin, int rfn, int rsm) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_rlo_generacion_estadistico_PD_resumido`(" + nod + "," + ipd + ",'" + lpd + "'," + iln + ",'" + fin.replace("/", "-") + "','" + ffn.replace("/", "-") + "'," + rin + "," + rfn + "," + rsm + ")");
//            List consulta = q.getResultList();
//            etm.getTransaction().commit();
//            etm.clear();
//            etm.close();
//            if (consulta.isEmpty()) {
//                return null;
//            } else {
//                return consulta;
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public List Generacion_estadistica_lista_PS(int nod, int ipd, String lpd, int iln, String fin, String ffn, int rin, int rfn) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_rlo_generacion_estadistico_PS`(" + nod + "," + ipd + ",'" + lpd + "'," + iln + ",'" + fin.replace("/", "-") + "','" + ffn.replace("/", "-") + "'," + rin + "," + rfn + ")");
//            List consulta = q.getResultList();
//            etm.getTransaction().commit();
//            etm.clear();
//            etm.close();
//            if (consulta.isEmpty()) {
//                return null;
//            } else {
//                return consulta;
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public List Generacion_estadistica_lista_PS_resumido(int nod, int ipd, String lpd, int iln, String fin, String ffn, int rin, int rfn, int rsm) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_rlo_generacion_estadistico_PS_resumido`(" + nod + "," + ipd + ",'" + lpd + "'," + iln + ",'" + fin.replace("/", "-") + "','" + ffn.replace("/", "-") + "'," + rin + "," + rfn + "," + rsm + ")");
//            List consulta = q.getResultList();
//            etm.getTransaction().commit();
//            etm.clear();
//            etm.close();
//            if (consulta.isEmpty()) {
//                return null;
//            } else {
//                return consulta;
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//    }
    public boolean Resumir_rollo(int irl, int rsm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE rollo SET resumido = " + rsm + " WHERE id_rollo = " + irl + "");
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
