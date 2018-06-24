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
    call_time timestamp without time zone,
    call_confirm timestamp without time zone
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

COMMENT ON COLUMN device_calls.call_time IS 'call time for waiters';


--
-- Name: COLUMN device_calls.call_confirm; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN device_calls.call_confirm IS 'confirm time of waiter';


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
    name character varying(20)
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
    order_state_id integer,
    time_requested timestamp without time zone,
    time_confirmed timestamp without time zone,
    time_delivered timestamp without time zone,
    time_completed timestamp without time zone
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
-- Name: COLUMN orders.order_state_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN orders.order_state_id IS 'Current state of order.';


--
-- Name: COLUMN orders.time_requested; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN orders.time_requested IS 'order time requested';


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
2	1
3	1
4	1
5	2
6	2
7	2
8	2
9	3
10	3
11	3
12	3
13	4
14	4
15	4
16	4
17	5
18	5
19	6
20	6
21	7
22	7
23	8
24	8
\.


--
-- Name: client_devices_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('client_devices_id_seq', 4, true);


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: -
--

COPY customer (id, username, email, pass, full_name, telephone_number) FROM stdin;
6	alymichalka	alymichalka@gmail.com	qwe123	Aly_Michalka	07482578548
7	nicholashoult	nicholashoult@gmail.com	qwe123	Nicholas_Hoult	07482478488
8	adambrody	adambrody@gmail.com	qw123	Adam_Brody	07482569869
9	chrispine	chrispine@gmail.com	qwe123	Chris_Pine	07482547868
10	natalieportman	natalieportman@gmail.com	qwe123	Natalie_Portman	07482236236
11	alexpettyfer	alexpettyfer@gmail.com	qwe123	Alex_Pettyfer	07482368362
12	meganfox	meganfox@gmail.com	qwe123	Megan_Fox	07482346734
13	emmastone	emmastone@gmail.com	qwe123	Emma_Stone	07482236231
14	ellenpage	emmastone\r\nellenpage@gmail.com\r\n\r\n	qwe123	Ellen_Page	07482323563
15	jessicalowndes	jessicalowndes@gmail.com	qwe123	Jessica_Lowndes	07482247223
16	leamichele	leamichele@gmail.com	qwe123	Lea_Michele	07482362666
17	jessicaalba	jessicaalba@gmail.com	qwe123	Jessica_Alba	07482456856
18	zacefron	zacefron@gmail.com	qwe123	Zac_Efron	07482335683
19	vanessahudgens	vanessahudgens@gmail.com	qwe123	Vanessa_Hudgens	07482678766
20	taylorlautner	taylorlautner@gmail.com	qwe123	Taylor_Lautner	07482236363
1	diannaagron	dianaagron@gmail.com	qwe123	Dianna_Agron	07482334651
2	keypanabaker	keypanabaker@gmail.com	qwe123	Kay_Panabaker	07482324611
3	lucyhale	lucyhale@gmail.com	qwe123	Lucy_Hale	07482363468
4	ashleybenson	ashleybenson@gmail.com	qwe123	Ashley_Benson	07482346744
5	adrianalima	adrianalima@gmail.com	qwe123	Adriana_Lima	07482367347
\.


--
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('customer_id_seq', 5, true);


--
-- Data for Name: device_calls; Type: TABLE DATA; Schema: public; Owner: -
--

