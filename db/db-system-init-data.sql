--------------------------------------------------------
--  Arquivo criado - Quinta-feira-Fevereiro-11-2016   
--------------------------------------------------------

DROP SCHEMA public ;
CREATE SCHEMA public;
SET search_path = public;

--------------------------------------------------------
--  Add sequences  
--------------------------------------------------------
CREATE SEQUENCE  SQ_SATUP_TB_ONIBUS INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;
CREATE SEQUENCE  SQ_SATUP_TB_LINHA INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;
CREATE SEQUENCE  SQ_SATUP_TB_PARADA INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;
CREATE SEQUENCE  SQ_SATUP_TB_LOCALIZACAO INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

--------------------------------------------------------
--  DDL for Index SATUP_RL_LINHA_PARADA_PK
--------------------------------------------------------
  CREATE TABLE RL_LINHA_PARADA (
    ID_LINHA BIGINT  NOT NULL,
    ID_PARADA BIGINT  NOT NULL,
    CONSTRAINT RL_LINHA_PARADA_PKEY PRIMARY KEY (ID_LINHA, ID_PARADA)
);

--------------------------------------------------------
--  DDL for Index SATUP_RL_ONIBUS_PRODUTO_PK
--------------------------------------------------------
  CREATE TABLE RL_ONIBUS_LINHA (
    ID_ONIBUS BIGINT  NOT NULL,
    ID_LINHA BIGINT  NOT NULL,
    CONSTRAINT RL_ONIBUS_PRODUTO_PKEY PRIMARY KEY (ID_ONIBUS, ID_LINHA)
);

--------------------------------------------------------
--  DDL for Table TB_ONIBUS
--------------------------------------------------------
CREATE TABLE TB_ONIBUS(
	ID BIGINT PRIMARY KEY,
	DS_NUMERO VARCHAR(16) NOT NULL,
	DS_PLACA VARCHAR(8) NOT NULL
);

--------------------------------------------------------
--  DDL for Table TB_LINHA
--------------------------------------------------------
CREATE TABLE TB_LINHA(
	ID BIGINT PRIMARY KEY,
	DS_NOME VARCHAR(200)NOT NULL,
	DS_LINHA VARCHAR(10)NOT NULL,
	DS_SENTIDO BOOLEAN NOT NULL,
	DS_VALOR FLOAT NOT NULL
);

--------------------------------------------------------
--  DDL for Table TB_PARADA
--------------------------------------------------------
CREATE TABLE TB_PARADA(
	ID BIGINT PRIMARY KEY,
	DS_NUMERO VARCHAR(20)NOT NULL,
	DS_NOME VARCHAR(30)NOT NULL,
	DS_POSICAO VARCHAR(23)NOT NULL
);

--------------------------------------------------------
--  DDL for Table TB_LOCALIZACAO
--------------------------------------------------------
CREATE TABLE TB_LOCALIZACAO(
	ID BIGINT PRIMARY KEY,
	DS_LOCALIZAZAO  VARCHAR(23) NOT NULL,
	DS_DATEHORA TIMESTAMP NOT NULL,
    ID_ONIBUS BIGINT
);
/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

ALTER TABLE TB_LOCALIZACAO ADD CONSTRAINT ID_ONIBUS_FK
    FOREIGN KEY (ID_ONIBUS) REFERENCES TB_ONIBUS (ID);