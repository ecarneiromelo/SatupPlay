package common.validation;

import common.annotations.validations.CNPJ;
import common.utils.BrasilUtil;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;

/**
 * @author jgomes - Jefferson Chaves Gomes
 *
 * @see <a
 *      href=
 *      "http://www.devmedia.com.br/validando-o-cnpj-em-uma-aplicacao-java/22374">
 *      Check
 *      CNPJ</a>
 */
public class CnpjCheck extends AbstractAnnotationCheck<CNPJ> {

    private static final long serialVersionUID = -8525257534471662045L;
    public static final String mes = "validation.cnpj";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see net.sf.oval.Check#isSatisfied(java.lang.Object, java.lang.Object,
    // net.sf.oval.context.OValContext, net.sf.oval.Validator)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, final OValContext context, final Validator validator) throws OValException {
        if (value == null) {
            return false;
        }
        return BrasilUtil.isCNPJ(value.toString());
    }
}
