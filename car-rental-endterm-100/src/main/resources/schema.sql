BEGIN;

CREATE SCHEMA IF NOT EXISTS car_rental;
SET search_path TO car_rental;

DROP TABLE IF EXISTS payments CASCADE;
DROP TABLE IF EXISTS rentals CASCADE;
DROP TABLE IF EXISTS vehicles CASCADE;
DROP TABLE IF EXISTS customers CASCADE;

CREATE TABLE customers (
  customer_id BIGSERIAL PRIMARY KEY,
  first_name  TEXT NOT NULL,
  last_name   TEXT NOT NULL,
  phone       TEXT UNIQUE,
  email       TEXT UNIQUE,
  created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE vehicles (
  vehicle_id   BIGSERIAL PRIMARY KEY,
  type         TEXT NOT NULL CHECK (type IN ('ECONOMY','SUV','LUXURY')),
  brand        TEXT NOT NULL,
  model        TEXT NOT NULL,
  year         INT  NOT NULL CHECK (year BETWEEN 1980 AND 2100),
  daily_price  NUMERIC(12,2) NOT NULL CHECK (daily_price >= 0),
  status       TEXT NOT NULL DEFAULT 'AVAILABLE' CHECK (status IN ('AVAILABLE','RENTED','MAINTENANCE')),
  created_at   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE rentals (
  rental_id    BIGSERIAL PRIMARY KEY,
  customer_id  BIGINT NOT NULL REFERENCES customers(customer_id) ON DELETE RESTRICT,
  vehicle_id   BIGINT NOT NULL REFERENCES vehicles(vehicle_id)   ON DELETE RESTRICT,
  start_date   DATE NOT NULL,
  end_date     DATE NOT NULL,
  total_price  NUMERIC(12,2) NOT NULL CHECK (total_price >= 0),
  status       TEXT NOT NULL DEFAULT 'CREATED' CHECK (status IN ('CREATED','ACTIVE','COMPLETED','CANCELLED')),
  created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
  CHECK (end_date >= start_date)
);

CREATE TABLE payments (
  payment_id  BIGSERIAL PRIMARY KEY,
  rental_id   BIGINT NOT NULL UNIQUE REFERENCES rentals(rental_id) ON DELETE CASCADE,
  method      TEXT NOT NULL CHECK (method IN ('CARD','CASH','TRANSFER')),
  amount      NUMERIC(12,2) NOT NULL CHECK (amount >= 0),
  paid_at     TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_rentals_customer_id ON rentals(customer_id);
CREATE INDEX idx_rentals_vehicle_id  ON rentals(vehicle_id);
CREATE INDEX idx_vehicles_status     ON vehicles(status);

COMMIT;
