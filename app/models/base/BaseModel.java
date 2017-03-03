package models.base;

import static common.constants.MessageConstants.VALIDATION_GREATER_OR_EQUAL_THAN_TODAY;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import common.exceptions.SystemException;
import common.json.EnumOrdinalSerializer;
import common.json.HibernateProxyTypeAdapter;
import common.json.SkipStrategy;
import common.utils.DateUtil;
import common.utils.DateUtil.DatePattern;
import play.Logger;
import play.data.validation.Check;
import play.db.jpa.GenericModel;
import play.db.jpa.JPABase;

/**
 * @author jgomes
 */
public abstract class BaseModel extends GenericModel {

    private static final long serialVersionUID = 2117445726264358092L;
    protected static final String ID = "id";
    protected static final String NAME = "name";
    protected static final String TITLE = "title";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // JSON aux methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public String toJson() {
        final GsonBuilder builder = buildJsonParser();
        final Gson gson = builder.create();
        return gson.toJson(this);
    }
    public static String toJson(final Object object) {
        final GsonBuilder builder = buildJsonParser();
        final Gson gson = builder.create();
        return gson.toJson(object);
    }
    public static String toJson(final List<?> list) {
        final GsonBuilder builder = buildJsonParser();
        final Gson gson = builder.create();
        return gson.toJson(list);
    }
    public static <T extends JPABase> T fromJson(final String jsonString, final Class<?> clazz) {
        final GsonBuilder builder = buildJsonParser();
        final Gson gson = builder.create();
        return (T) gson.fromJson(jsonString, clazz);
    }
    public static GsonBuilder buildJsonParser() {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeHierarchyAdapter(Enum.class, new EnumOrdinalSerializer());
        builder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        builder.setExclusionStrategies(new SkipStrategy());
        builder.setDateFormat(DatePattern.DASH_YYYY_MM_DD_HH_MM_SS_SSS.getValue());
        return builder;
    }

    /**
     * @author jgomes
     */
    protected static class GreaterOrEqualToday extends Check {

        @Override
        public boolean isSatisfied(final Object object, final Object value) {
            final Date date = (Date) value;
            try {
                if (date == null || !DateUtil.isBeforeTodayWithoutTime(date)) {
                    return true;
                }
            } catch (final SystemException e) {
                Logger.error(e, "GreaterOrEqualToday check failed");
            }
            this.setMessage(VALIDATION_GREATER_OR_EQUAL_THAN_TODAY);
            return false;
        }
    }
}