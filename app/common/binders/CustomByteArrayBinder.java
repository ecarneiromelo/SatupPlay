package common.binders;

import java.lang.annotation.Annotation;
import java.util.List;

import play.data.Upload;
import play.data.binding.Global;
import play.data.binding.TypeBinder;
import play.mvc.Http.Request;

/**
 * Fix play-1.4.2 ByteArrayBinder bug
 *
 * @author jgomes
 */
@Global
public class CustomByteArrayBinder implements TypeBinder<byte[]> {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see play.data.binding.TypeBinder#bind(java.lang.String,
    // java.lang.annotation.Annotation[], java.lang.String, java.lang.Class,
    // java.lang.reflect.Type)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public byte[] bind(final String name, final Annotation[] annotations, final String value, final Class actualClass, final java.lang.reflect.Type genericType) throws Exception {
        if (value == null || value.trim().length() == 0) {
            return null;
        }
        final Request req = Request.current();
        if (req != null && req.args != null) {
            final List<Upload> uploads = (List<Upload>) req.args.get("__UPLOADS");
            if (uploads != null) {
                for (final Upload upload : uploads) {
                    if (upload.getFieldName().equals(value)) {
                        if (upload.asFile() == null) {
                            return new byte[] {};
                        }
                        return upload.asBytes();
                    }
                }
            }
        }
        return null;
    }
}