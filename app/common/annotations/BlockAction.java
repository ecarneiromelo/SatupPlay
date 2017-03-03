package common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jgomes
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BlockAction {

    Action[] value();

    public enum Action {
        INSERT,
        UPDATE,
        DELETE;

        @Override
        public String toString() {
            switch (this) {
                case INSERT:
                    return "blockInsert";
                case UPDATE:
                    return "blockUpdate";
                case DELETE:
                    return "blockDelete";
                default:
                    break;
            }
            return "";
        }
    }
}
