--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.1
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: client_devices; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE client_devices (
    id integer NOT NULL,
    table_id integer
);


--
-- Name: TABLE client_devices; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE client_devices IS 'Client devices in restourant.';


--
-- Name: COLUMN client_devices.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN client_devices.id IS 'ID of client device.';


--
-- Name: COLUMN client_devices.table_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN client_devices.table_id IS 'ID of the table device is located.';


--
-- Name: client_devices_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE client_devices_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: client_devices_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE client_devices_id_seq OWNED BY client_devices.id;


--
-- Name: customer; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE customer (
    id integer NOT NULL,
    username character varying(50),
    email character varying(50),
    pass character varying(50),
    full_name character varying(50),
    telephone_number character varying(20)
);


--
-- Name: TABLE customer; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE customer IS 'Info for customers.';


--
-- Name: COLUMN customer.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN customer.id IS 'ID of customer.';


--
-- Name: COLUMN customer.username; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN customer.username IS 'Username of customer.';


--
-- Name: COLUMN customer.email; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN customer.email IS 'Email of customer.';


--
-- Name: COLUMN customer.pass; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN customer.pass IS 'Password of customer.';


--
-- Name: COLUMN customer.full_name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN customer.full_name IS 'Full name of customer.';


--
-- Name: COLUMN customer.telephone_number; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN customer.telephone_number IS 'Telephone number of customer';


--
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE customer_id_seq OWNED BY customer.id;


--
-- Name: device_calls; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE device_calls (
    client_device_id integer,
    call_time time without time zone,
    call_confirm time without time zone
);


--
-- Name: TABLE device_calls; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE device_calls IS 'Calls for waiters from customers.';


--
-- Name: COLUMN device_calls.client_device_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN device_calls.client_device_id IS 'Client device ID.';


--
-- Name: COLUMN device_calls.call_time; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN device_calls.call_time IS 'Time when waiter was called.';


--
-- Name: COLUMN device_calls.call_confirm; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN device_calls.call_confirm IS 'Time when waiter serviced customer call.';


--
-- Name: dietary_requirements; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE dietary_requirements (
    id integer NOT NULL,
    name character varying(50)
);


--
-- Name: TABLE dietary_requirements; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE dietary_requirements IS 'Dietary requirments for dishes.';


--
-- Name: COLUMN dietary_requirements.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dietary_requirements.id IS 'ID of dietary requirment';


--
-- Name: COLUMN dietary_requirements.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dietary_requirements.name IS 'Dietary requirment name.';


--
-- Name: dietary_requirements_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE dietary_requirements_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: dietary_requirements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE dietary_requirements_id_seq OWNED BY dietary_requirements.id;


--
-- Name: dishes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE dishes (
    id integer NOT NULL,
    name character varying(100),
    cost numeric(6,2),
    picture_url character varying(100),
    food_type_id integer,
    dietary_req_id integer,
    sort_order integer DEFAULT 0,
    in_stock boolean DEFAULT false,
    mealdeal_id integer
);


--
-- Name: TABLE dishes; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE dishes IS 'The dishes that are contained in the menu.';


--
-- Name: COLUMN dishes.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dishes.id IS 'Dishes ID.';


--
-- Name: COLUMN dishes.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dishes.name IS 'Dishes name.';


--
-- Name: COLUMN dishes.cost; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dishes.cost IS 'Dishes price.
';


--
-- Name: COLUMN dishes.picture_url; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dishes.picture_url IS 'URL of pictures of dishes.
';


--
-- Name: COLUMN dishes.food_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dishes.food_type_id IS 'ID of food type.';


--
-- Name: COLUMN dishes.dietary_req_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dishes.dietary_req_id IS 'Dietary requirments ID.';


--
-- Name: COLUMN dishes.sort_order; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dishes.sort_order IS 'Sort order of dishes in menu.';


--
-- Name: COLUMN dishes.in_stock; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dishes.in_stock IS 'True if in stock.';


--
-- Name: COLUMN dishes.mealdeal_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dishes.mealdeal_id IS 'ID of mealdeal.';


--
-- Name: dishes_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE dishes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: dishes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE dishes_id_seq OWNED BY dishes.id;


--
-- Name: dishes_ingredients; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE dishes_ingredients (
    dish_id integer,
    ingredient_id integer,
    ingredient_qty numeric(6,2)
);


--
-- Name: TABLE dishes_ingredients; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE dishes_ingredients IS 'All ingridients that are contained in a single dish.';


--
-- Name: COLUMN dishes_ingredients.dish_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dishes_ingredients.dish_id IS 'Dish ID.';


