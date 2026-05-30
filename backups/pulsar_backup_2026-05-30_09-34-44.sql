--
-- PostgreSQL database dump
--

\restrict mFIdLR1g9Z4uwMci6JqK63AjoXPE4SgXt7DrDLK6F4HFXOJpgVbRzAriU84SXzh

-- Dumped from database version 17.5
-- Dumped by pg_dump version 18.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS '';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: audit_logs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.audit_logs (
    id bigint NOT NULL,
    employee_username character varying(255),
    action character varying(255),
    entity_type character varying(255),
    entity_id bigint,
    created_at timestamp without time zone,
    details text
);


ALTER TABLE public.audit_logs OWNER TO postgres;

--
-- Name: audit_logs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.audit_logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.audit_logs_id_seq OWNER TO postgres;

--
-- Name: audit_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.audit_logs_id_seq OWNED BY public.audit_logs.id;


--
-- Name: clients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clients (
    id bigint NOT NULL,
    full_name character varying(255),
    gender character varying(50),
    birth_date date,
    monthly_income numeric(19,2),
    has_criminal_record boolean,
    credit_history_score integer,
    current_credit_debt numeric(19,2),
    is_deleted boolean
);


ALTER TABLE public.clients OWNER TO postgres;

--
-- Name: clients_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clients_id_seq OWNER TO postgres;

--
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clients_id_seq OWNED BY public.clients.id;


--
-- Name: credit_applications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credit_applications (
    id bigint NOT NULL,
    client_id bigint NOT NULL,
    requested_amount numeric(19,2),
    requested_months integer,
    status character varying(50),
    created_at timestamp without time zone
);


ALTER TABLE public.credit_applications OWNER TO postgres;

--
-- Name: credit_applications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.credit_applications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.credit_applications_id_seq OWNER TO postgres;

--
-- Name: credit_applications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.credit_applications_id_seq OWNED BY public.credit_applications.id;


--
-- Name: credit_score_results; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credit_score_results (
    id bigint NOT NULL,
    application_id bigint NOT NULL,
    score integer,
    reliability_level character varying(50),
    approved_amount numeric(19,2),
    decision_reason text,
    calculated_at timestamp without time zone
);


ALTER TABLE public.credit_score_results OWNER TO postgres;

--
-- Name: credit_score_results_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.credit_score_results_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.credit_score_results_id_seq OWNER TO postgres;

--
-- Name: credit_score_results_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.credit_score_results_id_seq OWNED BY public.credit_score_results.id;


--
-- Name: employees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employees (
    id bigint NOT NULL,
    full_name character varying(255),
    username character varying(255) NOT NULL,
    password character varying(255),
    role character varying(50),
    enabled boolean
);


ALTER TABLE public.employees OWNER TO postgres;

--
-- Name: employees_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.employees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.employees_id_seq OWNER TO postgres;

--
-- Name: employees_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.employees_id_seq OWNED BY public.employees.id;


--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- Name: audit_logs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audit_logs ALTER COLUMN id SET DEFAULT nextval('public.audit_logs_id_seq'::regclass);


--
-- Name: clients id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients ALTER COLUMN id SET DEFAULT nextval('public.clients_id_seq'::regclass);


--
-- Name: credit_applications id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_applications ALTER COLUMN id SET DEFAULT nextval('public.credit_applications_id_seq'::regclass);


--
-- Name: credit_score_results id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_score_results ALTER COLUMN id SET DEFAULT nextval('public.credit_score_results_id_seq'::regclass);


--
-- Name: employees id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees ALTER COLUMN id SET DEFAULT nextval('public.employees_id_seq'::regclass);


