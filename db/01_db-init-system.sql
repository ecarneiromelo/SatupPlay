/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases V9.1.5                     */
/* Target DBMS:           PostgreSQL 9                                    */
/* Project file:          Pset Model.dez                                  */
/* Project name:          gattaz_pset                                     */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2016-10-25 14:45                                */
/* ---------------------------------------------------------------------- */


DROP schema IF EXISTS pset cascade;
CREATE SCHEMA IF NOT EXISTS pset;
SET search_path = pset, pg_catalog;

/* ---------------------------------------------------------------------- */
/* Add sequences                                                          */
/* ---------------------------------------------------------------------- */

CREATE SEQUENCE pset.sq_tb_user INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

CREATE SEQUENCE pset.sq_tb_user_group INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

CREATE SEQUENCE pset.sq_tb_answer INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

CREATE SEQUENCE pset.sq_tb_question INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

CREATE SEQUENCE pset.sq_tb_option INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

CREATE SEQUENCE pset.sq_tb_program INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

CREATE SEQUENCE pset.sq_tb_respondent INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

CREATE SEQUENCE pset.sq_tb_question_deps INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

CREATE SEQUENCE pset.sq_tb_survey INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

CREATE SEQUENCE pset.sq_tb_invitation INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

CREATE SEQUENCE pset.sq_tb_question_group INCREMENT 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 CACHE 1;

/* ---------------------------------------------------------------------- */
/* Add tables                                                             */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_user_group"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_user_group (
    id BIGINT DEFAULT nextval('sq_tb_user_group')  NOT NULL,
    name CHARACTER VARYING(150)  NOT NULL,
    CONSTRAINT tb_user_group_pkey PRIMARY KEY (id),
    CONSTRAINT TUC_tb_user_group_1 UNIQUE (name)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_dmn_role"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_dmn_role (
    id BIGINT  NOT NULL,
    tag_name CHARACTER VARYING(100),
    tag_description CHARACTER VARYING(300),
    CONSTRAINT tb_dmn_role_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_system_parameter"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_system_parameter (
    id BIGINT  NOT NULL,
    name CHARACTER VARYING(150)  NOT NULL,
    description CHARACTER VARYING(300),
    param_value CHARACTER VARYING(300),
    CONSTRAINT tb_system_parameter_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_dmn_menu"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_dmn_menu (
    id BIGINT  NOT NULL,
    id_parent_menu BIGINT,
    tag_name CHARACTER VARYING(200)  NOT NULL,
    tag_description CHARACTER VARYING(200),
    menu_order SMALLINT  NOT NULL,
    url CHARACTER VARYING(256)  NOT NULL,
    CONSTRAINT tb_dmn_menu_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_option"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_option (
    id BIGINT DEFAULT nextval('sq_tb_option')  NOT NULL,
    option CHARACTER VARYING(150),
    CONSTRAINT tb_option_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_dmn_survey_variable"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_dmn_survey_variable (
    id BIGINT  NOT NULL,
    tag_name CHARACTER VARYING(50)  NOT NULL,
    column_name CHARACTER VARYING(30)  NOT NULL,
    CONSTRAINT tb_dmn_survey_variable_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.rl_role_menu"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.rl_role_menu (
    id_role BIGINT  NOT NULL,
    id_menu BIGINT  NOT NULL,
    CONSTRAINT rl_role_menu_pkey PRIMARY KEY (id_role, id_menu)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_respondent"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_respondent (
    id BIGINT DEFAULT nextval('sq_tb_respondent')  NOT NULL,
    id_user_group BIGINT  NOT NULL,
    email CHARACTER VARYING(150),
    cell_phone CHARACTER VARYING(50),
    name CHARACTER VARYING(150)  NOT NULL,
    gender CHARACTER VARYING(1)  NOT NULL,
    marital_status CHARACTER VARYING(150)  NOT NULL,
    salary DOUBLE PRECISION  NOT NULL,
    job_position CHARACTER VARYING(150)  NOT NULL,
    age SMALLINT  NOT NULL,
    working_hours SMALLINT  NOT NULL,
    company_time SMALLINT  NOT NULL,
    home_address CHARACTER VARYING(300)  NOT NULL,
    business_address CHARACTER VARYING(300)  NOT NULL,
    staff_member BOOLEAN DEFAULT true  NOT NULL,
    department CHARACTER VARYING(150)  NOT NULL,
    batch_history BIGINT,
    CONSTRAINT tb_repondent_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_dmn_question_type"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_dmn_question_type (
    id BIGINT  NOT NULL,
    tag_name CHARACTER VARYING(150)  NOT NULL,
    CONSTRAINT tb_dmn_question_type_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_dmn_survey_type"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_dmn_survey_type (
    id INTEGER  NOT NULL,
    name CHARACTER VARYING(150)  NOT NULL,
    CONSTRAINT tb_dmn_survey_type_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_question_group"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_question_group (
    id BIGINT DEFAULT nextval('sq_tb_question_group')  NOT NULL,
    name CHARACTER VARYING(40)  NOT NULL,
    description CHARACTER VARYING(255),
    CONSTRAINT tb_question_group_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_user"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_user (
    id BIGINT DEFAULT nextval('sq_tb_user')  NOT NULL,
    id_user_group BIGINT,
    id_dmn_role BIGINT  NOT NULL,
    email CHARACTER VARYING(150)  NOT NULL,
    name CHARACTER VARYING(150)  NOT NULL,
    pass CHARACTER VARYING(256)  NOT NULL,
    login_attempts NUMERIC(2),
    status INTEGER DEFAULT 0  NOT NULL,
    CONSTRAINT tb_user_pkey PRIMARY KEY (id),
    CONSTRAINT TUC_tb_user_1 UNIQUE (email)
);