--
-- Name: COLUMN dishes_ingredients.ingredient_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN dishes_ingredients.ingredient_id IS 'Ingredient ID.';


--
-- Name: extra_sales; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE extra_sales (
    waiter_id integer,
    extra_sales_made integer,
    order_id integer
);


--
-- Name: TABLE extra_sales; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE extra_sales IS 'Extra sales made by waiter.';


--
-- Name: COLUMN extra_sales.waiter_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN extra_sales.waiter_id IS 'ID of the waiter.
';


--
-- Name: COLUMN extra_sales.extra_sales_made; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN extra_sales.extra_sales_made IS 'How many extra sales were made to an order.';


--
-- Name: COLUMN extra_sales.order_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN extra_sales.order_id IS 'ID of order.';


--
-- Name: food_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE food_type (
    id integer NOT NULL,
    name character varying(100)
);


--
-- Name: TABLE food_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE food_type IS 'Type of food in menu.';


--
-- Name: COLUMN food_type.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN food_type.id IS 'Food type ID.';


--
-- Name: COLUMN food_type.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN food_type.name IS 'Food type name.';


--
-- Name: food_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE food_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: food_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE food_type_id_seq OWNED BY food_type.id;


--
-- Name: ingredients_allergies; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE ingredients_allergies (
    id integer NOT NULL,
    name character varying(50)
);


--
-- Name: TABLE ingredients_allergies; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE ingredients_allergies IS 'Allergies that appear in dishes.';


--
-- Name: COLUMN ingredients_allergies.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN ingredients_allergies.id IS 'ID of allergy';


--
-- Name: COLUMN ingredients_allergies.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN ingredients_allergies.name IS 'Name of allergy.';


--
-- Name: ingredients_allergies_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE ingredients_allergies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: ingredients_allergies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE ingredients_allergies_id_seq OWNED BY ingredients_allergies.id;


--
-- Name: ingredients_stock; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE ingredients_stock (
    id integer NOT NULL,
    name character varying(50),
    cost numeric(6,2),
    metric_id integer,
    calories integer,
    allergy_id integer,
    quantity numeric(6,2)
);


--
-- Name: TABLE ingredients_stock; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE ingredients_stock IS 'Contents ingredients.';


--
-- Name: COLUMN ingredients_stock.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN ingredients_stock.id IS 'ID of ingredient';


--
-- Name: COLUMN ingredients_stock.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN ingredients_stock.name IS 'Name of ingredient';


--
-- Name: COLUMN ingredients_stock.cost; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN ingredients_stock.cost IS 'Price of ingredient.';


--
-- Name: COLUMN ingredients_stock.metric_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN ingredients_stock.metric_id IS 'Metric of ingredient.';


--
-- Name: COLUMN ingredients_stock.calories; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN ingredients_stock.calories IS 'Calories of ingredient.';


--
-- Name: COLUMN ingredients_stock.allergy_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN ingredients_stock.allergy_id IS 'Allergy ID of ingredient.';


--
-- Name: COLUMN ingredients_stock.quantity; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN ingredients_stock.quantity IS 'Quantity of ingredients in stock';


--
-- Name: ingredients_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE ingredients_stock_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: ingredients_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE ingredients_stock_id_seq OWNED BY ingredients_stock.id;


--
-- Name: job_position; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE job_position (
    id integer NOT NULL,
    name character varying(30)
);


--
-- Name: TABLE job_position; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE job_position IS 'Contains job positions at the restourant.';


--
-- Name: COLUMN job_position.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN job_position.id IS 'Job position ID.
';


--
-- Name: COLUMN job_position.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN job_position.name IS 'Name of job position';


--
-- Name: job_position_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE job_position_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: job_position_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE job_position_id_seq OWNED BY job_position.id;


--
-- Name: mealdeal_dishes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE mealdeal_dishes (
    mealdeal_id integer,
    dish_id integer
);


--
-- Name: TABLE mealdeal_dishes; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE mealdeal_dishes IS 'Dishes contained in meadeal.';


--
-- Name: COLUMN mealdeal_dishes.mealdeal_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN mealdeal_dishes.mealdeal_id IS 'ID of a mealdeal.';


--
-- Name: COLUMN mealdeal_dishes.dish_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN mealdeal_dishes.dish_id IS 'ID of a dish.';


--
-- Name: metric_unit; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE metric_unit (
    id integer NOT NULL,
    name character varying(10)
);


--
-- Name: TABLE metric_unit; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE metric_unit IS 'Metric units (for ingredients).';


