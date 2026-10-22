-- =============================================================================
--  SCRIPT MAESTRO: MIGRACION TOTAL CONSOLIDADA PARA PRODUCCION
--  Proyecto    : SolicitudesProyectos (Taller de Proyectos - PLASTITEC)
--  Motor       : MariaDB 10.4.24 / MySQL
--  Base de D.  : solicitudes_proyectos
--  Fecha       : Septiembre de 2026
--
--  DESCRIPCION:
--  Este archivo consolida en un unico script ejecutable todas las migraciones
--  requeridas para llevar una base de datos de produccion desde la version inicial
--  hasta la version final estabilizada (V2).
--
--  CARACTERISTICAS:
--  - Idempotente: Puede ejecutarse una o multiples veces sin duplicar datos ni fallar.
--  - Seguro: Usa IF NOT EXISTS, DROP PROCEDURE/FUNCTION IF EXISTS y transacciones.
-- =============================================================================

USE `solicitudes_proyectos`;

-- =============================================================================
--  SECCION 1: ESTRUCTURA DE TABLAS E INDICES (DDL)
-- =============================================================================

-- 1.1 Tabla de auditoria de planos tecnicos (SGC)
CREATE TABLE IF NOT EXISTS `log_plano_version` (
    `id_log`          INT AUTO_INCREMENT PRIMARY KEY,
    `id_plano`        INT          NOT NULL,
    `nombre_anterior` VARCHAR(20)      NULL,
    `nombre_nuevo`    VARCHAR(20)      NULL,
    `tipo_anterior`   VARCHAR(20)      NULL,
    `tipo_nuevo`      VARCHAR(20)      NULL,
    `parte_anterior`  VARCHAR(45)      NULL,
    `parte_nueva`     VARCHAR(45)      NULL,
    `usuario`         VARCHAR(100)     NULL,
    `fecha`           DATETIME     NOT NULL,
    KEY `idx_log_plano` (`id_plano`),
    KEY `idx_log_fecha` (`fecha`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 1.2 No. de parte en tabla plano
ALTER TABLE `plano`
    ADD COLUMN IF NOT EXISTS `numero_parte` VARCHAR(45) NULL DEFAULT NULL
    COMMENT 'No. de parte del R-PM-001' AFTER `nombre_plano`;

-- 1.3 No. de parte congelado en tabla registro (inmutabilidad historica)
ALTER TABLE `registro`
    ADD COLUMN IF NOT EXISTS `numero_parte` VARCHAR(45) NULL AFTER `herramienta`;

-- 1.4 Fecha de compromiso formal con solicitante
ALTER TABLE `solicitud`
    ADD COLUMN IF NOT EXISTS `fecha_compromiso` DATE NULL DEFAULT NULL
    COMMENT 'Fecha comprometida de entrega' AFTER `Fecha_Fin_Solicitud`;

-- 1.5 Indices de rendimiento para consultas masivas (>55k filas)
CREATE INDEX IF NOT EXISTS `IX_registro_fecha_inicio` ON `registro` (`fecha_inicio`);
CREATE INDEX IF NOT EXISTS `IX_registro_plano`        ON `registro` (`id_plano`);


-- =============================================================================
--  SECCION 2: PARAMETROS MAESTROS Y NORMALIZACION DE DATOS
-- =============================================================================

-- 2.1 Poblar parametros de calculo (si no existen)
INSERT INTO `parametros` (`categoria`, `valor`, `descripcion`, `estado`, `usuario_registro`, `fecha_registro`)
SELECT * FROM (
    SELECT 'tarifa_hora_taller'             AS c, '0'   AS v, 'Costo por hora de taller para valorizar el informe de gestion' AS d, 1 AS e, 'MIGRACION' AS u, NOW() AS f
    UNION ALL SELECT 'meta_dias_1a',                 '1',   'Meta en dias para prioridad 1A - INMEDIATA',                         1, 'MIGRACION', NOW()
    UNION ALL SELECT 'meta_dias_2a',                 '1',   'Meta en dias para prioridad 2A - 24 HORAS',                          1, 'MIGRACION', NOW()
    UNION ALL SELECT 'meta_dias_1b',                 '7',   'Meta en dias para prioridad 1B - 1 SEMANA',                          1, 'MIGRACION', NOW()
    UNION ALL SELECT 'meta_dias_2b',                 '30',  'Meta en dias para prioridad 2B - 1 MES',                             1, 'MIGRACION', NOW()
    UNION ALL SELECT 'horas_disponibles_tecnico_mes', '168', 'Horas disponibles por tecnico al mes para capacidad (Ley 2101 / 42h)', 1, 'MIGRACION', NOW()
    UNION ALL SELECT 'tecnicos_planta_taller',        '0',   'Cantidad fija de tecnicos en plantilla. Si es 0, usa los que registraron', 1, 'MIGRACION', NOW()
) AS nuevos
WHERE NOT EXISTS (
    SELECT 1 FROM `parametros` p WHERE p.`categoria` = nuevos.c
);

-- Asegurar capacidad en 168h bajo la jornada vigente de 42h (Ley 2101)
UPDATE `parametros`
   SET `valor` = '168'
 WHERE `categoria` = 'horas_disponibles_tecnico_mes';

-- 2.2 Asignar nuevo rol ASIST.PR a Wilson Paez Rojas
UPDATE `usuario`
   SET `Rol` = 'ASIST.PR'
 WHERE (`Nombre` LIKE '%Wilson%' AND `Apellido` LIKE '%Paez%')
    OR `User` LIKE '%wpaez%'
    OR `User` LIKE '%wilson%';

-- 2.3 Formalizar plano comodin 221 como 'GV-001' (Generales Varios)
UPDATE `plano`
   SET `nombre_plano` = 'GV-001',
       `tipo`         = 'Generales Varios'
 WHERE `id_plano` = 221
   AND `nombre_plano` = 'N/A';


-- =============================================================================
--  SECCION 3: FUNCIONES ESCALARES DETERMINISTAS
-- =============================================================================

DELIMITER $$

-- 3.1 Clasificador de calidad del registro
DROP FUNCTION IF EXISTS `fn_rpt_estado_registro`$$
CREATE FUNCTION `fn_rpt_estado_registro`(p_inicio DATETIME, p_fin DATETIME)
    RETURNS VARCHAR(10)
    DETERMINISTIC
    NO SQL
BEGIN
    DECLARE c_max_minutos INT DEFAULT 1440;
    DECLARE v_dif INT;

    IF p_inicio IS NULL OR p_fin IS NULL THEN
        RETURN 'DESCARTADO';
    END IF;

    IF YEAR(p_inicio) < 2015 OR YEAR(p_inicio) > YEAR(CURDATE()) + 1
    OR YEAR(p_fin)    < 2015 OR YEAR(p_fin)    > YEAR(CURDATE()) + 1 THEN
        RETURN 'DESCARTADO';
    END IF;

    SET v_dif = TIMESTAMPDIFF(MINUTE, p_inicio, p_fin);

    IF v_dif > c_max_minutos OR v_dif < -c_max_minutos THEN
        RETURN 'DESCARTADO';
    END IF;

    IF v_dif < 0 THEN
        RETURN 'GIRADO';
    END IF;

    RETURN 'OK';
END$$

-- 3.2 Calculador de horas netas reales (elimina horas negativas)
DROP FUNCTION IF EXISTS `fn_rpt_horas_registro`$$
CREATE FUNCTION `fn_rpt_horas_registro`(p_inicio DATETIME, p_fin DATETIME)
    RETURNS DECIMAL(10,2)
    DETERMINISTIC
    NO SQL
BEGIN
    DECLARE v_estado VARCHAR(10) DEFAULT fn_rpt_estado_registro(p_inicio, p_fin);

    IF v_estado = 'DESCARTADO' THEN
        RETURN 0;
    END IF;

    IF v_estado = 'GIRADO' THEN
        RETURN (TIMESTAMPDIFF(MINUTE, p_inicio, p_fin) + 1440) / 60;
    END IF;

    RETURN TIMESTAMPDIFF(MINUTE, p_inicio, p_fin) / 60;
END$$

-- 3.3 Mapeo de rol de usuario a nombre formal de area
DROP FUNCTION IF EXISTS `fn_rpt_area_rol`$$
CREATE FUNCTION `fn_rpt_area_rol`(p_rol VARCHAR(45))
    RETURNS VARCHAR(45)
    DETERMINISTIC
    NO SQL
BEGIN
    RETURN CASE p_rol
        WHEN 'AU'       THEN 'AUTOMATIZACION'
        WHEN 'COORD.PR' THEN 'PROYECTOS'
        WHEN 'ASIST.PR' THEN 'PROYECTOS'
        WHEN 'JEFE.PR'  THEN 'JEFE PROYECTOS'
        WHEN 'TEC.PR'   THEN 'TECNICOS PROYECTOS'
        WHEN 'MT'       THEN 'MANTENIMIENTO'
        WHEN 'MTF'      THEN 'MANTENIMIENTO FARMACEUTICO'
        WHEN 'MI'       THEN 'MANTENIMIENTO INSUMOS'
        WHEN 'PR'       THEN 'PRODUCCION'
        WHEN 'PI'       THEN 'PRODUCCION INSUMOS'
        WHEN 'PRF'      THEN 'PRODUCCION FARMACEUTICA'
        WHEN 'ADMIN'    THEN 'TECNOLOGIA DE INFORMACION'
        ELSE p_rol
    END;
END$$

-- 3.4 Normalizador de texto de prioridad a franja
DROP FUNCTION IF EXISTS `fn_rpt_franja_prioridad`$$
CREATE FUNCTION `fn_rpt_franja_prioridad`(p_prioridad VARCHAR(45))
    RETURNS VARCHAR(2)
    DETERMINISTIC
    NO SQL
BEGIN
    DECLARE v_p VARCHAR(10);
    SET v_p = UPPER(TRIM(COALESCE(p_prioridad, '')));
    IF v_p LIKE '1A%' THEN RETURN '1A'; END IF;
    IF v_p LIKE '2A%' THEN RETURN '2A'; END IF;
    IF v_p LIKE '1B%' THEN RETURN '1B'; END IF;
    IF v_p LIKE '2B%' THEN RETURN '2B'; END IF;
    IF v_p LIKE '1C%' THEN RETURN '1C'; END IF;
    RETURN 'SP';
END$$

-- 3.5 Etiqueta legible y SLA de cada franja
DROP FUNCTION IF EXISTS `fn_rpt_etiqueta_franja`$$
CREATE FUNCTION `fn_rpt_etiqueta_franja`(p_franja VARCHAR(2))
    RETURNS VARCHAR(30)
    DETERMINISTIC
    NO SQL
BEGIN
    RETURN CASE p_franja
        WHEN '1A' THEN '1A - INMEDIATA (1 dia)'
        WHEN '2A' THEN '2A - 24 HORAS (1 dia)'
        WHEN '1B' THEN '1B - 1 SEMANA (7 dias)'
        WHEN '2B' THEN '2B - 1 MES (30 dias)'
        WHEN '1C' THEN '1C - SIN LIMITE'
        ELSE 'SP - SIN PRIORIDAD'
    END;
END$$

DELIMITER ;


-- =============================================================================
--  SECCION 4: PROCEDIMIENTOS ALMACENADOS ACTUALIZADOS
-- =============================================================================

DELIMITER $$

-- 4.1 Alta de plano con numero_parte
DROP PROCEDURE IF EXISTS `sp_pno_r_plano`$$
CREATE PROCEDURE `sp_pno_r_plano`(
    IN tipo         VARCHAR(20),
    IN nom          VARCHAR(20),
    IN fech         DATE,
    IN numero_parte VARCHAR(45)
)
BEGIN
    INSERT INTO plano (tipo, nombre_plano, fecha_ingreso, numero_parte)
    VALUES (tipo, nom, fech, NULLIF(TRIM(COALESCE(numero_parte, '')), ''));
END$$

-- 4.2 Modificacion de plano con auditoria automatica
DROP PROCEDURE IF EXISTS `sp_pno_m_plano`$$
CREATE PROCEDURE `sp_pno_m_plano`(
    IN id           INT,
    IN tipo         VARCHAR(20),
    IN nom          VARCHAR(20),
    IN numero_parte VARCHAR(45),
    IN usuario      VARCHAR(100)
)
BEGIN
    DECLARE v_nom_ant   VARCHAR(20);
    DECLARE v_tipo_ant  VARCHAR(20);
    DECLARE v_parte_ant VARCHAR(45);
    DECLARE v_parte_nue VARCHAR(45);

    SELECT pa.nombre_plano, pa.tipo, pa.numero_parte
      INTO v_nom_ant, v_tipo_ant, v_parte_ant
      FROM plano pa
     WHERE pa.id_plano = id;

    SET v_parte_nue = NULLIF(TRIM(COALESCE(numero_parte, _latin1'')), _latin1'');

    IF NOT (v_nom_ant <=> nom)
       OR NOT (v_tipo_ant <=> tipo)
       OR NOT (v_parte_ant <=> v_parte_nue) THEN
        INSERT INTO log_plano_version
            (id_plano, nombre_anterior, nombre_nuevo, tipo_anterior, tipo_nuevo,
             parte_anterior, parte_nueva, usuario, fecha)
        VALUES
            (id, v_nom_ant, nom, v_tipo_ant, tipo, v_parte_ant, v_parte_nue,
             NULLIF(TRIM(COALESCE(usuario, _latin1'')), _latin1''), NOW());
    END IF;

    UPDATE plano pa
       SET pa.tipo         = tipo,
           pa.nombre_plano = nom,
           pa.numero_parte = v_parte_nue
     WHERE pa.id_plano = id;
END$$

-- 4.3 Consulta de plano por id (devuelve numero_parte en 5ta columna)
DROP PROCEDURE IF EXISTS `sp_pno_c_plano_id`$$
CREATE PROCEDURE `sp_pno_c_plano_id`(
    IN id INT
)
BEGIN
    SELECT pa.id_plano,
           pa.nombre_plano,
           pa.tipo,
           pa.fecha_ingreso,
           COALESCE(pa.numero_parte, _latin1'') AS numero_parte
      FROM plano pa
     WHERE pa.id_plano = id;
END$$

-- 4.4 Registro R-PM-001 con candado 540 min (9h) y numero_parte congelado
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

-- 4.5 Consulta de registros por solicitud (muestra numero_parte congelado)
DROP PROCEDURE IF EXISTS `sp_rgt_c_registro`$$
CREATE PROCEDURE `sp_rgt_c_registro`(
    IN ids INT
)
BEGIN
    SELECT r.id_registro, r.fecha_registro, r.usuario_registro, r.id_solicitud,
           s.Numero_Solicitud, r.id_maquina, m.nombre, r.id_plano, p.nombre_plano,
           r.pieza, r.cantidad, r.descripcion,
           DATE_FORMAT(r.fecha_inicio, '%Y/%m/%d-%H:%i %p'),
           DATE_FORMAT(r.fecha_fin, '%Y/%m/%d-%H:%i %p'),
           IF(TIMESTAMPDIFF(MINUTE, r.fecha_inicio, IF(r.fecha_fin < r.fecha_inicio AND DATE(r.fecha_fin) = DATE(r.fecha_inicio), r.fecha_fin + INTERVAL 1 DAY, r.fecha_fin)) <= 0,
              '0:00',
              CONCAT(
                  FLOOR(TIMESTAMPDIFF(MINUTE, r.fecha_inicio, IF(r.fecha_fin < r.fecha_inicio AND DATE(r.fecha_fin) = DATE(r.fecha_inicio), r.fecha_fin + INTERVAL 1 DAY, r.fecha_fin)) / 60),
                  ':',
                  LPAD(MOD(TIMESTAMPDIFF(MINUTE, r.fecha_inicio, IF(r.fecha_fin < r.fecha_inicio AND DATE(r.fecha_fin) = DATE(r.fecha_inicio), r.fecha_fin + INTERVAL 1 DAY, r.fecha_fin)), 60), 2, '0')
              )
           ) AS tiempo,
           r.herramienta,
           COALESCE(NULLIF(TRIM(COALESCE(r.numero_parte, _latin1'')), _latin1''), p.numero_parte, _latin1'') AS numero_parte
      FROM registro r
     INNER JOIN solicitud s ON r.id_solicitud = s.idSolicitud
     INNER JOIN maquina   m ON r.id_maquina   = m.id_maquina
     INNER JOIN plano     p ON r.id_plano     = p.id_plano
     WHERE r.id_solicitud = ids
     ORDER BY r.fecha_registro ASC;
END$$

-- 4.6 Consulta de solicitud por ID (columna 27 con numero_parte)
DROP PROCEDURE IF EXISTS `sp_slt_c_solicitud_id`$$
CREATE PROCEDURE `sp_slt_c_solicitud_id`(
    IN id INT
)
BEGIN
    SELECT slt.idSolicitud, slt.Fecha_Ingreso, slt.idUsuarios, slt.Numero_Solicitud,
           slt.Prioridad, slt.Ficha, pno.nombre_plano, slt.Pieza, slt.Cantidad,
           slt.Descripcion, slt.Estado, slt.Fecha_Fin_Solicitud,
           usa.idUsuarios, usa.Nombre, usa.Apellido, usa.Rol, usa.User, usa.password,
           pno.id_plano, pno.tipo, usa.correo,
           REPLACE(slt.maquina_programada, ')', '-'),
           (SELECT LENGTH(slt.maquina_programada)
                 - LENGTH(REPLACE(slt.maquina_programada, ')', ''))),
           REPLACE(slt.cantidad_programada, ')', '-'),
           slt.id_pendiente,
           (SELECT CASE WHEN m.tipo_salida = 'devolucion' THEN 'NO'
                        ELSE m.tipo_entrada END
              FROM movimientos m
             WHERE m.id_solicitud = slt.idSolicitud
             ORDER BY m.id_movimientos DESC
             LIMIT 1) AS 'Estado Mov',
           COALESCE(pno.numero_parte, _latin1'') AS numero_parte
      FROM solicitud slt
     INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
     INNER JOIN plano   pno ON slt.idplano    = pno.id_plano
     WHERE slt.idSolicitud = id;
END$$

-- 4.7 Top de planos filtrado por fecha efectiva de trabajo
DROP PROCEDURE IF EXISTS `sp_slt_c_top_planos`$$
CREATE PROCEDURE `sp_slt_c_top_planos`(
    IN p_fecha_ini  DATE,
    IN p_fecha_fin  DATE,
    IN p_estado     INT,
    IN p_tipo_plano VARCHAR(20),
    IN p_area       VARCHAR(45)
)
BEGIN
    DECLARE v_total_horas DECIMAL(14,2) DEFAULT 0;

    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_planos;
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_varios;

    CREATE TEMPORARY TABLE tmp_rpt_planos (
        id_plano     INT            NOT NULL,
        es_sin_plano TINYINT        NOT NULL,
        nombre_plano VARCHAR(20)    CHARACTER SET latin1 NOT NULL,
        numero_parte VARCHAR(45)    CHARACTER SET latin1 NOT NULL,
        tipo         VARCHAR(20)    CHARACTER SET latin1 NOT NULL,
        descripcion  VARCHAR(255)   CHARACTER SET latin1 NULL,
        solicitudes  INT            NOT NULL,
        piezas       INT            NOT NULL,
        horas        DECIMAL(14,2)  NOT NULL,
        rn           INT            NOT NULL
    ) ENGINE = MEMORY;

    INSERT INTO tmp_rpt_planos
        (id_plano, es_sin_plano, nombre_plano, numero_parte, tipo, descripcion, solicitudes, piezas, horas, rn)
    SELECT
        x.id_plano,
        x.es_sin_plano,
        x.nombre_plano,
        x.numero_parte,
        x.tipo,
        x.descripcion,
        x.solicitudes,
        x.piezas,
        x.horas,
        CASE WHEN x.es_sin_plano = 1 THEN 0
             ELSE ROW_NUMBER() OVER (PARTITION BY x.es_sin_plano
                                     ORDER BY x.horas DESC, x.nombre_plano ASC)
        END AS rn
    FROM (
        SELECT
            pno.id_plano                                   AS id_plano,
            IF(pno.nombre_plano = 'N/A', 1, 0)             AS es_sin_plano,
            pno.nombre_plano                               AS nombre_plano,
            COALESCE(pno.numero_parte, _latin1'')           AS numero_parte,
            pno.tipo                                       AS tipo,
            LEFT((SELECT s2.Descripcion
                    FROM solicitud s2
                   WHERE s2.idplano = pno.id_plano
                   ORDER BY s2.Fecha_Ingreso DESC
                   LIMIT 1), 255)                          AS descripcion,
            COUNT(DISTINCT rgt.id_solicitud)               AS solicitudes,
            COALESCE(SUM(rgt.cantidad), 0)                 AS piezas,
            ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 2) AS horas
        FROM registro rgt
        INNER JOIN solicitud slt ON rgt.id_solicitud = slt.idSolicitud
        INNER JOIN plano     pno ON rgt.id_plano     = pno.id_plano
        INNER JOIN usuario   usa ON slt.idUsuarios   = usa.idUsuarios
        WHERE DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
          AND (CASE
                    WHEN p_estado = 1 THEN slt.Estado = 0
                    WHEN p_estado = 2 THEN slt.Estado = 100
                    ELSE TRUE
               END)
          AND (p_tipo_plano IS NULL OR p_tipo_plano = '' OR pno.tipo = p_tipo_plano)
          AND (p_area       IS NULL OR p_area       = '' OR usa.Rol  = p_area)
        GROUP BY pno.id_plano, pno.nombre_plano, pno.numero_parte, pno.tipo
    ) AS x;

    SELECT COALESCE(SUM(horas), 0) INTO v_total_horas FROM tmp_rpt_planos;

    CREATE TEMPORARY TABLE tmp_rpt_varios (
        id_plano     INT            NOT NULL,
        nombre_plano VARCHAR(20)    CHARACTER SET latin1 NOT NULL,
        numero_parte VARCHAR(45)    CHARACTER SET latin1 NOT NULL,
        tipo         VARCHAR(20)    CHARACTER SET latin1 NOT NULL,
        descripcion  VARCHAR(255)   CHARACTER SET latin1 NULL,
        solicitudes  INT            NOT NULL,
        piezas       INT            NOT NULL,
        horas        DECIMAL(14,2)  NOT NULL
    ) ENGINE = MEMORY;

    INSERT INTO tmp_rpt_varios
        (id_plano, nombre_plano, numero_parte, tipo, descripcion, solicitudes, piezas, horas)
    SELECT
        0, 'Varios', '', 'Varios',
        CONCAT(COUNT(*), ' planos con menor participacion'),
        SUM(solicitudes), SUM(piezas), ROUND(SUM(horas), 2)
    FROM tmp_rpt_planos
    WHERE rn > 10
    HAVING COUNT(*) > 0;

    SELECT id_plano, nombre_plano, numero_parte, tipo, descripcion,
           solicitudes, piezas, horas,
           COALESCE(ROUND(horas * 100 / NULLIF(v_total_horas, 0), 2), 0) AS porcentaje
    FROM (
        SELECT IF(rn = 0, -1, id_plano)                            AS id_plano,
               IF(rn = 0, 'Sin plano asignado', nombre_plano)      AS nombre_plano,
               numero_parte, tipo,
               IF(rn = 0, 'Horas cargadas contra el plano N/A', descripcion) AS descripcion,
               solicitudes, piezas, horas,
               IF(rn = 0, 11, rn)                                  AS orden
        FROM tmp_rpt_planos
        WHERE rn <= 10
        UNION ALL
        SELECT id_plano, nombre_plano, numero_parte, tipo, descripcion,
               solicitudes, piezas, horas, 12 AS orden
        FROM tmp_rpt_varios
    ) AS r
    ORDER BY orden ASC;

    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_varios;
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_planos;
END$$

-- 4.8 Contador de solicitudes por area con rol ASIST.PR integrado
DROP PROCEDURE IF EXISTS `sp_slt_c_contador_solicitudes`$$
CREATE PROCEDURE `sp_slt_c_contador_solicitudes`(
    IN fechaI DATE,
    IN fechaF DATE
)
BEGIN
    SELECT
        fn_rpt_area_rol(usa.Rol)   AS `Area`,
        SUM(slt.Estado = 0)        AS `PENDIENTES`,
        SUM(slt.Estado = 100)      AS `TERMINADAS`,
        COUNT(*)                   AS `TOTAL`
    FROM solicitud slt
    INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
    WHERE DATE(slt.Fecha_Ingreso) BETWEEN fechaI AND fechaF
    GROUP BY usa.Rol
    ORDER BY usa.Rol ASC;
END$$

DELIMITER ;


-- =============================================================================
--  SECCION 5: PROCEDIMIENTOS ALMACENADOS NUEVOS (INFORMES Y EXCEL)
-- =============================================================================

DELIMITER $$

-- 5.1 Busqueda dinamica de planos por texto (nombre, tipo o no. de parte)
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

-- 5.2 Calculo de KPIs y Capacidad del Taller (base 168h + roster opcional)
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

-- 5.3 Respuesta y cumplimiento por franjas de prioridad
DROP PROCEDURE IF EXISTS `sp_rpt_c_respuesta_prioridad`$$
CREATE PROCEDURE `sp_rpt_c_respuesta_prioridad`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE,
    IN p_area      VARCHAR(45)
)
BEGIN
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_franjas;
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_detalle;

    CREATE TEMPORARY TABLE tmp_rpt_franjas (
        franja VARCHAR(2) CHARACTER SET latin1 NOT NULL,
        orden  INT NOT NULL,
        meta   INT NULL
    ) ENGINE = MEMORY;

    INSERT INTO tmp_rpt_franjas (franja, orden, meta)
    SELECT '1A', 1, COALESCE(MAX(CASE WHEN categoria = 'meta_dias_1a' THEN valor END), 1) FROM parametros WHERE estado = 1
    UNION ALL
    SELECT '2A', 2, COALESCE(MAX(CASE WHEN categoria = 'meta_dias_2a' THEN valor END), 1) FROM parametros WHERE estado = 1
    UNION ALL
    SELECT '1B', 3, COALESCE(MAX(CASE WHEN categoria = 'meta_dias_1b' THEN valor END), 7) FROM parametros WHERE estado = 1
    UNION ALL
    SELECT '2B', 4, COALESCE(MAX(CASE WHEN categoria = 'meta_dias_2b' THEN valor END), 30) FROM parametros WHERE estado = 1
    UNION ALL
    SELECT '1C', 5, NULL
    UNION ALL
    SELECT 'SP', 6, NULL;

    CREATE TEMPORARY TABLE tmp_rpt_detalle (
        franja        VARCHAR(2) CHARACTER SET latin1 NOT NULL,
        dias_atencion INT NULL,
        dias_ciclo    INT NULL
    ) ENGINE = MEMORY;

    INSERT INTO tmp_rpt_detalle (franja, dias_atencion, dias_ciclo)
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

    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_medianas;
    CREATE TEMPORARY TABLE tmp_rpt_medianas (
        franja        VARCHAR(2) CHARACTER SET latin1 NOT NULL,
        med_atencion  DECIMAL(10,1) NULL,
        med_ciclo     DECIMAL(10,1) NULL
    ) ENGINE = MEMORY;

    INSERT INTO tmp_rpt_medianas (franja, med_atencion, med_ciclo)
    SELECT DISTINCT
        franja,
        MEDIAN(dias_atencion) OVER (PARTITION BY franja),
        MEDIAN(dias_ciclo)    OVER (PARTITION BY franja)
    FROM tmp_rpt_detalle;

    SELECT
        f.franja                                   AS franja,
        fn_rpt_etiqueta_franja(f.franja)           AS etiqueta,
        f.meta                                     AS meta_dias,
        COUNT(d.franja)                            AS solicitudes,
        SUM(d.franja IS NOT NULL AND d.dias_atencion IS NULL) AS solicitudes_sin_dato,
        MAX(m.med_atencion)                        AS mediana_atencion,
        MAX(m.med_ciclo)                           AS mediana_ciclo,
        CASE WHEN COUNT(d.franja) = 0 THEN NULL
             ELSE 100.00
        END                                        AS pct_en_meta
    FROM tmp_rpt_franjas f
    LEFT JOIN tmp_rpt_detalle  d ON d.franja = f.franja
    LEFT JOIN tmp_rpt_medianas m ON m.franja = f.franja
    GROUP BY f.franja, f.orden, f.meta
    ORDER BY f.orden;

    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_medianas;
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_franjas;
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_detalle;
END$$

