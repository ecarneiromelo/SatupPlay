package common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author jgomes
 */
public class NumberUtil {

    private static final int DEFAULT_SCALE = 2;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private NumberUtil() {
        super();
    }
    public static double round(final double value) {
        return round(value, DEFAULT_SCALE);
    }
    public static double truncate(final double value, final int scale) {
        final double fixFloatingPoint = Math.pow(10, scale);
        return (long) (value * fixFloatingPoint) / fixFloatingPoint;
    }
    public static double round(final double value, final int scale) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
    public static String formatDouble(final double value) {
        final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return numberFormat.format(value);
    }
    public static String formatDouble(final double value, final Locale locale) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        if (locale != null) {
            numberFormat = NumberFormat.getCurrencyInstance(locale);
        }
        return numberFormat.format(value);
    }
    public static String decimalToHexa(final String data) {
        return new BigInteger(data).toString(16);
    }
    public static Double limitDigits(final Double value, final int digitisNumber) {
        if (value == null) {
            return null;
        }
        String string = value.toString();
        final int decimalSeparatorIndexOf = string.indexOf(".");
        final int size = string.length() - 1;
        if (size - decimalSeparatorIndexOf > digitisNumber) {
            final String firstPiece = string.substring(0, decimalSeparatorIndexOf);
            final String secondPiece = string.substring(decimalSeparatorIndexOf, decimalSeparatorIndexOf + digitisNumber + 1);
            string = firstPiece + secondPiece;
        }
        return Double.parseDouble(string);
    }
    public static boolean isInteger(final String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
    public static boolean isDouble(final String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
}
