CREATE TABLE log_record
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    record VARCHAR(255),
    CONSTRAINT pk_logrecord PRIMARY KEY (id)
)