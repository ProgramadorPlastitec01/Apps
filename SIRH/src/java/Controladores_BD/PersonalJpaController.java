package Controladores_BD;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PersonalJpaController {

    public PersonalJpaController() {
        emf = Persistence.createEntityManagerFactory("SIRHPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Consultar_empleado_documento(String dcm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_psn_t_persona_documento`('" + dcm + "')");
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

    public List Consultar_CapacitacionExternal(int icp, long dcm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_cpd_c_ConsultPersonalExternal`(" + icp + ", " + dcm + ")");
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

    public List Consultar_empleado_documento_old(String dcm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_psn_t_persona_documento_old`('" + dcm + "')");
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

    public List Consultar_empleados(int etd, int iar, int cps) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_psn_c_empleados`('" + etd + "','" + iar + "','" + cps + "')");
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

    public List Consultar_empleados_general(int iar) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_psn_c_empleados_general`('" + iar + "')");
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

    public List Consultar_empleados_abc(String abc, int etd, int iar, int cps) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_psn_c_empleados_abc`('" + abc + "','" + etd + "','" + iar + "','" + cps + "')");
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

    public List Reporte_cumpleanios(int mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_psn_t_reporte_cumpleanios`('" + mes + "')");
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
    
    public List ConsultarIdGLPI(String doc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_c_prs_ConsutlarGLPI_id`('" + doc + "')");
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

    public boolean Registrar_id_glpi(int doc, int idgl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `Sp_r_glpi_RegistrarIdGLPI`(" + doc + ", " + idgl + ")");
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
    public boolean Registrar_empleado(String dcm, String nbs, String apl, String gnr, String fnc, String cfm, String epc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_psn_r_personal`('" + dcm + "','" + nbs + "','" + apl + "','" + gnr + "','" + fnc + "','" + cfm + "','" + epc + "')");
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

    public boolean Modificar_empleado(String dcm, String nbs, String apl, String gnr, String fnc, String cfm, String epc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_psn_m_personal`('" + dcm + "','" + nbs + "','" + apl + "','" + gnr + "','" + fnc + "','" + cfm + "','" + epc + "')");
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
//TABLA DATOS PERSONAL

    public boolean Registrar_datos_empleado(String dcm, int icg, String fig, String slr, String ctt, String etd, String cro, String tfn, String nhj, String bgd, String urg, String gsg, String cug, String rfs, String rmd, String ned, String fct, String lcl) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pdt_r_datos_personal`('" + dcm + "','" + icg + "','" + fig + "','" + slr + "','" + ctt + "','" + etd + "','" + cro + "','" + tfn + "','" + nhj + "','" + bgd + "','" + urg + "','" + gsg + "','" + cug + "','" + rfs + "','" + rmd + "','" + ned + "','" + fct + "','" + lcl + "')");
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

    public boolean Actualizar_salarios(String dcm, int slr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pdt_r_actualizar_salario`('" + dcm + "','" + slr + "')");
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

    public boolean Inactivar_datos_old_empleado(String dcm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE personal_datos SET vigencia = 0 WHERE documento = " + dcm);
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

    public boolean Modificar_especialidad(String dcm, String epc, String cdg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE personal SET especialidad = '" + epc + "', codigo_firma = '" + cdg + "' WHERE documento = " + dcm);
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

    public boolean ModificarSindicalizado(String dcm, String sdc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_psn_m_modificar_sindicalizado`('" + dcm + "','" + sdc + "')");
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

    //<editor-fold defaultstate="collapsed" desc="MARCACIONES">
    public List Historial_marcaciones(String fpi, String fpf, int iar, int icg, int tcs, String dcm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String sentencia_marcacion = "SELECT c.id_ces, c.documento, c.id_cargo, c.datos, c.motivo, c.estado, c.anio, c.mes, c.ent_1, c.sal_1,\n"
                    + "c.ent_2, c.sal_2,\n"
                    + "c.ent_3, c.sal_3,\n"
                    + "c.ent_4, c.sal_4,\n"
                    + "c.ent_5, c.sal_5,\n"
                    + "c.ent_6, c.sal_6,\n"
                    + "c.ent_7, c.sal_7,\n"
                    + "c.ent_8, c.sal_8,\n"
                    + "c.ent_9, c.sal_9,\n"
                    + "c.ent_10, c.sal_10,\n"
                    + "c.ent_11, c.sal_11,\n"
                    + "c.ent_12, c.sal_12,\n"
                    + "c.ent_13, c.sal_13,\n"
                    + "c.ent_14, c.sal_14,\n"
                    + "c.ent_15, c.sal_15,\n"
                    + "c.ent_16, c.sal_16,\n"
                    + "c.ent_17, c.sal_17,\n"
                    + "c.ent_18, c.sal_18,\n"
                    + "c.ent_19, c.sal_19,\n"
                    + "c.ent_20, c.sal_20,\n"
                    + "c.ent_21, c.sal_21,\n"
                    + "c.ent_22, c.sal_22,\n"
                    + "c.ent_23, c.sal_23,\n"
                    + "c.ent_24, c.sal_24,\n"
                    + "c.ent_25, c.sal_25,\n"
                    + "c.ent_26, c.sal_26,\n"
                    + "c.ent_27, c.sal_27,\n"
                    + "c.ent_28, c.sal_28,\n"
                    + "c.ent_29, c.sal_29,\n"
                    + "c.ent_30, c.sal_30,\n"
                    + "c.ent_31, c.sal_31, \n"
                    + "c.entrada, c.salida \n"
                    + "FROM sirh_ces.ces c INNER JOIN cargo cg ON c.id_cargo = cg.id_cargo\n"
                    + "	INNER JOIN area a ON cg.id_area = a.id_area\n"
                    + "WHERE (c.anio between year('" + fpi + "') and year('" + fpf + "')) and (c.mes between month('" + fpi + "') and month('" + fpf + "')) *condicion* \n"
                    + " ORDER BY c.documento, c.anio asc,c.mes asc";
            String sentencia_detalle = "SELECT c.*\n"
                    + "FROM sirh_ces.ces_seguimiento c INNER JOIN cargo cg ON c.id_cargo = cg.id_cargo\n"
                    + "	INNER JOIN area a ON cg.id_area = a.id_area\n"
                    + "WHERE (c.anio between year('" + fpi + "') and year('" + fpf + "')) and (c.mes between month('" + fpi + "') and month('" + fpf + "')) *condicion* \n"
                    + " ORDER BY c.documento, c.anio asc,c.mes asc";

            String sentencia_exe = "";
            if (tcs == 0) {
                sentencia_exe = sentencia_marcacion;
            } else {
                sentencia_exe = sentencia_detalle;
            }
            String condicion = "";
            String area = " AND a.id_area = " + iar;
            String cargo = " AND c.id_cargo = " + icg;
            String empleado = " AND c.documento = " + dcm;
            condicion = area + ((icg > 0) ? cargo : "") + ((dcm.equals("0")) ? "" : empleado);
            //condicion = area;
            sentencia_exe = sentencia_exe.replace("*condicion*", condicion);
            Query q = etm.createNativeQuery(sentencia_exe);
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

    public List Consultar_datos_calculados(String dcm, String icg, int anio, int mes, int dia) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String sentencia = "SELECT s.seg_" + dia + ",s.obs_" + dia + " FROM sirh_ces.ces_seguimiento s WHERE s.documento = " + dcm + " and s.id_cargo = " + icg + " and s.anio = " + anio + " and s.mes = " + mes + ";";
            Query q = etm.createNativeQuery(sentencia);
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

    public boolean Cambiar_marcacion(String dcm, int icg, int anio, int mes, int dia, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE sirh_ces.ces SET ent_" + dia + " = '" + fin + "', sal_" + dia + " = '" + ffn + "' WHERE documento = " + dcm + " and id_cargo = " + icg + " and anio = " + anio + " and mes = " + mes + ";");
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

    public String Calculos_marcacion(String dcm, String icg, int anio, String mes, String dia, String obs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String dia_seguimiento = "seg_" + Integer.parseInt(dia);
            String dia_inicio = "ent_" + Integer.parseInt(dia);
            String dia_fin = "sal_" + Integer.parseInt(dia);
            //String obs_dia = "obs_" + Integer.parseInt(dia) + " = CONCAT(obs_" + Integer.parseInt(dia) + ",'" + obs + "',now(),'<hr />'),";
            String obs_dia = "obs_" + Integer.parseInt(dia) + " = CONCAT('" + obs + "',now()) , ";
            String sentencia = "UPDATE sirh_ces.ces_seguimiento SET *observacion* seg_19 = "
                    + "(SELECT concat('Dia_entrada:',(ELT(WEEKDAY(c.ent_19) + 1, 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado', 'Domingo')),'/Entrada:',c.ent_19,\n"
                    + "	'/Dia_salida:',(ELT(WEEKDAY(c.sal_19) + 1, 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado', 'Domingo')),'/Salida:', c.sal_19,\n"
                    + "	'/Turno:',IF(c.ent_19 >= concat(date(c.ent_19),' 20:00:00'),'Turno 3', IF(c.ent_19 >= concat(date(c.ent_19),' 12:00:00'),'Turno 2','Turno 1')),\n"
                    + "	'/Horas_trabajo:',ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60),2),\n"
                    + "	'/Total_horas_extra:',ROUND(((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60) - 8),2),\n"
                    + "	'/Extras_diurnas:',CASE\n"
                    + "		WHEN ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60),2) > 8 and (c.sal_19 >= concat(date(c.sal_19),' 21:00:00') and c.ent_19 >= concat(date(c.ent_19),' 06:00:00'))\n"
                    + "			THEN IF(ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,concat(date(c.sal_19),' 21:00:00'))/60)-8,2)<0,0,ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,concat(date(c.sal_19),' 21:00:00'))/60)-8,2))\n"
                    + "		WHEN ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60),2) > 8 and (c.sal_19 < concat(date(c.sal_19),' 21:00:00') and c.ent_19 < concat(date(c.ent_19),' 06:00:00'))\n"
                    + "			THEN IF(ROUND(((TIMESTAMPDIFF(MINUTE,concat(date(c.ent_19),' 06:00:00'),c.sal_19)/60) - 8),2)<0,0,ROUND(((TIMESTAMPDIFF(MINUTE,concat(date(c.ent_19),' 06:00:00'),c.sal_19)/60) - 8),2))\n"
                    + "		WHEN ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60),2) > 8 and (c.sal_19 > concat(date(c.sal_19),' 06:00:00') and c.ent_19 >= concat(date(c.ent_19),' 21:00:00'))\n"
                    + "			THEN IF(ROUND(((TIMESTAMPDIFF(MINUTE,concat(date(c.sal_19),' 06:00:00'),c.sal_19))/60),2)<0,0,ROUND(((TIMESTAMPDIFF(MINUTE,concat(date(c.sal_19),' 06:00:00'),c.sal_19))/60),2))\n"
                    + "		WHEN ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60),2) > 8\n"
                    + "			THEN IF(ROUND(((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19))/60)-8,2)<0,0,ROUND(((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19))/60)-8,2))\n"
                    + "		ELSE 0\n"
                    + "	END,\n"
                    + "	'/Extras_nocturnas:',CASE\n"
                    + "		WHEN ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60),2) > 8 and c.sal_19 >= concat(date(c.sal_19),' 21:00:00')\n"
                    + "			THEN IF(ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60)-8,2)<0,0,ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60)-8,2))\n"
                    + "		WHEN ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60),2) > 8 and (c.sal_19 > concat(date(c.sal_19),' 06:00:00') and c.ent_19 >= concat(date(c.ent_19),' 21:00:00'))\n"
                    + "			THEN IF(ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,concat(date(c.sal_19),' 06:00:00'))/60)-8,2)<0,0,ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,concat(date(c.sal_19),' 06:00:00'))/60)-8,2))\n"
                    + "		ELSE 0\n"
                    + "	END,\n"
                    + "	'/Recargo_nocturno:',CASE\n"
                    + "   	WHEN c.ent_19 < concat(date(c.ent_19),' 21:00:00') and c.sal_19 >= concat(date(c.ent_19),' 21:00:00')\n"
                    + "			THEN ROUND((TIMESTAMPDIFF(MINUTE,concat(date(c.ent_19),' 21:00:00'),c.sal_19)/60),2)\n"
                    + "		WHEN c.ent_19 >= concat(date(c.ent_19),' 21:00:00') and c.sal_19 > concat(DATE_FORMAT(DATE_ADD(c.ent_19, interval 1 day), '%Y-%m-%d'),' 06:00:00')\n"
                    + "			THEN ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,concat(date(c.sal_19),' 06:00:00'))/60),2)\n"
                    + "		WHEN c.ent_19 >= concat(date(c.ent_19),' 21:00:00') AND c.sal_19 <= concat(DATE_FORMAT(DATE_ADD(c.ent_19, interval 1 day), '%Y-%m-%d'),' 06:00:00')\n"
                    + "			THEN ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60),2)\n"
                    + "   	ELSE 0\n"
                    + "	END,\n"
                    + "	'/Horas_dominical:',CASE\n"
                    + "   	WHEN (WEEKDAY(c.ent_19) + 1) = 7 and (WEEKDAY(c.sal_19) + 1) = 7\n"
                    + "			THEN ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)/60),2) \n"
                    + "		WHEN (WEEKDAY(c.ent_19) + 1) = 7 and (WEEKDAY(c.sal_19) + 1) <> 7\n"
                    + "			THEN ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,concat(date(c.ent_19),' 23:59:59'))/60),2) \n"
                    + "		WHEN (WEEKDAY(c.ent_19) + 1) <> 7 and (WEEKDAY(c.sal_19) + 1) = 7\n"
                    + "			THEN ROUND((TIMESTAMPDIFF(MINUTE,concat(date(c.sal_19),' 00:00:01'),c.sal_19)/60),2) \n"
                    + "   	ELSE 0\n"
                    + "	END)\n"
                    + "FROM sirh_ces.ces c WHERE c.documento = " + dcm + " AND c.id_cargo = " + icg + " AND c.anio = " + anio + " AND c.mes = '" + Integer.parseInt(mes) + "')"
                    + " WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + anio + " AND mes = '" + Integer.parseInt(mes) + "';";
            sentencia = sentencia.replace("seg_19", dia_seguimiento);
            sentencia = sentencia.replace("ent_19", dia_inicio);
            sentencia = sentencia.replace("sal_19", dia_fin);
            if (obs.length() > 0) {
                sentencia = sentencia.replace("*observacion*", obs_dia);
            } else {
                sentencia = sentencia.replace("*observacion*", "");
            }
            Query q = etm.createNativeQuery(sentencia);
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return "FALLO";
            } else {
                return "EXITO";
            }
        } catch (Exception ex) {
            return "FALLO";
        }
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CAFE">
    public List Historial_marcacionesCafe(String fpi, String fpf, int iar, int icg, int tcs, String dcm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String sentencia_marcacion = "SELECT c.id_cafe, c.documento, c.id_cargo, c.datos, c.motivo, c.estado, c.anio, c.mes, c.ent_1, c.sal_1,\n"
                    + "c.ent_2, c.sal_2,\n"
                    + "c.ent_3, c.sal_3,\n"
                    + "c.ent_4, c.sal_4,\n"
                    + "c.ent_5, c.sal_5,\n"
                    + "c.ent_6, c.sal_6,\n"
                    + "c.ent_7, c.sal_7,\n"
                    + "c.ent_8, c.sal_8,\n"
                    + "c.ent_9, c.sal_9,\n"
                    + "c.ent_10, c.sal_10,\n"
                    + "c.ent_11, c.sal_11,\n"
                    + "c.ent_12, c.sal_12,\n"
                    + "c.ent_13, c.sal_13,\n"
                    + "c.ent_14, c.sal_14,\n"
                    + "c.ent_15, c.sal_15,\n"
                    + "c.ent_16, c.sal_16,\n"
                    + "c.ent_17, c.sal_17,\n"
                    + "c.ent_18, c.sal_18,\n"
                    + "c.ent_19, c.sal_19,\n"
                    + "c.ent_20, c.sal_20,\n"
                    + "c.ent_21, c.sal_21,\n"
                    + "c.ent_22, c.sal_22,\n"
                    + "c.ent_23, c.sal_23,\n"
                    + "c.ent_24, c.sal_24,\n"
                    + "c.ent_25, c.sal_25,\n"
                    + "c.ent_26, c.sal_26,\n"
                    + "c.ent_27, c.sal_27,\n"
                    + "c.ent_28, c.sal_28,\n"
                    + "c.ent_29, c.sal_29,\n"
                    + "c.ent_30, c.sal_30,\n"
                    + "c.ent_31, c.sal_31, \n"
                    + "c.entrada, c.salida \n"
                    + "FROM sirh_ces.cafe c INNER JOIN cargo cg ON c.id_cargo = cg.id_cargo\n"
                    + "	INNER JOIN area a ON cg.id_area = a.id_area\n"
                    + "WHERE (c.anio between year('" + fpi + "') and year('" + fpf + "')) and (c.mes between month('" + fpi + "') and month('" + fpf + "')) *condicion* \n"
                    + " ORDER BY c.documento, c.anio asc,c.mes asc";
            String sentencia_detalle = "SELECT c.*\n"
                    + "FROM sirh_ces.cafe_seguimiento c INNER JOIN cargo cg ON c.id_cargo = cg.id_cargo\n"
                    + "	INNER JOIN area a ON cg.id_area = a.id_area\n"
                    + "WHERE (c.anio between year('" + fpi + "') and year('" + fpf + "')) and (c.mes between month('" + fpi + "') and month('" + fpf + "')) *condicion* \n"
                    + " ORDER BY c.documento, c.anio asc,c.mes asc";

            String sentencia_exe = "";
            if (tcs == 0) {
                sentencia_exe = sentencia_marcacion;
            } else {
                sentencia_exe = sentencia_detalle;
            }
            String condicion = "";
            String area = " AND a.id_area = " + iar;
            String cargo = " AND c.id_cargo = " + icg;
            String empleado = " AND c.documento = " + dcm;
            condicion = area + ((icg > 0) ? cargo : "") + ((dcm.equals("0")) ? "" : empleado);
            //condicion = area;
            sentencia_exe = sentencia_exe.replace("*condicion*", condicion);
            Query q = etm.createNativeQuery(sentencia_exe);
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

    public String Calculos_marcacionCafe(String dcm, String icg, int anio, String mes, String dia, String obs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String dia_seguimiento = "seg_" + Integer.parseInt(dia);
            String dia_inicio = "ent_" + Integer.parseInt(dia);
            String dia_fin = "sal_" + Integer.parseInt(dia);
            //String obs_dia = "obs_" + Integer.parseInt(dia) + " = CONCAT(obs_" + Integer.parseInt(dia) + ",'" + obs + "',now(),'<hr />'),";
            String obs_dia = "obs_" + Integer.parseInt(dia) + " = CONCAT('" + obs + "',now()) , ";
            String sentencia = "UPDATE sirh_ces.cafe_seguimiento SET *observacion* seg_19 = "
                    + "(SELECT concat('Dia_entrada:',(ELT(WEEKDAY(c.ent_19) + 1, 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado', 'Domingo')),'/Entrada:',c.ent_19,\n"
                    + "	'/Dia_salida:',(ELT(WEEKDAY(c.sal_19) + 1, 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado', 'Domingo')),'/Salida:', c.sal_19,\n"
                    + "	'/Turno:',IF(c.ent_19 >= concat(date(c.ent_19),' 20:00:00'),'Turno 3', IF(c.ent_19 >= concat(date(c.ent_19),' 12:00:00'),'Turno 2','Turno 1')),\n"
                    + "	'/Minutos_trabajo:',ROUND((TIMESTAMPDIFF(MINUTE,c.ent_19,c.sal_19)),2)) "
                    + "FROM sirh_ces.cafe c WHERE c.documento = " + dcm + " AND c.id_cargo = " + icg + " AND c.anio = " + anio + " AND c.mes = '" + Integer.parseInt(mes) + "')"
                    + " WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + anio + " AND mes = '" + Integer.parseInt(mes) + "';";
            sentencia = sentencia.replace("seg_19", dia_seguimiento);
            sentencia = sentencia.replace("ent_19", dia_inicio);
            sentencia = sentencia.replace("sal_19", dia_fin);
            if (obs.length() > 0) {
                sentencia = sentencia.replace("*observacion*", obs_dia);
            } else {
                sentencia = sentencia.replace("*observacion*", "");
            }
            Query q = etm.createNativeQuery(sentencia);
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return "FALLO";
            } else {
                return "EXITO";
            }
        } catch (Exception ex) {
            return "FALLO";
        }
    }

    public List Consultar_datos_calculados_Cafe(String dcm, String icg, int anio, int mes, int dia) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String sentencia = "SELECT s.seg_" + dia + ",s.obs_" + dia + " FROM sirh_ces.cafe_seguimiento s WHERE s.documento = " + dcm + " and s.id_cargo = " + icg + " and s.anio = " + anio + " and s.mes = " + mes + ";";
            Query q = etm.createNativeQuery(sentencia);
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

    public boolean Cambiar_marcacionCafe(String dcm, int icg, int anio, int mes, int dia, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE sirh_ces.cafe SET ent_" + dia + " = '" + fin + "', sal_" + dia + " = '" + ffn + "' WHERE documento = " + dcm + " and id_cargo = " + icg + " and anio = " + anio + " and mes = " + mes + ";");
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

    public List Verificacion_existencia_Cafe(String dcm, String icg, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "SELECT c.id_cafe,c.documento,c.id_cargo,c.datos,c.motivo, "
                    + "	(SELECT SUBSTRING_INDEX(GROUP_CONCAT(c.estado ORDER BY c.id_cafe DESC),',',2) FROM sirh_ces.cafe c WHERE c.documento = '" + dcm + "' AND c.id_cargo = '" + icg + "') AS 'estado',c.anio,c.mes, "
                    + "	IFNULL((SELECT s.id_cafe_seguimiento FROM sirh_ces.cafe_seguimiento s WHERE s.documento = '" + dcm + "' AND s.id_cargo = '" + icg + "' AND s.anio = " + anio + " AND s.mes = " + mes + "),0) AS 'id_cafe_seguimiento' "
                    + " FROM sirh_ces.cafe c "
                    + " WHERE c.documento = '" + dcm + "' AND c.id_cargo = '" + icg + "' AND c.anio = '" + anio + "' AND c.mes = '" + mes + "'";
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

    public boolean Registrar_seguimiento_anio_mes(String dcm, String icg, String dts, String mtv, String etd, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "insert into sirh_ces.cafe_seguimiento(documento,id_cargo,datos,motivo,estado,anio,mes) "
                    + "values(" + dcm + "," + icg + ",'" + dts + "','" + mtv + "','" + etd + "'," + anio + "," + mes + ")";
            Query q = etm.createNativeQuery(query);
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
