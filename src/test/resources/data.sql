insert into accommodation (accommodation_type, average_rating, country, deadline, is_automatic, is_price_per_guest, max_guests, min_guests, number, price, host_id, id, city, description, location, name,                       street, amenities, deleted, blocked)
                    values (2,					 0,				 0, 		4,		 false,		 false,					 1, 		1,		 1,		 0,		 1,		1, 'city', 'opis', 		"", 	'house of the rising sun', 'street', null,          false, false);


insert into accommodation (accommodation_type, average_rating, country, deadline, is_automatic, is_price_per_guest, max_guests, min_guests, number, price, host_id, id, city, description, location, name,                       street, amenities, deleted, blocked)
                    values (2,					 0,				 0, 		4,		 false,		 false,					 1, 		1,		 1,		 0,		 1,		2, 'city', 'opis', 		"", 	'house of the rising moon', 'street', null,          false, false);


insert into reservation_request (id, end_date, start_date, guest_number, status, timestamp, total_price, accommodation_id, guest_id)
            values (			1,     null,        null, 		1, 	         1,    	null, 	0, 				1, 			2);


insert into reservation_request (id, end_date, start_date, guest_number, status, timestamp, total_price, accommodation_id, guest_id)
            values (			2, null,         null, 		1, 	          0,	    null,   	0, 				1, 			2);

insert into reservation_request (id, end_date, start_date, guest_number, status, timestamp, total_price, accommodation_id, guest_id)
            values (			3,     null,        null, 		1, 	         1,    	null, 	0, 				2, 			2);


insert into reservation_request (id, end_date, start_date, guest_number, status, timestamp, total_price, accommodation_id, guest_id)
            values (			4, null,         null, 		1, 	          0,	    null,   	0, 				2, 			2);

