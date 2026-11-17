-- =========================================================================
-- Requerimiento 1.1.U04: Lista de verificación de documentos del lote
-- (R-GC-094 V11) dentro del Batch Record.
--
-- El contenido es el del R-GC-094 V10 que usa hoy el área; para el
-- aplicativo se rotula V11 sin cambios (decisión del cliente). Solo se
-- cargan las secciones de BOLSAS (las demás se certifican en otros
-- aplicativos). R-GC-194 (Bolsa Drenaje) queda sin sección hasta validarlo
-- con el cliente; se agrega aquí mismo cuando se defina.
--
--   checklist_section       Una sección del R-GC-094 (Bocas, Colpitts...).
--                           certificate_formats: certificados COA del lote que
--                           la habilitan, ej. '[R-GC-046][R-GC-220]'.
--                           trigger_codes: códigos de Registros LAB que deben
--                           existir en el lote para que aplique (la línea por
--                           la que pasó). Vacío = aplica solo por el formato.
--                           Si aplican varias (Bocas + Colpitt) se unen.
--   checklist_section_item  Renglones de la sección. source = cómo se
--                           verifica solo:
--                             LAB_REGISTRO   registro de Registros LAB (source_code = tipo_registro)
--                             LAB_DESPEJE    despeje de Registros LAB (source_code = plantilla.codigo)
--                             LAB_RESUMEN    resumen R-GC-017 de Registros LAB
--                             COA_CERTIFICADO certificado COA firmado del lote
--                             GL_ANEXO       anexos de Generación de Lotes
--                             MANUAL         solo lo marca el usuario
--   checklist_lote_item     Lo que el usuario marcó a mano en un lote
--                           (VERIFICADO con o sin archivo, o NA). Lo que el
--                           sistema encuentra solo NO se guarda: se calcula
--                           en vivo (Method.BatchRecordChecklist).
--                           item_key = código + nombre normalizados, así la
--                           marca sirve igual si el ítem viene de la unión
--                           de dos secciones.
--
-- Ejecutar contra la base `coa_record` (MySQL/MariaDB). Idempotente.
-- =========================================================================