--
-- Name: COLUMN metric_unit.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN metric_unit.id IS 'Unit ID.';


--
-- Name: COLUMN metric_unit.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN metric_unit.name IS 'Unit name.';


--
-- Name: metric_unit_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE metric_unit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: metric_unit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE metric_unit_id_seq OWNED BY metric_unit.id;


--
-- Name: order_dishes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE order_dishes (
    dish_id integer,
    order_id integer
);


--
-- Name: TABLE order_dishes; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE order_dishes IS 'Dishes that are included in an order.';


--
-- Name: COLUMN order_dishes.dish_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN order_dishes.dish_id IS 'ID of dish.
';


--
-- Name: COLUMN order_dishes.order_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN order_dishes.order_id IS 'ID of order.';


--
-- Name: order_state; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE order_state (
    id integer NOT NULL,
    name character varying(100)
);


--
-- Name: TABLE order_state; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE order_state IS 'Sates of  orders.';


--
-- Name: COLUMN order_state.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN order_state.id IS 'ID of order state.';


--
-- Name: COLUMN order_state.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN order_state.name IS 'Name of order state.';


--
-- Name: order_state_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE order_state_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: order_state_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE order_state_id_seq OWNED BY order_state.id;


--
-- Name: orders; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE orders (
    id integer NOT NULL,
    order_device_id integer,
    customer_id integer,
    cost numeric(6,2),
    time_requested time without time zone,
    time_confirmed time without time zone,
    time_delivered time without time zone,
    time_completed time without time zone,
    order_state_id integer
);


--
-- Name: TABLE orders; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE orders IS 'Contains orders from clients.';


--
-- Name: COLUMN orders.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN orders.id IS 'ID of order.';


--
-- Name: COLUMN orders.order_device_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN orders.order_device_id IS 'The ID of the device that made the order.';


--
-- Name: COLUMN orders.customer_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN orders.customer_id IS 'ID of customer.';


--
-- Name: COLUMN orders.cost; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN orders.cost IS 'Price of order.';


--
-- Name: COLUMN orders.time_requested; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN orders.time_requested IS 'Time order was requested.';


--
-- Name: COLUMN orders.time_confirmed; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN orders.time_confirmed IS 'Time until order was confirmed.';


--
-- Name: COLUMN orders.time_delivered; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN orders.time_delivered IS 'Time until order was delivered.';


--
-- Name: COLUMN orders.time_completed; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN orders.time_completed IS 'Time until order was completed.';


--
-- Name: COLUMN orders.order_state_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN orders.order_state_id IS 'Current state of order.';


--
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE orders_id_seq OWNED BY orders.id;


--
-- Name: reservation; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE reservation (
    id integer NOT NULL,
    table_id integer,
    customer_id integer,
    reservation_date_time timestamp without time zone
);


--
-- Name: TABLE reservation; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE reservation IS 'Contains reservations in the restourant.';


--
-- Name: COLUMN reservation.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN reservation.id IS 'ID of reservation';


--
-- Name: COLUMN reservation.table_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN reservation.table_id IS 'ID of the table reserved.';


--
-- Name: COLUMN reservation.customer_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN reservation.customer_id IS 'ID of customer';


--
-- Name: COLUMN reservation.reservation_date_time; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN reservation.reservation_date_time IS 'What date and time is the table reserved for.';


--
-- Name: reservation_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: reservation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE reservation_id_seq OWNED BY reservation.id;


--
-- Name: restourant_table; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE restourant_table (
    id integer NOT NULL,
    table_status_id integer,
    waiter_id integer
);


--
-- Name: TABLE restourant_table; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE restourant_table IS 'Represents a table in the restourant.';


--
-- Name: COLUMN restourant_table.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN restourant_table.id IS 'ID of the table.';


--
-- Name: COLUMN restourant_table.table_status_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN restourant_table.table_status_id IS 'ID showing status of a table.';


--
-- Name: COLUMN restourant_table.waiter_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN restourant_table.waiter_id IS 'ID of waiter operating on a table.';


--
-- Name: restourant_table_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE restourant_table_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: restourant_table_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE restourant_table_id_seq OWNED BY restourant_table.id;


--
-- Name: staff; Type: TABLE; Schema: public; Owner: -
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


--
-- Name: TABLE staff; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE staff IS 'Staff of restourant.';


--
-- Name: COLUMN staff.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN staff.id IS 'ID of staff member.';


--
-- Name: COLUMN staff.username; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN staff.username IS 'Username of staff member.';


--
-- Name: COLUMN staff.email; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN staff.email IS 'Email of staff membr';


