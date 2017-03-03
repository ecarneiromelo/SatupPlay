package common.annotations.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import common.validation.CpfCheck;
import net.sf.oval.configuration.annotation.Constraint;

/**
 * @author jgomes
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.FIELD,
        ElementType.PARAMETER
})
@Constraint(checkWith = CpfCheck.class)
public @interface CPF {

    String message() default CpfCheck.mes;
}
