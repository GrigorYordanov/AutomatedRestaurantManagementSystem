--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.1
-- Dumped by pg_dump version 9.6.1

-- Started on 2017-01-24 22:23:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2273 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 202 (class 1259 OID 25002)
-- Name: client_devices; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE client_devices (
    id integer NOT NULL,
    table_id integer,
    call_waiter boolean
);


ALTER TABLE client_devices OWNER TO "Test";

--
-- TOC entry 2274 (class 0 OID 0)
-- Dependencies: 202
-- Name: TABLE client_devices; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE client_devices IS 'Client devices in restourant.';


--
-- TOC entry 2275 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN client_devices.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN client_devices.id IS 'ID of client device.';


--
-- TOC entry 2276 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN client_devices.table_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN client_devices.table_id IS 'ID of the table device is located.';


--
-- TOC entry 2277 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN client_devices.call_waiter; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN client_devices.call_waiter IS 'True if waiter is called to the table from device.';


--
-- TOC entry 196 (class 1259 OID 24944)
-- Name: customer; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE customer (
    id integer NOT NULL,
    username character varying(50),
    email character varying(50),
    pass character varying(50),
    full_name character varying(50),
    telephone_number character varying(20)
);


ALTER TABLE customer OWNER TO "Test";

--
-- TOC entry 2278 (class 0 OID 0)
-- Dependencies: 196
-- Name: TABLE customer; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE customer IS 'Info for customers.';


--
-- TOC entry 2279 (class 0 OID 0)
-- Dependencies: 196
-- Name: COLUMN customer.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN customer.id IS 'ID of customer.';


--
-- TOC entry 2280 (class 0 OID 0)
-- Dependencies: 196
-- Name: COLUMN customer.username; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN customer.username IS 'Username of customer.';


--
-- TOC entry 2281 (class 0 OID 0)
-- Dependencies: 196
-- Name: COLUMN customer.email; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN customer.email IS 'Email of customer.';


--
-- TOC entry 2282 (class 0 OID 0)
-- Dependencies: 196
-- Name: COLUMN customer.pass; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN customer.pass IS 'Password of customer.';


--
-- TOC entry 2283 (class 0 OID 0)
-- Dependencies: 196
-- Name: COLUMN customer.full_name; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN customer.full_name IS 'Full name of customer.';


--
-- TOC entry 2284 (class 0 OID 0)
-- Dependencies: 196
-- Name: COLUMN customer.telephone_number; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN customer.telephone_number IS 'Telephone number of customer';


--
-- TOC entry 204 (class 1259 OID 26019)
-- Name: db_tables; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE db_tables (
    id integer NOT NULL,
    table_name character varying(30)
);


ALTER TABLE db_tables OWNER TO "Test";

--
-- TOC entry 189 (class 1259 OID 24802)
-- Name: dietary_requirements; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE dietary_requirements (
    id integer NOT NULL,
    name character varying(50)
);


ALTER TABLE dietary_requirements OWNER TO "Test";

--
-- TOC entry 2285 (class 0 OID 0)
-- Dependencies: 189
-- Name: TABLE dietary_requirements; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE dietary_requirements IS 'Dietary requirments for dishes.';


--
-- TOC entry 2286 (class 0 OID 0)
-- Dependencies: 189
-- Name: COLUMN dietary_requirements.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dietary_requirements.id IS 'ID of dietary requirment';


--
-- TOC entry 2287 (class 0 OID 0)
-- Dependencies: 189
-- Name: COLUMN dietary_requirements.name; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dietary_requirements.name IS 'Dietary requirment name.';


--
-- TOC entry 186 (class 1259 OID 24653)
-- Name: dishes; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE dishes (
    id integer NOT NULL,
    name character varying(100),
    cost numeric(6,2),
    picture_url character varying(100),
    food_type_id integer,
    dietary_req_id integer,
    mealdeal_id integer,
    sort_order integer,
    in_stock boolean
);


ALTER TABLE dishes OWNER TO "Test";

--
-- TOC entry 2288 (class 0 OID 0)
-- Dependencies: 186
-- Name: TABLE dishes; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE dishes IS 'The dishes that are contained in the menu.';


--
-- TOC entry 2289 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN dishes.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dishes.id IS 'Dishes ID.';


