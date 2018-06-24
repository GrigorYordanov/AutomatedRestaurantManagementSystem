--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.1
-- Dumped by pg_dump version 9.6.1

-- Started on 2017-01-24 22:14:28

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 2284 (class 0 OID 25002)
-- Dependencies: 202
-- Data for Name: client_devices; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY client_devices (id, table_id, call_waiter) FROM stdin;
0	0	t
1	1	t
2	2	t
3	3	t
\.


--
-- TOC entry 2278 (class 0 OID 24944)
-- Dependencies: 196
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY customer (id, username, email, pass, full_name, telephone_number) FROM stdin;
0	user1	email1@email.com	test123	Full Name	0748255555
1	user2	email2@email.com	test123	Full Name	0748255556
2	user3	email3@email.com	test123	Full Name	0748255557
3	user4	email4@email.com	test123	Full Name	0748255558
4	user5	email5@email.com	test123	Full Name	0748255559
\.


--
-- TOC entry 2286 (class 0 OID 26019)
-- Dependencies: 204
-- Data for Name: db_tables; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY db_tables (id, table_name) FROM stdin;
1	metric_unit
2	ingredients_allergies
3	food_type
4	dietary_requirements
5	dishes
6	ingredients_stock
7	dishes_ingredients
8	mealdeal_dishes
9	order_state
10	table_status
11	job_position
12	staff
13	table_
14	client_devices
15	customer
16	orders
17	order_dishes
18	reservation
\.


--
-- TOC entry 2271 (class 0 OID 24802)
-- Dependencies: 189
-- Data for Name: dietary_requirements; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY dietary_requirements (id, name) FROM stdin;
0	no requierment
1	spicy
2	vegan
3	vegiterian
\.


--
-- TOC entry 2268 (class 0 OID 24653)
-- Dependencies: 186
-- Data for Name: dishes; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY dishes (id, name, cost, picture_url, food_type_id, dietary_req_id, mealdeal_id, sort_order, in_stock) FROM stdin;
0	lasagnia	8.50	TEST	0	0	\N	1	t
1	pizza	6.50	TEST	0	0	\N	2	t
2	water	2.50	TEST	0	0	\N	3	t
3	cola	1.50	TEST	0	0	4	4	t
4	beer	3.20	TEST	0	0	5	5	t
5	coffe	1.20	TEST	3	0	6	6	t
6	donut	1.00	TEST	3	0	7	7	t
7	mealdeal1	5.50	TEST	0	0	8	8	t
8	mealdeal2	7.50	TEST	0	0	9	9	t
\.


--
-- TOC entry 2269 (class 0 OID 24658)
-- Dependencies: 187
-- Data for Name: dishes_ingredients; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY dishes_ingredients (dish_id, ingredient_id, ingredient_qty) FROM stdin;
0	0	1
0	1	15
0	2	15
0	5	4
0	7	4
0	9	6
0	13	5
0	12	15
0	15	3
1	0	1
1	1	15
1	5	4
1	10	6
1	13	5
1	12	15
1	15	3
\.


--
-- TOC entry 2285 (class 0 OID 25999)
-- Dependencies: 203
-- Data for Name: extra_sales; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY extra_sales (waiter_id, extra_sales_made, order_id) FROM stdin;
\.


--
-- TOC entry 2270 (class 0 OID 24681)
-- Dependencies: 188
-- Data for Name: food_type; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY food_type (id, name) FROM stdin;
0	Other
1	Indian
2	Chinese
3	Dessert
4	Drink
5	Meal
\.


--
-- TOC entry 2272 (class 0 OID 24807)
-- Dependencies: 190
-- Data for Name: ingredients_allergies; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY ingredients_allergies (id, name) FROM stdin;
0	no_allergy
1	eggs
2	peanuts
3	lactouse
4	gluten
\.


