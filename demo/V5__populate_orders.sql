START TRANSACTION;

INSERT INTO orders (offer_id, customer_id, completed, payed)
VALUES (1, 1, false, false),
       (2, 2, true, false);

COMMIT;