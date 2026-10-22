-- =============================================================================
--  MIGRACION: candado de turno laboral (max 8 h) en sp_rgt_r_registro
--  Motor: MariaDB 10.4.24
--
--  REGLA DE NEGOCIO
--  Un control del R-PM-001 no puede representar mas de un turno (8 h = 480 min)
--  ni una duracion <= 0. Si excede, se aborta con SIGNAL SQLSTATE '45000' y el
--  operario debe partir el trabajo largo en controles separados.
--
--  CONTEXTO (leer): ~6% de los registros historicos superan 8 h (trabajos que
--  cruzan turnos). El candado aplica SOLO a inserciones NUEVAS; no toca el
--  historico ni la reporteria, que sigue clasificando por fecha_inicio/fecha_fin.
--
--  Reemplaza la version de db/migracion_tiempo_registro.sql: conserva identica
--  la logica de `tiempo` (cruce de medianoche del mismo dia, horas acumuladas)
--  y solo agrega los dos SIGNAL antes del INSERT. Re-ejecutable.
-- =============================================================================

USE `solicitudes_proyectos`;

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
    IN horaF DATETIME
)
BEGIN
    DECLARE v_fin DATETIME;
    DECLARE v_min INT;

    -- Cruce de medianoche del mismo dia -> se asume un solo salto (+1 dia).
    SET v_fin = IF(horaF < horaI AND DATE(horaF) = DATE(horaI),
                   horaF + INTERVAL 1 DAY, horaF);
    SET v_min = TIMESTAMPDIFF(MINUTE, horaI, v_fin);

    -- Candado de turno laboral: duracion valida y de a lo sumo 8 horas.
    IF v_min <= 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: La fecha final debe ser posterior a la fecha inicial.';
    END IF;
    IF v_min > 480 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: No se puede registrar mas de 8 horas (un turno laboral). Registre controles separados.';
    END IF;

    INSERT INTO registro
        (usuario_registro, id_solicitud, id_maquina, id_plano, pieza,
         cantidad, descripcion, fecha_inicio, fecha_fin, tiempo)
    VALUES
        (usaR, ids, idm, idp, piz, cant, des,
         horaI,          -- fecha_inicio: cruda
         horaF,          -- fecha_fin:    cruda (la clasifica el reporte)
         CONCAT(FLOOR(v_min / 60), ':', LPAD(MOD(v_min, 60), 2, '0')));
END$$

DELIMITER ;
