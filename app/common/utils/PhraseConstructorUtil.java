package common.utils;

import java.util.List;

public class PhraseConstructorUtil {

    private static String COMMA = ", ";

    public static String constructConjunction(final List<String> objects, final String conjunctionStr) {
        if (objects == null || objects.isEmpty()) {
            return "";
        }
        final String conjunctionWSpace = " " + conjunctionStr + " ";
        String joined = StringUtil.join(objects.toArray(), COMMA);
        joined = joined.replaceFirst("(?s)" + COMMA + "(?!.*?" + COMMA + ")", conjunctionWSpace);
        return joined;
    }
}
