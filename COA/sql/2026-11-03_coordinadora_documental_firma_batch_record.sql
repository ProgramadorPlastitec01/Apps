-- =========================================================================
-- Requerimiento 1.1.U01: firma de la Coordinadora Documental en el Batch
-- Record (aval de que el expediente quedó correctamente parametrizado).
--
-- 1. Permiso nuevo [41] "Firmar / aprobar Batch Record" (módulo Batch
--    Record). Lo valida BatchRecordSignServlet y habilita la opción
--    "Firmar / Aprobar" en FileManager.jsp.
-- 2. El Administrador recibe [41] (tiene todos los permisos).
-- 3. Rol nuevo "Coordinadora Documental": mismos permisos que el Gestor
--    (id_role 3) + [41]. Se copia la lista del Gestor vigente al momento de
--    ejecutar, así respeta cualquier ajuste que ya se le haya hecho.
--
-- Idempotente: se puede ejecutar más de una vez sin duplicar.
-- Ejecutar contra la base `coa_record` (MySQL).
-- =========================================================================

INSERT INTO permissions (id_permissions, module, `option`, description, state, user_registration)
SELECT 41, 'Batch Record', 'Firmar / aprobar Batch Record',
       'Este permiso permite firmar el Batch Record generado, como aval de la revision y aprobacion documental del lote.',
       1, 'ADMINISTRADOR'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE id_permissions = 41);

UPDATE role
SET permission = CONCAT(permission, '[41]')
WHERE role_name = 'Administrador'
  AND permission NOT LIKE '%[41]%';

INSERT INTO role (role_name, permission, state, user_registration)
SELECT 'Coordinadora Documental', CONCAT(g.permission, '[41]'), 1, 'ADMINISTRADOR'
FROM role g
WHERE g.id_role = 3
  AND NOT EXISTS (SELECT 1 FROM role WHERE role_name = 'Coordinadora Documental');
