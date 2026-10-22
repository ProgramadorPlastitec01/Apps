-- =============================================================================
--  MIGRACION: convencion GV-001 y No. de parte en el R-PM-001
--  Motor: MariaDB 10.4.24
--
--  NO se toca ningun procedimiento que consulte `herramental_proceso`
--  (sp_slt_c_solicitudes, sp_slt_c_solicitudes_est, sp_pdt_*, etc.): el cruce
--  entre los dos esquemas queda exactamente como esta.
--
--  Tampoco se mueve ningun indice de los Object[] que ya consumen los Tags: la
--  columna nueva de sp_rgt_c_registro se agrega AL FINAL.
--
--  Se corre DESPUES de db/migracion_informe_gestion.sql. Es re-ejecutable.
-- =============================================================================

USE `solicitudes_proyectos`;

-- -----------------------------------------------------------------------------
--  1. GV-001: el plano de actividades generales de taller
--
--  El plano 221 concentra 4.060 solicitudes y 13.135 registros de trabajos
--  generales que se digitaban como 'N/A'. Se renombra, NO se fusiona ni se
--  borra: las solicitudes y los registros apuntan por id_plano, asi que el
--  historico queda intacto y el cambio es reversible.
--
--  El WHERE por nombre hace la sentencia idempotente: si ya quedo en GV-001 no
--  vuelve a tocar nada.
-- -----------------------------------------------------------------------------
UPDATE `plano`
   SET `nombre_plano` = 'GV-001',
       `tipo`         = 'Generales Varios'
 WHERE `id_plano` = 221
   AND `nombre_plano` = 'N/A';

-- Deja constancia del criterio para quien lea la tabla dentro de un anio.
UPDATE `plano`
   SET `numero_parte` = COALESCE(`numero_parte`, NULL)
 WHERE `nombre_plano` = 'GV-001';


-- -----------------------------------------------------------------------------
--  2. sp_rgt_c_registro  (REEMPLAZA al existente)
--
--  CAMBIO: agrega p.numero_parte como columna 17 (indice 16), AL FINAL.
--  Las columnas 0 a 15 conservan su posicion exacta porque Tag_registro las
--  lee por indice: mover una sola rompe toda la tabla del R-PM-001.
-- -----------------------------------------------------------------------------
DELIMITER $$

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
           r.tiempo, r.herramienta,
           -- Columna nueva, indice 16. Cadena vacia y no NULL para que la celda
           -- salga limpia en blanco cuando el plano aun no tiene numero.
           COALESCE(p.numero_parte, _latin1'') AS numero_parte
      FROM registro r
     INNER JOIN solicitud s ON r.id_solicitud = s.idSolicitud
     INNER JOIN maquina   m ON r.id_maquina   = m.id_maquina
     INNER JOIN plano     p ON r.id_plano     = p.id_plano
     WHERE r.id_solicitud = ids
     ORDER BY r.fecha_registro ASC;
END$$


-- -----------------------------------------------------------------------------
--  3. sp_slt_c_top_planos  (REEMPLAZA al existente)
--
--  CAMBIO: se retira el tratamiento especial que apartaba al plano 'N/A' del
--  ranking y lo mostraba como fila fija 'Sin plano asignado' con id -1. Con la
--  convencion GV-001 ese trabajo deja de ser "sin plano": es un plano
--  registrado, con su tipo y su numero de parte, y compite en el Top como
--  cualquier otro. La fila agregada 'Varios' (id 0) SI se conserva: sigue
--  siendo el resto agrupado, no un plano.
--
--  Las columnas y su orden no cambian.
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

    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_planos;
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_varios;

    -- DDL explicito para fijar el charset: si se dejaran inferir, los literales
    -- tomarian el de la conexion y el UNION final reventaria con "Illegal mix
    -- of collations" contra las columnas latin1 de las tablas.
    CREATE TEMPORARY TABLE tmp_rpt_planos (
        id_plano     INT            NOT NULL,
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
        (id_plano, nombre_plano, numero_parte, tipo, descripcion, solicitudes, piezas, horas, rn)
    SELECT
        x.id_plano, x.nombre_plano, x.numero_parte, x.tipo, x.descripcion,
        x.solicitudes, x.piezas, x.horas,
        ROW_NUMBER() OVER (ORDER BY x.horas DESC, x.nombre_plano ASC) AS rn
    FROM (
        SELECT
            pno.id_plano                                   AS id_plano,
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
    SELECT 0, 'Varios', '', 'Varios',
           CONCAT(COUNT(*), ' planos con menor participacion'),
           SUM(solicitudes), SUM(piezas), ROUND(SUM(horas), 2)
    FROM tmp_rpt_planos
    WHERE rn > 10
    -- HAVING y no WHERE: sin el filtro la agregacion devuelve una fila de NULLs
    -- y 'Varios' apareceria con horas vacias cuando hay 10 planos o menos.
    HAVING COUNT(*) > 0;

    SELECT id_plano, nombre_plano, numero_parte, tipo, descripcion,
           solicitudes, piezas, horas,
           COALESCE(ROUND(horas * 100 / NULLIF(v_total_horas, 0), 2), 0) AS porcentaje
    FROM (
        SELECT id_plano, nombre_plano, numero_parte, tipo, descripcion,
               solicitudes, piezas, horas, rn AS orden
        FROM tmp_rpt_planos
        WHERE rn <= 10

        UNION ALL

        -- orden 11 deja a 'Varios' al final sin depender de sus horas.
        SELECT id_plano, nombre_plano, numero_parte, tipo, descripcion,
               solicitudes, piezas, horas, 11 AS orden
        FROM tmp_rpt_varios
    ) AS u
    ORDER BY orden;

    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_planos;
    DROP TEMPORARY TABLE IF EXISTS tmp_rpt_varios;
END$$

DELIMITER ;


-- =============================================================================
--  ROLLBACK  (descomentar SOLO para revertir)
--  Los dos procedimientos EXISTIAN antes: revertir es restaurarlos desde
--  db/migracion_informe_gestion.sql y db/respaldo/rutinas_ANTES_migracion.sql,
--  no borrarlos.
-- =============================================================================
/*
UPDATE `plano` SET `nombre_plano` = 'N/A', `tipo` = 'N/A'
 WHERE `id_plano` = 221 AND `nombre_plano` = 'GV-001';
*/
