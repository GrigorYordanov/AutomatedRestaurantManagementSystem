
CREATE TABLE customer ( 
	id                   integer  NOT NULL,
	username             varchar(50)  ,
	email                varchar(50)  ,
	pass                 varchar(50)  ,
	full_name            varchar(50)  ,
	telephone_number     varchar(20)  ,
	CONSTRAINT pk_customer PRIMARY KEY ( id )
 );

COMMENT ON TABLE customer IS 'Info for customers.';

COMMENT ON COLUMN customer.id IS 'ID of customer.';

COMMENT ON COLUMN customer.username IS 'Username of customer.';

COMMENT ON COLUMN customer.email IS 'Email of customer.';

COMMENT ON COLUMN customer.pass IS 'Password of customer.';

COMMENT ON COLUMN customer.full_name IS 'Full name of customer.';

COMMENT ON COLUMN customer.telephone_number IS 'Telephone number of customer';

CREATE TABLE dietary_requirements ( 
	id                   integer  NOT NULL,
	name                 varchar(50)  ,
	CONSTRAINT pk_dishes_dietary_requirements PRIMARY KEY ( id )
 );

COMMENT ON TABLE dietary_requirements IS 'Dietary requirments for dishes.';

COMMENT ON COLUMN dietary_requirements.id IS 'ID of dietary requirment';

COMMENT ON COLUMN dietary_requirements.name IS 'Dietary requirment name.';

CREATE TABLE food_type ( 
	id                   integer  NOT NULL,
	name                 varchar(100)  ,
	CONSTRAINT pk_food_type PRIMARY KEY ( id )
 );

COMMENT ON TABLE food_type IS 'Type of food in menu.';

COMMENT ON COLUMN food_type.id IS 'Food type ID.';

COMMENT ON COLUMN food_type.name IS 'Food type name.';

CREATE TABLE ingredients_allergies ( 
	id                   integer  NOT NULL,
	name                 varchar(50)  ,
	CONSTRAINT pk_dishes_allergies PRIMARY KEY ( id )
 );

COMMENT ON TABLE ingredients_allergies IS 'Allergies that appear in dishes.';

COMMENT ON COLUMN ingredients_allergies.id IS 'ID of allergy';

COMMENT ON COLUMN ingredients_allergies.name IS 'Name of allergy.';

CREATE TABLE job_position ( 
	id                   integer  NOT NULL,
	name                 varchar(30)  ,
	CONSTRAINT pk_job_position PRIMARY KEY ( id )
 );

COMMENT ON TABLE job_position IS 'Contains job positions at the restourant.';

COMMENT ON COLUMN job_position.id IS 'Job position ID.
';

COMMENT ON COLUMN job_position.name IS 'Name of job position';

CREATE TABLE metric_unit ( 
	id                   integer  NOT NULL,
	name                 varchar(10)  ,
	CONSTRAINT pk_metric_unit PRIMARY KEY ( id )
 );

COMMENT ON TABLE metric_unit IS 'Metric units (for ingredients).';

COMMENT ON COLUMN metric_unit.id IS 'Unit ID.';

COMMENT ON COLUMN metric_unit.name IS 'Unit name.';

CREATE TABLE order_state ( 
	id                   integer  NOT NULL,
	name                 varchar(100)  ,
	CONSTRAINT pk_order_state_0 PRIMARY KEY ( id )
 );

COMMENT ON TABLE order_state IS 'Sates of  orders.';

COMMENT ON COLUMN order_state.id IS 'ID of order state.';

COMMENT ON COLUMN order_state.name IS 'Name of order state.';

CREATE TABLE staff ( 
	id                   integer  NOT NULL,
	username             varchar(50)  ,
	email                varchar(50)  ,
	pass                 varchar(50)  ,
	full_name            varchar(50)  ,
	telephone_number     varchar(20)  ,
	address              varchar(100)  ,
	job_position_id      integer  ,
	permission_level     integer  ,
	CONSTRAINT pk_staff PRIMARY KEY ( id ),
	CONSTRAINT fk_staff FOREIGN KEY ( job_position_id ) REFERENCES job_position( id )    
 );

