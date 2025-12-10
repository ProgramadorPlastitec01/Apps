-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.5.16 - MySQL Community Server (GPL)
-- SO del servidor:              Win32
-- HeidiSQL Versión:             8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura de base de datos para sirh_ces
CREATE DATABASE IF NOT EXISTS `sirh_ces` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sirh_ces`;


-- Volcando estructura para tabla sirh_ces.ces
CREATE TABLE IF NOT EXISTS `ces` (
  `id_ces` int(11) NOT NULL AUTO_INCREMENT,
  `documento` int(11) DEFAULT NULL,
  `id_cargo` int(11) DEFAULT NULL,
  `datos` text,
  `motivo` varchar(50) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `anio` int(11) DEFAULT NULL,
  `mes` int(11) DEFAULT NULL,
  `ent_1` datetime DEFAULT NULL,
  `sal_1` datetime DEFAULT NULL,
  `ent_2` datetime DEFAULT NULL,
  `sal_3` datetime DEFAULT NULL,
  `ent_4` datetime DEFAULT NULL,
  `sal_4` datetime DEFAULT NULL,
  `ent_5` datetime DEFAULT NULL,
  `sal_5` datetime DEFAULT NULL,
  `ent_6` datetime DEFAULT NULL,
  `sal_6` datetime DEFAULT NULL,
  `ent_7` datetime DEFAULT NULL,
  `sal_7` datetime DEFAULT NULL,
  `ent_8` datetime DEFAULT NULL,
  `sal_8` datetime DEFAULT NULL,
  `ent_9` datetime DEFAULT NULL,
  `sal_9` datetime DEFAULT NULL,
  `ent_10` datetime DEFAULT NULL,
  `sal_10` datetime DEFAULT NULL,
  `ent_11` datetime DEFAULT NULL,
  `sal_11` datetime DEFAULT NULL,
  `ent_12` datetime DEFAULT NULL,
  `sal_12` datetime DEFAULT NULL,
  `ent_13` datetime DEFAULT NULL,
  `sal_13` datetime DEFAULT NULL,
  `ent_14` datetime DEFAULT NULL,
  `sal_14` datetime DEFAULT NULL,
  `ent_15` datetime DEFAULT NULL,
  `sal_15` datetime DEFAULT NULL,
  `ent_16` datetime DEFAULT NULL,
  `sal_16` datetime DEFAULT NULL,
  `ent_17` datetime DEFAULT NULL,
  `sal_17` datetime DEFAULT NULL,
  `ent_18` datetime DEFAULT NULL,
  `sal_18` datetime DEFAULT NULL,
  `ent_19` datetime DEFAULT NULL,
  `sal_19` datetime DEFAULT NULL,
  `ent_20` datetime DEFAULT NULL,
  `sal_20` datetime DEFAULT NULL,
  `ent_21` datetime DEFAULT NULL,
  `sal_21` datetime DEFAULT NULL,
  `ent_22` datetime DEFAULT NULL,
  `sal_22` datetime DEFAULT NULL,
  `ent_23` datetime DEFAULT NULL,
  `sal_23` datetime DEFAULT NULL,
  `ent_24` datetime DEFAULT NULL,
  `sal_24` datetime DEFAULT NULL,
  `ent_25` datetime DEFAULT NULL,
  `sal_25` datetime DEFAULT NULL,
  `ent_26` datetime DEFAULT NULL,
  `sal_26` datetime DEFAULT NULL,
  `ent_27` datetime DEFAULT NULL,
  `sal_27` datetime DEFAULT NULL,
  `ent_28` datetime DEFAULT NULL,
  `sal_28` datetime DEFAULT NULL,
  `ent_29` datetime DEFAULT NULL,
  `sal_29` datetime DEFAULT NULL,
  `ent_30` datetime DEFAULT NULL,
  `sal_30` datetime DEFAULT NULL,
  `ent_31` datetime DEFAULT NULL,
  `sal_31` datetime DEFAULT NULL,
  PRIMARY KEY (`id_ces`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla sirh_ces.ces: ~0 rows (aproximadamente)
DELETE FROM `ces`;
/*!40000 ALTER TABLE `ces` DISABLE KEYS */;
/*!40000 ALTER TABLE `ces` ENABLE KEYS */;


-- Volcando estructura para procedimiento sirh_ces.sp_ces_datos_empleado
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ces_datos_empleado`(cfm int)
select p.Documento,p.Apellidos,p.Nombre,p.codigo_firma,p.`Área`,p.Cargo,p.id_cargo
from sirh.vw_personal_activo p
where p.codigo_firma = cfm//
DELIMITER ;


-- Volcando estructura para procedimiento sirh_ces.sp_ces_registrar
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ces_registrar`(IN `dcm` bigint, IN `icg` int, IN `dts` text, IN `mtv` varchar(50), IN `etd` varchar(50), IN `ano` int, IN `ms` int)
insert into ces(documento,id_cargo,datos,motivo,estado,anio,mes)
values(dcm,icg,dts,mtv,etd,ano,ms)//
DELIMITER ;


-- Volcando estructura para procedimiento sirh_ces.sp_ces_verificacion_existencia
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ces_verificacion_existencia`(IN `dcm` bigint, IN `icg` INT, IN `ano` int, IN `ms` int)
select c.id_ces,c.documento,c.id_cargo,c.datos,c.motivo,(select group_concat(c.estado) from ces c where c.documento = dcm and c.id_cargo = icg limit 2) as estado,c.anio,c.mes
from ces c
where c.documento = dcm and c.id_cargo = icg and c.anio = ano and mes = ms//
DELIMITER ;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
