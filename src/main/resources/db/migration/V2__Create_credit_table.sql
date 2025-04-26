CREATE TABLE IF NOT EXISTS credits (
    id UUID PRIMARY KEY,
    client_id UUID  NOT NULL,
    vehicle_model VARCHAR(5) NOT NULL,
    fee_type VARCHAR(9) NOT NULL,
    tax_value DECIMAL(3, 2) NOT NULL
);
