package common.exceptions;

/**
 * @author jgomes
 */
public class SystemException extends Exception {

    private static final long serialVersionUID = 1740560005319554195L;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public SystemException(final Throwable cause) {
        super(cause);
    }
    public SystemException(final String message) {
        super(message);
    }
    public SystemException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
