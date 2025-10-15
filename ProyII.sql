-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE DATABASE HospitalProy;
use HospitalProy;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `Paciente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Paciente` (
  `id` VARCHAR(100) NOT NULL,
  `nombre` VARCHAR(10) NULL,
  `fechaNacimiento` DATE NULL,
  `telefono` VARCHAR(10) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Medico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Medico` (
  `id` VARCHAR(100) NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `clave` VARCHAR(45) NULL,
  `especialidad` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Medicamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Medicamento` (
  `codigo` VARCHAR(10) NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `descripcion` VARCHAR(65) NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ItemReceta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ItemReceta` (
  `medicamentoCodigo` INT NOT NULL,
  `descripcion` VARCHAR(50) NULL,
  `cantidad` INT NULL,
  `indicaciones` VARCHAR(500) NULL,
  `duracionDias` INT NULL,
  PRIMARY KEY (`medicamentoCodigo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prescripcion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prescripcion` (
  `numero` INT NOT NULL AUTO_INCREMENT,
  `fechaConfeccion` DATE NULL,
  `fechaRetiro` DATE NULL,
  `estado` VARCHAR(45) NULL,
  `items` INT NOT NULL,
  `paciente` VARCHAR(10) NOT NULL,
  `medico` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`numero`),
  INDEX `fk_Prescripcion_ItemReceta1_idx` (`items` ASC) VISIBLE,
  INDEX `fk_Prescripcion_Paciente1_idx` (`paciente` ASC) VISIBLE,
  INDEX `fk_Prescripcion_Medico1_idx` (`medico` ASC) VISIBLE,
  CONSTRAINT `fk_Prescripcion_ItemReceta1`
    FOREIGN KEY (`items`)
    REFERENCES `ItemReceta` (`medicamentoCodigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescripcion_Paciente1`
    FOREIGN KEY (`paciente`)
    REFERENCES `Paciente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescripcion_Medico1`
    FOREIGN KEY (`medico`)
    REFERENCES `Medico` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EstadoReceta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EstadoReceta` (
  `estado` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`estado`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Receta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Receta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `medicoId` VARCHAR(10) NULL,
  `pacienteId` VARCHAR(10) NULL,
  `fechaConfeccion` DATE NULL,
  `fechaRetiro` DATE NULL,
  `estado` VARCHAR(45) NOT NULL,
  `medicamentos` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Receta_EstadoReceta1_idx` (`estado` ASC) VISIBLE,
  INDEX `fk_Receta_Medicamento1_idx` (`medicamentos` ASC) VISIBLE,
  CONSTRAINT `fk_Receta_EstadoReceta1`
    FOREIGN KEY (`estado`)
    REFERENCES `EstadoReceta` (`estado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Receta_Medicamento1`
    FOREIGN KEY (`medicamentos`)
    REFERENCES `Medicamento` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Usuario` (
  `id` VARCHAR(100) NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `clave` VARCHAR(45) NULL,
  `rol` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmaceuta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Farmaceuta` (
  `id` VARCHAR(100) NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `clave` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Admin` (
  `id` VARCHAR(100) NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `clave` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- ----------------------------------------------------
-- 
-- ----------------------------------------------------

INSERT INTO Paciente (id,nombre,fechaNacimiento,telefono) VALUES ('PAC-001','Laura','1990-05-12','8888-1111');
INSERT INTO Paciente (id,nombre,fechaNacimiento,telefono) VALUES ('PAC-002','Carlos','1985-08-23','8888-2222');
INSERT INTO Medico (id,nombre,clave,especialidad) VALUES ('MED-001','Dr. Salas','MED-001','Cardiologia');
INSERT INTO Medico (id,nombre,clave,especialidad) VALUES ('MED-002','Dra. Vargas','MED-002','Pediatria');
INSERT INTO Farmaceuta (id,nombre,clave) VALUES ('FAR-001','Carla Jimenez','FAR-001');
INSERT INTO Farmaceuta (id,nombre,clave) VALUES ('FAR-002','Luis Mora','FAR-002');
INSERT INTO Admin (id,nombre,clave) VALUES ('adm01','Administrador','1');
INSERT INTO Usuario (id,nombre,clave,rol) VALUES ('med01','Dr. House','1234','MED');
INSERT INTO Usuario (id,nombre,clave,rol) VALUES ('far01','Farmaceuta Ana','5678','FAR');
INSERT INTO Usuario (id,nombre,clave,rol) VALUES ('adm01','Administrador','1','ADM');

INSERT INTO EstadoReceta (estado) VALUES ('CONFECCIONADA');
INSERT INTO EstadoReceta (estado) VALUES ('EN_PROCESO');
INSERT INTO EstadoReceta (estado) VALUES ('LISTA');
INSERT INTO EstadoReceta (estado) VALUES ('ENTREGADA');

INSERT INTO Medicamento (codigo,nombre,descripcion) VALUES ('101','Paracetamol','Analgesico y antipiretico');
INSERT INTO Medicamento (codigo,nombre,descripcion) VALUES ('102','Paracetamol','Analgesico y antipiretico');

INSERT INTO Receta (id,medicoId,pacienteId,fechaConfeccion,fechaRetiro,estado,medicamentos) VALUES ('92f27bd5-bcda-4e7e-80e3-7c4fe7775600','MED-001','PAC-001','2025-10-13','2025-10-16','CONFECCIONADA','101');

INSERT INTO ItemReceta (medicamentoCodigo,descripcion,cantidad,indicaciones,duracionDias) VALUES ('101','Pastillas',10,'Tomar 1 cada dia',10);

INSERT INTO Prescripcion (fechaConfeccion,fechaRetiro,estado,items,paciente,medico) VALUES ('2025-10-13','2025-10-16','CONFECCIONADA','101','PAC-001','MED-001');


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
