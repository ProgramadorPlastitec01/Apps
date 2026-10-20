-- =========================================================================
-- Trazabilidad de "Duplicar certificado": quién duplicó qué certificado, a
-- partir de cuál original y cuándo. Antes Sp_ctf_r_RegistrerDuplicate solo
-- insertaba la copia sin dejar rastro de la operación en sí.
--
-- Ejecutar contra la base `coa_record` (MySQL).
-- =========================================================================

CREATE TABLE IF NOT EXISTS `certificate_duplications` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_certificate_origin` INT NOT NULL,
  `id_certificate_new` INT NOT NULL,
  `user_id` VARCHAR(100) DEFAULT NULL,
  `user_name` VARCHAR(200) NOT NULL,
  `duplicated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_certificate_duplications_origin` (`id_certificate_origin`),
  KEY `idx_certificate_duplications_new` (`id_certificate_new`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------------------
-- Sp_cdp_r_RegisterDuplication: una fila por cada duplicación realizada.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_cdp_r_RegisterDuplication`;

DELIMITER $$
CREATE PROCEDURE `Sp_cdp_r_RegisterDuplication`(
  IN p_id_certificate_origin INT,
  IN p_id_certificate_new INT,
  IN p_user_id VARCHAR(100),
  IN p_user_name VARCHAR(200)
)
BEGIN
  INSERT INTO certificate_duplications
    (id_certificate_origin, id_certificate_new, user_id, user_name)
  VALUES
    (p_id_certificate_origin, p_id_certificate_new, p_user_id, p_user_name);
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_cdp_c_ConsultByCertificate: historial de duplicaciones donde el
-- certificado dado participó, como original o como copia (para una futura
-- pantalla de trazabilidad; no se consume todavía desde ningún JSP).
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_cdp_c_ConsultByCertificate`;

DELIMITER $$
CREATE PROCEDURE `Sp_cdp_c_ConsultByCertificate`(
  IN p_id_certificate INT
)
BEGIN
  SELECT id, id_certificate_origin, id_certificate_new, user_id, user_name, duplicated_at
  FROM certificate_duplications
  WHERE id_certificate_origin = p_id_certificate OR id_certificate_new = p_id_certificate
  ORDER BY duplicated_at DESC;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_ctf_r_RegistrerDuplicate: ahora devuelve el id del certificado nuevo
-- (LAST_INSERT_ID()) para poder registrar la trazabilidad en
-- certificate_duplications justo después de duplicar. Sigue dejando el
-- duplicado en state=1 y sin firma (authorization_signature = NULL).
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_ctf_r_RegistrerDuplicate`;

DELIMITER $$
CREATE PROCEDURE `Sp_ctf_r_RegistrerDuplicate`(
	IN `IdReg` INT,
	IN `UserReg` VARCHAR(100)
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
	    CURRENT_TIMESTAMP,
	    NULL,
	    NULL
	FROM certificates
	WHERE id_certificate = IdReg;

	SELECT LAST_INSERT_ID() AS new_id;
END$$
DELIMITER ;