--
-- TOC entry 2290 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN dishes.name; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dishes.name IS 'Dishes name.';


--
-- TOC entry 2291 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN dishes.cost; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dishes.cost IS 'Dishes price.
';


--
-- TOC entry 2292 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN dishes.picture_url; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dishes.picture_url IS 'URL of pictures of dishes.
';


--
-- TOC entry 2293 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN dishes.food_type_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dishes.food_type_id IS 'ID of food type.';


--
-- TOC entry 2294 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN dishes.dietary_req_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dishes.dietary_req_id IS 'Dietary requirments ID.';


--
-- TOC entry 2295 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN dishes.mealdeal_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dishes.mealdeal_id IS 'ID of mealdeal.';


--
-- TOC entry 2296 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN dishes.sort_order; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dishes.sort_order IS 'Sort order of dishes in menu.';


--
-- TOC entry 2297 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN dishes.in_stock; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dishes.in_stock IS 'True if in stock.';


--
-- TOC entry 187 (class 1259 OID 24658)
-- Name: dishes_ingredients; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE dishes_ingredients (
    dish_id integer,
    ingredient_id integer,
    ingredient_qty integer
);


ALTER TABLE dishes_ingredients OWNER TO "Test";

--
-- TOC entry 2298 (class 0 OID 0)
-- Dependencies: 187
-- Name: TABLE dishes_ingredients; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE dishes_ingredients IS 'All ingridients that are contained in a single dish.';


--
-- TOC entry 2299 (class 0 OID 0)
-- Dependencies: 187
-- Name: COLUMN dishes_ingredients.dish_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dishes_ingredients.dish_id IS 'Dish ID.';


--
-- TOC entry 2300 (class 0 OID 0)
-- Dependencies: 187
-- Name: COLUMN dishes_ingredients.ingredient_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN dishes_ingredients.ingredient_id IS 'Ingredient ID.';


--
-- TOC entry 203 (class 1259 OID 25999)
-- Name: extra_sales; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE extra_sales (
    waiter_id integer,
    extra_sales_made integer,
    order_id integer
);


ALTER TABLE extra_sales OWNER TO "Test";

--
-- TOC entry 2301 (class 0 OID 0)
-- Dependencies: 203
-- Name: TABLE extra_sales; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE extra_sales IS 'Extra sales made by waiter.';


--
-- TOC entry 2302 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN extra_sales.waiter_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN extra_sales.waiter_id IS 'ID of the waiter.
';


--
-- TOC entry 2303 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN extra_sales.extra_sales_made; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN extra_sales.extra_sales_made IS 'How many extra sales were made to an order.';


--
-- TOC entry 2304 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN extra_sales.order_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN extra_sales.order_id IS 'ID of order.';


--
-- TOC entry 188 (class 1259 OID 24681)
-- Name: food_type; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE food_type (
    id integer NOT NULL,
    name character varying(100)
);


ALTER TABLE food_type OWNER TO "Test";

--
-- TOC entry 2305 (class 0 OID 0)
-- Dependencies: 188
-- Name: TABLE food_type; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE food_type IS 'Type of food in menu.';


--
-- TOC entry 2306 (class 0 OID 0)
-- Dependencies: 188
-- Name: COLUMN food_type.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN food_type.id IS 'Food type ID.';


--
-- TOC entry 2307 (class 0 OID 0)
-- Dependencies: 188
-- Name: COLUMN food_type.name; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN food_type.name IS 'Food type name.';


--
-- TOC entry 190 (class 1259 OID 24807)
-- Name: ingredients_allergies; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE ingredients_allergies (
    id integer NOT NULL,
    name character varying(50)
);


ALTER TABLE ingredients_allergies OWNER TO "Test";

--
-- TOC entry 2308 (class 0 OID 0)
-- Dependencies: 190
-- Name: TABLE ingredients_allergies; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE ingredients_allergies IS 'Allergies that appear in dishes.';


--
-- TOC entry 2309 (class 0 OID 0)
-- Dependencies: 190
-- Name: COLUMN ingredients_allergies.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN ingredients_allergies.id IS 'ID of allergy';


