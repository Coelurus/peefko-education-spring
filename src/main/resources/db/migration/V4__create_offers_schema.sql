CREATE SEQUENCE IF NOT EXISTS offers_seq START WITH 1 INCREMENT BY 50;

CREATE TYPE CREATOR_TYPE AS ENUM ('INDIVIDUAL', 'TEAM');

CREATE TABLE offers
(
    id           BIGSERIAL    NOT NULL,
    name         VARCHAR(255) NOT NULL,
    cost         BIGINT       NOT NULL,
    creator_type VARCHAR(255) NOT NULL,
    creator_id   BIGINT       NOT NULL,
    CONSTRAINT pk_offers PRIMARY KEY (id)
);

CREATE TABLE offers_services
(
    offer_id    BIGINT NOT NULL,
    services_id BIGINT NOT NULL
);

ALTER TABLE offers_services
    ADD CONSTRAINT fk_offser_on_my_service FOREIGN KEY (services_id) REFERENCES services (id);

ALTER TABLE offers_services
    ADD CONSTRAINT fk_offser_on_offer FOREIGN KEY (offer_id) REFERENCES offers (id);