--
-- TOC entry 2273 (class 0 OID 24823)
-- Dependencies: 191
-- Data for Name: ingredients_stock; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY ingredients_stock (id, name, cost, metric, calories, allergy_id, quantity) FROM stdin;
0	pepper	22	1	60	1	50.00
1	carrot	35	1	70	2	70.00
2	tommatos	40	1	50	3	30.00
3	cuccumbers	24	1	30	4	10.00
4	onion	20	1	30	1	11.00
5	cheese	70	1	120	2	14.00
6	milk	30	2	20	3	3.00
7	eggs	43	0	20	4	1.00
8	mixed meat	25	1	50	1	12.00
9	salami	12	1	70	2	5.00
10	chicken breast	60	1	40	3	7.00
11	beef	53	1	10	4	8.00
12	olive oil	25	3	90	1	1.00
13	salt	10	1	10	2	12.00
14	mixed herbs	7	1	20	3	6.00
15	butter	32	1	60	4	55.00
\.


--
-- TOC entry 2280 (class 0 OID 24968)
-- Dependencies: 198
-- Data for Name: job_position; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY job_position (id, name) FROM stdin;
0	manager
1	chef
2	cook
3	waiter
\.


--
-- TOC entry 2274 (class 0 OID 24885)
-- Dependencies: 192
-- Data for Name: mealdeal_dishes; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY mealdeal_dishes (mealdeal_id, dish_id) FROM stdin;
7	0
7	3
7	6
8	1
8	5
8	6
\.


--
-- TOC entry 2267 (class 0 OID 24641)
-- Dependencies: 185
-- Data for Name: metric_unit; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY metric_unit (id, name) FROM stdin;
0	quantity
1	kilogram
2	liter
3	meter
\.


--
-- TOC entry 2276 (class 0 OID 24908)
-- Dependencies: 194
-- Data for Name: order_dishes; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY order_dishes (order_id, dish_id) FROM stdin;
0	1
1	1
2	1
3	1
4	1
\.


--
-- TOC entry 2277 (class 0 OID 24925)
-- Dependencies: 195
-- Data for Name: order_state; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY order_state (id, name) FROM stdin;
0	placed
1	confirmed
2	delivered
3	completed
\.


--
-- TOC entry 2275 (class 0 OID 24903)
-- Dependencies: 193
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY orders (id, cost, time_requested, time_confirmed, time_completed, customer_id, order_state_id, order_device_id) FROM stdin;
0	5.50	10:35:22	10:45:22	11:00:22	0	0	0
1	7.80	12:22:54	12:32:54	12:52:54	1	1	1
2	9.20	09:02:15	09:12:15	09:45:15	2	2	2
3	6.50	07:44:59	07:54:59	08:13:59	3	2	3
4	2.19	07:12:30	07:22:30	07:50:30	3	3	3
\.


--
-- TOC entry 2281 (class 0 OID 24980)
-- Dependencies: 199
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY reservation (id, table_id, customer_id, reservation_date_time) FROM stdin;
0	0	0	2017-01-01 12:25:33
1	1	1	2017-02-02 13:25:33
2	2	2	2017-03-03 14:25:33
3	3	3	2017-04-04 15:25:33
\.


--
-- TOC entry 2279 (class 0 OID 24956)
-- Dependencies: 197
-- Data for Name: staff; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY staff (id, username, email, pass, full_name, telephone_number, address, job_position_id, permission_level) FROM stdin;
0	staffmember1	staffmember1@email.com	test123	Staff Member	0748266666	TEST	0	1
1	staffmember2	staffmember2@email.com	test123	Staff Member	0748266667	TEST	1	2
2	staffmember3	staffmember3@email.com	test123	Staff Member	0748266668	TEST	2	3
3	staffmember4	staffmember4@email.com	test123	Staff Member	0748266669	TEST	3	4
4	staffmember5	staffmember4@email.com	test123	Staff Member	0748266670	TEST	3	4
\.


--
-- TOC entry 2282 (class 0 OID 24985)
-- Dependencies: 200
-- Data for Name: table_; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY table_ (id, table_status_id, waiter_id) FROM stdin;
0	0	0
1	2	0
2	1	0
3	0	0
\.


--
-- TOC entry 2283 (class 0 OID 24990)
-- Dependencies: 201
-- Data for Name: table_status; Type: TABLE DATA; Schema: public; Owner: Test
--

COPY table_status (id, name) FROM stdin;
0	free
1	reserved
2	unavailable
\.


-- Completed on 2017-01-24 22:14:29

--
-- PostgreSQL database dump complete
--