--
-- TOC entry 2310 (class 0 OID 0)
-- Dependencies: 190
-- Name: COLUMN ingredients_allergies.name; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN ingredients_allergies.name IS 'Name of allergy.';


--
-- TOC entry 191 (class 1259 OID 24823)
-- Name: ingredients_stock; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE ingredients_stock (
    id integer NOT NULL,
    name character varying(50),
    cost integer,
    metric integer,
    calories integer,
    allergy_id integer,
    quantity numeric(6,2)
);


ALTER TABLE ingredients_stock OWNER TO "Test";

--
-- TOC entry 2311 (class 0 OID 0)
-- Dependencies: 191
-- Name: TABLE ingredients_stock; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE ingredients_stock IS 'Contents ingredients.';


--
-- TOC entry 2312 (class 0 OID 0)
-- Dependencies: 191
-- Name: COLUMN ingredients_stock.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN ingredients_stock.id IS 'ID of ingredient';


--
-- TOC entry 2313 (class 0 OID 0)
-- Dependencies: 191
-- Name: COLUMN ingredients_stock.name; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN ingredients_stock.name IS 'Name of ingredient';


--
-- TOC entry 2314 (class 0 OID 0)
-- Dependencies: 191
-- Name: COLUMN ingredients_stock.cost; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN ingredients_stock.cost IS 'Price of ingredient.';


--
-- TOC entry 2315 (class 0 OID 0)
-- Dependencies: 191
-- Name: COLUMN ingredients_stock.metric; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN ingredients_stock.metric IS 'Metric of ingredient.';


--
-- TOC entry 2316 (class 0 OID 0)
-- Dependencies: 191
-- Name: COLUMN ingredients_stock.calories; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN ingredients_stock.calories IS 'Calories of ingredient.';


--
-- TOC entry 2317 (class 0 OID 0)
-- Dependencies: 191
-- Name: COLUMN ingredients_stock.allergy_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN ingredients_stock.allergy_id IS 'Allergy ID of ingredient.';


--
-- TOC entry 2318 (class 0 OID 0)
-- Dependencies: 191
-- Name: COLUMN ingredients_stock.quantity; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN ingredients_stock.quantity IS 'Quantity of ingredients in stock';


--
-- TOC entry 198 (class 1259 OID 24968)
-- Name: job_position; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE job_position (
    id integer NOT NULL,
    name character varying(30)
);


ALTER TABLE job_position OWNER TO "Test";

--
-- TOC entry 2319 (class 0 OID 0)
-- Dependencies: 198
-- Name: TABLE job_position; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE job_position IS 'Contains job positions at the restourant.';


--
-- TOC entry 2320 (class 0 OID 0)
-- Dependencies: 198
-- Name: COLUMN job_position.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN job_position.id IS 'Job position ID.
';


--
-- TOC entry 2321 (class 0 OID 0)
-- Dependencies: 198
-- Name: COLUMN job_position.name; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN job_position.name IS 'Name of job position';


--
-- TOC entry 192 (class 1259 OID 24885)
-- Name: mealdeal_dishes; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE mealdeal_dishes (
    mealdeal_id integer,
    dish_id integer
);


ALTER TABLE mealdeal_dishes OWNER TO "Test";

--
-- TOC entry 2322 (class 0 OID 0)
-- Dependencies: 192
-- Name: TABLE mealdeal_dishes; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE mealdeal_dishes IS 'Dishes contained in meadeal.';


--
-- TOC entry 2323 (class 0 OID 0)
-- Dependencies: 192
-- Name: COLUMN mealdeal_dishes.mealdeal_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN mealdeal_dishes.mealdeal_id IS 'ID of a mealdeal.';


--
-- TOC entry 2324 (class 0 OID 0)
-- Dependencies: 192
-- Name: COLUMN mealdeal_dishes.dish_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN mealdeal_dishes.dish_id IS 'ID of a dish.';


--
-- TOC entry 185 (class 1259 OID 24641)
-- Name: metric_unit; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE metric_unit (
    id integer NOT NULL,
    name character varying(10)
);


ALTER TABLE metric_unit OWNER TO "Test";

--
-- TOC entry 2325 (class 0 OID 0)
-- Dependencies: 185
-- Name: TABLE metric_unit; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE metric_unit IS 'Metric units (for ingredients).';


