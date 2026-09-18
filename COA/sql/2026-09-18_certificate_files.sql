-- =========================================================================
-- Migración: metadatos de archivos de Batch Record (Gestor de Archivos)
-- migrados de filesystem local (web/Certificates/...) a Office Platform.
--
-- Esta tabla NO guarda binarios: solo la referencia al archivo en Office
-- Platform (office_file_id) y los datos necesarios para reconstruir la
-- tabla de documentos de FileManager.jsp sin depender del listado de la
-- API externa (que no es confiable, ver OfficePlatformService.java).
--
-- Ejecutar contra la base `coa_record` (MySQL) con un usuario que pueda
-- crear tablas y stored procedures.
-- =========================================================================

CREATE TABLE IF NOT EXISTS `certificate_files` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cliente` VARCHAR(150) NOT NULL,
  `anio` VARCHAR(20) NOT NULL,
  `orden` VARCHAR(100) NOT NULL,
  `lote` VARCHAR(100) NOT NULL,
  `carpeta` VARCHAR(100) NOT NULL DEFAULT '',
  `original_file_name` VARCHAR(500) NOT NULL,
  `office_file_id` BIGINT NOT NULL,
  `office_uuid` VARCHAR(100) DEFAULT NULL,
  `mime_type` VARCHAR(150) DEFAULT NULL,
  `size_bytes` BIGINT DEFAULT NULL,
  `uploaded_by_id` VARCHAR(100) DEFAULT NULL,
  `uploaded_by_name` VARCHAR(200) DEFAULT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_certificate_files_lote` (`cliente`, `anio`, `orden`, `lote`, `carpeta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------------------
-- Sp_cff_r_RegisterCertificateFile: inserta una fila tras subir el archivo
-- a Office Platform.
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
  IN p_uploaded_by_name VARCHAR(200)
)
BEGIN
  INSERT INTO certificate_files
    (cliente, anio, orden, lote, carpeta, original_file_name, office_file_id,
     office_uuid, mime_type, size_bytes, uploaded_by_id, uploaded_by_name)
  VALUES
    (p_cliente, p_anio, p_orden, p_lote, p_carpeta, p_original_file_name, p_office_file_id,
     p_office_uuid, p_mime_type, p_size_bytes, p_uploaded_by_id, p_uploaded_by_name);
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_cff_c_ConsultCertificateFilesByLote: reemplaza currentDir.listFiles()
-- para la tabla de documentos de un lote (carpeta = '' -> Físico,
-- carpeta = 'SupportDocs' -> Soporte, carpeta = 'BatchRecord' -> histórico
-- de PDFs unificados). uploaded_by_id/uploaded_by_name viajan también aquí
-- (no solo en ConsultCertificateFileById) para poder mostrar "Generado por"
-- en la pestaña Batch Record de FileManager.jsp.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_cff_c_ConsultCertificateFilesByLote`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_c_ConsultCertificateFilesByLote`(
  IN p_cliente VARCHAR(150),
  IN p_anio VARCHAR(20),
  IN p_orden VARCHAR(100),
  IN p_lote VARCHAR(100),
  IN p_carpeta VARCHAR(100)
)
BEGIN
  SELECT id, office_file_id, original_file_name, mime_type, size_bytes, created_at,
         uploaded_by_id, uploaded_by_name
  FROM certificate_files
  WHERE cliente = p_cliente AND anio = p_anio AND orden = p_orden AND lote = p_lote AND carpeta = p_carpeta
  ORDER BY created_at ASC;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_cff_c_ConsultCertificateFileById: usada por el proxy de descarga y por
-- el borrado (para obtener el office_file_id real).
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_cff_c_ConsultCertificateFileById`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_c_ConsultCertificateFileById`(
  IN p_id BIGINT
)
BEGIN
  -- uploaded_by_id/uploaded_by_name viajan porque OfficePlatformService.eliminarArchivo/
  -- purgarArchivo necesitan el MISMO userId con el que se subió el archivo: si se omite,
  -- la API de Office Platform responde "éxito" pero no borra nada (comprobado en vivo).
  SELECT id, office_file_id, original_file_name, mime_type, size_bytes, created_at,
         uploaded_by_id, uploaded_by_name
  FROM certificate_files
  WHERE id = p_id;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_cff_d_DeleteCertificateFile: borra la fila local (el borrado real del
-- binario, a la papelera de Office Platform, lo hace Java antes de llamar
-- a este SP).
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_cff_d_DeleteCertificateFile`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_d_DeleteCertificateFile`(
  IN p_id BIGINT
)
BEGIN
  DELETE FROM certificate_files WHERE id = p_id;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_cff_c_ConsultDistinct*: reemplazan la navegación de carpetas
-- cliente -> anio -> orden -> lote que hoy hace currentDir.listFiles().
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_cff_c_ConsultDistinctClientes`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_c_ConsultDistinctClientes`()
BEGIN
  SELECT DISTINCT cliente FROM certificate_files ORDER BY cliente;
END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `Sp_cff_c_ConsultDistinctAnios`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_c_ConsultDistinctAnios`(
  IN p_cliente VARCHAR(150)
)
BEGIN
  SELECT DISTINCT anio FROM certificate_files WHERE cliente = p_cliente ORDER BY anio;
END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `Sp_cff_c_ConsultDistinctOrdenes`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_c_ConsultDistinctOrdenes`(
  IN p_cliente VARCHAR(150),
  IN p_anio VARCHAR(20)
)
BEGIN
  SELECT DISTINCT orden FROM certificate_files WHERE cliente = p_cliente AND anio = p_anio ORDER BY orden;
END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `Sp_cff_c_ConsultDistinctLotes`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_c_ConsultDistinctLotes`(
  IN p_cliente VARCHAR(150),
  IN p_anio VARCHAR(20),
  IN p_orden VARCHAR(100)
)
BEGIN
  SELECT DISTINCT lote FROM certificate_files WHERE cliente = p_cliente AND anio = p_anio AND orden = p_orden ORDER BY lote;
END$$
DELIMITER ;