CREATE INDEX idx_staff ON staff ( job_position_id );

COMMENT ON TABLE staff IS 'Staff of restourant.';

COMMENT ON COLUMN staff.id IS 'ID of staff member.';

COMMENT ON COLUMN staff.username IS 'Username of staff member.';

COMMENT ON COLUMN staff.email IS 'Email of staff membr';

COMMENT ON COLUMN staff.pass IS 'Password of staff member.';

COMMENT ON COLUMN staff.full_name IS 'Full name of staff member';

COMMENT ON COLUMN staff.telephone_number IS 'Telephone number of staff member.';

COMMENT ON COLUMN staff.address IS 'Current address of staff member.';

COMMENT ON COLUMN staff.job_position_id IS 'Job position of staff member';

COMMENT ON COLUMN staff.permission_level IS 'Level of permission of staff member';

CREATE TABLE table_status ( 
	id                   integer  NOT NULL,
	name                 varchar(20)  ,
	CONSTRAINT pk_table_status PRIMARY KEY ( id )
 );

COMMENT ON TABLE table_status IS 'Shows the possible states of a table.';

COMMENT ON COLUMN table_status.id IS 'ID of table.';

COMMENT ON COLUMN table_status.name IS 'Status name.';

CREATE TABLE dishes ( 
	id                   integer  NOT NULL,
	name                 varchar(100)  ,
	cost                 numeric(6,2)  ,
	picture_url          varchar(100)  ,
	food_type_id         integer  ,
	dietary_req_id       integer  ,
	sort_order           integer  ,
	in_stock             bool  ,
	mealdeal_id          integer  ,
	CONSTRAINT pk_dishes PRIMARY KEY ( id ),
	CONSTRAINT idx_dishes UNIQUE ( mealdeal_id ) ,
	CONSTRAINT fk_dishes FOREIGN KEY ( food_type_id ) REFERENCES food_type( id )    ,
	CONSTRAINT fk_dishes_0 FOREIGN KEY ( dietary_req_id ) REFERENCES dietary_requirements( id )    
 );

CREATE INDEX idx_dishes_0 ON dishes ( food_type_id );

CREATE INDEX idx_dishes_1 ON dishes ( dietary_req_id );

COMMENT ON TABLE dishes IS 'The dishes that are contained in the menu.';

COMMENT ON COLUMN dishes.id IS 'Dishes ID.';

COMMENT ON COLUMN dishes.name IS 'Dishes name.';

COMMENT ON COLUMN dishes.cost IS 'Dishes price.
';

COMMENT ON COLUMN dishes.picture_url IS 'URL of pictures of dishes.
';

COMMENT ON COLUMN dishes.food_type_id IS 'ID of food type.';

COMMENT ON COLUMN dishes.dietary_req_id IS 'Dietary requirments ID.';

COMMENT ON COLUMN dishes.sort_order IS 'Sort order of dishes in menu.';

COMMENT ON COLUMN dishes.in_stock IS 'True if in stock.';

COMMENT ON COLUMN dishes.mealdeal_id IS 'ID of mealdeal.';

CREATE TABLE ingredients_stock ( 
	id                   integer  NOT NULL,
	name                 varchar(50)  ,
	cost                 integer  ,
	metric               integer  ,
	calories             integer  ,
	allergy_id           integer  ,
	quantity             numeric(6,2)  ,
	CONSTRAINT pk_ingredients PRIMARY KEY ( id ),
	CONSTRAINT fk_ingredients_stock FOREIGN KEY ( metric ) REFERENCES metric_unit( id )    ,
	CONSTRAINT fk_ingredients_stock_0 FOREIGN KEY ( allergy_id ) REFERENCES ingredients_allergies( id )    
 );