--
-- TOC entry 2326 (class 0 OID 0)
-- Dependencies: 185
-- Name: COLUMN metric_unit.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN metric_unit.id IS 'Unit ID.';


--
-- TOC entry 2327 (class 0 OID 0)
-- Dependencies: 185
-- Name: COLUMN metric_unit.name; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN metric_unit.name IS 'Unit name.';


--
-- TOC entry 194 (class 1259 OID 24908)
-- Name: order_dishes; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE order_dishes (
    order_id integer,
    dish_id integer
);


ALTER TABLE order_dishes OWNER TO "Test";

--
-- TOC entry 2328 (class 0 OID 0)
-- Dependencies: 194
-- Name: TABLE order_dishes; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE order_dishes IS 'Dishes that are included in an order.';


--
-- TOC entry 2329 (class 0 OID 0)
-- Dependencies: 194
-- Name: COLUMN order_dishes.order_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN order_dishes.order_id IS 'ID of order.';


--
-- TOC entry 2330 (class 0 OID 0)
-- Dependencies: 194
-- Name: COLUMN order_dishes.dish_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN order_dishes.dish_id IS 'ID of dish.
';


--
-- TOC entry 195 (class 1259 OID 24925)
-- Name: order_state; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE order_state (
    id integer NOT NULL,
    name character varying(100)
);


ALTER TABLE order_state OWNER TO "Test";

--
-- TOC entry 2331 (class 0 OID 0)
-- Dependencies: 195
-- Name: TABLE order_state; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE order_state IS 'Sates of  orders.';


--
-- TOC entry 2332 (class 0 OID 0)
-- Dependencies: 195
-- Name: COLUMN order_state.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN order_state.id IS 'ID of order state.';


--
-- TOC entry 2333 (class 0 OID 0)
-- Dependencies: 195
-- Name: COLUMN order_state.name; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN order_state.name IS 'Name of order state.';


--
-- TOC entry 193 (class 1259 OID 24903)
-- Name: orders; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE orders (
    id integer NOT NULL,
    cost numeric(6,2),
    time_requested time without time zone,
    time_confirmed time without time zone,
    time_completed time without time zone,
    customer_id integer,
    order_state_id integer,
    order_device_id integer
);


ALTER TABLE orders OWNER TO "Test";

--
-- TOC entry 2334 (class 0 OID 0)
-- Dependencies: 193
-- Name: TABLE orders; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE orders IS 'Contains orders from clients.';


--
-- TOC entry 2335 (class 0 OID 0)
-- Dependencies: 193
-- Name: COLUMN orders.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN orders.id IS 'ID of order.';


--
-- TOC entry 2336 (class 0 OID 0)
-- Dependencies: 193
-- Name: COLUMN orders.cost; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN orders.cost IS 'Price of order.';


--
-- TOC entry 2337 (class 0 OID 0)
-- Dependencies: 193
-- Name: COLUMN orders.time_requested; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN orders.time_requested IS 'Time order was requested.';


--
-- TOC entry 2338 (class 0 OID 0)
-- Dependencies: 193
-- Name: COLUMN orders.time_confirmed; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN orders.time_confirmed IS 'Time until order was confirmed.';


--
-- TOC entry 2339 (class 0 OID 0)
-- Dependencies: 193
-- Name: COLUMN orders.time_completed; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN orders.time_completed IS 'Time until order was completed.';


--
-- TOC entry 2340 (class 0 OID 0)
-- Dependencies: 193
-- Name: COLUMN orders.customer_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN orders.customer_id IS 'ID of customer.';


--
-- TOC entry 2341 (class 0 OID 0)
-- Dependencies: 193
-- Name: COLUMN orders.order_state_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN orders.order_state_id IS 'Current state of order.';


--
-- TOC entry 2342 (class 0 OID 0)
-- Dependencies: 193
-- Name: COLUMN orders.order_device_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN orders.order_device_id IS 'The ID of the device that made the order.';


--
-- TOC entry 199 (class 1259 OID 24980)
-- Name: reservation; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE reservation (
    id integer NOT NULL,
    table_id integer,
    customer_id integer,
    reservation_date_time timestamp without time zone
);


ALTER TABLE reservation OWNER TO "Test";

