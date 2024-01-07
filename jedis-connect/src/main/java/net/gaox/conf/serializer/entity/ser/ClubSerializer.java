package net.gaox.conf.serializer.entity.ser;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import net.gaox.conf.serializer.ProtostuffSerializer;
import net.gaox.conf.serializer.entity.Club;

/**
 * <p> club 对应的序列化器 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-07 17:32
 */
public class ClubSerializer {

    public static byte[] serialize(final Club club) {
        final LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<Club> schema = RuntimeSchema.createFrom(Club.class);
            return ProtostuffSerializer.serializeInternal(club, schema, buffer);
        } catch (final Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    public static Club deserialize(final byte[] bytes) {
        try {
            Schema<Club> schema = RuntimeSchema.createFrom(Club.class);
            Club club = ProtostuffSerializer.deserializeInternal(bytes, schema.newMessage(), schema);
            if (club != null) {
                return club;
            }
        } catch (final Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return null;
    }

}
