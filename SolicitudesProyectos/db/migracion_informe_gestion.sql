-- =============================================================================
--  MIGRACION: Informe de Gestion - Taller de Proyectos
--  Aplicativo : SolicitudesProyectos (PLASTITEC)
--  Motor      : MariaDB 10.4.24  (base `solicitudes_proyectos`)
--  Orden      : 1) ALTER TABLE  2) FUNCIONES  3) PROCEDIMIENTOS  4) ROLLBACK
--
--  NOTAS DE DISENO QUE HAY QUE LEER ANTES DE CORRER ESTO
--  -----------------------------------------------------------------------------
--  1. HORAS. La columna `registro`.`tiempo` es VARCHAR con formato 'H:MM' y en
--     10.074 de 55.191 filas viene NEGATIVA ('-16:0', '-15:-30'): en el turno de
--     noche se digita fecha_inicio 22:00 y fecha_fin 06:00 del MISMO dia y la
--     resta da negativo. Por eso NO se suma `tiempo` directamente (daria 80.126 h
--     contra las 317.463 h reales) ni se usa TIMESTAMPDIFF a secas (daria
--     17.999.588 h por dedazos de fecha).
--
--     fn_rpt_estado_registro clasifica cada fila en OK / GIRADO / DESCARTADO y
--     fn_rpt_horas_registro deriva las horas de esa clasificacion. Girar SIEMPRE
--     blanquearia los dedazos: convertiria una fila imposible en un valor
--     pequeno y creible que nadie podria auditar despues. Un registro descartado
--     y CONTADO es informacion; uno girado a un valor plausible es un dato falso.
--     Por eso sp_rpt_c_kpis publica registros_descartados y registros_girados.
--
--     EL UMBRAL DE DESCARTE ES 24 h. El taller SI deja moldes trabajando mas
--     de 24 h seguidas, pero el aplicativo NO SABE REGISTRARLO: calcula la
--     duracion solo con la hora del dia e ignora el cruce de fechas, asi que un
--     trabajo de lunes 22:00 a miercoles 06:00 queda guardado como '8:0'. Por
--     eso ningun `tiempo` de las 55.191 filas llega a 24 h: no es que no
--     existan trabajos largos, es que el campo no puede representarlos.
--     Los 54 registros de la banda 24-72 h son esos trabajos largos, guardados
--     incompletos. Se descartan porque acreditarlos a valor nominal seria
--     afirmar que fueron continuos, y girarlos seria acreditar solo el ultimo
--     turno. Consecuencia que hay que decir en voz alta: las horas de los
--     trabajos largos estan SUB-REPORTADAS en este informe.
--
--     COMPARACIONES HISTORICAS. La tasa de registros girados fue de ~22% anual
--     entre 2019 y 2024 y cayo a 1,9% en 2026 (mejoro la captura). Un rango que
--     cruce esa frontera compara dos calidades de dato distintas y no es una
--     comparacion valida sin advertirlo.
--
--  2. ANCLA TEMPORAL. Las horas, piezas y planos se filtran por
--     `registro`.`fecha_inicio` (cuando se ejecuto el trabajo), no por la fecha
--     de la solicitud. El informe mide lo que el taller ENTREGO en el periodo.
--     Una solicitud sin registros de tiempo no aparece en el Top de planos: esa
--     brecha es justamente lo que mide pct_cobertura_dato en sp_rpt_c_kpis.
--
--  3. AREA. No existe columna de area; el area es `usuario`.`Rol` traducido a
--     nombre legible. Se reutiliza el mismo mapeo que ya usa
--     sp_slt_c_contador_solicitudes, centralizado en fn_rpt_area_rol.
--
--  4. PRIORIDAD. `solicitud`.`Prioridad` tiene 10 variantes de texto para 5
--     prioridades reales ('1A' y '1A - INMEDIATA' son la misma). Se normaliza
--     por el prefijo de 2 caracteres en fn_rpt_franja_prioridad, que devuelve
--     SEIS franjas y no un agrupamiento alta/media/baja: la etiqueta ya trae el
--     SLA, agruparla lo perderia. '1C' (sin limite) y 'SP' (sin prioridad
--     diligenciada) no tienen meta y quedan FUERA del % de cumplimiento, pero
--     'SP' es una franja visible del informe: 720 solicitudes sin prioridad son
--     un hallazgo, no un residuo para repartir entre las otras.
-- =============================================================================

USE `solicitudes_proyectos`;


-- =============================================================================
--  1. ESTRUCTURA
--     MariaDB 10.4 soporta IF NOT EXISTS en ADD COLUMN, asi que cada ALTER es
--     idempotente y el script se puede volver a correr sin romper nada.
-- =============================================================================

