package common.jpa.dialects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.hibernate.id.SequenceGenerator;

import common.jpa.generators.CustomSequenceGenerator;
import play.Logger;

/**
 * This Generator requires that entities have the annotation <code>@Table</code>
 * with his <code>name</code> property assigned.</br>
 *
 * @author jgomes
 */
public class CustomOracle10gDialect extends Oracle10gDialect {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public CustomOracle10gDialect() {
        super();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see
    // org.hibernate.dialect.Oracle8iDialect#registerNumericTypeMappings()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    protected void registerNumericTypeMappings() {
        super.registerNumericTypeMappings();
        super.registerColumnType(Types.DOUBLE, "NUMBER(19,2)");
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see org.hibernate.dialect.Dialect#getNativeIdentifierGeneratorClass()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public Class getNativeIdentifierGeneratorClass() {
        final Class generator = super.getNativeIdentifierGeneratorClass();
        if (generator.isAssignableFrom(SequenceGenerator.class)) {
            return CustomSequenceGenerator.class;
        }
        return generator;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see
    // org.hibernate.dialect.Oracle9iDialect#getCurrentTimestampSelectString()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public String getCurrentTimestampSelectString(final Connection connection) {
        String currentTimestampSelectString = null;
        try {
            final int oracle10g = 10;
            final int databaseMajorVersion = connection.getMetaData().getDatabaseMajorVersion();
            if (databaseMajorVersion < oracle10g) {
                currentTimestampSelectString = new Oracle8iDialect().getCurrentTimestampSelectString();
            } else {
                currentTimestampSelectString = super.getCurrentTimestampSelectString();
            }
        } catch (final SQLException e) {
            Logger.info("Error on Retrieves the major version number of the oracle database.", e);
            currentTimestampSelectString = super.getCurrentTimestampSelectString();
        }
        return currentTimestampSelectString;
    }
}
