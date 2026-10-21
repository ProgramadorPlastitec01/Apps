-- =========================================================================
-- Devolución de certificado: correo al usuario responsable (no a correos
-- fijos de TI) + indicador visual "Devuelto" en el listado de Generación.
--
-- Hasta ahora:
--   - `certificates` no guardaba el ID del usuario que lo registró/editó,
--     solo un texto libre en `user_registration` (ej. "Administrador/Juan").
--   - `user` no tenía correo electrónico.
--   - El correo de devolución (Sp_nvt_r_RegisterNovelty + Mail.CertificateReturn)
--     ya existía, pero con dos destinatarios hardcodeados (correos de TI).
--
-- Con esta migración:
--   - `user.email` nuevo, editable desde la pantalla de usuarios.
--   - `certificates.id_user_registration` guarda el usuario que registró el
--     certificado; `certificates.user_modified` (ya existía, sin uso) ahora
--     guarda el usuario que hizo el último guardado/edición.
--   - `certificates.returned` (0/1): se activa cuando el certificado se
--     devuelve (Sp_nvt_r_RegisterNovelty, categoría 'Return') y se apaga
--     apenas alguien lo edita y guarda de nuevo (Sp_ctf_u_UpdateCertificates).
--   - Sp_ctf_c_ConsultCertificateResponsible resuelve el correo a notificar:
--     el último que lo tocó (user_modified) o, si nunca se ha editado, quien
--     lo registró (id_user_registration).
--
-- Ejecutar contra la base `coa_record` (MySQL).
-- =========================================================================

ALTER TABLE `user`
  ADD COLUMN `email` VARCHAR(200) NULL AFTER `user`;

ALTER TABLE `certificates`
  ADD COLUMN `id_user_registration` INT NULL AFTER `user_registration`,
  ADD COLUMN `returned` TINYINT(1) NOT NULL DEFAULT 0 AFTER `state`;

-- -------------------------------------------------------------------------
-- Sp_usr_r_RegisterUser: ahora recibe y guarda el correo.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_usr_r_RegisterUser`;

DELIMITER $$
CREATE PROCEDURE `Sp_usr_r_RegisterUser`(
	IN `nme` VARCHAR(100),
	IN `lnme` VARCHAR(100),
	IN `doc` INT,
	IN `cod` INT,
	IN `usr` VARCHAR(100),
	IN `idrl` INT,
	IN `usrReg` VARCHAR(100),
	IN `eml` VARCHAR(200)
)
INSERT INTO user (`name`,last_name,document,CODE,`user`,id_rol,`password`,user_registration,email)
VALUES (nme, lnme,doc, cod,usr,idrl, YEAR(NOW()), usrReg, eml)$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_usr_u_UpdateUser: ahora recibe y actualiza el correo.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_usr_u_UpdateUser`;

DELIMITER $$
CREATE PROCEDURE `Sp_usr_u_UpdateUser`(
	IN `idusr` INT,
	IN `nme` VARCHAR(250),
	IN `lnme` VARCHAR(250),
	IN `doc` INT,
	IN `cod` INT,
	IN `usr` VARCHAR(200),
	IN `idrl` INT,
	IN `eml` VARCHAR(200)
)
UPDATE user u
SET u.`name` = nme, u.last_name = lnme, u.document = doc, u.`code` = cod, `user` = usr, u.id_rol = idrl, u.email = eml
WHERE id_user = idusr$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_usr_c_ConsultUsers / Sp_usr_c_ConsultUserId: se agrega `email` al
-- final del SELECT (no se reordena nada para no romper los índices que ya
-- lee el código Java existente).
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_usr_c_ConsultUsers`;

DELIMITER $$
CREATE PROCEDURE `Sp_usr_c_ConsultUsers`()
SELECT u.id_user, u.name, u.last_name, u.document, u.code, u.`user`, u.`password`, u.id_rol, r.role_name, u.state, u.user_registration, u.registration_date, u.email
FROM USER u
INNER JOIN role r ON u.id_rol = r.id_role
ORDER BY u.state DESC$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `Sp_usr_c_ConsultUserId`;

DELIMITER $$
CREATE PROCEDURE `Sp_usr_c_ConsultUserId`(
	IN `IdU` int
)
SELECT u.id_user,u.`name`,u.last_name,u.document,u.`code`,u.`user`,u.`password`,u.state,u.signature,u.user_registration,u.registration_date,u.modification_date,u.id_rol,r.role_name,u.email
FROM user u
INNER JOIN role r ON u.id_rol = r.id_role
WHERE u.id_user = IdU$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_usr_c_ConsultUserPassword: se agrega `email` al final, para tenerlo
-- disponible en sesión (Servlet.Session) sin otra consulta.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_usr_c_ConsultUserPassword`;

DELIMITER $$
CREATE PROCEDURE `Sp_usr_c_ConsultUserPassword`(
	IN `usr` VARCHAR(100),
	IN `psw` VARCHAR(100)
)
SELECT u.id_user,CONCAT(u.`name`,' ',u.last_name),u.document,u.`code`,u.`user`,u.`password`,
				u.id_rol, r.role_name,
				IF(u.`password` = YEAR(CURDATE()) OR CHAR_LENGTH(u.`password`) < 30, 'YES',  'NO') AS 'CountCaracter',
				u.`name`,u.`password`,u.state,u.user_registration,u.registration_date,r.permission,u.signature,u.email
