
-- Creating the tables
CREATE TABLE IF NOT EXISTS cart_items (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            quantity INT NOT NULL,
                            client_id BIGINT,
                            dish_id BIGINT,
                            restaurant_id BIGINT,
                            CONSTRAINT FK_cart_items_client FOREIGN KEY (client_id) REFERENCES client(id_client),
                            CONSTRAINT FK_cart_items_dish FOREIGN KEY (dish_id, restaurant_id) REFERENCES dish(dish_id, restaurant_id)
);

CREATE TABLE IF NOT EXISTS client (
                        id_client BIGINT AUTO_INCREMENT PRIMARY KEY,
                        city VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        firstname VARCHAR(255) NOT NULL,
                        lastname VARCHAR(255) NOT NULL,
                        password VARCHAR(255),
                        phone VARCHAR(255) NOT NULL,
                        street_address VARCHAR(255) NOT NULL,
                        username VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS client_order (
                              id_order BIGINT AUTO_INCREMENT PRIMARY KEY,
                              id_client BIGINT,
                              restaurant BIGINT,
                              CONSTRAINT FK_client_order_client FOREIGN KEY (id_client) REFERENCES client(id_client),
                              CONSTRAINT FK_client_order_restaurant FOREIGN KEY (restaurant) REFERENCES restaurant(id_restaurant)
);

CREATE TABLE IF NOT EXISTS client_role (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             role VARCHAR(255),
                             user_id_client BIGINT,
                             CONSTRAINT FK_client_role_client FOREIGN KEY (user_id_client) REFERENCES client(id_client)
);

CREATE TABLE IF NOT EXISTS dish (
                      dish_id BIGINT NOT NULL,
                      restaurant_id BIGINT NOT NULL,
                      category VARCHAR(255),
                      dish_name VARCHAR(255) NOT NULL,
                      price DOUBLE,
                      quantity INT,
                      selected TINYINT(1) DEFAULT 0 NOT NULL,
                      PRIMARY KEY (dish_id, restaurant_id),
                      CONSTRAINT FK_dish_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant(id_restaurant)
);

CREATE TABLE IF NOT EXISTS order_dishes (
                              client_order_id BIGINT NOT NULL,
                              dish_id BIGINT NOT NULL,
                              restaurant_id BIGINT NOT NULL,
                              PRIMARY KEY (client_order_id, dish_id, restaurant_id),
                              CONSTRAINT FK_order_dishes_dish FOREIGN KEY (dish_id, restaurant_id) REFERENCES dish(dish_id, restaurant_id),
                              CONSTRAINT FK_order_dishes_client_order FOREIGN KEY (client_order_id) REFERENCES client_order(id_order)
);

CREATE TABLE IF NOT EXISTS restaurant (
                            id_restaurant BIGINT AUTO_INCREMENT PRIMARY KEY,
                            category VARCHAR(255),
                            city VARCHAR(255) NOT NULL,
                            email VARCHAR(255) NOT NULL,
                            name VARCHAR(255) NOT NULL,
                            password VARCHAR(255),
                            phone VARCHAR(255) NOT NULL,
                            street_address VARCHAR(255) NOT NULL,
                            username VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS restaurant_role (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 role VARCHAR(255),
                                 restaurant_id_restaurant BIGINT,
                                 CONSTRAINT FK_restaurant_role_restaurant FOREIGN KEY (restaurant_id_restaurant) REFERENCES restaurant(id_restaurant)
);
