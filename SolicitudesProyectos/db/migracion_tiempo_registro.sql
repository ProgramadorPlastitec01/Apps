-- =============================================================================
--  MIGRACION: calculo correcto del campo `tiempo` en sp_rgt_r_registro
--  Motor: MariaDB 10.4.24
--
--  PROBLEMA
--  El campo `tiempo` se calculaba con
--      CONCAT(EXTRACT(HOUR FROM TIMEDIFF(horaF,horaI)), ':',
--             EXTRACT(MINUTE FROM TIMEDIFF(horaF,horaI)))
--  Cuando horaF < horaI (cruce de medianoche capturado con la misma fecha, o
--  fecha final mal escrita) TIMEDIFF da un intervalo negativo y se guardaba
--  basura como "-10:-59" o "-16:0". Hay 9926 registros historicos asi.
--
--  ALCANCE DEL ARREGLO (leer antes de tocar)
--  `tiempo` es un campo de PRESENTACION. La capa de reporte NO lo usa:
--  fn_rpt_horas_registro calcula las horas desde fecha_inicio/fecha_fin con
--  TIMESTAMPDIFF y clasifica cada registro con fn_rpt_estado_registro
--  (OK / GIRADO / DESCARTADO). Por eso este cambio:
--    - Arregla SOLO el string `tiempo` para que salga positivo en HH:mm.
--    - NO toca fecha_inicio ni fecha_fin: se guardan TAL CUAL se capturaron.
--
--  POR QUE NO SE "CORRIGE" fecha_fin sumandole un dia
--  Un cruce con la misma fecha puede ser un turno de noche normal (<24 h) O un
--  trabajo real de VARIOS dias que el sistema no supo capturar (hallazgo de
--  Giovanni: la maquina corre >24 h pero el input solo trae hora del dia).
--  Sumar un dia a fecha_fin asumiria siempre lo primero y borraria la senal de
--  incompletitud (GIRADO/DESCARTADO) que el reporte usa para no inflar horas.
--  Se mantiene el dato crudo y que el reporte decida. Ademas asi los registros
--  nuevos se comportan igual que los historicos: compatibilidad total.
--
--  Para el VALOR MOSTRADO si se asume un unico cruce (fin + 1 dia) cuando fin
--  cae el mismo dia antes que inicio; si aun asi la diferencia no es positiva
--  (p. ej. fecha final de otro mes), se guarda '00:00' en vez de un negativo.
--  El formato es H:mm con las horas ACUMULADAS completas (13:01, 150:30,
--  5113:00): no se colapsa a la hora del reloj ni se recorta a dos digitos.
--
--  Es re-ejecutable. El procedimiento EXISTIA; revertir es restaurarlo desde
--  db/respaldo/rutinas_ANTES_migracion.sql.
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

    -- Solo para el valor mostrado: cruce de medianoche del MISMO dia -> +1 dia.
    SET v_fin = IF(horaF < horaI AND DATE(horaF) = DATE(horaI),
                   horaF + INTERVAL 1 DAY, horaF);
    SET v_min = TIMESTAMPDIFF(MINUTE, horaI, v_fin);

    INSERT INTO registro
        (usuario_registro, id_solicitud, id_maquina, id_plano, pieza,
         cantidad, descripcion, fecha_inicio, fecha_fin, tiempo)
    VALUES
        (usaR, ids, idm, idp, piz, cant, des,
         horaI,          -- fecha_inicio: cruda
         horaF,          -- fecha_fin:    cruda (la clasifica el reporte)
         IF(v_min <= 0, '00:00',
            -- Horas SIN LPAD: LPAD(x,2) trunca a 2 digitos cuando x tiene mas
            -- (LPAD('5113',2)='51'), colapsando trabajos de mas de 99 h. Las
            -- horas acumuladas van completas (150, 5113); solo el minuto se
            -- rellena a dos digitos porque siempre esta en 0..59.
            CONCAT(FLOOR(v_min / 60), ':',
                   LPAD(MOD(v_min, 60), 2, '0'))));