CREATE TABLE IF NOT EXISTS `checklist_section` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `record_code` VARCHAR(20) NOT NULL DEFAULT 'R-GC-094',
  `version` INT NOT NULL DEFAULT 11,
  `name` VARCHAR(200) NOT NULL,
  `certificate_formats` VARCHAR(200) NOT NULL DEFAULT '',
  `trigger_codes` VARCHAR(500) NOT NULL DEFAULT '',
  `sort_order` INT NOT NULL DEFAULT 0,
  `state` TINYINT NOT NULL DEFAULT 1,
  `user_registration` VARCHAR(200) DEFAULT NULL,
  `registration_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `checklist_section_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_section` INT NOT NULL,
  `consecutive` INT NOT NULL,
  `record_name` VARCHAR(250) NOT NULL,
  `record_code` VARCHAR(100) NOT NULL DEFAULT 'NA',
  `source` VARCHAR(30) NOT NULL DEFAULT 'MANUAL',
  `source_code` VARCHAR(100) NOT NULL DEFAULT '',
  `verification_note` VARCHAR(250) DEFAULT NULL,
  `required` TINYINT NOT NULL DEFAULT 1,
  `state` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `idx_checklist_item_section` (`id_section`, `consecutive`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `checklist_lote_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cliente` VARCHAR(150) NOT NULL,
  `anio` VARCHAR(20) NOT NULL,
  `orden` VARCHAR(100) NOT NULL,
  `lote` VARCHAR(100) NOT NULL,
  `id_item` INT DEFAULT NULL,
  `item_key` VARCHAR(255) NOT NULL,
  `status` VARCHAR(12) NOT NULL,
  `id_file` BIGINT DEFAULT NULL,
  `observation` VARCHAR(500) DEFAULT NULL,
  `user_document` VARCHAR(100) DEFAULT NULL,
  `user_name` VARCHAR(200) DEFAULT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_checklist_lote_item` (`cliente`, `anio`, `orden`, `lote`, `item_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_ckl_c_ConsultActiveItems`;

DELIMITER $$
CREATE PROCEDURE `Sp_ckl_c_ConsultActiveItems`()
BEGIN
  SELECT s.id, s.record_code, s.version, s.name, s.certificate_formats, s.trigger_codes, s.sort_order,
         i.id, i.consecutive, i.record_name, i.record_code, i.source, i.source_code,
         IFNULL(i.verification_note, ''), i.required
  FROM checklist_section s
  INNER JOIN checklist_section_item i ON i.id_section = s.id
  WHERE s.state = 1 AND i.state = 1
  ORDER BY s.sort_order ASC, s.id ASC, i.consecutive ASC, i.id ASC;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_ckl_c_ConsultLoteMarks`;

DELIMITER $$
CREATE PROCEDURE `Sp_ckl_c_ConsultLoteMarks`(
  IN p_cliente VARCHAR(150),
  IN p_anio VARCHAR(20),
  IN p_orden VARCHAR(100),
  IN p_lote VARCHAR(100)
)
BEGIN
  SELECT item_key, id_item, status, id_file, IFNULL(observation, ''), IFNULL(user_name, ''),
         DATE_FORMAT(updated_at, '%d/%m/%Y %H:%i')
  FROM checklist_lote_item
  WHERE cliente = p_cliente AND anio = p_anio AND orden = p_orden AND lote = p_lote;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_ckl_r_SaveLoteMark`;

DELIMITER $$
CREATE PROCEDURE `Sp_ckl_r_SaveLoteMark`(
  IN p_cliente VARCHAR(150),
  IN p_anio VARCHAR(20),
  IN p_orden VARCHAR(100),
  IN p_lote VARCHAR(100),
  IN p_id_item INT,
  IN p_item_key VARCHAR(255),
  IN p_status VARCHAR(12),
  IN p_id_file BIGINT,
  IN p_observation VARCHAR(500),
  IN p_user_document VARCHAR(100),
  IN p_user_name VARCHAR(200)
)
BEGIN
  INSERT INTO checklist_lote_item (cliente, anio, orden, lote, id_item, item_key, status, id_file,
      observation, user_document, user_name)
  VALUES (p_cliente, p_anio, p_orden, p_lote, p_id_item, p_item_key, p_status, p_id_file,
      p_observation, p_user_document, p_user_name)
  ON DUPLICATE KEY UPDATE id_item = p_id_item, status = p_status, id_file = p_id_file,
      observation = p_observation, user_document = p_user_document, user_name = p_user_name;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_ckl_d_DeleteLoteMark`;

DELIMITER $$
CREATE PROCEDURE `Sp_ckl_d_DeleteLoteMark`(
  IN p_cliente VARCHAR(150),
  IN p_anio VARCHAR(20),
  IN p_orden VARCHAR(100),
  IN p_lote VARCHAR(100),
  IN p_item_key VARCHAR(255)
)
BEGIN
  DELETE FROM checklist_lote_item
  WHERE cliente = p_cliente AND anio = p_anio AND orden = p_orden AND lote = p_lote AND item_key = p_item_key;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Carga inicial: secciones de bolsas del R-GC-094 (contenido V10, rotulado
-- V11). Solo si aún no hay secciones, para no duplicar ni pisar ajustes.
-- -------------------------------------------------------------------------
INSERT INTO checklist_section (record_code, version, name, certificate_formats, trigger_codes, sort_order, user_registration)
SELECT 'R-GC-094', 11, 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', '[R-GC-046]', '[R-PRF-011][R-PRF-006]', 1, 'ADMINISTRADOR'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM checklist_section WHERE name = 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS');

INSERT INTO checklist_section (record_code, version, name, certificate_formats, trigger_codes, sort_order, user_registration)
SELECT 'R-GC-094', 11, 'DOCUMENTOS DEL PRODUCTO TERMINADO COLPITTS', '[R-GC-046]', '[R-PRF-013][R-PRF-007]', 2, 'ADMINISTRADOR'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM checklist_section WHERE name = 'DOCUMENTOS DEL PRODUCTO TERMINADO COLPITTS');

-- Plumatt = líneas PP (R-GC-220 usa esta sección; el nombre del registro
-- aún no está actualizado a "PP").
INSERT INTO checklist_section (record_code, version, name, certificate_formats, trigger_codes, sort_order, user_registration)
SELECT 'R-GC-094', 11, 'DOCUMENTOS PRODUCTO TERMINADO PLUMATT', '[R-GC-046][R-GC-220]', '[R-PRF-056][R-PRF-057]', 3, 'ADMINISTRADOR'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM checklist_section WHERE name = 'DOCUMENTOS PRODUCTO TERMINADO PLUMATT');

INSERT INTO checklist_section (record_code, version, name, certificate_formats, trigger_codes, sort_order, user_registration)
SELECT 'R-GC-094', 11, 'DOCUMENTOS PRODUCTO TERMINADO BOLSA EVA', '[R-GC-074]', '', 4, 'ADMINISTRADOR'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM checklist_section WHERE name = 'DOCUMENTOS PRODUCTO TERMINADO BOLSA EVA');

-- Ítems: solo se cargan para las secciones que todavía no tienen ninguno.
INSERT INTO checklist_section_item (id_section, consecutive, record_name, record_code, source, source_code, verification_note)
SELECT s.id, v.consecutive, v.record_name, v.record_code, v.source, v.source_code, v.verification_note
FROM checklist_section s
INNER JOIN (
            SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS' AS section_name, 1 AS consecutive, 'Orden de trabajo' AS record_name, 'R-PRF-001' AS record_code, 'MANUAL' AS source, '' AS source_code, NULL AS verification_note
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', 2, 'Despeje y arranque de línea Screen-colas', 'R-PRF-005', 'LAB_DESPEJE', 'R-PRF-005', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', 3, 'Control proceso screen colas', 'R-PRF-010', 'LAB_REGISTRO', 'R-PRF-010', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', 4, 'Linea sellado Drenaje y Drenaje APD screen', 'R-PRF-012', 'LAB_REGISTRO', 'R-PRF-012', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', 5, 'Despeje y arranque de línea de sellado bocas', 'R-PRF-006', 'LAB_DESPEJE', 'R-PRF-006', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', 6, 'Control proceso línea Bocas', 'R-PRF-011', 'LAB_REGISTRO', 'R-PRF-011', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', 7, 'Resumen Sellado de líneas', 'R-GC-017', 'LAB_RESUMEN', 'R-GC-017', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', 8, 'Reporte de Alistamiento', 'NA', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', 9, 'Inspección en el despacho', 'R-GC-043', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', 10, 'Lista / Remisión (Internacional)', 'R-LO-008', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', 11, 'Certificado de análisis', 'NA', 'GL_ANEXO', '', 'Generación de Lotes'
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO BOCAS', 12, 'Certificado de Calidad', 'R-GC-046', 'COA_CERTIFICADO', '', 'Se archiva carpeta por cliente'

  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO COLPITTS', 1, 'Orden de trabajo', 'R-PRF-001', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO COLPITTS', 2, 'Despeje y arranque de línea Colpitt', 'R-PRF-007', 'LAB_DESPEJE', 'R-PRF-007', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO COLPITTS', 3, 'Control proceso línea Colpitt', 'R-PRF-013', 'LAB_REGISTRO', 'R-PRF-013', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO COLPITTS', 4, 'Resumen Sellado de líneas', 'R-GC-017', 'LAB_RESUMEN', 'R-GC-017', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO COLPITTS', 5, 'Reporte de Alistamiento', 'NA', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO COLPITTS', 6, 'Inspección en el despacho', 'R-GC-043', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO COLPITTS', 7, 'Lista / Remisión (Internacional)', 'R-LO-008', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO COLPITTS', 8, 'Certificado de análisis', 'NA', 'GL_ANEXO', '', 'Generación de Lotes'
  UNION ALL SELECT 'DOCUMENTOS DEL PRODUCTO TERMINADO COLPITTS', 9, 'Certificado de Calidad', 'R-GC-046', 'COA_CERTIFICADO', '', 'Se archiva carpeta por cliente'

  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO PLUMATT', 1, 'Orden de trabajo', 'R-PRF-001', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO PLUMATT', 2, 'Despeje y arranque de línea Plumatt', 'R-PRF-057', 'LAB_DESPEJE', 'R-PRF-057', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO PLUMATT', 3, 'Control de proceso línea Plumatt', 'R-PRF-056', 'LAB_REGISTRO', 'R-PRF-056', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO PLUMATT', 4, 'Resumen Sellado de líneas', 'R-GC-017', 'LAB_RESUMEN', 'R-GC-017', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO PLUMATT', 5, 'Reporte de Alistamiento', 'NA', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO PLUMATT', 6, 'Inspección en el despacho', 'R-GC-043', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO PLUMATT', 7, 'Lista / Remisión (Internacional)', 'R-LO-008', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO PLUMATT', 8, 'Certificado de análisis', 'NA', 'GL_ANEXO', '', 'Generación de Lotes'
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO PLUMATT', 9, 'Certificado de Calidad', 'R-GC-046', 'COA_CERTIFICADO', '', 'Se archiva en carpeta por cliente'

  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO BOLSA EVA', 1, 'Orden de trabajo', 'R-PRF-001', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO BOLSA EVA', 2, 'Despeje y arranque de línea Screen-colas', 'R-PRF-005', 'LAB_DESPEJE', 'R-PRF-005', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO BOLSA EVA', 3, 'Control proceso screen colas', 'R-PRF-010', 'LAB_REGISTRO', 'R-PRF-010', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO BOLSA EVA', 4, 'Despeje y arranque de línea Bolsa Eva', 'R-PRF-020', 'LAB_DESPEJE', 'R-PRF-020', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO BOLSA EVA', 5, 'Control Proceso Bolsa Eva', 'R-PRF-019', 'LAB_REGISTRO', 'R-PRF-019', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO BOLSA EVA', 6, 'Resumen Sellado de líneas', 'R-GC-017', 'LAB_RESUMEN', 'R-GC-017', 'Registros LAB'
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO BOLSA EVA', 7, 'Inspección en el despacho', 'R-GC-043', 'MANUAL', '', NULL
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO BOLSA EVA', 8, 'Certificado de Esterilización', 'NA', 'GL_ANEXO', '', 'Generación de Lotes'
  UNION ALL SELECT 'DOCUMENTOS PRODUCTO TERMINADO BOLSA EVA', 9, 'Certificado de Calidad', 'R-GC-074', 'COA_CERTIFICADO', '', 'Se archiva en carpeta por cliente'
) v ON v.section_name = s.name
WHERE NOT EXISTS (SELECT 1 FROM checklist_section_item x WHERE x.id_section = s.id);
