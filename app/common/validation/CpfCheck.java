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
public class CpfCheck extends AbstractAnnotationCheck<CNPJ> {

    private static final long serialVersionUID = 1522109926195795451L;
    public static final String mes = "validation.cpf";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see net.sf.oval.Check#isSatisfied(java.lang.Object, java.lang.Object,
    // net.sf.oval.context.OValContext, net.sf.oval.Validator)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, final OValContext context, final Validator validator) throws OValException {
        return BrasilUtil.isCPF(value.toString());
    }
}
