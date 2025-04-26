-- index for table clients
CREATE INDEX idx_client_age ON clients(age);
CREATE INDEX idx_client_income ON clients(income);

-- index for table credits
CREATE INDEX idx_credit_fee_type ON credits(fee_type);
CREATE INDEX idx_credit_client_id ON credits(client_id);