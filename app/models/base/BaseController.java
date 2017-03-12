package models.base;

import common.utils.StringUtil;
import play.Play;
import play.i18n.Lang;

/**
 * @author jgomes
 */
public class BaseController extends BaseCRUDController {

    private static final String QUERY_STRING_LOCALE = "locale=";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Methods - public static
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static void changeLanguage(final String locale, final String originalUrl) {
        Lang.change(locale);
        String url = Play.ctxPath + "/";
        if (StringUtil.isNotEmpty(originalUrl)) {
            url = originalUrl;
        }
        if (url.contains(QUERY_STRING_LOCALE)) {
            url = url.replaceAll(QUERY_STRING_LOCALE + "..", QUERY_STRING_LOCALE + locale);
        }
        redirect(url);
    }
}
