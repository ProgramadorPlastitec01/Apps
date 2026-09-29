-- =========================================================================
-- Migración: la imagen de firma del usuario (Perfil -> "Actualizar Firma")
-- deja de guardarse en el filesystem local (web/Interface/Uploads/Signature/)
-- y pasa a vivir en Office Platform (Gestor de Archivos), carpeta
-- "COA/Firmas".
--
-- user.signature y certificates.authorization_signature siguen guardando
-- SOLO el nombre del archivo (ej. "firma_15_20260923_101500.png"); esta
-- tabla traduce ese nombre al office_file_id del gestor para poder servir
-- la imagen (SignatureImage servlet) y estamparla en PDFs.
--
-- El nombre se genera único por usuario + fecha: subir una firma nueva NO
-- sobrescribe la anterior, así los certificados ya firmados conservan la
-- imagen con la que se firmaron.
--
-- Ejecutar contra la base `coa_record` (MySQL).
-- =========================================================================

CREATE TABLE IF NOT EXISTS `user_signatures` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL,
  `file_name` VARCHAR(200) NOT NULL,
  `office_file_id` BIGINT NOT NULL,
  `office_uuid` VARCHAR(100) DEFAULT NULL,
  `mime_type` VARCHAR(150) DEFAULT NULL,
  `size_bytes` BIGINT DEFAULT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_signatures_file_name` (`file_name`),
  KEY `idx_user_signatures_user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------------------
-- Sp_usg_r_RegisterUserSignature: registra una firma recién subida al gestor.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_usg_r_RegisterUserSignature`;

DELIMITER $$
CREATE PROCEDURE `Sp_usg_r_RegisterUserSignature`(
  IN p_id_user INT,
  IN p_file_name VARCHAR(200),
  IN p_office_file_id BIGINT,
  IN p_office_uuid VARCHAR(100),
  IN p_mime_type VARCHAR(150),
  IN p_size_bytes BIGINT
)
BEGIN
  INSERT INTO user_signatures (id_user, file_name, office_file_id, office_uuid, mime_type, size_bytes)
  VALUES (p_id_user, p_file_name, p_office_file_id, p_office_uuid, p_mime_type, p_size_bytes);
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_usg_c_ConsultUserSignatureByName: resuelve el nombre guardado en
-- user.signature / certificates.authorization_signature al archivo del gestor.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_usg_c_ConsultUserSignatureByName`;

DELIMITER $$
CREATE PROCEDURE `Sp_usg_c_ConsultUserSignatureByName`(
  IN p_file_name VARCHAR(200)
)
BEGIN
  SELECT office_file_id, mime_type, size_bytes
  FROM user_signatures
  WHERE file_name = p_file_name;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Limpieza: lote vacío ('' / 0 / 0 / '') registrado por la firma masiva
-- anterior, que no enviaba cliente/año/orden/lote.
-- -------------------------------------------------------------------------
DELETE FROM certificate_lotes WHERE cliente = '' AND lote = '';
