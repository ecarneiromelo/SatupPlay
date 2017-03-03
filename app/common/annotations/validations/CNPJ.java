package common.annotations.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import common.validation.CnpjCheck;
import net.sf.oval.configuration.annotation.Constraint;

/**
 * @author jgomes
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.FIELD,
        ElementType.PARAMETER
})
@Constraint(checkWith = CnpjCheck.class)
public @interface CNPJ {

    String message() default CnpjCheck.mes;
}