--
-- TOC entry 2343 (class 0 OID 0)
-- Dependencies: 199
-- Name: TABLE reservation; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE reservation IS 'Contains reservations in the restourant.';


--
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 199
-- Name: COLUMN reservation.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN reservation.id IS 'ID of reservation';


--
-- TOC entry 2345 (class 0 OID 0)
-- Dependencies: 199
-- Name: COLUMN reservation.table_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN reservation.table_id IS 'ID of the table reserved.';


--
-- TOC entry 2346 (class 0 OID 0)
-- Dependencies: 199
-- Name: COLUMN reservation.customer_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN reservation.customer_id IS 'ID of customer';


--
-- TOC entry 2347 (class 0 OID 0)
-- Dependencies: 199
-- Name: COLUMN reservation.reservation_date_time; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN reservation.reservation_date_time IS 'What date and time is the table reserved for.';


--
-- TOC entry 197 (class 1259 OID 24956)
-- Name: staff; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE staff (
    id integer NOT NULL,
    username character varying(50),
    email character varying(50),
    pass character varying(50),
    full_name character varying(50),
    telephone_number character varying(20),
    address character varying(100),
    job_position_id integer,
    permission_level integer
);


ALTER TABLE staff OWNER TO "Test";

--
-- TOC entry 2348 (class 0 OID 0)
-- Dependencies: 197
-- Name: TABLE staff; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE staff IS 'Staff of restourant.';


--
-- TOC entry 2349 (class 0 OID 0)
-- Dependencies: 197
-- Name: COLUMN staff.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN staff.id IS 'ID of staff member.';


--
-- TOC entry 2350 (class 0 OID 0)
-- Dependencies: 197
-- Name: COLUMN staff.username; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN staff.username IS 'Username of staff member.';


--
-- TOC entry 2351 (class 0 OID 0)
-- Dependencies: 197
-- Name: COLUMN staff.email; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN staff.email IS 'Email of staff membr';


--
-- TOC entry 2352 (class 0 OID 0)
-- Dependencies: 197
-- Name: COLUMN staff.pass; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN staff.pass IS 'Password of staff member.';


--
-- TOC entry 2353 (class 0 OID 0)
-- Dependencies: 197
-- Name: COLUMN staff.full_name; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN staff.full_name IS 'Full name of staff member';


--
-- TOC entry 2354 (class 0 OID 0)
-- Dependencies: 197
-- Name: COLUMN staff.telephone_number; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN staff.telephone_number IS 'Telephone number of staff member.';


--
-- TOC entry 2355 (class 0 OID 0)
-- Dependencies: 197
-- Name: COLUMN staff.address; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN staff.address IS 'Current address of staff member.';


--
-- TOC entry 2356 (class 0 OID 0)
-- Dependencies: 197
-- Name: COLUMN staff.job_position_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN staff.job_position_id IS 'Job position of staff member';


--
-- TOC entry 2357 (class 0 OID 0)
-- Dependencies: 197
-- Name: COLUMN staff.permission_level; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN staff.permission_level IS 'Level of permission of staff member';


--
-- TOC entry 200 (class 1259 OID 24985)
-- Name: table_; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE table_ (
    id integer NOT NULL,
    table_status_id integer,
    waiter_id integer
);


ALTER TABLE table_ OWNER TO "Test";

--
-- TOC entry 2358 (class 0 OID 0)
-- Dependencies: 200
-- Name: TABLE table_; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE table_ IS 'Represents a table in the restourant.';


--
-- TOC entry 2359 (class 0 OID 0)
-- Dependencies: 200
-- Name: COLUMN table_.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN table_.id IS 'ID of the table.';


--
-- TOC entry 2360 (class 0 OID 0)
-- Dependencies: 200
-- Name: COLUMN table_.table_status_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN table_.table_status_id IS 'ID showing status of a table.';


--
-- TOC entry 2361 (class 0 OID 0)
-- Dependencies: 200
-- Name: COLUMN table_.waiter_id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN table_.waiter_id IS 'ID of waiter operating on a table.';


--
-- TOC entry 201 (class 1259 OID 24990)
-- Name: table_status; Type: TABLE; Schema: public; Owner: Test
--

CREATE TABLE table_status (
    id integer NOT NULL,
    name character varying(20)
);