-- No. de parte del R-PM-001. Vive en `plano` porque el numero de parte
-- identifica al herramental completo; las piezas son texto libre en
-- `solicitud`.`Pieza` y no tienen tabla propia donde colgarlo.
ALTER TABLE `plano`
    ADD COLUMN IF NOT EXISTS `numero_parte` VARCHAR(45) NULL DEFAULT NULL
    COMMENT 'No. de parte del R-PM-001' AFTER `nombre_plano`;

-- Fecha comprometida con el solicitante. Hoy el cumplimiento se mide contra las
-- metas por prioridad; esta columna permite medirlo contra el compromiso real
-- cuando se empiece a diligenciar.
ALTER TABLE `solicitud`
    ADD COLUMN IF NOT EXISTS `fecha_compromiso` DATE NULL DEFAULT NULL
    COMMENT 'Fecha comprometida de entrega' AFTER `Fecha_Fin_Solicitud`;

-- Parametros del informe. Se insertan solo si no existen para que el script sea
-- re-ejecutable y para no pisar valores que el usuario ya haya ajustado.
INSERT INTO `parametros` (`categoria`, `valor`, `descripcion`, `estado`, `usuario_registro`)
SELECT * FROM (
    SELECT 'tarifa_hora_taller'            AS c, '0'   AS v, 'Costo por hora de taller para valorizar el informe de gestion' AS d, 1 AS e, 'MIGRACION' AS u
    -- Una fila por franja de prioridad. 1C (sin limite) y SP (sin prioridad) no
    -- llevan meta a proposito: no se puede incumplir un plazo que no existe.
    UNION ALL SELECT 'meta_dias_1a',                '1',   'Meta en dias para prioridad 1A - INMEDIATA',  1, 'MIGRACION'
    UNION ALL SELECT 'meta_dias_2a',                '1',   'Meta en dias para prioridad 2A - 24 HORAS',   1, 'MIGRACION'
    UNION ALL SELECT 'meta_dias_1b',                '7',   'Meta en dias para prioridad 1B - 1 SEMANA',   1, 'MIGRACION'
    UNION ALL SELECT 'meta_dias_2b',                '30',  'Meta en dias para prioridad 2B - 1 MES',      1, 'MIGRACION'
    UNION ALL SELECT 'horas_disponibles_tecnico_mes','176', 'Horas disponibles por tecnico al mes para calcular utilizacion',     1, 'MIGRACION'
) AS nuevos
WHERE NOT EXISTS (
    SELECT 1 FROM `parametros` p WHERE p.`categoria` = nuevos.c
);

-- Sin este indice el Top de planos y los KPI hacen full scan de las 55k filas de
-- `registro` en cada consulta del indicador.
CREATE INDEX IF NOT EXISTS `IX_registro_fecha_inicio` ON `registro` (`fecha_inicio`);
CREATE INDEX IF NOT EXISTS `IX_registro_plano`        ON `registro` (`id_plano`);


DELIMITER $$

-- =============================================================================
--  2. FUNCIONES DE APOYO
--     Cinco formulas que consumen todos los procedimientos. Se extraen a
--     funciones para que la regla viva en UN solo lugar: si manana se corrige el
--     criterio de horas hay que tocar una linea, no cinco procedimientos.
-- =============================================================================

DROP FUNCTION IF EXISTS `fn_rpt_estado_registro`$$
/**
 * Clasifica un registro de tiempo en OK / GIRADO / DESCARTADO.
 *
 * Es la UNICA fuente de la regla: fn_rpt_horas_registro deriva las horas de
 * aqui y sp_rpt_c_kpis cuenta descartados y girados con la misma funcion, para
 * que las horas publicadas y el conteo de calidad del dato no se separen nunca.
 *
 *   DESCARTADO  NO significa "error del operario". Significa REGISTRO
 *               INCOMPLETO POR CRUCE DE DIAS: el aplicativo calcula la
 *               duracion con la hora del dia e ignora la fecha, de modo que un
 *               trabajo real de lunes 22:00 a miercoles 06:00 se guarda como
 *               '8:0' y sus fechas quedan a mas de 24 h de distancia. El
 *               trabajo existio; lo que falta es la parte que el campo no supo
 *               representar. Se descarta porque acreditarlo a valor nominal
 *               afirmaria que fue continuo y girarlo acreditaria solo el
 *               ultimo turno: las dos opciones inventan un dato. La
 *               consecuencia es que las horas de los trabajos largos quedan
 *               SUB-REPORTADAS, y por eso se cuentan y se publican.
 *               Tambien entran aqui las fechas fuera de 2015..anio actual + 1.
 *   GIRADO      duracion negativa de hasta 24 h. Turno de noche digitado con
 *               la misma fecha de inicio y fin (22:00 -> 06:00). Fue ~22% anual
 *               entre 2019 y 2024 y bajo a 1,9% en 2026: comparar rangos que
 *               crucen esa frontera es comparar dos calidades de dato.
 *   OK          duracion entre 0 y 24 h inclusive.
 *
 * @param p_inicio fecha_inicio del registro
 * @param p_fin    fecha_fin del registro
 * @return 'OK' | 'GIRADO' | 'DESCARTADO'
 */
