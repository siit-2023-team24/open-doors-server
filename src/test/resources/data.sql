insert into users (id, first_name, last_name, country, number, image_id, dtype, username, password, role, enabled, city, phone, street, deleted, blocked)
values (1, 'vasilije', 'markovic', 54, 7, null, 'Host', 'vaske@test.test', '$2a$10$YqthrUYDrPgyerBrgJzDbO/ym7qiFEwW75H.sPuVEOGeYMOcirmNq', 1, true, 'ns', '123123', 'sdfsg', false, false);

insert into users (id, first_name, last_name, country, number, image_id, dtype, username, password, role, enabled, city, phone, street, deleted, blocked)
values (2, 'milica', 'misic', 54, 7, null, 'Guest', 'milica@test.test', '$2a$10$YqthrUYDrPgyerBrgJzDbO/ym7qiFEwW75H.sPuVEOGeYMOcirmNq', 0, true, 'ns', '123123', 'sdfsg', false, false);

insert into accommodation (id,accommodation_type, country, deadline, is_automatic, is_price_per_guest, max_guests,
                           min_guests, number, price, host_id, city, description, location, name, street, deleted, blocked)
values (1,4,0,4,false,false,2,1,1,0,1,'city1', 'opis', 'city1, afghanistan', 'house of the rising sun', 'street', false, false);

insert into accommodation (id,accommodation_type, country, deadline, is_automatic, is_price_per_guest, max_guests,
                           min_guests, number, price, host_id, city, description, location, name, street, deleted, blocked)
values (2,1,0,4,false,false,3,1,1,0,1,'city2', 'opis', 'city2, afghanistan', 'house of the rising sun', 'street', false, false);

insert into accommodation (id,accommodation_type, country, deadline, is_automatic, is_price_per_guest, max_guests,
                           min_guests, number, price, host_id, city, description, location, name, street, deleted, blocked)
values (3,5,0,4,false,false,6,1,1,0,1,'city3', 'opis', 'city3, afghanistan', 'house of the rising sun', 'street', false, false);

insert into accommodation (id,accommodation_type, country, deadline, is_automatic, is_price_per_guest, max_guests,
                           min_guests, number, price, host_id, city, description, location, name, street, deleted, blocked)
values (4,5,0,4,false,false,6,1,1,0,1,'city2', 'opis', 'city2, afghanistan', 'house of the rising sun', 'street', false, false);

-- Accommodation 1 Availability
INSERT INTO accommodation_availability (accommodation_id, start_date, end_date)
VALUES (1, '2024-06-01', '2024-06-15');

-- Accommodation 2 Availability
INSERT INTO accommodation_availability (accommodation_id, start_date, end_date)
VALUES (2, '2024-06-01', '2024-06-15'),
       (2, '2024-07-01', '2024-07-31'),
       (2, '2024-09-01', '2024-09-15');

-- Accommodation 3 Availability
INSERT INTO accommodation_availability (accommodation_id, start_date, end_date)
VALUES (3, '2024-06-01', '2024-06-15'),
       (3, '2024-07-01', '2024-07-31'),
       (3, '2024-09-01', '2024-09-15'),
       (3, '2024-08-01', '2024-08-31'),
       (3, '2024-10-01', '2024-10-15');

INSERT INTO accommodation_availability (accommodation_id, start_date, end_date)
VALUES (4, '2024-06-01', '2024-06-15');

insert into accommodation_amenities (accommodation_id, amenities)
values(1,1),(2,1),(2,2),(3,1),(3,2),(3,3),(4,1),(4,2);