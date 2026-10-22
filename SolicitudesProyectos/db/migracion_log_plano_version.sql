-- =============================================================================
--  MIGRACION: auditoria TI de cambios de plano (log_plano_version)
--  Motor: MariaDB 10.4.24
--
--  QUE
--  Registrar quien, cuando y que cambio cada vez que se modifica un plano en
--  Complementos (nombre, tipo o No. de parte). "Version" del plano se audita
--  como el antes/despues de esos tres campos mutables (el plano no tiene una
--  columna de version formal). Solo se registra si algo efectivamente cambio.
--
--  El log vive en la tabla nueva log_plano_version. La captura se hace dentro
--  de sp_pno_m_plano (misma transaccion que el UPDATE) para que no pueda haber
--  una modificacion sin su rastro. Re-ejecutable.
-- =============================================================================

USE `solicitudes_proyectos`;

CREATE TABLE IF NOT EXISTS `log_plano_version` (
    `id_log`          INT AUTO_INCREMENT PRIMARY KEY,
    `id_plano`        INT          NOT NULL,
    `nombre_anterior` VARCHAR(20)      NULL,
    `nombre_nuevo`    VARCHAR(20)      NULL,
    `tipo_anterior`   VARCHAR(20)      NULL,
    `tipo_nuevo`      VARCHAR(20)      NULL,
    `parte_anterior`  VARCHAR(45)      NULL,
    `parte_nueva`     VARCHAR(45)      NULL,
    `usuario`         VARCHAR(100)     NULL,
    `fecha`           DATETIME     NOT NULL,
    KEY `idx_log_plano` (`id_plano`),
    KEY `idx_log_fecha` (`fecha`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DELIMITER $$

-- sp_pno_m_plano (REEMPLAZA): audita antes de actualizar. 5to parametro usuario.
DROP PROCEDURE IF EXISTS `sp_pno_m_plano`$$
CREATE PROCEDURE `sp_pno_m_plano`(
    IN id           INT,
    IN tipo         VARCHAR(20),
    IN nom          VARCHAR(20),
    IN numero_parte VARCHAR(45),
    IN usuario      VARCHAR(100)
)
BEGIN
    DECLARE v_nom_ant   VARCHAR(20);
    DECLARE v_tipo_ant  VARCHAR(20);
    DECLARE v_parte_ant VARCHAR(45);
    DECLARE v_parte_nue VARCHAR(45);

    SELECT pa.nombre_plano, pa.tipo, pa.numero_parte
      INTO v_nom_ant, v_tipo_ant, v_parte_ant
      FROM plano pa
     WHERE pa.id_plano = id;

    SET v_parte_nue = NULLIF(TRIM(COALESCE(numero_parte, _latin1'')), _latin1'');

    -- <=> es igualdad segura ante NULL; solo se audita si algo cambio.
    IF NOT (v_nom_ant <=> nom)
       OR NOT (v_tipo_ant <=> tipo)
       OR NOT (v_parte_ant <=> v_parte_nue) THEN
        INSERT INTO log_plano_version
            (id_plano, nombre_anterior, nombre_nuevo, tipo_anterior, tipo_nuevo,
             parte_anterior, parte_nueva, usuario, fecha)
        VALUES
            (id, v_nom_ant, nom, v_tipo_ant, tipo, v_parte_ant, v_parte_nue,
             NULLIF(TRIM(COALESCE(usuario, _latin1'')), _latin1''), NOW());
    END IF;

    UPDATE plano pa
       SET pa.tipo         = tipo,
           pa.nombre_plano = nom,
           pa.numero_parte = v_parte_nue
     WHERE pa.id_plano = id;
END$$

DELIMITER ;
