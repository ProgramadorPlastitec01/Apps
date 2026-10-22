-- =============================================================================
--  MIGRACION: No. de parte visible en el modal "Registrar Control"
--  Motor: MariaDB 10.4.24
--
--  POR QUE ES TAN CHICO ESTE SCRIPT
--  El numero de parte NO se persiste en `registro` y no debe hacerlo: es un
--  atributo del PLANO. `registro` ya guarda id_plano, y sp_rgt_c_registro hace
--  JOIN con plano, asi que el dato ya viaja solo hasta el R-PM-001. Copiarlo
--  dentro de registro duplicaria el dato y dejaria mintiendo al historico el
--  dia que se corrija un numero de parte.
--
--  Lo unico que faltaba era MOSTRARLO en el modal de captura, y para eso la
--  consulta de la solicitud tiene que traerlo.
--
--  NO se toca ningun procedimiento que cruce con `herramental_proceso`.
--  NO se mueve ningun indice existente: la columna nueva va AL FINAL.
--
--  Se corre DESPUES de db/migracion_gv001.sql. Es re-ejecutable.
-- =============================================================================

USE `solicitudes_proyectos`;

DELIMITER $$

-- -----------------------------------------------------------------------------
--  sp_slt_c_solicitud_id  (REEMPLAZA al existente)
--
--  CAMBIO: agrega pno.numero_parte como columna 27 (indice 26), AL FINAL.
--  Los indices 0..25 conservan su posicion EXACTA. Tag_registro y Tag_solicitud
--  leen por posicion: obj_solicitud[6] es el nombre del plano, [10] el estado,
--  [18] el id_plano del hidden idP. Mover una sola columna rompe el modulo.
-- -----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS `sp_slt_c_solicitud_id`$$
CREATE PROCEDURE `sp_slt_c_solicitud_id`(
    IN id INT
)
BEGIN
    SELECT slt.idSolicitud, slt.Fecha_Ingreso, slt.idUsuarios, slt.Numero_Solicitud,
           slt.Prioridad, slt.Ficha, pno.nombre_plano, slt.Pieza, slt.Cantidad,
           slt.Descripcion, slt.Estado, slt.Fecha_Fin_Solicitud,
           usa.idUsuarios, usa.Nombre, usa.Apellido, usa.Rol, usa.User, usa.password,
           pno.id_plano, pno.tipo, usa.correo,
           REPLACE(slt.maquina_programada, ')', '-'),
           (SELECT LENGTH(slt.maquina_programada)
                 - LENGTH(REPLACE(slt.maquina_programada, ')', ''))),
           REPLACE(slt.cantidad_programada, ')', '-'),
           slt.id_pendiente,
           (SELECT CASE WHEN m.tipo_salida = 'devolucion' THEN 'NO'
                        ELSE m.tipo_entrada END
              FROM movimientos m
             WHERE m.id_solicitud = slt.idSolicitud
             ORDER BY m.id_movimientos DESC
             LIMIT 1) AS 'Estado Mov',
           -- Columna nueva, indice 26. Cadena vacia y no NULL para que el campo
           -- del modal salga limpio mientras no se carguen los numeros de parte.
           COALESCE(pno.numero_parte, _latin1'') AS numero_parte
      FROM solicitud slt
     INNER JOIN usuario usa ON slt.idUsuarios = usa.idUsuarios
     INNER JOIN plano   pno ON slt.idplano    = pno.id_plano
     WHERE slt.idSolicitud = id;
END$$

DELIMITER ;


-- =============================================================================
--  ROLLBACK  (descomentar SOLO para revertir)
--  El procedimiento EXISTIA antes: revertir es restaurarlo desde
--  db/respaldo/rutinas_ANTES_migracion.sql, no borrarlo.
-- =============================================================================