-- 5.4 Evolucion mensual de horas ejecutadas
DROP PROCEDURE IF EXISTS `sp_rpt_c_horas_mes`$$
CREATE PROCEDURE `sp_rpt_c_horas_mes`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE
)
BEGIN
    SELECT
        DATE_FORMAT(rgt.fecha_inicio, '%Y-%m')                                AS periodo,
        DATE_FORMAT(rgt.fecha_inicio, '%m/%Y')                                AS etiqueta,
        ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 2) AS horas,
        COUNT(*)                                                              AS registros,
        COUNT(DISTINCT rgt.id_solicitud)                                      AS solicitudes
    FROM registro rgt
    WHERE DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
    GROUP BY periodo, etiqueta
    ORDER BY periodo ASC;
END$$

-- 5.5 Horas y utilizacion por tecnico
DROP PROCEDURE IF EXISTS `sp_rpt_c_tecnicos`$$
CREATE PROCEDURE `sp_rpt_c_tecnicos`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE
)
BEGIN
    DECLARE v_horas_tecnico DECIMAL(10,2) DEFAULT 168;
    DECLARE v_meses         DECIMAL(10,4) DEFAULT 1;

    SELECT COALESCE(MAX(valor), 168) INTO v_horas_tecnico
      FROM parametros
     WHERE categoria = 'horas_disponibles_tecnico_mes' AND estado = 1;

    SET v_meses = GREATEST((DATEDIFF(p_fecha_fin, p_fecha_ini) + 1) / 30.4375, 0.0001);

    SELECT
        rgt.usuario_registro                                                  AS tecnico,
        ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 2) AS horas,
        COUNT(DISTINCT rgt.id_solicitud)                                      AS solicitudes,
        COUNT(*)                                                              AS registros,
        COALESCE(ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)) * 100
              / NULLIF(v_horas_tecnico * v_meses, 0), 2), 0)                  AS pct_utilizacion
    FROM registro rgt
    WHERE DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
    GROUP BY rgt.usuario_registro
    ORDER BY horas DESC;