--
-- Name: COLUMN staff.pass; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN staff.pass IS 'Password of staff member.';


--
-- Name: COLUMN staff.full_name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN staff.full_name IS 'Full name of staff member';


--
-- Name: COLUMN staff.telephone_number; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN staff.telephone_number IS 'Telephone number of staff member.';


--
-- Name: COLUMN staff.address; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN staff.address IS 'Current address of staff member.';


--
-- Name: COLUMN staff.job_position_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN staff.job_position_id IS 'Job position of staff member';


--
-- Name: COLUMN staff.permission_level; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN staff.permission_level IS 'Level of permission of staff member';


--
-- Name: staff_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE staff_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: staff_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE staff_id_seq OWNED BY staff.id;


--
-- Name: table_status; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE table_status (
    id integer NOT NULL,
    name character varying(20)
);


--
-- Name: TABLE table_status; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE table_status IS 'Shows the possible states of a table.';


--
-- Name: COLUMN table_status.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN table_status.id IS 'ID of table.';


--
-- Name: COLUMN table_status.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN table_status.name IS 'Status name.';


--
-- Name: table_status_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE table_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: table_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE table_status_id_seq OWNED BY table_status.id;


--
-- Name: client_devices id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY client_devices ALTER COLUMN id SET DEFAULT nextval('client_devices_id_seq'::regclass);


--
-- Name: customer id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer ALTER COLUMN id SET DEFAULT nextval('customer_id_seq'::regclass);


--
-- Name: dietary_requirements id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY dietary_requirements ALTER COLUMN id SET DEFAULT nextval('dietary_requirements_id_seq'::regclass);


--
-- Name: dishes id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY dishes ALTER COLUMN id SET DEFAULT nextval('dishes_id_seq'::regclass);


--
-- Name: food_type id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY food_type ALTER COLUMN id SET DEFAULT nextval('food_type_id_seq'::regclass);


--
-- Name: ingredients_allergies id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY ingredients_allergies ALTER COLUMN id SET DEFAULT nextval('ingredients_allergies_id_seq'::regclass);


--
-- Name: ingredients_stock id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY ingredients_stock ALTER COLUMN id SET DEFAULT nextval('ingredients_stock_id_seq'::regclass);


--
-- Name: job_position id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_position ALTER COLUMN id SET DEFAULT nextval('job_position_id_seq'::regclass);


--
-- Name: metric_unit id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY metric_unit ALTER COLUMN id SET DEFAULT nextval('metric_unit_id_seq'::regclass);


--
-- Name: order_state id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_state ALTER COLUMN id SET DEFAULT nextval('order_state_id_seq'::regclass);


--
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY orders ALTER COLUMN id SET DEFAULT nextval('orders_id_seq'::regclass);


--
-- Name: reservation id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation ALTER COLUMN id SET DEFAULT nextval('reservation_id_seq'::regclass);


--
-- Name: restourant_table id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY restourant_table ALTER COLUMN id SET DEFAULT nextval('restourant_table_id_seq'::regclass);


--
-- Name: staff id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY staff ALTER COLUMN id SET DEFAULT nextval('staff_id_seq'::regclass);


--
-- Name: table_status id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY table_status ALTER COLUMN id SET DEFAULT nextval('table_status_id_seq'::regclass);


--
-- Data for Name: client_devices; Type: TABLE DATA; Schema: public; Owner: -
--

COPY client_devices (id, table_id) FROM stdin;
1	1
2	2
3	3
4	4
\.


--
-- Name: client_devices_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('client_devices_id_seq', 4, true);


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: -
--

COPY customer (id, username, email, pass, full_name, telephone_number) FROM stdin;
1	testuser1	testuser1@gmail.com	test123	Test User 1	0748255555
2	testuser2	testuser2@gmail.com	test123	Test User 2	0748255556
3	testuser3	testuser3@gmail.com	test123	Test User 3	0748255557
4	testuser4	testuser4@gmail.com	test123	Test User 4	0748255558
5	testuser5	testuser5@gmail.com	test123	Test User 5	0748255559
\.


--
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('customer_id_seq', 5, true);


--
-- Data for Name: device_calls; Type: TABLE DATA; Schema: public; Owner: -
--

COPY device_calls (client_device_id, call_time, call_confirm) FROM stdin;
1	10:23:33	10:25:50
2	11:55:44	12:00:00
3	15:55:44	16:05:10
4	09:20:32	09:22:00
\.


--
-- Data for Name: dietary_requirements; Type: TABLE DATA; Schema: public; Owner: -
--

