package controllers.admin;

import controllers.Check;
import controllers.base.BaseCRUDController;

/**
 * @author jgomes | 8 de jul de 2016 - 21:51:23
 */
@Check("/adm/home")
public class HomeAdminController extends BaseCRUDController {

    public static void index() {
       
        render();
    }
}