CREATE INDEX idx_ingredients_stock ON ingredients_stock ( allergy_id );

COMMENT ON TABLE ingredients_stock IS 'Contents ingredients.';

COMMENT ON COLUMN ingredients_stock.id IS 'ID of ingredient';

COMMENT ON COLUMN ingredients_stock.name IS 'Name of ingredient';

COMMENT ON COLUMN ingredients_stock.cost IS 'Price of ingredient.';

COMMENT ON COLUMN ingredients_stock.metric IS 'Metric of ingredient.';

COMMENT ON COLUMN ingredients_stock.calories IS 'Calories of ingredient.';

COMMENT ON COLUMN ingredients_stock.allergy_id IS 'Allergy ID of ingredient.';

COMMENT ON COLUMN ingredients_stock.quantity IS 'Quantity of ingredients in stock';

CREATE TABLE mealdeal_dishes ( 
	mealdeal_id          integer  ,
	dish_id              integer  ,
	CONSTRAINT fk_mealdeal_dishes FOREIGN KEY ( mealdeal_id ) REFERENCES dishes( mealdeal_id )    ,
	CONSTRAINT fk_mealdeal_dishes_0 FOREIGN KEY ( dish_id ) REFERENCES dishes( id )    
 );

CREATE INDEX idx_mealdeal_dishes ON mealdeal_dishes ( dish_id );

CREATE INDEX idx_mealdeal_dishes_0 ON mealdeal_dishes ( mealdeal_id );

COMMENT ON TABLE mealdeal_dishes IS 'Dishes contained in meadeal.';

COMMENT ON COLUMN mealdeal_dishes.mealdeal_id IS 'ID of a mealdeal.';

COMMENT ON COLUMN mealdeal_dishes.dish_id IS 'ID of a dish.';

CREATE TABLE restourant_table ( 
	id                   integer  NOT NULL,
	table_status_id      integer  ,
	waiter_id            integer  ,
	CONSTRAINT pk_table PRIMARY KEY ( id ),
	CONSTRAINT fk_table_0 FOREIGN KEY ( table_status_id ) REFERENCES table_status( id )    ,
	CONSTRAINT fk_table_1 FOREIGN KEY ( waiter_id ) REFERENCES staff( id )    
 );

CREATE INDEX idx_table_0 ON restourant_table ( table_status_id );

CREATE INDEX idx_table_1 ON restourant_table ( waiter_id );

COMMENT ON TABLE restourant_table IS 'Represents a table in the restourant.';

COMMENT ON COLUMN restourant_table.id IS 'ID of the table.';

COMMENT ON COLUMN restourant_table.table_status_id IS 'ID showing status of a table.';

COMMENT ON COLUMN restourant_table.waiter_id IS 'ID of waiter operating on a table.';

CREATE TABLE client_devices ( 
	id                   integer  NOT NULL,
	table_id             integer  ,
	CONSTRAINT pk_client_devices PRIMARY KEY ( id ),
	CONSTRAINT fk_client_devices FOREIGN KEY ( table_id ) REFERENCES restourant_table( id )    
 );

CREATE INDEX idx_client_devices ON client_devices ( table_id );

COMMENT ON TABLE client_devices IS 'Client devices in restourant.';

COMMENT ON COLUMN client_devices.id IS 'ID of client device.';

COMMENT ON COLUMN client_devices.table_id IS 'ID of the table device is located.';

CREATE TABLE device_calls ( 
	client_device_id     integer  ,
	call_time            time  ,
	call_confirm         time  ,
	CONSTRAINT fk_device_calls FOREIGN KEY ( client_device_id ) REFERENCES client_devices( id )    
 );

CREATE INDEX idx_device_calls ON device_calls ( client_device_id );

COMMENT ON TABLE device_calls IS 'Calls for waiters from customers.';

COMMENT ON COLUMN device_calls.client_device_id IS 'Client device ID.';

