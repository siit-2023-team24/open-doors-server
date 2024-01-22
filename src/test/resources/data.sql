insert into users (id, first_name, last_name, country, number, image_id, dtype, username, password, role, enabled, city, phone, street, deleted, blocked)
values (1, 'vasilije', 'markovic', 54, 7, null, 'Host', 'vaske@test.test', '$2a$10$YqthrUYDrPgyerBrgJzDbO/ym7qiFEwW75H.sPuVEOGeYMOcirmNq', 1, true, 'ns', '123123', 'sdfsg', false, false);


insert into users (id, first_name, last_name, country, number, image_id, dtype, username, password, role, enabled, city, phone, street, deleted, blocked)
values (2, 'milica', 'misic', 54, 7, null, 'Guest', 'milica@test.test', '$2a$10$YqthrUYDrPgyerBrgJzDbO/ym7qiFEwW75H.sPuVEOGeYMOcirmNq', 0, true, 'ns', '123123', 'sdfsg', false, false);


insert into accommodation (accommodation_type, country, deadline, is_automatic, is_price_per_guest, max_guests, min_guests, number, price, host_id, id, city, description, location, name,                       street, amenities, deleted, blocked)
values (2,			 0, 		1,		 false,		 false,					 1, 		1,		 1,		 0,		 1,		1, 'city', 'opis', 		'', 	'house of the rising sun', 'street', null,          false, false);


insert into accommodation (accommodation_type, country, deadline, is_automatic, is_price_per_guest, max_guests, min_guests, number, price, host_id, id, city, description, location, name,                       street, amenities, deleted, blocked)
                      values (2,				 0, 		10,		 false,		 false,					 1, 		1,		 1,		 0,		 1,		2, 'city', 'opis', 		'', 	'house of the rising moon', 'street', null,          false, false);

insert into accommodation_availability (accommodation_id, start_date, end_date)
values (1, '2024-12-21', '2024-12-21');


insert into reservation_request (id, end_date, start_date, guest_number, status, timestamp, total_price, accommodation_id, guest_id)
            values (			1,  '2024-02-26', '2024-02-24', 	1, 	       1,    	'2024-01-20', 	0, 				1, 			2);


insert into reservation_request (id, end_date, start_date, guest_number, status, timestamp, total_price, accommodation_id, guest_id)
            values (			2, '2024-12-21',         '2024-12-21', 		1, 	          0,	    '2024-01-20',   	0, 				1, 			2);

insert into reservation_request (id, end_date, start_date, guest_number, status, timestamp, total_price, accommodation_id, guest_id)
            values (			3,     '2024-12-21',        '2024-12-21', 		1, 	         1,    	'2024-01-18', 	0, 				2, 			2);


insert into reservation_request (id, end_date, start_date, guest_number, status, timestamp, total_price, accommodation_id, guest_id)
            values (			4, '2024-02-26',     '2024-01-25', 		1, 	          0,	    '2024-01-20',   	0, 				2, 			2);