COPY device_calls (client_device_id, call_time, call_confirm) FROM stdin;
1	2017-03-01 16:16:32	2017-03-01 16:21:44
2	2017-03-01 14:44:36	2017-03-01 14:51:55
3	2017-03-01 11:14:53	2017-03-01 11:17:43
4	2017-03-01 12:31:53	2017-03-01 12:39:48
5	2017-03-01 13:22:12	2017-03-01 13:29:54
6	2017-03-01 13:54:37	2017-03-01 13:59:55
7	2017-03-01 14:44:31	2017-03-01 14:49:22
8	2017-03-01 16:55:25	2017-03-01 17:01:27
9	2017-03-01 17:01:01	2017-03-01 17:04:57
10	2017-03-01 17:11:23	2017-03-01 17:20:27
11	2017-03-01 17:23:22	2017-03-01 17:29:39
12	2017-03-02 17:35:25	2017-03-02 17:42:43
13	2017-03-02 18:22:31	2017-03-02 18:27:55
14	2017-03-02 18:41:56	2017-03-02 18:46:33
15	2017-03-02 18:55:37	2017-03-02 18:59:59
16	2017-03-02 19:43:56	2017-03-02 19:52:53
17	2017-03-02 19:15:15	2017-03-02 19:20:44
18	2017-03-02 19:56:31	2017-03-02 20:01:37
19	2017-03-02 20:16:32	2017-03-02 20:22:49
20	2017-03-02 20:17:55	2017-03-02 20:21:16
21	2017-03-02 20:33:33	2017-03-02 20:40:04
22	2017-03-03 21:01:07	2017-03-03 21:05:16
23	2017-03-03 21:04:32	2017-03-03 21:09:08
24	2017-03-03 21:16:33	2017-03-03 21:16:46
1	2017-03-03 21:23:23	2017-03-03 21:26:48
2	2017-03-03 22:06:04	2017-03-03 22:08:12
3	2017-03-03 22:12:45	2017-03-03 22:15:01
4	2017-03-03 22:20:07	2017-03-03 22:27:41
5	2017-03-03 22:55:00	2017-03-03 22:59:45
6	2017-03-04 23:01:01	2017-03-04 23:04:07
7	2017-03-04 23:06:31	2017-03-04 23:16:04
8	2017-03-04 14:16:43	2017-03-04 14:20:56
9	2017-03-04 14:23:45	2017-03-04 14:27:56
10	2017-03-04 14:43:54	2017-03-04 14:48:51
11	2017-03-04 16:43:32	2017-03-04 16:51:03
12	2017-03-05 16:44:53	2017-03-05 16:51:53
13	2017-03-05 15:43:54	2017-03-05 15:52:48
14	2017-03-05 11:43:34	2017-03-05 11:48:51
15	2017-03-05 12:16:51	2017-03-05 12:22:55
16	2017-03-05 13:41:51	2017-03-05 13:46:32
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
23	Breakfast_Meal_Deal	5.00	Breakfast_Meal_Deal_Url	5	1	23	t	1
24	Lunch_Meal_Deal	6.00	Lunch_Meal_Deal_Url	5	1	24	t	2
25	Dinner_Meal_Deal	7.00	Dinner_Meal_Deal_Url	5	1	25	t	3
1	Sandwich	234.00	Sandwich#_Url	1	1	1	f	\N
2	Pizza	324.00	Pizza#_Url	1	2	2	t	\N
3	Pizza_Vegiterian	234.00	Pizza#_Vegiterian#_Url	1	4	3	t	\N
4	Pork_And_Potatoes	234.00	Pork#_And#_Potatoes#_Url	1	1	4	t	\N
5	Beef_And_Rice	234.00	Beef#_And#_Rice#_Url	1	2	5	t	\N
6	Chicken_And_Potatoes	234.00	Chicken#_And#_Potatoes#_Url	1	1	6	f	\N
7	Salad	234.00	Salad#_#_Url	4	1	7	t	\N
8	Fruit_Salad	23.00	Fruit#_Salad#_Url	2	1	8	t	\N
9	Omlet	4356.00	Omlet#_#_Url	1	1	9	t	\N
10	Chicken_Soup	23.00	Chicken_Soup_Url	1	2	10	t	\N
11	Fish_Soup	23.00	Fish_Soup_Url	1	1	11	t	\N
12	Vegiterian_Soup	23.00	Vegiterian_Soup_Url	1	4	12	t	\N
13	Apple_Pie	23.00	Apple_Pie_Url	3	1	13	t	\N
14	Banana_Cake	23.00	Banana_Cake_Url	3	1	14	t	\N
15	Coffe	23.00	Coffe_Url	6	1	15	t	\N
16	Beer_330ml	23.00	Beer__Url	7	1	16	t	\N
17	Beer_500ml	23.00	Beer__Url	7	1	17	t	\N
18	Spring_Water_330ml	23.00	Spring_Water_Url	6	1	18	t	\N
19	Spring_Water_500ml	23.00	Spring_Water_Url	6	1	19	t	\N
20	Tea	23.00	Tea_Url	6	1	20	t	\N
21	Coca_Cola_330ml	23.00	Coca_Cola_Url	6	1	21	t	\N
22	Coca_Cola_500ml	23.00	Coca_Cola_Url	6	1	22	t	\N
\.


--
-- Name: dishes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('dishes_id_seq', 9, true);


--
-- Data for Name: dishes_ingredients; Type: TABLE DATA; Schema: public; Owner: -
--

