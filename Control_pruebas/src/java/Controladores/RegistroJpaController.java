package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RegistroJpaController {

    public RegistroJpaController() {
        emf = Persistence.createEntityManagerFactory("Control_pruebasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Registros() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_c_registros`()");
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
    public List Registros_fecha_ano(String fec_ano) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_c_registros_ano`('"+fec_ano+"')");
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

    public List Registros_filtro(String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registros_filtro`('" + fto + "')");
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

    public List Registros_filtro_anos() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select DATE_FORMAT(r.fecha_registro,'%Y'),count(r.id_registro) from registro r group by DATE_FORMAT(r.fecha_registro,'%Y') desc;");
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
    
    
        public List Traer_filtro_ano(String f_ano) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_c_registros_año`('"+f_ano+"')");
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
    
    
    

    public List Traer_registro_id(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_t_registro_id`(" + irg + ")");
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

    public boolean Ingresar_registro(String fch, String tno, String rps, String osv, String urg, int ipm, String pcs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_r_turno`('" + fch + "','" + tno + "','" + rps + "','" + osv + "','" + urg + "','" + ipm + "','" + pcs + "')");
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

    public boolean Modificar_registro(int irg, String fch, String tno, String osv, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_m_turno`(" + irg + ",'" + fch + "','" + tno + "','" + osv + "','" + urg + "')");
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

    public boolean Firmar_registro(int irg, int etd, String usa) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query_1 = "UPDATE registro SET entrega='" + usa + "' WHERE id_registro=" + irg + "";
            String query_2 = "UPDATE registro SET recibe='" + usa + "' WHERE id_registro=" + irg + "";
            Query q = etm.createNativeQuery((etd == 0) ? query_1 : query_2);
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

    /**
     * Agrega "Rol/Nombre" a registro.responsable de un turno abierto, si no
     * esta ya en la lista. Conserva fecha_registro (la columna tiene ON UPDATE).
     */
    public boolean Agregar_responsable(int irg, String rps) {
        EntityManager etm = getEntityManager();
        try {
            etm.getTransaction().begin();
            Query q = etm.createNativeQuery("UPDATE registro "
                    + "SET responsable = CONCAT(responsable, ?), fecha_registro = fecha_registro "
                    + "WHERE id_registro = ? AND estado = 1 "
                    + "AND CONCAT(',', REPLACE(responsable, ', ', ','), ',') NOT LIKE CONCAT('%,', ?, ',%')");
            q.setParameter(1, Metodos.Control_turno.SEPARADOR + rps);
            q.setParameter(2, irg);
            q.setParameter(3, rps);
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            return exitoso > 0;
        } catch (Exception ex) {
            if (etm.getTransaction().isActive()) {
                etm.getTransaction().rollback();
            }
            return false;
        } finally {
            etm.clear();
            etm.close();
        }
    }

    public boolean Estado_registro(int irg, int etd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE registro SET estado=" + etd + " WHERE id_registro=" + irg + "");
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

    public boolean Cerrar_registro_pruebas_pendiente(String pcs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            //Query q = etm.createNativeQuery("UPDATE registro SET estado=0 WHERE id_registro=(select max(rd.id_registro) from registro_detalle rd) AND proceso=(select r.proceso from registro_detalle rd inner join registro r on rd.id_registro = r.id_registro limit 1)");
            Query q = etm.createNativeQuery("UPDATE registro SET estado=0 WHERE id_registro=(select max(rd.id_registro) from registro_detalle rd) AND proceso='" + pcs + "'");
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
