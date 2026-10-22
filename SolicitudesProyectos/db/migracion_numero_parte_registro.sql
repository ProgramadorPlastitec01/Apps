-- =============================================================================
--  MIGRACION: congelar No. de parte en el registro (inmutabilidad historica)
--  Motor: MariaDB 10.4.24
--
--  QUE Y POR QUE
--  El R-PM-001 es un registro de calidad (SGC): debe reflejar la parte que se
--  intervino EN EL MOMENTO del control, no el numero de parte que el plano
--  tenga hoy. Hasta ahora el No. de parte se resolvia por JOIN a plano al
--  imprimir, asi que editar el plano cambiaba el historico. Se corrige:
--    1. registro gana su propia columna numero_parte (el valor CONGELADO).
--    2. sp_rgt_r_registro recibe y guarda ese valor al crear el control.
--    3. sp_rgt_c_registro muestra el valor congelado; si el registro es viejo
--       (anterior a esta migracion, sin valor) cae al numero actual del plano.
--
--  El candado de turno (max 8 h) de db/migracion_turno_8h.sql se conserva.
--  Re-ejecutable.
-- =============================================================================

USE `solicitudes_proyectos`;

-- 1. Columna congelada. NULL para los ~55k registros historicos (caen al JOIN).
ALTER TABLE `registro`
    ADD COLUMN IF NOT EXISTS `numero_parte` VARCHAR(45) NULL AFTER `herramienta`;

DELIMITER $$

-- 2. sp_rgt_r_registro: 10mo parametro npart, se guarda tal cual (congelado).
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

    -- Candado de turno laboral (ver db/migracion_turno_8h.sql).
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
         cantidad, descripcion, fecha_inicio, fecha_fin, tiempo, numero_parte)
    VALUES
        (usaR, ids, idm, idp, piz, cant, des, horaI, horaF,
         CONCAT(FLOOR(v_min / 60), ':', LPAD(MOD(v_min, 60), 2, '0')),
         NULLIF(TRIM(COALESCE(npart, _latin1'')), _latin1''));
END$$

-- 3. sp_rgt_c_registro: muestra el valor CONGELADO; fallback al plano si el
--    registro es anterior a esta migracion (numero_parte NULL). Indice 16
--    intacto: Tag_registro lee por posicion.
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

DELIMITER ;