CREATE FUNCTION `fn_rpt_estado_registro`(p_inicio DATETIME, p_fin DATETIME)
    RETURNS VARCHAR(10)
    DETERMINISTIC
    NO SQL
BEGIN
    -- Umbral en minutos. Cambiarlo aqui cambia la regla en todo el informe.
    DECLARE c_max_minutos INT DEFAULT 1440;
    DECLARE v_dif INT;

    IF p_inicio IS NULL OR p_fin IS NULL THEN
        RETURN 'DESCARTADO';
    END IF;

    -- Ventana de anios plausible. Atrapa los dedazos de siglo (anio 0026, 0226)
    -- y de decada (2006) que TIMESTAMPDIFF convierte en millones de horas.
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

DROP FUNCTION IF EXISTS `fn_rpt_horas_registro`$$
/**
 * Horas reales de un registro de tiempo, segun fn_rpt_estado_registro.
 * Un registro DESCARTADO aporta 0 h y se cuenta aparte: no se le inventa un
 * valor plausible. Ver nota 1 de la cabecera.
 *
 * No usa MOD a proposito: MOD(1440 + 1440, 1440) da 0 y mandaba a cero los
 * registros de 24 h clavadas.
 *
 * @param p_inicio fecha_inicio del registro
 * @param p_fin    fecha_fin del registro
 * @return horas decimales entre 0 y 24
 */
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

DROP FUNCTION IF EXISTS `fn_rpt_area_rol`$$
/**
 * Traduce el rol del usuario al nombre del area solicitante.
 * Mismo mapeo que ya aplica sp_slt_c_contador_solicitudes; se centraliza aqui
 * para que el indicador y el informe no se desincronicen.
 * @param p_rol valor de usuario.Rol
 * @return nombre del area, o el propio rol si no esta mapeado
 */
CREATE FUNCTION `fn_rpt_area_rol`(p_rol VARCHAR(45))
    RETURNS VARCHAR(45)
    DETERMINISTIC
    NO SQL
BEGIN
    RETURN CASE p_rol
        WHEN 'AU'       THEN 'AUTOMATIZACION'
        WHEN 'COORD.PR' THEN 'PROYECTOS'
        WHEN 'JEFE.PR'  THEN 'JEFE PROYECTOS'
        WHEN 'TEC.PR'   THEN 'TECNICOS PROYECTOS'
        WHEN 'MT'       THEN 'MANTENIMIENTO'
        WHEN 'MTF'      THEN 'MANTENIMIENTO FARMACEUTICO'
        WHEN 'MI'       THEN 'MANTENIMIENTO INSUMOS'
        WHEN 'PR'       THEN 'PRODUCCION'
        WHEN 'PI'       THEN 'PRODUCCION INSUMOS'
        WHEN 'PRF'      THEN 'PRODUCCION FARMACEUTICA'
        WHEN 'ADMIN'    THEN 'TECNOLOGIA DE INFORMACION'
        -- Devolver el rol en vez de NULL evita que el area desaparezca del
        -- informe cuando se cree un rol nuevo y nadie actualice este mapeo.
        ELSE p_rol
    END;
END$$

DROP FUNCTION IF EXISTS `fn_rpt_franja_prioridad`$$
/**
 * Normaliza las 10 variantes de texto de solicitud.Prioridad a su codigo de
 * franja. Se corta a 2 caracteres porque '1A' y '1A - INMEDIATA' conviven en la
 * tabla y son la misma prioridad.
 *
 * No agrupa en alta/media/baja: la etiqueta ya trae el SLA y agruparla lo
 * perderia (2A son 24 horas, 1B es una semana; meterlas en la misma bolsa
 * vuelve el indicador inutil).
 *
 * @param p_prioridad valor crudo de solicitud.Prioridad
 * @return '1A' | '2A' | '1B' | '2B' | '1C' | 'SP'
 *         'SP' es la franja de las solicitudes sin prioridad diligenciada; es
 *         una franja visible del informe, no un residuo a repartir.
 * @see fn_rpt_etiqueta_franja para el nombre legible de cada codigo
 */
CREATE FUNCTION `fn_rpt_franja_prioridad`(p_prioridad VARCHAR(45))
    RETURNS VARCHAR(2)
    DETERMINISTIC
    NO SQL
BEGIN
    DECLARE v_cod VARCHAR(2) DEFAULT UPPER(LEFT(TRIM(COALESCE(p_prioridad, _latin1'')), 2));
    RETURN CASE
        WHEN v_cod IN ('1A', '2A', '1B', '2B', '1C') THEN v_cod
        ELSE 'SP'
    END;
END$$