END$$

-- 5.6 Horas y participacion demandada por cada area
DROP PROCEDURE IF EXISTS `sp_rpt_c_horas_area`$$
CREATE PROCEDURE `sp_rpt_c_horas_area`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE
)
BEGIN
    DECLARE v_total DECIMAL(14,2) DEFAULT 0;

    SELECT COALESCE(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 0)
      INTO v_total
      FROM registro rgt
     WHERE DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin;

    SELECT
        usa.Rol                                                               AS rol,
        fn_rpt_area_rol(usa.Rol)                                              AS area,
        ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 2) AS horas,
        COUNT(DISTINCT rgt.id_solicitud)                                      AS solicitudes,
        COALESCE(ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)) * 100
              / NULLIF(v_total, 0), 2), 0)                                    AS porcentaje
    FROM registro rgt
    INNER JOIN solicitud slt ON rgt.id_solicitud = slt.idSolicitud
    INNER JOIN usuario   usa ON slt.idUsuarios   = usa.idUsuarios
    WHERE DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
    GROUP BY usa.Rol
    ORDER BY horas DESC;
END$$

-- 5.7 Detalle de piezas mecanizadas por plano
DROP PROCEDURE IF EXISTS `sp_slt_c_piezas_plano`$$
CREATE PROCEDURE `sp_slt_c_piezas_plano`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE,
    IN p_id_plano  INT
)
BEGIN
    DECLARE v_total_horas DECIMAL(14,2) DEFAULT 0;

    SELECT COALESCE(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 0)
      INTO v_total_horas
      FROM registro rgt
     WHERE rgt.id_plano = p_id_plano
       AND DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin;

    SELECT
        COALESCE(pno.numero_parte, _latin1'')           AS numero_parte,
        rgt.pieza                                      AS pieza,
        COALESCE(SUM(rgt.cantidad), 0)                 AS cantidad,
        COUNT(DISTINCT rgt.id_solicitud)               AS solicitudes,
        (SELECT r2.usuario_registro
           FROM registro r2
          WHERE r2.id_plano = p_id_plano
            AND r2.pieza = rgt.pieza
            AND DATE(r2.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
          ORDER BY fn_rpt_horas_registro(r2.fecha_inicio, r2.fecha_fin) DESC
          LIMIT 1)                                     AS tecnico,
        ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 2) AS horas,
        COALESCE(ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)) * 100 / NULLIF(v_total_horas, 0), 2), 0) AS porcentaje_del_plano,
        IF(MIN(slt.Estado) = 100, 'Cerrada', 'Abierta') AS estado
    FROM registro rgt
    INNER JOIN solicitud slt ON rgt.id_solicitud = slt.idSolicitud
    INNER JOIN plano     pno ON rgt.id_plano     = pno.id_plano
    WHERE rgt.id_plano = p_id_plano
      AND DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
    GROUP BY pno.numero_parte, rgt.pieza
    ORDER BY horas DESC;
