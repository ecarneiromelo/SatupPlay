/* ---------------------------------------------------------------------- */                          
/* Project name:          gattaz_pset                                     */
/* Author:                                                                */
/* Script type:           Alter database script                           */
/* Created on:            2016-08-31 12:33                                */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Alter table "pset.tb_program"                                          */
/* ---------------------------------------------------------------------- */
ALTER TABLE pset.tb_program ADD COLUMN has_treatment BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE pset.tb_program ADD COLUMN show_report BOOLEAN;