COPY dishes_ingredients (dish_id, ingredient_id, ingredient_qty) FROM stdin;
1	37	0.20
1	15	0.05
1	24	0.01
1	12	0.10
1	6	0.10
1	20	0.13
1	16	0.05
2	14	0.10
2	15	0.10
2	22	0.20
2	23	0.40
2	24	0.01
2	26	0.03
2	31	0.10
2	32	0.10
2	8	0.05
3	23	0.40
3	24	0.01
3	13	0.07
3	15	0.10
3	8	0.05
3	6	0.15
3	26	0.03
4	24	0.01
4	14	0.15
4	16	0.05
4	7	0.20
4	22	0.25
5	24	0.01
5	8	0.08
5	16	0.05
5	21	0.25
5	30	0.20
6	24	0.01
6	9	0.07
6	16	0.05
6	7	0.25
6	20	0.25
7	24	0.01
7	12	0.20
7	11	0.10
7	15	0.10
7	10	0.06
7	8	0.03
7	6	0.14
7	41	0.07
8	1	0.10
8	2	0.12
8	4	0.13
8	5	0.50
9	24	0.01
9	18	4.00
9	16	0.05
9	11	0.15
9	22	0.10
10	24	0.01
10	20	0.30
10	10	0.10
10	8	0.05
10	9	0.03
10	23	0.01
11	24	0.01
11	19	0.30
11	10	0.10
11	8	0.05
11	9	0.03
11	23	0.01
12	24	0.01
12	10	0.10
12	8	0.05
12	9	0.03
12	6	0.09
12	23	0.10
13	25	0.05
13	29	0.10
13	1	0.40
13	38	0.50
13	4	0.10
13	3	0.50
14	25	0.05
14	29	0.10
14	2	0.50
14	38	0.50
14	4	0.10
15	27	0.10
15	17	0.05
15	25	0.05
16	36	0.33
17	36	0.50
18	35	0.33
19	35	0.50
20	29	0.03
20	33	0.30
20	25	0.01
21	34	0.33
22	34	0.50
\.


--
-- Data for Name: extra_sales; Type: TABLE DATA; Schema: public; Owner: -
--

COPY extra_sales (waiter_id, extra_sales_made, order_id) FROM stdin;
1	5	1
2	4	17
3	3	34
4	6	51
5	7	68
6	12	76
7	33	84
8	2	90
1	7	3
2	6	19
3	9	41
4	15	58
5	3	76
6	12	80
7	14	87
8	11	96
1	8	4
2	9	22
3	17	47
4	20	64
\.


--
-- Data for Name: food_type; Type: TABLE DATA; Schema: public; Owner: -
--

COPY food_type (id, name) FROM stdin;
1	Meal
2	Fruit
3	Dessert
4	Salad
5	Meal_Deal
6	None_Alcoholic_Drink
7	Alcoholic_Drink
\.


--
-- Name: food_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('food_type_id_seq', 7, true);


--
-- Data for Name: ingredients_allergies; Type: TABLE DATA; Schema: public; Owner: -
--

COPY ingredients_allergies (id, name) FROM stdin;
1	non-allergenic
2	Eggs
3	Peanuts
4	Fish
5	Wheat
6	Soy
7	Milk
\.


--
-- Name: ingredients_allergies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('ingredients_allergies_id_seq', 5, true);


--
-- Data for Name: ingredients_stock; Type: TABLE DATA; Schema: public; Owner: -
--

COPY ingredients_stock (id, name, cost, metric_id, calories, allergy_id, quantity) FROM stdin;
1	Apples	2.70	2	495	1	23.50
2	Bananas	3.00	2	890	1	34.30
3	Lemons	0.40	1	17	1	31.00
4	Grapes	2.87	2	690	1	44.15
5	Pineapple	5.40	1	452	1	11.00
6	Tomatoes	2.00	2	180	1	45.40
7	Potatoes	1.80	2	770	1	67.43
8	Onions	1.50	2	400	1	23.12
9	Garlic	1.00	2	1490	1	11.43
10	Carrots	1.70	2	410	1	44.33
11	Cucumbers	1.20	2	160	1	37.20
12	Lettuce	2.00	2	150	1	29.84
13	Olives	1.50	2	1150	1	19.44
14	Peppers	1.80	2	400	1	41.94
15	Cheese	10.00	2	4020	1	32.76
16	Butter	2.50	1	130	1	14.00
17	Milk	2.00	3	420	7	43.22
18	Eggs	0.16	1	78	2	126.00
19	Fish	9.00	2	2060	4	21.32
20	Chicken	6.30	2	2390	1	37.39
21	Beef	13.37	2	2500	1	19.65
22	Pork	6.10	2	2420	1	28.54
23	Flour	2.50	2	3640	1	133.90
24	Salt	1.00	2	0	1	34.40
25	Sugar	3.00	2	3870	1	27.45
26	Herbs	19.30	2	220	1	39.31
27	Coffe	26.30	3	0	1	43.22
28	Peanuts	6.00	2	5670	3	40.12
29	Honey	9.30	2	3040	1	13.61
30	Rice	3.00	2	1300	1	56.31
31	Ketchup	2.20	2	1120	1	23.00
32	Mayonnaise	2.40	2	6809	1	33.00
33	Tea	19.32	3	12	1	32.20
36	Beer	2.70	3	430	1	140.43
37	Bread	0.92	1	2650	1	23.00
38	Biscuits	6.40	2	3530	1	32.20
40	Chocolate	9.40	2	5460	1	39.30
34	Coca_Cola	1.90	3	530	1	190.34
35	Spring_Water	0.98	3	0	1	220.84
39	Ice_Cream	22.31	2	2070	7	24.30
41	Oil	3.50	3	8840	1	40.30
\.