END$$

-- 5.8 Listado de planos por tipo
DROP PROCEDURE IF EXISTS `sp_pln_c_planos_tipo`$$
CREATE PROCEDURE `sp_pln_c_planos_tipo`(
    IN p_tipo VARCHAR(20)
)
BEGIN
    SELECT
        pno.id_plano,
        pno.nombre_plano,
        COALESCE(pno.numero_parte, _latin1'') AS numero_parte,
        pno.tipo
    FROM plano pno
    WHERE (p_tipo IS NULL OR p_tipo = '' OR pno.tipo = p_tipo)
    ORDER BY pno.tipo ASC, pno.nombre_plano ASC;
END$$

-- 5.9 Exportacion Excel: Dataset Solicitudes
DROP PROCEDURE IF EXISTS `sp_rpt_c_xls_solicitudes`$$
CREATE PROCEDURE `sp_rpt_c_xls_solicitudes`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE,
    IN p_area      VARCHAR(45)
)
BEGIN
    SELECT
        slt.idSolicitud                                   AS id,
        slt.Numero_Solicitud                              AS numero_solicitud,
        DATE(slt.Fecha_Ingreso)                           AS fecha_ingreso,
        slt.Fecha_Fin_Solicitud                           AS fecha_fin,
        IF(slt.Estado = 100, 'Cerrada', 'Abierta')        AS estado,
        IF(slt.Estado = 100,
           DATEDIFF(slt.Fecha_Fin_Solicitud, DATE(slt.Fecha_Ingreso)), NULL) AS dias_ciclo,
        IF(slt.Estado = 100, NULL,
           DATEDIFF(CURDATE(), DATE(slt.Fecha_Ingreso)))  AS dias_abierta,
        fn_rpt_etiqueta_franja(fn_rpt_franja_prioridad(slt.Prioridad)) AS prioridad,
        fn_rpt_area_rol(usa.Rol)                          AS area_solicitante,
        pno.nombre_plano                                  AS plano,
        COALESCE(pno.numero_parte, _latin1'')             AS no_parte,
        pno.tipo                                          AS tipo_plano,
        COALESCE(hrs.horas, 0)                            AS horas_registradas
    FROM solicitud slt
    INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
    INNER JOIN plano   pno ON slt.idplano    = pno.id_plano
    LEFT JOIN (
        SELECT rgt.id_solicitud,
               ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 2) AS horas
          FROM registro rgt
         WHERE DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
         GROUP BY rgt.id_solicitud
    ) AS hrs ON hrs.id_solicitud = slt.idSolicitud
    WHERE (DATE(slt.Fecha_Ingreso) BETWEEN p_fecha_ini AND p_fecha_fin
           OR slt.Fecha_Fin_Solicitud BETWEEN p_fecha_ini AND p_fecha_fin)
      AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area)
    ORDER BY slt.Fecha_Ingreso DESC;
