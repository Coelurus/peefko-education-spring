START TRANSACTION;

INSERT INTO offers_services(offer_id, services_id)
VALUES (1, 3),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 1);

COMMIT;