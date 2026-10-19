-- =========================================================================
-- Migración: vincula el documento de soporte a un Certificado COA específico
-- (id_certificate), en vez de solo al lote. Un lote puede tener varios
-- Certificados COA (certificates.id_certificate, relación por order+batch),
-- así que ahora cada certificado puede tener su propio documento de soporte
-- (subir/ver/firmar/descargar), gestionado desde el módulo de Generación
-- (GenerateReport.java / Visual.java), junto al botón "Batch Record".
--
-- Ejecutar contra la base `coa_record` (MySQL).
-- =========================================================================

ALTER TABLE `certificate_files`
  ADD COLUMN `id_certificate` BIGINT NULL DEFAULT NULL AFTER `lote`,
  ADD INDEX `idx_certificate_files_certificate` (`id_certificate`, `carpeta`);

-- -------------------------------------------------------------------------
-- Sp_cff_r_RegisterCertificateFile: se agrega p_id_certificate (puede ser
-- NULL para archivos que no están ligados a un certificado, como los
-- "Archivos físicos" o el PDF unificado de Batch Record).
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_cff_r_RegisterCertificateFile`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_r_RegisterCertificateFile`(
  IN p_cliente VARCHAR(150),
  IN p_anio VARCHAR(20),
  IN p_orden VARCHAR(100),
  IN p_lote VARCHAR(100),
  IN p_carpeta VARCHAR(100),
  IN p_original_file_name VARCHAR(500),
  IN p_office_file_id BIGINT,
  IN p_office_uuid VARCHAR(100),
  IN p_mime_type VARCHAR(150),
  IN p_size_bytes BIGINT,
  IN p_uploaded_by_id VARCHAR(100),
  IN p_uploaded_by_name VARCHAR(200),
  IN p_id_certificate BIGINT
)
BEGIN
  INSERT INTO certificate_files
    (cliente, anio, orden, lote, carpeta, original_file_name, office_file_id,
     office_uuid, mime_type, size_bytes, uploaded_by_id, uploaded_by_name, id_certificate)
  VALUES
    (p_cliente, p_anio, p_orden, p_lote, p_carpeta, p_original_file_name, p_office_file_id,
     p_office_uuid, p_mime_type, p_size_bytes, p_uploaded_by_id, p_uploaded_by_name, p_id_certificate);
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_cff_c_ConsultCertificateFileById: se agrega id_certificate al SELECT
-- para poder conservarlo al re-registrar el archivo firmado
-- (SupportDocumentSignServlet).
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_cff_c_ConsultCertificateFileById`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_c_ConsultCertificateFileById`(
  IN p_id BIGINT
)
BEGIN
  SELECT id, office_file_id, original_file_name, mime_type, size_bytes, created_at,
         uploaded_by_id, uploaded_by_name, id_certificate
  FROM certificate_files
  WHERE id = p_id;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_cff_c_ConsultSupportFileByCertificate: documento de soporte vigente
-- (pendiente o firmado) de un Certificado COA puntual. Solo puede existir
-- uno a la vez por certificado (el firmado reemplaza al pendiente), por eso
-- basta con el más reciente.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_cff_c_ConsultSupportFileByCertificate`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_c_ConsultSupportFileByCertificate`(
  IN p_id_certificate BIGINT
)
BEGIN
  SELECT id, office_file_id, original_file_name, mime_type, size_bytes, created_at,
         uploaded_by_id, uploaded_by_name, id_certificate
  FROM certificate_files
  WHERE id_certificate = p_id_certificate AND carpeta = 'SupportDocs'
  ORDER BY created_at DESC
  LIMIT 1;
END$$
DELIMITER ;
