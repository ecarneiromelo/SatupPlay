package common.constants;

/**
 * @author jgomes
 */
public class DomainConstants {

    // Roles domain
    // ~~~~
    public static final long MASTER = 1;
    public static final long COMPANY = 2;
    // Program status domain
    // ~~~~
    public static final short WAITING_RESPONDENT = 0;
    public static final short IN_PROGRESS = 1;
    public static final short CLOSED = 2;
    // Respondents domain
    // ~~~~
    public static final String STAFF_MEMBER = "funcionario";
    public static final String STAFF_MEMBER_TO = "funcionrio";
    public static final String NOT_STAFF_MEMBER = "dependente";
    // Upload Respondents domain
    // ~~~~
    public static final int COLUMNS_NUMBER = 14;
    public static final int POSITION_NAME = 0;
    public static final int POSITION_EMAIL = 1;
    public static final int POSITION_CELLPHONE = 2;
    public static final int POSITION_GENDER = 3;
    public static final int POSITION_MARITAL_STATUS = 4;
    public static final int POSITION_SALARY = 5;
    public static final int POSITION_JOB_POSITION = 6;
    public static final int POSITION_AGE = 7;
    public static final int POSITION_WORKING_HOURS = 8;
    public static final int POSITION_COMPANY_TIME = 9;
    public static final int POSITION_HOME_ADDRESS = 10;
    public static final int POSITION_BUSINESS_ADDRESS = 11;
    public static final int POSITION_DEPARTMENT = 12;
    public static final int POSITION_STAFF_MEMBER = 13;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private DomainConstants() {
        super();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Enums.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static enum SentType {
        EMAIL,
        SMS,
        ERROR
    }

    public static enum UserStatus {
        ACTIVE,
        BLOCKED,
        INACTIVE
    }
}
