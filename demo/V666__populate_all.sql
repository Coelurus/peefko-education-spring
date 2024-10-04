START TRANSACTION;

INSERT INTO services (id, name, description)
VALUES (0, 'NAP', 'Like sleeping'),
       (1, 'COFFEE SERVICE', 'I drink all your milk'),
       (2, 'DESK INSPECTION', 'Dont even ask');

INSERT INTO users (id, username, password, status, name)
VALUES (0, 'user', '$2a$10$bn5VZttAU9R.t0kjqHMvyObBe7WgNTlQKjnrhkSHAAXnTmPjYIFIu', 'CUSTOMER', 'IM PAYING'),
       (1, 'admin', '$2a$10$tr2M6phHFwYA7aUslseGgOFGZq9RPZ61NscCfuWa2WBqnnC.d9AZm', 'SUPPLIER', 'MONEYZ'),
       (2, 'creator', '$2a$10$mzeho1bfdaTUhRPUGwP1weyRliR3IeyFWyCiPgUKpRPg3mm1mA.XG ', 'SUPPLIER', 'I CREATE STUFF');


INSERT INTO teams (id, leader_id)
VALUES (0, 1);


UPDATE users
SET team_id = 0
where id = 1
   or id = 2;


INSERT INTO offers (id, name, cost, creator_type, creator_id)
VALUES (0, 'SECURITY', 2000, 'TEAM', 0),
       (1, 'All exclusive', 666, 'INDIVIDUAL', 1),
       (2, 'Home Page Button', 100000, 'INDIVIDUAL', 2);

SELECT setval(pg_get_serial_sequence('offers', 'id'), (SELECT MAX(id) FROM offers));

INSERT INTO offers_services(offer_id, services_id)
VALUES (0, 2),
       (1, 0),
       (1, 1),
       (1, 2),
       (2, 0);

INSERT INTO orders (id, offer_id, customer_id, completed, payed)
VALUES (0, 0, 0, false, false),
       (1, 1, 1, true, false);

SELECT setval(pg_get_serial_sequence('orders', 'id'), (SELECT MAX(id) FROM orders));

COMMIT;