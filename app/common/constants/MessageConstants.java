package common.constants;

/**
 * @author jgomes
 */
public final class MessageConstants {

    // Messages
    // ~~~~
    public static final String MESSAGE_SUCCESS_OPERATION = "message.success.operation";
    public static final String MESSAGE_SUCCESS_PASSWD_RESET = "message.success.passwd.reset";
    public static final String MESSAGE_SUCCESS_NEW_PASSWD_SENT = "message.success.new.password.sent";
    public static final String MESSAGE_SUCCESS_OPERATION_WAS_SENT = "message.success.operation.was.sent";
    public static final String MESSAGE_WARNING_OPERATION = "message.warning.operation";
    public static final String MESSAGE_WARNING_EMPTY_RESULT = "message.warning.empty.result";
    public static final String MESSAGE_ERROR_OPERATION_FAILED = "message.error.operation.failed";
    public static final String MESSAGE_ERROR_SECURE_ERROR = "secure.error";
    public static final String MESSAGE_ERROR_SECURE_BLOCKED_USER = "secure.error.blocked.user";
    public static final String MESSAGE_ERROR_SECURE_INACTIVE_USER = "secure.error.inactive.user";
    public static final String MESSAGE_ERROR_SECURE_ACCESS_DENIED = "403.error.message";
    public static final String MESSAGE_ERROR_SECURE_EXPIRED_PASSWD = "secure.error.expired.passwd";
    public static final String MESSAGE_ERROR_INVALID_CAPTCHA_CODE = "message.error.invalid.captcha.code";
    public static final String MESSAGE_ERROR_INVALID_USER_MAIL_NOTFOUND = "message.error.invalid.user.mail.notfound";
    public static final String MESSAGE_ERROR_IMPORT_FILE_COLUMN_NUMBER_NOT_MATCHING = "message.error.import.file.column.number.not.matching";
    public static final String MESSAGE_ERROR_IMPORT_FILE_COLUMN_INVALID_VALUE = "message.error.import.file.invalid.value";
    public static final String MESSAGE_ERROR_IMPORT_FILE_PROGRAM_NOT_FOUND = "message.error.import.file.program.not.found";
    public static final String MESSAGE_ERROR_PROGRAM_ALREADY_EXISTS = "message.error.program.already.exists";
    public static final String MESSAGE_ERROR_PROGRAM_ALREADY_EXISTS_FOR_USER = "message.error.program.already.exists.for.user";
    public static final String MESSAGE_ERROR_USER_ALREADY_EXISTIS_IN_MASTER = "message.error.user.already.existis.is.master";
    public static final String MESSAGE_WARNING_PROGRAM_BURNOUT_SURVEY = "message.warning.program.burnout.survey";
    public static final String MESSAGE_WARNING_PROGRAM_HOPITALAR_SURVEY = "message.warning.program.hospitalar.survey";
    public static final String MESSAGE_OBJECTS_NOT_FOUND_PROGRAM = "objects.not.found.program";
    public static final String MESSAGE_SUCCESS_PASS_CHANGED = "message.success.pass.changed";
    public static final String MESSAGE_MAIL_SUBJECT = "message.mail.subject";
    public static final String MESSAGE_REPORT_TITLE = "report.title";
    // Status
    // ~~~~
    public static final String STATUS_WAITING_RESPONDENT = "waitting.respondent";
    public static final String STATUS_IN_PROGRESS = "in.progress";
    public static final String STATUS_CLOSED = "closed";
    // Mail
    // ~~~~
    public static final String MAIL_SUBJECT_RESET_PASSWD = "mail.subject.reset.password";
    // SMS
    // ~~~~
    public static final String SMS_API_URL_NULL = "message.error.sms.config.url.null";
    public static final String SMS_API_USER_NULL = "message.error.sms.config.user.null";
    public static final String SMS_API_KEY_NULL = "message.error.sms.config.key.null";
    public static final String SMS_INVITE_MESSAGE = "sms.invite.message";
    public static final String SMS_FINISH_MESSAGE = "sms.finish.message";
    // Validations
    // ~~~~
    public static final String VALIDATION_REQUIRED = "validation.required";
    public static final String VALIDATION_MAX_SIZE = "validation.maxSize";
    public static final String VALIDATION_CPF = "validation.cpf";
    public static final String VALIDATION_CNPJ = "validation.cnpj";
    public static final String VALIDATION_PASSWD_NOT_MATCH = "validation.passwd.not.match";
    public static final String VALIDATION_AT_LEAST_ONE_FILLED = "validation.at.least.one.filled";
    public static final String VALIDATION_PROFILE_NOT_EXIST = "validation.profile.not.exist";
    public static final String VALIDATION_INVALID = "validation.invalid";
    public static final String VALIDATION_PASSWD_DIFFERENT_OLD = "validation.passwd.different.old";
    public static final String VALIDATION_PASSWORD_MINSIZE = "validation.password.minSize";
    public static final String VALIDATION_UNIQUE_ID = "validation.unique.id";
    public static final String VALIDATION_UNIQUE = "validation.unique";
    public static final String VALIDATION_INVALID_LOG = "validation.invalid.log";
    public static final String VALIDATION_MUSTBE_GREATER_THAN = "validation.mustbe.greater.than";
    public static final String VALIDATION_GREATER_OR_EQUAL_THAN_TODAY = "validation.greaterOrEqualThan.today";
    public static final String VALIDATION_UNIQUE_EMAIL = "validation.email.not.unique";
    public static final String VALIDATAION_REQUIRED_WHEN_SOME_FIELD_NOT_INFORMED = "validataion.required.when.some.field.not.informed";
    public static final String VALIDATION_MAX_SIZE_IMAGE = "validation.max.size.image";
    // Labels
    // ~~~~
    // Links
    // ~~~~

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private MessageConstants() {
        super();
    }
}