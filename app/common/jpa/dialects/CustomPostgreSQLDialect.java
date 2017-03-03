package common.jpa.dialects;

import org.hibernate.dialect.PostgreSQL82Dialect;

import common.jpa.generators.CustomSequenceGenerator;

/**
 * This Generator requires that entities have the annotation <code>@Table</code>
 * with his <code>name</code> property assigned.</br>
 *
 * @author jgomes
 */
public class CustomPostgreSQLDialect extends PostgreSQL82Dialect {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see
    // org.hibernate.dialect.PostgreSQLDialect#getNativeIdentifierGeneratorClass()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public Class getNativeIdentifierGeneratorClass() {
        return CustomSequenceGenerator.class;
    }
    public String getCurrentTimestampUTC0() {
        return "SELECT timezone('UTC'::text, now())";
    }
}