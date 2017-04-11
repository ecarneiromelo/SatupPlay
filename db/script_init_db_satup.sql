--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.4
-- Dumped by pg_dump version 9.4.4
-- Started on 2017-04-11 18:27:55 -03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE satup;
--
-- TOC entry 2338 (class 1262 OID 17325)
-- Name: satup; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE satup WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'pt_BR.UTF-8' LC_CTYPE = 'pt_BR.UTF-8';


ALTER DATABASE satup OWNER TO postgres;

\connect satup

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 6 (class 2615 OID 17704)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 186 (class 3079 OID 12123)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2339 (class 0 OID 0)
-- Dependencies: 186
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 185 (class 1259 OID 17786)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 177 (class 1259 OID 17715)
-- Name: rl_linha_parada; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rl_linha_parada (
    id_linha bigint NOT NULL,
    id_parada bigint NOT NULL
);


ALTER TABLE rl_linha_parada OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 17707)
-- Name: sq_satup_tb_linha; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_satup_tb_linha
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sq_satup_tb_linha OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 17711)
-- Name: sq_satup_tb_localizacao; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_satup_tb_localizacao
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sq_satup_tb_localizacao OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 17705)
-- Name: sq_satup_tb_onibus; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_satup_tb_onibus
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sq_satup_tb_onibus OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 17709)
-- Name: sq_satup_tb_parada; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_satup_tb_parada
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sq_satup_tb_parada OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 17713)
-- Name: sq_tb_user; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_tb_user
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sq_tb_user OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 17740)
-- Name: tb_dmn_role; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_dmn_role (
    id bigint NOT NULL,
    tag_name character varying(100),
    tag_description character varying(300)
);


ALTER TABLE tb_dmn_role OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 17725)
-- Name: tb_linha; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_linha (
    id bigint NOT NULL,
    ds_nome character varying(200) NOT NULL,
    ds_linha character varying(10) NOT NULL,
    ds_sentido boolean NOT NULL,
    ds_valor double precision NOT NULL
);


ALTER TABLE tb_linha OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 17735)
-- Name: tb_localizacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_localizacao (
    id bigint NOT NULL,
    ds_localizazao character varying(23) NOT NULL,
    ds_datehora timestamp without time zone NOT NULL,
    id_onibus bigint
);


ALTER TABLE tb_localizacao OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 17720)
-- Name: tb_onibus; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_onibus (
    id bigint NOT NULL,
    ds_numero character varying(16) NOT NULL,
    ds_placa character varying(8) NOT NULL,
    id_linha bigint
);


ALTER TABLE tb_onibus OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 17730)
-- Name: tb_parada; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_parada (
    id bigint NOT NULL,
    ds_numero character varying(20) NOT NULL,
    ds_nome character varying(30) NOT NULL,
    ds_posicao character varying(50) NOT NULL
);


ALTER TABLE tb_parada OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 17745)
-- Name: tb_system_parameter; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_system_parameter (
    id bigint NOT NULL,
    name character varying(150) NOT NULL,
    description character varying(300),
    param_value character varying(300)
);


ALTER TABLE tb_system_parameter OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 17753)
-- Name: tb_user; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_user (
    id bigint DEFAULT nextval('sq_tb_user'::regclass) NOT NULL,
    email character varying(150) NOT NULL,
    name character varying(150) NOT NULL,
    pass character varying(256) NOT NULL,
    login_attempts numeric(2,0),
    status integer DEFAULT 0 NOT NULL
);


ALTER TABLE tb_user OWNER TO postgres;

--
-- TOC entry 2340 (class 0 OID 0)
-- Dependencies: 184
-- Name: COLUMN tb_user.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN tb_user.status IS '0: ativo,  1: bloqueado,   2: inativo';


--
-- TOC entry 2341 (class 0 OID 0)
-- Dependencies: 185
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 2, true);


--
-- TOC entry 2325 (class 0 OID 17715)
-- Dependencies: 177
-- Data for Name: rl_linha_parada; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rl_linha_parada (id_linha, id_parada) FROM stdin;
\.


--
-- TOC entry 2342 (class 0 OID 0)
-- Dependencies: 173
-- Name: sq_satup_tb_linha; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_satup_tb_linha', 0, false);


--
-- TOC entry 2343 (class 0 OID 0)
-- Dependencies: 175
-- Name: sq_satup_tb_localizacao; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_satup_tb_localizacao', 0, false);


--
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 172
-- Name: sq_satup_tb_onibus; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_satup_tb_onibus', 0, false);


--
-- TOC entry 2345 (class 0 OID 0)
-- Dependencies: 174
-- Name: sq_satup_tb_parada; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_satup_tb_parada', 0, false);


