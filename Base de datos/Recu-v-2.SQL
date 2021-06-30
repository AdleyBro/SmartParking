/*
Created: 24/01/2021
Modified: 30/06/2021
Model: Modelo de tablas
Database: MySQL 5.0
*/

-- Create tables section -------------------------------------------------

-- Table Cliente

CREATE TABLE `Cliente`
(
  `NombreDeUsuario` Varchar(20) NOT NULL,
  `Nombre` Longtext NOT NULL,
  `Email` Longtext NOT NULL,
  `Tlf` Bigint NOT NULL,
  `FechaDeRegistro` Datetime NOT NULL,
  `Password` Text NOT NULL,
  `EstaDentro` Boolean NOT NULL
)
;

ALTER TABLE `Cliente` ADD PRIMARY KEY (`NombreDeUsuario`)
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
  `Latitud` Double NOT NULL,
  `Longitud` Double NOT NULL
)
;

ALTER TABLE `Parking` ADD PRIMARY KEY (`IdParking`)
;

-- Table Plaza

CREATE TABLE `Plaza`
(
  `IdPlaza` Bigint NOT NULL,
  `EstaOcupado` Boolean NOT NULL,
  `EsReservable` Boolean NOT NULL,
  `IdParking` Bigint NOT NULL
)
;

ALTER TABLE `Plaza` ADD PRIMARY KEY (`IdPlaza`,`IdParking`)
;

-- Table Historial plazas

CREATE TABLE `Historial plazas`
(
  `FechaHoraEntrada` Datetime NOT NULL,
  `EsReservable` Boolean NOT NULL,
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
  `IdCliente` Varchar(20) NOT NULL,
  `IdPlaza` Bigint NOT NULL,
  `IdParking` Bigint NOT NULL
)
;

CREATE INDEX `IX_Reserva2` ON `Reserva` (`IdPlaza`,`IdParking`)
;

ALTER TABLE `Reserva` ADD PRIMARY KEY (`FechaHoraInicio`,`IdCliente`)
;

-- Table HistorialEntrada

CREATE TABLE `HistorialEntrada`
(
  `FechaEntrada` Char(20) NOT NULL,
  `NombreDeUsuario` Varchar(20) NOT NULL,
  `IdParking` Bigint NOT NULL
)
;

ALTER TABLE `HistorialEntrada` ADD PRIMARY KEY (`FechaEntrada`,`NombreDeUsuario`,`IdParking`)
;

-- Table HistorialSalida

CREATE TABLE `HistorialSalida`
(
  `FechaSalida` Datetime NOT NULL,
  `NombreDeUsuario` Varchar(20) NOT NULL,
  `IdParking` Bigint NOT NULL
)
;

ALTER TABLE `HistorialSalida` ADD PRIMARY KEY (`FechaSalida`,`NombreDeUsuario`,`IdParking`)
;

-- Create foreign keys (relationships) section -------------------------------------------------

ALTER TABLE `Tabla de precios` ADD CONSTRAINT `Se asocia con` FOREIGN KEY (`IdParking`) REFERENCES `Parking` (`IdParking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Plaza` ADD CONSTRAINT `Esta compuesto de` FOREIGN KEY (`IdParking`) REFERENCES `Parking` (`IdParking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Historial plazas` ADD CONSTRAINT `Mantiene` FOREIGN KEY (`IdPlaza`, `IdParking`) REFERENCES `Plaza` (`IdPlaza`, `IdParking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Reserva` ADD CONSTRAINT `Reserva1` FOREIGN KEY (`IdCliente`) REFERENCES `Cliente` (`NombreDeUsuario`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Reserva` ADD CONSTRAINT `Reserva2` FOREIGN KEY (`IdPlaza`, `IdParking`) REFERENCES `Plaza` (`IdPlaza`, `IdParking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `HistorialSalida` ADD CONSTRAINT `Relationship3` FOREIGN KEY (`NombreDeUsuario`) REFERENCES `Cliente` (`NombreDeUsuario`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `HistorialEntrada` ADD CONSTRAINT `Relationship4` FOREIGN KEY (`NombreDeUsuario`) REFERENCES `Cliente` (`NombreDeUsuario`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `HistorialEntrada` ADD CONSTRAINT `Relationship5` FOREIGN KEY (`IdParking`) REFERENCES `Parking` (`IdParking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `HistorialSalida` ADD CONSTRAINT `Relationship6` FOREIGN KEY (`IdParking`) REFERENCES `Parking` (`IdParking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

