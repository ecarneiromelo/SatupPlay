package common.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import common.exceptions.SystemException;
import common.jpa.dialects.CustomOracle10gDialect;
import common.jpa.dialects.CustomPostgreSQLDialect;
import play.Play;

/**
 * @author jgomes
 */
public class DateUtil extends DateUtils {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static Date currentDate() throws SystemException {
        final String driver = PlayUtil.getDefaultJdbcDriver();
        if (driver.toLowerCase().contains("postgre")) {
            return currentTimestampDbPostgres();
        } else if (driver.toLowerCase().contains("oracle")) {
            return currentTimestampDbOracle();
        }
        return new Date();
    }
    public static Date currentDateUTC0() throws SystemException {
        final String driver = PlayUtil.getDefaultJdbcDriver();
        if (driver.toLowerCase().contains("postgre")) {
            return getCurrentTimestampDbPostgresUTC0();
        } else if (driver.toLowerCase().contains("oracle")) {
            // TODO
        }
        return null;
    }
    public static Timestamp currentTimestamp() throws SystemException {
        return new Timestamp(currentDate().getTime());
    }
    public static Date currentTimestampDbOracle() throws SystemException {
        Date currentTimestamp = null;
        final CustomOracle10gDialect dialect = new CustomOracle10gDialect();
        final String query = dialect.getCurrentTimestampSelectString(DBUtil.getConnection());
        try (
             final ResultSet rs = DBUtil.executeQuery(query)) {
            if (rs.next()) {
                final long datetime = rs.getTimestamp(1).getTime();
                currentTimestamp = new Date(datetime);
            }
        } catch (final SQLException e) {
            throw new SystemException(e);
        }
        return currentTimestamp;
    }
    public static Date currentTimestampDbPostgres() throws SystemException {
        Date currentTimestamp = null;
        final CustomPostgreSQLDialect dialect = new CustomPostgreSQLDialect();
        final String query = dialect.getCurrentTimestampSelectString();
        try (
             final ResultSet rs = DBUtil.executeQuery(query)) {
            if (rs.next()) {
                final String string = rs.getString(1);
                currentTimestamp = parseDate(string);
            }
        } catch (SQLException | ParseException e) {
            throw new SystemException(e);
        }
        return currentTimestamp;
    }
    public static Date getCurrentTimestampDbPostgresUTC0() throws SystemException {
        Date currentTimestamp = null;
        final CustomPostgreSQLDialect dialect = new CustomPostgreSQLDialect();
        final String query = dialect.getCurrentTimestampUTC0();
        try (
             final ResultSet rs = DBUtil.executeQuery(query)) {
            if (rs.next()) {
                final String string = rs.getString(1);
                currentTimestamp = parseDate(string);
            }
        } catch (SQLException | ParseException e) {
            throw new SystemException(e);
        }
        return currentTimestamp;
    }
    public static void synchronizeHostsTimes() throws InterruptedException, SystemException {
        final Date dataBaseDate = currentDate();
        final long diff = dataBaseDate.getTime() - new Date().getTime();
        if (diff > 0) {
            if (Play.mode.isDev()) {
                Logger.getLogger(DateUtil.class).warn(String.format("The hosts times are different, sleeping for %d milliseconds", diff));
            }
            Thread.sleep(diff);
        }
    }
    public static int getDay(final Date date) {
        return get(Calendar.DAY_OF_MONTH, date);
    }
    public static int getFirstDayOfWeek(final Date date) {
        return get(Calendar.DAY_OF_WEEK, date);
    }
    public static int getYear(final Date date) {
        return get(Calendar.YEAR, date);
    }
    public static int getMonth(final Date date) {
        return get(Calendar.MONTH, date);
    }
    public static int getHours(final Date date) {
        return get(Calendar.HOUR_OF_DAY, date);
    }
    public static int getMinutes(final Date date) {
        return get(Calendar.MINUTE, date);
    }
    public static int getSeconds(final Date date) {
        return get(Calendar.SECOND, date);
    }
    public static int get(final int dateField, final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(dateField);
    }
    public static Date toFirstTimeOfDay(final Date date) {
        if (date == null) {
            return null;
        }
        Date firstTimeDate = setHours(date, 0);
        firstTimeDate = setMinutes(firstTimeDate, 0);
        firstTimeDate = setSeconds(firstTimeDate, 0);
        firstTimeDate = setMilliseconds(firstTimeDate, 0);
        return firstTimeDate;
    }
    public static Date toLastTimeOfDay(final Date date) {
        if (date == null) {
            return null;
        }
        Date lastTimeDate = setHours(date, 23);
        lastTimeDate = setMinutes(lastTimeDate, 59);
        lastTimeDate = setSeconds(lastTimeDate, 59);
        lastTimeDate = setMilliseconds(lastTimeDate, 0);
        return lastTimeDate;
    }
    public static Boolean isBeforeToday(final Date date) throws SystemException {
        final Calendar cal1 = Calendar.getInstance();
        final Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);
        cal2.setTime(currentDate());
        if (cal1.before(cal2)) {
            return true;
        }
        return false;
    }
    public static Boolean isAfterToday(final Date date) throws SystemException {
        final Calendar cal1 = Calendar.getInstance();
        final Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);
        cal2.setTime(currentDate());
        if (cal1.after(cal2)) {
            return true;
        }
        return false;
    }
    public static Boolean isCurrentDateInDayLightSaving() throws SystemException {
        final Date date = currentDate();
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone(date.toString()));
        c.setTime(date);
        final int offset = c.get(Calendar.DST_OFFSET);
        if (offset != 0) {
            return true;
        }
        return false;
    }
    public static Date addHoursToDate(final Date date, final int hours) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }
    /**
     * Compare the argument's date at 00:00:00.000 with currentDate at
     * 00:00:00.000.
     *
     * @param date
     *            the Object <code>java.util.Date</code> to be compared.
     * @return <code>true</code> if the time of method's argument is before the
     *         time represented by <code>currentDate</code>; <code>false</code>
     *         otherwise.
     * @throws ParseException
     * @throws SQLException
     */
    public static boolean isBeforeTodayWithoutTime(final Date date) throws SystemException {
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(toFirstTimeOfDay(date));
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(toFirstTimeOfDay(currentDate()));
        if (cal1.before(cal2)) {
            return true;
        }
        return false;
    }
    public static Date parseDate(final String dateValue) throws ParseException {
        try {
            return parseDate(dateValue, DatePattern.SLASH_DD_MM_YYYY_HH_MM_SS_SSS);
        } catch (final ParseException e) {
            return parseDate(dateValue, DatePattern.toStringArray());
        }
    }
    public static Time parseTime(final String value) throws ParseException {
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        final DateFormat formatter = new SimpleDateFormat("HH:mm");
        final long time = formatter.parse(value).getTime();
        return new Time(time);
    }
    public static Date parseDate(final String dateValue, final DatePattern pattern) throws ParseException {
        return parseDate(dateValue, getParsePatterns(pattern));
    }
    public static Date parseDateStrictly(final String str, final DatePattern pattern) throws ParseException {
        return parseDateStrictly(str, getParsePatterns(pattern));
    }
    public static String parseString(final Date date) {
        return parseString(date, DatePattern.SLASH_DD_MM_YYYY_HH_MM_SS_SSS);
    }
    public static String parseString(final Date date, final DatePattern pattern) {
        if (date == null || pattern == null) {
            throw new IllegalArgumentException();
        }
        final DateFormat formatter = new SimpleDateFormat(pattern.getValue());
        return formatter.format(date);
    }
    public static Date parseDate(final String dateValue, final String[] parsePatterns) throws ParseException {
        Date date = null;
        final ParsePosition position = new ParsePosition(0);
        for (final String pattern : parsePatterns) {
            final int timeZonePosition = dateValue.length() - dateValue.indexOf("-");
            if (pattern.indexOf("/") != dateValue.indexOf("/") || timeZonePosition > 5 && pattern.indexOf("-") != dateValue.indexOf("-")) {
                continue;
            }
            if (pattern.equals(DatePattern.DASH_YYYY_MM_DD_HH_MM_SS_SSS.value)) {
                try {
                    final Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Timestamp.valueOf(dateValue).getTime());
                    return calendar.getTime();
                } catch (final IllegalArgumentException e) {
                    Logger.getLogger(DateUtil.class).warn(String.format("Unable to parse the date: %s! Another attempt will be performed with another date pattern", dateValue));
                }
            }
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            date = format.parse(dateValue, position);
            if (date != null) {
                return date;
            }
            position.setIndex(0);
            format = new SimpleDateFormat(pattern, Locale.ENGLISH);
            date = format.parse(dateValue, position);
            if (date != null) {
                return date;
            }
        }
        throw new ParseException("Unable to parse the date: " + dateValue, -1);
    }
    public static boolean isDate(final String str, final String pattern) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.parse(str);
            return true;
        } catch (final ParseException e) {
            return false;
        }
    }
    public static Long differenceOfDaysBetweenDates(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            return null;
        }
        final long differenceMilliSeconds = date2.getTime() - date1.getTime();
        return differenceMilliSeconds / 1000 / 60 / 60 / 24;
    }
    public static Long differenceOfHoursBetweenDates(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            return null;
        }
        final long differenceMilliSeconds = date2.getTime() - date1.getTime();
        return differenceMilliSeconds / 1000 / 60 / 60;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private static methods.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static String[] getParsePatterns(final DatePattern pattern) {
        if (pattern.getValue() == null) {
            return DatePattern.toStringArray();
        }
        return new String[] {
                pattern.getValue()
        };
    }

    /**
     * @author jgomes | 08/11/2012 - 08:58:09
     */
    public enum DatePattern {
        SLASH_DD_MM_YYYY_HH_MM_SS_SSS("dd/MM/yyyy HH:mm:ss.SSS"),
        SLASH_DD_MM_YYYY_HH_MM_SS("dd/MM/yyyy HH:mm:ss"),
        SLASH_DD_MM_YYYY_HH_MM("dd/MM/yyyy HH:mm"),
        SLASH_DD_MM_YYYY("dd/MM/yyyy"),
        SLASH_YYYY_MM_DD_HH_MM_SS_SSS("yyyy/MM/dd HH:mm:ss.SSS"),
        SLASH_YYYY_MM_DD_HH_MM_SS("yyyy/MM/dd HH:mm:ss"),
        SLASH_YYYY_MM_DD_HH_MM("yyyy/MM/dd HH:mm"),
        SLASH_YYYY_MM_DD("yyyy/MM/dd"),
        SLASH_YYYY_MM_DD_HH_MM_SS_SSS_Z("yyyy/MM/dd HH:mm:ss.SSS Z"),
        SLASH_DD_MM_YY_HH_MM_SS_SSS_Z("dd/MM/yy HH:mm:ss.SSS Z"),
        DASH_YYYY_MM_DD_HH_MM_SS_SSS_Z("yyyy-MM-dd HH:mm:ss.SSS Z"),
        DASH_YYYY_MM_DD_HH_MM_SS_SSS("yyyy-MM-dd HH:mm:ss.SSS"),
        DASH_YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
        DASH_YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
        DASH_YYYY_MM_DD_T_HH_MM_SS_SSSZ("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
        DASH_YYYY_MM_DD_T_HH_MM_SSZ("yyyy-MM-dd'T'HH:mm:ssz"),
        DASH_YYYY_MM_DD("yyyy-MM-dd"),
        HH_MM_SS_SSS("HH:mm:ss.SSS"),
        HH_MM_SS("HH:mm:ss"),
        HH_MM("HH:mm"),
        SLASH_DD_MM("dd/MM"),
        DASH_DD_MM_YYYY("dd-MM-yyyy"),
        EEE_DD_MMM_YYYY_HH_MM_SS_Z("EEE, dd MMM yyyy HH:mm:ss z"),
        EEE_MMM_DD_SS_ZZZ_YYYY("EEE MMM dd HH:mm:ss zzz yyyy"),
        YYYYMMDD_HHMM("yyyyMMdd-HHmm"),
        YYYY_DD_MMM_HH_MM_SS_SSS_Z("yyyy dd MMM HH:mm:ss:SSS Z");

        private String value;

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Constructors.
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        private DatePattern(final String pattern) {
            this.value = pattern;
        }
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Get/Set.
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        public String getValue() {
            return this.value;
        }
        public void setValue(final String pattern) {
            this.value = pattern;
        }
        static String[] toStringArray() {
            final DatePattern[] patterns = DatePattern.values();
            final String[] allPatterns = new String[patterns.length];
            for (int i = 0; i < allPatterns.length; i++) {
                allPatterns[i] = patterns[i].getValue();
            }
            return allPatterns;
        }
    }
}