COMMENT ON COLUMN device_calls.call_time IS 'Time when waiter was called.';

COMMENT ON COLUMN device_calls.call_confirm IS 'Time when waiter serviced customer call.';

CREATE TABLE dishes_ingredients ( 
	dish_id              integer  ,
	ingredient_id        integer  ,
	ingredient_qty       integer  ,
	CONSTRAINT fk_dishes_ingredients FOREIGN KEY ( ingredient_id ) REFERENCES ingredients_stock( id )    ,
	CONSTRAINT fk_dishes_ingredients_0 FOREIGN KEY ( dish_id ) REFERENCES dishes( id )    
 );

CREATE INDEX idx_dishes_ingredients ON dishes_ingredients ( dish_id );

CREATE INDEX idx_dishes_ingredients_0 ON dishes_ingredients ( ingredient_id );

COMMENT ON TABLE dishes_ingredients IS 'All ingridients that are contained in a single dish.';

COMMENT ON COLUMN dishes_ingredients.dish_id IS 'Dish ID.';

COMMENT ON COLUMN dishes_ingredients.ingredient_id IS 'Ingredient ID.';

CREATE TABLE orders ( 
	id                   integer  NOT NULL,
	order_device_id      integer  ,
	customer_id          integer  ,
	cost                 numeric(6,2)  ,
	time_requested       time  ,
	time_confirmed       time  ,
	time_delivered       time  ,
	time_completed       time  ,
	order_state_id       integer  ,
	CONSTRAINT pk_orders PRIMARY KEY ( id ),
	CONSTRAINT fk_orders FOREIGN KEY ( order_state_id ) REFERENCES order_state( id )    ,
	CONSTRAINT fk_orders_0 FOREIGN KEY ( order_device_id ) REFERENCES client_devices( id )    ,
	CONSTRAINT fk_orders_1 FOREIGN KEY ( customer_id ) REFERENCES customer( id )    
 );

CREATE INDEX idx_orders ON orders ( order_state_id );

CREATE INDEX idx_orders_0 ON orders ( order_device_id );

CREATE INDEX idx_orders_1 ON orders ( customer_id );

COMMENT ON TABLE orders IS 'Contains orders from clients.';

COMMENT ON COLUMN orders.id IS 'ID of order.';

COMMENT ON COLUMN orders.order_device_id IS 'The ID of the device that made the order.';

COMMENT ON COLUMN orders.customer_id IS 'ID of customer.';

COMMENT ON COLUMN orders.cost IS 'Price of order.';

COMMENT ON COLUMN orders.time_requested IS 'Time order was requested.';

COMMENT ON COLUMN orders.time_confirmed IS 'Time until order was confirmed.';

COMMENT ON COLUMN orders.time_delivered IS 'Time until order was delivered.';

COMMENT ON COLUMN orders.time_completed IS 'Time until order was completed.';

COMMENT ON COLUMN orders.order_state_id IS 'Current state of order.';

CREATE TABLE reservation ( 
	id                   integer  NOT NULL,
	table_id             integer  ,
	customer_id          integer  ,
	reservation_date_time timestamp  ,
	CONSTRAINT pk_reservation PRIMARY KEY ( id ),
	CONSTRAINT fk_reservation FOREIGN KEY ( table_id ) REFERENCES restourant_table( id )    ,
	CONSTRAINT fk_reservation_0 FOREIGN KEY ( customer_id ) REFERENCES customer( id )    
 );

CREATE INDEX idx_reservation ON reservation ( table_id );

CREATE INDEX idx_reservation_0 ON reservation ( customer_id );

COMMENT ON TABLE reservation IS 'Contains reservations in the restourant.';

COMMENT ON COLUMN reservation.id IS 'ID of reservation';

COMMENT ON COLUMN reservation.table_id IS 'ID of the table reserved.';

COMMENT ON COLUMN reservation.customer_id IS 'ID of customer';

