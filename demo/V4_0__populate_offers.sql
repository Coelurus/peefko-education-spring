START TRANSACTION;

INSERT INTO offers (name, cost, creator_type, creator_id)
VALUES ('SECURITY', 2000, 'team', 2),
       ('All exclusive', 666, 'individual', 2),
       ('Home Page Button', 100000, 'individual', 3);

COMMIT;