COPY dietary_requirements (id, name) FROM stdin;
1	none
2	spicy
3	vegan
4	vegetarian
\.


--
-- Name: dietary_requirements_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('dietary_requirements_id_seq', 4, true);


--
-- Data for Name: dishes; Type: TABLE DATA; Schema: public; Owner: -
--

COPY dishes (id, name, cost, picture_url, food_type_id, dietary_req_id, sort_order, in_stock, mealdeal_id) FROM stdin;
4	Cola	1.50	TEST	4	2	4	t	\N
5	Beer	3.20	TEST	4	2	5	t	\N
7	apple	1.20	TEST	3	2	7	t	\N
6	Coffee	2.30	TEST	4	2	6	t	\N
8	Mealdeal1	5.50	TEST	6	1	8	f	1
9	Mealdeal2	7.50	TEST	6	1	9	t	2
1	Lasagne	2.64	TEST	7	1	1	f	\N
3	Water	5.00	TEST	4	2	3	t	\N
2	Pizza	1.31	TEST	7	2	1	t	\N
\.


--
-- Name: dishes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('dishes_id_seq', 9, true);


--
-- Data for Name: dishes_ingredients; Type: TABLE DATA; Schema: public; Owner: -
--

COPY dishes_ingredients (dish_id, ingredient_id, ingredient_qty) FROM stdin;
1	3	15.00
1	7	4.00
1	9	6.00
2	6	10.00
1	6	10.00
2	10	10.00
\.


--
-- Data for Name: extra_sales; Type: TABLE DATA; Schema: public; Owner: -
--

COPY extra_sales (waiter_id, extra_sales_made, order_id) FROM stdin;
\.


--
-- Data for Name: food_type; Type: TABLE DATA; Schema: public; Owner: -
--

COPY food_type (id, name) FROM stdin;
1	Other
2	Indian
3	Chinese
4	Dessert
5	Drink
6	Meal-Deal
7	food
\.


--
-- Name: food_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('food_type_id_seq', 7, true);


--
-- Data for Name: ingredients_allergies; Type: TABLE DATA; Schema: public; Owner: -
--

COPY ingredients_allergies (id, name) FROM stdin;
1	no_allergy
2	eggs
3	peanuts
4	lactouse
5	gluten
\.


--
-- Name: ingredients_allergies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('ingredients_allergies_id_seq', 5, true);


--
-- Data for Name: ingredients_stock; Type: TABLE DATA; Schema: public; Owner: -
--

COPY ingredients_stock (id, name, cost, metric_id, calories, allergy_id, quantity) FROM stdin;
1	pepper	22.00	1	60	1	50.00
5	onion	20.00	1	30	1	11.00
6	cheese	70.00	1	120	2	14.00
12	beef	53.00	1	10	4	8.00
14	salt	10.00	1	10	2	12.00
16	butter	32.00	1	60	4	55.00
15	mixed_herbs	7.00	1	20	3	6.00
7	milk	30.00	2	20	3	250.00
11	chicken_breast	60.00	1	40	3	1000.00
13	olive_oil	25.00	2	90	1	50.00
2	carrot	35.00	1	70	2	500.00
8	eggs	43.00	1	20	4	550.00
10	salami	12.00	1	70	2	3000.00
9	mixed_meat	25.00	1	50	1	900.00
3	tomato	40.00	1	50	3	656.00
4	cucumbers	24.00	1	30	4	300.00
\.


--
-- Name: ingredients_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('ingredients_stock_id_seq', 16, true);


--
-- Data for Name: job_position; Type: TABLE DATA; Schema: public; Owner: -
--

COPY job_position (id, name) FROM stdin;
1	manager
2	chef
3	cook
4	waiter
\.


--
-- Name: job_position_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('job_position_id_seq', 4, true);


--
-- Data for Name: mealdeal_dishes; Type: TABLE DATA; Schema: public; Owner: -
--

COPY mealdeal_dishes (mealdeal_id, dish_id) FROM stdin;
1	2
1	3
1	6
2	1
2	5
2	6
\.


--
-- Data for Name: metric_unit; Type: TABLE DATA; Schema: public; Owner: -
--

COPY metric_unit (id, name) FROM stdin;
1	quantity
2	kilogram
3	liter
\.


--
-- Name: metric_unit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('metric_unit_id_seq', 3, true);


--
-- Data for Name: order_dishes; Type: TABLE DATA; Schema: public; Owner: -
--

