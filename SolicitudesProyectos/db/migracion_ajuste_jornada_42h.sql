-- =============================================================================
--  MIGRACION: ajuste a la jornada de 42 horas (Ley 2101, 2026)
--  Motor: MariaDB 10.4.24
--
--  Cuatro ajustes quirurgicos e idempotentes:
--    1. Candado de turno: 480 -> 540 min (permite la jornada de 8h24 = 504 min).
--    2. Capacidad legal: horas_disponibles_tecnico_mes 176 -> 168.
--    3. Roster fijo opcional (tecnicos_planta_taller) para el denominador de
--       utilizacion, con fallback al conteo tradicional.
--    4. Crea el SP faltante sp_pno_c_plano_filtro (busqueda de planos rota).
--
--  Re-ejecutable. No altera firmas ni el resto de la logica ya funcional.
-- =============================================================================

USE `solicitudes_proyectos`;

-- -----------------------------------------------------------------------------
--  1. sp_rgt_r_registro: umbral 480 -> 540 min. Todo lo demas IDENTICO
--     (10 parametros, cruce de medianoche, freeze de numero_parte).
-- -----------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `sp_rgt_r_registro`$$
CREATE PROCEDURE `sp_rgt_r_registro`(
    IN usaR  VARCHAR(100),
    IN ids   INT,
    IN idm   INT,
    IN idp   INT,
    IN piz   VARCHAR(100),
    IN cant  INT,
    IN des   TEXT,
    IN horaI DATETIME,
    IN horaF DATETIME,
    IN npart VARCHAR(45)
)
BEGIN
    DECLARE v_fin DATETIME;
    DECLARE v_min INT;

    SET v_fin = IF(horaF < horaI AND DATE(horaF) = DATE(horaI),
                   horaF + INTERVAL 1 DAY, horaF);
    SET v_min = TIMESTAMPDIFF(MINUTE, horaI, v_fin);

    -- Candado de turno laboral (Ley 2101: jornada de 8h24 = 504 min; se deja
    -- una ventana de cierre hasta 540 min = 9 h, pero se sigue bloqueando el
    -- turno doble no autorizado).
    IF v_min <= 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: La fecha final debe ser posterior a la fecha inicial.';
    END IF;
    IF v_min > 540 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: No se puede registrar mas de 9 horas en un turno continuo (max 540 min). Registre controles separados.';
    END IF;

    INSERT INTO registro
        (usuario_registro, id_solicitud, id_maquina, id_plano, pieza,
         cantidad, descripcion, fecha_inicio, fecha_fin, tiempo, numero_parte)
    VALUES
        (usaR, ids, idm, idp, piz, cant, des, horaI, horaF,
         CONCAT(FLOOR(v_min / 60), ':', LPAD(MOD(v_min, 60), 2, '0')),
         NULLIF(TRIM(COALESCE(npart, _latin1'')), _latin1''));
END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--  2. Capacidad legal: 176 (jornada vieja de 48h) -> 168 (42h/sem).
-- -----------------------------------------------------------------------------
UPDATE `parametros`
   SET `valor` = '168'
 WHERE `categoria` = 'horas_disponibles_tecnico_mes';

-- -----------------------------------------------------------------------------
--  3. Roster fijo opcional. INSERT idempotente por NOT EXISTS (categoria no es
--     UNIQUE, asi que INSERT IGNORE no bastaria).
-- -----------------------------------------------------------------------------
INSERT INTO `parametros` (`categoria`, `valor`, `descripcion`, `estado`, `usuario_registro`, `fecha_registro`)
SELECT 'tecnicos_planta_taller', '0',
       'Cantidad fija de tecnicos en plantilla para calculo de capacidad. Si es 0, usa los tecnicos con registros en el periodo',
       1, 'MIGRACION', NOW()
  FROM DUAL
 WHERE NOT EXISTS (SELECT 1 FROM `parametros` WHERE `categoria` = 'tecnicos_planta_taller');