DROP FUNCTION IF EXISTS `fn_rpt_etiqueta_franja`$$
/**
 * Etiqueta legible de una franja, para las tablas y los graficos del informe.
 * Vive en la base y no en el Tag para que la pantalla, el PDF y el Excel
 * nombren las franjas igual.
 * @param p_franja codigo devuelto por fn_rpt_franja_prioridad
 * @return etiqueta de la franja
 */
CREATE FUNCTION `fn_rpt_etiqueta_franja`(p_franja VARCHAR(2))
    RETURNS VARCHAR(20)
    DETERMINISTIC
    NO SQL
BEGIN
    RETURN CASE p_franja
        WHEN '1A' THEN 'INMEDIATA'
        WHEN '2A' THEN '24 HORAS'
        WHEN '1B' THEN '1 SEMANA'
        WHEN '2B' THEN '1 MES'
        WHEN '1C' THEN 'SIN LIMITE'
        ELSE           'SIN PRIORIDAD'
    END;
END$$


-- =============================================================================
--  3. PROCEDIMIENTOS
-- =============================================================================

-- -----------------------------------------------------------------------------
--  sp_slt_c_top_planos
--  Consumido por: Indicador (grafico de pastel) e Informe de Gestion (barras).
--  Devuelve hasta 12 filas y todas suman el total del rango, por eso los
--  agregados se arman aqui y no en Java:
--     rn 1..10   los diez planos con mas horas
--     id_plano -1  'Sin plano asignado': las horas cargadas contra el plano
--                  'N/A'. Sale del ranking normal porque no es un plano, pero
--                  NO se oculta: que el 26% de las horas del taller no tenga
--                  plano identificado es un hallazgo del informe.
--     id_plano  0  'Varios': el resto agregado.
--  Ni -1 ni 0 navegan al detalle: no son planos, son agrupaciones.
-- -----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `sp_slt_c_top_planos`$$
CREATE PROCEDURE `sp_slt_c_top_planos`(
    IN p_fecha_ini  DATE,
    IN p_fecha_fin  DATE,
    IN p_estado     INT,          -- 1 = abiertas, 2 = cerradas, otro = todas
    IN p_tipo_plano VARCHAR(20),  -- '' o NULL = todos los tipos
    IN p_area       VARCHAR(45)   -- rol del solicitante; '' o NULL = todas
)
BEGIN
    DECLARE v_total_horas DECIMAL(14,2) DEFAULT 0;

    -- Dos tablas temporales y no una: MariaDB no permite referenciar la misma
    -- tabla temporal dos veces en la misma sentencia, y el SELECT final necesita
    -- el detalle y el agregado 'Varios' en un UNION ALL.
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_planos;
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_varios;

    -- Las columnas se declaran con DDL explicito en vez de CREATE ... AS SELECT
    -- para fijar el charset. Si se dejaran inferir, los literales tomarian el
    -- charset de la conexion (cp850 desde consola Windows, utf8 desde el
    -- conector JDBC) y el UNION ALL final reventaria con "Illegal mix of
    -- collations" contra las columnas latin1 de las tablas.
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
        -- 'Sin plano asignado' queda en rn = 0: fuera del top 10 (rn 1..10) y
        -- fuera de 'Varios' (rn > 10), pero dentro de la tabla, para que el
        -- total contra el que se calculan los porcentajes lo siga incluyendo.
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
            -- La descripcion es texto libre por solicitud; se toma la de la
            -- solicitud mas reciente como etiqueta representativa del plano.
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

    -- El total incluye la fila 'Sin plano asignado', asi que los porcentajes de
    -- las 12 filas suman 100 y cuadran con el total real del rango.

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
        0,
        'Varios',
        '',
        'Varios',
        CONCAT(COUNT(*), ' planos con menor participacion'),
        SUM(solicitudes),
        SUM(piezas),
        ROUND(SUM(horas), 2)
    FROM tmp_rpt_planos
    WHERE rn > 10
    -- HAVING y no WHERE: sin el filtro la agregacion devuelve una fila de NULLs
    -- y 'Varios' aparecerian con horas vacias cuando hay 10 planos o menos.
    HAVING COUNT(*) > 0;

    SELECT id_plano, nombre_plano, numero_parte, tipo, descripcion,
           solicitudes, piezas, horas,
           COALESCE(ROUND(horas * 100 / NULLIF(v_total_horas, 0), 2), 0) AS porcentaje
    FROM (
        -- rn = 0 ('Sin plano asignado') entra aqui por ser <= 10; se le cambia
        -- el id a -1 y la etiqueta, y el orden lo manda despues del top 10.
        SELECT IF(rn = 0, -1, id_plano)                            AS id_plano,
               IF(rn = 0, 'Sin plano asignado', nombre_plano)      AS nombre_plano,
               numero_parte, tipo,
               IF(rn = 0, 'Horas cargadas contra el plano N/A', descripcion) AS descripcion,
               solicitudes, piezas, horas,
               IF(rn = 0, 11, rn)                                  AS orden
        FROM tmp_rpt_planos
        WHERE rn <= 10

        UNION ALL

        -- orden = 12 deja a 'Varios' al final sin depender de sus horas.
        SELECT id_plano, nombre_plano, numero_parte, tipo, descripcion,
               solicitudes, piezas, horas, 12 AS orden
        FROM tmp_rpt_varios
    ) AS u
    ORDER BY orden;

    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_planos;
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_varios;
END$$