COMMENT ON COLUMN reservation.reservation_date_time IS 'What date and time is the table reserved for.';

CREATE TABLE extra_sales ( 
	waiter_id            integer  ,
	extra_sales_made     integer  ,
	order_id             integer  ,
	CONSTRAINT fk_extra_sales FOREIGN KEY ( waiter_id ) REFERENCES staff( id )    ,
	CONSTRAINT fk_extra_sales_0 FOREIGN KEY ( order_id ) REFERENCES orders( id )    
 );

CREATE INDEX idx_extra_sales ON extra_sales ( waiter_id );

CREATE INDEX idx_extra_sales_0 ON extra_sales ( order_id );

COMMENT ON TABLE extra_sales IS 'Extra sales made by waiter.';

COMMENT ON COLUMN extra_sales.waiter_id IS 'ID of the waiter.
';

COMMENT ON COLUMN extra_sales.extra_sales_made IS 'How many extra sales were made to an order.';

COMMENT ON COLUMN extra_sales.order_id IS 'ID of order.';

CREATE TABLE order_dishes ( 
	dish_id              integer  ,
	order_id             integer  ,
	CONSTRAINT fk_order_dishes FOREIGN KEY ( dish_id ) REFERENCES dishes( id )    ,
	CONSTRAINT fk_order_dishes_0 FOREIGN KEY ( order_id ) REFERENCES orders( id )    
 );

COMMENT ON TABLE order_dishes IS 'Dishes that are included in an order.';

COMMENT ON COLUMN order_dishes.dish_id IS 'ID of dish.
';

COMMENT ON COLUMN order_dishes.order_id IS 'ID of order.';

INSERT INTO customer( id, username, email, pass, full_name, telephone_number ) VALUES ( 0, 'testuser1', 'testuser1@gmail.com', 'test123', 'Test User 1', '0748255555' ); 
INSERT INTO customer( id, username, email, pass, full_name, telephone_number ) VALUES ( 1, 'testuser2', 'testuser2@gmail.com', 'test123', 'Test User 2', '0748255556' ); 
INSERT INTO customer( id, username, email, pass, full_name, telephone_number ) VALUES ( 2, 'testuser3', 'testuser3@gmail.com', 'test123', 'Test User 3', '0748255557' ); 
INSERT INTO customer( id, username, email, pass, full_name, telephone_number ) VALUES ( 3, 'testuser4', 'testuser4@gmail.com', 'test123', 'Test User 4', '0748255558' ); 
INSERT INTO customer( id, username, email, pass, full_name, telephone_number ) VALUES ( 4, 'testuser5', 'testuser5@gmail.com', 'test123', 'Test User 5', '0748255559' ); 

INSERT INTO dietary_requirements( id, name ) VALUES ( 0, 'no requirement' ); 
INSERT INTO dietary_requirements( id, name ) VALUES ( 1, 'spicy' ); 
INSERT INTO dietary_requirements( id, name ) VALUES ( 2, 'vegan' ); 
INSERT INTO dietary_requirements( id, name ) VALUES ( 3, 'vegetarian' ); 

INSERT INTO food_type( id, name ) VALUES ( 0, 'Other' ); 
INSERT INTO food_type( id, name ) VALUES ( 1, 'Indian' ); 
INSERT INTO food_type( id, name ) VALUES ( 2, 'Chinese' ); 
INSERT INTO food_type( id, name ) VALUES ( 3, 'Dessert' ); 
INSERT INTO food_type( id, name ) VALUES ( 4, 'Drink' ); 
INSERT INTO food_type( id, name ) VALUES ( 5, 'Meal' ); 
INSERT INTO food_type( id, name ) VALUES ( 6, 'Meal-Deal' ); 

