CREATE TABLE personas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(10),
    edad INT,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(50)
);

CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    persona_id INT,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL,
    CONSTRAINT fk_clientes_persona FOREIGN KEY (persona_id) REFERENCES personas(id)
);

CREATE TABLE cuentas (
    id SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(50) UNIQUE NOT NULL,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo_inicial NUMERIC(18,2) NOT NULL,
    saldo_disponible NUMERIC(18,2) NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id VARCHAR(50) NOT NULL
);

CREATE TABLE movimientos (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor NUMERIC(18,2) NOT NULL,
    saldo NUMERIC(18,2) NOT NULL,
    cuenta_id INT NOT NULL REFERENCES cuentas(id)
);