COPY order_dishes (dish_id, order_id) FROM stdin;
1	1
1	3
1	4
1	5
2	93
1	96
2	96
1	97
1	48
2	48
1	49
2	49
1	50
1	51
1	52
2	52
1	53
1	55
2	55
1	56
1	58
2	58
1	59
1	62
2	62
1	63
1	66
2	66
1	67
1	70
2	70
1	71
1	74
2	74
1	75
1	78
2	78
1	79
2	80
1	82
2	82
1	83
2	84
1	86
2	86
1	87
2	88
1	91
2	91
1	92
\.


--
-- Data for Name: order_state; Type: TABLE DATA; Schema: public; Owner: -
--

COPY order_state (id, name) FROM stdin;
1	placed
2	confirmed
3	delivered
4	payed
5	canceled
6	cooked
\.


--
-- Name: order_state_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('order_state_id_seq', 6, true);


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: -
--

COPY orders (id, order_device_id, customer_id, cost, time_requested, time_confirmed, time_delivered, time_completed, order_state_id) FROM stdin;
4	4	4	6.50	07:44:59	07:54:59	08:13:59	\N	5
1	1	1	5.50	10:35:22	10:45:22	11:00:22	\N	2
52	2	2	3.95	01:03:25	\N	\N	\N	1
53	2	2	2.64	01:03:27	\N	\N	\N	1
55	2	2	3.95	01:09:31	\N	\N	\N	1
56	2	2	2.64	01:09:33	\N	\N	\N	1
58	2	2	3.95	01:14:06	\N	\N	\N	1
59	2	2	2.64	01:14:08	\N	\N	\N	1
62	2	2	3.95	01:34:20	\N	\N	\N	1
63	2	2	2.64	01:34:22	\N	\N	\N	1
66	2	2	3.95	01:37:10	\N	\N	\N	1
67	2	2	2.64	01:37:12	\N	\N	\N	1
70	2	2	3.95	01:41:07	\N	\N	\N	1
71	2	2	2.64	01:41:09	\N	\N	\N	1
74	2	2	3.95	01:42:28	\N	\N	\N	1
75	2	2	2.64	01:42:30	\N	\N	\N	1
78	2	2	3.95	01:43:56	\N	\N	\N	1
79	2	2	2.64	01:43:58	\N	\N	\N	1
80	2	2	1.31	01:45:59	\N	\N	\N	1
82	2	2	3.95	01:46:04	\N	\N	\N	1
83	2	2	2.64	01:46:06	\N	\N	\N	1
84	2	2	1.31	01:51:59	\N	\N	\N	1
5	4	4	2.19	07:12:30	07:22:30	07:50:30	\N	1
3	3	3	9.20	09:02:15	09:12:15	09:45:15	\N	3
86	2	2	3.95	01:52:05	\N	\N	\N	1
87	2	2	2.64	01:52:07	\N	\N	\N	1
88	2	2	1.31	02:01:14	\N	\N	\N	1
91	2	2	3.95	02:01:50	\N	\N	\N	1
92	2	2	2.64	02:02:01	\N	\N	\N	1
93	2	2	1.31	02:05:13	\N	\N	\N	1
96	2	2	3.95	02:05:43	\N	\N	\N	1
97	2	2	2.64	02:05:47	\N	\N	\N	1
48	2	2	3.95	01:00:47	\N	\N	\N	5
51	2	2	2.64	01:03:23	\N	\N	\N	4
49	2	2	3.95	01:00:50	\N	\N	\N	5
50	2	2	2.64	01:00:52	\N	\N	\N	3
\.


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('orders_id_seq', 97, true);


--
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: -
--

COPY reservation (id, table_id, customer_id, reservation_date_time) FROM stdin;
1	1	1	2017-01-01 12:25:33
2	2	2	2017-02-02 13:25:33
3	3	3	2017-03-03 14:25:33
4	4	4	2017-04-04 15:25:33
\.


--
-- Name: reservation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('reservation_id_seq', 4, true);


--
-- Data for Name: restourant_table; Type: TABLE DATA; Schema: public; Owner: -
--

COPY restourant_table (id, table_status_id, waiter_id) FROM stdin;
3	1	5
4	3	5
1	1	4
2	2	4
\.


--
-- Name: restourant_table_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('restourant_table_id_seq', 4, true);


--
-- Data for Name: staff; Type: TABLE DATA; Schema: public; Owner: -
--

