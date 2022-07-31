CREATE TABLE stock
(
    company_name    VARCHAR(255) NOT NULL,
    currency        VARCHAR(255),
    previous_volume INTEGER,
    volume          INTEGER,
    latest_price    INTEGER,
    CONSTRAINT pk_stock PRIMARY KEY (company_name)
)