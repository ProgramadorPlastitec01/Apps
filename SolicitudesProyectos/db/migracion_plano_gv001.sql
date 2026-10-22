-- =============================================================================
-- MIGRACION: CONSOLIDACION DE REGISTROS SIN PLANO A 'GV-001' (Generales Varios)
-- Fecha: 2026-09-04
-- =============================================================================
-- Consolida los planos historicos 'N/A' (id 221) y 'NA-NA' (id 755)
-- bajo la convencion 'GV-001' con tipo 'Generales Varios'.
-- Esto permite que los registros y actividades no asociadas a planos especificos
-- se organicen y segmenten en indicadores, modulo R-PM-001 e informe de taller.
-- =============================================================================

START TRANSACTION;

-- Reasignar registros asociados a NA-NA (id 755) al id 221
UPDATE registro SET id_plano = 221 WHERE id_plano = 755;

-- Reasignar solicitudes asociadas a NA-NA (id 755) al id 221
UPDATE solicitud SET idplano = 221 WHERE idplano = 755;

-- Eliminar el plano redundante NA-NA (id 755)
DELETE FROM plano WHERE id_plano = 755;

-- Renombrar plano id 221 a 'GV-001' con tipo 'Generales Varios'
UPDATE plano SET nombre_plano = 'GV-001', tipo = 'Generales Varios' WHERE id_plano = 221;

COMMIT;
