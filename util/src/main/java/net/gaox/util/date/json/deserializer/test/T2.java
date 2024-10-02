package net.gaox.util.date.json.deserializer.test;

import com.alibaba.fastjson2.JSONObject;
import net.gaox.util.date.json.deserializer.model.Time2;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2024-10-10 17:21
 */
public class T2 {

    public static void main(String[] args) {

        String json = "{\"time\":0}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        Time2 bean = jsonObject.toJavaObject(Time2.class);
        System.out.println(bean.getTime());

        String json2 = "{\"time\":1577836800000}";
        Time2 bean2 = JSONObject.parseObject(json2, Time2.class);
        System.out.println(bean2.getTime());
    }

}
