START TRANSACTION;

INSERT INTO users (username, password, status, name)
VALUES ('user', 'user', 'customer', 'IM PAYING'),
       ('admin', 'admin', 'supplier', 'MONEYZ'),
       ('creator', 'creator', 'supplier', 'I CREATE STUFF');


COMMIT;