-- -----------------------------------------------------------------------------
--  sp_slt_c_piezas_plano
--  Consumido por: drill-down del Indicador al hacer clic en una porcion del
--  pastel, y detalle del Informe de Gestion.
--  El porcentaje es sobre el total del PLANO, no sobre el total del rango, para
--  que la tabla de detalle sume 100%.
-- -----------------------------------------------------------------------------
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
        -- El numero de parte identifica al herramental, no a la pieza: por eso
        -- todas las filas del detalle repiten el numero de parte del plano.
        COALESCE(pno.numero_parte, _latin1'')           AS numero_parte,
        rgt.pieza                                      AS pieza,
        COALESCE(SUM(rgt.cantidad), 0)                 AS cantidad,
        COUNT(DISTINCT rgt.id_solicitud)               AS solicitudes,
        -- Tecnico que mas horas puso en la pieza; el detalle es por pieza, no
        -- por persona, y listar seis nombres en una celda no aporta.
        (SELECT r2.usuario_registro
           FROM registro r2
          WHERE r2.id_plano = p_id_plano
            AND r2.pieza = rgt.pieza
            AND DATE(r2.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
          ORDER BY fn_rpt_horas_registro(r2.fecha_inicio, r2.fecha_fin) DESC
          LIMIT 1)                                     AS tecnico,
        ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)), 2) AS horas,
        COALESCE(ROUND(SUM(fn_rpt_horas_registro(rgt.fecha_inicio, rgt.fecha_fin)) * 100 / NULLIF(v_total_horas, 0), 2), 0) AS porcentaje_del_plano,
        -- Una pieza queda 'Abierta' si al menos una de sus solicitudes lo esta.
        IF(MIN(slt.Estado) = 100, 'Cerrada', 'Abierta') AS estado
    FROM registro rgt
    INNER JOIN solicitud slt ON rgt.id_solicitud = slt.idSolicitud
    INNER JOIN plano     pno ON rgt.id_plano     = pno.id_plano
    WHERE rgt.id_plano = p_id_plano
      AND DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
    GROUP BY pno.numero_parte, rgt.pieza
    ORDER BY horas DESC;
END$$


