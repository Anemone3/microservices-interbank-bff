-- Extensión UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Enums
DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'documenttype') THEN
      CREATE TYPE documenttype AS ENUM ('DNI', 'CARNET_EXTRANJERIA', 'RUC', 'UNKNOWN');
   END IF;
   IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'statuscustomer') THEN
      CREATE TYPE statuscustomer AS ENUM ('PENDIENTE', 'ACTIVO', 'BLOQUEADO');
   END IF;
END $$;

-- Tabla Customer
CREATE TABLE IF NOT EXISTS t_customer (
   id VARCHAR(60) PRIMARY KEY DEFAULT uuid_generate_v4()::text,  
   first_name VARCHAR(50) NOT NULL,
   middle_name VARCHAR(50),
   pat_surname VARCHAR(50) NOT NULL,
   mat_surname VARCHAR(50) NOT NULL,
   document_type documenttype NOT NULL,             
   document_number VARCHAR(15) NOT NULL UNIQUE,
   email VARCHAR(50) NOT NULL,
   phone VARCHAR(9) NOT NULL,
   address VARCHAR(50) NOT NULL,
   status_account statuscustomer,                       
   registration_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Tabla Productos financieros
CREATE TABLE IF NOT EXISTS t_finance_products (
   id VARCHAR(60) PRIMARY KEY DEFAULT uuid_generate_v4()::text,  
   product_type VARCHAR(50) UNIQUE NOT NULL,
   description TEXT,
   interest_rate NUMERIC(5,2)
);

-- Relación cliente - producto
CREATE TABLE IF NOT EXISTS t_customer_finance_products (
   id VARCHAR(60) PRIMARY KEY DEFAULT uuid_generate_v4()::text,  
   customer_id VARCHAR(60) NOT NULL,
   product_id VARCHAR(60) NOT NULL REFERENCES t_finance_products(id),
   account_number VARCHAR(20) UNIQUE NOT NULL,
   balance NUMERIC(10,2) NOT NULL,
   creation_date TIMESTAMP DEFAULT NOW()
);

-- Insertar productos financieros
INSERT INTO t_finance_products (product_type, description, interest_rate) VALUES
('Cuenta de Ahorros', 'Cuenta básica con disponibilidad inmediata', 0.50),
('Cuenta Sueldo', 'Cuenta sueldo Interbank con beneficios', 0.20),
('Tarjeta de Crédito Visa', 'Tarjeta clásica Interbank', 35.00),
('Préstamo Personal', 'Préstamo en cuotas fijas', 18.50),
('CTS', 'Cuenta de Compensación por Tiempo de Servicios', 5.00)
ON CONFLICT (product_type) DO NOTHING;

-- Insertar clientes
INSERT INTO t_customer (first_name, middle_name, pat_surname, mat_surname, document_type, document_number, email, phone, address, status_account)
VALUES
('Juan', 'Carlos', 'Ramírez', 'García', 'DNI', '45678901', 'juan.ramirez@gmail.com', '987654321', 'Av. Arequipa 123, Lima', 'ACTIVO'),
('María', NULL, 'Fernández', 'Lopez', 'DNI', '87654321', 'maria.fernandez@gmail.com', '912345678', 'Jr. Puno 456, Lima', 'ACTIVO'),
('Pedro', NULL, 'Sánchez', 'Torres', 'DNI', '11223344', 'pedro.sanchez@gmail.com', '998877665', 'Av. Universitaria 890, Callao', 'PENDIENTE'),
('Lucía', 'Gabriela', 'Castro', 'Rojas', 'CARNET_EXTRANJERIA', 'CE987654', 'lucia.castro@hotmail.com', '976543210', 'Av. Primavera 450, Surco', 'ACTIVO'),
('Empresa', NULL, 'Comercial', 'Andina', 'RUC', '20123456789', 'contacto@comercialandina.pe', '015555555', 'Av. Industrial 234, Ate', 'ACTIVO')
ON CONFLICT (document_number) DO NOTHING;

-- Relacionar clientes con productos
INSERT INTO t_customer_finance_products (customer_id, product_id, account_number, balance)
SELECT c.id, p.id, '1001-45678901-01', 2500.50
FROM t_customer c, t_finance_products p
WHERE c.document_number = '45678901' AND p.product_type = 'Cuenta de Ahorros'
ON CONFLICT (account_number) DO NOTHING;

INSERT INTO t_customer_finance_products (customer_id, product_id, account_number, balance)
SELECT c.id, p.id, '1002-87654321-01', 12500.75
FROM t_customer c, t_finance_products p
WHERE c.document_number = '87654321' AND p.product_type = 'Cuenta Sueldo'
ON CONFLICT (account_number) DO NOTHING;

INSERT INTO t_customer_finance_products (customer_id, product_id, account_number, balance)
SELECT c.id, p.id, '4111-11223344-01', -1500.00
FROM t_customer c, t_finance_products p
WHERE c.document_number = '11223344' AND p.product_type = 'Tarjeta de Crédito Visa'
ON CONFLICT (account_number) DO NOTHING;

INSERT INTO t_customer_finance_products (customer_id, product_id, account_number, balance)
SELECT c.id, p.id, '2001-987654CE-01', 6000.00
FROM t_customer c, t_finance_products p
WHERE c.document_number = 'CE987654' AND p.product_type = 'Préstamo Personal'
ON CONFLICT (account_number) DO NOTHING;

INSERT INTO t_customer_finance_products (customer_id, product_id, account_number, balance)
SELECT c.id, p.id, '3001-20123456789-01', 50000.00
FROM t_customer c, t_finance_products p
WHERE c.document_number = '20123456789' AND p.product_type = 'CTS'
ON CONFLICT (account_number) DO NOTHING;

