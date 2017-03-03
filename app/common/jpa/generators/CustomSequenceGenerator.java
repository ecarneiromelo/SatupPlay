package common.jpa.generators;

import java.util.Properties;

import javax.persistence.Table;

import org.hibernate.dialect.Dialect;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;

import common.utils.PlayUtil;
import common.utils.StringUtil;
import play.Play;

/**
 * @author jgomes
 */
public class CustomSequenceGenerator extends SequenceGenerator {

    private static final String TABLE_PREFIX_RL = "rl_";
    private static final String SEQUENCE_PREFIX = "sq_";

    @Override
    public void configure(final Type type, final Properties params, final Dialect dialect) {
        if (params.getProperty(SEQUENCE) == null || params.getProperty(SEQUENCE).length() == 0) {
            final String tableName = params.getProperty(TABLE);
            if (tableName != null) {
                params.setProperty(SEQUENCE, getSequenceName(tableName));
            }
        }
        super.configure(type, params, dialect);
    }
    public static String getSequenceName(final Class<?> cls) {
        String schema = "";
        if (Play.runingInTestMode()) {
            schema = PlayUtil.getDefaultDatabaseSchema();
            if (StringUtil.isNotEmpty(schema)) {
                schema += ".";
            }
        }
        String tableName = cls.getSimpleName();
        final Table tableAnnotation = cls.getAnnotation(Table.class);
        if (tableAnnotation != null) {
            tableName = tableAnnotation.name();
        }
        return schema + getSequenceName(tableName);
    }
    private static String getSequenceName(final String tableName) {
        return SEQUENCE_PREFIX + tableName.toLowerCase().replaceAll(TABLE_PREFIX_RL, "");
    }
}
