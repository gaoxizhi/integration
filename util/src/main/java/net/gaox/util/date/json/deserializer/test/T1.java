package net.gaox.util.date.json.deserializer.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import net.gaox.util.date.json.deserializer.LocalDateTimeDeserializer;
import net.gaox.util.date.json.deserializer.model.Time;

import java.time.LocalDateTime;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2024-10-10 17:21
 */
public class T1 {

    public static void main(String[] args) {
        // 注册自定义的反序列化器
        ParserConfig.getGlobalInstance().putDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());

        String json = "{\"time\":16022000}";
        Time bean = JSON.parseObject(json, Time.class);
        System.out.println(bean.getTime());

        String json2 = "{\"time\":1577836800000}";
        Time bean2 = JSON.parseObject(json2, Time.class);
        System.out.println(bean2.getTime());
    }

}