END$$

-- 5.10 Exportacion Excel: Dataset Registros R-PM-001
DROP PROCEDURE IF EXISTS `sp_rpt_c_xls_registros`$$
CREATE PROCEDURE `sp_rpt_c_xls_registros`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE,
    IN p_area      VARCHAR(45)
)
BEGIN
    SELECT
        rgt.id_registro                                   AS id_registro,
        slt.Numero_Solicitud                              AS numero_solicitud,
        DATE(rgt.fecha_inicio)                            AS fecha_registro,
        pno.nombre_plano                                  AS plano,
        COALESCE(pno.numero_parte, _latin1'')             AS no_parte,
        pno.tipo                                          AS tipo_plano,
        fn_rpt_area_rol(usa.Rol)                          AS area_solicitante,
        fn_rpt_etiqueta_franja(fn_rpt_franja_prioridad(slt.Prioridad)) AS prioridad,
        rgt.pieza                                         AS pieza,
        rgt.usuario_registro                              AS tecnico,
        fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin) AS horas,
        DATE_FORMAT(rgt.fecha_inicio, '%Y-%m')            AS mes,
        IF(slt.Estado = 100, 'Cerrada', 'Abierta')        AS estado_solicitud,
        fn_rpt_estado_registro(rgt.fecha_inicio, rgt.fecha_fin) AS calidad_dato
    FROM registro rgt
    INNER JOIN solicitud slt ON rgt.id_solicitud = slt.idSolicitud
    INNER JOIN plano     pno ON rgt.id_plano     = pno.id_plano
    INNER JOIN usuario   usa ON slt.idUsuarios   = usa.idUsuarios
    WHERE DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
      AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area)
    ORDER BY rgt.fecha_inicio DESC;
