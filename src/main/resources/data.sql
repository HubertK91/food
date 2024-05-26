INSERT INTO client(firstname, lastname, username, password, address)
VALUES('Jan','Kowalski', 'admin','{noop}admin','Warszawa'),('Maria','Zawadzka', 'maria', '{noop}maria','Wrocław');

INSERT INTO restaurant(name, username, password, address)
VALUES ('Sphinx', 'Sphinx', '{noop}sphinx', 'Sosnowiec'), ('Italia', 'Italia', '{noop}italia', 'Sosnowiec');

INSERT INTO client_order(id_client)
VALUES (1), (2);

INSERT INTO client_role(user_id_client, role)
VALUES (1, 'ROLE_ADMIN'), (1, 'ROLE_USER'), (1, 'ROLE_RESTAURANT'),(2, 'ROLE_USER');

INSERT INTO restaurant_role(restaurant_id_restaurant, role)
VALUES (1, 'ROLE_RESTAURANT');

INSERT INTO dish(dish_name, price, selected, category, id_restaurant)
VALUES ('Schabowy', 50.0, false ,'Polish', 1), ('Spaghetti',1500.0,false, 'Italian',1),
       ('Ryż z kurczakiem', 150.0, false ,'Chinese',2), ('Musaka', 300.20, false ,'Greek',2);

INSERT INTO order_dishes(client_order_id, dish_id)
VALUES (1,1), (2,3), (1,2);






