package controllers.security;

import static common.constants.ControllerConstants.CHECK;
import static common.constants.ControllerConstants.GET_METHOD;
import static common.constants.ControllerConstants.REMEMBER_ME;
import static common.constants.ControllerConstants.USERNAME;
import static common.constants.MessageConstants.MESSAGE_ERROR_SECURE_BLOCKED_USER;
import static common.constants.MessageConstants.MESSAGE_ERROR_SECURE_ERROR;
import static common.constants.MessageConstants.MESSAGE_ERROR_SECURE_INACTIVE_USER;
import static common.constants.MessageConstants.MESSAGE_SUCCESS_OPERATION;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import common.constants.DomainConstants.UserStatus;
import common.exceptions.SystemException;
import common.utils.PlayUtil;
import controllers.Check;
import controllers.Secure;
import models.UserBO;
import play.Play;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.libs.Crypto;
import play.libs.Time;
import play.mvc.Before;
import play.mvc.Controller;
import play.utils.Java;

/**
 * @author jgomes
 */
public class SecureController extends Controller {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // @Before Filters
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Before(unless = {
            "login",
            "authenticate",
            "logout"
    })
    static void checkAccess() throws Throwable {
        // Authent
        if (!session.contains(USERNAME)) {
            flash.put("url", GET_METHOD.equals(request.method) ? request.url : Play.ctxPath + "/");
            Secure.login();
        }
        // Checks
        Check check = getActionAnnotation(Check.class);
        if (check != null) {
            check(check);
        }
        check = getControllerInheritedAnnotation(Check.class);
        if (check != null) {
            check(check);
        }
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static void authenticate(@Required final String username, @Required final String password, final boolean remember) throws Throwable {
        // Check tokens
        final Boolean allowed = (Boolean) Security.invoke("authenticate", username, password);
        if (Validation.hasErrors() || !allowed) {
            flash.keep("url");
            setErrorMessage(username);
            params.flash();
            Secure.login();
        }
        configConnectedUser(username, remember);
        // Redirect to the original URL (or /)
        redirectToOriginalURL();
    }
    public static void logout() throws Throwable {
        Security.invoke("onDisconnect");
        session.clear();
        response.removeCookie(REMEMBER_ME);
        flash.keep(MESSAGE_SUCCESS_OPERATION);
        Security.invoke("onDisconnected");
        flash.success("secure.logout");
        Secure.login();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Default static methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    static void redirectToOriginalURL() throws Throwable {
        Security.invoke("onAuthenticated");
        String url = flash.get("url");
        if (url == null) {
            url = Play.ctxPath + "/";
        }
        redirect(url);
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private static methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static void configConnectedUser(final String username, final boolean remember) {
        // Mark user as connected
        session.put(USERNAME, username);
        // Remember if needed
        if (remember) {
            final Date expiration = new Date();
            final String duration = PlayUtil.getSecureRememberMeDuration();
            expiration.setTime(expiration.getTime() + Time.parseDuration(duration) * 1000);
            response.setCookie(REMEMBER_ME, Crypto.sign(username + "-" + expiration.getTime()) + "-" + username + "-" + expiration.getTime(), duration);
        }
    }
    private static void check(final Check check) throws Throwable {
        int i = check.value().length;
        for (final String profile : check.value()) {
            final boolean hasProfile = (Boolean) Security.invoke(CHECK, profile);
            if (!hasProfile) {
                if (i > 1) {
                    i--;
                    continue;
                }
                Security.invoke("onCheckFailed", profile);
            }
        }
    }
    private static void setErrorMessage(final String username) throws SystemException, ParseException, SQLException {
        final UserBO object = UserBO.findByEmail(username);
        if (object != null && object.getStatus() == UserStatus.BLOCKED) {
            flash.error(MESSAGE_ERROR_SECURE_BLOCKED_USER);
        } else if (object != null && object.getStatus() == UserStatus.INACTIVE) {
            flash.error(MESSAGE_ERROR_SECURE_INACTIVE_USER);
        } else {
            flash.error(MESSAGE_ERROR_SECURE_ERROR);
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Inner Classes
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * @author jgomes | 8 de jul de 2016 - 21:47:11
     */
    public static class Security extends SecurityController {

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Protected static methods
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        protected static Object invoke(final String m, final Object... args) throws Throwable {
            try {
                return Java.invokeChildOrStatic(SecurityController.class, m, args);
            } catch (final InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }
}
