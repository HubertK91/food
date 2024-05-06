INSERT INTO client(firstname, lastname, username, password, address)
VALUES('Jan','Kowalski', 'kowal','{noop}kowal','Warszawa'),('Maria','Zawadzka', 'maria', '{noop}maria','Wrocław');

INSERT INTO restaurant(name, username, password, address)
VALUES ('Sphinx', 'Sphinx', '{noop}sphinx', 'Sosnowiec');

INSERT INTO client_order(id_client)
VALUES (1), (2);

INSERT INTO client_role(user_id_client, role)
VALUES (1, 'ROLE_ADMIN'), (1, 'ROLE_USER'), (2, 'ROLE_USER');

INSERT INTO dish(dish_name, price, selected, category)
VALUES ('Schabowy', 50.0, false ,'Kuchnia polska'), ('Spaghetti',1500.0,false, 'Kuchnia włoska'),
       ('Ryż z kurczakiem', 150.0, false ,'Kuchnia chińska'), ('Musaka', 300.20, false ,'Kuchnia grecka');

INSERT INTO order_dishes(client_order_id, dish_id)
VALUES (1,1), (2,3), (1,2);





