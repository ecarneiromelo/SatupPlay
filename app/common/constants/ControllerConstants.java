package common.constants;

/**
 * @author jgomes
 */
public final class ControllerConstants {

    // Listeners @Before | @After | @Finally interceptor priority
    // ~~~~~~~
    public static final short MAX_PRIORITY = 0;
    public static final short HIGH_PRIORITY = 10;
    public static final short NORM_PRIORITY = 20;
    public static final short MIN_PRIORITY = 40;
    // Session
    // ~~~~~~~
    public static final String CACHE_TIMEOUT = "1h";
    // HTTP methods
    // ~~~~~~~
    public static final String POST_METHOD = "POST";
    public static final String GET_METHOD = "GET";
    public static final String DELETE_METHOD = "DELETE";
    // Secure module constants
    // ~~~~~~~
    public static final String CHECK = "check";
    public static final String USERNAME = "username";
    public static final String REMEMBER_ME = "rememberme";
    // CRUD module constants
    // ~~~~~~~
    public static final String LIST_HTML = "list.html";
    public static final String INDEX_HTML = "index.html";
    public static final String SHOW_HTML = "show.html";
    public static final String BLANK_HTML = "blank.html";
    public static final String CRUD_MAIN_PATH = "CRUD/";
    public static final String CRUD_SHOW_HTML = CRUD_MAIN_PATH + SHOW_HTML;
    public static final String CRUD_BLANK_HTML = CRUD_MAIN_PATH + BLANK_HTML;
    public static final String CRUD_LIST_HTML = CRUD_MAIN_PATH + LIST_HTML;
    public static final String CRUD_PAGE_SIZE = "crud.pageSize";
    public static final String CRUD_DELETED_MESSAGE = "crud.deleted";
    public static final String CRUD_DELETE_ERROR_MESSAGE = "crud.delete.error";
    public static final String CRUD_BATCH_DELETED_MESSAGE = "crud.batch.deleted";
    public static final String CRUD_BATCH_FIRMWARE_UPDATED_MESSAGE = "crud.batch.firmware.updated";
    public static final String CRUD_BATCH_DELETE_ERROR_MESSAGE = "crud.batch.delete.error";
    public static final String CRUD_BATCH_UPDATE_FIRMWARE_ERROR_MESSAGE = "crud.batch.update.firmware.error";
    public static final String CRUD_BATCH_SELECT_TCA_ERROR = "crud.batch.select.tca.error";
    public static final String CRUD_SAVED_MESSAGE = "crud.saved";
    public static final String CRUD_CREATED_MESSAGE = "crud.created";
    public static final String CRUD_HAS_ERRORS = "crud.hasErrors";
    public static final String CRUD_DEFAULT_PAGE_SIZE = "30";
    public static final String DOT_BLANK = ".blank";
    public static final String DOT_LIST = ".list";
    public static final String DOT_SHOW = ".show";
    public static final String DOT_INDEX = ".index";
    public static final String SAVE = "_save";
    public static final String SAVE_AND_ADD_ANOTHER = "_saveAndAddAnother";
    public static final String DOT = ".";
    public static final String SLASH = "/";
    public static final String PAGE = "page";
    public static final String ERROR = "error";
    public static final String WARNING = "warning";
    public static final String OBJECT = "object";
    public static final String HIDDEN = "hidden";
    public static final String WHERE = "where";
    public static final String TEXT = "text";
    public static final String LONGTEXT = "longtext";
    public static final String PASSWD = "password";
    public static final String EMPTY_STRING = "";
    public static final String SPACE_STRING = " ";
    public static final String REFERER = "referer";
    public static final String COMMA = ",";
    public static final String DOT_COMMA = ";";
    // Generic constants
    // ~~~~~~~
    public static final String CONNECTED_USER = "connectedUser";
    public static final String CAPTCHA_COLOR = "#194067";
    public static final String CAPTCHA_EXPIRATION = "5mn";
    public static final String EMPTY_JSON_STRING = "{}";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private ControllerConstants() {
        super();
    }
}
