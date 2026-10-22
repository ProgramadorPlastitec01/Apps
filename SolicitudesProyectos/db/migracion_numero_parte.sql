-- =============================================================================
--  MIGRACION: captura del No. de parte del plano
--  Aplicativo : SolicitudesProyectos (PLASTITEC)
--  Motor      : MariaDB 10.4.24
--
--  La columna `plano`.`numero_parte` la creo db/migracion_informe_gestion.sql,
--  pero no habia por donde diligenciarla: los procedimientos de alta y de
--  modificacion de plano no la recibian y la consulta por id no la devolvia.
--  Este script deja el campo funcional de punta a punta.
--
--  Se corre DESPUES de db/migracion_informe_gestion.sql y es re-ejecutable.
--  NO rellena los planos existentes: el numero de parte lo carga el usuario
--  desde la pantalla de Planos. Inventarlos falsearia el R-PM-001.
-- =============================================================================

USE `solicitudes_proyectos`;

DELIMITER $$

-- -----------------------------------------------------------------------------
--  sp_pno_r_plano  (REEMPLAZA al existente)
--  CAMBIO: recibe un cuarto parametro, el numero de parte. Se acepta vacio
--  porque no todo plano tiene numero de parte asignado al momento de crearlo.
-- -----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `sp_pno_r_plano`$$
CREATE PROCEDURE `sp_pno_r_plano`(
    IN tipo         VARCHAR(20),
    IN nom          VARCHAR(20),
    IN fech         DATE,
    IN numero_parte VARCHAR(45)
)
BEGIN
    INSERT INTO plano (tipo, nombre_plano, fecha_ingreso, numero_parte)
    -- NULL y no cadena vacia cuando no se diligencia: asi el informe distingue
    -- "sin numero de parte" de "numero de parte en blanco" y pinta la raya gris.
    VALUES (tipo, nom, fech, NULLIF(TRIM(COALESCE(numero_parte, '')), ''));
END$$

-- -----------------------------------------------------------------------------
--  sp_pno_m_plano  (REEMPLAZA al existente)
--  CAMBIO: recibe y actualiza el numero de parte.
-- -----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `sp_pno_m_plano`$$
CREATE PROCEDURE `sp_pno_m_plano`(
    IN id           INT,
    IN tipo         VARCHAR(20),
    IN nom          VARCHAR(20),
    IN numero_parte VARCHAR(45)
)
BEGIN
    UPDATE plano pa
       SET pa.tipo         = tipo,
           pa.nombre_plano = nom,
           pa.numero_parte = NULLIF(TRIM(COALESCE(numero_parte, '')), '')
     WHERE pa.id_plano = id;
END$$

-- -----------------------------------------------------------------------------
--  sp_pno_c_plano_id  (REEMPLAZA al existente)
--  CAMBIO: devuelve el numero de parte como QUINTA columna, al final, para no
--  correr los indices que ya usa Tag_plano (0=id, 1=nombre, 2=tipo, 3=fecha).
-- -----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `sp_pno_c_plano_id`$$
CREATE PROCEDURE `sp_pno_c_plano_id`(
    IN id INT
)
BEGIN
    SELECT pa.id_plano,
           pa.nombre_plano,
           pa.tipo,
           pa.fecha_ingreso,
           COALESCE(pa.numero_parte, _latin1'') AS numero_parte
      FROM plano pa
     WHERE pa.id_plano = id;
END$$

DELIMITER ;


-- =============================================================================
--  ROLLBACK  (descomentar SOLO para revertir)
--  OJO: los tres procedimientos EXISTIAN antes de esta migracion. Revertir no
--  es borrarlos: hay que restaurarlos desde db/respaldo/rutinas_ANTES_migracion.sql
--  o el modulo de Planos queda sin alta, sin edicion y sin consulta por id.
-- =============================================================================
/*
USE `solicitudes_proyectos`;
SOURCE db/respaldo/rutinas_ANTES_migracion.sql;
*/
