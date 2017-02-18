
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (1, 'system.params.smtp.server', 'system.params.smtp.server.desc', 'sedna.brisa.org.br');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (2, 'system.params.smtp.port', 'system.params.smtp.port.desc', '25');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (3, 'system.params.smtp.user', 'system.params.smtp.user.desc', NULL);
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (4, 'system.params.smtp.passwd', 'system.params.smtp.passwd.desc', NULL);
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (5, 'system.params.smtp.sender', 'system.params.smtp.sender.desc', 'contato@brisa.org.br');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (6, 'system.params.email.url', 'system.params.email.url.desc', 'http://df.brisa.org.br');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (7, 'system.params.auth.limit', 'system.params.auth.limit.desc', '5');
INSERT INTO TB_SYSTEM_PARAMETER(ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (8, 'system.params.sms.api.url', 'system.params.sms.api.url.desc', 'https://mkt.otimatel.com.br/en/api/');
INSERT INTO TB_SYSTEM_PARAMETER(ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (9, 'system.params.sms.api.user', 'system.params.sms.api.user.desc', 'brisa');
INSERT INTO TB_SYSTEM_PARAMETER(ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (10, 'system.params.sms.api.key', 'system.params.sms.api.key.desc', '93d4ae3b2ade40d5d4e8a006889d6ce53a68c816');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (11, 'system.params.official.site', 'system.params.official.site.desc', 'http://www.brisa.org.br');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (12, 'system.params.google.api.key', 'system.params.google.api.key.desc', 'AIzaSyAH2rCIvHj63l2dX8NtlJ3WdcAt3tf6ZWg');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (13,'system.params.google.api.url.shotener', 'system.params.google.api.url.shotener.desc', 'https://www.googleapis.com/urlshortener/v1/url');
 
INSERT INTO TB_DMN_ROLE (ID, TAG_NAME, TAG_DESCRIPTION) VALUES (1, 'role.master', 'role.master.desc');
INSERT INTO TB_DMN_ROLE (ID, TAG_NAME, TAG_DESCRIPTION) VALUES (2, 'role.company', 'role.company.desc');

INSERT INTO TB_USER(ID, ID_USER_GROUP, ID_DMN_ROLE, EMAIL, NAME, PASS, LOGIN_ATTEMPTS, STATUS) 
     VALUES (
    0, 
    NULL,
    1,
    'master@gattazhr.com', 
    'Usuário Adm. Brisa', 
    '44464E57714F7A316E753162484B74716D73764D4448526F4D536B6D7835396E62455369367A6D74377A48484161666247586D71366F6A774B63635448456A315452476C5645554E526468446D77436A33733855795377564E756976', -- pass: brisa
    0, 
    0
);

select nextval('sq_tb_user');
