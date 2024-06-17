insert into users (id, first_name, last_name, country, number, image_id, dtype, username, password, role, enabled, city, phone, street, deleted, blocked)
values (1, 'vasilije', 'markovic', 54, 7, null, 'Host', 'vaske@test.test', '$2a$10$YqthrUYDrPgyerBrgJzDbO/ym7qiFEwW75H.sPuVEOGeYMOcirmNq', 1, true, 'ns', '123123', 'sdfsg', false, false);

insert into users (id, first_name, last_name, country, number, image_id, dtype, username, password, role, enabled, city, phone, street, deleted, blocked)
values (2, 'milica', 'misic', 54, 7, null, 'Guest', 'milica@test.test', '$2a$10$YqthrUYDrPgyerBrgJzDbO/ym7qiFEwW75H.sPuVEOGeYMOcirmNq', 0, true, 'ns', '123123', 'sdfsg', false, false);

insert into accommodation (id,accommodation_type, country, deadline, is_automatic, is_price_per_guest, max_guests,
                           min_guests, number, price, host_id, city, description, location, name, street, deleted, blocked)
values (1,2,0,4,false,false,1,1,1,0,1,'city', 'opis', null, 'house of the rising sun', 'street', false, false);

INSERT INTO accommodation_availability (accommodation_id, start_date, end_date)
VALUES (1, '2024-06-01', '2024-10-01');