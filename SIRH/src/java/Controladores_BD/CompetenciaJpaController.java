package Controladores_BD;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CompetenciaJpaController implements Serializable {

    public CompetenciaJpaController() {
        emf = Persistence.createEntityManagerFactory("SIRHPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="GRUPOS">
    public List Consultar_grupos_competencias() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_gpo_c_grupos_competencias`()");
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
    //<editor-fold defaultstate="collapsed" desc="CARGO">

    public boolean Registrar_competencia_cargo(int icg, String cdg, int vso, String ttl, int fce, String pdr, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_cgo_r_plantilla_cargo`('" + icg + "','" + cdg + "','" + vso + "','" + ttl + "','" + fce + "','" + pdr + "','" + urg + "')");
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

    //public boolean Formatos_obsoletos(String cdg, int vso) {
    public boolean Formatos_obsoletos(int icg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update mc_cargo set estado = 0 where id_cargo = '" + icg + "'");
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

    public List Formato_existente(String cdg, int vso) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select * from mc_cargo where codigo = '" + cdg + "' and version = " + vso);
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

    public List Consultar_cargos_competencias() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_cgo_c_competencias_cargo`()");
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

    public List Consultar_cargos_especiales() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cgo_t_cargos_especiales`()");
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

    public List Consultar_cargos_especiales_mc() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cgo_t_cargos_especiales_mc`()");
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

    public List Filtro_personal_competencias(String nbe, String apl, long dcm, int iar, int icg, int iep) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "select p.documento,p.nombres,p.apellidos,p.codigo_firma,c.id_cargo,c.nombre,c.id_area,a.nombre,a.sigla,pd.fecha_ingreso,pd.estado,mc.id_mc_cargo,mc.codigo,mc.version,mc.titulo,ifnull(p.especialidad,'N/A') from  personal p inner join personal_datos pd on p.documento=pd.documento inner join cargo c on pd.id_cargo = c.id_cargo inner join area a on c.id_area = a.id_area inner join mc_cargo mc on c.id_cargo = mc.id_cargo where (mc.estado = 1 and pd.estado = 1 and pd.vigencia = 1)";
            if (nbe.length() > 0) {
                query = query + " and p.nombres like '%" + nbe + "%' ";
            }
            if (apl.length() > 0) {
                query = query + " and p.apellidos like '%" + apl + "%' ";
            }
            if (dcm > 0) {
                query = query + " and p.documento = '" + dcm + "' ";
            }
            if (iar == 5 || iar == 7) {

            } else if (iar > 0) {
                query = query + " and a.id_area= '" + iar + "' ";
            } 
            if (icg > 0) {
                query = query + " and c.id_cargo = '" + icg + "' ";
            }
            if (iep > 0) {
                query = query + " and p.especialidad like '%" + iep + "%' ";
            }
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
        } catch (Exception ex) {
            return null;
        }
    }

    public List Ultimo_registro_mc_cargo() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("Select max(id_mc_cargo),max(id_mc_cargo) from mc_cargo");
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

    public List Consultar_cargos_competencias_id(int imccg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_cgo_t_competencias_id_mc_cargo`(" + imccg + ")");
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

    public List Consultar_formatos_vigentes_cargo(int icg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_cgo_t_competencias_id_cargo`(" + icg + ")");
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

    public List Porcentajes_grupo_formato_cargo(int imccg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select d.id_mc_grupo,d.valor_grupo,count(d.id_mc_definicion) from mc_cargo c inner join mc_definicion d on c.id_mc_cargo = d.id_mc_cargo where c.id_mc_cargo = " + imccg + " group by d.id_mc_grupo,d.valor_grupo");
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

    public List Consultar_grupos_definicion_id_cargo(int imccg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select md.id_mc_grupo,mg.nombre,md.valor_grupo,count(md.id_mc_definicion)\n"
                    + "from mc_cargo mc inner join mc_definicion md on mc.id_mc_cargo = md.id_mc_cargo\n"
                    + "	inner join mc_grupo mg on md.id_mc_grupo = mg.id_mc_grupo\n"
                    + "where mc.id_mc_cargo = " + imccg + "\n"
                    + "GROUP BY md.id_mc_grupo,md.valor_grupo\n"
                    + "order by mg.pocision asc");
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

    public boolean Activar_mc_cargo(int icg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_cgo_m_activar`('" + icg + "')");
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

    public boolean Desactivar_mc_cargo(int icg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_cgo_m_desactivar`('" + icg + "')");
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
    //<editor-fold defaultstate="collapsed" desc="DEFINICIONES">

    public List Consultar_definicion_competencias_id_cargo(int imccg, int imcdf) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_dfn_formato_cargo`(" + imccg + "," + imcdf + ")");
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

    public boolean Registrar_deficniones_formato(int imccgo, int imcgpo, int vgp, String dfn, String cdt, int odn, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_dfn_r_registrar_definiciones`('" + imccgo + "','" + imcgpo + "','" + vgp + "','" + dfn + "','" + cdt + "','" + odn + "','" + urg + "')");
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
    //<editor-fold defaultstate="collapsed" desc="CALIFICACION">

    public List Consultar_calificacion_realizadas(String fpi, String fpf, int iar, int cps, String min, String max) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_clf_c_realizadas`('" + fpi + "','" + fpf + "','" + iar + "','" + cps + "','" + min + "','" + max + "')");
            //Query q = etm.createNativeQuery("CALL `sp_mc_clf_c_realizadas`('" + fpi + "','" + fpf + "','" + iar + "','" + cps + "')");
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

    public List Consultar_calificacion_realizada_id(int imcclf) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_clf_t_calificacion_id`(" + imcclf + ")");
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

    public List Consultar_calificacion_realizada_documento(long dcm, int anio_ini, int anio_fin) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_clf_t_calificacion_documento`('" + dcm + "','" + anio_ini + "','" + anio_fin + "')");
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

    public List Consultar_calificacion_realizada_documento_general(long dcm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_clf_t_calificacion_documento_general`('" + dcm + "')");
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

    public boolean Actualizar_calificacion_personal(int imcclf, String fch, String evl, String dcl, String gcl, double clf, String rcm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_clf_m_actualizar_calificacion`('" + imcclf + "','" + fch + "','" + evl + "','" + dcl + "','" + gcl + "','" + clf + "','" + rcm + "')");
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

    public boolean Activar_calificacion(int icl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_clf_m_activar`('" + icl + "')");
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

    public boolean Desactivar_calificacion(int icl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_clf_m_desactivar`('" + icl + "')");
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

    public boolean Registrar_calificacion(long dcm, int imccgo, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_clf_r_registrar`('" + dcm + "','" + imccgo + "','" + urg + "')");
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

    public boolean Eliminar_calificacion(int icl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_clf_e_calificacion`('" + icl + "')");
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

    public List Matriz_empleado(long dcm, int anio_ini, int anio_fin) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_clf_t_matriz_empleado`('" + dcm + "','" + anio_ini + "','" + anio_fin + "')");
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
    //<editor-fold defaultstate="collapsed" desc="RENDICION DE CUENTAS">
    //CONSULTAR FORMATO ACTUAL POR CODDIGO
    public List Consultar_sst_rendicion_codigo(String cdg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_sst_rdc_t_rendicion_codigo`('" + cdg + "')");
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

    public List Consultar_sst_rendicion_id(int imsr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_sst_rdc_t_rendicion_id`('" + imsr + "')");
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

    public List Consultar_sst_rendicion_definiciones_id(int imsr, int img) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_sst_definicion_rendicion_id`('" + imsr + "','" + img + "')");
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

    public List Consultar_sst_grupos_definicion_id_rendicion(int imsr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select md.id_mc_grupo,mg.nombre,md.valor_grupo,count(md.id_mc_sst_definicion)\n"
                    + "from mc_sst_rendicion mr inner join mc_sst_definicion md on mr.id_mc_sst_rendicion = md.id_mc_sst_rendicion\n"
                    + "	inner join mc_grupo mg on md.id_mc_grupo = mg.id_mc_grupo\n"
                    + "where mr.id_mc_sst_rendicion = " + imsr + "\n"
                    + "GROUP BY md.id_mc_grupo,md.valor_grupo\n"
                    + "order by mg.pocision asc");
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

    public boolean Registrar_mc_sst_calificacion(int imcc, int imcsr, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_sst_clf_r_calificacion`('" + imcc + "','" + imcsr + "','" + urg + "')");
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

    //VERIFICAR CALIFICACION DE RENDICION
    public List Consultar_sst_rendicion_codigo(int imcc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_sst_clf_t_calificacion_rendicion`('" + imcc + "')");
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

    public boolean Actualizar_calificacion_sst_personal(int imcclf, String dcl, String gcl, double clf) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_mc_sst_clf_m_actualizar_calificacion`('" + imcclf + "','" + dcl + "','" + gcl + "','" + clf + "')");
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
