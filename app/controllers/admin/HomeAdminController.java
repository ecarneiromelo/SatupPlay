package controllers.admin;

import controllers.Check;
import controllers.base.BaseAdminController;

/**
 * @author jgomes | 8 de jul de 2016 - 21:51:23
 */
@Check("/adm/home")
public class HomeAdminController extends BaseAdminController {

    public static void index() {
        render();
    }
}
