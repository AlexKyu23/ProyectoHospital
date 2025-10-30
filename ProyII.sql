-- Reiniciar configuración temporal
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Crear base de datos y usarla
DROP DATABASE IF EXISTS HospitalProy;
CREATE DATABASE HospitalProy;
USE HospitalProy;

-- Tabla EstadoReceta
CREATE TABLE EstadoReceta (
                              estado VARCHAR(45) NOT NULL PRIMARY KEY
) ENGINE=InnoDB;

-- Tabla Paciente
CREATE TABLE Paciente (
                          id VARCHAR(10) NOT NULL PRIMARY KEY,
                          nombre VARCHAR(45),
                          fechaNacimiento DATE,
                          telefono VARCHAR(15)
) ENGINE=InnoDB;

-- Tabla Medico
CREATE TABLE Medico (
                        id VARCHAR(10) NOT NULL PRIMARY KEY,
                        nombre VARCHAR(45),
                        clave VARCHAR(45),
                        especialidad VARCHAR(45)
) ENGINE=InnoDB;

-- Tabla Medicamento
CREATE TABLE Medicamento (
                             codigo VARCHAR(15) NOT NULL PRIMARY KEY,
                             nombre VARCHAR(45),
                             descripcion VARCHAR(255)
) ENGINE=InnoDB;

-- Tabla Receta
CREATE TABLE Receta (
                        id VARCHAR(10) NOT NULL PRIMARY KEY,
                        medicoId VARCHAR(10),
                        pacienteId VARCHAR(10),
                        fechaConfeccion DATE,
                        fechaRetiro DATE,
                        estado VARCHAR(45) NOT NULL,
                        FOREIGN KEY (medicoId) REFERENCES Medico(id) ON DELETE CASCADE ON UPDATE CASCADE,
                        FOREIGN KEY (pacienteId) REFERENCES Paciente(id) ON DELETE CASCADE ON UPDATE CASCADE,
                        FOREIGN KEY (estado) REFERENCES EstadoReceta(estado) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Tabla ItemReceta
CREATE TABLE ItemReceta (
                            itemRecetaId VARCHAR(10) NOT NULL PRIMARY KEY,
                            recetaId VARCHAR(10) NOT NULL,
                            medicamentoCodigo VARCHAR(15) NOT NULL,
                            descripcion VARCHAR(50),
                            cantidad INT,
                            indicaciones VARCHAR(500),
                            duracionDias INT,
                            FOREIGN KEY (recetaId) REFERENCES Receta(id) ON DELETE CASCADE ON UPDATE CASCADE,
                            FOREIGN KEY (medicamentoCodigo) REFERENCES Medicamento(codigo) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Tabla Usuario
CREATE TABLE Usuario (
                         id VARCHAR(10) NOT NULL PRIMARY KEY,
                         nombre VARCHAR(45),
                         clave VARCHAR(45),
                         rol VARCHAR(45)
) ENGINE=InnoDB;

-- Tabla Farmaceuta
CREATE TABLE Farmaceuta (
                            id VARCHAR(10) NOT NULL PRIMARY KEY,
                            nombre VARCHAR(45),
                            clave VARCHAR(45)
) ENGINE=InnoDB;

-- Tabla Admin
CREATE TABLE Admin (
                       id VARCHAR(10) NOT NULL PRIMARY KEY,
                       nombre VARCHAR(45),
                       clave VARCHAR(45)
) ENGINE=InnoDB;

-- Tabla Prescripcion (columnas consistentes con DAO)
CREATE TABLE Prescripcion (
                              numero INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              fechaConfeccion DATETIME,
                              fechaRetiro DATETIME,
                              estado VARCHAR(45),
                              itemRecetaId VARCHAR(10) NOT NULL,
                              paciente VARCHAR(10) NOT NULL,
                              medico VARCHAR(10) NOT NULL,
                              FOREIGN KEY (itemRecetaId) REFERENCES ItemReceta(itemRecetaId) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (paciente) REFERENCES Paciente(id) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (medico) REFERENCES Medico(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Insertar estados válidos
INSERT INTO EstadoReceta VALUES
                             ('CONFECCIONADA'),
                             ('EN_PROCESO'),
                             ('LISTA'),
                             ('ENTREGADA');

-- Insertar Medicamentos de prueba
INSERT INTO Medicamento (codigo, nombre, descripcion) VALUES
                                                          ('101', 'Paracetamol', 'Analgesico y antipiretico'),
                                                          ('102', 'Paracetamol', 'Analgesico y antipiretico'),
                                                          ('999', 'Ibuprofeno', 'Antiinflamatorio');

-- Insertar Pacientes de prueba
INSERT INTO Paciente (id, nombre, fechaNacimiento, telefono) VALUES
                                                                 ('PAC-001', 'Laura', '1990-05-12', '8888-1111'),
                                                                 ('PAC-002', 'Carlos', '1985-07-23', '8888-2222'),
                                                                 ('PAC-100', 'Juan Perez', '1990-01-15', '8888-3333');

-- Insertar Medicos de prueba
INSERT INTO Medico (id, nombre, clave, especialidad) VALUES
                                                         ('MED-001', 'Dr. Salas', 'MED-001', 'Cardiologia'),
                                                         ('MED-002', 'Dra. Vargas', 'MED-002', 'Pediatria'),
                                                         ('MED-100', 'Dr. Test', '1234', 'Cardiologia');

-- Insertar Farmaceutas de prueba
INSERT INTO Farmaceuta (id, nombre, clave) VALUES
                                               ('FAR-001', 'Carla Jimenez', 'FAR-001'),
                                               ('FAR-002', 'Luis Mora', 'FAR-002'),
                                               ('FAR-100', 'Ana Farm', '9999');

-- Insertar Admin de prueba
INSERT INTO Admin (id, nombre, clave) VALUES ('adm01', 'Administrador', '1');

-- Insertar Usuarios
INSERT INTO Usuario (id, nombre, clave, rol) VALUES
                                                 ('med01', 'Dr. House', '1234', 'MED'),
                                                 ('far01', 'Farmaceuta Ana', '5678', 'FAR'),
                                                 ('adm01', 'Administrador', '1', 'ADM');
-- --------------------------
-- Crear Receta
-- --------------------------
INSERT INTO Receta (id, medicoId, pacienteId, fechaConfeccion, fechaRetiro, estado)
VALUES ('REC-200', 'MED-001', 'PAC-001', '2025-10-30', '2025-11-06', 'CONFECCIONADA');

-- --------------------------
-- Crear Item de Receta
-- --------------------------
INSERT INTO ItemReceta (itemRecetaId, recetaId, medicamentoCodigo, descripcion, cantidad, indicaciones, duracionDias)
VALUES ('ITEM-200', 'REC-200', '101', 'Paracetamol 500mg', 10, 'Tomar cada 12 horas', 5);

-- --------------------------
-- Crear Prescripción
-- --------------------------
INSERT INTO Prescripcion (fechaConfeccion, fechaRetiro, estado, itemRecetaId, paciente, medico)
VALUES ('2025-10-30 10:00:00', '2025-11-06 10:00:00', 'CONFECCIONADA', 'ITEM-200', 'PAC-001', 'MED-001');
-- Restaurar configuración
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