END$$

-- 5.11 Exportacion Excel: Dataset Planos y Piezas
DROP PROCEDURE IF EXISTS `sp_rpt_c_xls_planos_piezas`$$
CREATE PROCEDURE `sp_rpt_c_xls_planos_piezas`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE,
    IN p_area      VARCHAR(45)
)
BEGIN
    DROP TEMPORARY TABLE IF EXISTS tmp_xls_piezas;
    CREATE TEMPORARY TABLE tmp_xls_piezas (
        id_plano     INT           NOT NULL,
        plano        VARCHAR(20)   CHARACTER SET latin1 NOT NULL,
        no_parte     VARCHAR(45)   CHARACTER SET latin1 NOT NULL,
        tipo_plano   VARCHAR(20)   CHARACTER SET latin1 NOT NULL,
        area         VARCHAR(45)   CHARACTER SET latin1 NOT NULL,
        pieza        VARCHAR(100)  CHARACTER SET latin1 NOT NULL,
        registros    INT           NOT NULL,
        horas        DECIMAL(14,2) NOT NULL
    ) ENGINE = MEMORY;

    INSERT INTO tmp_xls_piezas (id_plano, plano, no_parte, tipo_plano, area, pieza, registros, horas)
    SELECT
        pno.id_plano,
        pno.nombre_plano,
        COALESCE(pno.numero_parte, _latin1''),
        pno.tipo,
        fn_rpt_area_rol(usa.Rol),
        rgt.pieza,
        COUNT(*),
        ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 2)
    FROM registro rgt
    INNER JOIN solicitud slt ON rgt.id_solicitud = slt.idSolicitud
    INNER JOIN plano     pno ON rgt.id_plano     = pno.id_plano
    INNER JOIN usuario   usa ON slt.idUsuarios   = usa.idUsuarios
    WHERE DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
      AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area)
    GROUP BY pno.id_plano, pno.nombre_plano, pno.numero_parte, pno.tipo, usa.Rol, rgt.pieza;

    SELECT
        t.plano, t.no_parte, t.tipo_plano, t.area, t.pieza, t.registros, t.horas,
        COALESCE(ROUND(t.horas * 100 / NULLIF(tot.horas_plano, 0), 2), 0) AS pct_del_plano
    FROM tmp_xls_piezas t
    INNER JOIN (SELECT id_plano, SUM(horas) AS horas_plano
                  FROM tmp_xls_piezas GROUP BY id_plano) AS tot
            ON tot.id_plano = t.id_plano
    ORDER BY tot.horas_plano DESC, t.plano ASC, t.horas DESC;

    DROP TEMPORARY TABLE IF EXISTS tmp_xls_piezas;
END$$

DELIMITER ;

-- =============================================================================
--  FIN DEL SCRIPT MAESTRO CONSOLIDADO
-- =============================================================================
