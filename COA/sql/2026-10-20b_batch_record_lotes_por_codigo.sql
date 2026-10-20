-- =========================================================================
-- El tab "Generación de Lotes" de Batch Record (FileManager.jsp) y el PDF
-- unificado (BatchRecordManifest.java) traían TODOS los anexos de
-- Generación de Lotes que compartieran el mismo lote, sin importar si eran
-- de la recepción de material (tinta) exacta que el usuario seleccionó en
-- el certificado cuando había más de un consecutivo/REF para ese lote
-- (Util.replaceLoteReception). Un mismo lote puede tener varias recepciones
-- (distinto r.codigo) con anexos DIFERENTES cada una, así que filtrar solo
-- por lote mezclaba anexos de recepciones que no aplicaban a ese certificado.
--
-- Esto no cambia una vez el certificado queda Finalizado/Firmado: la
-- recepción elegida quedó grabada en el HTML guardado (Util.java ahora
-- escribe data-ref='<codigo>' en el span COSn), así que se puede usar ese
-- dato para filtrar.
--
-- Ejecutar contra la base `coa_record` (MySQL).
-- =========================================================================

-- -------------------------------------------------------------------------
-- Sp_ctf_c_ConsultCertificatesBatchRecord: se agrega c.format para que
-- FileManager.jsp / BatchRecordManifest.java puedan extraer de ahí los
-- codigos de recepción seleccionados (Util.extractSelectedReceptionCodes).
-- -------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `Sp_ctf_c_ConsultCertificatesBatchRecord`;

DELIMITER $$
CREATE PROCEDURE `Sp_ctf_c_ConsultCertificatesBatchRecord`(
	IN `odr` VARCHAR(200),
	IN `bth` VARCHAR(200)
)
BEGIN
	SELECT c.id_certificate, c.`code`, c.consecutive_quality,
	       CONCAT('Generate?opt=9&IdCertificates=', c.id_certificate) AS 'Link',
	       c.material_batch, c.format
	FROM certificates c
	WHERE c.`order` = odr AND c.batch = bth AND c.state = 3;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- BatchRecordGeneracionLotes: se agrega el placeholder XDataCodigosCondX,
-- que Java sustituye por "" (sin certificados con selección capturada: se
-- mantiene el comportamiento histórico, solo por lote) o por
-- "AND r.codigo IN ('COD1','COD2',...)" cuando sí hay codigos capturados.
-- -------------------------------------------------------------------------
UPDATE `setting`
SET `value` = 'SELECT DISTINCT a.id_anexos, a.registro, a.nombre, a.descripcion\nFROM anexos a\nLEFT JOIN control_consecutivos c ON c.id_control_consecutivos = a.id_registro\nLEFT JOIN recepcion_material r ON r.id_recepcion_material = a.id_registro\nWHERE c.lote IN  XDataBatchX\n   OR (r.lote IN  XDataBatchX XDataCodigosCondX)'
WHERE `categorie` = 'BatchRecordGeneracionLotes';