--
-- Name: ingredients_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('ingredients_stock_id_seq', 16, true);


--
-- Data for Name: job_position; Type: TABLE DATA; Schema: public; Owner: -
--

COPY job_position (id, name) FROM stdin;
1	waiter
2	cook
3	chef
4	manager
\.


--
-- Name: job_position_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('job_position_id_seq', 4, true);


--
-- Data for Name: mealdeal_dishes; Type: TABLE DATA; Schema: public; Owner: -
--

COPY mealdeal_dishes (mealdeal_id, dish_id) FROM stdin;
1	1
1	15
1	13
2	2
2	25
2	14
3	5
3	17
3	8
\.


--
-- Data for Name: metric_unit; Type: TABLE DATA; Schema: public; Owner: -
--

COPY metric_unit (id, name) FROM stdin;
1	quantity
2	kilogram(s)
3	liter(s)
\.


--
-- Name: metric_unit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('metric_unit_id_seq', 3, true);


--
-- Data for Name: order_dishes; Type: TABLE DATA; Schema: public; Owner: -
--

COPY order_dishes (dish_id, order_id) FROM stdin;
23	1
23	2
15	3
20	4
8	4
4	5
19	5
7	5
2	6
22	6
13	6
24	7
15	7
8	8
9	8
13	8
14	9
10	9
18	10
20	11
10	11
7	11
1	12
13	12
21	12
22	12
23	13
22	13
24	14
13	14
18	14
25	15
25	16
2	17
7	18
8	18
5	19
21	19
8	19
24	20
10	21
16	21
6	22
4	23
11	24
17	24
9	25
23	26
23	27
24	28
25	29
2	30
1	31
20	31
14	32
15	33
1	33
21	33
5	34
18	35
6	35
20	35
7	36
17	37
22	38
2	38
8	38
9	39
21	39
13	40
15	40
10	41
7	41
16	41
19	42
23	42
25	43
25	44
25	45
5	46
7	46
16	46
17	46
13	47
20	47
23	48
7	48
9	49
2	50
22	50
1	51
15	51
7	51
1	52
24	53
24	54
7	54
13	55
15	55
8	56
17	56
8	56
23	57
23	58
3	59
16	59
12	59
3	60
20	60
6	61
13	61
22	61
4	62
19	62
19	63
20	64
15	65
23	66
23	67
23	68
23	69
11	70
10	71
15	71
13	71
14	72
15	72
22	73
9	74
7	74
20	74
2	75
21	75
1	76
10	77
7	77
19	77
6	78
16	78
2	79
22	79
8	79
13	80
19	80
14	81
22	81
4	82
7	82
21	82
5	83
17	83
25	84
25	85
19	85
3	86
7	86
19	86
25	87
17	87
25	88
17	88
12	90
19	90
11	91
18	91
23	92
23	93
23	94
13	95
9	96
15	96
\.


--
-- Data for Name: order_state; Type: TABLE DATA; Schema: public; Owner: -
--

COPY order_state (id, name) FROM stdin;
1	placed
2	confirmed
3	prepaired
4	delivered
5	completed
6	canceled
\.


--
-- Name: order_state_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('order_state_id_seq', 6, true);


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: -
--

