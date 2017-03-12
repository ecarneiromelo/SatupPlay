package controllers.security;

import static common.constants.ControllerConstants.REFERER;
import static common.constants.MessageConstants.MESSAGE_ERROR_SECURE_ACCESS_DENIED;
import static common.constants.MessageConstants.MESSAGE_SUCCESS_OPERATION;

import common.constants.DomainConstants.UserStatus;
import common.encrypt.EncryptProvider;
import common.exceptions.SystemException;
import common.utils.BinaryUtil;
import common.utils.StringUtil;
import controllers.Secure;
import controllers.admin.HomeAdminController;
import models.MenuBO;
import models.UserBO;
import play.Logger;
import play.i18n.Messages;

/**
 * @author jgomes
 */
public class SecurityController extends Secure.Security {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static boolean authenticate(final String username, final String password) throws SystemException {
        boolean authenticated = false;
        final UserBO user = UserBO.findByEmail(username);
        if (user == null || user.getStatus() == UserStatus.BLOCKED) {
            return false;
        }
        final byte[] byteFromHex = BinaryUtil.hex2byte(user.getPass());
        final String result = EncryptProvider.decryptAESWithHmac256(new String(byteFromHex), password);
        if (user.getId().toString().equals(result)) {
            authenticated = true;
        }
        authenticated = checkAttempts(username, authenticated);
        return authenticated;
    }
    public static boolean check(final String link) throws Throwable {
        if (request.isAjax()) {
            return true;
        }
        if (isConnected() && request.headers.containsKey(REFERER)) {
            final UserBO user = UserBO.findByEmail(connected());
            if (user == null) {
                return false;
            }
            final Long count = MenuBO.countMenuByUrlAndRoleId(link, user.getRole().getId());
            return count > 0;
        }
        return false;
    }
    public static boolean isConnected() {
        return session.contains("username");
    }
    public static String connected() {
        return session.get("username");
    }
    public static void onCheckFailed(final String profile) {
        final String username = SecurityController.connected();
        if (StringUtil.isNotEmpty(username)) {
            Logger.warn("onCheckFailed! \n\tuser: %1s \n\tmenu: %2s", username, profile);
        }
        forbidden(Messages.get(MESSAGE_ERROR_SECURE_ACCESS_DENIED));
    }
    public static void onAuthenticated() {
        HomeAdminController.index();
    }
    public static void onDisconnected() throws Throwable {
        flash.keep(MESSAGE_SUCCESS_OPERATION);
        Secure.login();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Priavate static methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static boolean checkAttempts(final String username, final boolean authenticated) {
        final UserBO object = UserBO.findByEmail(username);
        if (object != null) {
            if (object.getStatus() == UserStatus.BLOCKED || object.getStatus() == UserStatus.INACTIVE) {
                return false;
            } else {
                object.updateAccessAttempts(authenticated);
            }
        }
        return authenticated;
    }
}