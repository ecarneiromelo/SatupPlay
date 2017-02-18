
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (1, 'system.params.smtp.server', 'system.params.smtp.server.desc', 'smtpi.gattazhr.com');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (2, 'system.params.smtp.port', 'system.params.smtp.port.desc', '587');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (3, 'system.params.smtp.user', 'system.params.smtp.user.desc', 'contato@gattazhr.com');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (4, 'system.params.smtp.passwd', 'system.params.smtp.passwd.desc', 'gattaz#2015');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (5, 'system.params.smtp.sender', 'system.params.smtp.sender.desc', 'contato@gattazhr.com');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (6, 'system.params.email.url', 'system.params.email.url.desc', 'http://mentalhealth.gattazhr.com');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (7, 'system.params.auth.limit', 'system.params.auth.limit.desc', '5');
INSERT INTO TB_SYSTEM_PARAMETER(ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (8, 'system.params.sms.api.url', 'system.params.sms.api.url.desc', 'http://mm.otimatel.com.br/en/api/');
INSERT INTO TB_SYSTEM_PARAMETER(ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (9, 'system.params.sms.api.user', 'system.params.sms.api.user.desc', 'gattaz');
INSERT INTO TB_SYSTEM_PARAMETER(ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (10, 'system.params.sms.api.key', 'system.params.sms.api.key.desc', '91d6e4451aa8dd06c08997b33b848b3633b46402');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (11, 'system.params.official.site', 'system.params.official.site.desc', 'http://www.gattazhr.com/');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (12, 'system.params.google.api.key', 'system.params.google.api.key.desc', 'AIzaSyCZ7H8vUOWzhGMGGL-lj6SElJpkrpvsGzY');
INSERT INTO TB_SYSTEM_PARAMETER (ID, NAME, DESCRIPTION, PARAM_VALUE) VALUES (13,'system.params.google.api.url.shotener', 'system.params.google.api.url.shotener.desc', 'https://www.googleapis.com/urlshortener/v1/url');

INSERT INTO TB_DMN_ROLE (ID, TAG_NAME, TAG_DESCRIPTION) VALUES (1, 'role.master', 'role.master.desc');
INSERT INTO TB_DMN_ROLE (ID, TAG_NAME, TAG_DESCRIPTION) VALUES (2, 'role.company', 'role.company.desc');

INSERT INTO TB_USER(ID, ID_USER_GROUP, ID_DMN_ROLE, EMAIL, NAME, PASS, LOGIN_ATTEMPTS, STATUS) 
     VALUES (
    0, 
    NULL,
    1,
    'master@gattazhr.com', 
    'Usuário Administrador', 
    '4B634E4275426357746D415A35336F57754B7830714B45326D673878304533746F504F3862786636654176764C34735743706B5A372B41504D346379624F697436714B5646646B4459333479484D394F6A31765A3574515455797252', -- pass: gattazhr
    0, 
    0
);

select nextval('sq_tb_user');
