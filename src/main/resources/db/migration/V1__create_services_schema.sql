CREATE SEQUENCE IF NOT EXISTS services_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE services
(
    id          BIGSERIAL    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    CONSTRAINT pk_services PRIMARY KEY (id)
);