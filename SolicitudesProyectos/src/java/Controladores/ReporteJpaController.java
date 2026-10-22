package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Controlador de consulta del Informe de Gestion del Taller de Proyectos.
 *
 * Solo invoca procedimientos almacenados y devuelve la List cruda; ningun
 * calculo de negocio vive aqui. A diferencia del resto de controladores del
 * proyecto, los parametros van por setParameter y no concatenados dentro del
 * CALL, para no exponer los procedimientos a inyeccion SQL desde el filtro.
 */
public class ReporteJpaController implements Serializable {

    public ReporteJpaController() {
        emf = Metodos.JPA.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Indicadores del tablero del informe. Mide el volumen y rendimiento real
     * del taller de Proyectos: horas entregadas, solicitudes recibidas,
     * total de trabajos realizados (cerradas), en proceso (backlog),
     * tiempo real de ciclo, primera atención, % de productividad real
     * (solicitudes_cerradas / solicitudes_rango * 100 sin penalización por prioridad),
     * utilización y cobertura del dato.
     *
     * @param fechaInicio fecha inicial del periodo (yyyy-MM-dd)
     * @param fechaFin fecha final del periodo (yyyy-MM-dd)
     * @param area rol del area solicitante; cadena vacia para todas
     * @return una unica fila Object[] con horas_entregadas, solicitudes_rango,
     *         solicitudes_cerradas, backlog, ciclo_mediana,
     *         mediana_primera_atencion, pct_cumplimiento_meta (productividad real),
     *         pct_utilizacion, pct_cobertura_dato, registros_descartados y registros_girados;
     *         o null
     * @see sp_rpt_c_kpis
     */
    public List consultaKpis(String fechaInicio, String fechaFin, String area) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("CALL `sp_rpt_c_kpis`(?,?,?)");
            q.setParameter(1, fechaInicio);
            q.setParameter(2, fechaFin);
            q.setParameter(3, area);
            List resultado = q.getResultList();
            return resultado.isEmpty() ? null : resultado;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Serie mensual de horas entregadas para el grafico de columnas.
     *
     * @param fechaInicio fecha inicial del periodo (yyyy-MM-dd)
     * @param fechaFin fecha final del periodo (yyyy-MM-dd)
     * @param agrupacion 'mes', 'semana' o 'dia'; define el grano de la serie
     * @return filas con periodo, etiqueta, horas, registros y solicitudes, o null
     * @see sp_rpt_c_horas_mes
     */
    public List consultaHorasMes(String fechaInicio, String fechaFin, String agrupacion) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("CALL `sp_rpt_c_horas_mes`(?,?,?)");
            q.setParameter(1, fechaInicio);
            q.setParameter(2, fechaFin);
            q.setParameter(3, agrupacion);
            List resultado = q.getResultList();
            return resultado.isEmpty() ? null : resultado;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Horas entregadas por area solicitante para la dona del informe.
     *
     * @param fechaInicio fecha inicial del periodo (yyyy-MM-dd)
     * @param fechaFin fecha final del periodo (yyyy-MM-dd)
     * @return filas con rol, area, horas, solicitudes y porcentaje, o null
     * @see sp_rpt_c_horas_area
     */
    public List consultaHorasArea(String fechaInicio, String fechaFin) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("CALL `sp_rpt_c_horas_area`(?,?)");
            q.setParameter(1, fechaInicio);
            q.setParameter(2, fechaFin);
            List resultado = q.getResultList();
            return resultado.isEmpty() ? null : resultado;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Capacidad del equipo: horas y utilizacion por tecnico en el periodo.
     *
     * @param fechaInicio fecha inicial del periodo (yyyy-MM-dd)
     * @param fechaFin fecha final del periodo (yyyy-MM-dd)
     * @return filas con tecnico, horas, solicitudes, registros y utilizacion, o null
     * @see sp_rpt_c_tecnicos
     */
    public List consultaTecnicos(String fechaInicio, String fechaFin) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("CALL `sp_rpt_c_tecnicos`(?,?)");
            q.setParameter(1, fechaInicio);
            q.setParameter(2, fechaFin);
            List resultado = q.getResultList();
            return resultado.isEmpty() ? null : resultado;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Respuesta del taller por franja de prioridad. Devuelve SIEMPRE las seis
     * franjas, tambien las que no tienen solicitudes en el periodo, para que la
     * tabla del informe no cambie de forma entre periodos.
     *
     * @param fechaInicio fecha inicial del periodo (yyyy-MM-dd)
     * @param fechaFin fecha final del periodo (yyyy-MM-dd)
     * @param area rol del area solicitante; cadena vacia para todas
     * @return filas con franja, etiqueta, meta_dias, solicitudes,
     *         solicitudes_sin_dato, mediana_atencion, mediana_ciclo y
     *         pct_en_meta; o null
     * @see sp_rpt_c_respuesta_prioridad
     */
    public List consultaRespuestaPrioridad(String fechaInicio, String fechaFin, String area) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("CALL `sp_rpt_c_respuesta_prioridad`(?,?,?)");
            q.setParameter(1, fechaInicio);
            q.setParameter(2, fechaFin);
            q.setParameter(3, area);
            List resultado = q.getResultList();
            return resultado.isEmpty() ? null : resultado;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Detalle de solicitudes para la hoja "Solicitudes" del Excel.
     * Ancladas por fecha de ingreso O de cierre dentro del periodo, el mismo
     * criterio del registro R-PM-009; las horas si van por registro de tiempo.
     *
     * @param fechaInicio fecha inicial del periodo (yyyy-MM-dd)
     * @param fechaFin fecha final del periodo (yyyy-MM-dd)
     * @param area rol del area solicitante; cadena vacia para todas
     * @return filas con id, numero_solicitud, fecha_ingreso, fecha_fin, estado,
     *         dias_ciclo, dias_abierta, prioridad, area_solicitante, plano,
     *         no_parte, tipo_plano y horas_registradas; o null
     * @see sp_rpt_c_xls_solicitudes
     */
    public List consultaXlsSolicitudes(String fechaInicio, String fechaFin, String area) {
        return consultaTresParametros("sp_rpt_c_xls_solicitudes", fechaInicio, fechaFin, area);
    }

    /**
     * Detalle de registros de tiempo. Alimenta las hojas "Registro_Horas" y
     * "Datos_crudos": es la misma consulta y el exportador elige que columnas
     * escribe en cada una, para que las dos hojas no puedan discrepar.
     *
     * @param fechaInicio fecha inicial del periodo (yyyy-MM-dd)
     * @param fechaFin fecha final del periodo (yyyy-MM-dd)
     * @param area rol del area solicitante; cadena vacia para todas
     * @return filas con id_registro, numero_solicitud, fecha_registro, plano,
     *         no_parte, tipo_plano, area_solicitante, prioridad, pieza, tecnico,
     *         horas, mes, estado_solicitud y calidad_dato; o null
     * @see sp_rpt_c_xls_registros
     */
    public List consultaXlsRegistros(String fechaInicio, String fechaFin, String area) {
        return consultaTresParametros("sp_rpt_c_xls_registros", fechaInicio, fechaFin, area);
    }

    /**
     * Horas por plano y pieza para la hoja "Planos_Piezas" del Excel.
     *
     * @param fechaInicio fecha inicial del periodo (yyyy-MM-dd)
     * @param fechaFin fecha final del periodo (yyyy-MM-dd)
     * @param area rol del area solicitante; cadena vacia para todas
     * @return filas con plano, no_parte, tipo_plano, area, pieza, registros,
     *         horas y pct_del_plano; o null
     * @see sp_rpt_c_xls_planos_piezas
     */
    public List consultaXlsPlanosPiezas(String fechaInicio, String fechaFin, String area) {
        return consultaTresParametros("sp_rpt_c_xls_planos_piezas", fechaInicio, fechaFin, area);
    }

    /**
     * Invoca un procedimiento de consulta que recibe rango de fechas y area.
     * Las tres consultas del Excel comparten firma y manejo de errores; tenerlo
     * en un solo sitio evita repetir el patron de cierre cuatro veces.
     *
     * @param procedimiento nombre del procedimiento almacenado
     * @param fechaInicio fecha inicial del periodo (yyyy-MM-dd)
     * @param fechaFin fecha final del periodo (yyyy-MM-dd)
     * @param area rol del area solicitante; cadena vacia para todas
     * @return la List de resultados, o null si no hay filas o fallo la consulta
     */
    private List consultaTresParametros(String procedimiento, String fechaInicio, String fechaFin, String area) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("CALL `" + procedimiento + "`(?,?,?)");
            q.setParameter(1, fechaInicio);
            q.setParameter(2, fechaFin);
            q.setParameter(3, area);
            List resultado = q.getResultList();
            return resultado.isEmpty() ? null : resultado;
        } catch (Exception e) {
            return null;
        } finally {
            if (em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }
}
