-- =====================================================================
-- Cambio de cliente en el Visual: guardar también la cabecera del registro
-- (certificates.customer), no solo el HTML.
--
-- Sp_ctf_u_UpdateCertificates (guardado de un certificado existente,
-- incluidos los duplicados) solo actualiza consecutivo, cantidad, fecha,
-- lotes y HTML; el cliente que se escoge en el Visual quedaba únicamente en
-- el HTML y la lista principal seguía mostrando el cliente anterior.
--
-- Procedimiento aparte (no se cambia la firma de Sp_ctf_u_UpdateCertificates)
-- para que el script se pueda ejecutar antes o después de desplegar la app.
-- certificates.code NO se toca: guarda el registro (R-GC-074, ...) que la
-- lista muestra en la columna "Registro". Un cliente vacío no reemplaza el
-- que ya hay. Idempotente.
-- =====================================================================

DROP PROCEDURE IF EXISTS `Sp_ctf_u_UpdateCertificateCustomer`;

DELIMITER $$
CREATE PROCEDURE `Sp_ctf_u_UpdateCertificateCustomer`(
    IN `IdC` INT,
    IN `ctm` VARCHAR(300)
)
BEGIN
    UPDATE certificates c
    SET c.customer = COALESCE(NULLIF(TRIM(ctm), ''), c.customer)
    WHERE c.id_certificate = IdC;
END$$
DELIMITER ;
