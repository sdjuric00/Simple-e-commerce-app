
insert into users (email, password, first_name, last_name, date_of_birth, image, acc_type, active)
values ('admin@gmail.com', '12345', 'Admin', 'Admin', '25/06/1999', 'default', 'ADMIN', true),
    ('srdjan@gmail.com', '12345', 'Srdjan', 'Djuric', '23/08/2000', 'default', 'REGULAR_USER', true);

insert into products(name, category, price, quantity, sold, image, description, deleted)
values ('Kinder bueno', 'SWEATS', 100.0, 10, 1, 'default', 'Best italian chocolate.', false),
       ('CHICKEN 500g', 'MEAT_PRODUCTS', 800.0, 8, 0, 'default', 'Chicken meat from Serbian farmers.', false),
       ('Sprite 0.5l', 'DRINKS', 70.0, 10, 2, 'default', 'Cold sprite soda drink.', false);

insert into orders(user_id, time, total)
values (2, '05/09/2022', 170),
       (2, '05/09/2022', 70);

insert into chosen_products(product_id, order_id, quantity, price)
values (1, 1, 1, 100.0),
       (3, 1, 1, 70.0),
       (3, 2, 1, 70.0);

insert into reviews(user_id, product_id, rate, time)
values (2, 1, 4, '07/09/2022');