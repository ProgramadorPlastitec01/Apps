-- =========================================================================
-- El tooltip de la fila resaltada como "Devuelto" mostraba un texto fijo
-- ("Certificado devuelto: pendiente de corrección"). Ahora muestra el
-- motivo real que escribió quien devolvió el certificado (tabla `novelty`,
-- columna `description`), tomando la devolución más reciente.
--
-- Se agrega `last_return_description` como último column de los 3 listados
-- de certificados (después de `returned`, que ya se agregó en
-- 2026-10-21_certificate_return_notification.sql), vía subconsulta
-- correlacionada para no hacer una consulta extra por fila desde Java.
--
-- Ejecutar contra la base `coa_record` (MySQL), después de
-- 2026-10-21_certificate_return_notification.sql.
-- =========================================================================

DROP PROCEDURE IF EXISTS `Sp_ctf_c_ConsultCertificates`;

DELIMITER $$
CREATE PROCEDURE `Sp_ctf_c_ConsultCertificates`(
	IN `tpe` VARCHAR(50),
	IN `ste` INT
)
SELECT c.id_certificate,
       c.type,
       c.code,
       c.consecutive_quality,
       c.customer,
       c.order,
       c.product,
       c.batch,
       c.state,
       c.registration_date,
       c.amount,
       c.dispatch_date,
       YEAR(c.registration_date) AS 'Year Generate',
       c.user_registration,
       c.amount_reg,
       c.returned,
       (SELECT n.description FROM novelty n
         WHERE n.id_certificate = c.id_certificate AND n.category = 'Return'
         ORDER BY n.id_novelty DESC LIMIT 1) AS last_return_description
FROM certificates c
WHERE
    (tpe IS NULL OR tpe = '' OR c.type = tpe) AND (ste IS NULL OR ste = 0 OR c.state = ste) AND c.state > 0
	 ORDER BY c.state ASC, c.id_certificate DESC$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `Sp_ctf_c_ConsultCertificatesId`;

DELIMITER $$
CREATE PROCEDURE `Sp_ctf_c_ConsultCertificatesId`(
	IN `tpe` VARCHAR(50),
	IN `IdC` INT
)
SELECT c.id_certificate,
       c.type,
       c.code,
       c.consecutive_quality,
       c.customer,
       c.order,
       c.product,
       c.batch,
       c.state,
       c.registration_date,
       c.amount,
       c.dispatch_date,
       YEAR(c.registration_date) AS 'Year Generate',
       c.user_registration,
       c.amount_reg,
       c.returned,
       (SELECT n.description FROM novelty n
         WHERE n.id_certificate = c.id_certificate AND n.category = 'Return'
         ORDER BY n.id_novelty DESC LIMIT 1) AS last_return_description
FROM certificates c
WHERE
    (tpe IS NULL OR tpe = '' OR c.type = tpe) AND c.id_certificate = IdC
	 ORDER BY c.state ASC, c.id_certificate DESC$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `Sp_ctf_c_ConsultCertificatesDelete`;

DELIMITER $$
CREATE PROCEDURE `Sp_ctf_c_ConsultCertificatesDelete`(
	IN `tpe` VARCHAR(50)
)
SELECT c.id_certificate,
       c.type,
       c.code,
       c.consecutive_quality,
       c.customer,
       c.order,
       c.product,
       c.batch,
       c.state,
       c.registration_date,
       c.amount,
       c.dispatch_date,
       YEAR(c.registration_date) AS 'Year Generate',
       c.user_registration,
       c.amount_reg,
       c.returned,
       (SELECT n.description FROM novelty n
         WHERE n.id_certificate = c.id_certificate AND n.category = 'Return'
         ORDER BY n.id_novelty DESC LIMIT 1) AS last_return_description
FROM certificates c
WHERE c.`type` = tpe AND c.state = 0
ORDER BY c.consecutive_quality desc$$
DELIMITER ;