--
-- TOC entry 2346 (class 0 OID 0)
-- Dependencies: 176
-- Name: sq_tb_user; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_tb_user', 0, false);


--
-- TOC entry 2330 (class 0 OID 17740)
-- Dependencies: 182
-- Data for Name: tb_dmn_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_dmn_role (id, tag_name, tag_description) FROM stdin;
\.


--
-- TOC entry 2327 (class 0 OID 17725)
-- Dependencies: 179
-- Data for Name: tb_linha; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_linha (id, ds_nome, ds_linha, ds_sentido, ds_valor) FROM stdin;
\.


--
-- TOC entry 2329 (class 0 OID 17735)
-- Dependencies: 181
-- Data for Name: tb_localizacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_localizacao (id, ds_localizazao, ds_datehora, id_onibus) FROM stdin;
\.


--
-- TOC entry 2326 (class 0 OID 17720)
-- Dependencies: 178
-- Data for Name: tb_onibus; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_onibus (id, ds_numero, ds_placa, id_linha) FROM stdin;
0	84157811103	JJI-3020	\N
\.


--
-- TOC entry 2328 (class 0 OID 17730)
-- Dependencies: 180
-- Data for Name: tb_parada; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_parada (id, ds_numero, ds_nome, ds_posicao) FROM stdin;
2	330431	Patio Brasil Shopping	-15.796464376690354 , -47.89157152175903
\.


--
-- TOC entry 2331 (class 0 OID 17745)
-- Dependencies: 183
-- Data for Name: tb_system_parameter; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_system_parameter (id, name, description, param_value) FROM stdin;
\.


--
-- TOC entry 2332 (class 0 OID 17753)
-- Dependencies: 184
-- Data for Name: tb_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_user (id, email, name, pass, login_attempts, status) FROM stdin;
0	master	master	eb0a191797624dd3a48fa681d3061212	0	0
\.


--
-- TOC entry 2190 (class 2606 OID 17719)
-- Name: rl_linha_parada_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rl_linha_parada
    ADD CONSTRAINT rl_linha_parada_pkey PRIMARY KEY (id_linha, id_parada);


--
-- TOC entry 2200 (class 2606 OID 17744)
-- Name: tb_dmn_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_dmn_role
    ADD CONSTRAINT tb_dmn_role_pkey PRIMARY KEY (id);


--
-- TOC entry 2194 (class 2606 OID 17729)
-- Name: tb_linha_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_linha
    ADD CONSTRAINT tb_linha_pkey PRIMARY KEY (id);


--
-- TOC entry 2198 (class 2606 OID 17739)
-- Name: tb_localizacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_localizacao
    ADD CONSTRAINT tb_localizacao_pkey PRIMARY KEY (id);


--
-- TOC entry 2192 (class 2606 OID 17724)
-- Name: tb_onibus_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_onibus
    ADD CONSTRAINT tb_onibus_pkey PRIMARY KEY (id);


--
-- TOC entry 2196 (class 2606 OID 17734)
-- Name: tb_parada_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_parada
    ADD CONSTRAINT tb_parada_pkey PRIMARY KEY (id);


--
-- TOC entry 2202 (class 2606 OID 17752)
-- Name: tb_system_parameter_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_system_parameter
    ADD CONSTRAINT tb_system_parameter_pkey PRIMARY KEY (id);


--
-- TOC entry 2204 (class 2606 OID 17762)
-- Name: tb_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_user
    ADD CONSTRAINT tb_user_pkey PRIMARY KEY (id);


--
-- TOC entry 2206 (class 2606 OID 17764)
-- Name: tuc_tb_user_1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_user
    ADD CONSTRAINT tuc_tb_user_1 UNIQUE (email);


--
-- TOC entry 2209 (class 2606 OID 17775)
-- Name: id_linha_onibus_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_onibus
    ADD CONSTRAINT id_linha_onibus_fk FOREIGN KEY (id_linha) REFERENCES tb_onibus(id);


--
-- TOC entry 2210 (class 2606 OID 17780)
-- Name: id_onibus_localizacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_localizacao
    ADD CONSTRAINT id_onibus_localizacao_fk FOREIGN KEY (id_onibus) REFERENCES tb_onibus(id);


--
-- TOC entry 2208 (class 2606 OID 17770)
-- Name: id_parada_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rl_linha_parada
    ADD CONSTRAINT id_parada_fk FOREIGN KEY (id_parada) REFERENCES tb_parada(id);


--
-- TOC entry 2207 (class 2606 OID 17765)
-- Name: tb_linha_tb_parada_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rl_linha_parada
    ADD CONSTRAINT tb_linha_tb_parada_fk FOREIGN KEY (id_linha) REFERENCES tb_linha(id);


-- Completed on 2017-04-11 18:27:56 -03

--
-- PostgreSQL database dump complete
--

