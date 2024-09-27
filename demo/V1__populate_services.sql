START TRANSACTION;

INSERT INTO services (name, description)
VALUES ('NAP', 'Like sleeping'),
       ('COFFEE SERVICE', 'I drink all your milk'),
       ('DESK INSPECTION', 'Dont even ask');

COMMIT;