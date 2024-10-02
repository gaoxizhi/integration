package net.gaox.util.date.json.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * <p> 时间反序列化 </p>
 *
 * @author gaox·Eric
 * @date 2024-10-10 17:05
 */
public class LocalDateTimeDeserializer implements ObjectDeserializer {

    @Override
    public LocalDateTime deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Object timestamp = parser.parse();

        if (timestamp == null) {
            return null;
        }

        if (timestamp instanceof Long) {
            return LocalDateTime.ofInstant(new Date((Long) timestamp).toInstant(), ZoneId.systemDefault());
        } else if (timestamp instanceof Integer) {
            return LocalDateTime.ofInstant(new Date(((Integer) timestamp).longValue()).toInstant(), ZoneId.systemDefault());
        } else {
            throw new IllegalArgumentException("Unsupported timestamp type: " + timestamp.getClass().getName());
        }
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
