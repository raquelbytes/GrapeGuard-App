DROP DATABASE IF EXISTS GrapeGuardDatabase;

CREATE DATABASE GrapeGuardDatabase;

USE GrapeGuardDatabase;

CREATE TABLE Usuario (
    ID_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(255) NOT NULL, 
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL, 
	foto longblob
);

CREATE TABLE Vinedo (
    ID_vinedo INT AUTO_INCREMENT PRIMARY KEY,
    ID_usuario INT,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(255) NOT NULL,
    fecha_plantacion DATE,
    hectareas DECIMAL(10, 6),
    FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID_usuario)
);

CREATE TABLE Cosecha (
    ID_cosecha INT AUTO_INCREMENT PRIMARY KEY,
    ID_vinedo INT,
    nombre_variedad VARCHAR(100) NOT NULL,
    cantidad_uvas DECIMAL(10, 2) NOT NULL,
    precio_venta_kg DECIMAL(10, 2) NOT NULL,
    fecha_cosecha DATE,
    FOREIGN KEY (ID_vinedo) REFERENCES Vinedo(ID_vinedo)
);

CREATE TABLE Nota (
    ID_nota INT AUTO_INCREMENT PRIMARY KEY,
    ID_vinedo INT,
    nota TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    prioridad ENUM('Alta', 'Media', 'Baja') DEFAULT 'Media',
    FOREIGN KEY (ID_vinedo) REFERENCES Vinedo(ID_vinedo)
);

CREATE TABLE Tarea (
    ID_tarea INT AUTO_INCREMENT PRIMARY KEY,
    ID_vinedo INT,
    tarea VARCHAR(255) NOT NULL,
    estado ENUM('Pendiente', 'EnProgreso', 'Completada') DEFAULT 'Pendiente',
    recordatorio DATETIME,
    fecha_realizacion DATE,
    FOREIGN KEY (ID_vinedo) REFERENCES Vinedo(ID_vinedo)
);

CREATE TABLE Tratamiento (
    ID_tratamiento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
     cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Vinedo_Tratamiento (
    ID_vinedo_tratamiento INT AUTO_INCREMENT PRIMARY KEY,
    ID_vinedo INT,
    ID_tratamiento INT,
    FOREIGN KEY (ID_vinedo) REFERENCES Vinedo(ID_vinedo),
    FOREIGN KEY (ID_tratamiento) REFERENCES Tratamiento(ID_tratamiento)
);

INSERT INTO Usuario (nombre, apellido, email, contrasena) VALUES 
('Juan', 'Pérez', 'juan.perez@example.com', 'password123'),
('Ana', 'García', 'ana.garcia@example.com', 'password123'),
('Luis', 'Martínez', 'luis.martinez@example.com', 'password123'),
('María', 'López', 'maria.lopez@example.com', 'password123'),
('Carlos', 'González', 'carlos.gonzalez@example.com', 'password123');

-- Inserciones en la tabla Vinedo
INSERT INTO Vinedo (ID_usuario, nombre, ubicacion, fecha_plantacion, hectareas) VALUES 
(1, 'Viñedo La Esperanza', 'Rioja', '2010-03-15', 25.000000),
(2, 'Viñedo San Juan', 'Ribera del Duero', '2008-05-20', 30.000000),
(3, 'Viñedo Los Olivos', 'Priorat', '2015-04-10', 15.000000),
(4, 'Viñedo El Paraíso', 'Rías Baixas', '2012-07-25', 20.000000),
(5, 'Viñedo La Hacienda', 'La Mancha', '2011-09-05', 35.000000);

-- Inserciones en la tabla Cosecha
INSERT INTO Cosecha (ID_vinedo, nombre_variedad, cantidad_uvas, precio_venta_kg, fecha_cosecha) VALUES 
(1, 'Garnacha', 2000.50, 2.50, '2022-09-15'),
(2, 'Mencía', 1800.75, 3.00, '2022-09-20'),
(3, 'Godello', 1500.00, 2.80, '2022-09-25'),
(4, 'Albariño', 1700.20, 3.20, '2022-09-30'),
(5, 'Tempranillo', 2200.35, 2.70, '2022-10-05');

-- Inserciones en la tabla Nota
INSERT INTO Nota (ID_vinedo, nota, prioridad) VALUES 
(1, 'Revisión de riego automático', 'Alta'),
(2, 'Control de plagas', 'Media'),
(3, 'Podar viñedos', 'Baja'),
(4, 'Analizar calidad del suelo', 'Alta'),
(5, 'Planificación de fertilización', 'Media');

-- Inserciones en la tabla Tarea
INSERT INTO Tarea (ID_vinedo, tarea, estado, recordatorio, fecha_realizacion) VALUES 
(1, 'Revisar sistema de riego', 'Pendiente', '2023-06-15 08:00:00', '2023-06-20'),
(2, 'Fertilizar terreno', 'EnProgreso', '2023-06-17 09:00:00', '2023-06-25'),
(3, 'Controlar plagas', 'Completada', '2023-06-20 10:00:00', '2023-06-30'),
(4, 'Podar viñas', 'Pendiente', '2023-06-22 11:00:00', '2023-07-01'),
(5, 'Analizar calidad del suelo', 'EnProgreso', '2023-06-25 12:00:00', '2023-07-05');

-- Inserciones en la tabla Tratamiento
INSERT INTO Tratamiento (nombre, cantidad, precio_unitario) VALUES 
('Fungicida', 100, 5.50),
('Insecticida', 200, 4.75),
('Herbicida', 150, 6.00),
('Fertilizante', 300, 3.25),
('Nutrientes', 250, 7.10);

-- Inserciones en la tabla Vinedo_Tratamiento
INSERT INTO Vinedo_Tratamiento (ID_vinedo, ID_tratamiento) VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);


