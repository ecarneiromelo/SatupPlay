package controllers.admin;

import controllers.CRUD;
import controllers.CRUD.For;
import models.OnibusBO;
import play.db.jpa.GenericModel;

@CRUD.For(OnibusBO.class)
public class OnibusController extends CRUD{
}
