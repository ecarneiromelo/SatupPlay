package common.exceptions;

/**
 * @author jgomes
 */
public class JasperFileNotFoundException extends Exception {

    private static final long serialVersionUID = -4395406840804967088L;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see java.lang.Throwable#getMessage()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public String getMessage() {
        return "Jasper file does not exist!";
    }
}
