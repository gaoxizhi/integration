package net.gaox.util.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.Map;

/**
 * <p> json工具 </p>
 *
 * @author gaox·Eric
 * @date 2024-06-06 19:22
 */
public class JsonUtil {

    public static void main(String[] args) {
        JSONObject flatJsonObj = new JSONObject();
        flatJsonObj.put("a.b", new JSONArray(Arrays.asList(1, 3, 5)));
        flatJsonObj.put("a.c", new JSONArray(Arrays.asList(1, 3, 51)));
        flatJsonObj.put("c.b", new JSONArray(Arrays.asList(1, 13, 5)));

        JSONObject nestedJsonObj = transformFlatToJson(flatJsonObj);
        System.out.println(nestedJsonObj.toJSONString());
    }

    public static JSONObject transformFlatToJson(JSONObject flatJsonObject) {
        JSONObject result = new JSONObject();

        for (Map.Entry<String, Object> entry : flatJsonObject.entrySet()) {
            String[] keys = entry.getKey().split("\\.");
            JSONObject current = result;
            for (int i = 0; i < keys.length - 1; i++) {
                JSONObject nextLevel = current.getJSONObject(keys[i]);
                if (nextLevel == null) {
                    nextLevel = new JSONObject();
                    current.put(keys[i], nextLevel);
                }
                current = nextLevel;
            }
            current.put(keys[keys.length - 1], entry.getValue());
        }

        return result;
    }

}
