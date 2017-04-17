package controllers.admin;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.base.BaseCRUDController;
import models.LinhaBO;
import models.OnibusBO;
import play.Play;

/**
 * @author jgomes | 8 de jul de 2016 - 21:51:23
 */

public class HomeAdminController extends BaseCRUDController {
    
	private static final GsonBuilder builder = OnibusBO.buildJsonParser();
    private static Gson gson = null;
    static {
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setDateFormat(Play.configuration.getProperty("date.format"));
        gson = builder.create();
    }
	
    public static void index() {
        render();
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Ajax static methods.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    public static void getLoadParada(final String idLinha){
    	List<OnibusBO> lstOnibus = new ArrayList<OnibusBO>();
    	Long id = Long.parseLong(idLinha);
    	LinhaBO linha =  LinhaBO.findById(id);
    	if(linha != null && linha.id != 0){
    		lstOnibus = OnibusBO.findByLinha(linha);
    	}
        renderJSON(lstOnibus, gson);
    }
    public static void getLoadMarker(int idOnibus, int iDparada){
    	
    }
}