END$$

DELIMITER ;


-- =============================================================================
--  SANEAMIENTO DEL HISTORICO + LECTURA DINAMICA (R-PM-001)
--
--  Los registros viejos guardaron `tiempo` con EXTRACT(HOUR FROM TIMEDIFF(..)):
--    - 24:00:00 hace rollover a 0  -> "0:0"  (tarea de 24 h se ve como 0)
--    - turnos que cruzan medianoche -> negativos como "-3:0"
--  Como `tiempo` es un campo de PRESENTACION derivado de fecha_inicio/fecha_fin,
--  se recalcula desde esas columnas (que NO se tocan). El cruce del mismo dia se
--  asume de un solo salto (+1 dia); si aun asi la diferencia no es positiva se
--  guarda '0:00'. Horas acumuladas completas, minuto a dos digitos.
--
--  Es idempotente: recalcula siempre desde las fechas, correr de nuevo no daña.
-- =============================================================================

USE `solicitudes_proyectos`;

UPDATE `registro`
SET `tiempo` = IF(
    TIMESTAMPDIFF(MINUTE, fecha_inicio, IF(fecha_fin < fecha_inicio AND DATE(fecha_fin) = DATE(fecha_inicio), fecha_fin + INTERVAL 1 DAY, fecha_fin)) <= 0,
    '0:00',
    CONCAT(
        FLOOR(TIMESTAMPDIFF(MINUTE, fecha_inicio, IF(fecha_fin < fecha_inicio AND DATE(fecha_fin) = DATE(fecha_inicio), fecha_fin + INTERVAL 1 DAY, fecha_fin)) / 60),
        ':',
        LPAD(MOD(TIMESTAMPDIFF(MINUTE, fecha_inicio, IF(fecha_fin < fecha_inicio AND DATE(fecha_fin) = DATE(fecha_inicio), fecha_fin + INTERVAL 1 DAY, fecha_fin)), 60), 2, '0')
    )
);

-- -----------------------------------------------------------------------------
--  sp_rgt_c_registro  (REEMPLAZA): calcula `tiempo` al vuelo desde las fechas,
--  para que la tabla R-PM-001 nunca vuelva a mostrar 0:0 ni negativos aunque el
--  campo almacenado quedara viejo. Mismo orden de columnas de siempre: solo
--  cambia la expresion del indice 14 (tiempo). Tag_registro lee por posicion.
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
           IF(TIMESTAMPDIFF(MINUTE, r.fecha_inicio, IF(r.fecha_fin < r.fecha_inicio AND DATE(r.fecha_fin) = DATE(r.fecha_inicio), r.fecha_fin + INTERVAL 1 DAY, r.fecha_fin)) <= 0,
              '0:00',
              CONCAT(
                  FLOOR(TIMESTAMPDIFF(MINUTE, r.fecha_inicio, IF(r.fecha_fin < r.fecha_inicio AND DATE(r.fecha_fin) = DATE(r.fecha_inicio), r.fecha_fin + INTERVAL 1 DAY, r.fecha_fin)) / 60),
                  ':',
                  LPAD(MOD(TIMESTAMPDIFF(MINUTE, r.fecha_inicio, IF(r.fecha_fin < r.fecha_inicio AND DATE(r.fecha_fin) = DATE(r.fecha_inicio), r.fecha_fin + INTERVAL 1 DAY, r.fecha_fin)), 60), 2, '0')
              )
           ) AS tiempo,
           r.herramienta,
           COALESCE(p.numero_parte, _latin1'') AS numero_parte
      FROM registro r
     INNER JOIN solicitud s ON r.id_solicitud = s.idSolicitud
     INNER JOIN maquina   m ON r.id_maquina   = m.id_maquina
     INNER JOIN plano     p ON r.id_plano     = p.id_plano
     WHERE r.id_solicitud = ids
     ORDER BY r.fecha_registro ASC;
END$$
DELIMITER ;
