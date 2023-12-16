-- select * from accommodation

insert into accommodation (accommodation_type, average_rating, country, deadline, is_automatic, is_price_per_night, max_guests, min_guests, number, price, host_id, id, city, description, location, name, street, amenities)
					values (2,					 0,				 0, 		4,		 false,		 false,					 1, 		1,		 1,		 0,		 1,		1, 'city', 'opis', 		null, 	'house of the rising sun', 'street', null);
					

insert into pending_accommodation (accommodation_type, average_rating, country, deadline, is_automatic, is_price_per_night, max_guests, min_guests, number, price, host_id, id, city, description, location, name, street, amenities, accommodation_id)
							values (2,					 0,				 0, 		4,		 false,		 false,					 1, 		1,		 1,		 0,		 1,		1, 'city', 'opis', 		null, 	'house of the rising sun', 'street', null, null);
					