INSERT INTO ingredients_allergies( id, name ) VALUES ( 0, 'no_allergy' ); 
INSERT INTO ingredients_allergies( id, name ) VALUES ( 1, 'eggs' ); 
INSERT INTO ingredients_allergies( id, name ) VALUES ( 2, 'peanuts' ); 
INSERT INTO ingredients_allergies( id, name ) VALUES ( 3, 'lactouse' ); 
INSERT INTO ingredients_allergies( id, name ) VALUES ( 4, 'gluten' ); 

INSERT INTO job_position( id, name ) VALUES ( 0, 'manager' ); 
INSERT INTO job_position( id, name ) VALUES ( 1, 'chef' ); 
INSERT INTO job_position( id, name ) VALUES ( 2, 'cook' ); 
INSERT INTO job_position( id, name ) VALUES ( 3, 'waiter' ); 

INSERT INTO metric_unit( id, name ) VALUES ( 0, 'quantity' ); 
INSERT INTO metric_unit( id, name ) VALUES ( 1, 'kilogram' ); 
INSERT INTO metric_unit( id, name ) VALUES ( 2, 'liter' ); 

INSERT INTO order_state( id, name ) VALUES ( 0, 'placed' ); 
INSERT INTO order_state( id, name ) VALUES ( 1, 'confirmed' ); 
INSERT INTO order_state( id, name ) VALUES ( 2, 'delivered' ); 
INSERT INTO order_state( id, name ) VALUES ( 3, 'payed' ); 
INSERT INTO order_state( id, name ) VALUES ( 4, 'canceled' ); 
INSERT INTO order_state( id, name ) VALUES ( 5, 'cooked' ); 

INSERT INTO staff( id, username, email, pass, full_name, telephone_number, address, job_position_id, permission_level ) VALUES ( 0, 'teststaffmember1', 'teststaffmember1@gmail.com', 'test123', 'Staff Member', '0748266666', 'TEST', 0, 1 ); 
INSERT INTO staff( id, username, email, pass, full_name, telephone_number, address, job_position_id, permission_level ) VALUES ( 1, 'teststaffmember2', 'teststaffmember2@gmail.com', 'test123', 'Staff Member', '0748266667', 'TEST', 1, 2 ); 
INSERT INTO staff( id, username, email, pass, full_name, telephone_number, address, job_position_id, permission_level ) VALUES ( 2, 'teststaffmember3', 'teststaffmember3@gmail.com', 'test123', 'Staff Member', '0748266668', 'TEST', 2, 3 ); 
INSERT INTO staff( id, username, email, pass, full_name, telephone_number, address, job_position_id, permission_level ) VALUES ( 3, 'teststaffmember4', 'teststaffmember4@gmail.com', 'test123', 'Staff Member', '0748266669', 'TEST', 3, 4 ); 
INSERT INTO staff( id, username, email, pass, full_name, telephone_number, address, job_position_id, permission_level ) VALUES ( 4, 'teststaffmember5', 'teststaffmember4@gmail.com', 'test123', 'Staff Member', '0748266670', 'TEST', 3, 4 ); 

INSERT INTO table_status( id, name ) VALUES ( 0, 'free' ); 
INSERT INTO table_status( id, name ) VALUES ( 1, 'reserved' ); 
INSERT INTO table_status( id, name ) VALUES ( 2, 'unavailable' ); 

