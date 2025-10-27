package Controladoras;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Persistence;

public class RegistroJpaController implements Serializable {

    public RegistroJpaController() {
        emf = Persistence.createEntityManagerFactory("REDEACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaRegistros() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rgt_c_registros`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean registroDigitalizacion(String fechaD, String carpeta, String num_cap, String ruta, String fechaE, String usuario) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_dgt_r_digitalizacion`('" + fechaD + "','" + carpeta + "','" + num_cap + "','" + ruta + "','" + fechaE + "','" + usuario + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ModificarDigitalizacion(int id_digitalizacion, String fechaD, String carpeta, String num_cap, String ruta, String fechaE) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_dgt_m_digitalizacion`('" + id_digitalizacion + "','" + fechaD + "','" + carpeta + "','" + num_cap + "','" + ruta + "','" + fechaE + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ModificarEstadoDigitalizacion(int id_digitalizacion) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_dgt_m_estado_digitalizacion`('" + id_digitalizacion + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean firmarDigitalizacion(int id_digitalizacion, int id_usuario) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_dgt_a_firmar_digitalizacion`('" + id_digitalizacion + "','" + id_usuario + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List consultaDigitalizacion() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_dgt_t_digitalizacion`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaModificarDigitalizacion(int id_digitalizacion) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_dgt_c_digitalizacion__modificar`('" + id_digitalizacion + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaPlantilla(int id_acta) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_acta_c_consulta_plantilla`('" + id_acta + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaIdmodificar(int ira) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_acta_c_consulta_id_registro`('" + ira + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Registros() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT r.id_registro, concat(r.codigo, '  V- ', r.VERSION) as nombre, r.nombre, r.estado, r.plantilla, r.fch_registro "
                    + "FROM registro r "
                    + "where r.codigo LIKE '%R-TI%' "
                    + "ORDER BY r.id_registro");
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

    public List consultaFiltroDigitalizacion(String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_dgt_t_digitalizacion_filtro`('" + filtro + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Consultar_registros_id(int id_reg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reg_c_consultarRegistros_id`('" + id_reg + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultaFiltroFecha(String fechaI, String fechaF, String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_dgt_t_filtro_fecha`('" + fechaI + "','" + fechaF + "','" + filtro + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    // CONTOLADORES DE ACTAS 
    public List consultarActas() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_acta_c_consultar_actas`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultarPersonal(int codigo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_acta_c_personal_sirh`('" + codigo + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean registrarActa(int id_acta, String fecha, String asunto, String personal, String usr_registro) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_acta_r_registrar_actas`('" + id_acta + "','" + fecha + "','" + asunto + "','" + personal + "','" + usr_registro + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarActa(int id_acta, String fecha, String asunto) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_acta_m_modificar_acta`('" + id_acta + "','" + fecha + "','" + asunto + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarContenidoActa(int id_acta, String contenido) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_acta_m_contenido_acta`('" + id_acta + "','" + contenido + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Registrar_acta(int id_reg, String fech, String txt_asunto, String txt_contenido, String usu_reg, int id_rol) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_acta_r_registrarActa`('" + id_reg + "','" + fech + "','" + txt_asunto + "','" + txt_contenido + "','" + usu_reg + "','" + id_rol + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Modificar_personal_acta(int id_acta, String personal) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_acta_m_modificar_personalActa`('" + id_acta + "','" + personal + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Modificar_Contenido_acta(int id_acta, String txt_contActa) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_acta_m_modificarContenidoActa`('" + id_acta + "','" + txt_contActa + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Registrar_firmas_usuarios(int id_firma, String txt_firma) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_registrar_firmas_sirh`('" + id_firma + "','" + txt_firma + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Registrar_NuevaFirma_usuario(int txt_documento, int txt_cod, String txt_firma) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_registrar_nuevaFirma`('" + txt_documento + "','" + txt_cod + "','" + txt_firma + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List consultarActaId(int id_acta) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_acta_c_acta_por_id`('" + id_acta + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List consultarRegistros_id(int id_reg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT r.id_registro, r.codigo, r.VERSION, r.nombre, r.estado, r.plantilla, r.fch_registro\n"
                    + "\n"
                    + "FROM registro r\n"
                    + "\n"
                    + "where r.codigo LIKE '%R-SI%'\n"
                    + "\n"
                    + "ORDER BY r.id_registro");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List FiltroActa(String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_acta_f_filtro_general`('" + filtro + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Consultar_Personal_Acta(int id_act) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_act_c_consultar_personalActas`('" + id_act + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

//    public List Consulta_contadores_mes(int anio, int mes) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            String query = "SELECT ((SELECT COUNT(a.id_actividad_reportada) \n"
//                    + "                    FROM actividad_reportada a \n"
//                    + "                    WHERE YEAR(a.fecha_terminacion) = " + anio + " \n"
//                    + "                    AND MONTH(a.fecha_terminacion) = " + mes + ") + \n"
//                    + "                    (SELECT COUNT(c.id_caso)FROM caso c \n"
//                    + "                     WHERE YEAR(c.fecha_solucion) = " + anio + " AND MONTH(c.fecha_solucion) = " + mes + ") + \n"
//                    + "							(SELECT COUNT(d.id_actividad) FROM actividades_diarias d \n"
//                    + "							WHERE YEAR(d.fecha_Hora_ejecucion) = " + anio + " AND MONTH (d.fecha_Hora_ejecucion) = " + mes + ")) AS 'ID',\n"
//                    + "                     ((SELECT SUM(a.Tiempo_Parada)\n"
//                    + "                    FROM actividad_reportada a \n"
//                    + "                    WHERE YEAR(a.fecha_terminacion) = " + anio + " \n"
//                    + "                    AND MONTH(a.fecha_terminacion) = " + mes + ") + \n"
//                    + "                    (SELECT  SUM(c.parada_equipo) FROM caso c \n"
//                    + "                     WHERE YEAR(c.fecha_envio) = " + anio + " AND MONTH(c.fecha_envio) = " + mes + ") + \n"
//                    + "							(SELECT SUM(d.parada_pc) FROM actividades_diarias d \n"
//                    + "							WHERE YEAR(d.fecha_Hora_ejecucion) = " + anio + " AND MONTH(d.fecha_Hora_ejecucion) = " + mes + ")) AS 'Parada Equipo',\n"
//                    + "                      ((SELECT SUM(a.Parada_Produccion)\n"
//                    + "                    FROM actividad_reportada a \n"
//                    + "                    WHERE YEAR(a.fecha_terminacion) = " + anio + " \n"
//                    + "                    AND MONTH(a.fecha_terminacion) = " + mes + ") + \n"
//                    + "                    (SELECT  SUM(c.parada_produccion) FROM caso c \n"
//                    + "                     WHERE YEAR(c.fecha_envio) = " + anio + " AND MONTH(c.fecha_envio) = " + mes + ") + \n"
//                    + "							(SELECT SUM(d.parada_Produccion) FROM actividades_diarias d \n"
//                    + "							WHERE YEAR(d.fecha_Hora_ejecucion) = " + anio + " AND MONTH(d.fecha_Hora_ejecucion) = " + mes + ")) AS 'Parada Produccion'";
//            Query q = etm.createNativeQuery(query);
//            List consulta = q.getResultList();
//            etm.getTransaction().commit();
//            etm.clear();
//            etm.close();
//            if (!consulta.isEmpty()) {
//                return consulta;
//            } else {
//                return null;
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//    }
    public List Consulta_contadores_mes(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "SELECT ((SELECT COUNT(a.id_actividad_reportada)\n"
                    + "FROM actividad_reportada a \n"
                    + "WHERE YEAR(a.fecha_terminacion) = "+ anio +" AND MONTH(a.fecha_terminacion) = "+ mes +") + \n"
                    + "(SELECT COUNT(c.id_caso)FROM caso c \n"
                    + "WHERE YEAR(c.fecha_solucion) = "+ anio +" AND MONTH(c.fecha_solucion) = "+ mes +") + \n"
                    + "(SELECT COUNT(d.id_actividad) FROM actividades_diarias d \n"
                    + "WHERE YEAR(d.fecha_Hora_ejecucion) = "+ anio +" AND MONTH (d.fecha_Hora_ejecucion) = "+ mes +")) AS 'ID',\n"
                    
                    
                    
                    + "((SELECT if(a.Tiempo_Parada IS NULL, 0 ,SUM(a.Tiempo_Parada))\n"
                    + "FROM actividad_reportada a \n"
                    + "WHERE YEAR(a.fecha_terminacion) = "+ anio +" AND MONTH(a.fecha_terminacion) = "+ mes +") + \n"
                    + "(SELECT SUM(c.parada_equipo) FROM caso c\n"
                    + "WHERE YEAR(c.fecha_solucion) = "+ anio +" AND MONTH(c.fecha_solucion) = "+ mes +") + \n"
                    + "(SELECT if(d.parada_pc IS NULL, 0, SUM(d.parada_pc))  FROM actividades_diarias d \n"
                    + "WHERE YEAR(d.fecha_Hora_ejecucion) = "+ anio +" AND MONTH(d.fecha_Hora_ejecucion) = "+ mes +")) AS 'Parada Equipo',\n"
                    
                    
                    
                    + "((SELECT if(a.Parada_Produccion IS NULL, 0 ,SUM(a.Parada_Produccion))\n"
                    + "FROM actividad_reportada a \n"
                    + "WHERE YEAR(a.fecha_terminacion) = "+ anio +" AND MONTH(a.fecha_terminacion) = "+ mes +") + \n"
                    + "(SELECT  SUM(c.parada_produccion) FROM caso c \n"
                    + "WHERE YEAR(c.fecha_solucion) = "+ anio +" AND MONTH(c.fecha_solucion) = "+ mes +") + \n"
                    + "(SELECT if(d.parada_Produccion IS NULL, 0, SUM(d.parada_Produccion)) FROM actividades_diarias d \n"
                    + "WHERE YEAR(d.fecha_Hora_ejecucion) = "+ anio +" AND MONTH(d.fecha_Hora_ejecucion) = "+ mes +")) AS 'Parada Produccion'";
            Query q = etm.createNativeQuery(query);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Contador_CasosRegistrados(int anio, int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "SELECT ((SELECT COUNT(a.id_actividad_reportada)\n"
                    + "FROM actividad_reportada a \n"
                    + "WHERE YEAR(a.fecha_reportante) = " + anio + " AND MONTH(a.fecha_reportante) = " + mes + ") + \n"
                    + "(SELECT COUNT(c.id_caso)FROM caso c \n"
                    + "WHERE YEAR(c.fecha_envio) = " + anio + " AND MONTH(c.fecha_envio) = " + mes + ") + \n"
                    + "(SELECT COUNT(d.id_actividad) FROM actividades_diarias d \n"
                    + "WHERE YEAR(d.Fecha_Hora) = " + anio + " AND MONTH (d.Fecha_Hora) = " + mes + ")) AS 'ID',\n"
                    + "((SELECT SUM(a.Tiempo_Parada)\n"
                    + "FROM actividad_reportada a \n"
                    + "WHERE YEAR(a.fecha_reportante) = " + anio + " AND MONTH(a.fecha_reportante) = " + mes + ") + \n"
                    + "(SELECT  SUM(c.parada_equipo) FROM caso c\n"
                    + "WHERE YEAR(c.fecha_envio) = " + anio + " AND MONTH(c.fecha_envio) = " + mes + ") + \n"
                    + "(SELECT if(d.parada_pc IS NULL, 0, SUM(d.parada_pc)) FROM actividades_diarias d \n"
                    + "WHERE YEAR(d.Fecha_Hora) = " + anio + " AND MONTH(d.Fecha_Hora) = " + mes + ")) AS 'Parada Equipo',\n"
                    + "((SELECT SUM(a.Parada_Produccion)\n"
                    + "FROM actividad_reportada a \n"
                    + "WHERE YEAR(a.fecha_reportante) = " + anio + " AND MONTH(a.fecha_reportante) = " + mes + ") + \n"
                    + "(SELECT  SUM(c.parada_produccion) FROM caso c \n"
                    + "WHERE YEAR(c.fecha_envio) = " + anio + " AND MONTH(c.fecha_envio) = " + mes + ") + \n"
                    + "(SELECT if(d.parada_Produccion IS NULL, 0, SUM(d.parada_Produccion)) FROM actividades_diarias d \n"
                    + "WHERE YEAR(d.Fecha_Hora) = " + anio + " AND MONTH(d.Fecha_Hora) = " + mes + ")) AS 'Parada Produccion'";
            Query q = etm.createNativeQuery(query);
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

}