COMMENT ON COLUMN pset.tb_user.status IS '0: ativo,  1: bloqueado,   2: inativo';

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_dmn_score_class"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_dmn_score_class (
    id BIGINT  NOT NULL,
    id_survey_type INTEGER  NOT NULL,
    id_question_group BIGINT,
    description CHARACTER VARYING(300)  NOT NULL,
    min_points DOUBLE PRECISION  NOT NULL,
    max_points DOUBLE PRECISION  NOT NULL,
    normal BOOLEAN DEFAULT false  NOT NULL,
    average BOOLEAN DEFAULT false  NOT NULL,
    CONSTRAINT tb_dmn_score_class_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_program"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_program (
    id BIGINT DEFAULT nextval('sq_tb_program')  NOT NULL,
    id_user_group BIGINT  NOT NULL,
    id_user BIGINT  NOT NULL,
    registry_date TIMESTAMP  NOT NULL,
    dispatch_date TIMESTAMP,
    sent_date TIMESTAMP,
    limit_date TIMESTAMP,
    title CHARACTER VARYING(150),
    status SMALLINT DEFAULT 0  NOT NULL,
    has_treatment BOOLEAN DEFAULT true  NOT NULL,
    show_report BOOLEAN,
    language CHARACTER VARYING(6) DEFAULT 'br'  NOT NULL,
    CONSTRAINT tb_program_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_invitation"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_invitation (
    id BIGINT DEFAULT nextval('sq_tb_invitation')  NOT NULL,
    id_respondent BIGINT  NOT NULL,
    id_program BIGINT  NOT NULL,
    dispatch_date TIMESTAMP,
    sent_date TIMESTAMP,
    limit_date TIMESTAMP,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    invitation_viewed BOOLEAN DEFAULT false  NOT NULL,
    term_accepted BOOLEAN,
    orientation_requested BOOLEAN,
    orientation_completed BOOLEAN DEFAULT false  NOT NULL,
    link_code BIGINT  NOT NULL,
    sent_type INTEGER DEFAULT 0  NOT NULL,
    CONSTRAINT tb_invitation_pkey PRIMARY KEY (id)
);