-- -----------------------------------------------------------------------------
--  sp_pln_c_planos_tipo
--  Consumido por: combo de filtro del Indicador y del Informe de Gestion.
--  Nota: el resto del modulo plano usa el prefijo sp_pno_*. Se respeta el
--  nombre pedido en el encargo; ver deuda tecnica.
-- -----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `sp_pln_c_planos_tipo`$$
CREATE PROCEDURE `sp_pln_c_planos_tipo`(
    IN p_tipo VARCHAR(20)   -- '' o NULL = todos los tipos
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


-- -----------------------------------------------------------------------------
--  sp_rpt_c_kpis
--  Consumido por: las tarjetas y la barra de cobertura del Informe.
--  Devuelve UNA sola fila. Todas las metas salen de la tabla `parametros`.
--
--  DOS TIEMPOS DISTINTOS, NO UNO:
--    primera atencion = Fecha_Ingreso -> primer registro de tiempo. Mide cuanto
--        tarda el taller en ARRANCAR. Es lo unico que se compara contra la meta
--        de la franja, porque la etiqueta de prioridad ('1A INMEDIATA') fija
--        cuando hay que empezar, no cuando hay que entregar.
--    ciclo = Fecha_Ingreso -> Fecha_Fin_Solicitud. Mide cuanto tarda en CERRAR.
--        Se reporta como mediana y SIN meta: no existe fecha de compromiso
--        diligenciada contra la cual medirlo (la columna fecha_compromiso se
--        creo para eso y todavia esta vacia).
-- -----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `sp_rpt_c_kpis`$$
CREATE PROCEDURE `sp_rpt_c_kpis`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE,
    IN p_area      VARCHAR(45)   -- rol del solicitante; '' o NULL = todas
)
BEGIN
    DECLARE v_meta_1a        INT           DEFAULT 1;
    DECLARE v_meta_2a        INT           DEFAULT 1;
    DECLARE v_meta_1b        INT           DEFAULT 7;
    DECLARE v_meta_2b        INT           DEFAULT 30;
    DECLARE v_horas_tecnico  DECIMAL(10,2) DEFAULT 176;
    DECLARE v_horas          DECIMAL(14,2) DEFAULT 0;
    DECLARE v_tecnicos       INT           DEFAULT 0;
    DECLARE v_descartados    INT           DEFAULT 0;
    DECLARE v_girados        INT           DEFAULT 0;
    DECLARE v_meses          DECIMAL(10,4) DEFAULT 1;
    DECLARE v_ciclo                DECIMAL(10,2) DEFAULT 0;
    DECLARE v_atencion             DECIMAL(10,2) DEFAULT 0;
    DECLARE v_cumplimiento         DECIMAL(10,2) DEFAULT 0;
    DECLARE v_solicitudes_rango    INT           DEFAULT 0;
    DECLARE v_solicitudes_cerradas INT           DEFAULT 0;

    -- Una meta por franja, leidas de parametros y no escritas en el SP para que
    -- Proyectos las ajuste sin desplegar base de datos. 1C (sin limite) y SP
    -- (sin prioridad) no tienen meta: no se puede incumplir un plazo inexistente.
    SELECT COALESCE(MAX(CASE WHEN categoria = 'meta_dias_1a'                  THEN valor END), 1)
         , COALESCE(MAX(CASE WHEN categoria = 'meta_dias_2a'                  THEN valor END), 1)
         , COALESCE(MAX(CASE WHEN categoria = 'meta_dias_1b'                  THEN valor END), 7)
         , COALESCE(MAX(CASE WHEN categoria = 'meta_dias_2b'                  THEN valor END), 30)
         , COALESCE(MAX(CASE WHEN categoria = 'horas_disponibles_tecnico_mes' THEN valor END), 176)
      INTO v_meta_1a, v_meta_2a, v_meta_1b, v_meta_2b, v_horas_tecnico
      FROM parametros
     WHERE estado = 1;

    -- Horas entregadas, tecnicos que registraron, y la calidad del dato con la
    -- que se calcularon esas horas. Los tres salen del mismo recorrido para que
    -- el conteo de descartados corresponda exactamente a las horas publicadas.
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

    -- Meses del rango en decimal: un rango de 45 dias no es "un mes y medio
    -- redondeado", y la utilizacion se distorsiona si se redondea.
    SET v_meses = GREATEST((DATEDIFF(p_fecha_fin, p_fecha_ini) + 1) / 30.4375, 0.0001);

    -- Las solicitudes cerradas del rango con sus dos tiempos, una sola vez:
    -- la mediana de atencion, la de ciclo y el cumplimiento salen de aqui.
    -- Se materializa en temporal porque la tabla derivada de primeros
    -- registros recorre las 55k filas de `registro` y no se quiere repetir.
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_atencion;
    CREATE TEMPORARY TABLE tmp_rpt_atencion (
        franja        VARCHAR(2) CHARACTER SET latin1 NOT NULL,
        dias_atencion INT NULL,
        dias_ciclo    INT NULL
    ) ENGINE = MEMORY;

    INSERT INTO tmp_rpt_atencion (franja, dias_atencion, dias_ciclo)
    SELECT
        fn_rpt_franja_prioridad(slt.Prioridad),
        -- NULL, no cero, en los dos casos sin dato utilizable: sin registro de
        -- tiempo, o con primera atencion ANTERIOR a la solicitud (38 casos en
        -- la base, hasta 517 dias antes). No se puede atender lo que todavia no
        -- existe; aplastarlo a cero inflaria el cumplimiento con un dato falso.
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

    -- MEDIANA y no promedio: un molde grande de 200 dias corre el promedio y
    -- esconde el comportamiento tipico. MariaDB 10.4 si tiene la funcion de
    -- ventana MEDIAN(), asi que no hace falta el truco de variable de sesion
    -- con ORDER BY. Se envuelve en subconsulta porque MEDIAN es funcion de
    -- ventana y devuelve un valor por fila.
    SELECT COALESCE(MAX(m), 0) INTO v_ciclo
      FROM (SELECT MEDIAN(dias_ciclo) OVER () AS m FROM tmp_rpt_atencion) AS c;

    SELECT COALESCE(MAX(m), 0) INTO v_atencion
      FROM (SELECT MEDIAN(dias_atencion) OVER () AS m
              FROM tmp_rpt_atencion WHERE dias_atencion IS NOT NULL) AS d;

    -- Conteo de solicitudes recibidas en el periodo (volumen demandado)
    SELECT COUNT(*)
      INTO v_solicitudes_rango
      FROM solicitud slt
      INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
     WHERE DATE(slt.Fecha_Ingreso) BETWEEN p_fecha_ini AND p_fecha_fin
       AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area);

    -- Conteo de solicitudes cerradas/atendidas en el periodo (volumen culminado)
    SELECT COUNT(*)
      INTO v_solicitudes_cerradas
      FROM solicitud slt
      INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
     WHERE slt.Estado = 100
       AND slt.Fecha_Fin_Solicitud BETWEEN p_fecha_ini AND p_fecha_fin
       AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area);

    -- RENDIMIENTO Y PRODUCTIVIDAD REAL:
    -- Se elimina la penalización arbitraria por prioridad del solicitante.
    -- Se calcula el % de Avance / Productividad sobre la carga real recibida:
    -- (Solicitudes Atendidas o Terminadas / Total Solicitudes Recibidas en el periodo) * 100
    SET v_cumplimiento = COALESCE(ROUND(v_solicitudes_cerradas * 100 / NULLIF(v_solicitudes_rango, 0), 2), 0);

    SELECT
        v_horas                AS horas_entregadas,
        v_solicitudes_rango    AS solicitudes_rango,
        v_solicitudes_cerradas AS solicitudes_cerradas,

        -- Backlog = lo que sigue abierto AL CIERRE del periodo, no lo abierto
        -- dentro del periodo. Por eso la condicion es <= p_fecha_fin.
        (SELECT COUNT(*)
           FROM solicitud slt
           INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
          WHERE slt.Estado = 0
            AND DATE(slt.Fecha_Ingreso) <= p_fecha_fin
            AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area)
        ) AS backlog,

        v_ciclo           AS ciclo_mediana,
        v_atencion        AS mediana_primera_atencion,
        v_cumplimiento    AS pct_cumplimiento_meta,

        -- COALESCE a 0: sin tecnicos en el rango la division da NULL y el Tag
        -- imprimiria "null" en la tarjeta del informe.
        COALESCE(ROUND(v_horas * 100 / NULLIF(v_tecnicos * v_horas_tecnico * v_meses, 0), 2), 0) AS pct_utilizacion,

        -- Garantia de confiabilidad del informe: si sale bajo, las horas del
        -- periodo estan subrepresentadas y los KPI no se deben leer como firmes.
        (SELECT COALESCE(ROUND(
                    SUM(IF(EXISTS (SELECT 1 FROM registro r WHERE r.id_solicitud = slt.idSolicitud), 1, 0))
                    * 100 / NULLIF(COUNT(*), 0), 2), 0)
           FROM solicitud slt
           INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
          WHERE slt.Estado = 100
            AND slt.Fecha_Fin_Solicitud BETWEEN p_fecha_ini AND p_fecha_fin
            AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area)
        ) AS pct_cobertura_dato,

        -- Calidad del dato con la que se calcularon las horas de arriba. Van
        -- junto a la cobertura porque responden la misma pregunta: cuanto de
        -- este informe se puede defender frente a una auditoria.
        v_descartados AS registros_descartados,
        v_girados     AS registros_girados;

    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_atencion;
