-- =====================================================================
-- sp_rgt_t_lotes_registro_verificar_lote
-- Registros_lab - Reporte R-GC-017 - "Comprobar errores"
--
-- Reemplaza (para Registros_lab) a sp_rgt_t_lotes_registro_verificar.
-- El SP anterior NO se modifica porque tambien lo usa COA
-- (ConnectionRegistrosLAB.java).
-- sp_rgt_t_lotes_registro_verificar_rango se sigue usando para pintar el
-- contenido del certificado (rango de fechas seleccionado).
--
-- Diferencias con el SP anterior:
--   * Filtra por orden (o.numero), linea y ciclo. NO filtra por fechas:
--     compara todos los grupos pendientes del lote (los que aparecen como
--     opciones separadas en el selector de lote) para poder unificarlos.
--     Reporte?opc=9 corrige exactamente estos mismos registros.
--   * lote_manga_p, lote_dto_drc_p y lote_dto_iqe_p salen del propio grupo
--     (antes: subconsulta "ultimo registro del lote", que ocultaba
--     diferencias) y forman parte del GROUP BY.
--   * La columna 14 devuelve r.lote_cola (antes: literal 'N/A').
--
-- El orden de columnas es IDENTICO al SP anterior (indices 0..36 que usa
-- Tag_reportes.java), por eso no hay que tocar la vista.
--
-- Orden de despliegue: 1) ejecutar este script en la BD
--                      2) desplegar la nueva version de Registros_lab
-- =====================================================================

DROP PROCEDURE IF EXISTS `sp_rgt_t_lotes_registro_verificar_lote`;

DELIMITER $$

CREATE PROCEDURE `sp_rgt_t_lotes_registro_verificar_lote`(
    IN `ipd` INT,
    IN `lte` VARCHAR(50),
    IN `iln` INT,
    IN `cet` VARCHAR(50),
    IN `nmr` INT
)
BEGIN
    SELECT
        r.lote_producto,                                                    -- 0
        COUNT(r.id_registro),                                               -- 1
        r.lote_manga_c,                                                     -- 2
        r.lote_manga_p,                                                     -- 3
        r.lote_dto_drc_c,                                                   -- 4
        r.lote_dto_drc_p,                                                   -- 5
        r.lote_dto_iqe_c,                                                   -- 6
        r.lote_dto_iqe_p,                                                   -- 7
        r.ensamble,                                                         -- 8
        r.lote_ensamble,                                                    -- 9
        r.lote_tinta,                                                       -- 10
        l.nombre,                                                           -- 11
        MIN(CONCAT(r.fecha_turno, ' ', DATE_FORMAT(r.fecha_registro, '%H:%i %p'))) AS hora_inicio, -- 12
        MAX(CONCAT(r.fecha_turno, ' ', DATE_FORMAT(r.fecha_registro, '%H:%i %p'))) AS hora_fin,    -- 13
        r.lote_cola,                                                        -- 14
        r.color_tinta,                                                      -- 15
        r.id_linea,                                                         -- 16
        r.ensamble_2,                                                       -- 17
        r.lote_ensamble_2,                                                  -- 18
        r.lote_manga_c_alt,                                                 -- 19
        r.lote_dto_ctl_c,                                                   -- 20
        r.lote_dto_ctl_p,                                                   -- 21
        MAX(r.lote_boca),                                                   -- 22
        r.lote_dto_c_alt,                                                   -- 23
        r.lote_tubo_refuerzo,                                               -- 24
        r.ciclo_esterilizacion,                                             -- 25
        r.ensamble_3,                                                       -- 26
        r.ensamble_4,                                                       -- 27
        r.lote_ensamble_3,                                                  -- 28
        r.lote_ensamble_4,                                                  -- 29
        'N/A',                                                              -- 30
        'N/A',                                                              -- 31
        'N/A',                                                              -- 32
        'N/A',                                                              -- 33
        'N/A',                                                              -- 34
        'N/A',                                                              -- 35
        MIN(r.id_registro)                                                  -- 36
    FROM registro r
    INNER JOIN linea l ON l.id_linea = r.id_linea
    INNER JOIN producto p ON p.id_producto = r.id_producto
    INNER JOIN orden_produccion o ON o.id_orden_produccion = p.id_orden_produccion
    WHERE r.id_producto = ipd
      AND r.resumido = 0
      AND r.estado = 0
      AND r.lote_producto = lte
      AND r.id_linea = iln
      AND r.ciclo_esterilizacion = cet
      AND o.numero = nmr
    GROUP BY
        r.lote_producto,
        r.id_linea,
        r.lote_manga_c,
        r.lote_manga_p,
        r.lote_dto_drc_c,
        r.lote_dto_drc_p,
        r.lote_dto_iqe_c,
        r.lote_dto_iqe_p,
        r.ensamble,
        r.lote_ensamble,
        r.lote_tinta,
        r.color_tinta,
        r.lote_cola,
        r.ensamble_2,
        r.lote_ensamble_2,
        r.lote_manga_c_alt,
        r.lote_dto_ctl_c,
        r.lote_dto_ctl_p,
        r.lote_dto_c_alt,
        r.lote_tubo_refuerzo,
        r.ciclo_esterilizacion,
        r.ensamble_3,
        r.ensamble_4,
        r.lote_ensamble_3,
        r.lote_ensamble_4,
        l.nombre
    ORDER BY
        MAX(r.fecha_registro) DESC;
END$$

DELIMITER ;