COMMENT ON COLUMN pset.tb_invitation.sent_type IS '0: email,  
1: sms';

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_survey"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_survey (
    id BIGINT DEFAULT nextval('sq_tb_survey')  NOT NULL,
    id_user BIGINT  NOT NULL,
    id_survey_type INTEGER  NOT NULL,
    name CHARACTER VARYING(150)  NOT NULL,
    description CHARACTER VARYING(255) DEFAULT 'NULL',
    active BOOLEAN DEFAULT true  NOT NULL,
    registry_date TIMESTAMP  NOT NULL,
    CONSTRAINT tb_survey_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.rl_program_survey"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.rl_program_survey (
    id_program BIGINT  NOT NULL,
    id_survey BIGINT  NOT NULL,
    CONSTRAINT rl_program_survey_pkey PRIMARY KEY (id_program, id_survey)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_question"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_question (
    id BIGINT DEFAULT nextval('sq_tb_question')  NOT NULL,
    id_survey BIGINT  NOT NULL,
    id_question_type BIGINT  NOT NULL,
    id_question_group BIGINT,
    question CHARACTER VARYING(250),
    CONSTRAINT tb_question_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.rl_question_option"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.rl_question_option (
    id_question BIGINT  NOT NULL,
    id_option BIGINT  NOT NULL,
    question_option_order BIGINT  NOT NULL,
    weight BIGINT  NOT NULL,
    CONSTRAINT rl_question_option_pkey PRIMARY KEY (id_question, id_option)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_question_deps"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_question_deps (
    id BIGINT DEFAULT nextval('sq_tb_question_deps')  NOT NULL,
    id_question BIGINT  NOT NULL,
    id_question_deps BIGINT  NOT NULL,
    id_option_deps BIGINT  NOT NULL,
    CONSTRAINT tb_question_deps_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "pset.tb_answer"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE pset.tb_answer (
    id BIGINT DEFAULT nextval('sq_tb_answer')  NOT NULL,
    id_invitation BIGINT,
    id_question BIGINT  NOT NULL,
    id_option BIGINT  NOT NULL,
    answer_date TIMESTAMP,
    CONSTRAINT tb_answer_pkey PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

ALTER TABLE pset.tb_user ADD CONSTRAINT tb_user_group_fk 
    FOREIGN KEY (id_user_group) REFERENCES pset.tb_user_group (id);

ALTER TABLE pset.tb_user ADD CONSTRAINT tb_dmn_role_tb_user_fk 
    FOREIGN KEY (id_dmn_role) REFERENCES pset.tb_dmn_role (id);

ALTER TABLE pset.tb_dmn_score_class ADD CONSTRAINT tb_question_group_tb_dmn_score_class_fk 
    FOREIGN KEY (id_question_group) REFERENCES pset.tb_question_group (id);

ALTER TABLE pset.tb_dmn_score_class ADD CONSTRAINT tb_dmn_survey_type_tb_dmn_score_class_fk 
    FOREIGN KEY (id_survey_type) REFERENCES pset.tb_dmn_survey_type (id);

ALTER TABLE pset.tb_question ADD CONSTRAINT tb_dmn_question_type_fk 
    FOREIGN KEY (id_question_type) REFERENCES pset.tb_dmn_question_type (id);

ALTER TABLE pset.tb_question ADD CONSTRAINT tb_survey_fk 
    FOREIGN KEY (id_survey) REFERENCES pset.tb_survey (id);

ALTER TABLE pset.tb_question ADD CONSTRAINT tb_question_group_tb_question_fk 
    FOREIGN KEY (id_question_group) REFERENCES pset.tb_question_group (id);

ALTER TABLE pset.tb_dmn_menu ADD CONSTRAINT tb_dmn_menu_fk 
    FOREIGN KEY (id_parent_menu) REFERENCES pset.tb_dmn_menu (id);

ALTER TABLE pset.tb_answer ADD CONSTRAINT rl_question_option_fk 
    FOREIGN KEY (id_option, id_question) REFERENCES pset.rl_question_option (id_option,id_question);

ALTER TABLE pset.tb_answer ADD CONSTRAINT tb_invitation_tb_answer_fk 
    FOREIGN KEY (id_invitation) REFERENCES pset.tb_invitation (id);

ALTER TABLE pset.tb_program ADD CONSTRAINT tb_user_group_tb_program_fk 
    FOREIGN KEY (id_user_group) REFERENCES pset.tb_user_group (id);

ALTER TABLE pset.tb_program ADD CONSTRAINT tb_user_tb_program_fk 
    FOREIGN KEY (id_user) REFERENCES pset.tb_user (id);

ALTER TABLE pset.rl_question_option ADD CONSTRAINT tb_question_fk 
    FOREIGN KEY (id_question) REFERENCES pset.tb_question (id);

ALTER TABLE pset.rl_question_option ADD CONSTRAINT tb_option_fk 
    FOREIGN KEY (id_option) REFERENCES pset.tb_option (id);

ALTER TABLE pset.tb_invitation ADD CONSTRAINT tb_respondent_fk 
    FOREIGN KEY (id_respondent) REFERENCES pset.tb_respondent (id);

ALTER TABLE pset.tb_invitation ADD CONSTRAINT tb_program_fk 
    FOREIGN KEY (id_program) REFERENCES pset.tb_program (id);

ALTER TABLE pset.rl_role_menu ADD CONSTRAINT tb_dmn_menu_fk1 
    FOREIGN KEY (id_menu) REFERENCES pset.tb_dmn_menu (id);

ALTER TABLE pset.rl_role_menu ADD CONSTRAINT tb_dmn_role_fk 
    FOREIGN KEY (id_role) REFERENCES pset.tb_dmn_role (id);

ALTER TABLE pset.tb_survey ADD CONSTRAINT tb_user_fk1 
    FOREIGN KEY (id_user) REFERENCES pset.tb_user (id);

ALTER TABLE pset.tb_survey ADD CONSTRAINT tb_dmn_survey_type_tb_survey_fk 
    FOREIGN KEY (id_survey_type) REFERENCES pset.tb_dmn_survey_type (id);

ALTER TABLE pset.tb_respondent ADD CONSTRAINT tb_user_group_fk1 
    FOREIGN KEY (id_user_group) REFERENCES pset.tb_user_group (id);

ALTER TABLE pset.rl_program_survey ADD CONSTRAINT tb_program_fk1 
    FOREIGN KEY (id_program) REFERENCES pset.tb_program (id);

ALTER TABLE pset.rl_program_survey ADD CONSTRAINT tb_survey_fk1 
    FOREIGN KEY (id_survey) REFERENCES pset.tb_survey (id);

ALTER TABLE pset.tb_question_deps ADD CONSTRAINT rl_question_option_tb_question_deps_fk 
    FOREIGN KEY (id_question_deps, id_option_deps) REFERENCES pset.rl_question_option (id_question,id_option);

ALTER TABLE pset.tb_question_deps ADD CONSTRAINT tb_question_tb_question_deps_fk 
    FOREIGN KEY (id_question) REFERENCES pset.tb_question (id);

/* ---------------------------------------------------------------------- */
/* Add views                                                              */
/* ---------------------------------------------------------------------- */

CREATE OR REPLACE VIEW pset.vw_count_question_deps
AS

SELECT q.id_survey,
    q.id AS id_question,
    COALESCE(vw_counter.count_deps
, (0)::bigint) AS count_deps

FROM (tb_question q

LEFT JOIN (
SELECT qd.id_question,
            count(*) AS count_deps

FROM tb_question_deps qd
          GROUP BY qd.id_question) vw_counter
ON ((q.id = vw_counter.id_question)));

CREATE OR REPLACE VIEW pset.vw_count_answer_score
AS

SELECT vw_score.id_invitation,
    vw_score.id_survey,
    sc.id AS id_score_class,
    vw_score.score,
    round(vw_score.score_avg
, 1) AS score_avg

FROM ((((
SELECT a.id_invitation,
            q.id_survey,
            COALESCE(sum(qo.weight)
, (0)::numeric) AS score,
            COALESCE(avg(qo.weight)
, (0)::numeric) AS score_avg

FROM ((tb_answer a
      JOIN rl_question_option qo
ON (((a.id_question = qo.id_question)
AND (a.id_option = qo.id_option))))
   JOIN tb_question q
ON ((q.id = a.id_question)))
  GROUP BY a.id_invitation
, q.id_survey) vw_score
   JOIN tb_survey s
ON ((s.id = vw_score.id_survey)))
   JOIN tb_dmn_survey_type st
ON ((st.id = s.id_survey_type)))
   JOIN tb_dmn_score_class sc
ON ((((st.id = sc.id_survey_type)
AND (sc.id_question_group IS NULL))
AND ((((sc.average = true)
AND ((vw_score.score_avg)::double precision > sc.min_points))
AND ((vw_score.score_avg)::double precision <= sc.max_points)) OR (((sc.average = false)
AND ((vw_score.score)::double precision > sc.min_points))
AND ((vw_score.score)::double precision <= sc.max_points))))));

CREATE OR REPLACE VIEW pset.vw_count_answer_score_all
AS

SELECT vw_inv_sur.id_invitation,
    vw_inv_sur.id_survey,
    vw.id_score_class,
    vw.score,
    vw.score_avg

FROM ((
SELECT i.id AS id_invitation,
            s.id AS id_survey

FROM (tb_invitation i
     CROSS JOIN tb_survey s)
     ORDER BY i.id
, s.id) vw_inv_sur

LEFT JOIN vw_count_answer_score vw
ON (((vw_inv_sur.id_invitation = vw.id_invitation)
AND (vw_inv_sur.id_survey = vw.id_survey))))
  ORDER BY vw_inv_sur.id_invitation
, vw_inv_sur.id_survey;

CREATE OR REPLACE VIEW pset.vw_count_answer_score_group
AS

SELECT vw_score.id_invitation,
    vw_score.id_survey,
    vw_score.id_question_group,
    sc.id AS id_score_class,
    vw_score.score,
    round(vw_score.score_avg
, 1) AS score_avg,
    vw_score.max_score

FROM ((((
SELECT a.id_invitation,
            q.id_survey,
            q.id_question_group,
            COALESCE(sum(qo.weight)
, (0)::numeric) AS score,
            COALESCE(avg(qo.weight)
, (0)::numeric) AS score_avg,
            max(qo.weight) AS max_score

FROM ((tb_answer a
      JOIN rl_question_option qo
ON (((a.id_question = qo.id_question)
AND (a.id_option = qo.id_option))))
   JOIN tb_question q
ON ((q.id = a.id_question)))

WHERE (q.id_question_group IS NOT NULL)
  GROUP BY a.id_invitation
, q.id_survey
, q.id_question_group) vw_score
   JOIN tb_survey s
ON ((s.id = vw_score.id_survey)))
   JOIN tb_dmn_survey_type st
ON ((st.id = s.id_survey_type)))
   JOIN tb_dmn_score_class sc
ON ((((st.id = sc.id_survey_type)
AND (sc.id_question_group = vw_score.id_question_group))
AND ((((sc.average = true)
AND ((vw_score.score_avg)::double precision > sc.min_points))
AND ((vw_score.score_avg)::double precision <= sc.max_points)) OR (((sc.average = false)
AND ((vw_score.score)::double precision > sc.min_points))
AND ((vw_score.score)::double precision <= sc.max_points))))));

CREATE OR REPLACE VIEW pset.vw_count_answer_score_group_all
AS

SELECT vw_inv_grp.id_invitation,
    vw_inv_grp.id_question_group,
    vw.id_score_class,
    vw.score,
    vw.score_avg,
    vw.max_score

FROM ((
SELECT i.id AS id_invitation,
            g.id AS id_question_group

FROM (tb_invitation i
     CROSS JOIN tb_question_group g)) vw_inv_grp

LEFT JOIN vw_count_answer_score_group vw
ON (((vw_inv_grp.id_invitation = vw.id_invitation)
AND (vw_inv_grp.id_question_group = vw.id_question_group))))
  ORDER BY vw_inv_grp.id_invitation
, vw_inv_grp.id_question_group;

CREATE OR REPLACE VIEW pset.vw_count_answer_score_burnout
AS

SELECT vw4.id_invitation,
    vw4.id_survey,
    vw4.id_question_group,
    sc.id AS id_score_class,
    vw4.score,
    vw4.score_avg,
    vw4.max_score

FROM (((((
SELECT vw1.id_invitation,
            vw1.id_survey,
            max(vw2.id_question_group) AS id_question_group

FROM ((
SELECT vw_count_answer_score_group.id_invitation,
                    vw_count_answer_score_group.id_survey,
                    max(vw_count_answer_score_group.score_avg) AS max_score_avg

FROM vw_count_answer_score_group

WHERE (vw_count_answer_score_group.id_survey = 4)
                  GROUP BY vw_count_answer_score_group.id_invitation
, vw_count_answer_score_group.id_survey) vw1
      JOIN (
SELECT vw_count_answer_score_group.id_invitation,
                    vw_count_answer_score_group.id_survey,
                    vw_count_answer_score_group.id_question_group,
                    vw_count_answer_score_group.score_avg

FROM vw_count_answer_score_group

WHERE (vw_count_answer_score_group.id_survey = 4)) vw2
ON (((((vw1.id_invitation = vw2.id_invitation)
AND (vw1.id_survey = vw2.id_survey))
AND (vw1.id_survey = 4))
AND (vw1.max_score_avg = vw2.score_avg))))
     GROUP BY vw1.id_invitation
, vw1.id_survey
, vw1.max_score_avg) vw3
   JOIN vw_count_answer_score_group vw4
ON ((((vw3.id_invitation = vw4.id_invitation)
AND (vw3.id_survey = vw4.id_survey))
AND (vw3.id_question_group = vw4.id_question_group))))
   JOIN tb_survey s
ON ((s.id = vw4.id_survey)))
   JOIN tb_dmn_survey_type st
ON ((st.id = s.id_survey_type)))
   JOIN tb_dmn_score_class sc
ON (((sc.id_survey_type = st.id)
AND (vw4.id_question_group = sc.id_question_group))))

WHERE (sc.max_points > (10000)::double precision);

CREATE OR REPLACE VIEW pset.vw_count_answer_score_burnout_all
AS

SELECT vw_inv_sur.id_invitation,
    vw_inv_sur.id_survey,
    vw.id_score_class,
    vw.score,
    vw.score_avg

FROM ((
SELECT i.id AS id_invitation,
            s.id AS id_survey

FROM (tb_invitation i
     CROSS JOIN tb_survey s)
     ORDER BY i.id
, s.id) vw_inv_sur

LEFT JOIN vw_count_answer_score_burnout vw
ON (((vw_inv_sur.id_invitation = vw.id_invitation)
AND (vw_inv_sur.id_survey = vw.id_survey))))
  ORDER BY vw_inv_sur.id_invitation
, vw_inv_sur.id_survey;

CREATE OR REPLACE VIEW pset.vw_invitation_answers
AS

SELECT a.id_invitation,
    q.id_survey,
    a.id AS id_answer

FROM (tb_answer a
   JOIN tb_question q
ON ((q.id = a.id_question)))
  ORDER BY a.id_invitation
, q.id_survey
, a.id_question;

CREATE OR REPLACE VIEW pset.vw_summary_report_yes_no_program
AS

SELECT program.id AS id_program,
    ((((COALESCE(inv_yes.completed_qtd
, (0)::bigint))::numeric / ((COALESCE(inv_yes.completed_qtd
, (0)::bigint))::numeric + (COALESCE(inv_no.completed_qtd
, (0)::bigint))::numeric)) * 100.0))::bigint AS p_yes,
    ((((COALESCE(inv_no.completed_qtd
, (0)::bigint))::numeric / ((COALESCE(inv_yes.completed_qtd
, (0)::bigint))::numeric + (COALESCE(inv_no.completed_qtd
, (0)::bigint))::numeric)) * 100.0))::bigint AS p_no,
    COALESCE(inv_yes.completed_qtd
, (0)::bigint) AS t_yes,
    COALESCE(inv_no.completed_qtd
, (0)::bigint) AS t_no

FROM ((tb_program program

LEFT JOIN (
SELECT tb_invitation.id_program,
            count(tb_invitation.id) AS completed_qtd

FROM tb_invitation

WHERE (tb_invitation.end_date IS NOT NULL)
          GROUP BY tb_invitation.id_program) inv_yes
ON ((inv_yes.id_program = program.id)))

LEFT JOIN (
SELECT tb_invitation.id_program,
       count(tb_invitation.id) AS completed_qtd

FROM tb_invitation

WHERE (tb_invitation.end_date IS NULL)
     GROUP BY tb_invitation.id_program) inv_no
ON ((inv_no.id_program = program.id)))

WHERE ((inv_yes.completed_qtd > 0) OR (inv_no.completed_qtd > 0));

CREATE OR REPLACE VIEW pset.vw_summary_report_program_response_week
AS

SELECT program.id AS id_program,
    COALESCE(this_week.counter
, (0)::bigint) AS this_week,
    COALESCE(last_week.counter
, (0)::bigint) AS last_week,
    COALESCE(last_two_weeks.counter
, (0)::bigint) AS last_two_weeks,
    COALESCE(last_three_weeks.counter
, (0)::bigint) AS last_three_weeks,
    COALESCE(last_four_weeks.counter
, (0)::bigint) AS last_four_weeks,
    COALESCE(before_last_four_weeks.counter
, (0)::bigint) AS before_last_four_weeks

FROM ((((((tb_program program

LEFT JOIN (
SELECT tb_invitation.id_program,
            count(*) AS counter

FROM tb_invitation

WHERE (date_part('week'::text
, tb_invitation.end_date) = date_part('week'::text
, now()))
          GROUP BY tb_invitation.id_program) this_week
ON ((this_week.id_program = program.id)))

LEFT JOIN (
SELECT tb_invitation.id_program,
       count(*) AS counter

FROM tb_invitation

WHERE (date_part('week'::text
, tb_invitation.end_date) = (date_part('week'::text
, now()) - (1)::double precision))
     GROUP BY tb_invitation.id_program) last_week
ON ((last_week.id_program = program.id)))

LEFT JOIN (
SELECT tb_invitation.id_program,
    count(*) AS counter

FROM tb_invitation

WHERE (date_part('week'::text
, tb_invitation.end_date) = (date_part('week'::text
, now()) - (2)::double precision))
  GROUP BY tb_invitation.id_program) last_two_weeks
ON ((last_two_weeks.id_program = program.id)))

LEFT JOIN (
SELECT tb_invitation.id_program,
    count(*) AS counter

FROM tb_invitation

WHERE (date_part('week'::text
, tb_invitation.end_date) = (date_part('week'::text
, now()) - (3)::double precision))
  GROUP BY tb_invitation.id_program) last_three_weeks
ON ((last_three_weeks.id_program = program.id)))

LEFT JOIN (
SELECT tb_invitation.id_program,
    count(*) AS counter

FROM tb_invitation

WHERE (date_part('week'::text
, tb_invitation.end_date) = (date_part('week'::text
, now()) - (4)::double precision))
  GROUP BY tb_invitation.id_program) last_four_weeks
ON ((last_four_weeks.id_program = program.id)))

LEFT JOIN (
SELECT tb_invitation.id_program,
    count(*) AS counter

FROM tb_invitation

WHERE (date_part('week'::text
, tb_invitation.end_date) < (date_part('week'::text
, now()) - (4)::double precision))
  GROUP BY tb_invitation.id_program) before_last_four_weeks
ON ((before_last_four_weeks.id_program = program.id)));

CREATE OR REPLACE VIEW pset.vw_invitation_answers_all
AS

SELECT vw.id_invitation,
    vw.id_survey,
    vw.id_question,
    a.id_option

FROM ((
SELECT i.id AS id_invitation,
            vw_ps.id_program,
            vw_ps.id_survey,
            q.id AS id_question

FROM ((tb_invitation i
      JOIN (
SELECT p.id AS id_program,
                    s.id AS id_survey

FROM (tb_program p
             CROSS JOIN tb_survey s)) vw_ps
ON ((vw_ps.id_program = i.id_program)))
   JOIN tb_question q
ON ((vw_ps.id_survey = q.id_survey)))) vw

LEFT JOIN tb_answer a
ON (((a.id_invitation = vw.id_invitation)
AND (a.id_question = vw.id_question))))
  ORDER BY vw.id_invitation
, vw.id_survey
, vw.id_question;

CREATE OR REPLACE VIEW pset.vw_count_answer_deps
AS

SELECT vw1.id_invitation,
    vw1.id_survey,
    vw1.id_question,
    vw1.count_deps,
    COALESCE(vw2.count_deps_answers
, (0)::bigint) AS count_deps_answers

FROM ((
SELECT i.id AS id_invitation,
            cqd.id_survey,
            cqd.id_question,
            cqd.count_deps

FROM ((vw_count_question_deps cqd
      JOIN rl_program_survey ps
ON ((cqd.id_survey = ps.id_survey)))
   JOIN tb_invitation i
ON ((ps.id_program = i.id_program)))) vw1

LEFT JOIN (
SELECT a.id_invitation,
            q.id_survey,
            qd.id_question,
            count(*) AS count_deps_answers

FROM ((tb_answer a
      JOIN tb_question_deps qd
ON (((a.id_question = qd.id_question_deps)
AND (a.id_option = qd.id_option_deps))))
   JOIN tb_question q
ON ((qd.id_question = q.id)))
  GROUP BY a.id_invitation
, q.id_survey
, qd.id_question) vw2
ON ((((vw1.id_invitation = vw2.id_invitation)
AND (vw1.id_survey = vw2.id_survey))
AND (vw1.id_question = vw2.id_question))))
  ORDER BY vw1.id_invitation
, vw1.id_survey
, vw1.id_question;