--
-- Data for Name: audit_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.audit_logs (id, employee_username, action, entity_type, entity_id, created_at, details) FROM stdin;
1	admin	LOGIN	Employee	\N	2026-05-30 08:03:40.769701	Сотрудник вошёл в систему
2	admin	CREATE_CLIENT	Client	1	2026-05-30 08:03:50.784903	Создан клиент: Игорь Воiтенка
3	admin	LOGIN	Employee	\N	2026-05-30 08:04:57.655422	Сотрудник вошёл в систему
4	admin	CREATE_CLIENT	Client	2	2026-05-30 08:05:19.042974	Создан клиент: Артём Новиков
5	admin	LOGIN	Employee	\N	2026-05-30 08:36:17.737783	Сотрудник вошёл в систему
6	admin	LOGIN	Employee	\N	2026-05-30 08:38:04.875469	Сотрудник вошёл в систему
7	admin	LOGIN	Employee	\N	2026-05-30 08:56:31.090338	Сотрудник вошёл в систему
8	admin	CREATE_CLIENT	Client	3	2026-05-30 08:57:18.118798	Создан клиент: Ваня Петров
9	admin	CREATE_CREDIT_APPLICATION	CreditApplication	1	2026-05-30 08:57:47.194546	Создана кредитная заявка для клиента: Артём Новиков
10	admin	CALCULATE_CREDIT_SCORE	CreditApplication	1	2026-05-30 08:57:52.607512	Выполнен расчёт кредитной надёжности. Балл: 63, уровень: MEDIUM
11	admin	CREATE_CLIENT	Client	4	2026-05-30 08:58:32.508245	Создан клиент: Ванек Попов
12	admin	CREATE_CREDIT_APPLICATION	CreditApplication	2	2026-05-30 08:58:48.017774	Создана кредитная заявка для клиента: Ванек Попов
13	admin	CALCULATE_CREDIT_SCORE	CreditApplication	2	2026-05-30 08:58:49.255115	Выполнен расчёт кредитной надёжности. Балл: 30, уровень: LOW
14	admin	CREATE_CREDIT_APPLICATION	CreditApplication	3	2026-05-30 08:59:30.200905	Создана кредитная заявка для клиента: Игорь Воiтенка
15	admin	CALCULATE_CREDIT_SCORE	CreditApplication	3	2026-05-30 08:59:31.288874	Выполнен расчёт кредитной надёжности. Балл: 68, уровень: MEDIUM
16	admin	CREATE_CLIENT	Client	5	2026-05-30 08:59:57.717672	Создан клиент: Егор Попов
17	admin	CREATE_CREDIT_APPLICATION	CreditApplication	4	2026-05-30 09:00:07.006678	Создана кредитная заявка для клиента: Егор Попов
18	admin	CALCULATE_CREDIT_SCORE	CreditApplication	4	2026-05-30 09:00:08.200754	Выполнен расчёт кредитной надёжности. Балл: 89, уровень: HIGH
19	admin	LOGIN	Employee	\N	2026-05-30 09:19:08.712565	Сотрудник вошёл в систему
20	admin	UPDATE_CLIENT	Client	2	2026-05-30 09:19:29.836625	Изменены данные клиента: Артём Новиков
21	admin	LOGIN	Employee	\N	2026-05-30 09:22:25.853346	Сотрудник вошёл в систему
22	admin	LOGIN	Employee	\N	2026-05-30 09:24:59.053635	Сотрудник вошёл в систему
23	admin	LOGIN	Employee	\N	2026-05-30 09:33:26.748693	Сотрудник вошёл в систему
24	admin	LOGIN	Employee	\N	2026-05-30 09:34:38.50659	Сотрудник вошёл в систему
\.


--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clients (id, full_name, gender, birth_date, monthly_income, has_criminal_record, credit_history_score, current_credit_debt, is_deleted) FROM stdin;
1	Игорь Воiтенка	MALE	\N	120000.00	f	80	35000.00	f
3	Ваня Петров	MALE	2005-05-08	93000.00	f	93	0.00	f
4	Ванек Попов	MALE	2005-05-08	35000.00	f	12	900000.00	f
5	Егор Попов	MALE	2005-05-08	300000.00	f	99	0.00	f
2	Артём Новиков	MALE	\N	70000.00	t	83	12000.00	f
\.