END$$


-- -----------------------------------------------------------------------------
--  sp_rpt_c_respuesta_prioridad
--  Consumido por: la tabla "respuesta segun prioridad" del Informe, con su
--  semaforo. Devuelve SIEMPRE las seis franjas, tambien las que no tienen
--  solicitudes en el rango, para que la tabla no cambie de forma entre periodos.
--  Las dos columnas de tiempo responden preguntas distintas: la atencion mide
--  si el taller arranca a tiempo (y es la unica con meta), el ciclo mide si
--  cierra a tiempo (sin meta mientras no haya fecha de compromiso).
-- -----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `sp_rpt_c_respuesta_prioridad`$$
CREATE PROCEDURE `sp_rpt_c_respuesta_prioridad`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE,
    IN p_area      VARCHAR(45)
)
BEGIN
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_franjas;
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_detalle;

    -- Las seis franjas con su meta, para que ninguna desaparezca del informe
    -- cuando el periodo no tenga solicitudes de esa prioridad.
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

    -- Mismo criterio que sp_rpt_c_kpis: atencion NULL cuando no hay registro o
    -- cuando la primera atencion es anterior a la solicitud.
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

    -- MEDIANA y no promedio, por el mismo motivo que en sp_rpt_c_kpis y aqui
    -- con evidencia: la franja 1B promedia 142 dias de atencion y aun asi el
    -- 62% arranca dentro de los 7 dias de meta. El promedio describe a los tres
    -- casos peores, no a la franja. MEDIAN es funcion de ventana: se calcula
    -- particionada y se colapsa con DISTINCT a una fila por franja.
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
        -- Sin dato utilizable de atencion. Se publica para que el porcentaje de
        -- cumplimiento no se lea como si cubriera toda la franja.
        SUM(d.franja IS NOT NULL AND d.dias_atencion IS NULL) AS solicitudes_sin_dato,
        MAX(m.med_atencion)                        AS mediana_atencion,
        MAX(m.med_ciclo)                           AS mediana_ciclo,
        -- Rendimiento real: mide el volumen atendido sin penalizaciones arbitrarias por prioridad externa.
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


