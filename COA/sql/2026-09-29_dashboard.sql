-- ============================================================================
-- Dashboard de inicio (Start.jsp) - 2026-09-29
--
-- Reemplaza los indicadores meramente informativos por colas de trabajo y
-- corrige dos defectos del tablero anterior:
--   * Sp_sta_c_ConsultRankingCustomer decia "Top 5" pero ordenaba por fecha,
--     no por cantidad, y contaba certificados eliminados (state = 0).
--   * La tarjeta "Ultimos 7 meses" mostraba el total del anio en curso.
--
-- Nota sobre antiguedad: certificates.modification_date no lo escribe ningun
-- procedimiento, por eso la antiguedad se mide desde registration_date y se
-- rotula como "dias desde la generacion", no como tiempo de ciclo.
-- ============================================================================

-- ----------------------------------------------------------------------------
-- KPIs de cabecera. `dias` es el umbral de atraso (3 por defecto en la app).
-- ----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_sta_c_ConsultKpis`;

DELIMITER $$
CREATE PROCEDURE `Sp_sta_c_ConsultKpis`(IN `dias` INT)
BEGIN

    IF dias IS NULL OR dias < 1 THEN
        SET dias = 3;
    END IF;

    SELECT
        (SELECT COUNT(*) FROM certificates
          WHERE state = 1) AS en_gestion,

        (SELECT COUNT(*) FROM certificates
          WHERE state = 1
            AND registration_date < DATE_SUB(NOW(), INTERVAL dias DAY)) AS en_gestion_atrasados,

        (SELECT COUNT(*) FROM certificates
          WHERE state = 2) AS por_firmar,

        (SELECT COUNT(*) FROM certificates
          WHERE state = 2
            AND registration_date < DATE_SUB(NOW(), INTERVAL dias DAY)) AS por_firmar_atrasados,

        -- Generados (no firmados): registration_date es la unica marca de
        -- tiempo confiable, asi que el mes se mide por generacion.
        (SELECT COUNT(*) FROM certificates
          WHERE state > 0
            AND registration_date >= DATE_FORMAT(CURDATE(), '%Y-%m-01')) AS generados_mes,

        (SELECT COUNT(*) FROM certificates
          WHERE state > 0
            AND registration_date >= DATE_SUB(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 1 MONTH)
            AND registration_date <  DATE_FORMAT(CURDATE(), '%Y-%m-01')) AS generados_mes_anterior,

        (SELECT COUNT(*) FROM novelty
          WHERE category = 'Return'
            AND registration_date >= DATE_SUB(NOW(), INTERVAL 30 DAY)) AS devoluciones_30d,

        (SELECT COUNT(*) FROM novelty
          WHERE category = 'Delete'
            AND registration_date >= DATE_SUB(NOW(), INTERVAL 30 DAY)) AS eliminados_30d,

        (SELECT COUNT(*) FROM certificates
          WHERE state > 0) AS total;

END$$
DELIMITER ;

-- ----------------------------------------------------------------------------
-- Produccion de los ultimos 6 meses desagregada por el estado actual del
-- certificado. Se arma sobre una serie de meses para no dejar huecos en la
-- grafica cuando un mes no tuvo movimiento.
-- ----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_sta_c_ConsultProductionByState`;

DELIMITER $$
CREATE PROCEDURE `Sp_sta_c_ConsultProductionByState`()
BEGIN

    SET lc_time_names = 'es_ES';

    SELECT
        DATE_FORMAT(m.inicio, '%Y-%m') AS mes_key,
        DATE_FORMAT(m.inicio, '%b')    AS mes_nombre,
        COUNT(CASE WHEN c.state = 1 THEN 1 END) AS en_gestion,
        COUNT(CASE WHEN c.state = 2 THEN 1 END) AS finalizados,
        COUNT(CASE WHEN c.state = 3 THEN 1 END) AS firmados
    FROM (
        SELECT DATE_SUB(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL n MONTH) AS inicio
        FROM (
            SELECT 5 AS n UNION ALL SELECT 4 UNION ALL SELECT 3
            UNION ALL SELECT 2 UNION ALL SELECT 1 UNION ALL SELECT 0
        ) s
    ) m
    LEFT JOIN certificates c
           ON c.state > 0
          AND c.registration_date >= m.inicio
          AND c.registration_date <  DATE_ADD(m.inicio, INTERVAL 1 MONTH)
    GROUP BY mes_key, mes_nombre
    ORDER BY mes_key ASC;