--
-- Data for Name: credit_applications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credit_applications (id, client_id, requested_amount, requested_months, status, created_at) FROM stdin;
1	2	30000.00	12	MANUAL_REVIEW	2026-05-30 08:57:47.189545
2	4	120000.00	12	REJECTED	2026-05-30 08:58:48.015771
3	1	40000.00	12	MANUAL_REVIEW	2026-05-30 08:59:30.198905
4	5	120000.00	12	APPROVED	2026-05-30 09:00:07.004728
\.


--
-- Data for Name: credit_score_results; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credit_score_results (id, application_id, score, reliability_level, approved_amount, decision_reason, calculated_at) FROM stdin;
1	1	63	MEDIUM	30000.00	Доход клиента добавил 7 баллов. Кредитная история добавила 16 баллов. Наличие текущей задолженности снизило оценку на 10 баллов. Итоговый балл: 63. Уровень надежности: MEDIUM.	2026-05-30 08:57:52.595967
2	2	30	LOW	0.00	Доход клиента добавил 3 баллов. Кредитная история добавила 2 баллов. Высокая долговая нагрузка снизила оценку на 25 баллов. Итоговый балл: 30. Уровень надежности: LOW.	2026-05-30 08:58:49.253113
3	3	68	MEDIUM	40000.00	Доход клиента добавил 12 баллов. Кредитная история добавила 16 баллов. Наличие текущей задолженности снизило оценку на 10 баллов. Итоговый балл: 68. Уровень надежности: MEDIUM.	2026-05-30 08:59:31.285874
4	4	89	HIGH	120000.00	Доход клиента добавил 30 баллов. Кредитная история добавила 19 баллов. Наличие текущей задолженности снизило оценку на 10 баллов. Итоговый балл: 89. Уровень надежности: HIGH.	2026-05-30 09:00:08.197756
\.


--
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employees (id, full_name, username, password, role, enabled) FROM stdin;
1	System Administrator	admin	$2a$10$yTMowEAqyaBMhxSuSwvf2.tac00qvEu3ORNcZ4ta0hfBBsD6lpbPW	ADMIN	t
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	init database	SQL	V1__init_database.sql	-756060742	postgres	2026-05-30 08:03:15.084685	47	t
\.


--
-- Name: audit_logs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.audit_logs_id_seq', 24, true);


--
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clients_id_seq', 5, true);


--
-- Name: credit_applications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.credit_applications_id_seq', 4, true);


--
-- Name: credit_score_results_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.credit_score_results_id_seq', 4, true);


--
-- Name: employees_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.employees_id_seq', 1, true);


--
-- Name: audit_logs audit_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audit_logs
    ADD CONSTRAINT audit_logs_pkey PRIMARY KEY (id);


--
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- Name: credit_applications credit_applications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_applications
    ADD CONSTRAINT credit_applications_pkey PRIMARY KEY (id);


--
-- Name: credit_score_results credit_score_results_application_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_score_results
    ADD CONSTRAINT credit_score_results_application_id_key UNIQUE (application_id);


--
-- Name: credit_score_results credit_score_results_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_score_results
    ADD CONSTRAINT credit_score_results_pkey PRIMARY KEY (id);


--
-- Name: employees employees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);


--
-- Name: employees employees_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_username_key UNIQUE (username);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: credit_applications fk_credit_application_client; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_applications
    ADD CONSTRAINT fk_credit_application_client FOREIGN KEY (client_id) REFERENCES public.clients(id);


--
-- Name: credit_score_results fk_score_result_application; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_score_results
    ADD CONSTRAINT fk_score_result_application FOREIGN KEY (application_id) REFERENCES public.credit_applications(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


--
-- PostgreSQL database dump complete
--

\unrestrict mFIdLR1g9Z4uwMci6JqK63AjoXPE4SgXt7DrDLK6F4HFXOJpgVbRzAriU84SXzh