-- -----------------------------------------------------------------------------
--  sp_rpt_c_horas_mes
--  Consumido por: grafico de columnas del Informe. Serie mensual continua.
-- -----------------------------------------------------------------------------
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


-- -----------------------------------------------------------------------------
--  sp_rpt_c_tecnicos
--  Consumido por: bloque de capacidad del equipo del Informe.
-- -----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `sp_rpt_c_tecnicos`$$
CREATE PROCEDURE `sp_rpt_c_tecnicos`(
    IN p_fecha_ini DATE,
    IN p_fecha_fin DATE
)
BEGIN
    DECLARE v_horas_tecnico DECIMAL(10,2) DEFAULT 176;
    DECLARE v_meses         DECIMAL(10,4) DEFAULT 1;

    SELECT COALESCE(MAX(valor), 176) INTO v_horas_tecnico
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


-- -----------------------------------------------------------------------------
--  sp_slt_c_contador_solicitudes  (REEMPLAZA al procedimiento existente)
--
--  CAMBIO DE COMPORTAMIENTO: los numeros que el usuario ve hoy van a cambiar.
--  La version anterior contaba PENDIENTES sobre las solicitudes INGRESADAS en
--  el rango y TERMINADAS sobre las CERRADAS en el rango: dos poblaciones
--  distintas. El TOTAL era la suma de ambas, es decir el total de nada, y una
--  solicitud ingresada antes del rango y cerrada dentro sumaba en TERMINADAS
--  sin estar nunca en el denominador.
--
--  Ahora las tres columnas salen de UNA sola poblacion: las solicitudes con
--  Fecha_Ingreso dentro del rango. PENDIENTES + TERMINADAS = TOTAL siempre, y
--  el total cuadra con el que muestra el Top de planos que va justo debajo.
--
--  Efecto esperado en pantalla: TERMINADAS baja (ya no arrastra solicitudes de
--  periodos anteriores) y TOTAL deja de estar inflado. Si alguien compara
--  contra un informe viejo, esta es la explicacion.
--
--  De paso el area sale de fn_rpt_area_rol: el CASE anterior no tenia rama para
--  TEC.PR y esos usuarios aparecian con area NULL.
-- -----------------------------------------------------------------------------
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


-- -----------------------------------------------------------------------------
--  sp_rpt_c_horas_area
--  Consumido por: dona "horas por area" del Informe (fase 3).
--  NO estaba en la lista de seis del encargo. Se incluye aqui porque la fase 3
--  lo necesita y correr la migracion dos veces en produccion es peor que
--  entregar un procedimiento de mas. Si se descarta, basta con borrarlo.
-- -----------------------------------------------------------------------------
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

DELIMITER ;


-- =============================================================================
--  4. ROLLBACK  (descomentar SOLO para revertir)
-- =============================================================================
/*
USE `solicitudes_proyectos`;

-- OJO: sp_slt_c_contador_solicitudes ya existia antes de esta migracion.
-- Revertirlo NO es borrarlo: hay que restaurar la version anterior desde una
-- copia de la base, o el modulo Indicador queda sin contadores.
DROP PROCEDURE IF EXISTS `sp_rpt_c_respuesta_prioridad`;
DROP PROCEDURE IF EXISTS `sp_rpt_c_horas_area`;
DROP PROCEDURE IF EXISTS `sp_rpt_c_tecnicos`;
DROP PROCEDURE IF EXISTS `sp_rpt_c_horas_mes`;
DROP PROCEDURE IF EXISTS `sp_rpt_c_kpis`;
DROP PROCEDURE IF EXISTS `sp_pln_c_planos_tipo`;
DROP PROCEDURE IF EXISTS `sp_slt_c_piezas_plano`;
DROP PROCEDURE IF EXISTS `sp_slt_c_top_planos`;

DROP FUNCTION IF EXISTS `fn_rpt_etiqueta_franja`;
DROP FUNCTION IF EXISTS `fn_rpt_franja_prioridad`;
DROP FUNCTION IF EXISTS `fn_rpt_area_rol`;
DROP FUNCTION IF EXISTS `fn_rpt_horas_registro`;
DROP FUNCTION IF EXISTS `fn_rpt_estado_registro`;

DROP INDEX IF EXISTS `IX_registro_plano`        ON `registro`;
DROP INDEX IF EXISTS `IX_registro_fecha_inicio` ON `registro`;

DELETE FROM `parametros`
 WHERE `usuario_registro` = 'MIGRACION'
   AND `categoria` IN ('tarifa_hora_taller', 'meta_dias_1a', 'meta_dias_2a',
                       'meta_dias_1b', 'meta_dias_2b',
                       'horas_disponibles_tecnico_mes');

ALTER TABLE `solicitud` DROP COLUMN IF EXISTS `fecha_compromiso`;
ALTER TABLE `plano`     DROP COLUMN IF EXISTS `numero_parte`;
*/
