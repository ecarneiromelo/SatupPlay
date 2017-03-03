package common.utils;

import java.text.ParseException;
import java.util.Arrays;

/**
 * @author jgomes
 */
public class BrasilUtil {

    private static final String[] CNPJ_FAKE_ARRAY;
    private static final String[] CPF_FAKE_ARRAY;
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Static block.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    static {
        CNPJ_FAKE_ARRAY = new String[] {
                StringUtil.padding(14, '0'),
                StringUtil.padding(14, '1'),
                StringUtil.padding(14, '2'),
                StringUtil.padding(14, '3'),
                StringUtil.padding(14, '4'),
                StringUtil.padding(14, '5'),
                StringUtil.padding(14, '6'),
                StringUtil.padding(14, '7'),
                StringUtil.padding(14, '8'),
                StringUtil.padding(14, '9')
        };
        CPF_FAKE_ARRAY = new String[] {
                StringUtil.padding(11, '0'),
                StringUtil.padding(11, '1'),
                StringUtil.padding(11, '2'),
                StringUtil.padding(11, '3'),
                StringUtil.padding(11, '4'),
                StringUtil.padding(11, '5'),
                StringUtil.padding(11, '6'),
                StringUtil.padding(11, '7'),
                StringUtil.padding(11, '8'),
                StringUtil.padding(11, '9')
        };
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private BrasilUtil() {
        super();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static String formatCNPJ(final String value) throws ParseException {
        final String aux = value.replaceAll("[./-]", "");
        return StringUtil.format("##.###.###/####-##", aux);
    }
    public static String formatCPF(final String value) throws ParseException {
        final String aux = value.replaceAll("[.-]", "");
        return StringUtil.format("###.###.###-##", aux);
    }
    public static boolean isCNPJ(final String cnpj) {
        final String auxCnpj = cnpj.replaceAll("[./-]", "");
        if (auxCnpj.length() != 14 || Arrays.asList(CNPJ_FAKE_ARRAY).contains(auxCnpj)) {
            return false;
        }
        final char[] cnpjCharArray = auxCnpj.toCharArray();
        // First step
        int count = cnpjDoStepOne(cnpjCharArray);
        int checksum = 11 - count % 11;
        String aux = auxCnpj.substring(0, 12);
        aux += checksum == 10 || checksum == 11 ? "0" : Integer.toString(checksum);
        // Seconde step
        count = cnpjDoStepTwo(cnpjCharArray);
        checksum = 11 - count % 11;
        aux += checksum == 10 || checksum == 11 ? "0" : Integer.toString(checksum);
        return auxCnpj.equals(aux);
    }
    private static int cnpjDoStepOne(final char[] cnpjCharArray) {
        int count = 0;
        // multiply/Sum all the numbers in the char array by a standard sequence
        for (int i = 0; i < 4; i++) {
            if (cnpjCharArray[i] - 48 >= 0 && cnpjCharArray[i] - 48 <= 9) {
                count += (cnpjCharArray[i] - 48) * (6 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (cnpjCharArray[i + 4] - 48 >= 0 && cnpjCharArray[i + 4] - 48 <= 9) {
                count += (cnpjCharArray[i + 4] - 48) * (10 - (i + 1));
            }
        }
        return count;
    }
    private static int cnpjDoStepTwo(final char[] cnpjCharArray) {
        int count;
        count = 0;
        for (int i = 0; i < 5; i++) {
            if (cnpjCharArray[i] - 48 >= 0 && cnpjCharArray[i] - 48 <= 9) {
                count += (cnpjCharArray[i] - 48) * (7 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (cnpjCharArray[i + 5] - 48 >= 0 && cnpjCharArray[i + 5] - 48 <= 9) {
                count += (cnpjCharArray[i + 5] - 48) * (10 - (i + 1));
            }
        }
        return count;
    }
    public static boolean isCPF(final String cpf) {
        final String auxCpf = cpf.replaceAll("[.-]", "");
        if (auxCpf.length() != 11 || Arrays.asList(CPF_FAKE_ARRAY).contains(auxCpf)) {
            return false;
        }
        int auxDigit1 = 0, auxDigit2 = 0, cpfDigit;
        for (int i = 1; i < auxCpf.length() - 1; i++) {
            cpfDigit = Integer.parseInt(auxCpf.substring(i - 1, i));
            auxDigit1 = auxDigit1 + (11 - i) * cpfDigit;
            auxDigit2 = auxDigit2 + (12 - i) * cpfDigit;
        }
        int digit1 = 0, digit2 = 0;
        int remainder = auxDigit1 % 11;
        if (remainder < 2) {
            digit1 = 0;
        } else {
            digit1 = 11 - remainder;
        }
        auxDigit2 += 2 * digit1;
        remainder = auxDigit2 % 11;
        if (remainder < 2) {
            digit2 = 0;
        } else {
            digit2 = 11 - remainder;
        }
        final String checksum = auxCpf.substring(auxCpf.length() - 2, auxCpf.length());
        final String result = String.valueOf(digit1) + String.valueOf(digit2);
        return checksum.equals(result);
    }
}