package common.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.jpa.generators.CustomSequenceGenerator;
import play.db.DB;

/**
 * @author jgomes
 */
public class DBUtil extends DB {

    private static final int DEFAULT_SEQUENCE_START_VALUE = 1;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static void resetSequence(final Class<?> cls) {
        resetSequence(cls, DEFAULT_SEQUENCE_START_VALUE);
    }
    public static void resetSequence(final Class<?> cls, final int startValue) {
        final StringBuilder builder = new StringBuilder("ALTER SEQUENCE ");
        builder.append(CustomSequenceGenerator.getSequenceName(cls));
        builder.append(" RESTART WITH ");
        builder.append(startValue);
        execute(builder.toString());
    }
    public static Long nextval(final Class<?> cls) throws SQLException {
        final String sequenceName = CustomSequenceGenerator.getSequenceName(cls);
        final String driver = PlayUtil.getDefaultJdbcDriver();
        String query = null;
        if (driver.toLowerCase().contains("postgre")) {
            query = "select nextval('" + sequenceName + "')";
        } else if (driver.toLowerCase().contains("oracle")) {
            query = "select " + sequenceName + ".NEXTVAL FROM DUAL";
        }
        Long nextval = null;
        if (StringUtil.isNotEmpty(query)) {
            ResultSet rs = null;
            try {
                rs = executeQuery(query);
                if (rs.next()) {
                    nextval = rs.getLong("nextval");
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
        return nextval;
    }
}