package common.utils;

import static common.constants.DomainConstants.CLOSED;
import static common.constants.DomainConstants.IN_PROGRESS;
import static common.constants.DomainConstants.WAITING_RESPONDENT;
import static common.constants.MessageConstants.STATUS_CLOSED;
import static common.constants.MessageConstants.STATUS_IN_PROGRESS;
import static common.constants.MessageConstants.STATUS_WAITING_RESPONDENT;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jgomes
 */
public class DomainUtil {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private DomainUtil() {
        super();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static final Map<Short, String> getStatus() {
        final Map<Short, String> map = new LinkedHashMap<>();
        map.put(WAITING_RESPONDENT, STATUS_WAITING_RESPONDENT);
        map.put(IN_PROGRESS, STATUS_IN_PROGRESS);
        map.put(CLOSED, STATUS_CLOSED);
        return map;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static enums.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ...
}
