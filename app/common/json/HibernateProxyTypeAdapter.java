package common.json;

import java.io.IOException;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * @author jgomes
 */
public class HibernateProxyTypeAdapter extends TypeAdapter<HibernateProxy> {

    public static final TypeAdapterFactory FACTORY;
    private final Gson gsonContext;
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Static Block.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    static {
        FACTORY = new TypeAdapterFactory() {

            @Override
            public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
                final boolean assignableFromType = HibernateProxy.class.isAssignableFrom(type.getRawType());
                if (assignableFromType) {
                    return (TypeAdapter<T>) new HibernateProxyTypeAdapter(gson);
                }
                return null;
            }
        };
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private HibernateProxyTypeAdapter(final Gson gsonContext) {
        this.gsonContext = gsonContext;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see
    // com.google.gson.TypeAdapter#read(com.google.gson.stream.JsonReader)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public HibernateProxy read(final JsonReader arg0) throws IOException {
        return null;
    }
    @Override
    public void write(final JsonWriter writer, final HibernateProxy proxy) throws IOException {
        if (proxy == null) {
            writer.nullValue();
            return;
        }
        final Class<?> baseType = Hibernate.getClass(proxy);
        final TypeAdapter adapter = this.gsonContext.getAdapter(TypeToken.get(baseType));
        final Object originalObject = proxy.getHibernateLazyInitializer().getImplementation();
        adapter.write(writer, originalObject);
    }
}