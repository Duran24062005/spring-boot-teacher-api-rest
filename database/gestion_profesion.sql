CREATE DATABASE IF NOT EXISTS gestion_profesion
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gestion_profesion;

DROP TABLE IF EXISTS persona;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS profesion;

CREATE TABLE profesion (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(120) NOT NULL,
    role VARCHAR(30) NOT NULL,
    enabled TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuario_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE persona (
    id BIGINT NOT NULL AUTO_INCREMENT,
    documento VARCHAR(30) NOT NULL,
    nombre VARCHAR(60) NOT NULL,
    apellido VARCHAR(60) NOT NULL,
    edad INT NOT NULL,
    salario DECIMAL(12, 2) NOT NULL,
    profesion_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_persona_documento (documento),
    CONSTRAINT fk_persona_profesion
        FOREIGN KEY (profesion_id) REFERENCES profesion (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO usuario (username, password, role, enabled) VALUES
    ('admin', '$2a$10$7j4a9VebWpA4LlvCZcZ5e.Qh0Bbsf9KqGOUGMR5f8TUonSGCL3a1m', 'ADMIN', 1),
    ('demo', '$2a$10$7j4a9VebWpA4LlvCZcZ5e.Qh0Bbsf9KqGOUGMR5f8TUonSGCL3a1m', 'USER', 1);

INSERT INTO profesion (nombre, descripcion) VALUES
    ('Ingenieria de Sistemas', 'Profesional orientado al desarrollo de software y soluciones tecnológicas'),
    ('Diseno Grafico', 'Profesional orientado a identidad visual, interfaces y piezas creativas'),
    ('Contaduria', 'Profesional orientado a procesos contables, financieros y tributarios');

INSERT INTO persona (documento, nombre, apellido, edad, salario, profesion_id) VALUES
    ('1007999211', 'Laura', 'Martinez', 27, 4200000.00, 1),
    ('1007999212', 'Carlos', 'Rojas', 31, 3100000.00, 2),
    ('1007999213', 'Andrea', 'Lopez', 29, 3600000.00, 3),
    ('1007999214', 'Mateo', 'Herrera', 24, 2800000.00, 1);
