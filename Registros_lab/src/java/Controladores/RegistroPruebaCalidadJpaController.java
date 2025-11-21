package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RegistroPruebaCalidadJpaController {

    public RegistroPruebaCalidadJpaController() {
        emf = Persistence.createEntityManagerFactory("RegistrosLaboratorioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Parametros_registro_prueba_calidad(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpc_t_parametros_registro`('" + irg + "')");
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
    public List Parametros_registro_prueba_calidad_pmtt(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpc_t_parametros_registro_plumatt`('" + irg + "')");
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

    public List Parametros_tomas_registro_prueba_calidad(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpc_c_parametro_tomas_registro`('" + irg + "')");
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

    public boolean Registrar_verificacion_prueba_calidad(int irg, int ipr, String vlr, int fce, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE `registro_prueba_calidad` SET `toma" + fce + "`='" + vlr + "',`usuario_toma" + fce + "`='" + urg + "' WHERE `id_registro`='" + irg + "' AND `id_parametro`='" + ipr + "';");
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

    public boolean Registrar_verificacion_prueba_calidad(int irg, int ipr, int fce) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpc_r_prueba_calidad`('" + irg + "','" + ipr + "','" + fce + "')");
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

    public List Registros_lote(int nmr, int ipd, String prm, String lte, int iln,String cet, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpc_t_registros_lote`('" + nmr + "','" + ipd + "','" + prm + "','" + lte + "','" + iln + "','" + cet + "','" + fin + "','" + ffn + "')");
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
    public List Registros_lote_resumidos(int nmr, int ipd, String prm, String lte, int iln,String cet, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpc_t_registros_lote_resumido`('" + nmr + "','" + ipd + "','" + prm + "','" + lte + "','" + iln + "','" + cet + "','" + fin + "','" + ffn + "')");
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
//    public List Registros_lote_resumidos(int nmr, int ipd, String prm, String lte, int iln, String fin, String ffn) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_rpc_t_registros_lote_resumido`('" + nmr + "','" + ipd + "','" + prm + "','" + lte + "','" + iln + "','" + fin + "','" + ffn + "')");
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

//    public List Registros_lote_mas(int nmr, int ipd, String prm, String prm2, String lte, int iln, String fin, String ffn) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            //String CON = "SELECT rpc.id_registro,r.fecha_turno,rpc.frecuencia,rpc.toma1,rpc.toma2,rpc.toma3,rpc.toma4,rpc.toma5,rpc.toma6,rpc.toma7,rpc.toma8 FROM registro_prueba_calidad rpc INNER JOIN parametro p on rpc.id_parametro = p.id_parametro INNER JOIN registro r on r.id_registro = rpc.id_registro INNER JOIN producto pd on r.id_producto = pd.id_producto INNER JOIN orden_produccion o on o.id_orden_produccion = pd.id_orden_produccion WHERE (o.numero = " + nmr + " and pd.id_producto = " + ipd + ") AND (p.nombre LIKE '%" + prm + "%' OR p.nombre LIKE '%" + prm2 + "%') AND (r.lote_producto = '" + lte + "' AND r.id_linea = " + iln + " and r.estado = 0) AND (CONCAT(r.fecha_turno ,' ',DATE_FORMAT(r.fecha_registro,'%H:%i:00')) BETWEEN '" + fin.replace("/", "-") + "' AND '" + ffn.replace("/", "-") + "')";
//            Query q = etm.createNativeQuery("SELECT rpc.id_registro,r.fecha_turno,rpc.frecuencia,GROUP_CONCAT(IFNULL(rpc.toma1,0),',',IFNULL(rpc.toma2,0),',',IFNULL(rpc.toma3,0),',',IFNULL(rpc.toma4,0),',',IFNULL(rpc.toma5,0),',',IFNULL(rpc.toma6,0),',',IFNULL(rpc.toma7,0),',',IFNULL(rpc.toma8,0)) FROM registro_prueba_calidad rpc INNER JOIN parametro p on rpc.id_parametro = p.id_parametro INNER JOIN registro r on r.id_registro = rpc.id_registro INNER JOIN producto pd on r.id_producto = pd.id_producto INNER JOIN orden_produccion o on o.id_orden_produccion = pd.id_orden_produccion WHERE (o.numero = " + nmr + " and pd.id_producto = " + ipd + ") AND (p.nombre LIKE '%" + prm + "%' OR p.nombre LIKE '%" + prm2 + "%') AND (r.lote_producto = '" + lte + "' AND r.id_linea = " + iln + " and r.estado = 0) AND (CONCAT(r.fecha_turno ,' ',DATE_FORMAT(r.fecha_registro,'%H:%i:00')) BETWEEN '" + fin.replace("/", "-") + "' AND '" + ffn.replace("/", "-") + "')");
//            //Query q = etm.createNativeQuery("SELECT rpc.id_registro,r.fecha_turno,rpc.frecuencia,rpc.toma1,rpc.toma2,rpc.toma3,rpc.toma4,rpc.toma5,rpc.toma6,rpc.toma7,rpc.toma8 FROM registro_prueba_calidad rpc INNER JOIN parametro p on rpc.id_parametro = p.id_parametro INNER JOIN registro r on r.id_registro = rpc.id_registro INNER JOIN producto pd on r.id_producto = pd.id_producto INNER JOIN orden_produccion o on o.id_orden_produccion = pd.id_orden_produccion WHERE (o.numero = " + nmr + " and pd.id_producto = " + ipd + ") AND (p.nombre LIKE '%" + prm + "%' OR p.nombre LIKE '%" + prm2 + "%') AND (r.lote_producto = '" + lte + "' AND r.id_linea = " + iln + " and r.estado = 0) AND (CONCAT(r.fecha_turno ,' ',DATE_FORMAT(r.fecha_registro,'%H:%i:00')) BETWEEN '" + fin.replace("/", "-") + "' AND '" + ffn.replace("/", "-") + "')");
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

    public boolean Eliminar_prueba_calidad_registro(int irg, int ipr, int fce) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE registro_prueba_calidad SET toma" + fce + " = NULL , usuario_toma" + fce + " = NULL WHERE id_registro = " + irg + " AND id_parametro = " + ipr + "");
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
