START TRANSACTION;

INSERT INTO teams (leader_id)
VALUES (2);

UPDATE users
SET team_id = 2
where id = 2
   or id = 3;

COMMIT;
