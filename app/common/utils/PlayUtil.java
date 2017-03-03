package common.utils;

import static common.constants.SystemConstants.APPLICATION_URL;
import static common.constants.SystemConstants.SMTP_HOST;
import static common.constants.SystemConstants.SMTP_PASS;
import static common.constants.SystemConstants.SMTP_PORT;
import static common.constants.SystemConstants.SMTP_SENDER;
import static common.constants.SystemConstants.SMTP_USER;

import models.SystemParameterBO;
import play.Play;

/**
 * @author jgomes
 */
public class PlayUtil {

    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_SMTP_USER = "mail.smtp.user";
    private static final String MAIL_SMTP_PASS = "mail.smtp.pass";
    private static final String MAIL_SMTP_FROM = "mail.smtp.from";
    private static final String SECURE_REMEMBERME_DURATION = "secure.rememberme.duration";
    private static final String APPLICATION_BASE_URL = "application.baseUrl";
    private static final String APPLICATION_ID = "application.secret";
    private static final String APPLICATION_WEB_ENCODING = "application.web_encoding";
    private static final String HTTP_INTEGRATION_PATH = "http.integration.path";
    private static final String DB_DEFAULT_DRIVER = "db.default.driver";
    private static final String CRUD_PAGE_SIZE = "crud.pageSize";
    private static final String CRUD_DEFAULT_PAGE_SIZE = "30";
    private static final String DEFAULT_DURATION = "30d";
    private static final String DEFAULT_LANG = "default.lang";
    private static final String DEFAULT_DB_SCHEMA = "hibernate.default.default_schema";
    private static final String HTTP_PATH = "http.path";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private PlayUtil() {
        super();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static String getProperty(final String key) {
        return Play.configuration.getProperty(key, "");
    }
    private static String getProperty(final String key, final String defaultValue) {
        return Play.configuration.getProperty(key, defaultValue);
    }
    public static void setConfiguration(final String key, final String value) {
        Play.configuration.setProperty(key, value);
    }
    public static void setConfigurationIfNotNull(final String key, final String value) {
        if (StringUtil.isNotEmpty(value)) {
            setConfiguration(key, value);
        }
    }
    public static String getIntegrationPath() {
        return getProperty(PlayUtil.HTTP_INTEGRATION_PATH);
    }
    public static int getPageSize() {
        return Integer.parseInt(getProperty(CRUD_PAGE_SIZE, CRUD_DEFAULT_PAGE_SIZE));
    }
    public static String getDefaultLang() {
        return getProperty(PlayUtil.DEFAULT_LANG);
    }
    public static String getHttpPath() {
        return getProperty(PlayUtil.HTTP_PATH);
    }
    public static String getDefaultWebEnconde() {
        return getProperty(APPLICATION_WEB_ENCODING);
    }
    public static String getDefaultDatabaseSchema() {
        return getProperty(DEFAULT_DB_SCHEMA);
    }
    public static String getDefaultDatabaseSchemaForQuery() {
        return StringUtil.isEmpty(getDefaultDatabaseSchema()) ? "" : getDefaultDatabaseSchema() + ".";
    }
    public static String getDefaultJdbcDriver() {
        return getProperty(PlayUtil.DB_DEFAULT_DRIVER);
    }
    public static String getSecureRememberMeDuration() {
        return getProperty(PlayUtil.SECURE_REMEMBERME_DURATION, DEFAULT_DURATION);
    }
    public static String getApplicationBaseUrl() {
        return getProperty(PlayUtil.APPLICATION_BASE_URL, "NULL");
    }
    public static String getApplicationId() {
        return getProperty(PlayUtil.APPLICATION_ID);
    }
//    public static void refreshSmtpConfiguration() {
//        SystemParameterBO param = (SystemParameterBO) SystemParameterBO.findById(SMTP_HOST);
//        setConfigurationIfNotNull(MAIL_SMTP_HOST, param != null ? param.getValue() : null);
//        param = (SystemParameterBO) SystemParameterBO.findById(SMTP_PORT);
//        setConfigurationIfNotNull(MAIL_SMTP_PORT, param != null ? param.getValue() : null);
//        param = (SystemParameterBO) SystemParameterBO.findById(SMTP_USER);
//        setConfigurationIfNotNull(MAIL_SMTP_USER, param != null ? param.getValue() : null);
//        param = (SystemParameterBO) SystemParameterBO.findById(SMTP_PASS);
//        setConfigurationIfNotNull(MAIL_SMTP_PASS, param != null ? param.getValue() : null);
//        param = (SystemParameterBO) SystemParameterBO.findById(SMTP_SENDER);
//        setConfigurationIfNotNull(MAIL_SMTP_FROM, param != null ? param.getValue() : null);
//    }
//    public static void refreshApplicationBaseUrl() {
//        final SystemParameterBO param = (SystemParameterBO) SystemParameterBO.findById(APPLICATION_URL);
//        setConfigurationIfNotNull(APPLICATION_BASE_URL, param != null ? param.getValue() : null);
//    }
}
