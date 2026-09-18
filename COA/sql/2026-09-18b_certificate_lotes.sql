-- =========================================================================
-- Migración: marcador de "lote firmado" independiente de si tiene archivos.
--
-- Antes de la migración a Office Platform, Generate.java (firma de
-- certificados, módulo de Generación) creaba una carpeta local vacía
-- (web/Certificates/<Cliente>/<Anio>/<Orden>/<Lote>/mkdirs()) al firmar,
-- y esa carpeta física era lo que hacía "aparecer" el lote en la navegación
-- de FileManager.jsp, incluso sin ningún archivo subido.
--
-- certificate_files (ver 2026-09-18_certificate_files.sql) solo registra
-- filas cuando se sube o genera un archivo real, así que un lote firmado
-- pero sin archivos nunca aparecía en la navegación nueva. Esta tabla
-- restaura ese comportamiento SIN volver a tocar el filesystem: Generate.java
-- solo inserta la combinación cliente/anio/orden/lote aquí al firmar, y la
-- navegación de FileManager.jsp combina esta tabla con certificate_files.
--
-- Ejecutar contra la base `coa_record` (MySQL).
-- =========================================================================

CREATE TABLE IF NOT EXISTS `certificate_lotes` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cliente` VARCHAR(150) NOT NULL,
  `anio` VARCHAR(20) NOT NULL,
  `orden` VARCHAR(100) NOT NULL,
  `lote` VARCHAR(100) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_certificate_lotes` (`cliente`, `anio`, `orden`, `lote`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------------------
-- Sp_clt_r_RegisterLote: marca que un lote existe (se firmó), sin archivo.
-- Idempotente (INSERT IGNORE): firmar el mismo lote varias veces no duplica.
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_clt_r_RegisterLote`;

DELIMITER $$
CREATE PROCEDURE `Sp_clt_r_RegisterLote`(
  IN p_cliente VARCHAR(150),
  IN p_anio VARCHAR(20),
  IN p_orden VARCHAR(100),
  IN p_lote VARCHAR(100)
)
BEGIN
  INSERT IGNORE INTO certificate_lotes (cliente, anio, orden, lote)
  VALUES (p_cliente, p_anio, p_orden, p_lote);
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Sp_cff_c_ConsultDistinct*: se redefinen para combinar certificate_files
-- (lotes con archivos) y certificate_lotes (lotes firmados sin archivos).
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_cff_c_ConsultDistinctClientes`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_c_ConsultDistinctClientes`()
BEGIN
  SELECT cliente FROM (
    SELECT DISTINCT cliente FROM certificate_files
    UNION
    SELECT DISTINCT cliente FROM certificate_lotes
  ) t ORDER BY cliente;
END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `Sp_cff_c_ConsultDistinctAnios`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_c_ConsultDistinctAnios`(
  IN p_cliente VARCHAR(150)
)
BEGIN
  SELECT anio FROM (
    SELECT DISTINCT anio FROM certificate_files WHERE cliente = p_cliente
    UNION
    SELECT DISTINCT anio FROM certificate_lotes WHERE cliente = p_cliente
  ) t ORDER BY anio;
END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `Sp_cff_c_ConsultDistinctOrdenes`;

DELIMITER $$
CREATE PROCEDURE `Sp_cff_c_ConsultDistinctOrdenes`(
  IN p_cliente VARCHAR(150),
  IN p_anio VARCHAR(20)
)
BEGIN
  SELECT orden FROM (
    SELECT DISTINCT orden FROM certificate_files WHERE cliente = p_cliente AND anio = p_anio
    UNION
    SELECT DISTINCT orden FROM certificate_lotes WHERE cliente = p_cliente AND anio = p_anio
  ) t ORDER BY orden;
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
  SELECT lote FROM (
    SELECT DISTINCT lote FROM certificate_files WHERE cliente = p_cliente AND anio = p_anio AND orden = p_orden
    UNION
    SELECT DISTINCT lote FROM certificate_lotes WHERE cliente = p_cliente AND anio = p_anio AND orden = p_orden
  ) t ORDER BY lote;
END$$
DELIMITER ;
