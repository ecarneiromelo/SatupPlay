package controllers.admin;

import static common.constants.ControllerConstants.CRUD_SAVED_MESSAGE;
import static common.constants.ControllerConstants.OBJECT;
import static common.constants.ControllerConstants.SHOW_HTML;

import common.encrypt.EncryptProvider;
import common.exceptions.SystemException;
import controllers.CRUD.For;
import controllers.CRUD.ObjectType;
import models.UserBO;
import models.base.BaseAdminController;
import play.data.binding.Binder;
import play.db.Model;
import play.i18n.Messages;

@For(UserBO.class)
public class UserController extends BaseAdminController {
	public static void save(final Long id) throws SystemException {
        try {
            final ObjectType type = new ObjectType(UserBO.class);
            notFoundIfNull(type);
            final UserBO object = UserBO.findById(id);
            notFoundIfNull(object);
            Binder.bindBean(params.getRootParamNode(), OBJECT, object);
            validation.valid(object);
            renderIfHasErrors(type, object, SHOW_HTML, true);
            object.setPass(EncryptProvider.encryptMD5(object.getPass()));
            object._save();
            flash.success(Messages.get(CRUD_SAVED_MESSAGE, type.modelName));
            redirect(object);
        } catch (final Exception e) {
            throw new SystemException(e);
        }
    }

}
