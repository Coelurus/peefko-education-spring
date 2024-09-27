START TRANSACTION;

INSERT INTO services (name, description)
VALUES ('NAP', 'Like sleeping'),
       ('COFFEE SERVICE', 'I drink all your milk'),
       ('DESK INSPECTION', 'Dont even ask');

INSERT INTO users (username, password, status, name)
VALUES ('user', 'user', 'customer', 'IM PAYING'),
       ('admin', 'admin', 'supplier', 'MONEYZ'),
       ('creator', 'creator', 'supplier', 'I CREATE STUFF');


INSERT INTO teams (leader_id)
VALUES (2);

UPDATE users
SET team_id = 2
where id = 2
   or id = 3;


INSERT INTO offers (name, cost, creator_type, creator_id)
VALUES ('SECURITY', 2000, 'team', 2),
       ('All exclusive', 666, 'individual', 2),
       ('Home Page Button', 100000, 'individual', 3);


INSERT INTO offers_services(offer_id, services_id)
VALUES (1, 3),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 1);


INSERT INTO orders (offer_id, customer_id, completed, payed)
VALUES (1, 1, false, false),
       (2, 2, true, false);


COMMIT;