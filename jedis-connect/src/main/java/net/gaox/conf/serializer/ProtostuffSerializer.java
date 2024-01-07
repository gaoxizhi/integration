package net.gaox.conf.serializer;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;

/**
 * <p> Protostuff 序列化工具 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-07 17:27
 */
public class ProtostuffSerializer {

    public static <T> byte[] serializeInternal(final T source, final Schema<T> schema, final LinkedBuffer buffer) {
        return ProtostuffIOUtil.toByteArray(source, schema, buffer);
    }

    public static <T> T deserializeInternal(final byte[] bytes, final T result, final
    Schema<T> schema) {
        ProtostuffIOUtil.mergeFrom(bytes, result, schema);
        return result;
    }
}
