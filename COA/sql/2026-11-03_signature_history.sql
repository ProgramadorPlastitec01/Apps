-- =========================================================================
-- Requerimiento 1.1.U03: historial de firmas (trazabilidad tipo "audit
-- trail" de HelloSign) para los tres documentos que se firman en COA:
--
--   document_type   document_key                        eventos
--   CERTIFICADO     id_certificate                      REGISTRADO, FINALIZADO, FIRMADO
--   SOPORTE         id_certificate (1 soporte vigente)  REGISTRADO (adjuntado), FIRMADO
--   BATCH_RECORD    nombre del PDF generado sin ".pdf"  REGISTRADO (generado), FIRMADO (aprobado)
--
-- El Batch Record y el soporte firmados son un archivo NUEVO (sufijo
-- "_FIRMADO_..."), por eso la llave no es el id de certificate_files sino
-- algo que sobrevive al reemplazo (ver Method.SignatureHistory).
--
-- Cada evento guarda quién (usuario, documento, rol), cuándo (fecha/hora
-- del servidor), desde dónde (IP, aplicativo y navegador/equipo).
--
-- Ejecutar contra la base `coa_record` (MySQL/MariaDB). Idempotente.
-- =========================================================================

CREATE TABLE IF NOT EXISTS `signature_history` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `document_type` VARCHAR(20) NOT NULL,
  `document_key` VARCHAR(255) NOT NULL,
  `id_certificate` INT DEFAULT NULL,
  `document_name` VARCHAR(500) DEFAULT NULL,
  `cliente` VARCHAR(300) DEFAULT NULL,
  `anio` VARCHAR(20) DEFAULT NULL,
  `orden` VARCHAR(100) DEFAULT NULL,
  `lote` VARCHAR(100) DEFAULT NULL,
  `event` VARCHAR(20) NOT NULL,
  `detail` VARCHAR(500) DEFAULT NULL,
  `id_user` INT DEFAULT NULL,
  `user_document` VARCHAR(100) DEFAULT NULL,
  `user_name` VARCHAR(200) DEFAULT NULL,
  `user_role` VARCHAR(200) DEFAULT NULL,
  `ip_address` VARCHAR(64) DEFAULT NULL,
  `application` VARCHAR(100) DEFAULT NULL,
  `user_agent` VARCHAR(500) DEFAULT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_signature_history_document` (`document_type`, `document_key`),
  KEY `idx_signature_history_certificate` (`id_certificate`),
  KEY `idx_signature_history_lote` (`cliente`, `anio`, `orden`, `lote`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_sgh_r_RegisterSignatureHistory`;

DELIMITER $$
CREATE PROCEDURE `Sp_sgh_r_RegisterSignatureHistory`(
  IN p_document_type VARCHAR(20),
  IN p_document_key VARCHAR(255),
  IN p_id_certificate INT,
  IN p_document_name VARCHAR(500),
  IN p_cliente VARCHAR(300),
  IN p_anio VARCHAR(20),
  IN p_orden VARCHAR(100),
  IN p_lote VARCHAR(100),
  IN p_event VARCHAR(20),
  IN p_detail VARCHAR(500),
  IN p_id_user INT,
  IN p_user_document VARCHAR(100),
  IN p_user_name VARCHAR(200),
  IN p_user_role VARCHAR(200),
  IN p_ip_address VARCHAR(64),
  IN p_application VARCHAR(100),
  IN p_user_agent VARCHAR(500)
)
BEGIN
  INSERT INTO signature_history (document_type, document_key, id_certificate, document_name,
      cliente, anio, orden, lote, event, detail, id_user, user_document, user_name, user_role,
      ip_address, application, user_agent)
  VALUES (p_document_type, p_document_key, p_id_certificate, p_document_name,
      p_cliente, p_anio, p_orden, p_lote, p_event, p_detail, p_id_user, p_user_document, p_user_name, p_user_role,
      p_ip_address, p_application, p_user_agent);
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_sgh_c_ConsultSignatureHistory`;

DELIMITER $$
CREATE PROCEDURE `Sp_sgh_c_ConsultSignatureHistory`(
  IN p_document_type VARCHAR(20),
  IN p_document_key VARCHAR(255)
)
BEGIN
  SELECT id, event, detail, user_name, user_role, ip_address, application, user_agent,
         DATE_FORMAT(created_at, '%d/%m/%Y') AS fecha,
         DATE_FORMAT(created_at, '%H:%i:%s') AS hora,
         document_name
  FROM signature_history
  WHERE document_type = p_document_type AND document_key = p_document_key
  ORDER BY created_at ASC, id ASC;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Carga inicial de lo que ya existía antes del historial: solo se conoce
-- quién y cuándo (no IP, aplicativo ni equipo), así que se marca en detail.
-- Solo se ejecuta si la tabla está vacía, para no duplicar.
-- -------------------------------------------------------------------------
INSERT INTO signature_history (document_type, document_key, id_certificate, document_name,
    cliente, anio, orden, lote, event, detail, user_name, application, created_at)
SELECT 'CERTIFICADO', CAST(c.id_certificate AS CHAR), c.id_certificate, c.consecutive_quality,
       c.customer, CAST(YEAR(c.registration_date) AS CHAR), c.`order`, c.batch,
       'REGISTRADO', 'Registro anterior al historial de firmas (sin IP ni equipo).',
       CASE WHEN c.user_registration LIKE '%/%' THEN SUBSTRING_INDEX(c.user_registration, '/', -1) ELSE c.user_registration END,
       'COA', c.registration_date
FROM certificates c
WHERE c.registration_date IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM signature_history);

INSERT INTO signature_history (document_type, document_key, id_certificate, document_name,
    cliente, anio, orden, lote, event, detail, user_document, user_name, application, created_at)
SELECT CASE WHEN f.carpeta = 'SupportDocs' THEN 'SOPORTE' ELSE 'BATCH_RECORD' END,
       CASE WHEN f.carpeta = 'SupportDocs' THEN CAST(f.id_certificate AS CHAR)
            WHEN f.original_file_name LIKE '%\_FIRMADO\_%' THEN SUBSTRING_INDEX(f.original_file_name, '_FIRMADO_', 1)
            ELSE SUBSTRING(f.original_file_name, 1, CHAR_LENGTH(f.original_file_name) - 4) END,
       f.id_certificate, f.original_file_name, f.cliente, f.anio, f.orden, f.lote,
       CASE WHEN f.original_file_name LIKE '%\_FIRMADO\_%' THEN 'FIRMADO' ELSE 'REGISTRADO' END,
       'Registro anterior al historial de firmas (sin IP ni equipo).',
       f.uploaded_by_id, f.uploaded_by_name, 'COA', f.created_at
FROM certificate_files f
WHERE (f.carpeta = 'BatchRecord' OR (f.carpeta = 'SupportDocs' AND f.id_certificate IS NOT NULL))
  AND NOT EXISTS (SELECT 1 FROM signature_history WHERE document_type IN ('SOPORTE', 'BATCH_RECORD'));
