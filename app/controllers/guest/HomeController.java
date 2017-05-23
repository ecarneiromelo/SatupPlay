package controllers.guest;

import java.util.ArrayList;
import java.util.List;

import controllers.base.BaseGuestController;
import models.LinhaBO;
import models.OnibusBO;
import models.ParadaBO;
import models.base.BaseAdminController;
import models.base.BaseModel;

/**
 * @author jgomes | 8 de jul de 2016 - 21:51:23
 */
public class HomeController extends BaseGuestController {

    public static void index() {
        render();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Ajax static methods.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static void getLoadParada(final String idLinha) {
        List<OnibusBO> lstOnibus = new ArrayList<OnibusBO>();
        Long id = Long.parseLong(idLinha);
        LinhaBO linha = LinhaBO.findById(id);
        if (linha != null && linha.getId() != null) {
            lstOnibus = OnibusBO.findByLinha(linha);
            for (OnibusBO obj : lstOnibus) {
                obj.loadLastLocation();
            }
        }
        renderJSON(BaseModel.toJson(lstOnibus));
    }
    public static void getLoadMarker(final String idLinha) {
    	List<ParadaBO> lstParada = new ArrayList<ParadaBO>();
        Long id = Long.parseLong(idLinha);
        if (id != null) {
        	lstParada = ParadaBO.findParadaByLinha(id);
        }
        renderJSON(BaseModel.toJson(lstParada));
    }
}
