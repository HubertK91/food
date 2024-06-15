DROP SEQUENCE IF EXISTS DISH_SEQ;
CREATE SEQUENCE DISH_SEQ START WITH 1 INCREMENT BY 1;


INSERT INTO client(firstname, lastname, city, phone , email, street_Address, username, password)
VALUES('Jan','Kowalski', 'Warszawa', '888999123', 'jan@gmail.com', 'Słoneczna 12', 'admin', '{noop}admin')
     ,('Maria','Zawadzka','Wrocław', '777555543', 'maria@gmail.com', 'Smutna 18', 'maria', '{noop}maria');

INSERT INTO restaurant(name, username, password, city, phone , email, street_Address)
VALUES ('Sphinx', 'Sphinx', '{noop}sphinx', 'Sosnowiec', '666666666', 'sphinx@gmail.com', 'Czerwonego Krzyża 10'),
       ('Italia', 'Italia', '{noop}italia', 'Sosnowiec', '999666666', 'italia@gmail.com', 'Sobieskiego 10');

INSERT INTO client_order(id_client)
VALUES (1), (2);

INSERT INTO client_role(user_id_client, role)
VALUES (1, 'ROLE_ADMIN'), (1, 'ROLE_USER'), (1, 'ROLE_RESTAURANT'),(2, 'ROLE_USER');

INSERT INTO restaurant_role(restaurant_id_restaurant, role)
VALUES (1, 'ROLE_RESTAURANT');

INSERT INTO dish(dish_id, dish_name, price, selected, category, restaurant_id)
VALUES (1,'Schabowy', 50.0, false ,'Polish', 1), (2,'Spaghetti',1500.0,false, 'Italian',1),
       (1,'Ryż z kurczakiem', 150.0, false ,'Chinese',2), (2,'Musaka', 300.20, false ,'Greek',2);

INSERT INTO order_dishes(client_order_id, dish_id, restaurant_id)
VALUES (1,1,1), (2,2,1), (1,1,2);