-- -----------------------------------------------------------------------------
--  sp_rpt_c_kpis (REEMPLAZA): fallback 168 y denominador por roster fijo si se
--  configura. El resto de la logica queda IDENTICO al vigente.
-- -----------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `sp_rpt_c_kpis`$$
CREATE PROCEDURE `sp_rpt_c_kpis`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE,
    IN p_area      VARCHAR(45)
)
BEGIN
    DECLARE v_meta_1a              INT           DEFAULT 1;
    DECLARE v_meta_2a              INT           DEFAULT 1;
    DECLARE v_meta_1b              INT           DEFAULT 7;
    DECLARE v_meta_2b              INT           DEFAULT 30;
    DECLARE v_horas_tecnico        DECIMAL(10,2) DEFAULT 168;
    DECLARE v_tecnicos_fijos       INT           DEFAULT 0;
    DECLARE v_horas                DECIMAL(14,2) DEFAULT 0;
    DECLARE v_tecnicos             INT           DEFAULT 0;
    DECLARE v_descartados          INT           DEFAULT 0;
    DECLARE v_girados              INT           DEFAULT 0;
    DECLARE v_meses                DECIMAL(10,4) DEFAULT 1;
    DECLARE v_ciclo                DECIMAL(10,2) DEFAULT 0;
    DECLARE v_atencion             DECIMAL(10,2) DEFAULT 0;
    DECLARE v_cumplimiento         DECIMAL(10,2) DEFAULT 0;
    DECLARE v_solicitudes_rango    INT           DEFAULT 0;
    DECLARE v_solicitudes_cerradas INT           DEFAULT 0;

    SELECT COALESCE(MAX(CASE WHEN categoria = 'meta_dias_1a'                  THEN valor END), 1)
         , COALESCE(MAX(CASE WHEN categoria = 'meta_dias_2a'                  THEN valor END), 1)
         , COALESCE(MAX(CASE WHEN categoria = 'meta_dias_1b'                  THEN valor END), 7)
         , COALESCE(MAX(CASE WHEN categoria = 'meta_dias_2b'                  THEN valor END), 30)
         , COALESCE(MAX(CASE WHEN categoria = 'horas_disponibles_tecnico_mes' THEN valor END), 168)
         , COALESCE(MAX(CASE WHEN categoria = 'tecnicos_planta_taller'        THEN valor END), 0)
      INTO v_meta_1a, v_meta_2a, v_meta_1b, v_meta_2b, v_horas_tecnico, v_tecnicos_fijos
      FROM parametros
     WHERE estado = 1;

    SELECT COALESCE(ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 2), 0),
           COUNT(DISTINCT rgt.usuario_registro),
           SUM(fn_rpt_estado_registro(rgt.fecha_inicio, rgt.fecha_fin) = 'DESCARTADO'),
           SUM(fn_rpt_estado_registro(rgt.fecha_inicio, rgt.fecha_fin) = 'GIRADO')
      INTO v_horas, v_tecnicos, v_descartados, v_girados
      FROM registro rgt
      INNER JOIN solicitud slt ON rgt.id_solicitud = slt.idSolicitud
      INNER JOIN usuario   usa ON slt.idUsuarios   = usa.idUsuarios
     WHERE DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
       AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area);

    SET v_meses = GREATEST((DATEDIFF(p_fecha_fin, p_fecha_ini) + 1) / 30.4375, 0.0001);

    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_atencion;
    CREATE TEMPORARY TABLE tmp_rpt_atencion (
        franja        VARCHAR(2) CHARACTER SET latin1 NOT NULL,
        dias_atencion INT NULL,
        dias_ciclo    INT NULL
    ) ENGINE = MEMORY;

    INSERT INTO tmp_rpt_atencion (franja, dias_atencion, dias_ciclo)
    SELECT
        fn_rpt_franja_prioridad(slt.Prioridad),
        CASE
            WHEN pa.primera IS NULL THEN NULL
            WHEN DATEDIFF(pa.primera, DATE(slt.Fecha_Ingreso)) < 0 THEN NULL
            ELSE DATEDIFF(pa.primera, DATE(slt.Fecha_Ingreso))
        END,
        DATEDIFF(slt.Fecha_Fin_Solicitud, DATE(slt.Fecha_Ingreso))
    FROM solicitud slt
    INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
    LEFT JOIN (SELECT rgt.id_solicitud, MIN(rgt.fecha_inicio) AS primera
                 FROM registro rgt
                WHERE fn_rpt_estado_registro(rgt.fecha_inicio, rgt.fecha_fin) <> 'DESCARTADO'
                GROUP BY rgt.id_solicitud) AS pa ON pa.id_solicitud = slt.idSolicitud
    WHERE slt.Estado = 100
      AND slt.Fecha_Fin_Solicitud BETWEEN p_fecha_ini AND p_fecha_fin
      AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area);

    SELECT COALESCE(MAX(m), 0) INTO v_ciclo
      FROM (SELECT MEDIAN(dias_ciclo) OVER () AS m FROM tmp_rpt_atencion) AS c;

    SELECT COALESCE(MAX(m), 0) INTO v_atencion
      FROM (SELECT MEDIAN(dias_atencion) OVER () AS m
              FROM tmp_rpt_atencion WHERE dias_atencion IS NOT NULL) AS d;

    SELECT COUNT(*)
      INTO v_solicitudes_rango
      FROM solicitud slt
      INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
     WHERE DATE(slt.Fecha_Ingreso) BETWEEN p_fecha_ini AND p_fecha_fin
       AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area);

    SELECT COUNT(*)
      INTO v_solicitudes_cerradas
      FROM solicitud slt
      INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
     WHERE slt.Estado = 100
       AND slt.Fecha_Fin_Solicitud BETWEEN p_fecha_ini AND p_fecha_fin
       AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area);

    SET v_cumplimiento = COALESCE(ROUND(v_solicitudes_cerradas * 100 / NULLIF(v_solicitudes_rango, 0), 2), 0);

    SELECT
        v_horas                AS horas_entregadas,
        v_solicitudes_rango    AS solicitudes_rango,
        v_solicitudes_cerradas AS solicitudes_cerradas,
        (SELECT COUNT(*)
           FROM solicitud slt
           INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
          WHERE slt.Estado = 0
            AND DATE(slt.Fecha_Ingreso) <= p_fecha_fin
            AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area)
        )                      AS backlog,
        v_ciclo                AS ciclo_mediana,
        v_atencion             AS mediana_primera_atencion,
        v_cumplimiento         AS pct_cumplimiento_meta,
        -- Denominador de capacidad: si hay roster fijo configurado se usa; si no,
        -- el conteo de tecnicos que registraron (retrocompatibilidad).
        COALESCE(ROUND(v_horas * 100
                       / NULLIF(IF(v_tecnicos_fijos > 0, v_tecnicos_fijos, v_tecnicos)
                                * v_horas_tecnico * v_meses, 0), 2), 0) AS pct_utilizacion,
        (SELECT COALESCE(ROUND(
                    SUM(IF(EXISTS (SELECT 1 FROM registro r WHERE r.id_solicitud = slt.idSolicitud), 1, 0))
                    * 100 / NULLIF(COUNT(*), 0), 2), 0)
           FROM solicitud slt
           INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
          WHERE slt.Estado = 100
            AND slt.Fecha_Fin_Solicitud BETWEEN p_fecha_ini AND p_fecha_fin
            AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area)
        )                      AS pct_cobertura_dato,
        v_descartados          AS registros_descartados,
        v_girados              AS registros_girados;

    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_atencion;
END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--  4. sp_pno_c_plano_filtro (NUEVO): la busqueda de planos invocaba un SP
--     inexistente. Devuelve las MISMAS 5 columnas y orden que sp_pno_c_plano_id.
-- -----------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `sp_pno_c_plano_filtro`$$
CREATE PROCEDURE `sp_pno_c_plano_filtro`(
    IN p_filtro VARCHAR(100)
)
BEGIN
    DECLARE v_filtro VARCHAR(102);
    SET v_filtro = CONCAT('%', COALESCE(TRIM(p_filtro), _latin1''), '%');

    SELECT pa.id_plano,
           pa.nombre_plano,
           pa.tipo,
           pa.fecha_ingreso,
           COALESCE(pa.numero_parte, _latin1'') AS numero_parte
      FROM plano pa
     WHERE pa.nombre_plano LIKE v_filtro
        OR pa.tipo LIKE v_filtro
        OR pa.numero_parte LIKE v_filtro
     ORDER BY pa.nombre_plano ASC;
END$$
DELIMITER ;
