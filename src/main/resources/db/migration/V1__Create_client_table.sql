CREATE TABLE IF NOT EXISTS clients (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age smallint NOT NULL,
    income DECIMAL(10, 2) NOT NULL
);
