INSERT INTO client(firstname, lastname, city, phone , email, street_Address, username, password)
VALUES('Jan','Kowalski', 'Warszawa', '888999123', 'jan@gmail.com', 'Słoneczna 12', 'admin', '{noop}admin');

INSERT INTO client_role(user_id_client, role)
VALUES (1, 'ROLE_ADMIN');