INSERT INTO dishes( id, name, cost, picture_url, food_type_id, dietary_req_id, mealdeal_id, sort_order, in_stock ) VALUES ( 0, 'lasagnia', 8.50, 'TEST', 5, 1, null, 1, false ); 
INSERT INTO dishes( id, name, cost, picture_url, food_type_id, dietary_req_id, mealdeal_id, sort_order, in_stock ) VALUES ( 1, 'pizza', 6.50, 'TEST', 5, 0, null, 1, true ); 
INSERT INTO dishes( id, name, cost, picture_url, food_type_id, dietary_req_id, mealdeal_id, sort_order, in_stock ) VALUES ( 2, 'water', 2.50, 'TEST', 4, 0, null, 3, true  ); 
INSERT INTO dishes( id, name, cost, picture_url, food_type_id, dietary_req_id, mealdeal_id, sort_order, in_stock ) VALUES ( 3, 'cola', 1.50, 'TEST', 4, 0, null, 4, true  ); 
INSERT INTO dishes( id, name, cost, picture_url, food_type_id, dietary_req_id, mealdeal_id, sort_order, in_stock ) VALUES ( 4, 'beer', 3.20, 'TEST', 4, 0, null, 5, true  ); 
INSERT INTO dishes( id, name, cost, picture_url, food_type_id, dietary_req_id, mealdeal_id, sort_order, in_stock ) VALUES ( 5, 'coffe', 1.20, 'TEST', 4, 0, null, 6, true  ); 
INSERT INTO dishes( id, name, cost, picture_url, food_type_id, dietary_req_id, mealdeal_id, sort_order, in_stock ) VALUES ( 6, 'donut', 1.00, 'TEST', 3, 0, null, 7, true ); 
INSERT INTO dishes( id, name, cost, picture_url, food_type_id, dietary_req_id, mealdeal_id, sort_order, in_stock ) VALUES ( 7, 'mealdeal1', 5.50, 'TEST', 6, 1, 1, 8, false ); 
INSERT INTO dishes( id, name, cost, picture_url, food_type_id, dietary_req_id, mealdeal_id, sort_order, in_stock ) VALUES ( 8, 'mealdeal2', 7.50, 'TEST', 6, 1, 2, 9, true  ); 

INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 0, 'pepper', 22, 1, 60, 1, 50.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 1, 'carrot', 35, 1, 70, 2, 70.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 2, 'tomattos', 40, 1, 50, 3, 30.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 3, 'cucumbers', 24, 1, 30, 4, 10.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 4, 'onion', 20, 1, 30, 1, 11.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 5, 'cheese', 70, 1, 120, 2, 14.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 6, 'milk', 30, 2, 20, 3, 3.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 7, 'eggs', 43, 0, 20, 4, 1.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 8, 'mixed meat', 25, 1, 50, 1, 12.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 9, 'salami', 12, 1, 70, 2, 5.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 10, 'chicken breast', 60, 1, 40, 3, 7.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 11, 'beef', 53, 1, 10, 4, 8.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 12, 'olive oil', 25, 2, 90, 1, 1.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 13, 'salt', 10, 1, 10, 2, 12.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 14, 'mixed herbs', 7, 1, 20, 3, 6.00 ); 
INSERT INTO ingredients_stock( id, name, cost, metric, calories, allergy_id, quantity ) VALUES ( 15, 'butter', 32, 1, 60, 4, 55.00 ); 

INSERT INTO mealdeal_dishes( mealdeal_id, dish_id ) VALUES ( 1, 0 ); 
INSERT INTO mealdeal_dishes( mealdeal_id, dish_id ) VALUES ( 1, 3 ); 
INSERT INTO mealdeal_dishes( mealdeal_id, dish_id ) VALUES ( 1, 6 ); 
INSERT INTO mealdeal_dishes( mealdeal_id, dish_id ) VALUES ( 2, 1 ); 
INSERT INTO mealdeal_dishes( mealdeal_id, dish_id ) VALUES ( 2, 5 ); 
INSERT INTO mealdeal_dishes( mealdeal_id, dish_id ) VALUES ( 2, 6 ); 

INSERT INTO restourant_table( id, table_status_id, waiter_id ) VALUES ( 0, 0, 0 ); 
INSERT INTO restourant_table( id, table_status_id, waiter_id ) VALUES ( 1, 2, 0 ); 
INSERT INTO restourant_table( id, table_status_id, waiter_id ) VALUES ( 2, 1, 0 ); 
INSERT INTO restourant_table( id, table_status_id, waiter_id ) VALUES ( 3, 0, 0 ); 

