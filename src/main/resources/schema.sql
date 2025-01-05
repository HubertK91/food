-- Usunięcie tabel (jeśli istnieją)
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS client_order;
DROP TABLE IF EXISTS client_role;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS order_dishes;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS restaurant_role;

-- Tworzenie tabel
CREATE TABLE cart_items (
                            id INTEGER NOT NULL AUTO_INCREMENT,
                            quantity INTEGER NOT NULL,
                            client_id BIGINT,
                            dish_id BIGINT,
                            restaurant_id BIGINT,
                            PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE client (
                        id_client BIGINT NOT NULL AUTO_INCREMENT,
                        city VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        firstname VARCHAR(255) NOT NULL,
                        lastname VARCHAR(255) NOT NULL,
                        password VARCHAR(255),
                        phone VARCHAR(255) NOT NULL,
                        street_address VARCHAR(255) NOT NULL,
                        username VARCHAR(255),
                        PRIMARY KEY (id_client)
) ENGINE=InnoDB;

CREATE TABLE client_order (
                              id_order BIGINT NOT NULL AUTO_INCREMENT,
                              id_client BIGINT,
                              restaurant BIGINT,
                              PRIMARY KEY (id_order)
) ENGINE=InnoDB;

CREATE TABLE client_role (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             role VARCHAR(255),
                             user_id_client BIGINT,
                             PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE dish (
                      dish_id BIGINT NOT NULL,
                      restaurant_id BIGINT NOT NULL,
                      category VARCHAR(255),
                      dish_name VARCHAR(255) NOT NULL,
                      price DOUBLE PRECISION,
                      quantity INTEGER,
                      selected BIT NOT NULL,
                      PRIMARY KEY (dish_id, restaurant_id)
) ENGINE=InnoDB;

CREATE TABLE order_dishes (
                              client_order_id BIGINT NOT NULL,
                              dish_id BIGINT NOT NULL,
                              restaurant_id BIGINT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE restaurant (
                            id_restaurant BIGINT NOT NULL AUTO_INCREMENT,
                            category VARCHAR(255),
                            city VARCHAR(255) NOT NULL,
                            email VARCHAR(255) NOT NULL,
                            name VARCHAR(255) NOT NULL,
                            password VARCHAR(255),
                            phone VARCHAR(255) NOT NULL,
                            street_address VARCHAR(255) NOT NULL,
                            username VARCHAR(255),
                            PRIMARY KEY (id_restaurant)
) ENGINE=InnoDB;

CREATE TABLE restaurant_role (
                                 id BIGINT NOT NULL AUTO_INCREMENT,
                                 role VARCHAR(255),
                                 restaurant_id_restaurant BIGINT,
                                 PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Dodanie constraintów (kluczy obcych)
ALTER TABLE cart_items
    ADD CONSTRAINT FKcywefv5dkhx3u3u3v6r9xybcg
        FOREIGN KEY (client_id)
            REFERENCES client (id_client);

ALTER TABLE cart_items
    ADD CONSTRAINT FKbmr3xqvcueaitsxxhav4f8ka0
        FOREIGN KEY (dish_id, restaurant_id)
            REFERENCES dish (dish_id, restaurant_id);

ALTER TABLE client_order
    ADD CONSTRAINT FK6ni3ry5dd7dojygmaqebtod71
        FOREIGN KEY (id_client)
            REFERENCES client (id_client);

ALTER TABLE client_order
    ADD CONSTRAINT FKjg4qh72u9bcexnfy8u2qp6twl
        FOREIGN KEY (restaurant)
            REFERENCES restaurant (id_restaurant);

ALTER TABLE client_role
    ADD CONSTRAINT FKrt4xy9oa64m54gvtt4n70ywnr
        FOREIGN KEY (user_id_client)
            REFERENCES client (id_client);

ALTER TABLE dish
    ADD CONSTRAINT FKt13glsbe9ivpka00hbeg539cv
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (id_restaurant);

ALTER TABLE order_dishes
    ADD CONSTRAINT FKbsythobcuaxpd1180t1tubjp3
        FOREIGN KEY (dish_id, restaurant_id)
            REFERENCES dish (dish_id, restaurant_id);

ALTER TABLE order_dishes
    ADD CONSTRAINT FK5r79h7lvkswfaai5levie41wu
        FOREIGN KEY (client_order_id)
            REFERENCES client_order (id_order);

ALTER TABLE restaurant_role
    ADD CONSTRAINT FK9tqhxo6envtecus6o78o6dh9j
        FOREIGN KEY (restaurant_id_restaurant)
            REFERENCES restaurant (id_restaurant);
