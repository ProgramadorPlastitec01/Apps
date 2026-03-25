package Controladores_BD;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class MenuJpaController {

    public MenuJpaController() {
        emf = Persistence.createEntityManagerFactory("SIRHPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    //<editor-fold defaultstate="collapsed" desc="MENU">

    public List Menu_usuario(int ius) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mno_t_menu`('" + ius + "')");
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

    public List Opciones_usuario(int imn, int ius) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mno_t_opciones`('" + imn + "','" + ius + "')");
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

    public List Opciones_usuario_id(int ioc, int ius) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mno_t_opcion_id`('" + ioc + "','" + ius + "')");
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

    public List Menu_todo() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mnu_c_menu`()");
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

    public List Opciones_todas(int imn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_oco_c_opciones`('" + imn + "')");
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

    public boolean Registrar_privilegios(int ioc, int ius) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mno_r_privilegios`('" + ioc + "','" + ius + "')");
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

    public boolean Eliminar_privilegios(int ius) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mno_e_permisos`('" + ius + "')");
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

    public boolean Registrar_privilegios_detallados(int ioc, int ius, String pms) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mno_m_privilegios_detallados`('" + ioc + "','" + ius + "','" + pms + "')");
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
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="FIRMAS">

//    public List Traer_firma(long dcm, int cdg) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_signature_documento_codigo`('" + dcm + "','" + cdg + "')");
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
//    public boolean Registrar_firma(long dcm, int cdg, String fma) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_signature_registrar_firma`('" + dcm + "','" + cdg + "','" + fma + "')");
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
//
//    public boolean Actualizar_firma(long dcm, int cdg, String fma) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_signature_actualizar_firma`('" + dcm + "','" + cdg + "','" + fma + "')");
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
//
//    public boolean Cambiar_estados_firma(long dcm, long cdg) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_signature_actualizar_estados`('" + dcm + "','" + cdg + "')");
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
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="REPORTES GENERALES">

    public List Informe_rotacion(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_informe_rotacion`(" + anio + "," + mes + ")");
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

    public List Vistas_sirh() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SHOW full TABLES where Table_type='VIEW'");
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

    public List Vistas_sirh_exe(String view) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT * FROM " + view + "");
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

    public List Informe_rotacion_detallado(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_informe_rotacion_detallado`(" + anio + "," + mes + ")");
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

    public List Informe_ausencias_agrupado(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_informe_ausencias_agrupado`(" + anio + "," + mes + ")");
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
    public List Informe_ausencias_agrupado_sst(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_informe_ausencias_agrupado_sst`(" + anio + "," + mes + ")");
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

    public List Informe_ausencias_detallado(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_informe_ausencias_detallado`(" + anio + "," + mes + ")");
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
    public List Informe_ausencias_detallado_sst(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_informe_ausencias_detallado_sst`(" + anio + "," + mes + ")");
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
    public List Empleados_anio_mes_sst(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mnu_c_empleados_anio_mes_sst`(" + anio + "," + mes + ")");
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

    
    public List Empleados_anio_mes(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            //Query q = etm.createNativeQuery("select count(p.documento),(count(p.documento)*240) from  personal p inner join personal_datos pd on p.documento = pd.documento where pd.fecha_ingreso <=  LAST_DAY('" + anio + "-" + ((mes <= 9) ? "0" + mes : mes) + "-01') and pd.estado = 1");
            //240 CANTIDAD //
            Query q = etm.createNativeQuery("select nt.numero,(nt.numero*240) from numero_trabajadores nt where nt.anio = " + anio + " and nt.mes = " + mes);
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

    public List Empleados_inicio_anio(int anio) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            //Query q = etm.createNativeQuery("select count(p.documento),now() from  personal p inner join personal_datos pd on p.documento = pd.documento where pd.fecha_ingreso <=  '" + anio + "-01-01' and pd.estado = 1");
            Query q = etm.createNativeQuery("select nt.numero,(nt.numero*240) from numero_trabajadores nt where nt.anio = " + anio + " and nt.mes = 1");
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

    public List Ingresos_anio_mes(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_informe_rotacion_contador`(" + anio + "," + mes + ")");
            //Query q = etm.createNativeQuery("select count(p.documento),now() from personal p inner join personal_datos pd on p.documento = pd.documento where pd.vigencia = 1 and (pd.fecha_ingreso between '" + anio + "-" + ((mes <= 9) ? "0" + mes : mes) + "-01' and LAST_DAY('" + anio + "-" + ((mes <= 9) ? "0" + mes : mes) + "-01'))");
            //Query q = etm.createNativeQuery("select count(p.documento),(count(p.documento)*240) from  personal p inner join personal_datos pd on p.documento = pd.documento where pd.fecha_ingreso =  '" + anio + "-" + ((mes <= 9) ? "0" + mes : mes) + "-01' and pd.estado = 1");
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

    public List Verificacion_registros_anio_mes(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_informe_verificacion_registros`(" + anio + "," + mes + ")");
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

    public List Informe_calificacion_competencias(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_informe_mc_competencias`(" + anio + "," + mes + ")");
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

    public List Informe_personal_calificado(int iar) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_informe_mc_calificacion_personal`('" + iar + "')");
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

    public List Informe_personal_calificado_correo(int iar) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_informe_mc_calificacion_personal_correo`('" + iar + "')");
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

    public boolean Registrar_actualizacion_salarios(String fch, String apn, String cct, String psn, String osl, String nsl, int etd, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_asl_r_actualizacion_salario`('" + fch + "','" + apn + "','" + cct + "','" + psn + "','" + osl + "','" + nsl + "','" + etd + "','" + urg + "')");
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

    public List Consulta_actualizacion_salarios() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_asl_c_actualizacion_salarios`()");
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

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="NUMERO DE TRABAJADORES">
    public List Numero_trabajadores_anio_mes(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ntb_c_numero_trabajadores_anio_mes`(" + anio + "," + mes + ")");
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

    public List Numero_trabajadores_anio(int anio) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ntb_c_numero_trabajadores_anio`(" + anio + ")");
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

    public List Numero_trabajadores() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ntb_c_numero_trabajadores`()");
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

    public List Numero_trabajadores_id(int intb) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ntb_c_numero_trabajadores_id`('" + intb + "')");
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

    public boolean Actualizar_numero_trabajadores_anio_mes(int anio, int mes, int num) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ntb_m_numero_trabajadores_anio_mes`('" + anio + "','" + mes + "','" + num + "')");
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

    public boolean Registrar_numero_trabajadores_anio_mes(int anio, int mes, int num, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ntb_r_numero_trabajadores_anio_mes`('" + anio + "','" + mes + "','" + num + "','" + urg + "')");
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

//</editor-fold>
}