INSERT INTO client_devices( id, table_id ) VALUES ( 0, 0 ); 
INSERT INTO client_devices( id, table_id ) VALUES ( 1, 1 ); 
INSERT INTO client_devices( id, table_id ) VALUES ( 2, 2 ); 
INSERT INTO client_devices( id, table_id ) VALUES ( 3, 3 ); 

INSERT INTO device_calls( client_device_id, call_time, call_confirm ) VALUES ( 0, '10:23:33', '10:25:50' ); 
INSERT INTO device_calls( client_device_id, call_time, call_confirm ) VALUES ( 1, '11:55:44', '12:00:00' ); 
INSERT INTO device_calls( client_device_id, call_time, call_confirm ) VALUES ( 2, '15:55:44', '16:05:10' ); 
INSERT INTO device_calls( client_device_id, call_time, call_confirm ) VALUES ( 3, '09:20:32', '09:22:00' ); 

INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 0, 0, 1 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 0, 1, 15 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 0, 2, 15 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 0, 5, 4 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 0, 7, 4 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 0, 9, 6 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 0, 13, 5 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 0, 12, 15 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 0, 15, 3 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 1, 0, 1 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 1, 1, 15 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 1, 5, 4 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 1, 10, 6 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 1, 13, 5 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 1, 12, 15 ); 
INSERT INTO dishes_ingredients( dish_id, ingredient_id, ingredient_qty ) VALUES ( 1, 15, 3 ); 

INSERT INTO orders( id, cost, time_requested, time_confirmed, time_delivered, customer_id, order_state_id, order_device_id, time_completed ) VALUES ( 0, 5.50, '10:35:22', '10:45:22', '11:00:22', 0, 0, 0, null ); 
INSERT INTO orders( id, cost, time_requested, time_confirmed, time_delivered, customer_id, order_state_id, order_device_id, time_completed ) VALUES ( 1, 7.80, '12:22:54', '12:32:54', '12:52:54', 1, 1, 1, null ); 
INSERT INTO orders( id, cost, time_requested, time_confirmed, time_delivered, customer_id, order_state_id, order_device_id, time_completed ) VALUES ( 2, 9.20, '09:02:15', '09:12:15', '09:45:15', 2, 2, 2, null ); 
INSERT INTO orders( id, cost, time_requested, time_confirmed, time_delivered, customer_id, order_state_id, order_device_id, time_completed ) VALUES ( 3, 6.50, '07:44:59', '07:54:59', '08:13:59', 3, 2, 3, null ); 
INSERT INTO orders( id, cost, time_requested, time_confirmed, time_delivered, customer_id, order_state_id, order_device_id, time_completed ) VALUES ( 4, 2.19, '07:12:30', '07:22:30', '07:50:30', 3, 3, 3, null ); 

INSERT INTO reservation( id, table_id, customer_id, reservation_date_time ) VALUES ( 0, 0, 0, '2017-01-01 12:25:33' ); 
INSERT INTO reservation( id, table_id, customer_id, reservation_date_time ) VALUES ( 1, 1, 1, '2017-02-02 13:25:33' ); 
INSERT INTO reservation( id, table_id, customer_id, reservation_date_time ) VALUES ( 2, 2, 2, '2017-03-03 14:25:33' ); 
INSERT INTO reservation( id, table_id, customer_id, reservation_date_time ) VALUES ( 3, 3, 3, '2017-04-04 15:25:33' ); 

INSERT INTO order_dishes( order_id, dish_id ) VALUES ( 0, 1 ); 
INSERT INTO order_dishes( order_id, dish_id ) VALUES ( 1, 1 ); 
INSERT INTO order_dishes( order_id, dish_id ) VALUES ( 2, 1 ); 
INSERT INTO order_dishes( order_id, dish_id ) VALUES ( 3, 1 ); 
INSERT INTO order_dishes( order_id, dish_id ) VALUES ( 4, 1 ); 

