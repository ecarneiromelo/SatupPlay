package common.json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author jgomes
 */
public class EnumOrdinalSerializer implements JsonSerializer<Enum<?>> {

    private static final JsonParser PARSER = new JsonParser();

    @Override
    public JsonElement serialize(final Enum<?> object, final Type type, final JsonSerializationContext context) {
        final Integer ordinal = object.ordinal();
        return PARSER.parse(ordinal.toString());
    }
}