--
-- PostgreSQL database dump
--

\restrict dQdqWK2sLApyQSonAOahtwygrKP6L7YeSZ9rA6qbFSHFlL8eaE6dhYnlpSJIxLd

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
    created_at timestamp(6) without time zone,
    entity_id bigint,
    id bigint NOT NULL,
    action character varying(255),
    details character varying(255),
    employee_username character varying(255),
    entity_type character varying(255)
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
    birth_date date,
    credit_history_score integer,
    current_credit_debt numeric(38,2),
    has_criminal_record boolean,
    is_deleted boolean,
    monthly_income numeric(38,2),
    id bigint NOT NULL,
    full_name character varying(255),
    gender character varying(255),
    CONSTRAINT clients_gender_check CHECK (((gender)::text = ANY ((ARRAY['MALE'::character varying, 'FEMALE'::character varying])::text[])))
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
    requested_amount numeric(38,2),
    requested_months integer,
    client_id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    id bigint NOT NULL,
    status character varying(255),
    CONSTRAINT credit_applications_status_check CHECK (((status)::text = ANY ((ARRAY['CREATED'::character varying, 'APPROVED'::character varying, 'REJECTED'::character varying, 'MANUAL_REVIEW'::character varying])::text[])))
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
    approved_amount numeric(38,2),
    score integer,
    application_id bigint NOT NULL,
    calculated_at timestamp(6) without time zone,
    id bigint NOT NULL,
    decision_reason character varying(255),
    reliability_level character varying(255),
    CONSTRAINT credit_score_results_reliability_level_check CHECK (((reliability_level)::text = ANY ((ARRAY['HIGH'::character varying, 'MEDIUM'::character varying, 'LOW'::character varying])::text[])))
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
    enabled boolean,
    id bigint NOT NULL,
    full_name character varying(255),
    password character varying(255),
    role character varying(255),
    username character varying(255) NOT NULL,
    CONSTRAINT employees_role_check CHECK (((role)::text = ANY ((ARRAY['ADMIN'::character varying, 'MANAGER'::character varying, 'ANALYST'::character varying, 'AUDITOR'::character varying])::text[])))
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

COPY public.audit_logs (created_at, entity_id, id, action, details, employee_username, entity_type) FROM stdin;
\.


--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clients (birth_date, credit_history_score, current_credit_debt, has_criminal_record, is_deleted, monthly_income, id, full_name, gender) FROM stdin;
\.


--
-- Data for Name: credit_applications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credit_applications (requested_amount, requested_months, client_id, created_at, id, status) FROM stdin;
\.


--
-- Data for Name: credit_score_results; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credit_score_results (approved_amount, score, application_id, calculated_at, id, decision_reason, reliability_level) FROM stdin;
\.


--
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employees (enabled, id, full_name, password, role, username) FROM stdin;
t	1	System Administrator	$2a$10$Z6WTf5FzCq8BzdLARx8cfOSFEmS/ttSFggHfyD/rvZ5eduberY5Ry	ADMIN	admin
\.


--
-- Name: audit_logs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.audit_logs_id_seq', 1, false);


--
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clients_id_seq', 1, false);


--
-- Name: credit_applications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.credit_applications_id_seq', 1, false);


--
-- Name: credit_score_results_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.credit_score_results_id_seq', 1, false);


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
-- Name: credit_score_results fka1q41m9o1q7frhein7pw5tunw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_score_results
    ADD CONSTRAINT fka1q41m9o1q7frhein7pw5tunw FOREIGN KEY (application_id) REFERENCES public.credit_applications(id);


--
-- Name: credit_applications fkp1xwy96pipxx9fb3cg3mg55pq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_applications
    ADD CONSTRAINT fkp1xwy96pipxx9fb3cg3mg55pq FOREIGN KEY (client_id) REFERENCES public.clients(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


--
-- PostgreSQL database dump complete
--

\unrestrict dQdqWK2sLApyQSonAOahtwygrKP6L7YeSZ9rA6qbFSHFlL8eaE6dhYnlpSJIxLd