COPY orders (id, order_device_id, customer_id, cost, order_state_id, time_requested, time_confirmed, time_delivered, time_completed) FROM stdin;
35	9	15	16.50	5	2017-03-03 14:50:01	2017-03-03 15:01:01	2017-03-03 15:24:31	2017-03-03 15:55:20
1	1	1	5.50	5	2017-03-01 01:01:01	2017-03-01 01:08:21	2017-03-01 01:25:51	2017-03-01 01:43:23
36	9	16	32.50	5	2017-03-03 14:31:01	2017-03-03 14:33:47	2017-03-03 14:49:58	2017-03-03 14:59:54
2	1	2	5.50	5	2017-03-01 01:32:15	2017-03-01 01:39:32	2017-03-01 01:43:01	2017-03-05 02:10:50.073
37	10	17	9.20	5	2017-03-03 15:31:22	2017-03-03 15:33:56	2017-03-03 15:56:31	2017-03-03 16:22:49
38	10	18	5.40	5	2017-03-03 15:44:43	2017-03-15 15:46:51	2017-03-03 15:59:01	2017-03-03 16:23:45
39	10	19	13.20	5	2017-03-03 15:34:54	2017-03-03 15:41:51	2017-03-03 16:01:01	2017-03-03 16:33:12
40	10	20	11.10	5	2017-03-03 16:01:01	2017-03-03 16:04:43	2017-03-03 16:23:57	2017-03-03 16:55:37
3	1	3	9.20	5	2017-03-01 01:55:01	2017-03-01 02:01:31	2017-03-01 02:33:52	2017-03-01 02:47:25
41	11	1	63.21	5	2017-03-04 16:31:53	2017-03-04 16:32:53	2017-03-04 16:48:04	2017-03-04 16:59:01
4	1	4	6.50	5	2017-03-01 02:13:14	2017-03-01 02:15:26	2017-03-01 02:52:22	2017-03-01 03:11:51
5	2	5	2.19	5	2017-03-01 02:15:19	2017-03-01 02:17:31	2017-03-01 02:46:41	2017-03-01 02:59:41
6	2	6	90.50	5	2017-03-01 03:12:51	2017-03-01 03:15:21	2017-03-01 03:35:51	2017-03-01 03:52:57
7	2	7	90.50	5	2017-03-01 03:52:45	2017-03-01 03:55:42	2017-03-01 04:12:52	2017-03-01 04:36:53
8	2	8	5.70	5	2017-03-01 04:52:23	2017-03-01 04:57:05	2017-03-01 05:13:54	2017-03-01 05:33:54
9	3	9	6.29	5	2017-03-01 04:32:43	2017-03-01 04:36:51	2017-03-01 04:55:32	2017-03-01 05:22:31
10	3	10	3.30	5	2017-03-01 05:21:12	2017-03-01 05:29:41	2017-03-01 05:36:51	2017-03-01 05:53:38
11	3	11	4.42	5	2017-03-01 06:12:33	2017-03-01 06:18:43	2017-03-01 06:27:52	2017-03-01 06:55:59
12	3	12	12.20	5	2017-03-01 07:34:51	2017-03-01 07:36:51	2017-03-01 07:53:52	2017-03-01 08:01:01
13	4	13	13.50	5	2017-03-02 07:31:55	2017-03-02 07:41:52	2017-03-02 07:59:13	2017-03-02 08:12:51
14	4	14	20.30	5	2017-03-02 08:05:13	2017-03-02 08:09:31	2017-03-02 08:25:53	2017-03-02 08:44:52
15	4	15	40.32	5	2017-03-02 08:10:41	2017-03-02 08:16:23	2017-03-02 08:34:52	2017-03-02 08:58:48
16	4	16	18.30	5	2017-03-02 08:23:54	2017-03-02 08:36:54	2017-03-02 08:57:01	2017-03-02 09:21:13
17	5	17	27.40	5	2017-03-02 08:31:34	2017-03-02 08:36:31	2017-03-02 08:54:32	2017-03-02 09:01:01
18	5	18	50.10	5	2017-03-02 09:14:31	2017-03-02 09:17:51	2017-03-02 09:34:51	2017-03-02 10:03:31
19	5	19	36.90	5	2017-03-02 09:32:52	2017-03-02 09:35:11	2017-03-02 09:53:48	2017-03-02 10:22:37
20	5	20	22.60	5	2017-03-02 09:11:31	2017-03-02 09:18:41	2017-03-02 09:33:41	2017-03-02 09:55:51
21	6	1	30.40	5	2017-03-02 09:32:51	2017-03-02 09:37:43	2017-03-02 09:54:39	2017-03-02 10:11:53
22	6	2	7.50	5	2017-03-02 10:05:02	2017-03-02 10:10:04	2017-03-02 10:30:41	2017-03-02 10:53:41
23	6	3	90.20	5	2017-03-02 10:51:33	2017-03-02 10:54:31	2017-03-02 11:08:41	2017-03-02 11:31:01
24	6	4	33.33	5	2017-03-02 10:31:33	2017-03-02 10:39:05	2017-03-02 10:51:55	2017-03-02 11:11:11
25	7	5	14.40	5	2017-03-02 11:12:31	2017-03-02 11:15:31	2017-03-02 01:36:53	2017-03-02 11:58:20
26	7	6	50.50	5	2017-03-02 12:14:53	2017-03-02 12:17:41	2017-03-02 12:31:53	2017-03-02 12:54:31
27	7	7	2.20	5	2017-03-02 12:43:31	2017-03-02 12:55:33	2017-03-02 13:15:31	2017-03-02 13:44:05
28	7	8	3.40	5	2017-03-02 12:31:53	2017-03-02 12:36:43	2017-03-02 12:59:51	2017-03-02 13:42:35
29	8	9	5.60	5	2017-03-03 12:31:55	2017-03-03 12:35:51	2017-03-03 12:55:31	2017-03-03 13:33:02
30	8	10	7.90	5	2017-03-03 13:01:01	2017-03-03 13:05:31	2017-03-03 13:27:41	2017-03-03 13:56:43
31	8	11	6.60	5	2017-03-03 13:13:51	2017-03-03 13:22:33	2017-03-03 13:47:31	2017-03-03 13:59:45
32	8	12	7.80	5	2017-03-03 13:31:31	2017-03-03 13:36:43	2017-03-03 13:49:11	2017-03-03 14:11:15
33	9	13	9.70	5	2017-03-03 14:05:01	2017-03-03 14:07:36	2017-03-03 14:27:53	2017-03-03 14:38:11
34	9	14	12.50	5	2017-03-03 14:08:53	2017-03-03 14:10:51	2017-03-03 14:33:53	2017-03-03 14:55:06
42	11	2	65.65	5	2017-03-04 16:43:45	2017-03-04 16:47:44	2017-03-04 17:05:01	2017-03-04 17:30:08
43	11	3	16.41	5	2017-03-04 16:12:44	2017-03-04 16:16:53	2017-03-04 16:41:01	2017-03-04 16:57:31
44	11	4	13.64	5	2017-03-04 16:32:41	2017-03-04 16:33:56	2017-03-04 16:53:31	2017-03-04 17:05:01
45	12	5	18.23	5	2017-03-04 17:03:45	2017-03-04 17:05:41	2017-03-04 17:35:51	2017-03-04 17:57:49
46	12	6	21.52	5	2017-03-04 17:31:22	2017-03-04 17:37:12	2017-03-04 17:48:31	2017-03-04 18:03:01
47	12	7	25.64	5	2017-03-04 17:45:53	2017-03-04 17:50:34	2017-03-04 18:05:33	2017-03-04 18:36:51
48	12	8	27.43	5	2017-03-04 17:44:31	2017-03-04 17:50:01	2017-03-04 18:15:53	2017-03-04 18:44:01
49	13	9	60.66	5	2017-03-04 18:01:01	2017-03-04 18:04:43	2017-03-04 18:24:58	2017-03-04 18:55:04
50	13	10	11.41	5	2017-03-04 18:32:32	2017-03-04 18:35:41	2017-03-04 18:48:41	2017-03-04 19:03:01
51	13	11	20.20	1	2017-03-05 18:31:33	2017-03-05 18:36:04	2017-03-05 18:51:41	2017-03-05 19:21:08
88	22	8	33.41	3	2017-03-05 22:41:38	2017-03-05 22:47:57	2017-03-05 23:18:41	2017-03-05 23:43:39
53	14	13	15.41	3	2017-03-05 18:14:31	2017-03-05 18:18:05	2017-03-05 18:31:41	2017-03-05 18:57:31
54	14	14	7.54	4	2017-03-05 18:41:31	2017-03-05 18:44:04	2017-03-05 19:10:12	2017-03-05 19:37:04
55	14	15	8.53	5	2017-03-05 18:57:31	2017-03-05 19:01:01	2017-03-05 19:22:03	2017-03-05 19:55:31
56	14	16	9.60	1	2017-03-05 19:00:01	2017-03-05 19:03:01	2017-03-05 19:29:58	2017-03-05 19:54:00
57	15	17	12.10	2	2017-03-05 19:03:04	2017-03-05 19:05:55	2017-03-05 19:35:54	2017-03-05 19:57:48
58	15	18	10.10	3	2017-03-05 19:11:13	2017-03-05 19:14:44	2017-03-05 19:41:32	2017-03-05 19:59:55
59	15	19	10.90	4	2017-03-05 19:22:32	2017-03-05 19:26:31	2017-03-05 19:44:57	2017-03-05 20:03:04
52	13	12	31.11	2	2017-03-05 18:03:06	2017-03-05 18:07:53	2017-03-05 18:24:33	2017-03-05 18:48:07
60	15	20	12.33	5	2017-03-05 19:34:41	2017-03-05 19:39:41	2017-03-05 19:58:06	2017-03-05 20:05:52
61	16	1	14.55	1	2017-03-05 19:46:01	2017-03-05 19:51:01	2017-03-05 20:14:38	2017-03-05 20:39:43
62	16	2	17.21	2	2017-03-05 20:01:51	2017-03-05 20:03:43	2017-03-05 20:31:04	2017-03-05 20:49:42
63	16	3	20.33	3	2017-03-05 20:04:31	2017-03-05 20:07:41	2017-03-05 20:31:07	2017-03-05 20:56:09
64	16	4	15.44	4	2017-03-05 20:12:31	2017-03-05 20:14:49	2017-03-05 20:39:41	2017-03-05 20:58:04
65	17	5	18.30	5	2017-03-05 20:31:22	2017-03-05 20:35:41	2017-03-05 20:56:31	2017-03-05 21:33:05
66	17	6	19.20	1	2017-03-05 20:14:31	2017-03-05 20:21:43	2017-03-05 20:41:48	2017-03-05 20:58:59
67	17	7	21.22	2	2017-03-05 20:18:53	2017-03-05 20:22:41	2017-03-05 20:48:22	2017-03-05 21:11:11
68	17	8	31.40	3	2017-03-05 20:33:31	2017-03-05 20:37:43	2017-03-05 20:58:33	2017-03-05 21:22:43
69	18	9	16.50	4	2017-03-05 20:39:41	2017-03-05 20:42:35	2017-03-05 20:59:01	2017-03-05 21:21:33
70	18	10	19.20	5	2017-03-05 20:44:32	2017-03-05 20:48:03	2017-03-05 21:03:05	2017-03-05 21:31:57
71	18	11	8.20	1	2017-03-05 20:53:01	2017-03-05 20:56:55	2017-03-05 21:24:53	2017-03-05 21:48:41
72	18	12	3.40	2	2017-03-05 21:02:01	2017-03-05 21:06:01	2017-03-05 21:38:00	2017-03-05 22:03:01
73	19	13	2.90	3	2017-03-05 21:03:04	2017-03-05 21:07:37	2017-03-05 21:33:41	2017-03-05 21:57:58
74	19	14	5.50	4	2017-03-05 21:06:03	2017-03-05 21:09:59	2017-03-05 21:32:41	2017-03-05 21:58:19
75	19	15	7.20	5	2017-03-05 21:11:03	2017-03-05 21:16:41	2017-03-05 21:34:53	2017-03-05 21:54:07
76	19	16	2.50	1	2017-03-05 21:21:21	2017-03-05 21:35:08	2017-03-05 21:53:53	2017-03-05 22:11:51
77	20	17	4.49	2	2017-03-05 21:24:03	2017-03-05 21:28:45	2017-03-05 21:58:45	2017-03-05 22:24:06
78	20	18	6.69	3	2017-03-05 21:33:54	2017-03-05 21:40:01	2017-03-05 21:59:21	2017-03-05 22:22:22
79	20	19	10.90	4	2017-03-05 21:45:27	2017-03-05 21:49:44	2017-03-05 22:14:57	2017-03-05 22:37:53
80	20	20	12.10	5	2017-03-05 21:36:41	2017-03-05 21:40:05	2017-03-05 21:59:56	2017-03-05 22:28:43
81	21	1	15.40	1	2017-03-05 21:44:32	2017-03-05 21:47:48	2017-03-05 21:59:37	2017-03-05 22:22:22
82	21	2	18.30	2	2017-03-05 22:01:01	2017-03-05 22:05:07	2017-03-05 22:36:51	2017-03-05 22:59:41
83	21	3	16.20	3	2017-03-05 22:03:41	2017-03-05 22:11:22	2017-03-05 22:33:58	2017-03-05 22:57:28
84	21	4	25.80	4	2017-03-05 22:07:36	2017-03-05 22:11:41	2017-03-05 22:37:41	2017-03-05 23:01:41
85	22	5	67.53	5	2017-03-05 22:14:44	2017-03-05 22:17:41	2017-03-05 22:39:56	2017-03-05 23:05:58
86	22	6	8.40	1	2017-03-05 22:17:41	2017-03-05 22:23:01	2017-03-05 22:55:38	2017-03-05 23:31:23
87	22	7	19.20	2	2017-03-05 22:24:54	2017-03-05 22:30:52	2017-03-05 22:55:38	2017-03-05 23:27:26
89	23	9	64.10	4	2017-03-05 22:55:55	2017-03-05 22:57:58	2017-03-05 23:31:37	2017-03-05 23:58:17
90	23	10	95.42	5	2017-03-05 23:01:01	2017-03-05 23:07:01	2017-03-05 23:39:24	2017-03-05 23:59:59
91	23	11	10.22	1	2017-03-05 23:14:32	2017-03-05 23:19:21	2017-03-05 23:38:51	2017-03-05 00:04:01
92	23	12	7.43	2	2017-03-05 23:22:22	2017-03-05 23:28:53	2017-03-05 23:55:55	2017-03-05 00:17:04
93	24	13	9.43	3	2017-03-05 00:01:03	2017-03-05 00:05:54	2017-03-05 00:32:04	2017-03-05 00:51:30
94	24	14	8.11	4	2017-03-05 00:04:04	2017-03-05 00:08:44	2017-03-05 00:33:44	2017-03-05 00:59:59
95	24	15	18.41	5	2017-03-05 00:25:55	2017-03-05 00:31:43	2017-03-05 00:55:04	2017-03-05 01:05:08
96	24	16	16.39	1	2017-03-05 00:41:43	2017-03-05 00:46:03	2017-03-05 00:59:54	2017-03-05 01:21:20
\.


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('orders_id_seq', 99, true);


