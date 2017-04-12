package controllers.base;

import static common.constants.ControllerConstants.CONNECTED_USER;
import static common.constants.ControllerConstants.HIGH_PRIORITY;
import static common.constants.ControllerConstants.POST_METHOD;

import common.annotations.BlockAction;
import common.annotations.BlockAction.Action;
import common.exceptions.SystemException;
import common.utils.StringUtil;
import controllers.Secure;
import controllers.security.SecureController;
import controllers.security.SecurityController;
import models.UserBO;
import play.mvc.Before;
import play.mvc.With;

/**
 * @author jgomes
 */
@With(Secure.class)
public class BaseAdminController extends BaseCRUDController {

    protected static final String ADM_USER_CONTROLLER_SAVE = "admin.UserController.save";
    protected static final String SHOW_PASSWORD_HTML = "showPassword.html";
    protected static final String OBJECT_NEW_PASSWD = "object.newPass";
    protected static final String OBJECT_PASSWD = "object.pass";
    protected static final String OBJECT_CHECK_PASSWD = "object.checkPass";
    protected static final int MIN_PASS_SIZE = 6;
    protected static final int MAX_PASS_SIZE = 100;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Filters
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Before(priority = HIGH_PRIORITY)
    public static void checkAuthenticityToken() {
        if (needToCheckRequest()) {
            checkAuthenticity();
        }
    }
    @Before(priority = HIGH_PRIORITY)
    public static void setConnectedUser() throws SystemException {
        if (!request.isAjax()) {
            if (SecurityController.isConnected()) {
                renderArgs.put(CONNECTED_USER, getConnectedUser());
            }
        }
    }
    @Before(priority = HIGH_PRIORITY)
    public static void blockAction() {
        if (!request.isAjax()) {
            final BlockAction annotation = getControllerClass().getAnnotation(BlockAction.class);
            if (annotation != null) {
                final Action[] arrayActions = annotation.value();
                for (int i = 0; i < arrayActions.length; i++) {
                    renderArgs.put(arrayActions[i].toString(), true);
                }
            }
        }
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static UserBO getConnectedUser() throws SystemException {
        UserBO user = renderArgs.get(CONNECTED_USER, UserBO.class);
        if (user == null) {
            try {
                final String login = SecurityController.connected();
                if (StringUtil.isEmpty(login)) {
                    SecureController.logout();
                }
                user = UserBO.findByEmail(login);
                if (user == null) {
                    SecureController.logout();
                }
            } catch (final Throwable e) {
                throw new SystemException("Error: Can not retrieve the connected user.");
            }
        }
        return user;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Protected static methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    protected static String addAndClause(String where) {
        where = StringUtil.isEmpty(where) ? "" : where + " AND ";
        return where;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Priavate static methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static boolean needToCheckRequest() {
        boolean needToCheck = true;
        if (request.isAjax()) {
            needToCheck = false;
        }
        return request.method == POST_METHOD && needToCheck;
    }
}