COPY staff (id, username, email, pass, full_name, telephone_number, address, job_position_id, permission_level) FROM stdin;
5	teststaffmember5	teststaffmember4@gmail.com	test123	Christos	0748266670	TEST	4	4
1	teststaffmember1	teststaffmember1@gmail.com	test123	Grigor	0748266666	TEST	1	1
4	teststaffmember4	teststaffmember4@gmail.com	test123	Svetoslav	0748266669	TEST	4	4
2	teststaffmember2	teststaffmember2@gmail.com	test123	Mark	0748266667	TEST	2	2
3	teststaffmember3	teststaffmember3@gmail.com	test123	Jamie	0748266668	TEST	3	3
\.


--
-- Name: staff_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('staff_id_seq', 5, true);


--
-- Data for Name: table_status; Type: TABLE DATA; Schema: public; Owner: -
--

COPY table_status (id, name) FROM stdin;
1	free
2	reserved
3	unavailable
\.


--
-- Name: table_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('table_status_id_seq', 3, true);


--
-- Name: dishes idx_dishes; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dishes
    ADD CONSTRAINT idx_dishes UNIQUE (mealdeal_id);


--
-- Name: client_devices pk_client_devices; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY client_devices
    ADD CONSTRAINT pk_client_devices PRIMARY KEY (id);


--
-- Name: customer pk_customer; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT pk_customer PRIMARY KEY (id);


--
-- Name: dishes pk_dishes; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dishes
    ADD CONSTRAINT pk_dishes PRIMARY KEY (id);


--
-- Name: ingredients_allergies pk_dishes_allergies; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ingredients_allergies
    ADD CONSTRAINT pk_dishes_allergies PRIMARY KEY (id);


--
-- Name: dietary_requirements pk_dishes_dietary_requirements; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dietary_requirements
    ADD CONSTRAINT pk_dishes_dietary_requirements PRIMARY KEY (id);


--
-- Name: food_type pk_food_type; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY food_type
    ADD CONSTRAINT pk_food_type PRIMARY KEY (id);


--
-- Name: ingredients_stock pk_ingredients; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ingredients_stock
    ADD CONSTRAINT pk_ingredients PRIMARY KEY (id);


--
-- Name: job_position pk_job_position; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_position
    ADD CONSTRAINT pk_job_position PRIMARY KEY (id);


--
-- Name: metric_unit pk_metric_unit; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY metric_unit
    ADD CONSTRAINT pk_metric_unit PRIMARY KEY (id);


--
-- Name: order_state pk_order_state_0; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_state
    ADD CONSTRAINT pk_order_state_0 PRIMARY KEY (id);


--
-- Name: orders pk_orders; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT pk_orders PRIMARY KEY (id);


--
-- Name: reservation pk_reservation; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT pk_reservation PRIMARY KEY (id);


--
-- Name: staff pk_staff; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY staff
    ADD CONSTRAINT pk_staff PRIMARY KEY (id);


--
-- Name: restourant_table pk_table; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY restourant_table
    ADD CONSTRAINT pk_table PRIMARY KEY (id);


--
-- Name: table_status pk_table_status; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY table_status
    ADD CONSTRAINT pk_table_status PRIMARY KEY (id);


--
-- Name: idx_client_devices; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_client_devices ON client_devices USING btree (table_id);


--
-- Name: idx_device_calls; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_device_calls ON device_calls USING btree (client_device_id);


--
-- Name: idx_dishes_0; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_dishes_0 ON dishes USING btree (food_type_id);


--
-- Name: idx_dishes_1; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_dishes_1 ON dishes USING btree (dietary_req_id);


--
-- Name: idx_dishes_ingredients; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_dishes_ingredients ON dishes_ingredients USING btree (dish_id);


--
-- Name: idx_dishes_ingredients_0; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_dishes_ingredients_0 ON dishes_ingredients USING btree (ingredient_id);


--
-- Name: idx_extra_sales; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_extra_sales ON extra_sales USING btree (waiter_id);


--
-- Name: idx_extra_sales_0; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_extra_sales_0 ON extra_sales USING btree (order_id);


--
-- Name: idx_ingredients_stock; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_ingredients_stock ON ingredients_stock USING btree (allergy_id);


--
-- Name: idx_mealdeal_dishes; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_mealdeal_dishes ON mealdeal_dishes USING btree (dish_id);


--
-- Name: idx_mealdeal_dishes_0; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_mealdeal_dishes_0 ON mealdeal_dishes USING btree (mealdeal_id);


--
-- Name: idx_orders; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_orders ON orders USING btree (order_state_id);


--
-- Name: idx_orders_0; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_orders_0 ON orders USING btree (order_device_id);


--
-- Name: idx_orders_1; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_orders_1 ON orders USING btree (customer_id);


--
-- Name: idx_reservation; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_reservation ON reservation USING btree (table_id);