ALTER TABLE table_status OWNER TO "Test";

--
-- TOC entry 2362 (class 0 OID 0)
-- Dependencies: 201
-- Name: TABLE table_status; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON TABLE table_status IS 'Shows the possible states of a table.';


--
-- TOC entry 2363 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN table_status.id; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN table_status.id IS 'ID of table.';


--
-- TOC entry 2364 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN table_status.name; Type: COMMENT; Schema: public; Owner: Test
--

COMMENT ON COLUMN table_status.name IS 'Status name.';


--
-- TOC entry 2128 (class 2606 OID 26023)
-- Name: db_tables db_tables_pkey; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY db_tables
    ADD CONSTRAINT db_tables_pkey PRIMARY KEY (id);


--
-- TOC entry 2078 (class 2606 OID 25157)
-- Name: dishes idx_dishes; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY dishes
    ADD CONSTRAINT idx_dishes UNIQUE (mealdeal_id);


--
-- TOC entry 2124 (class 2606 OID 25006)
-- Name: client_devices pk_client_devices; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY client_devices
    ADD CONSTRAINT pk_client_devices PRIMARY KEY (id);


--
-- TOC entry 2106 (class 2606 OID 24948)
-- Name: customer pk_customer; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT pk_customer PRIMARY KEY (id);


--
-- TOC entry 2082 (class 2606 OID 24657)
-- Name: dishes pk_dishes; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY dishes
    ADD CONSTRAINT pk_dishes PRIMARY KEY (id);


--
-- TOC entry 2090 (class 2606 OID 24811)
-- Name: ingredients_allergies pk_dishes_allergies; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY ingredients_allergies
    ADD CONSTRAINT pk_dishes_allergies PRIMARY KEY (id);


--
-- TOC entry 2088 (class 2606 OID 24806)
-- Name: dietary_requirements pk_dishes_dietary_requirements; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY dietary_requirements
    ADD CONSTRAINT pk_dishes_dietary_requirements PRIMARY KEY (id);


--
-- TOC entry 2086 (class 2606 OID 24685)
-- Name: food_type pk_food_type; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY food_type
    ADD CONSTRAINT pk_food_type PRIMARY KEY (id);


--
-- TOC entry 2093 (class 2606 OID 24827)
-- Name: ingredients_stock pk_ingredients; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY ingredients_stock
    ADD CONSTRAINT pk_ingredients PRIMARY KEY (id);


--
-- TOC entry 2111 (class 2606 OID 24972)
-- Name: job_position pk_job_position; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY job_position
    ADD CONSTRAINT pk_job_position PRIMARY KEY (id);


--
-- TOC entry 2076 (class 2606 OID 24645)
-- Name: metric_unit pk_metric_unit; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY metric_unit
    ADD CONSTRAINT pk_metric_unit PRIMARY KEY (id);


--
-- TOC entry 2102 (class 2606 OID 24929)
-- Name: order_state pk_order_state; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY order_state
    ADD CONSTRAINT pk_order_state UNIQUE (id);


--
-- TOC entry 2104 (class 2606 OID 24936)
-- Name: order_state pk_order_state_0; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY order_state
    ADD CONSTRAINT pk_order_state_0 PRIMARY KEY (id);


--
-- TOC entry 2100 (class 2606 OID 24907)
-- Name: orders pk_orders; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT pk_orders PRIMARY KEY (id);


--
-- TOC entry 2115 (class 2606 OID 24984)
-- Name: reservation pk_reservation; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT pk_reservation PRIMARY KEY (id);


--
-- TOC entry 2109 (class 2606 OID 24960)
-- Name: staff pk_staff; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY staff
    ADD CONSTRAINT pk_staff PRIMARY KEY (id);


--
-- TOC entry 2119 (class 2606 OID 24989)
-- Name: table_ pk_table; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY table_
    ADD CONSTRAINT pk_table PRIMARY KEY (id);


--
-- TOC entry 2121 (class 2606 OID 24994)
-- Name: table_status pk_table_status; Type: CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY table_status
    ADD CONSTRAINT pk_table_status PRIMARY KEY (id);


--
-- TOC entry 2122 (class 1259 OID 25904)
-- Name: idx_client_devices; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_client_devices ON client_devices USING btree (table_id);


