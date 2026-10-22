-- =============================================================================
--  MIGRACION: rol ASIST.PR (Asistente Proyectos) para Wilson Paez Rojas
--  Motor: MariaDB 10.4.24
--
--  QUE HACE
--  1. Reasigna a Wilson Paez Rojas (hoy TEC.PR) al nuevo rol ASIST.PR.
--     Verificado que el WHERE matchea SOLO a WILSON PAEZ ROJAS (idUsuarios 44)
--     y NO a los otros dos Wilson de la base (DUQUE id 26, CUITIVA id 73):
--     el AND Apellido LIKE '%Paez%' los excluye y sus usuarios no contienen
--     'wilson' ni 'wpaez'.
--  2. Mapea ASIST.PR al area 'PROYECTOS' en fn_rpt_area_rol. La funcion se
--     reemplaza completa pero SOLO agrega el WHEN 'ASIST.PR'; el resto de los
--     mapeos queda identico al actual (verificado uno por uno).
--
--  Los permisos de UI/menu de ASIST.PR (union de COORD.PR + TEC.PR) viven en
--  los Tags Java, no en la base.
--
--  Es re-ejecutable. fn_rpt_area_rol EXISTIA; revertir es restaurarla desde
--  db/respaldo/rutinas_ANTES_migracion.sql o db/migracion_informe_gestion.sql.
-- =============================================================================

USE `solicitudes_proyectos`;

-- 1. Wilson Paez Rojas -> ASIST.PR
UPDATE `usuario`
   SET `Rol` = 'ASIST.PR'
 WHERE (`Nombre` LIKE '%Wilson%' AND `Apellido` LIKE '%Paez%')
    OR `User` LIKE '%wpaez%'
    OR `User` LIKE '%wilson%';

-- 2. fn_rpt_area_rol: ASIST.PR pertenece a PROYECTOS
DELIMITER $$
DROP FUNCTION IF EXISTS `fn_rpt_area_rol`$$
CREATE FUNCTION `fn_rpt_area_rol`(p_rol VARCHAR(45))
    RETURNS VARCHAR(45)
    DETERMINISTIC
    NO SQL
BEGIN
    RETURN CASE p_rol
        WHEN 'AU'       THEN 'AUTOMATIZACION'
        WHEN 'COORD.PR' THEN 'PROYECTOS'
        WHEN 'ASIST.PR' THEN 'PROYECTOS'
        WHEN 'JEFE.PR'  THEN 'JEFE PROYECTOS'
        WHEN 'TEC.PR'   THEN 'TECNICOS PROYECTOS'
        WHEN 'MT'       THEN 'MANTENIMIENTO'
        WHEN 'MTF'      THEN 'MANTENIMIENTO FARMACEUTICO'
        WHEN 'MI'       THEN 'MANTENIMIENTO INSUMOS'
        WHEN 'PR'       THEN 'PRODUCCION'
        WHEN 'PI'       THEN 'PRODUCCION INSUMOS'
        WHEN 'PRF'      THEN 'PRODUCCION FARMACEUTICA'
        WHEN 'ADMIN'    THEN 'TECNOLOGIA DE INFORMACION'
        ELSE p_rol
    END;
END$$
DELIMITER ;