--
-- Name: idx_reservation_0; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_reservation_0 ON reservation USING btree (customer_id);


--
-- Name: idx_staff; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_staff ON staff USING btree (job_position_id);


--
-- Name: idx_table_0; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_table_0 ON restourant_table USING btree (table_status_id);


--
-- Name: idx_table_1; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_table_1 ON restourant_table USING btree (waiter_id);


--
-- Name: client_devices fk_client_devices; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY client_devices
    ADD CONSTRAINT fk_client_devices FOREIGN KEY (table_id) REFERENCES restourant_table(id);


--
-- Name: device_calls fk_device_calls; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY device_calls
    ADD CONSTRAINT fk_device_calls FOREIGN KEY (client_device_id) REFERENCES client_devices(id);


--
-- Name: dishes fk_dishes; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dishes
    ADD CONSTRAINT fk_dishes FOREIGN KEY (food_type_id) REFERENCES food_type(id);


--
-- Name: dishes fk_dishes_0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dishes
    ADD CONSTRAINT fk_dishes_0 FOREIGN KEY (dietary_req_id) REFERENCES dietary_requirements(id);


--
-- Name: dishes_ingredients fk_dishes_ingredients; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dishes_ingredients
    ADD CONSTRAINT fk_dishes_ingredients FOREIGN KEY (ingredient_id) REFERENCES ingredients_stock(id);


--
-- Name: dishes_ingredients fk_dishes_ingredients_0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dishes_ingredients
    ADD CONSTRAINT fk_dishes_ingredients_0 FOREIGN KEY (dish_id) REFERENCES dishes(id);


--
-- Name: extra_sales fk_extra_sales; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY extra_sales
    ADD CONSTRAINT fk_extra_sales FOREIGN KEY (waiter_id) REFERENCES staff(id);


--
-- Name: extra_sales fk_extra_sales_0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY extra_sales
    ADD CONSTRAINT fk_extra_sales_0 FOREIGN KEY (order_id) REFERENCES orders(id);


--
-- Name: ingredients_stock fk_ingredients_stock; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ingredients_stock
    ADD CONSTRAINT fk_ingredients_stock FOREIGN KEY (metric_id) REFERENCES metric_unit(id);


--
-- Name: ingredients_stock fk_ingredients_stock_0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ingredients_stock
    ADD CONSTRAINT fk_ingredients_stock_0 FOREIGN KEY (allergy_id) REFERENCES ingredients_allergies(id);


--
-- Name: mealdeal_dishes fk_mealdeal_dishes; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mealdeal_dishes
    ADD CONSTRAINT fk_mealdeal_dishes FOREIGN KEY (mealdeal_id) REFERENCES dishes(mealdeal_id);


--
-- Name: mealdeal_dishes fk_mealdeal_dishes_0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mealdeal_dishes
    ADD CONSTRAINT fk_mealdeal_dishes_0 FOREIGN KEY (dish_id) REFERENCES dishes(id);


--
-- Name: order_dishes fk_order_dishes; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_dishes
    ADD CONSTRAINT fk_order_dishes FOREIGN KEY (dish_id) REFERENCES dishes(id);


--
-- Name: order_dishes fk_order_dishes_0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_dishes
    ADD CONSTRAINT fk_order_dishes_0 FOREIGN KEY (order_id) REFERENCES orders(id);


--
-- Name: orders fk_orders; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_orders FOREIGN KEY (order_state_id) REFERENCES order_state(id);


--
-- Name: orders fk_orders_0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_orders_0 FOREIGN KEY (order_device_id) REFERENCES client_devices(id);


--
-- Name: orders fk_orders_1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_orders_1 FOREIGN KEY (customer_id) REFERENCES customer(id);


--
-- Name: reservation fk_reservation; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk_reservation FOREIGN KEY (table_id) REFERENCES restourant_table(id);


--
-- Name: reservation fk_reservation_0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk_reservation_0 FOREIGN KEY (customer_id) REFERENCES customer(id);


--
-- Name: staff fk_staff; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY staff
    ADD CONSTRAINT fk_staff FOREIGN KEY (job_position_id) REFERENCES job_position(id);


--
-- Name: restourant_table fk_table_0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY restourant_table
    ADD CONSTRAINT fk_table_0 FOREIGN KEY (table_status_id) REFERENCES table_status(id);


--
-- Name: restourant_table fk_table_1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY restourant_table
    ADD CONSTRAINT fk_table_1 FOREIGN KEY (waiter_id) REFERENCES staff(id);


--
-- PostgreSQL database dump complete
--