--
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: -
--

COPY reservation (id, table_id, customer_id, reservation_date_time) FROM stdin;
1	1	1	2017-09-05 12:00:00
2	2	2	2017-07-08 13:00:00
3	3	3	2017-10-05 14:30:00
4	4	4	2017-08-04 15:25:00
5	5	5	2017-10-09 17:00:00
6	6	6	2017-06-26 12:20:00
7	7	7	2017-08-23 20:00:00
8	8	8	2017-11-30 19:30:00
9	1	9	2017-05-24 14:35:01
10	2	10	2017-08-23 18:40:00
\.


--
-- Name: reservation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('reservation_id_seq', 4, true);


--
-- Data for Name: restourant_table; Type: TABLE DATA; Schema: public; Owner: -
--

COPY restourant_table (id, table_status_id, waiter_id) FROM stdin;
1	2	1
2	2	2
3	2	3
4	2	4
5	2	5
6	2	6
7	1	7
8	3	8
\.


--
-- Name: restourant_table_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('restourant_table_id_seq', 4, true);


--
-- Data for Name: staff; Type: TABLE DATA; Schema: public; Owner: -
--

COPY staff (id, username, email, pass, full_name, telephone_number, address, job_position_id, permission_level) FROM stdin;
1	jackiechan	jackiechan@gmail.com	asd123	Jackie_Chan	07482543421	TW20_0EX	1	1
2	selenagomez	selenagomez@gmail.com	asd123	Selena_Gomez	07482543443	TW20_0EX	1	1
3	johnnydepp	johnnydepp@gmail.com	asd123	Johnny_Depp	07482543431	TW20_0EX	1	1
4	brucewillis	brucewillis@gmail.com	asd123	Burce_Willis	07482545421	TW20_0EX	1	1
5	harrypotter	harrypotter@gmail.com	asd123	Harry_Potter	07482543453	TW20_0EX	1	1
6	kendricklamar	kendricklamar@gmail.com	asd123	Kendrick_Lamar	07482543476	TW20_0EX	1	1
7	justinbieber	justinbieber@gmail.com	asd123	Justin_Bieber	07482543765	TW20_0EX	1	1
8	arianagrande	arianagrande@gmail.com	asd123	Ariana_grande	07482543745	TW20_0EX	1	1
9	grigoryordanov	grigoryordanov@gmail.com	asd123	Grigor_Yordanov	07482548546	TW20_0EX	2	2
10	svetoslavmihovski	svetoslavmihovski@gmail.com	asd123	Svetoslav_Mihovski	07482546346	TW20_0EX	2	2
11	darrenmatthews	darrenmatthews@gmail.com	asd123	Darren_Matthews	07482543573	TW20_0EX	2	2
12	jamieharris	jamieharris@gmail.com	asd123	Jamie Harris	07482546743	TW20_0EX	2	2
13	markabdel	markabdel@gmail.com	asd123	Mark_Abdel	07482534648	TW20_0EX	2	2
14	christos	christos@gmail.com	asd123	Christos	07482568968	TW20_0EX	2	2
15	connordalgarno	connordalgarno@gmail.com	asd123	Connor_Dalgarno	07482536866	TW20_0EX	3	3
16	johanneskinder	johanneskinder@gmail.com	asd123	Johannes_Kinder	07482374774	TW20_0EX	4	4
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
4	unavailable
2	occupied
3	reserved
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

