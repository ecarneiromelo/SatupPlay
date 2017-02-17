CREATE TABLE TB_ONIBUS(
	ID BIGINT ,
	DS_NUMERO VARCHAR(16) NOT NULL,
	DS_PLACA VARCHAR(8) NOT NULL,
    PRIMARY KEY (ID)
);
CREATE TABLE TB_LINHA(
	ID BIGINT ,
	DS_NOME VARCHAR(200)NOT NULL,
	DS_LINHA VARCHAR(10)NOT NULL,
	DS_SENTIDO BOOLEAN NOT NULL,
	DS_VALOR FLOAT NOT NULL,
    ID_ONIBUS BIGINT
);
CREATE TABLE TB_PARADA(
	ID BIGINT ,
	DS_NUMERO VARCHAR(20)NOT NULL,
	DS_NOME VARCHAR(30)NOT NULL,
	DS_POSICAO VARCHAR(23)NOT NULL,
    ID_LINHA BIGINT
);
CREATE TABLE TB_LOCALIZACAO(
	ID BIGINT ,
	DS_LOCALIZAZAO  VARCHAR(23) NOT NULL,
	DS_DATEHORA TIMESTAMP NOT NULL,
    ID_ONIBUS BIGINT
);
