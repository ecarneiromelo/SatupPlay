package common.enums;

/**
 * @author jgomes
 */
public enum FileTypeConstant {
    PDF,
    XLSX,
    XLS,
    TXT,
    GIF,
    PNG,
    JASPER;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see java.lang.Enum#toString()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}