--------------------------------------------------------
--  Arquivo criado - Quinta-feira-Fevereiro-11-2016   
--------------------------------------------------------

DROP SCHEMA PUBLIC CASCADE;
CREATE SCHEMA PUBLIC;
SET SEARCH_PATH = PUBLIC;

--------------------------------------------------------
--  ADD SEQUENCES  
--------------------------------------------------------

CREATE SEQUENCE  SQ_SATUP_TB_ONIBUS INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;
CREATE SEQUENCE  SQ_SATUP_TB_LINHA INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;
CREATE SEQUENCE  SQ_SATUP_TB_PARADA INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;
CREATE SEQUENCE  SQ_SATUP_TB_LOCALIZACAO INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;
CREATE SEQUENCE  SQ_TB_USER INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

--------------------------------------------------------
--  DDL FOR INDEX SATUP_RL_LINHA_PARADA_PK
--------------------------------------------------------

  CREATE TABLE RL_LINHA_PARADA (
    ID_LINHA BIGINT  NOT NULL,
    ID_PARADA BIGINT  NOT NULL,
    CONSTRAINT RL_LINHA_PARADA_PKEY PRIMARY KEY (ID_LINHA, ID_PARADA)
);

--------------------------------------------------------
--  DDL FOR TABLE TB_ONIBUS
--------------------------------------------------------

CREATE TABLE TB_ONIBUS(
	ID BIGINT PRIMARY KEY,
	DS_NUMERO VARCHAR(16) NOT NULL,
	DS_PLACA VARCHAR(8) NOT NULL,
    ID_LINHA BIGINT
);

--------------------------------------------------------
--  DDL FOR TABLE TB_LINHA
--------------------------------------------------------

CREATE TABLE TB_LINHA(
	ID BIGINT PRIMARY KEY,
	DS_NOME VARCHAR(200)NOT NULL,
	DS_LINHA VARCHAR(10)NOT NULL,
	DS_SENTIDO BOOLEAN NOT NULL,
	DS_VALOR FLOAT NOT NULL
);

--------------------------------------------------------
--  DDL FOR TABLE TB_PARADA
--------------------------------------------------------

CREATE TABLE TB_PARADA(
	ID BIGINT PRIMARY KEY,
	DS_NUMERO VARCHAR(20)NOT NULL,
	DS_NOME VARCHAR(30)NOT NULL,
	DS_POSICAO VARCHAR(50)NOT NULL
);

--------------------------------------------------------
--  DDL FOR TABLE TB_LOCALIZACAO
--------------------------------------------------------

CREATE TABLE TB_LOCALIZACAO(
	ID BIGINT PRIMARY KEY,
	DS_LOCALIZAZAO  VARCHAR(50) NOT NULL,
	DS_DATEHORA TIMESTAMP NOT NULL,
    ID_ONIBUS BIGINT
);

--------------------------------------------------------
-- ADD TABLE "TB_DMN_ROLE"                            
--------------------------------------------------------

CREATE TABLE TB_DMN_ROLE (
    ID BIGINT  NOT NULL,
    TAG_NAME CHARACTER VARYING(100),
    TAG_DESCRIPTION CHARACTER VARYING(300),
    CONSTRAINT TB_DMN_ROLE_PKEY PRIMARY KEY (ID)
);

--------------------------------------------------------
-- ADD TABLE "TB_SYSTEM_PARAMETER"                    
--------------------------------------------------------

CREATE TABLE TB_SYSTEM_PARAMETER (
    ID BIGINT  NOT NULL,
    NAME CHARACTER VARYING(150)  NOT NULL,
    DESCRIPTION CHARACTER VARYING(300),
    PARAM_VALUE CHARACTER VARYING(300),
    CONSTRAINT TB_SYSTEM_PARAMETER_PKEY PRIMARY KEY (ID)
);

--------------------------------------------------------
-- ADD TABLE "TB_USER"                                
--------------------------------------------------------

CREATE TABLE TB_USER (
    ID BIGINT DEFAULT NEXTVAL('SQ_TB_USER')  NOT NULL,
    EMAIL CHARACTER VARYING(150)  NOT NULL,
    NAME CHARACTER VARYING(150)  NOT NULL,
    PASS CHARACTER VARYING(256)  NOT NULL,
    LOGIN_ATTEMPTS NUMERIC(2),
    STATUS INTEGER DEFAULT 0  NOT NULL,
    CONSTRAINT TB_USER_PKEY PRIMARY KEY (ID),
    CONSTRAINT TUC_TB_USER_1 UNIQUE (EMAIL)
);

COMMENT ON COLUMN tb_user.status IS '0: ativo,  1: bloqueado,   2: inativo';

--------------------------------------------------------
--   Add foreign key constraints  
--------------------------------------------------------

ALTER TABLE RL_LINHA_PARADA ADD CONSTRAINT TB_LINHA_TB_PARADA_FK
    FOREIGN KEY (ID_LINHA) REFERENCES TB_LINHA (ID);
	
ALTER TABLE RL_LINHA_PARADA ADD CONSTRAINT ID_PARADA_FK
    FOREIGN KEY (ID_PARADA) REFERENCES TB_PARADA (ID);
	    
ALTER TABLE TB_ONIBUS ADD CONSTRAINT ID_LINHA_ONIBUS_FK
	FOREIGN KEY (ID_LINHA) REFERENCES TB_LINHA (ID);
	
ALTER TABLE TB_LOCALIZACAO ADD CONSTRAINT ID_ONIBUS_LOCALIZACAO_FK
    FOREIGN KEY (ID_ONIBUS) REFERENCES TB_ONIBUS (ID);

ALTER TABLE TB_USER ADD CONSTRAINT TB_DMN_ROLE_TB_USER_FK 
    FOREIGN KEY (ID_DMN_ROLE) REFERENCES TB_DMN_ROLE (ID);

ALTER TABLE TB_DMN_MENU ADD CONSTRAINT TB_DMN_MENU_FK 
    FOREIGN KEY (ID_PARENT_MENU) REFERENCES TB_DMN_MENU (ID);

ALTER TABLE RL_ROLE_MENU ADD CONSTRAINT TB_DMN_MENU_FK1 
    FOREIGN KEY (ID_MENU) REFERENCES TB_DMN_MENU (ID);

ALTER TABLE RL_ROLE_MENU ADD CONSTRAINT TB_DMN_ROLE_FK 
    FOREIGN KEY (ID_ROLE) REFERENCES TB_DMN_ROLE (ID);