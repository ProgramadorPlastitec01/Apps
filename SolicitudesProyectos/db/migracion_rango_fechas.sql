-- =============================================================================
--  MIGRACION: modo "Rango de fechas" del Informe de Gestion
--  Motor: MariaDB 10.4.24
--
--  ALCANCE MINIMO A PROPOSITO. Los nueve procedimientos del informe ya reciben
--  una ventana (p_fecha_ini, p_fecha_fin): el mes lo derivaba el Servlet, no la
--  base. Verificado con datos reales, el rango jun-ago 2026 da exactamente la
--  suma de los tres meses:
--      horas    2937,88 + 3436,06 + 2957,74 = 9331,68
--      rango         88 +      95 +      82 =     265
--      cerradas      64 +     124 +      37 =     225
--  Por eso NO se tocan. El unico que necesita cambio es la serie de tendencia:
--  agrupar por mes un rango de dos semanas devuelve una sola barra.
--
--  Se corre DESPUES de db/migracion_informe_gestion.sql. Es re-ejecutable.
-- =============================================================================

USE `solicitudes_proyectos`;

DELIMITER $$

-- -----------------------------------------------------------------------------
--  sp_rpt_c_horas_mes  (REEMPLAZA al existente)
--
--  CAMBIO: tercer parametro con la agrupacion de la serie. El modo Mes sigue
--  pidiendo 'mes' sobre su ventana de 12 meses, asi que su grafico es
--  identico al de antes; el modo Rango pide 'semana' o 'dia' cuando el rango es
--  corto y una sola barra mensual no diria nada.
--
--  Las columnas y su orden NO cambian, para no mover los indices que ya usan
--  Tag_reporte y ExportadorExcel.
-- -----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `sp_rpt_c_horas_mes`$$
CREATE PROCEDURE `sp_rpt_c_horas_mes`(
    IN p_fecha_ini  DATE,
    IN p_fecha_fin  DATE,
    IN p_agrupacion VARCHAR(10)   -- 'mes' (predeterminado), 'semana' o 'dia'
)
BEGIN
    DECLARE v_agrupacion VARCHAR(10) DEFAULT LOWER(COALESCE(p_agrupacion, 'mes'));

    IF v_agrupacion NOT IN ('mes', 'semana', 'dia') THEN
        SET v_agrupacion = 'mes';
    END IF;

    SELECT
        -- periodo: clave ordenable. etiqueta: lo que se ve en el grafico.
        CASE v_agrupacion
            WHEN 'dia'    THEN DATE_FORMAT(rgt.fecha_inicio, '%Y-%m-%d')
            -- %x-%v es el anio y la semana ISO: sin %x, la semana 1 de enero
            -- se mezcla con la del anio anterior.
            WHEN 'semana' THEN DATE_FORMAT(rgt.fecha_inicio, '%x-S%v')
            ELSE               DATE_FORMAT(rgt.fecha_inicio, '%Y-%m')
        END                                                                   AS periodo,
        CASE v_agrupacion
            WHEN 'dia'    THEN DATE_FORMAT(rgt.fecha_inicio, '%d/%m')
            WHEN 'semana' THEN CONCAT('sem ', DATE_FORMAT(rgt.fecha_inicio, '%v'))
            ELSE               DATE_FORMAT(rgt.fecha_inicio, '%m/%Y')
        END                                                                   AS etiqueta,
        ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 2) AS horas,
        COUNT(*)                                                              AS registros,
        COUNT(DISTINCT rgt.id_solicitud)                                      AS solicitudes
    FROM registro rgt
    WHERE DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
    GROUP BY periodo, etiqueta
    ORDER BY periodo ASC;
END$$

DELIMITER ;


-- =============================================================================
--  ROLLBACK  (descomentar SOLO para revertir)
--  OJO: sp_rpt_c_horas_mes existia antes con DOS parametros. Revertir es
--  restaurar esa version desde db/migracion_informe_gestion.sql, no borrarlo.
-- =============================================================================
