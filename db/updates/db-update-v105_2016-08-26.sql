/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases V9.1.2                     */
/* Target DBMS:           PostgreSQL 9                                    */
/* Project file:          model.dez                                       */
/* Project name:          gattaz_pset                                     */
/* Author:                                                                */
/* Script type:           Alter database script                           */
/* Created on:            2016-08-26 09:27                                */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Drop foreign key constraints                                           */
/* ---------------------------------------------------------------------- */

ALTER TABLE pset.tb_user DROP CONSTRAINT tb_user_group_fk;

ALTER TABLE pset.rl_program_survey DROP CONSTRAINT tb_program_fk1;

ALTER TABLE pset.tb_invitation DROP CONSTRAINT tb_program_fk;

ALTER TABLE pset.tb_respondent DROP CONSTRAINT tb_user_group_fk1;

/* ---------------------------------------------------------------------- */
/* Alter table "pset.tb_user_group"                                       */
/* ---------------------------------------------------------------------- */

ALTER TABLE pset.tb_user_group DROP address;

ALTER TABLE pset.tb_user_group DROP logo;

/* ---------------------------------------------------------------------- */
/* Alter table "pset.tb_program"                                          */
/* ---------------------------------------------------------------------- */

ALTER TABLE pset.tb_program DROP greeting_message;

ALTER TABLE pset.tb_program ADD COLUMN id_user_group BIGINT;

-- BRISA adjust BEGIN
UPDATE pset.tb_program SET id_user_group = 0; -- ***************************************** CUIDADO... escolher o ID da empresa que passará a possuir todos os programas ou fazer DUMP e arrumar na unha **************************
UPDATE pset.tb_user SET id_user_group = 0 where id_user_group IS NOT NULL; --  ************************** CUIDADO... agora coloca todos os usuários na mesma empresa ou fazer DUMP e arrumar na unha **************************
UPDATE pset.tb_respondent SET id_user_group = 0; --  ************************** CUIDADO... Mantendo apenas a EMPRESA escolhida acima  **************************
DELETE FROM pset.tb_user_group WHERE id <> 0; --  ************************** CUIDADO... Mantendo apenas a EMPRESA escolhida acima  **************************
-- BRISA adjust END

ALTER TABLE pset.tb_program ALTER COLUMN id_user_group SET NOT NULL;

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

ALTER TABLE pset.tb_program ADD CONSTRAINT tb_user_group_tb_program_fk 
    FOREIGN KEY (id_user_group) REFERENCES pset.tb_user_group (id);

ALTER TABLE pset.tb_user ADD CONSTRAINT tb_user_group_fk 
    FOREIGN KEY (id_user_group) REFERENCES pset.tb_user_group (id);

ALTER TABLE pset.rl_program_survey ADD CONSTRAINT tb_program_fk1 
    FOREIGN KEY (id_program) REFERENCES pset.tb_program (id);

ALTER TABLE pset.tb_invitation ADD CONSTRAINT tb_program_fk 
    FOREIGN KEY (id_program) REFERENCES pset.tb_program (id);

ALTER TABLE pset.tb_respondent ADD CONSTRAINT tb_user_group_fk1 
    FOREIGN KEY (id_user_group) REFERENCES pset.tb_user_group (id);




