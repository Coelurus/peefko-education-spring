CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 50;

CREATE TYPE STATUS AS ENUM ('CUSTOMER', 'SUPPLIER');

CREATE TABLE users
(
    id       BIGSERIAL    NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status   STATUS       NOT NULL,
    name     VARCHAR(255) NOT NULL,
    team_id  BIGINT,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
