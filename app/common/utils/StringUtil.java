package common.utils;

import java.text.Normalizer;
import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang.StringUtils;

/**
 * @author jgomes
 */
public class StringUtil extends StringUtils {

    private static final String HTML_SPACE = "&nbsp;";
    private static final String ACENTOS_A = "[ÀÁÂÃÄ]";
    private static final String ACENTOS_E = "[ÈÉÊË]";
    private static final String ACENTOS_I = "[ÌÍÎÏ]";
    private static final String ACENTOS_O = "[ÒÓÔÖÕ]";
    private static final String ACENTOS_U = "[ÙÚÛÜ]";
    private static final String ACENTOS_C = "Ç";
    private static final String NON_ASCII = "[^\\p{ASCII}]";
    private static final String EMPTY_STRING = "";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private StringUtil() {
        super();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static boolean isEmpty(final String string) {
        return string == null ? true : string.trim().length() == 0 ? true : false;
    }
    public static boolean isNotEmpty(final String string) {
        return !isEmpty(string);
    }
    public static String format(final String format, final String value) throws ParseException {
        final MaskFormatter mask = new MaskFormatter(format);
        mask.setValueContainsLiteralCharacters(false);
        return mask.valueToString(value);
    }
    public static String removeSpecialsCharacters(final String string) {
        String value = string.toUpperCase();
        value = value.replaceAll(ACENTOS_A, "A");
        value = value.replaceAll(ACENTOS_E, "E");
        value = value.replaceAll(ACENTOS_I, "I");
        value = value.replaceAll(ACENTOS_O, "O");
        value = value.replaceAll(ACENTOS_U, "U");
        value = value.replaceAll(ACENTOS_C, "C");
        return value;
    }
    public static int compare(final String arg0, final String arg1) {
        return StringUtil.removeSpecialsCharacters(arg0).compareToIgnoreCase(StringUtil.removeSpecialsCharacters(arg1));
    }
    public static String padding(final int size, final char padChar) throws IndexOutOfBoundsException {
        final char[] buf = new char[size];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }
    public static String padLeftHtml(String string, final Integer size) {
        final int remaingSize = size - string.length();
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < remaingSize; i++) {
            builder.append(HTML_SPACE);
        }
        string = builder.toString() + string;
        return string;
    }
    public static String padRightHtml(String string, final Integer size) {
        final int remaingSize = size - string.length();
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < remaingSize; i++) {
            builder.append(HTML_SPACE);
        }
        string += builder.toString();
        return string;
    }
    public static String toAscii(final String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll(NON_ASCII, EMPTY_STRING);
    }
}