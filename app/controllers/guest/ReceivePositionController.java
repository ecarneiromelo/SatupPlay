package controllers.guest;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.omg.CORBA.Current;

import common.exceptions.SystemException;
import common.utils.DateUtil;
import controllers.CRUD;
import controllers.CRUD.For;
import controllers.base.BaseGuestController;
import models.LocalizacaoBO;
import models.OnibusBO;
import models.UserBO;
import models.base.BaseAdminController;
@For(LocalizacaoBO.class)
public class ReceivePositionController extends BaseGuestController {
	
	public void savePosition(String idPosition) throws SystemException{
		String [] ArrayIdPosition = idPosition.split(",");
		OnibusBO onibus = OnibusBO.findById(Long.parseLong(ArrayIdPosition[0]));
		LocalizacaoBO localizacao = new LocalizacaoBO();
		localizacao.setTbOnibus(onibus);
		localizacao.setDsDatehora(DateUtil.currentTimestamp());
		localizacao.setDsLocalizazao(ArrayIdPosition[4]+","+ArrayIdPosition[5]);
		localizacao.save();
	}
}
