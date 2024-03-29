CREATE TABLE company
(
    id                    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    symbol                VARCHAR(255),
    exchange              VARCHAR(255),
    exchange_name         VARCHAR(255),
    exchange_segment      VARCHAR(255),
    exchange_suffix       VARCHAR(255),
    exchange_segment_name VARCHAR(255),
    name                  VARCHAR(255),
    date                  date,
    type                  VARCHAR(255),
    iex_id                VARCHAR(255),
    region                VARCHAR(255),
    currency              VARCHAR(255),
    is_enabled            BOOLEAN,
    figi                  VARCHAR(255),
    cik                   VARCHAR(255),
    lei                   VARCHAR(255),
    CONSTRAINT pk_company PRIMARY KEY (id)
)