END$$
DELIMITER ;

-- ----------------------------------------------------------------------------
-- Distribucion por modulo de origen (RegistrosLab, InspeccionManga, ...).
-- ----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_sta_c_ConsultByType`;

DELIMITER $$
CREATE PROCEDURE `Sp_sta_c_ConsultByType`()
BEGIN

    SELECT
        c.type,
        COUNT(*) AS total
    FROM certificates c
    WHERE c.state > 0
      AND c.type IS NOT NULL
      AND c.type <> ''
    GROUP BY c.type
    ORDER BY total DESC;

END$$
DELIMITER ;

-- ----------------------------------------------------------------------------
-- Certificados sin cerrar (en gestion o por firmar) ordenados del mas antiguo
-- al mas reciente: es la cola que requiere atencion.
-- ----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_sta_c_ConsultPending`;

DELIMITER $$
CREATE PROCEDURE `Sp_sta_c_ConsultPending`(IN `limite` INT)
BEGIN

    IF limite IS NULL OR limite < 1 THEN
        SET limite = 10;
    END IF;

    SELECT
        c.id_certificate,
        c.consecutive_quality,
        c.customer,
        c.order,
        c.batch,
        c.user_registration,
        c.state,
        c.type,
        TIMESTAMPDIFF(DAY, c.registration_date, NOW()) AS dias,
        CASE c.state
            WHEN 1 THEN CONCAT('Generate?opt=1&Type=&IdCertificate=', c.id_certificate)
            ELSE        CONCAT('Generate?opt=8&StateM=2&IdCertificate=', c.id_certificate)
        END AS link
    FROM certificates c
    WHERE c.state IN (1, 2)
    ORDER BY c.registration_date ASC
    LIMIT limite;

END$$
DELIMITER ;

-- ----------------------------------------------------------------------------
-- Top clientes. Antes ordenaba por fecha (no era un ranking) e incluia
-- certificados eliminados.
-- ----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_sta_c_ConsultRankingCustomer`;

DELIMITER $$
CREATE PROCEDURE `Sp_sta_c_ConsultRankingCustomer`()
BEGIN

    SELECT
        t.customer,
        t.total_certificados,
        t.ultima_fecha,
        (SELECT u.consecutive_quality
           FROM certificates u
          WHERE u.customer = t.customer
            AND u.state > 0
          ORDER BY u.registration_date DESC, u.id_certificate DESC
          LIMIT 1) AS ultimo_consecutivo
    FROM (
        SELECT
            c.customer,
            COUNT(*)                   AS total_certificados,
            MAX(c.registration_date)   AS ultima_fecha
        FROM certificates c
        WHERE c.state > 0
          AND c.customer IS NOT NULL
          AND c.customer <> ''
        GROUP BY c.customer
    ) t
    ORDER BY t.total_certificados DESC, t.ultima_fecha DESC
    LIMIT 5;

END$$
DELIMITER ;

-- ----------------------------------------------------------------------------
-- Ultimos movimientos. Se agregan type y registration_date para poder mostrar
-- el icono del modulo y el tiempo relativo.
-- ----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_sta_c_ConsultUltimateCertificate`;

DELIMITER $$
CREATE PROCEDURE `Sp_sta_c_ConsultUltimateCertificate`()
BEGIN

    SELECT
        c.consecutive_quality,
        c.customer,
        CASE c.state
            WHEN 1 THEN 'En gestión'
            WHEN 2 THEN 'Finalizado'
            ELSE        'Firmado'
        END AS estado,
        CASE c.state
            WHEN 1 THEN CONCAT('Generate?opt=1&Type=&IdCertificate=', c.id_certificate)
            ELSE        CONCAT('Generate?opt=8&IdCertificate=', c.id_certificate)
        END AS link,
        c.type,
        c.state,
        TIMESTAMPDIFF(MINUTE, c.registration_date, NOW()) AS minutos
    FROM certificates c
    WHERE c.state > 0
    ORDER BY c.id_certificate DESC
    LIMIT 6;

END$$
DELIMITER ;
