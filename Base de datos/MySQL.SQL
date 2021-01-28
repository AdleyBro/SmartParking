/*
Created: 24/01/2021
Modified: 24/01/2021
Model: Modelo de tablas
Database: MySQL 5.0
*/

-- Create tables section -------------------------------------------------

-- Table Cliente

CREATE TABLE `Cliente`
(
  `IdUsuario` Bigint NOT NULL,
  `Nombre` Longtext NOT NULL,
  `Email` Longtext NOT NULL,
  `Tlf` Bigint NOT NULL,
  `FechaDeRegistro` Datetime NOT NULL,
  `NombreDeUsuario` Varchar(20) NOT NULL
)
;

CREATE INDEX `IX_Tiene` ON `Cliente` (`NombreDeUsuario`)
;

ALTER TABLE `Cliente` ADD PRIMARY KEY (`IdUsuario`)
;

-- Table Usuario

CREATE TABLE `Usuario`
(
  `NombreDeUsuario` Varchar(20) NOT NULL,
  `Password` Longtext NOT NULL,
  `UltimoAccesoParking` Timestamp NOT NULL
)
;

ALTER TABLE `Usuario` ADD PRIMARY KEY (`NombreDeUsuario`)
;

-- Table Tabla de precios

CREATE TABLE `Tabla de precios`
(
  `Hora` Time NOT NULL,
  `Precio` Decimal(10,2) NOT NULL,
  `IdParking` Bigint NOT NULL
)
;

ALTER TABLE `Tabla de precios` ADD PRIMARY KEY (`Hora`,`IdParking`)
;

-- Table Parking

CREATE TABLE `Parking`
(
  `IdParking` Bigint NOT NULL,
  `Ciudad` Longtext NOT NULL,
  `Direccion` Longtext NOT NULL,
  `Coordenadas` Point NOT NULL
)
;

ALTER TABLE `Parking` ADD PRIMARY KEY (`IdParking`)
;

-- Table Plaza

CREATE TABLE `Plaza`
(
  `IdPlaza` Bigint NOT NULL,
  `EstaOcupado` Bool NOT NULL,
  `EsReservable` Bool NOT NULL,
  `IdParking` Bigint NOT NULL
)
;

ALTER TABLE `Plaza` ADD PRIMARY KEY (`IdPlaza`,`IdParking`)
;

-- Table Historial plazas

CREATE TABLE `Historial plazas`
(
  `FechaHoraEntrada` Datetime NOT NULL,
  `EraReservable` Bool NOT NULL,
  `FechaHoraSalida` Datetime NOT NULL,
  `IdPlaza` Bigint NOT NULL,
  `IdParking` Bigint NOT NULL
)
;

ALTER TABLE `Historial plazas` ADD PRIMARY KEY (`FechaHoraEntrada`,`IdPlaza`,`IdParking`)
;

-- Table Reserva

CREATE TABLE `Reserva`
(
  `FechaHoraInicio` Datetime NOT NULL,
  `FechaHoraFin` Datetime NOT NULL,
  `PrecioPagado` Decimal(10,2) NOT NULL,
  `IdUsuario` Bigint NOT NULL,
  `IdPlaza` Bigint NOT NULL,
  `IdParking` Bigint NOT NULL
)
;

CREATE INDEX `IX_Reserva2` ON `Reserva` (`IdPlaza`,`IdParking`)
;

ALTER TABLE `Reserva` ADD PRIMARY KEY (`FechaHoraInicio`,`IdUsuario`)
;

-- Create foreign keys (relationships) section -------------------------------------------------

ALTER TABLE `Cliente` ADD CONSTRAINT `Tiene` FOREIGN KEY (`NombreDeUsuario`) REFERENCES `Usuario` (`NombreDeUsuario`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Tabla de precios` ADD CONSTRAINT `Se asocia con` FOREIGN KEY (`IdParking`) REFERENCES `Parking` (`IdParking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Plaza` ADD CONSTRAINT `Esta compuesto de` FOREIGN KEY (`IdParking`) REFERENCES `Parking` (`IdParking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Historial plazas` ADD CONSTRAINT `Mantiene` FOREIGN KEY (`IdPlaza`, `IdParking`) REFERENCES `Plaza` (`IdPlaza`, `IdParking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Reserva` ADD CONSTRAINT `Reserva1` FOREIGN KEY (`IdUsuario`) REFERENCES `Cliente` (`IdUsuario`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Reserva` ADD CONSTRAINT `Reserva2` FOREIGN KEY (`IdPlaza`, `IdParking`) REFERENCES `Plaza` (`IdPlaza`, `IdParking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

