/*
Created: 09/01/2021
Modified: 09/01/2021
Model: Modelo en MySQL
Database: MySQL 8.0
*/

-- Create tables section -------------------------------------------------

-- Table Cliente

CREATE TABLE `Cliente`
(
  `Id Usuario` Bigint NOT NULL,
  `Nombre` Longtext NOT NULL,
  `Email` Longtext NOT NULL,
  `Teléfono` Bigint NOT NULL,
  `Nombre de usuario` Longtext NOT NULL
)
;

CREATE INDEX `IX_Tiene` ON `Cliente` (`Nombre de usuario`)
;

ALTER TABLE `Cliente` ADD PRIMARY KEY (`Id Usuario`)
;

-- Table Usuario

CREATE TABLE `Usuario`
(
  `Nombre de usuario` Longtext NOT NULL,
  `Contraseña` Longtext NOT NULL
)
;

ALTER TABLE `Usuario` ADD PRIMARY KEY (`Nombre de usuario`)
;

-- Table Tabla de precios

CREATE TABLE `Tabla de precios`
(
  `Hora` Time NOT NULL,
  `Id parking` Bigint NOT NULL,
  `Precio` Decimal(10,2) NOT NULL
)
;

ALTER TABLE `Tabla de precios` ADD PRIMARY KEY (`Hora`, `Id parking`)
;

-- Table Parking

CREATE TABLE `Parking`
(
  `Id parking` Bigint NOT NULL,
  `Ciudad` Longtext NOT NULL,
  `Dirección` Longtext NOT NULL
)
;

ALTER TABLE `Parking` ADD PRIMARY KEY (`Id parking`)
;

-- Table Plaza

CREATE TABLE `Plaza`
(
  `Id plaza` Bigint NOT NULL,
  `Id parking` Bigint NOT NULL,
  `EstaOcupado` Bool NOT NULL,
  `EsReservable` Bigint NOT NULL
)
;

ALTER TABLE `Plaza` ADD PRIMARY KEY (`Id plaza`, `Id parking`)
;

-- Table Historial plazas

CREATE TABLE `Historial plazas`
(
  `Fecha hora` Datetime NOT NULL,
  `Id plaza` Bigint NOT NULL,
  `Id parking` Bigint NOT NULL,
  `EraReservable` Bool NOT NULL,
  `EstabaOcupado` Bool NOT NULL
)
;

ALTER TABLE `Historial plazas` ADD PRIMARY KEY (`Fecha hora`, `Id plaza`, `Id parking`)
;

-- Table Reserva

CREATE TABLE `Reserva`
(
  `Fecha hora inicio` Datetime NOT NULL,
  `Id Usuario` Bigint NOT NULL,
  `Fecha hora fin` Datetime NOT NULL,
  `Precio pagado` Decimal(10,2) NOT NULL,
  `Id plaza` Bigint NOT NULL,
  `Id parking` Bigint NOT NULL
)
;

CREATE INDEX `IX_Reserva2` ON `Reserva` (`Id plaza`, `Id parking`)
;

ALTER TABLE `Reserva` ADD PRIMARY KEY (`Fecha hora inicio`, `Id Usuario`)
;

-- Create foreign keys (relationships) section -------------------------------------------------

ALTER TABLE `Cliente` ADD CONSTRAINT `Tiene` FOREIGN KEY (`Nombre de usuario`) REFERENCES `Usuario` (`Nombre de usuario`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Tabla de precios` ADD CONSTRAINT `Se asocia con` FOREIGN KEY (`Id parking`) REFERENCES `Parking` (`Id parking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Plaza` ADD CONSTRAINT `Esta compuesto de` FOREIGN KEY (`Id parking`) REFERENCES `Parking` (`Id parking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Historial plazas` ADD CONSTRAINT `Mantiene` FOREIGN KEY (`Id plaza`, `Id parking`) REFERENCES `Plaza` (`Id plaza`, `Id parking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Reserva` ADD CONSTRAINT `Reserva1` FOREIGN KEY (`Id Usuario`) REFERENCES `Cliente` (`Id Usuario`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Reserva` ADD CONSTRAINT `Reserva2` FOREIGN KEY (`Id plaza`, `Id parking`) REFERENCES `Plaza` (`Id plaza`, `Id parking`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

