
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY NOT NULL,
    email varchar(30) NOT NULL UNIQUE,
    password varchar(30) NOT NULL,
    first_name varchar(30) NOT NULL,
    last_name varchar(30) NOT NULL,
    date_of_birth DATE NOT NULL,
    image varchar (150),
    acc_type varchar(20) NOT NULL,
    active boolean NOT NULL
);

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(30) NOT NULL UNIQUE,
    category VARCHAR(20) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    quantity INTEGER NOT NULL,
    sold INTEGER NOT NULL,
    image VARCHAR(150),
    description VARCHAR(150),
    deleted BOOLEAN NOT NULL
);

CREATE TABLE orders (
   order_id SERIAL PRIMARY KEY NOT NULL,
   user_id INT NOT NULL,
   time    TIMESTAMP NOT NULL,
   total   DOUBLE PRECISION NOT NULL,
   CONSTRAINT  fk_user FOREIGN KEY(user_id) REFERENCES users(user_id)
);

CREATE TABLE chosen_products (
    id SERIAL PRIMARY KEY NOT NULL,
    product_id INT NOT NULL,
    order_id INT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    CONSTRAINT  fk_order FOREIGN KEY(order_id) REFERENCES orders(order_id),
    CONSTRAINT  fk_product FOREIGN KEY(product_id) REFERENCES products(product_id)
);


CREATE TABLE reviews (
     review_id SERIAL PRIMARY KEY NOT NULL,
     user_id INT NOT NULL,
     product_id INT NOT NULL,
     rate INT NOT NULL,
     time    TIMESTAMP NOT NULL,
     CONSTRAINT  fk_user FOREIGN KEY(user_id) REFERENCES users(user_id),
     CONSTRAINT  fk_product FOREIGN KEY(product_id) REFERENCES products(product_id)
);

CREATE TABLE verify (
     verify_id SERIAL PRIMARY KEY NOT NULL,
     email varchar(30) NOT NULL UNIQUE,
     code INT NOT NULL,
     num_tries INT NOT NULL
);