FROM user u
INNER JOIN role r ON u.id_rol = r.id_role
WHERE u.`user` = usr AND u.`password` = psw$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_cft_r_RegisterCertificates: ahora recibe el id del usuario que registra
-- (además del texto de rol/nombre que ya se guardaba en user_registration).
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_cft_r_RegisterCertificates`;

DELIMITER $$
CREATE PROCEDURE `Sp_cft_r_RegisterCertificates`(
	IN `tpe` VARCHAR(50),
	IN `cde` VARCHAR(200),
	IN `odr` VARCHAR(200),
	IN `pdt` VARCHAR(200),
	IN `btc` VARCHAR(200),
	IN `cql` VARCHAR(200),
	IN `ctm` VARCHAR(300),
	IN `amt` VARCHAR(200),
	IN `ddt` VARCHAR(200),
	IN `urg` VARCHAR(200),
	IN `mbt` VARCHAR(500),
	IN `arg` INT,
	IN `fmt` LONGTEXT,
	IN `idu` INT
)
INSERT INTO certificates (`type`,`code`,`order`,product,batch,consecutive_quality,customer,amount, dispatch_date ,user_registration,material_batch,amount_reg,`format`,id_user_registration)
VALUES (tpe, cde, odr, pdt,btc,cql,ctm,amt,ddt,urg,mbt,arg,fmt,idu)$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_ctf_u_UpdateCertificates: ahora recibe el id de quien edita/guarda,
-- lo deja en user_modified, y apaga el indicador de "devuelto" (returned=0)
-- porque el guardado es la señal de que ya se está gestionando la corrección.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_ctf_u_UpdateCertificates`;

DELIMITER $$
CREATE PROCEDURE `Sp_ctf_u_UpdateCertificates`(
	IN `IdC` INT,
	IN `cql` VARCHAR(200),
	IN `amt` VARCHAR(200),
	IN `ddt` VARCHAR(200),
	IN `mtb` VARCHAR(500),
	IN `fmt` LONGTEXT,
	IN `idu` INT
)
UPDATE certificates c
SET c.consecutive_quality = cql, c.amount = amt, c.dispatch_date = ddt, c.material_batch = mtb, c.`format` = fmt,
    c.user_modified = idu, c.modification_date = CURRENT_TIMESTAMP, c.returned = 0
WHERE c.id_certificate = IdC$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_ctf_r_RegistrerDuplicate: además del texto (user_registration), ahora
-- guarda también el id de quien duplica, en id_user_registration.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_ctf_r_RegistrerDuplicate`;

DELIMITER $$
CREATE PROCEDURE `Sp_ctf_r_RegistrerDuplicate`(
	IN `IdReg` INT,
	IN `UserReg` VARCHAR(100),
	IN `IdUserReg` INT
)
BEGIN
	INSERT INTO certificates (
	    type,
	    code,
	    `order`,
	    product,
	    batch,
	    consecutive_quality,
	    customer,
	    amount,
	    dispatch_date,
	    format,
	    state,
	    authorization_signature,
	    material_batch,
	    user_registration,
	    id_user_registration,
	    registration_date,
	    user_modified,
	    modification_date
	)
	SELECT
	    type,
	    code,
	    `order`,
	    product,
	    batch,
	    consecutive_quality,
	    customer,
	    amount,
	    dispatch_date,
	    format,
	    1,
	    NULL,
	    material_batch,
	    UserReg,
	    IdUserReg,
	    CURRENT_TIMESTAMP,
	    NULL,
	    NULL
	FROM certificates
	WHERE id_certificate = IdReg;

	SELECT LAST_INSERT_ID() AS new_id;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_nvt_r_RegisterNovelty: cuando la novedad es una devolución ('Return'),
-- además de mover el estado, enciende certificates.returned.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_nvt_r_RegisterNovelty`;

DELIMITER $$
CREATE PROCEDURE `Sp_nvt_r_RegisterNovelty`(
	IN `IdC` INT,
	IN `IdC2` INT,
	IN `ctg` VARCHAR(100),
	IN `dpc` VARCHAR(300),
	IN `urg` VARCHAR(200),
	IN `ste` INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    INSERT INTO novelty (id_certificate, category, `description`, user_registration)
    VALUES (IdC, ctg, dpc, urg);

    UPDATE certificates
    SET state = ste,
        returned = CASE WHEN ctg = 'Return' THEN 1 ELSE returned END
    WHERE id_certificate = IdC2;

    COMMIT;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_ctf_c_ConsultCertificateResponsible: resuelve el correo a notificar
-- cuando se devuelve un certificado. Prioriza a quien lo editó por última
-- vez (user_modified); si nunca se ha editado, usa a quien lo registró
-- (id_user_registration). Devuelve NULL si el certificado no tiene usuario
-- vinculado (certificados históricos, previos a esta migración).
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_ctf_c_ConsultCertificateResponsible`;

DELIMITER $$
CREATE PROCEDURE `Sp_ctf_c_ConsultCertificateResponsible`(
	IN `IdC` INT
)
SELECT u.email, CONCAT(u.name,' ',u.last_name) AS responsible_name
FROM certificates c
LEFT JOIN user u ON u.id_user = COALESCE(c.user_modified, c.id_user_registration)
WHERE c.id_certificate = IdC$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Listados de certificados: se agrega `c.returned` al final del SELECT
-- (índice nuevo, no se reordena nada existente) para poder pintar el ícono
-- de "Devuelto" en Tag.GenerateReport.
-- -------------------------------------------------------------------------
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
       c.returned
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
       c.returned
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
       c.returned
FROM certificates c
WHERE c.`type` = tpe AND c.state = 0
ORDER BY c.consecutive_quality desc$$
DELIMITER ;
