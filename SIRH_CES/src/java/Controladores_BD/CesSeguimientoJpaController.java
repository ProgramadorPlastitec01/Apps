package Controladores_BD;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CesSeguimientoJpaController implements Serializable {

    public CesSeguimientoJpaController() {
        emf = Persistence.createEntityManagerFactory("SIRH_CESPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Verificacion_existencia_seg(String dcm, String icg, int anio, String mes) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_csg_verificacion_existencia`('" + dcm + "','" + icg + "','" + anio + "','" + mes + "');");
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
            Query q = etm.createNativeQuery("CALL `sp_csg_registrar`('" + dcm + "', '" + icg + "', '" + dts + "', '" + mtv + "', '" + etd + "', '" + anio + "', '" + mes + "');");
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

    public String Calculos_marcacion(String dcm, String icg, int anio, String mes, String dia) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String dia_seguimiento = "seg_" + Integer.parseInt(dia);
            String dia_inicio = "ent_" + Integer.parseInt(dia);
            String dia_fin = "sal_" + Integer.parseInt(dia);
            String sentencia = "UPDATE ces_seguimiento SET seg_19 = "
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
                    + "FROM ces c WHERE c.documento = " + dcm + " AND c.id_cargo = " + icg + " AND c.anio = " + anio + " AND c.mes = '" + Integer.parseInt(mes) + "')"
                    + " WHERE documento = " + dcm + " AND id_cargo = " + icg + " AND anio = " + anio + " AND mes = '" + Integer.parseInt(mes) + "';";
            sentencia = sentencia.replace("seg_19", dia_seguimiento);
            sentencia = sentencia.replace("ent_19", dia_inicio);
            sentencia = sentencia.replace("sal_19", dia_fin);
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

}
