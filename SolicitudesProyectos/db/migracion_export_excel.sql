-- =============================================================================
--  MIGRACION: detalle para la exportacion a Excel del Informe de Gestion
--  Motor: MariaDB 10.4.24
--
--  Tres procedimientos, no cinco: la hoja Registro_Horas y la hoja Datos_crudos
--  son la MISMA consulta (Datos_crudos solo agrega mes y estado de la
--  solicitud), asi que las sirve un unico procedimiento y el exportador decide
--  que columnas escribe en cada hoja. Las hojas Tablero, KPIs, Tecnicos y la
--  lista de areas/prioridades salen de los procedimientos que ya existen.
--
--  Todos reutilizan fn_rpt_horas_registro y fn_rpt_area_rol: el Excel y la
--  pantalla tienen que dar el mismo numero o el informe no sirve.
--
--  Se corre DESPUES de db/migracion_informe_gestion.sql. Es re-ejecutable.
-- =============================================================================

USE `solicitudes_proyectos`;

DELIMITER $$

-- -----------------------------------------------------------------------------
--  sp_rpt_c_xls_solicitudes
--  Hoja "Solicitudes".
--  Ancla temporal: ingresadas O cerradas dentro del periodo, el mismo criterio
--  que ya usa el registro R-PM-009 del modulo Indicador. Las horas de cada
--  solicitud si van ancladas al registro de tiempo, como en toda la fase.
-- -----------------------------------------------------------------------------
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
        -- Solo tiene sentido en las cerradas; en las abiertas es NULL y el
        -- exportador deja la celda vacia en vez de escribir un cero enganioso.
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


-- -----------------------------------------------------------------------------
--  sp_rpt_c_xls_registros
--  Alimenta DOS hojas: "Registro_Horas" (once primeras columnas) y
--  "Datos_crudos" (las mismas mas mes y estado_solicitud). Una sola consulta
--  para que las dos hojas no puedan discrepar.
-- -----------------------------------------------------------------------------
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
        -- Se publica para que el usuario pueda filtrar en el Excel los
        -- registros que el informe descarto y auditarlos.
        fn_rpt_estado_registro(rgt.fecha_inicio, rgt.fecha_fin) AS calidad_dato
    FROM registro rgt
    INNER JOIN solicitud slt ON rgt.id_solicitud = slt.idSolicitud
    INNER JOIN plano     pno ON rgt.id_plano     = pno.id_plano
    INNER JOIN usuario   usa ON slt.idUsuarios   = usa.idUsuarios
    WHERE DATE(rgt.fecha_inicio) BETWEEN p_fecha_ini AND p_fecha_fin
      AND (p_area IS NULL OR p_area = '' OR usa.Rol = p_area)
    ORDER BY rgt.fecha_inicio DESC;
END$$


-- -----------------------------------------------------------------------------
--  sp_rpt_c_xls_planos_piezas
--  Hoja "Planos_Piezas": una fila por plano y pieza.
--  El porcentaje es sobre el total del PLANO, igual que el detalle del
--  Indicador, para que la suma por plano de 100.
-- -----------------------------------------------------------------------------
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
--  ROLLBACK  (descomentar SOLO para revertir)
-- =============================================================================
/*
USE `solicitudes_proyectos`;
DROP PROCEDURE IF EXISTS `sp_rpt_c_xls_planos_piezas`;
DROP PROCEDURE IF EXISTS `sp_rpt_c_xls_registros`;
DROP PROCEDURE IF EXISTS `sp_rpt_c_xls_solicitudes`;
*/