--
-- TOC entry 2079 (class 1259 OID 25144)
-- Name: idx_dishes_0; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_dishes_0 ON dishes USING btree (food_type_id);


--
-- TOC entry 2080 (class 1259 OID 25150)
-- Name: idx_dishes_1; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_dishes_1 ON dishes USING btree (dietary_req_id);


--
-- TOC entry 2083 (class 1259 OID 25238)
-- Name: idx_dishes_ingredients; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_dishes_ingredients ON dishes_ingredients USING btree (dish_id);


--
-- TOC entry 2084 (class 1259 OID 25244)
-- Name: idx_dishes_ingredients_0; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_dishes_ingredients_0 ON dishes_ingredients USING btree (ingredient_id);


--
-- TOC entry 2125 (class 1259 OID 26002)
-- Name: idx_extra_sales; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_extra_sales ON extra_sales USING btree (waiter_id);


--
-- TOC entry 2126 (class 1259 OID 26013)
-- Name: idx_extra_sales_0; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_extra_sales_0 ON extra_sales USING btree (order_id);


--
-- TOC entry 2091 (class 1259 OID 25303)
-- Name: idx_ingredients_stock; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_ingredients_stock ON ingredients_stock USING btree (allergy_id);


--
-- TOC entry 2094 (class 1259 OID 25606)
-- Name: idx_mealdeal_dishes; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_mealdeal_dishes ON mealdeal_dishes USING btree (dish_id);


--
-- TOC entry 2095 (class 1259 OID 25612)
-- Name: idx_mealdeal_dishes_0; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_mealdeal_dishes_0 ON mealdeal_dishes USING btree (mealdeal_id);


--
-- TOC entry 2096 (class 1259 OID 25703)
-- Name: idx_orders; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_orders ON orders USING btree (order_state_id);


--
-- TOC entry 2097 (class 1259 OID 25709)
-- Name: idx_orders_0; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_orders_0 ON orders USING btree (order_device_id);


--
-- TOC entry 2098 (class 1259 OID 25715)
-- Name: idx_orders_1; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_orders_1 ON orders USING btree (customer_id);


--
-- TOC entry 2112 (class 1259 OID 25966)
-- Name: idx_reservation; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_reservation ON reservation USING btree (table_id);


--
-- TOC entry 2113 (class 1259 OID 25972)
-- Name: idx_reservation_0; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_reservation_0 ON reservation USING btree (customer_id);


--
-- TOC entry 2107 (class 1259 OID 25940)
-- Name: idx_staff; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_staff ON staff USING btree (job_position_id);


--
-- TOC entry 2116 (class 1259 OID 25898)
-- Name: idx_table_0; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_table_0 ON table_ USING btree (table_status_id);


--
-- TOC entry 2117 (class 1259 OID 25946)
-- Name: idx_table_1; Type: INDEX; Schema: public; Owner: Test
--

CREATE INDEX idx_table_1 ON table_ USING btree (waiter_id);


--
-- TOC entry 2147 (class 2606 OID 25925)
-- Name: client_devices fk_client_devices; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY client_devices
    ADD CONSTRAINT fk_client_devices FOREIGN KEY (table_id) REFERENCES table_(id);


--
-- TOC entry 2130 (class 2606 OID 25145)
-- Name: dishes fk_dishes; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY dishes
    ADD CONSTRAINT fk_dishes FOREIGN KEY (food_type_id) REFERENCES food_type(id);


--
-- TOC entry 2129 (class 2606 OID 25565)
-- Name: dishes fk_dishes_0; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY dishes
    ADD CONSTRAINT fk_dishes_0 FOREIGN KEY (dietary_req_id) REFERENCES dietary_requirements(id);


--
-- TOC entry 2131 (class 2606 OID 25422)
-- Name: dishes_ingredients fk_dishes_ingredients; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY dishes_ingredients
    ADD CONSTRAINT fk_dishes_ingredients FOREIGN KEY (ingredient_id) REFERENCES ingredients_stock(id);


--
-- TOC entry 2132 (class 2606 OID 25427)
-- Name: dishes_ingredients fk_dishes_ingredients_0; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY dishes_ingredients
    ADD CONSTRAINT fk_dishes_ingredients_0 FOREIGN KEY (dish_id) REFERENCES dishes(id);


