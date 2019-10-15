package net.gaox.redis.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * 这里注意的是，序列化的时候，我设置了 SerializerFeature.WriteClassName，这会讲泛型T的类型写入JSON中多了一个@type的属性，来存储类型，这样反序列化的时候就能自动转成原本的类型
 * <p>
 * 同时需要在spring boot中全局设置 ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
 * <p>
 *
 * @author gaox·Eric
 * @date 2019/4/14 11:37
 */
@Configuration
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    /**
     * Serialize the given object to binary data.
     *
     * @param o object to serialize. Can be {@literal null}.
     * @return the equivalent binary data. Can be {@literal null}.
     */

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        try {
//                return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
            return JSON.toJSONBytes(t, SerializerFeature.WriteClassName);
        } catch (Exception ex) {
            throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation. Can be {@literal null}.
     * @return the equivalent object instance. Can be {@literal null}.
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String data = new String(bytes, DEFAULT_CHARSET);
        T result = (T) JSON.parse(data);
        return result;
    }
}