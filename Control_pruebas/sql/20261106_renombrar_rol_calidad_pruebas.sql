-- =====================================================================
-- Control Pruebas - Renombra el rol "Inspectora-Calidad" a "Calidad-Pruebas"
-- Fecha: 2026-11-06
--
-- Los permisos se validan por el NOMBRE del rol (Tag_complemento /
-- Tag_registro) y el responsable de un turno se identifica comparando el
-- texto "Rol/Nombre" guardado en registro.responsable. Por eso:
--   1. Se renombra el rol (id_rol = 4).
--   2. Se actualiza registro.responsable SOLO en turnos ABIERTOS (estado = 1),
--      para que las usuarias sigan siendo reconocidas como responsables.
--      Los turnos cerrados y demas columnas historicas (entrega, recibe,
--      usuario_registro, responsable_obs, responsable_dec) conservan el
--      nombre de rol vigente cuando se registraron.
--
-- Desplegar junto con el codigo que usa "Calidad-Pruebas".
-- Las usuarias con sesion abierta deben volver a iniciar sesion.
-- =====================================================================

START TRANSACTION;

UPDATE rol
SET nombre = 'Calidad-Pruebas'
WHERE id_rol = 4 AND nombre = 'Inspectora-Calidad';

UPDATE registro
SET responsable = REPLACE(responsable, 'Inspectora-Calidad/', 'Calidad-Pruebas/')
WHERE estado = 1 AND responsable LIKE '%Inspectora-Calidad/%';

COMMIT;

-- Verificacion
SELECT id_rol, nombre FROM rol WHERE id_rol = 4;
SELECT COUNT(*) AS turnos_abiertos_con_nombre_anterior
FROM registro WHERE estado = 1 AND responsable LIKE '%Inspectora-Calidad/%';

-- Reversion (si fuera necesario):
-- UPDATE rol SET nombre = 'Inspectora-Calidad' WHERE id_rol = 4;
-- UPDATE registro SET responsable = REPLACE(responsable, 'Calidad-Pruebas/', 'Inspectora-Calidad/')
--   WHERE estado = 1 AND responsable LIKE '%Calidad-Pruebas/%';
