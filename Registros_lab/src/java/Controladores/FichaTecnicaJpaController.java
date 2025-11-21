package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class FichaTecnicaJpaController {

    public FichaTecnicaJpaController() {
        emf = Persistence.createEntityManagerFactory("RegistrosLaboratorioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Fichas_tecnicas() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_c_fichas`()");
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

    public List Fichas_tecnicas_codigo(String cdg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_t_fichas_codigo`('" + cdg + "')");
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

    public List Fichas_tecnicas_filtro(String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_t_fichas_filtro`('" + fto + "')");
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

    public boolean Registrar_ficha(String cdg, int vso, String pdb, String pdbmax, String pdbmin, String psc, String pscmax, String pscmin, String psb, String psbmax, String psbmin, String psbalt, String psbmaxalt, String psbminalt,
            String pscl, String psclmax, String psclmin, String psclalt, String psclmaxalt, String psclminalt, String lcs, String lcsmax, String lcsmin, String ddl, String ddlmax, String ddlmin, String dil, String dilmax, String dilmin,
            String didd, String diddmax, String diddmin, String dedd, String deddmax, String deddmin, String didi, String didimax, String didimin, String dedi, String dedimax, String dedimin,
            String amg, String amgmax, String amgmin, String urg, String pdt, String mtr, String osv,
            String dcl, String dclmax, String dclmin, String didc, String didcmax, String didcmin, String dedc, String dedcmax, String dedcmin, String avt, String avtmax, String avtmin,
            String dbce, String dbcemax, String dbcemin, String dbci, String dbcimax, String dbcimin, String fteva, String pse, String psemax, String psemin,
            String dtcx4, String dtcx4max, String dtcx4min, String dtcx5, String dtcx5max, String dtcx5min) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_r_ficha`('" + cdg + "','" + vso + "','" + pdb + "','" + pdbmax + "','" + pdbmin + "','" + psc + "'"
                    + ",'" + pscmax + "','" + pscmin + "','" + psb + "','" + psbmax + "','" + psbmin + "','" + psbalt + "','" + psbmaxalt + "','" + psbminalt + "','" + pscl + "','" + psclmax + "','" + psclmin + "','" + psclalt + "','" + psclmaxalt + "','" + psclminalt + "'"
                    + ",'" + lcs + "','" + lcsmax + "','" + lcsmin + "','" + ddl + "','" + ddlmax + "','" + ddlmin + "','" + dil + "','" + dilmax + "','" + dilmin + "'"
                    + ",'" + didd + "','" + diddmax + "','" + diddmin + "','" + dedd + "','" + deddmax + "','" + deddmin + "','" + didi + "','" + didimax + "','" + didimin + "'"
                    + ",'" + dedi + "','" + dedimax + "','" + dedimin + "','" + amg + "','" + amgmax + "','" + amgmin + "','" + urg + "','" + pdt + "','" + mtr + "','" + osv + "'"
                    + ",'" + dcl + "','" + dclmax + "','" + dclmin + "','" + didc + "','" + didcmax + "','" + didcmin + "','" + dedc + "','" + dedcmax + "','" + dedcmin + "','" + avt + "','" + avtmax + "','" + avtmin + "','" + dbci + "','" + dbcimax + "','" + dbcimin + "'"
                    + ",'" + dbce + "','" + dbcemax + "','" + dbcemin + "','" + fteva + "','" + pse + "','" + psemax + "','" + psemin + "','" + dtcx4 + "','" + dtcx4max + "','" + dtcx4min + "','" + dtcx5 + "','" + dtcx5max + "','" + dtcx5min + "')");
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

    public boolean Activar_ficha(int ift) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_m_activar`('" + ift + "')");
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

    public List Traer_ficha_registro(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_t_ficha_registro`('" + irg + "')");
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

    public List Traer_ficha_producto(int ipd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_t_fichas_producto`('" + ipd + "')");
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

    public List Traer_ficha_id(int ift) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_t_ficha_id`('" + ift + "')");
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

    public boolean Desactivar_ficha(int ftn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_m_desactivar`('" + ftn + "')");
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

    public boolean Desactivar_ficha_version_old(String cdg, int vrs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_m_version`('" + cdg + "','" + (vrs - 1) + "')");
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

    public boolean Actualizar_datos_x4x5(String cdg, int vrs, String dtcx4, String dtcx4max, String dtcx4min, String dtcx5, String dtcx5max, String dtcx5min) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ftn_m_parametros_x4x5`('" + cdg + "','" + vrs + "','" + dtcx4 + "','" + dtcx4max + "','" + dtcx4min + "','" + dtcx5 + "','" + dtcx5max + "','" + dtcx5min + "')");
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
