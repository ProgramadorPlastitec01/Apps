-- =========================================================================
-- Ajuste: "Duplicar" ahora también está disponible sobre un certificado ya
-- firmado (State == 3), desde el módulo de Generación (GenerateReport.java).
-- El duplicado ya quedaba en estado de gestión (state = 1, sin cambios), pero
-- copiaba authorization_signature del original: un certificado "en gestión"
-- no debe traer una firma ya aplicada, así que ahora queda en NULL.
--
-- Ejecutar contra la base `coa_record` (MySQL).
-- =========================================================================

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
END$$
DELIMITER ;
