INSERT INTO client(firstname, lastname, username, password, address)
VALUES('Jan','Kowalski', 'kowal','{noop}kowal','Warszawa'),('Maria','Zawadzka', 'maria', '{noop}maria','Wrocław');

INSERT INTO client_order(id_client)
VALUES (1), (2);

INSERT INTO client_role(user_id_client, role)
VALUES (1, 'ROLE_ADMIN'), (1, 'ROLE_USER'), (2, 'ROLE_USER');

INSERT INTO part(part_name, price, selected, category)
VALUES ('Filtron', 50.0, false ,'FILTERS'), ('Sprężarka',1500.0,false, 'AIR_CONDITIONING'),
       ('Motul', 150.0, false ,'OILS'), ('Continental', 300.20, false ,'TIRES');

INSERT INTO order_parts(client_order_id, part_id)
VALUES (1,1), (2,3), (1,2);