--
-- TOC entry 2148 (class 2606 OID 26003)
-- Name: extra_sales fk_extra_sales; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY extra_sales
    ADD CONSTRAINT fk_extra_sales FOREIGN KEY (waiter_id) REFERENCES staff(id);


--
-- TOC entry 2149 (class 2606 OID 26014)
-- Name: extra_sales fk_extra_sales_0; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY extra_sales
    ADD CONSTRAINT fk_extra_sales_0 FOREIGN KEY (order_id) REFERENCES orders(id);


--
-- TOC entry 2133 (class 2606 OID 25298)
-- Name: ingredients_stock fk_ingredients_stock; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY ingredients_stock
    ADD CONSTRAINT fk_ingredients_stock FOREIGN KEY (metric) REFERENCES metric_unit(id);


--
-- TOC entry 2134 (class 2606 OID 25304)
-- Name: ingredients_stock fk_ingredients_stock_0; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY ingredients_stock
    ADD CONSTRAINT fk_ingredients_stock_0 FOREIGN KEY (allergy_id) REFERENCES ingredients_allergies(id);


--
-- TOC entry 2135 (class 2606 OID 25613)
-- Name: mealdeal_dishes fk_mealdeal_dishes; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY mealdeal_dishes
    ADD CONSTRAINT fk_mealdeal_dishes FOREIGN KEY (mealdeal_id) REFERENCES dishes(mealdeal_id);


--
-- TOC entry 2136 (class 2606 OID 25623)
-- Name: mealdeal_dishes fk_mealdeal_dishes_0; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY mealdeal_dishes
    ADD CONSTRAINT fk_mealdeal_dishes_0 FOREIGN KEY (dish_id) REFERENCES dishes(id);


--
-- TOC entry 2141 (class 2606 OID 25173)
-- Name: order_dishes fk_order_dishes; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY order_dishes
    ADD CONSTRAINT fk_order_dishes FOREIGN KEY (dish_id) REFERENCES dishes(id);


--
-- TOC entry 2140 (class 2606 OID 25693)
-- Name: order_dishes fk_order_dishes_0; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY order_dishes
    ADD CONSTRAINT fk_order_dishes_0 FOREIGN KEY (order_id) REFERENCES orders(id);


--
-- TOC entry 2139 (class 2606 OID 25704)
-- Name: orders fk_orders; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_orders FOREIGN KEY (order_state_id) REFERENCES order_state(id);


--
-- TOC entry 2138 (class 2606 OID 25832)
-- Name: orders fk_orders_0; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_orders_0 FOREIGN KEY (order_device_id) REFERENCES client_devices(id);


--
-- TOC entry 2137 (class 2606 OID 25837)
-- Name: orders fk_orders_1; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_orders_1 FOREIGN KEY (customer_id) REFERENCES customer(id);


--
-- TOC entry 2143 (class 2606 OID 25967)
-- Name: reservation fk_reservation; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk_reservation FOREIGN KEY (table_id) REFERENCES table_(id);


--
-- TOC entry 2144 (class 2606 OID 25973)
-- Name: reservation fk_reservation_0; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk_reservation_0 FOREIGN KEY (customer_id) REFERENCES customer(id);


--
-- TOC entry 2142 (class 2606 OID 25941)
-- Name: staff fk_staff; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY staff
    ADD CONSTRAINT fk_staff FOREIGN KEY (job_position_id) REFERENCES job_position(id);


--
-- TOC entry 2145 (class 2606 OID 25899)
-- Name: table_ fk_table_0; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY table_
    ADD CONSTRAINT fk_table_0 FOREIGN KEY (table_status_id) REFERENCES table_status(id);


--
-- TOC entry 2146 (class 2606 OID 25957)
-- Name: table_ fk_table_1; Type: FK CONSTRAINT; Schema: public; Owner: Test
--

ALTER TABLE ONLY table_
    ADD CONSTRAINT fk_table_1 FOREIGN KEY (waiter_id) REFERENCES staff(id);


-- Completed on 2017-01-24 22:23:09

--
-- PostgreSQL database dump complete
--

